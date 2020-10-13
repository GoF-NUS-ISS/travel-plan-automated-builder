package com.gof.springcloud.automatedbuilder.Infrastructure.Repository;

import com.gof.springcloud.automatedbuilder.Application.Request.QueryBody;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Location;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.TravelCost;
import com.gof.springcloud.automatedbuilder.Domain.Repository.IGeneratePlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Primary
@Transactional
@Slf4j
public class Neo4JDbGeneratePlanRepository implements IGeneratePlanRepository {

    private final SpringDataNeo4jRepository repository;
    private final SpringDataNeo4jActivityRepository activityRepository;

    @Autowired
    public Neo4JDbGeneratePlanRepository(final SpringDataNeo4jRepository springDataNeo4JActivityRepository, final SpringDataNeo4jActivityRepository activityRepository){
        this.repository = springDataNeo4JActivityRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public void Save(AbstractNodeEntity entity){
        repository.save(entity);
    }

    @Override
    public List<AbstractNodeEntity> GeneratePlan(QueryBody body) {
        List<Activity> activityList = activityRepository.getActivitiesByRank(body.getCategory(), body.getDescription(), body.getStars());

        List<AbstractNodeEntity> entities = generatePlanFromActivityList(activityList, body.getTotalCost());
        log.info("Generated entity list: {}", entities);
        return entities;
    }

    @Override
    public void trigger() {
        QueryBody body = new QueryBody();
        body.setCategory("food");
        body.setDescription("soup");
        body.setStars(4);
        body.setTotalCost(2000);
        List<Activity> activityList = activityRepository.getActivitiesByRank(body.getCategory(), body.getDescription(), body.getStars());

        List<AbstractNodeEntity> entities = generatePlanFromActivityList(activityList, body.getTotalCost());
        log.info("Generated entity list: {}", entities);

    }

    private List<AbstractNodeEntity> generatePlanFromActivityList(List<Activity> activityList, int totalCost) {
        List<AbstractNodeEntity> entityList = new ArrayList<>();

        Map<Long, Map<Long, Double>> allPairCombinations = convert(activityRepository.getAllPairs());
        log.info("all pairs: {}", allPairCombinations);

        Map<Long, Long> sourceDistMap = new HashMap<>();
        int currCost=0;
        int i=1;
        while(currCost<=totalCost && i<activityList.size()){
            Long sourceId = activityList.get(i-1).getId();
            Long targetId = activityList.get(i).getId();
            log.info("sourceId: {}", sourceId);
            log.info("targetId: {}", targetId);
            Map<Long, Double> map = allPairCombinations.get(sourceId);
            double test = map.get(targetId);
            log.info("test {}", test);
            int cost = (int)Math.ceil(test);
            sourceDistMap.put(sourceId, targetId);
            currCost+= cost;
            i++;
        }
        log.info("currCost: {}", currCost);
        log.info("sourceDistMap: {}", sourceDistMap);

        sourceDistMap.keySet().forEach(s ->{
            Long tgt=sourceDistMap.get(s);
            log.info("Get path from node id {} to id {}", s, tgt);
            Iterable<Map<String, Object>> path = activityRepository.getPath(s, tgt);
            path.forEach(indivMap -> {
                log.info("indiv map: {}", indivMap);
                AbstractNodeEntity entity = (AbstractNodeEntity) indivMap.get("n");
                if(entityList.isEmpty()){
                    entityList.add(entity);
                }
                AbstractNodeEntity lastElem = entityList.get(entityList.size()-1);
                log.info("lastElem: {}", lastElem);
                log.info("entity: {}", entity);

                if(!lastElem.getId().equals(entity.getId())){
                    if(!(entity instanceof Location && lastElem instanceof Location)){
                        if(lastElem instanceof TravelCost){
                            AbstractNodeEntity secondLast = entityList.get(entityList.size()-2);
                            if(!secondLast.getId().equals(entity.getId())){
                                log.info("Adding entity {}", entity);
                                entityList.add(entity);
                            }
                        }
                        else{
                            log.info("Adding entity {}", entity);
                            entityList.add(entity);
                        }
                    }
                    else if(indivMap.get("r") instanceof TravelCost){
                        log.info("Adding location {}", entity);
                        entityList.add(entity);
                        TravelCost cost = (TravelCost)indivMap.get("r");
                        log.info("Add travel cost: {}", cost);
                        entityList.add(cost);
                    }

                }
            });
        });

        return entityList;
    }

    private Map<Long, Map<Long, Double>> convert(Iterable<Map<String, Object>> iterable){
        log.info("iterable {}", iterable);
        Map<Long, Map<Long, Double>> outerMap = new HashMap<>();

        iterable.forEach(x ->{
            Map<Long, Double> innerMap = outerMap.getOrDefault((Long) x.get("sourceNodeId"), new HashMap<>());
            innerMap.put((Long) x.get("targetNodeId"), (Double) x.get("distance"));
            outerMap.put((Long) x.get("sourceNodeId"), innerMap);
        });

        return outerMap;
    }
}
