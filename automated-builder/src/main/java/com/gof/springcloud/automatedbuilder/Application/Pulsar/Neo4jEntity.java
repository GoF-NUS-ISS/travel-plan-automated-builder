package com.gof.springcloud.automatedbuilder.Application.Pulsar;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntityLinkedList;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Neo4jEntity {

    private AbstractNodeEntity entity;

    private AbstractNodeEntityLinkedList linkedList;
}
