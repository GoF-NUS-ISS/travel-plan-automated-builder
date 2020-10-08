package com.gof.springcloud.automatedbuilder.Infrastructure.Model;

import lombok.Data;

@Data
public class Triple {

    public Triple(long sourceId, long targetId, double distance){
        this.distance=distance;
        this.sourceId=sourceId;
        this.targetId=targetId;
    }

    private long sourceId;

    private long targetId;

    private double distance;
}
