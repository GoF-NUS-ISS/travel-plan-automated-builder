package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.HasActivityCost;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Location extends AbstractNodeEntity {
    private String location;

    @Relationship(type = "HAS_ACTIVITY")
    @JsonIgnoreProperties("location")
    private HasActivityCost hasActivityCost;

    @Relationship(type = "TRAVELS_TO")
    @JsonIgnoreProperties({"startLoc", "endLoc"})
    private TravelCost travelCost;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public HasActivityCost getHasActivityCost() {
        return hasActivityCost;
    }

    public void setHasActivityCost(HasActivityCost hasActivityCost) {
        this.hasActivityCost = hasActivityCost;
    }

    public TravelCost getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(TravelCost travelCost) {
        this.travelCost = travelCost;
    }
}
