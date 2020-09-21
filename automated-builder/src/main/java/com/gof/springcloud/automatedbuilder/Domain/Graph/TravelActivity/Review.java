package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Review extends AbstractNodeEntity {

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
}
