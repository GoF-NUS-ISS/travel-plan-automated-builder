package com.gof.springcloud.automatedbuilder.Infrastructure.Repository;

import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelLeg.Location;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SpringDataNeo4jLocationRepository extends Neo4jRepository<Location, Long> {

    @Query("MATCH (n:Location) WHERE n.location=$location RETURN n")
    List<Location> findLocationByString(String location);
}
