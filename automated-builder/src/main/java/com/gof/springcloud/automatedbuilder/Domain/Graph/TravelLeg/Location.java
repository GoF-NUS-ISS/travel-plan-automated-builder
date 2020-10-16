package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.HasActivityCost;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Location extends AbstractNodeEntity {
    private String location;

    @Relationship(type = "HAS_ACTIVITY")
    @JsonIgnoreProperties("location")
    private List<HasActivityCost> hasActivityCost;

    @Relationship(type = "TRAVELS_TO")
    @JsonIgnoreProperties({"startLoc", "endLoc"})
    private List<TravelCost> travelCost;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<HasActivityCost> getHasActivityCost() {
        return hasActivityCost;
    }

    public void setHasActivityCost(List<HasActivityCost> hasActivityCost) {
        this.hasActivityCost = hasActivityCost;
    }

    public List<TravelCost> getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(List<TravelCost> travelCost) {
        this.travelCost = travelCost;
    }

    @Override
    public String toString() {
        return "Location{" +
                "ID='" + this.getId() + '\'' +
                "location='" + location + '\'' +
                ", hasActivityCost=" + hasActivityCost +
                ", travelCost=" + travelCost +
                '}';
    }
}
