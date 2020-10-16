package com.gof.springcloud.automatedbuilder.Domain.Repository;

import com.gof.springcloud.automatedbuilder.Application.Request.QueryBody;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntityLinkedList;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;

import java.util.List;

public interface IGeneratePlanRepository {
    void Save(AbstractNodeEntity entity, AbstractNodeEntityLinkedList linkedList);

    List<AbstractNodeEntity> GeneratePlan(QueryBody body);
}
