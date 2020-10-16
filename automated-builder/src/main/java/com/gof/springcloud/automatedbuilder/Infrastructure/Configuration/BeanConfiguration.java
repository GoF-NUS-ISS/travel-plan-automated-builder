package com.gof.springcloud.automatedbuilder.Infrastructure.Configuration;

import com.gof.springcloud.automatedbuilder.AutomatedBuilderApplication;
import com.gof.springcloud.automatedbuilder.Domain.Repository.IGeneratePlanRepository;
import com.gof.springcloud.automatedbuilder.Domain.Service.GeneratePlanService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AutomatedBuilderApplication.class)
public class BeanConfiguration {

    @Bean
    GeneratePlanService queryService(final IGeneratePlanRepository orderRepository){
        return new GeneratePlanService(orderRepository);
    }
}
