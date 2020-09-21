package com.gof.springcloud.automatedbuilder.Domain.Repository;

import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;

public interface ITravelRepository {
    void Save(AbstractNodeEntity entity);
}
