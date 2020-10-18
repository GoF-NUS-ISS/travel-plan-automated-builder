package com.gof.springcloud.automatedbuilder.Application.Pulsar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gof.springcloud.automatedbuilder.Application.Mapper.ModelToGraphMapper;
import com.gof.springcloud.automatedbuilder.Domain.Service.IGeneratePlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.majusko.pulsar.annotation.PulsarConsumer;

@Service
@Slf4j
public class Consumer {

    private final IGeneratePlanService generatePlanService;

    @Autowired
    public Consumer(IGeneratePlanService generatePlanService){
        this.generatePlanService = generatePlanService;
    }

    @PulsarConsumer(topic = Topics.PLAN_EVENT_JSON, clazz = String.class)
    public void planJsonConsume(String message) {
        log.info("Automated Builder - Pulsar planJsonConsume receive message: {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            TravelPlanEvent event = objectMapper.readValue(message, TravelPlanEvent.class);
            if (event.getOptType().equals("SAVE")) {
                log.info(event.getModel().toString());
                Neo4jEntity entity = ModelToGraphMapper.convert(event.getModel());
                generatePlanService.SavePlanAsGraph(entity.getEntity(), entity.getLinkedList());
            }
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: {}", e.getMessage());
        }

    }
}
