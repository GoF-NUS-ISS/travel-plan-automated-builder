package com.gof.springcloud.automatedbuilder.Domain.Service;

import com.gof.springcloud.automatedbuilder.Application.Request.QueryBody;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Repository.IGeneratePlanRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GeneratePlanService implements IGeneratePlanService {

    private final IGeneratePlanRepository repository;

    public GeneratePlanService(final IGeneratePlanRepository repository){
        this.repository = repository;
    }

    @Override
    public void SavePlanAsGraph(AbstractNodeEntity entity){
        log.info("saving plan in service layer");
        repository.Save(entity);
    }

    @Override
    public List<AbstractNodeEntity> GeneratePlan(QueryBody body) {
        List<AbstractNodeEntity> entityList = repository.GeneratePlan(body);
        return entityList;
    }

    @Override
    public void triggerAllPairs() {
        repository.trigger();
    }
}
