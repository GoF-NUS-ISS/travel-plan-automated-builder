package com.gof.springcloud.automatedbuilder.Infrastructure.Configuration;

import com.gof.springcloud.automatedbuilder.AutomatedBuilderApplication;
import com.gof.springcloud.automatedbuilder.Domain.Repository.IOrderRepository;
import com.gof.springcloud.automatedbuilder.Domain.Service.QueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AutomatedBuilderApplication.class)
public class BeanConfiguration {

    @Bean
    QueryService queryService(final IOrderRepository orderRepository){
        return new QueryService(orderRepository);
    }
}
