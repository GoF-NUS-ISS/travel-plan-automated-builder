package com.gof.springcloud.automatedbuilder.Domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class ExistingPlan {
    @ApiModelProperty(value = "days")
    private List<TravelPlanModel_Day> days;
}

class TravelPlanModel_Day {
    @ApiModelProperty(value = "current date")
    private Date date;

    @ApiModelProperty(value = "nodes")
    private List<TravelPlanModel_DayNode> nodes;
}

abstract class TravelPlanModel_DayNode {

//    @ApiModelProperty(value = "type")
//    private String type;

}

class TravelPlanModel_Leg extends TravelPlanModel_DayNode {

    @ApiModelProperty(value = "depart from")
    private String from;

    @ApiModelProperty(value = "arrive at")
    private String to;

    @ApiModelProperty(value = "start on")
    public Date startOn;

    @ApiModelProperty(value = "return date")
    public Date returnDate;

    @ApiModelProperty(value = "transport mode")
    public String transportMode;

    @ApiModelProperty(value = "cost")
    public Double cost;
}

class TravelPlanModel_Activity extends TravelPlanModel_DayNode {

    @ApiModelProperty(value = "activity category")
    public String category;

    @ApiModelProperty(value = "cost")
    public Double cost;

    @ApiModelProperty(value="rating")
    public int rating;

    @ApiModelProperty(value="review")
    public String review;

    @ApiModelProperty(value="location")
    public String location;

    @ApiModelProperty(value = "time start")
    public Date timeStart;

    @ApiModelProperty(value = "time end")
    public Date timeEnd;
}
