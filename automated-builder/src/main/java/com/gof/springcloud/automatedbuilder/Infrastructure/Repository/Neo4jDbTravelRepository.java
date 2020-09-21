package com.gof.springcloud.automatedbuilder.Infrastructure.Repository;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Repository.ITravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Primary
@Transactional
public class Neo4jDbTravelRepository implements ITravelRepository {

    private final SpringDataNeo4jRepository repository;

    @Autowired
    public Neo4jDbTravelRepository(final SpringDataNeo4jRepository springDataNeo4JActivityRepository){
        this.repository = springDataNeo4JActivityRepository;
    }

    @Override
    public void Save(AbstractNodeEntity entity){
        repository.save(entity);
    }

}
