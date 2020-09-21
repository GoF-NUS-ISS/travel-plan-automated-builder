package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Origin extends Location {

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    private String origin;

    public TravelCost getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(TravelCost travelCost) {
        this.travelCost = travelCost;
    }

    @Relationship(type = "TRAVELS_TO")
    private TravelCost travelCost;

}
