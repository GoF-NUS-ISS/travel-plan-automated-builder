package com.gof.springcloud.automatedbuilder.Application.Mapper;

import com.gof.springcloud.automatedbuilder.Application.Request.*;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.TravelCost;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class GraphToModelMapper {

    public static TravelPlanModel convert(List<AbstractNodeEntity> entityList, Setting setting){
        TravelPlanModel travelPlanModel = new TravelPlanModel();

        ZoneId zoneId = ZoneId.of(setting.getTimeZone());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(setting.getDateFormat());
        LocalDateTime currDateTime = LocalDateTime.now(zoneId);
        if(entityList.isEmpty()){
            log.info("entityList empty");
            travelPlanModel.setId("Cannot satisfy query");
        }
        else{
            String nameTitle = "Suggested plan from travel plan platform. Created at " + currDateTime.format(formatter);
            log.info("nameTitle :{}", nameTitle);
            travelPlanModel.setName(nameTitle);
            travelPlanModel.setTitle(nameTitle);
        }

        LocalDateTime start = LocalDateTime.of(currDateTime.toLocalDate(), currDateTime.toLocalTime()).toLocalDate().atStartOfDay().plusHours(setting.getHrsPastMidnight());
        LocalDateTime end = LocalDateTime.of(start.toLocalDate(), start.toLocalTime());
        LocalDateTime endOfDay = start.plusHours(setting.getDayDuration());

        TravelPlanModel_Day day = new TravelPlanModel_Day();
        day.setDate(start);
        log.info("day starts at: {}", start);
        log.info("day ends at: {}", endOfDay);

        for (AbstractNodeEntity entity: entityList){
            long duration=0L;
            TravelPlanModel_DayNode node = null;
            if(entity instanceof Activity){
                Activity activity = (Activity) entity;
                TravelPlanModel_Activity modelActivity = new TravelPlanModel_Activity();
                modelActivity.setCost(activity.getCost());
                modelActivity.setCategory(activity.getCategory());
                modelActivity.setLocation(activity.getLocation());
                modelActivity.setRating(activity.getStars());
                modelActivity.setReview(activity.getDescription());
                duration = activity.getSeconds();
                node = modelActivity;
            }

            if(entity instanceof TravelCost){
                TravelCost cost = (TravelCost) entity;
                TravelPlanModel_Leg modelLeg = new TravelPlanModel_Leg();
                modelLeg.setCost(cost.getCost());
                modelLeg.setTransportMode(cost.getTransportMode());
                modelLeg.setFrom(cost.getStartLoc().getLocation());
                modelLeg.setTo(cost.getEndLoc().getLocation());
                duration = cost.getSeconds();
                node = modelLeg;
            }

            end = start.plusSeconds(duration);
            if(!end.isBefore(endOfDay)){
                travelPlanModel.getDays().add(day);
                endOfDay = endOfDay.plusDays(1);
                start = endOfDay.toLocalDate().atStartOfDay().plusHours(setting.getHrsPastMidnight());
                day = new TravelPlanModel_Day();
                log.info("new day");
                log.info("day starts at: {}", start);
                log.info("day ends at: {}", endOfDay);
                day.setDate(start);

                end = start.plusSeconds(duration);
            }

            if(node instanceof TravelPlanModel_Leg){
                ((TravelPlanModel_Leg) node).setStartOn(start);
                ((TravelPlanModel_Leg) node).setStopAt(end);
            }
            if(node instanceof TravelPlanModel_Activity){
                ((TravelPlanModel_Activity) node).setTimeStart(start);
                ((TravelPlanModel_Activity) node).setTimeEnd(end);
            }
            day.getNodes().add(node);
            start = LocalDateTime.of(end.toLocalDate(), end.toLocalTime());

        }
        travelPlanModel.getDays().add(day);
        log.info("ended at: {}", end);

        return travelPlanModel;
    }
}
