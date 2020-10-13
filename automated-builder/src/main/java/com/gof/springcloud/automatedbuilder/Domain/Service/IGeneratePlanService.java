package com.gof.springcloud.automatedbuilder.Domain.Service;

import com.gof.springcloud.automatedbuilder.Application.Request.QueryBody;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;

import java.util.List;

public interface IGeneratePlanService {

    void SavePlanAsGraph(AbstractNodeEntity entity);

    List<AbstractNodeEntity> GeneratePlan(QueryBody body);

    void triggerAllPairs();
}
