package com.gof.springcloud.automatedbuilder.Domain.Graph;

import org.neo4j.ogm.annotation.GeneratedValue;

public abstract class AbstractNodeEntity {

    @org.neo4j.ogm.annotation.Id
    @GeneratedValue
    private Long Id;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
