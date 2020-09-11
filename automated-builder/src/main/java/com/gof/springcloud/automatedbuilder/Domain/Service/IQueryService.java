package com.gof.springcloud.automatedbuilder.Domain.Service;

import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Leg;

import java.util.List;

public interface IQueryService {

    void SaveAll(List<Leg> travelLegList, List<Activity> activityList);
}
