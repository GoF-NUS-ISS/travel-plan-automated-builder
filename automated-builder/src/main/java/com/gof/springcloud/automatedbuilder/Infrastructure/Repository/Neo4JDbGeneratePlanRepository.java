package com.gof.springcloud.automatedbuilder.Infrastructure.Repository;

import com.gof.springcloud.automatedbuilder.Application.Request.QueryBody;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntityLinkedList;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.HasActivityCost;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.IsLocatedCost;
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

    private final SpringDataNeo4jRepository neo4jRepository;
    private final SpringDataNeo4jActivityRepository activityRepository;
    private final SpringDataNeo4jLocationRepository locationRepository;

    @Autowired
    public Neo4JDbGeneratePlanRepository(final SpringDataNeo4jRepository springDataNeo4JActivityRepository,
                                         final SpringDataNeo4jActivityRepository activityRepository,
                                         final SpringDataNeo4jLocationRepository locationRepository){
        this.neo4jRepository = springDataNeo4JActivityRepository;
        this.activityRepository = activityRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public AbstractNodeEntity Save(AbstractNodeEntity entity, AbstractNodeEntityLinkedList linkedListNode){
        List<AbstractNodeEntity> entityList = new ArrayList<>();
        boolean matchFound = false;
        while(linkedListNode != null){
            AbstractNodeEntity abstractNodeEntity = linkedListNode.getAbstractNodeEntity();
            if(abstractNodeEntity instanceof Location){
                Location newLocation = ((Location) abstractNodeEntity);
                List<Location> currLocationInDBList = locationRepository.findLocationByString(newLocation.getLocation());
                if (!currLocationInDBList.isEmpty()){
                    Location currLocationInDB = currLocationInDBList.get(0);
                    matchFound = true;
                    log.info("current location {} found", newLocation.getLocation());
                    //move outgoing edge to current node
                    updateLocationOutgoingNodes(currLocationInDB, newLocation);

                    //point incoming edge to current node
                    updateLocationIncomingLocation(currLocationInDB, linkedListNode.getPrev(), entityList);
                    updateLocationIncomingActivity(currLocationInDB, linkedListNode.getPrev(), entityList);

                    //For next node, only can update Activity as Location as no incoming relationship
                    updateLocationIncomingActivity(currLocationInDB, linkedListNode.getNext(), entityList);

                    entityList.add(currLocationInDB);
                }
            }
            linkedListNode = linkedListNode.getPrev();
        }
        if (matchFound) {
            neo4jRepository.saveAll(entityList);
        } else {
            neo4jRepository.save(entity);
        }

        return entity;
    }

    private void updateLocationIncomingLocation(Location currLocationInDB, AbstractNodeEntityLinkedList linkedListNode, List<AbstractNodeEntity> entityList) {
        if(linkedListNode != null){
            AbstractNodeEntity entity = linkedListNode.getAbstractNodeEntity();
            if(entity instanceof Location){
                List<TravelCost> travelCostList = ((Location) entity).getTravelCost();
                if(travelCostList != null && !travelCostList.isEmpty()){
                    log.info("incoming location");
                    travelCostList.get(0).setStartLoc(currLocationInDB);
                    entityList.add(entity);
                }
            }
        }
    }

    private void updateLocationIncomingActivity(Location currLocationInDB, AbstractNodeEntityLinkedList linkedListNode, List<AbstractNodeEntity> entityList) {
        if(linkedListNode != null){
            AbstractNodeEntity entity = linkedListNode.getAbstractNodeEntity();
            if(entity instanceof Activity){
                List<IsLocatedCost> isLocatedCostList = ((Activity) entity).getIsLocatedCost();
                if(isLocatedCostList != null && !isLocatedCostList.isEmpty()){
                    log.info("incoming activity");
                    isLocatedCostList.get(0).setLocation(currLocationInDB);
                    entityList.add(entity);
                }
            }
        }
    }


    private void updateLocationOutgoingNodes(Location currLocationInDB, Location newLocation) {
        if(newLocation.getHasActivityCost() != null && !newLocation.getHasActivityCost().isEmpty()){
            List<HasActivityCost> currActCostList = currLocationInDB.getHasActivityCost();
            if(currActCostList == null){
                currActCostList = new ArrayList<>();
            }
            HasActivityCost newActCost = newLocation.getHasActivityCost().get(0);
            newActCost.setLocation(currLocationInDB);
            currActCostList.add(newActCost);
            currLocationInDB.setHasActivityCost(currActCostList);
            log.info("outgoing");
        }

        if(newLocation.getTravelCost() != null && !newLocation.getTravelCost().isEmpty()){
            List<TravelCost> currTravelCostList = currLocationInDB.getTravelCost();

            if(currTravelCostList == null){
                currTravelCostList = new ArrayList<>();
            }

            currTravelCostList.add(newLocation.getTravelCost().get(0));
            currLocationInDB.setTravelCost(currTravelCostList);
        }
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
