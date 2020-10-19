package com.gof.springcloud.automatedbuilder.Infrastructure.Repository;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SpringDataNeo4jRepository extends Neo4jRepository<AbstractNodeEntity, Long> {
}
