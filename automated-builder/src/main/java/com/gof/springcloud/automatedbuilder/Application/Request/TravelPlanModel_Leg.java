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
@ApiModel(value = "leg", parent = TravelPlanModel_DayNode.class)
public class TravelPlanModel_Leg extends TravelPlanModel_DayNode {

    @ApiModelProperty(value = "depart from")
    private String from;

    @ApiModelProperty(value = "arrive at")
    private String to;

    @ApiModelProperty(value = "start on")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public Date startOn;

    @ApiModelProperty(value = "stop at")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public LocalDateTime stopAt;

    @ApiModelProperty(value = "transport mode")
    public String transportMode;

    @ApiModelProperty(value = "cost")
    public Double cost;
}

