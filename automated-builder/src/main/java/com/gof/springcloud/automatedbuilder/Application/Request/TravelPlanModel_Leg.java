package com.gof.springcloud.automatedbuilder.Application.Request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "leg", parent = TravelPlanModel_DayNode.class)
public class TravelPlanModel_Leg extends TravelPlanModel_DayNode {

    @ApiModelProperty(value = "depart from")
    private String from;

    @ApiModelProperty(value = "arrive at")
    private String to;

    @ApiModelProperty(value = "start on")
    public LocalDateTime startOn;

    @ApiModelProperty(value = "return date")
    public LocalDateTime returnDate;

    @ApiModelProperty(value = "transport mode")
    public String transportMode;

    @ApiModelProperty(value = "cost")
    public Double cost;
}

