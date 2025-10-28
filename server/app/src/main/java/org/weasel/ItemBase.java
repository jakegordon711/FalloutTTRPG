package org.weasel;

import java.sql.Connection;
import java.sql.PreparedStatement;

abstract class ItemBase {
    
    protected int id;
    protected int weight;
    protected int cost;

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

    /**
     * get the attribute given the attribute name
     * @param attribute - name of the attribute to be set
     * @return String - value of the attribute passed or empty string if the attribute does not exist
     */
    abstract public String getAttributeString(String attribute);

    /**
     * get the attribute given the attribute name
     * @param attribute - name of the attribute to be set
     * @return int - value of the attribute passed or -1 the attribute does not exist
     */
    abstract public int getAttribute(String attribute);

    /**
     * Sets the attribute given the attribute name and value
     * @param attribute - name of the attribute to be set
     * @param value - value the attribute should be set to
     */
    abstract public void setAttribute(String attribute, String value);

    abstract public void addItem(Connection conn, PreparedStatement statement);

}
