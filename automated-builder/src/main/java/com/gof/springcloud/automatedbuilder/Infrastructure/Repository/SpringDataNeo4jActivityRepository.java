package com.gof.springcloud.automatedbuilder.Infrastructure.Repository;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SpringDataNeo4jActivityRepository extends Neo4jRepository<Activity, Long> {
    @Query("MATCH (a:Activity)\n" +
            "WITH a\n" +
            "ORDER BY a.category CONTAINS $category DESC,a.stars = 4 DESC,a.description CONTAINS $desc DESC\n" +
            "WITH COLLECT (a) AS orderedList\n" +
            "WITH [a IN orderedList WHERE NOT(a.category CONTAINS $category AND NOT(a.stars=$stars) AND NOT(a.description CONTAINS $desc)) OR NOT(NOT(a.category CONTAINS $category) AND NOT(a.stars=$stars) AND NOT(a.description CONTAINS $desc))] AS list1, orderedList\n" +
            "WITH [a IN orderedList WHERE a.category CONTAINS $category AND NOT(a.stars=$stars) AND NOT(a.description CONTAINS $desc)] AS list2, orderedList, list1\n" +
            "WITH [a IN orderedList WHERE NOT(a.category CONTAINS $category) AND NOT(a.stars=$stars) AND NOT(a.description CONTAINS $desc)] AS list3, list2, list1\n" +
            "UNWIND (list1+list2+list3) AS orderedActivities\n" +
            "RETURN orderedActivities")
    List<Activity> getActivitiesByRank(@Param("category") String category, @Param("desc") String desc, @Param("stars") int stars);

    @Query("CALL gds.alpha.allShortestPaths.stream({\n" +
            "  nodeQuery: 'MATCH (n) RETURN id(n) AS id',\n" +
            "  relationshipQuery: 'MATCH (n)-[r]-(p) RETURN id(n) AS source, id(p) AS target, r.cost AS cost',\n" +
            "  relationshipWeightProperty: 'cost'\n" +
            "})\n" +
            "YIELD sourceNodeId, targetNodeId, distance\n" +
            "WITH sourceNodeId, targetNodeId, distance\n" +
            "WHERE gds.util.isFinite(distance) = true\n" +
            "MATCH (source:Activity) WHERE id(source) = sourceNodeId\n" +
            "MATCH (target:Activity) WHERE id(target) = targetNodeId\n" +
            "WITH id(source) AS sourceNodeId, id(target) AS targetNodeId, distance WHERE source <> target\n" +
            "RETURN sourceNodeId, targetNodeId, distance")
    Iterable<Map<String, Object>> getAllPairs();
}
