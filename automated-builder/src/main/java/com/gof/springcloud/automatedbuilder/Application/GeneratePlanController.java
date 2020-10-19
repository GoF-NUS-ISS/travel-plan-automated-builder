package com.gof.springcloud.automatedbuilder.Application;

import com.gof.springcloud.automatedbuilder.Application.Feign.TravelPlanApiClient;
import com.gof.springcloud.automatedbuilder.Application.Mapper.GraphToModelMapper;
import com.gof.springcloud.automatedbuilder.Application.Mapper.Setting;
import com.gof.springcloud.automatedbuilder.Application.Request.*;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Service.IGeneratePlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Automated Plan Builder API")
@RestController
@RequestMapping("/")
@Slf4j
public class GeneratePlanController {
    private final TravelPlanApiClient travelPlanApiClient;

    private final IGeneratePlanService generatePlanService;

    private final Setting setting;
    @Autowired
    public GeneratePlanController(IGeneratePlanService generatePlanService, Setting setting, TravelPlanApiClient travelPlanApiClient){
        this.generatePlanService = generatePlanService;
        this.setting = setting;
        this.travelPlanApiClient = travelPlanApiClient;
    }

    @PostMapping("/generatePlan")
    @ApiOperation(value = "Generate Plan")
    public ResponseEntity generatePlan(@RequestBody QueryBody queryBody){
        if(!queryBody.validate()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Querybody not correct");
        }
        log.info("querybody: {}",queryBody);
        List<AbstractNodeEntity> entityList = generatePlanService.GeneratePlan(queryBody);
        log.info("Convert graph to model");
        TravelPlanModel travelPlanModel = GraphToModelMapper.convert(entityList, setting);
        TravelPlanModel newModel = travelPlanApiClient.addPlan(travelPlanModel);
        log.info("Return model: {}", newModel);
        return ResponseEntity.status(HttpStatus.OK).body(newModel.getId());
    }
}
