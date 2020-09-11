package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.time.Period;

@NodeEntity
public class Duration {
    @org.neo4j.ogm.annotation.Id
    @GeneratedValue
    private Long Id;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
    private long seconds;

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

}
