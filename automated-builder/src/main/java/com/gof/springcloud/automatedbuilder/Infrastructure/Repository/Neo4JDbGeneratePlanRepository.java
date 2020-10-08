package com.gof.springcloud.automatedbuilder.Infrastructure.Repository;

import com.gof.springcloud.automatedbuilder.Application.Request.QueryBody;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Repository.IGeneratePlanRepository;
import com.gof.springcloud.automatedbuilder.Infrastructure.Model.Triple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Primary
@Transactional
@Slf4j
public class Neo4JDbGeneratePlanRepository implements IGeneratePlanRepository {

    private final SpringDataNeo4jRepository repository;
    private final SpringDataNeo4jActivityRepository activityRepository;

    @Autowired
    public Neo4JDbGeneratePlanRepository(final SpringDataNeo4jRepository springDataNeo4JActivityRepository, final SpringDataNeo4jActivityRepository activityRepository){
        this.repository = springDataNeo4JActivityRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public void Save(AbstractNodeEntity entity){
        repository.save(entity);
    }

    @Override
    public List<Activity> SavePlanAsGraph(QueryBody body) {


        return activityRepository.getActivitiesByRank(body.getCategory(), body.getDescription(), body.getStars());
    }

    @Override
    public void triggerAllPairs() {
        log.info("all pairs:{}", convert(activityRepository.getAllPairs()));
    }

    private List<Triple> convert(Iterable<Map<String, Object>> iterable){
        List<Triple> allPairs = new ArrayList<>();
        iterable.forEach(x ->{
            allPairs.add(new Triple((long) x.get("sourceNodeId"), (long) x.get("targetNodeId"), (double) x.get("distance")));
        });
        return allPairs;
    }
}
