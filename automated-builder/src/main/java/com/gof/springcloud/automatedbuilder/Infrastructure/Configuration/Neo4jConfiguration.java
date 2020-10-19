package com.gof.springcloud.automatedbuilder.Infrastructure.Configuration;

import com.gof.springcloud.automatedbuilder.Infrastructure.Repository.SpringDataNeo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

//inject repositories. No need annotate repository interfaces
@EnableNeo4jRepositories(basePackageClasses = SpringDataNeo4jRepository.class)
public class Neo4jConfiguration {
}
