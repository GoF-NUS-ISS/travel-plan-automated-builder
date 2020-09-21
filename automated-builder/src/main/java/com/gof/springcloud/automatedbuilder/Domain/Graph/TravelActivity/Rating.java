package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Rating extends AbstractNodeEntity {

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    private int stars;
}
