package com.gof.springcloud.automatedbuilder.Application.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "activity", parent = TravelPlanModel_DayNode.class)
public class TravelPlanModel_Activity extends TravelPlanModel_DayNode {

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
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime timeStart;

    @ApiModelProperty(value = "time end")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime timeEnd;
}
