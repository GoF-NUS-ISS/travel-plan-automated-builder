package com.gof.springcloud.automatedbuilder.Infrastructure.Repository;

import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Leg;
import com.gof.springcloud.automatedbuilder.Domain.Repository.ITravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Primary
@Transactional
public class Neo4jDbTravelRepository implements ITravelRepository {

    private final SpringDataNeo4jActivityRepository activityRepository;
    private final SpringDataNeo4jLegRepository legRepository;

    @Autowired
    public Neo4jDbTravelRepository(final SpringDataNeo4jActivityRepository springDataNeo4JActivityRepository, final SpringDataNeo4jLegRepository springDataNeo4jLegRepository){
        this.activityRepository = springDataNeo4JActivityRepository;
        this.legRepository = springDataNeo4jLegRepository;
    }

    @Override
    public void SaveAllActivity(List<Activity> activityList){
        activityRepository.saveAll(activityList);
    }

    @Override
    public void SaveAllLeg(List<Leg> legList){
        legRepository.saveAll(legList);
    }

}
