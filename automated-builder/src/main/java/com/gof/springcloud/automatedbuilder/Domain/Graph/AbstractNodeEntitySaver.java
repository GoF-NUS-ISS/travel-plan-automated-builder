package com.gof.springcloud.automatedbuilder.Domain.Graph;

public class AbstractNodeEntitySaver {
    public AbstractNodeEntitySaver(){
    }
    public AbstractNodeEntitySaver(AbstractNodeEntity abstractNodeEntity){
        this.abstractNodeEntity = abstractNodeEntity;
    }

    public AbstractNodeEntity getAbstractNodeEntity() {
        return abstractNodeEntity;
    }

    public void setAbstractNodeEntity(AbstractNodeEntity abstractNodeEntity) {
        this.abstractNodeEntity = abstractNodeEntity;
    }

    private AbstractNodeEntity abstractNodeEntity;

}
