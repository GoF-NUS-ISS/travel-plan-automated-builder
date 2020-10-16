package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractCostNode;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Location;
import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "IS_LOCATED")
public class IsLocatedCost extends AbstractCostNode {
    @StartNode
    @JsonIgnoreProperties({"hasActivityCost", "travelCost"})
    private Location location;

    @EndNode
    @JsonIgnoreProperties({"isNextToCost", "isLocatedCost"})
    private Activity activity;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}