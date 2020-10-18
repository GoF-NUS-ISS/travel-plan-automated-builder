package com.gof.springcloud.automatedbuilder.Application.Mapper;

import com.gof.springcloud.automatedbuilder.Application.Pulsar.Neo4jEntity;
import com.gof.springcloud.automatedbuilder.Application.Request.*;
import com.gof.springcloud.automatedbuilder.Application.TravelNode_LinkedList;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntityLinkedList;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.HasActivityCost;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.IsLocatedCost;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.IsNextToCost;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Location;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.TravelCost;
import lombok.extern.slf4j.Slf4j;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ModelToGraphMapper {

    public static Neo4jEntity convert(TravelPlanModel travelPlanModel){
        log.info("travelPlanModel: {}", travelPlanModel);
        TravelNode_LinkedList headModelNode = new TravelNode_LinkedList();
        TravelNode_LinkedList currModelNode  = headModelNode;

        for(TravelPlanModel_Day day: travelPlanModel.getDays()){
            for(TravelPlanModel_DayNode node: day.getNodes()){
                currModelNode.setRequestModelNode(node);
                TravelNode_LinkedList next = new TravelNode_LinkedList();
                currModelNode.setNext(next);
                currModelNode = next;
            }
            currModelNode.setNext(null);
        }

        currModelNode = headModelNode;

        AbstractNodeEntity head = new Activity();
        AbstractNodeEntityLinkedList nodeEntityLinkedList = new AbstractNodeEntityLinkedList();

        while(currModelNode.getNext() != null){
            if(currModelNode.getRequestModelNode() instanceof TravelPlanModel_Activity){
                TravelPlanModel_Activity activityModelCurr = (TravelPlanModel_Activity) currModelNode.getRequestModelNode();
                Activity activityNode = new Activity();

                activityNode.setCategory(activityModelCurr.getCategory());
                activityNode.setCost(activityModelCurr.getCost());
                activityNode.setStars(activityModelCurr.getRating());
                activityNode.setDescription(activityModelCurr.getReview());
                activityNode.setSeconds(ChronoUnit.SECONDS.between(activityModelCurr.timeStart.toLocalTime(), activityModelCurr.timeEnd.toLocalTime()));

                if(nodeEntityLinkedList.getPrev() != null && nodeEntityLinkedList.getPrev().getAbstractNodeEntity() != null){
                    AbstractNodeEntity prevEntity = nodeEntityLinkedList.getPrev().getAbstractNodeEntity();
                    if(prevEntity instanceof Activity){
                        IsNextToCost isNextToCost = new IsNextToCost();
                        List<IsNextToCost> isNextToCostList = new ArrayList<>();
                        isNextToCost.setActivity((Activity) prevEntity);
                        isNextToCost.setActivity1(activityNode);
                        isNextToCostList.add(isNextToCost);
                        ((Activity) prevEntity).setIsNextToCost(isNextToCostList);
                    }
                    if(prevEntity instanceof Location){
                        List<HasActivityCost> activityCostList = new ArrayList<>();
                        HasActivityCost hasActivityCost = new HasActivityCost();
                        List<IsLocatedCost> isLocatedCostList = new ArrayList<>();
                        IsLocatedCost isLocatedCost = new IsLocatedCost();

                        hasActivityCost.setCost(activityNode.getCost());
                        isLocatedCost.setCost(activityNode.getCost());

                        hasActivityCost.setActivity(activityNode);
                        hasActivityCost.setLocation((Location) prevEntity);

                        isLocatedCost.setActivity(activityNode);
                        isLocatedCost.setLocation((Location) prevEntity);

                        activityCostList.add(hasActivityCost);
                        ((Location) prevEntity).setHasActivityCost(activityCostList);
                        isLocatedCostList.add(isLocatedCost);
                        activityNode.setIsLocatedCost(isLocatedCostList);
                    }
                }
                else{
                    head = activityNode;
                }

                nodeEntityLinkedList = getNextNode(activityNode, nodeEntityLinkedList);
            }

            if(currModelNode.getRequestModelNode() instanceof TravelPlanModel_Leg){
                Location start = new Location();
                Location end = new Location();
                TravelCost travelCost = new TravelCost();
                List<TravelCost> travelCostList = new ArrayList<>();

                TravelPlanModel_Leg leg = (TravelPlanModel_Leg) currModelNode.getRequestModelNode();
                travelCost.setCost(leg.getCost());
                travelCost.setTransportMode(leg.getTransportMode());
                travelCost.setSeconds(ChronoUnit.SECONDS.between(leg.getStartOn().toLocalTime(), leg.getStopAt().toLocalTime()));

                start.setLocation(leg.getFrom());
                end.setLocation(leg.getTo());

                travelCost.setStartLoc(start);
                travelCost.setEndLoc(end);
                travelCostList.add(travelCost);
                start.setTravelCost(travelCostList);

                if(nodeEntityLinkedList.getPrev() != null && nodeEntityLinkedList.getPrev().getAbstractNodeEntity() != null){
                    AbstractNodeEntity prevEntity = nodeEntityLinkedList.getPrev().getAbstractNodeEntity();
                    if(prevEntity instanceof Activity){
                        HasActivityCost hasActivityCost = new HasActivityCost();
                        List<HasActivityCost> hasActivityCostList = new ArrayList<>();
                        IsLocatedCost isLocatedCost = new IsLocatedCost();
                        List<IsLocatedCost> isLocatedCostList = new ArrayList<>();

                        hasActivityCost.setCost(((Activity) prevEntity).getCost());
                        isLocatedCost.setCost(((Activity) prevEntity).getCost());

                        hasActivityCost.setActivity((Activity) prevEntity);
                        hasActivityCost.setLocation(start);

                        isLocatedCost.setActivity((Activity) prevEntity);
                        isLocatedCost.setLocation(start);

                        isLocatedCostList.add(isLocatedCost);
                        ((Activity) prevEntity).setIsLocatedCost(isLocatedCostList);
                        hasActivityCostList.add(hasActivityCost);
                        start.setHasActivityCost(hasActivityCostList);
                    }
                    if(prevEntity instanceof Location){
                        TravelCost bridgeLocationCost = new TravelCost();
                        List<TravelCost> bridgeLocationCostList = new ArrayList<>();
                        bridgeLocationCost.setStartLoc((Location) prevEntity);
                        bridgeLocationCost.setEndLoc(start);
                        bridgeLocationCostList.add(bridgeLocationCost);
                        ((Location) prevEntity).setTravelCost(bridgeLocationCostList);
                    }
                }
                else{
                    head = start;
                }

                //for start location
                nodeEntityLinkedList = getNextNode(start, nodeEntityLinkedList);
                //for end location
                nodeEntityLinkedList = getNextNode(end, nodeEntityLinkedList);
            }

            currModelNode = currModelNode.getNext();

        }
        log.info("final head: {}", head);
        return new Neo4jEntity(head, nodeEntityLinkedList);
    }

    private static AbstractNodeEntityLinkedList getNextNode(AbstractNodeEntity currEntity, AbstractNodeEntityLinkedList currNodeEntityLinkedList) {
        AbstractNodeEntityLinkedList next = new AbstractNodeEntityLinkedList();

        currNodeEntityLinkedList.setAbstractNodeEntity(currEntity);
        next.setPrev(currNodeEntityLinkedList);
        currNodeEntityLinkedList.setNext(next);

        return next;
    }
}
