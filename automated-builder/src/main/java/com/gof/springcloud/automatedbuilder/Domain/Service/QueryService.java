package com.gof.springcloud.automatedbuilder.Domain.Service;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Repository.ITravelRepository;

public class QueryService implements IQueryService {

    private final ITravelRepository repository;

    public QueryService (final ITravelRepository repository){
        this.repository = repository;
    }

    @Override
    public void Save(AbstractNodeEntity entity){
        repository.Save(entity);
    }
}
