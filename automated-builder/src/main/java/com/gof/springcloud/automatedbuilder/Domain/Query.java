package com.gof.springcloud.automatedbuilder.Domain;

import io.swagger.annotations.ApiModelProperty;

public class Query {

    @ApiModelProperty(value = "transport mode")
    public String transportMode;

    @ApiModelProperty(value = "cost")
    public Double cost;

    @ApiModelProperty(value="rating")
    public int rating;

    @ApiModelProperty(value="review")
    public String review;
}
