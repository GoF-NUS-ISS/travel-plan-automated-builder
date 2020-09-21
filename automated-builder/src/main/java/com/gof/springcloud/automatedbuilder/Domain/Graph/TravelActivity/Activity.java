package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Location;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Activity extends AbstractNodeEntity {

    public Activity(){
        this.location = new Location();
        this.category = new Category();
        this.cost = new Cost();
        this.duration = new Duration();
        this.rating = new Rating();
        this.review = new Review();
    }

    @Relationship(type = "IS_LOCATED_IN")
    private Location location;

    @Relationship(type = "CATEGORY")
    private Category category;

    @Relationship(type = "AMOUNT_SPENT")
    private Cost cost;

    @Relationship(type = "TIME_SPENT")
    private Duration duration;

    @Relationship(type = "IS_RATED")
    private Rating rating;

    @Relationship(type = "HAS_REVIEW")
    private Review review;

    @Relationship(type = "IS_NEXT_TO")
    private Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

}

