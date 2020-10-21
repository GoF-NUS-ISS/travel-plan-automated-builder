package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;
import java.util.Objects;

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
                ", location='" + location + '\'' +
                '}';
    }

    private String location;

    private double cost;

    private String category;

    private long seconds;

    private int stars;

    private String description;

    @Relationship(type = "IS_LOCATED")
    @JsonIgnoreProperties("activity")
    private List<IsLocatedCost> isLocatedCost;

    @Relationship(type = "IS_NEXT_TO")
    @JsonIgnoreProperties({"activity", "activity1"})
    private List<IsNextToCost> isNextToCost;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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

    public List<IsLocatedCost> getIsLocatedCost() {
        return isLocatedCost;
    }

    public void setIsLocatedCost(List<IsLocatedCost> isLocatedCost) {
        this.isLocatedCost = isLocatedCost;
    }

    public List<IsNextToCost> getIsNextToCost() {
        return isNextToCost;
    }

    public void setIsNextToCost(List<IsNextToCost> isNextToCost) {
        this.isNextToCost = isNextToCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(category, activity.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }
}

