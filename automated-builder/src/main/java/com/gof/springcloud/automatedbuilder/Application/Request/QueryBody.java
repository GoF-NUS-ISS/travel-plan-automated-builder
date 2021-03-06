package com.gof.springcloud.automatedbuilder.Application.Request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "QueryBody", description = "Model for generating plans")

public class QueryBody {

    @ApiModelProperty(value = "stars")
    private int stars;
    @ApiModelProperty(value = "category")
    private String category;
    @ApiModelProperty(value = "description")
    private String description;
    @ApiModelProperty(value = "startCost")
    private int startCost;
    @ApiModelProperty(value = "endCost")
    private int endCost;
    @ApiModelProperty(value = "name")
    private String name;

    public boolean validate(){
        return this.getStartCost()<=this.getEndCost() &&
                !this.getCategory().isEmpty() &&
                !this.getDescription().isEmpty() &&
                this.getStars() != 0;
    }
}

