package com.gof.springcloud.automatedbuilder.Application;

import com.gof.springcloud.automatedbuilder.Application.Request.*;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Leg;
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

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

        List<Leg> legList = new ArrayList<>();
        List<Activity> activityList = new ArrayList<>();

        for(TravelPlanModel_Day day: travelPlanModel.getDays()){
            for(TravelPlanModel_DayNode node: day.getNodes()){
                if(node instanceof TravelPlanModel_Activity){
                    activityList.add(convertActivityModelToGraph((TravelPlanModel_Activity)node));
                }
                else{
                    legList.add(convertLegModelToGraph((TravelPlanModel_Leg)node));
                }
            }
        }

        queryService.SaveAll(legList, activityList);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private Activity convertActivityModelToGraph(TravelPlanModel_Activity activity) {
        Activity activityNode = new Activity();
        activityNode.getCategory().setActivityCategory(activity.getCategory());
        activityNode.getCost().setCost(activity.getCost());
        activityNode.getLocation().setLocation(activity.getLocation());
        activityNode.getRating().setStars(activity.getRating());
        activityNode.getReview().setDescription(activity.getReview());
        activityNode.getDuration().setSeconds(ChronoUnit.SECONDS.between(activity.timeStart.toLocalTime(), activity.timeEnd.toLocalTime()));

        return activityNode;
    }

    private Leg convertLegModelToGraph(TravelPlanModel_Leg leg) {
        Leg legNode = new Leg();
        legNode.getCost().setCost(leg.getCost());
        legNode.getDestination().setDestination(leg.getFrom());
        legNode.getOrigin().setOrigin(leg.getTo());
        legNode.getTransportMode().setTransportMode(leg.getTransportMode());
        legNode.getDuration().setSeconds(ChronoUnit.SECONDS.between(leg.getStartOn().toLocalTime(), leg.getReturnDate().toLocalTime()));

        return legNode;

    }

}
