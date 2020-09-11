package com.gof.springcloud.automatedbuilder.Application.Request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;


@ApiModel(subTypes = {com.gof.springcloud.automatedbuilder.Application.Request.TravelPlanModel_Activity.class, com.gof.springcloud.automatedbuilder.Application.Request.TravelPlanModel_Leg.class}, discriminator = "type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = com.gof.springcloud.automatedbuilder.Application.Request.TravelPlanModel_Activity.class, name = "activity"),
        @JsonSubTypes.Type(value = com.gof.springcloud.automatedbuilder.Application.Request.TravelPlanModel_Leg.class, name = "leg")})
public abstract class TravelPlanModel_DayNode {

//    @ApiModelProperty(value = "type")
//    private String type;

}
