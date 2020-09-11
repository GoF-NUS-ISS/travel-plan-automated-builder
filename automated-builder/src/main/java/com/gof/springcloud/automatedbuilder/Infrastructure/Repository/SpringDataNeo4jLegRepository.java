package com.gof.springcloud.automatedbuilder.Infrastructure.Repository;

import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Leg;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SpringDataNeo4jLegRepository extends Neo4jRepository<Leg, Long> {
}
