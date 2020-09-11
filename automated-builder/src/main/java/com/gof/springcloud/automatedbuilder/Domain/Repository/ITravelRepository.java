package com.gof.springcloud.automatedbuilder.Domain.Repository;

import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Leg;

import java.util.List;

public interface ITravelRepository {

    void SaveAllActivity(List<Activity> activityList);

    void SaveAllLeg(List<Leg> legList);
}
