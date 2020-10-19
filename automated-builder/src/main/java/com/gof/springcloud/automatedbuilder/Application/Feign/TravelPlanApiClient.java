package com.gof.springcloud.automatedbuilder.Application.Feign;

import com.gof.springcloud.automatedbuilder.Application.Request.TravelPlanModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "travel-plan-api", url = "${feign.client.config.default.url}")
public interface TravelPlanApiClient {

    @PostMapping("/travelPlanBuilder")
    String addPlanBuilder(@RequestBody TravelPlanModel travelPlan);
}
