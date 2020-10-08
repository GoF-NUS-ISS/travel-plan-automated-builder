package com.gof.springcloud.automatedbuilder.Application;

import com.gof.springcloud.automatedbuilder.Application.Request.*;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntitySaver;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.HasActivityCost;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.IsLocatedCost;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.IsNextToCost;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Location;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.TravelCost;
import com.gof.springcloud.automatedbuilder.Domain.Service.IGeneratePlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;

@Api("Automated Plan Builder API")
@RestController
@RequestMapping("/")
@Slf4j
public class GeneratePlanController {

    private final IGeneratePlanService generatePlanService;

    @Autowired
    public GeneratePlanController(IGeneratePlanService generatePlanService){
        this.generatePlanService = generatePlanService;
    }

    @GetMapping("triggerAllPairs")
    @ApiOperation(value="trigger")
    public ResponseEntity trigger(){
        generatePlanService.triggerAllPairs();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/generatePlan")
    @ApiOperation(value = "Generate Plan")
    public ResponseEntity generatePlan(@RequestBody QueryBody queryBody){
        log.info("querybody: {}",queryBody);
        return ResponseEntity.status(HttpStatus.OK).body(generatePlanService.GeneratePlan(queryBody));
    }

    @PostMapping("/addPlanAsGraph")
    @ApiOperation(value = "add existing plan as graph")
    public ResponseEntity addPlanAsGraph(@RequestBody TravelPlanModel travelPlanModel){
        log.info("travelPlanModel: {}", travelPlanModel);
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

                activityNode.setCategory(activityModelCurr.getCategory());
                activityNode.setCost(activityModelCurr.getCost());
                activityNode.setStars(activityModelCurr.getRating());
                activityNode.setDescription(activityModelCurr.getReview());
                activityNode.setSeconds(ChronoUnit.SECONDS.between(activityModelCurr.timeStart.toLocalTime(), activityModelCurr.timeEnd.toLocalTime()));

                AbstractNodeEntity prevEntity = abstractNodeEntitySaver.getAbstractNodeEntity();
                if(prevEntity != null){
                    if(prevEntity instanceof Activity){
                        IsNextToCost isNextToCost = new IsNextToCost();
                        isNextToCost.setActivity((Activity) prevEntity);
                        isNextToCost.setActivity1(activityNode);
                        (activityNode).setIsNextToCost(isNextToCost);
                        ((Activity) prevEntity).setIsNextToCost(isNextToCost);
                    }
                    if(prevEntity instanceof Location){
                        HasActivityCost hasActivityCost = new HasActivityCost();
                        IsLocatedCost isLocatedCost = new IsLocatedCost();

                        hasActivityCost.setCost(activityNode.getCost());
                        isLocatedCost.setCost(activityNode.getCost());

                        hasActivityCost.setActivity(activityNode);
                        hasActivityCost.setLocation((Location) prevEntity);

                        isLocatedCost.setActivity(activityNode);
                        isLocatedCost.setLocation((Location) prevEntity);

                        ((Location) prevEntity).setHasActivityCost(hasActivityCost);
                        activityNode.setIsLocatedCost(isLocatedCost);
                    }
                }
                else{
                    head = activityNode;
                }

                abstractNodeEntitySaver.setAbstractNodeEntity(activityNode);
            }

            if(currModelNode.getRequestModelNode() instanceof TravelPlanModel_Leg){
                Location start = new Location();
                Location end = new Location();
                TravelCost travelCost = new TravelCost();

                TravelPlanModel_Leg leg = (TravelPlanModel_Leg) currModelNode.getRequestModelNode();
                travelCost.setCost(leg.getCost());
                travelCost.setTransportMode(leg.getTransportMode());
                travelCost.setSeconds(ChronoUnit.SECONDS.between(leg.getStartOn().toLocalTime(), leg.getStopAt().toLocalTime()));

                start.setLocation(leg.getFrom());
                end.setLocation(leg.getTo());

                travelCost.setStartLoc(start);
                travelCost.setEndLoc(end);
                start.setTravelCost(travelCost);
                end.setTravelCost(travelCost);

                AbstractNodeEntity prevEntity = abstractNodeEntitySaver.getAbstractNodeEntity();
                if(prevEntity != null){
                    if(prevEntity instanceof Activity){
                        HasActivityCost hasActivityCost = new HasActivityCost();
                        IsLocatedCost isLocatedCost = new IsLocatedCost();

                        hasActivityCost.setCost(((Activity) prevEntity).getCost());
                        isLocatedCost.setCost(((Activity) prevEntity).getCost());

                        hasActivityCost.setActivity((Activity) prevEntity);
                        hasActivityCost.setLocation(start);

                        isLocatedCost.setActivity((Activity) prevEntity);
                        isLocatedCost.setLocation(start);

                        ((Activity) prevEntity).setIsLocatedCost(isLocatedCost);
                        start.setHasActivityCost(hasActivityCost);
                    }
                    if(prevEntity instanceof Location){
                        TravelCost bridgeLocationCost = new TravelCost();
                        bridgeLocationCost.setStartLoc((Location) prevEntity);
                        bridgeLocationCost.setEndLoc(start);
                        ((Location) prevEntity).setTravelCost(bridgeLocationCost);
                    }
                }
                else{
                    head = start;
                }

                abstractNodeEntitySaver.setAbstractNodeEntity(end);
            }

            currModelNode = currModelNode.getNext();

        }

        generatePlanService.SavePlanAsGraph(head);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }





}
