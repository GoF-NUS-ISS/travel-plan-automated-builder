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

        List<AbstractNodeEntity> entities = generatePlanFromActivityList(activityList, body.getStartCost(), body.getEndCost());
        log.info("Generated entity list: {}", entities);
        return entities;
    }

    private List<AbstractNodeEntity> generatePlanFromActivityList(List<Activity> activityList, int startCost, int endCost) {
        List<AbstractNodeEntity> entityList = new ArrayList<>();

        Map<Long, Map<Long, Double>> allPairCombinations = convert(activityRepository.getAllPairs());
        log.info("all pairs: {}", allPairCombinations);

        Map<Long, Long> sourceDistMap = new HashMap<>();
        int currCost=0;
        int i=1;
        while(currCost<=endCost && i<activityList.size()){
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
        if(currCost<startCost){
            log.info("Unable to fulfill start cost");
        }
        else {
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

                    if(!lastElem.getId().equals(entity.getId())){
                        if(!(entity instanceof Location && lastElem instanceof Location)){
                            if(lastElem instanceof TravelCost){
                                TravelCost cost = (TravelCost) lastElem;
                                if(!((TravelCost) lastElem).getEndLoc().getId().equals(entity.getId())){
                                    entityList.add(entity);
                                }
                            }
                            else{
                                entityList.add(entity);
                            }
                        }
                        else if(indivMap.get("r") instanceof TravelCost){
                            Location start = (Location) lastElem;
                            Location end = (Location) entity;
                            TravelCost cost = (TravelCost)indivMap.get("r");

                            //fix bug for where cost refer to same object
                            TravelCost newCost = new TravelCost();
                            newCost.setId(cost.getId());
                            newCost.setSeconds(cost.getSeconds());
                            newCost.setCost(cost.getCost());
                            newCost.setTransportMode(cost.getTransportMode());
                            newCost.setStartLoc(start);
                            newCost.setEndLoc(end);

                            entityList.remove(start);
                            entityList.add(newCost);
                        }

                    }
                });
            });
        }

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
