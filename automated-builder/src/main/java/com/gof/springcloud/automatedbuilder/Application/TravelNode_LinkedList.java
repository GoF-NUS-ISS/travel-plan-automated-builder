package com.gof.springcloud.automatedbuilder.Application;

import com.gof.springcloud.automatedbuilder.Application.Request.TravelPlanModel_DayNode;

public class TravelNode_LinkedList {
    public TravelNode_LinkedList (){
        this.next = null;
    }
    private TravelPlanModel_DayNode requestModelNode;

    private TravelNode_LinkedList next;

    public TravelPlanModel_DayNode getRequestModelNode() {
        return requestModelNode;
    }

    public void setRequestModelNode(TravelPlanModel_DayNode requestModelNode) {
        this.requestModelNode = requestModelNode;
    }

    public TravelNode_LinkedList getNext() {
        return next;
    }

    public void setNext(TravelNode_LinkedList next) {
        this.next = next;
    }
}
