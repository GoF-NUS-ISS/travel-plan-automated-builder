package com.gof.springcloud.automatedbuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class AutomatedBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomatedBuilderApplication.class, args);
	}

}
