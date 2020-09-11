package com.gof.springcloud.automatedbuilder.Infrastructure.Repository;

import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataNeo4jActivityRepository extends Neo4jRepository<Activity, Long> {
}
