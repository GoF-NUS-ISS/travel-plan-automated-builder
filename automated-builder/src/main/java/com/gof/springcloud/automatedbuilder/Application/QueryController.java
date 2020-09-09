package com.gof.springcloud.automatedbuilder.Application;

import com.gof.springcloud.automatedbuilder.Domain.Service.IQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class QueryController {

    private final IQueryService queryService;

    @Autowired
    public QueryController (IQueryService queryService){
        this.queryService = queryService;
    }

}
