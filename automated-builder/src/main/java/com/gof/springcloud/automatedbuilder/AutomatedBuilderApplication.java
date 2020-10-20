package com.gof.springcloud.automatedbuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient //Register into eureka after initiation
@EnableDiscoveryClient
//@EnableCircuitBreaker //import hystrix for fusing mechanism
@EnableCaching
public class AutomatedBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomatedBuilderApplication.class, args);
	}


}
