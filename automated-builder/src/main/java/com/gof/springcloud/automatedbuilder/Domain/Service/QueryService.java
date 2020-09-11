package com.gof.springcloud.automatedbuilder.Domain.Service;

import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Leg;
import com.gof.springcloud.automatedbuilder.Domain.Repository.ITravelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class QueryService implements IQueryService {

    private final ITravelRepository orderRepository;

    public QueryService (final ITravelRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public void SaveAll(List<Leg> travelLegList, List<Activity> activityList){
        orderRepository.SaveAllActivity(activityList);
        orderRepository.SaveAllLeg(travelLegList);
    }
}
