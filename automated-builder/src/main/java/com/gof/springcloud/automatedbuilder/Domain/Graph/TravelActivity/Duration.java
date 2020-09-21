package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Duration extends AbstractNodeEntity {

    private long seconds;

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }
}
