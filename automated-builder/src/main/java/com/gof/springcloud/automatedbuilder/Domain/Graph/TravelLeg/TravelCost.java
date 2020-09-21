package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "TRAVELS_TO")
public class TravelCost extends AbstractNodeEntity {

    @StartNode
    private Origin startFrom;

    @EndNode
    private Destination stopAt;

    private long seconds;

    private double cost;

    private String transportMode;

    public Location getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(Origin startFrom) {
        this.startFrom = startFrom;
    }

    public Location getStopAt() {
        return stopAt;
    }

    public void setStopAt(Destination stopAt) {
        this.stopAt = stopAt;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }



}
