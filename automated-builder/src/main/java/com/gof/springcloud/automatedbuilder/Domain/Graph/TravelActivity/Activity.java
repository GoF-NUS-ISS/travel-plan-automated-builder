package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Activity extends AbstractNodeEntity {
    @Override
    public String toString() {
        return "Activity{" +
                "ID='" + this.getId() + '\'' +
                "cost=" + cost +
                ", category='" + category + '\'' +
                ", seconds=" + seconds +
                ", stars=" + stars +
                ", description='" + description + '\'' +
                '}';
    }

    private double cost;

    private String category;

    private long seconds;

    private int stars;

    private String description;

    @Relationship(type = "IS_LOCATED")
    @JsonIgnoreProperties("activity")
    private IsLocatedCost isLocatedCost;

    @Relationship(type = "IS_NEXT_TO")
    @JsonIgnoreProperties({"activity", "activity1"})
    private IsNextToCost isNextToCost;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IsLocatedCost getIsLocatedCost() {
        return isLocatedCost;
    }

    public void setIsLocatedCost(IsLocatedCost isLocatedCost) {
        this.isLocatedCost = isLocatedCost;
    }

    public IsNextToCost getIsNextToCost() {
        return isNextToCost;
    }

    public void setIsNextToCost(IsNextToCost isNextToCost) {
        this.isNextToCost = isNextToCost;
    }
}

