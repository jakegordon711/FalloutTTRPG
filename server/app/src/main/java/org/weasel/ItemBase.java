package org.weasel;

import java.util.UUID;

public class ItemBase {
    
    protected UUID id;
    protected int weight;
    protected int cost;

    public ItemBase(){
        generateId();
    }

    private void generateId(){
        id = UUID.randomUUID();
    }

    public UUID getId(){
        return id;
    }

    public int getWeight(){
        return weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public int getCost(){
        return cost;
    }

    public void setCost(int cost){
        this.cost = cost;
    }



}
