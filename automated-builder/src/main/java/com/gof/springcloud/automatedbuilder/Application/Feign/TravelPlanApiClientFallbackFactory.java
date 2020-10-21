package com.gof.springcloud.automatedbuilder.Application.Feign;

import com.gof.springcloud.automatedbuilder.Application.Request.TravelPlanModel;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TravelPlanApiClientFallbackFactory implements FallbackFactory<TravelPlanApiClient> {

    @Override
    public TravelPlanApiClient create(Throwable cause) {
        return new TravelPlanApiClient() {
            @Override
            public String addPlanBuilder(TravelPlanModel travelPlan) {
                log.error("Feign error: {}", cause.getMessage());
                return "FEIGNERROR";
            }
        };
    }
}
