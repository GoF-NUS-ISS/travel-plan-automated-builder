package com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg;

import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Location;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Leg {

    public Leg(){
        this.destination = new Destination();
        this.cost = new Cost();
        this.duration = new Duration();
        this.transportMode = new TransportMode();
        this.origin = new Origin();
    }

    @Id
    @GeneratedValue
    private Long Id;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Relationship("TIME_SPENT")
    private Duration duration;

    @Relationship("AMOUNT_SPENT")
    private Cost cost;

    @Relationship("TRAVEL_BY")
    private TransportMode transportMode;

    @Relationship("COMING_FROM")
    private Origin origin;

    @Relationship("GOING_TO")
    private Destination destination;

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public TransportMode getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(TransportMode transportMode) {
        this.transportMode = transportMode;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
