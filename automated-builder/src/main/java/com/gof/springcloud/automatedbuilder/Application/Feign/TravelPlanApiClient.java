package com.gof.springcloud.automatedbuilder.Application.Feign;

import com.gof.springcloud.automatedbuilder.Application.Request.TravelPlanModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "${feign.client.config.default.value}", url = "${feign.client.config.default.url}", fallbackFactory = TravelPlanApiClientFallbackFactory.class)
public interface TravelPlanApiClient {

    @PostMapping("/travelPlanBuilder")
    String addPlanBuilder(@RequestBody TravelPlanModel travelPlan);
}
