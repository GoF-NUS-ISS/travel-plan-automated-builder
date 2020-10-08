package com.gof.springcloud.automatedbuilder.Domain.Graph;

import lombok.Data;

@Data
public abstract class AbstractCostNode extends AbstractNodeEntity {
    private long seconds=0L;
    private double cost=0.00;
}
