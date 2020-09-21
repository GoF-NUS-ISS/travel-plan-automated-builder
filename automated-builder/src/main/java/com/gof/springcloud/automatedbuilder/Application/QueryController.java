package com.gof.springcloud.automatedbuilder.Application;

import com.gof.springcloud.automatedbuilder.Application.Request.*;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntitySaver;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Destination;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Origin;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.TravelCost;
import com.gof.springcloud.automatedbuilder.Domain.Service.IQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.temporal.ChronoUnit;

@Api("Automated Plan Builder API")
@RestController
@RequestMapping("/")
public class QueryController {

    private final IQueryService queryService;

    @Autowired
    public QueryController (IQueryService queryService){
        this.queryService = queryService;
    }

    @PostMapping("/addPlanAsGraph")
    @ApiOperation(value = "add existing plan as graph")
    public ResponseEntity addPlanAsGraph(@RequestBody TravelPlanModel travelPlanModel){



        TravelNode_LinkedList headModelNode = new TravelNode_LinkedList();
        TravelNode_LinkedList currModelNode = headModelNode;

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


        AbstractNodeEntitySaver abstractNodeEntitySaver = new AbstractNodeEntitySaver();
        AbstractNodeEntity head = new Activity();

        while(currModelNode.getNext() != null){
            if(currModelNode.getRequestModelNode() instanceof TravelPlanModel_Activity){
                TravelPlanModel_Activity activityModelCurr = (TravelPlanModel_Activity) currModelNode.getRequestModelNode();
                Activity activityNode = new Activity();

                activityNode.getCategory().setActivityCategory(activityModelCurr.getCategory());
                activityNode.getCost().setCost(activityModelCurr.getCost());
                activityNode.getRating().setStars(activityModelCurr.getRating());
                activityNode.getReview().setDescription(activityModelCurr.getReview());
                activityNode.getDuration().setSeconds(ChronoUnit.SECONDS.between(activityModelCurr.timeStart.toLocalTime(), activityModelCurr.timeEnd.toLocalTime()));

                AbstractNodeEntity prevEntity = abstractNodeEntitySaver.getAbstractNodeEntity();
                if(prevEntity != null){
                    if(prevEntity instanceof Activity){
                        ((Activity) prevEntity).setActivity(activityNode);
                    }
                    if(prevEntity instanceof Destination){
                        ((Destination) prevEntity).setActivity(activityNode);
                    }
                }
                else{
                    head = activityNode;
                }

                abstractNodeEntitySaver.setAbstractNodeEntity(activityNode);
            }

            if(currModelNode.getRequestModelNode() instanceof TravelPlanModel_Leg){
                Origin origin = new Origin();
                TravelCost travelCost = new TravelCost();
                Destination destination = new Destination();

                TravelPlanModel_Leg leg = (TravelPlanModel_Leg) currModelNode.getRequestModelNode();
                origin.setOrigin(leg.getFrom());
                travelCost.setCost(leg.getCost());
                travelCost.setTransportMode(leg.getTransportMode());
                travelCost.setSeconds(ChronoUnit.SECONDS.between(leg.getStartOn().toLocalTime(), leg.getReturnDate().toLocalTime()));
                destination.setDestination(leg.getTo());
                origin.setTravelCost(travelCost);
                destination.setTravelCost(travelCost);
                travelCost.setStartFrom(origin);
                travelCost.setStopAt(destination);

                AbstractNodeEntity prevEntity = abstractNodeEntitySaver.getAbstractNodeEntity();
                if(prevEntity != null){
                    if(prevEntity instanceof Activity){
                        origin.setActivity((Activity) prevEntity);
                        ((Activity) prevEntity).setLocation(origin);
                    }
                    if(prevEntity instanceof Destination){
                        ((Destination) prevEntity).setNeighbour(origin);
                    }
                }
                else{
                    head = origin;
                }

                abstractNodeEntitySaver.setAbstractNodeEntity(destination);
            }

            currModelNode = currModelNode.getNext();

        }

        queryService.Save(head);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }





}
