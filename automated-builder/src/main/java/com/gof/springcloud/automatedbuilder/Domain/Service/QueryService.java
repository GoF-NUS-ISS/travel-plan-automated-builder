package com.gof.springcloud.automatedbuilder.Domain.Service;

import com.gof.springcloud.automatedbuilder.Domain.Repository.IOrderRepository;

public class QueryService implements IQueryService {

    private final IOrderRepository orderRepository;

    public QueryService (final IOrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }


}
