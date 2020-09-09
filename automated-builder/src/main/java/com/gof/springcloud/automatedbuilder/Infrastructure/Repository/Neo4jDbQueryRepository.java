package com.gof.springcloud.automatedbuilder.Infrastructure.Repository;

import com.gof.springcloud.automatedbuilder.Domain.Repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Neo4jDbQueryRepository implements IOrderRepository {

    private final SpringDataNeo4jQueryRepository repository;

    @Autowired
    public Neo4jDbQueryRepository(final SpringDataNeo4jQueryRepository springDataNeo4jQueryRepository){
        this.repository = springDataNeo4jQueryRepository;
    }
}
