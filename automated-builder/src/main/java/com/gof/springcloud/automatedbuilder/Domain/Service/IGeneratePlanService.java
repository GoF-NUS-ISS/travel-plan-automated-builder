package com.gof.springcloud.automatedbuilder.Domain.Service;

import com.gof.springcloud.automatedbuilder.Application.Request.QueryBody;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntityLinkedList;

import java.util.List;

public interface IGeneratePlanService {

    AbstractNodeEntity SavePlanAsGraph(AbstractNodeEntity entity, AbstractNodeEntityLinkedList linkedList);

    List<AbstractNodeEntity> GeneratePlan(QueryBody body);

    void delete();
}
