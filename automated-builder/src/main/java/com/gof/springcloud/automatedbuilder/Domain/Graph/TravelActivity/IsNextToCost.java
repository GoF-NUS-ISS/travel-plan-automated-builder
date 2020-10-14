package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractCostNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "IS_NEXT_TO")
public class IsNextToCost extends AbstractCostNode {

    @StartNode
    @JsonIgnoreProperties({"isNextToCost", "isLocatedCost"})
    private Activity activity;

    @EndNode
    @JsonIgnoreProperties({"isNextToCost", "isLocatedCost"})
    private Activity activity1;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity1() {
        return activity1;
    }

    public void setActivity1(Activity activity1) {
        this.activity1 = activity1;
    }
}
