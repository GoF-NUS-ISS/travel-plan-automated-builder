package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractCostNode;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "TRAVELS_TO")
public class TravelCost extends AbstractCostNode {

    @StartNode
    @JsonIgnoreProperties("travelCost")
    private Location startLoc;

    @EndNode
    @JsonIgnoreProperties("travelCost")
    private Location endLoc;

    private String transportMode;

    public Location getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(Location startLoc) {
        this.startLoc = startLoc;
    }

    public Location getEndLoc() {
        return endLoc;
    }

    public void setEndLoc(Location endLoc) {
        this.endLoc = endLoc;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }
}
