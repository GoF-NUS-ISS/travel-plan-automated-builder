package com.gof.springcloud.automatedbuilder.Domain.Graph;

import lombok.Data;

@Data
public class AbstractNodeEntityLinkedList {
    private AbstractNodeEntityLinkedList next=null;

    private AbstractNodeEntityLinkedList prev=null;

    private AbstractNodeEntity abstractNodeEntity=null;

    @Override
    public String toString() {
        return "AbstractNodeEntityLinkedList{" +
                ", abstractNodeEntity=" + abstractNodeEntity +
                ", prev=" + prev +
                '}';
    }
}
