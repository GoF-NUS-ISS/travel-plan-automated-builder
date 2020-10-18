package com.gof.springcloud.automatedbuilder.Application;

import com.gof.springcloud.automatedbuilder.Application.Mapper.GraphToModelMapper;
import com.gof.springcloud.automatedbuilder.Application.Mapper.Setting;
import com.gof.springcloud.automatedbuilder.Application.Request.*;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Service.IGeneratePlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Automated Plan Builder API")
@RestController
@RequestMapping("/")
@Slf4j
public class GeneratePlanController {

//    @Value("${spring.application.setting.dayduration}")
//    private int dayDuration;
//
//    @Value("${spring.application.setting.hrspastmidnight}")
//    private int hrsPastMidnight;
//
//    @Value("${spring.application.setting.timezone}")
//    private String timeZone;
//
//    @Value("${spring.application.setting.dateformat}")
//    private String dateFormat;

    private final IGeneratePlanService generatePlanService;

    private final Setting setting;
    @Autowired
    public GeneratePlanController(IGeneratePlanService generatePlanService, Setting setting){
        this.generatePlanService = generatePlanService;
        this.setting = setting;
    }

    @PostMapping("/generatePlan")
    @ApiOperation(value = "Generate Plan")
    public ResponseEntity generatePlan(@RequestBody QueryBody queryBody){
        if(!queryBody.validate()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Querybody not correct");
        }
        log.info("querybody: {}",queryBody);
        List<AbstractNodeEntity> entityList = generatePlanService.GeneratePlan(queryBody);
        log.info("Convert graph to model");
        TravelPlanModel travelPlanModel = GraphToModelMapper.convert(entityList, setting);
        log.info("Return model: {}", travelPlanModel);
        return ResponseEntity.status(HttpStatus.OK).body(travelPlanModel);
    }

//    private TravelPlanModel convertToModel(List<AbstractNodeEntity> entityList) {
//        TravelPlanModel travelPlanModel = new TravelPlanModel();
//
//        ZoneId zoneId = ZoneId.of(timeZone);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
//        LocalDateTime currDateTime = LocalDateTime.now(zoneId);
//        if(entityList.isEmpty()){
//            log.info("entityList empty");
//            travelPlanModel.setId("Cannot satisfy query");
//        }
//        else{
//            String nameTitle = "Suggested plan from travel plan platform. Created at " + currDateTime.format(formatter);
//            log.info("nameTitle :{}", nameTitle);
//            travelPlanModel.setName(nameTitle);
//            travelPlanModel.setTitle(nameTitle);
//        }
//
//        LocalDateTime start = LocalDateTime.of(currDateTime.toLocalDate(), currDateTime.toLocalTime()).toLocalDate().atStartOfDay().plusHours(hrsPastMidnight);
//        LocalDateTime end = LocalDateTime.of(start.toLocalDate(), start.toLocalTime());
//        LocalDateTime endOfDay = start.plusHours(dayDuration);
//
//        TravelPlanModel_Day day = new TravelPlanModel_Day();
//        day.setDate(start);
//        log.info("day starts at: {}", start);
//        log.info("day ends at: {}", endOfDay);
//
//        for (AbstractNodeEntity entity: entityList){
//            long duration=0L;
//            TravelPlanModel_DayNode node = null;
//            if(entity instanceof Activity){
//                Activity activity = (Activity) entity;
//                TravelPlanModel_Activity modelActivity = new TravelPlanModel_Activity();
//                modelActivity.setCost(activity.getCost());
//                modelActivity.setCategory(activity.getCategory());
//                modelActivity.setLocation(activity.getLocation());
//                modelActivity.setRating(activity.getStars());
//                modelActivity.setReview(activity.getDescription());
//                duration = activity.getSeconds();
//                node = modelActivity;
//            }
//
//            if(entity instanceof TravelCost){
//                TravelCost cost = (TravelCost) entity;
//                TravelPlanModel_Leg modelLeg = new TravelPlanModel_Leg();
//                modelLeg.setCost(cost.getCost());
//                modelLeg.setTransportMode(cost.getTransportMode());
//                modelLeg.setFrom(cost.getStartLoc().getLocation());
//                modelLeg.setTo(cost.getEndLoc().getLocation());
//                duration = cost.getSeconds();
//                node = modelLeg;
//            }
//
//            end = start.plusSeconds(duration);
//            if(!end.isBefore(endOfDay)){
//                travelPlanModel.getDays().add(day);
//                endOfDay = endOfDay.plusDays(1);
//                start = endOfDay.toLocalDate().atStartOfDay().plusHours(hrsPastMidnight);
//                day = new TravelPlanModel_Day();
//                log.info("new day");
//                log.info("day starts at: {}", start);
//                log.info("day ends at: {}", endOfDay);
//                day.setDate(start);
//
//                end = start.plusSeconds(duration);
//            }
//
//            if(node instanceof TravelPlanModel_Leg){
//                ((TravelPlanModel_Leg) node).setStartOn(start);
//                ((TravelPlanModel_Leg) node).setStopAt(end);
//            }
//            if(node instanceof TravelPlanModel_Activity){
//                ((TravelPlanModel_Activity) node).setTimeStart(start);
//                ((TravelPlanModel_Activity) node).setTimeEnd(end);
//            }
//            day.getNodes().add(node);
//            start = LocalDateTime.of(end.toLocalDate(), end.toLocalTime());
//
//        }
//        travelPlanModel.getDays().add(day);
//        log.info("ended at: {}", end);
//
//        return travelPlanModel;
//    }

//    @PostMapping("/addPlanAsGraph")
//    @ApiOperation(value = "add existing plan as graph")
//    public ResponseEntity addPlanAsGraph(@RequestBody TravelPlanModel travelPlanModel){
//        log.info("travelPlanModel: {}", travelPlanModel);
//        TravelNode_LinkedList headModelNode = new TravelNode_LinkedList();
//        TravelNode_LinkedList currModelNode  = headModelNode;
//
//        for(TravelPlanModel_Day day: travelPlanModel.getDays()){
//            for(TravelPlanModel_DayNode node: day.getNodes()){
//                currModelNode.setRequestModelNode(node);
//                TravelNode_LinkedList next = new TravelNode_LinkedList();
//                currModelNode.setNext(next);
//                currModelNode = next;
//            }
//            currModelNode.setNext(null);
//        }
//
//        currModelNode = headModelNode;
//
//        AbstractNodeEntity head = new Activity();
//        AbstractNodeEntityLinkedList nodeEntityLinkedList = new AbstractNodeEntityLinkedList();
//
//        while(currModelNode.getNext() != null){
//            if(currModelNode.getRequestModelNode() instanceof TravelPlanModel_Activity){
//                TravelPlanModel_Activity activityModelCurr = (TravelPlanModel_Activity) currModelNode.getRequestModelNode();
//                Activity activityNode = new Activity();
//
//                activityNode.setCategory(activityModelCurr.getCategory());
//                activityNode.setCost(activityModelCurr.getCost());
//                activityNode.setStars(activityModelCurr.getRating());
//                activityNode.setDescription(activityModelCurr.getReview());
//                activityNode.setSeconds(ChronoUnit.SECONDS.between(activityModelCurr.timeStart.toLocalTime(), activityModelCurr.timeEnd.toLocalTime()));
//
//                if(nodeEntityLinkedList.getPrev() != null && nodeEntityLinkedList.getPrev().getAbstractNodeEntity() != null){
//                    AbstractNodeEntity prevEntity = nodeEntityLinkedList.getPrev().getAbstractNodeEntity();
//                    if(prevEntity instanceof Activity){
//                        IsNextToCost isNextToCost = new IsNextToCost();
//                        List<IsNextToCost> isNextToCostList = new ArrayList<>();
//                        isNextToCost.setActivity((Activity) prevEntity);
//                        isNextToCost.setActivity1(activityNode);
//                        isNextToCostList.add(isNextToCost);
//                        ((Activity) prevEntity).setIsNextToCost(isNextToCostList);
//                    }
//                    if(prevEntity instanceof Location){
//                        List<HasActivityCost> activityCostList = new ArrayList<>();
//                        HasActivityCost hasActivityCost = new HasActivityCost();
//                        List<IsLocatedCost> isLocatedCostList = new ArrayList<>();
//                        IsLocatedCost isLocatedCost = new IsLocatedCost();
//
//                        hasActivityCost.setCost(activityNode.getCost());
//                        isLocatedCost.setCost(activityNode.getCost());
//
//                        hasActivityCost.setActivity(activityNode);
//                        hasActivityCost.setLocation((Location) prevEntity);
//
//                        isLocatedCost.setActivity(activityNode);
//                        isLocatedCost.setLocation((Location) prevEntity);
//
//                        activityCostList.add(hasActivityCost);
//                        ((Location) prevEntity).setHasActivityCost(activityCostList);
//                        isLocatedCostList.add(isLocatedCost);
//                        activityNode.setIsLocatedCost(isLocatedCostList);
//                    }
//                }
//                else{
//                    head = activityNode;
//                }
//
//                nodeEntityLinkedList = getNextNode(activityNode, nodeEntityLinkedList);
//            }
//
//            if(currModelNode.getRequestModelNode() instanceof TravelPlanModel_Leg){
//                Location start = new Location();
//                Location end = new Location();
//                TravelCost travelCost = new TravelCost();
//                List<TravelCost> travelCostList = new ArrayList<>();
//
//                TravelPlanModel_Leg leg = (TravelPlanModel_Leg) currModelNode.getRequestModelNode();
//                travelCost.setCost(leg.getCost());
//                travelCost.setTransportMode(leg.getTransportMode());
//                travelCost.setSeconds(ChronoUnit.SECONDS.between(leg.getStartOn().toLocalTime(), leg.getStopAt().toLocalTime()));
//
//                start.setLocation(leg.getFrom());
//                end.setLocation(leg.getTo());
//
//                travelCost.setStartLoc(start);
//                travelCost.setEndLoc(end);
//                travelCostList.add(travelCost);
//                start.setTravelCost(travelCostList);
//
//                if(nodeEntityLinkedList.getPrev() != null && nodeEntityLinkedList.getPrev().getAbstractNodeEntity() != null){
//                    AbstractNodeEntity prevEntity = nodeEntityLinkedList.getPrev().getAbstractNodeEntity();
//                    if(prevEntity instanceof Activity){
//                        HasActivityCost hasActivityCost = new HasActivityCost();
//                        List<HasActivityCost> hasActivityCostList = new ArrayList<>();
//                        IsLocatedCost isLocatedCost = new IsLocatedCost();
//                        List<IsLocatedCost> isLocatedCostList = new ArrayList<>();
//
//                        hasActivityCost.setCost(((Activity) prevEntity).getCost());
//                        isLocatedCost.setCost(((Activity) prevEntity).getCost());
//
//                        hasActivityCost.setActivity((Activity) prevEntity);
//                        hasActivityCost.setLocation(start);
//
//                        isLocatedCost.setActivity((Activity) prevEntity);
//                        isLocatedCost.setLocation(start);
//
//                        isLocatedCostList.add(isLocatedCost);
//                        ((Activity) prevEntity).setIsLocatedCost(isLocatedCostList);
//                        hasActivityCostList.add(hasActivityCost);
//                        start.setHasActivityCost(hasActivityCostList);
//                    }
//                    if(prevEntity instanceof Location){
//                        TravelCost bridgeLocationCost = new TravelCost();
//                        List<TravelCost> bridgeLocationCostList = new ArrayList<>();
//                        bridgeLocationCost.setStartLoc((Location) prevEntity);
//                        bridgeLocationCost.setEndLoc(start);
//                        bridgeLocationCostList.add(bridgeLocationCost);
//                        ((Location) prevEntity).setTravelCost(bridgeLocationCostList);
//                    }
//                }
//                else{
//                    head = start;
//                }
//
//                //for start location
//                nodeEntityLinkedList = getNextNode(start, nodeEntityLinkedList);
//                //for end location
//                nodeEntityLinkedList = getNextNode(end, nodeEntityLinkedList);
//            }
//
//            currModelNode = currModelNode.getNext();
//
//        }

//        generatePlanService.SavePlanAsGraph(head, nodeEntityLinkedList);
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

//    private AbstractNodeEntityLinkedList getNextNode(AbstractNodeEntity currEntity, AbstractNodeEntityLinkedList currNodeEntityLinkedList) {
//        AbstractNodeEntityLinkedList next = new AbstractNodeEntityLinkedList();
//
//        currNodeEntityLinkedList.setAbstractNodeEntity(currEntity);
//        next.setPrev(currNodeEntityLinkedList);
//        currNodeEntityLinkedList.setNext(next);
//
//        return next;
//    }


}
