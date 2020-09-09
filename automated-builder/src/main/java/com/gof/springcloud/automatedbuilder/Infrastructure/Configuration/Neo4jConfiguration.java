package com.gof.springcloud.automatedbuilder.Infrastructure.Configuration;

import com.gof.springcloud.automatedbuilder.Infrastructure.Repository.SpringDataNeo4jQueryRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@EnableNeo4jRepositories(basePackageClasses = SpringDataNeo4jQueryRepository.class)
public class Neo4jConfiguration {
}
