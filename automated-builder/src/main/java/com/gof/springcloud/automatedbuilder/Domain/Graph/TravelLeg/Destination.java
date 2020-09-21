package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Destination extends Location {

    private String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Relationship(type = "NEIGHBOUR_OF")
    private Origin neighbour;

    public Origin getNeighbour() {
        return neighbour;
    }

    public void setNeighbour(Origin neighbour) {
        this.neighbour = neighbour;
    }

    public TravelCost getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(TravelCost travelCost) {
        this.travelCost = travelCost;
    }

    @Relationship(type = "TRAVELS_TO", direction = "INCOMING")
    private TravelCost travelCost;

}
