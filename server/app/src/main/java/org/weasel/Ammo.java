package org.weasel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ammo extends ItemBase{
    
private String name;
private String quantityFound;
private String effect;
private int rarity;

    /**
    * Name of the ammo
    * @return String name - name of the ammo
    */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the ammo
     * @param name - new name for the ammo to be set to 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the quantity found of the ammo
     * @return quantityFound - the amount of ammo found
     */
    public String getQuantityFound() {
        return quantityFound;
    }

    /**
     * Sets the quantity found of the ammo
     * @param quantityFound - the new quantityFound value expected to be in a %d * (%d + %dD6) i.e 2+5D6
     */
    public void setQuantityFound(String quantityFound) {
        this.quantityFound = quantityFound;
    }

    /**
     * Gets the effect for the ammo if it has one
     * @return effect - the effect the ammo has
     */
    public String getEffect() {
        return effect;
    }

    /**
     * Sets the effect the ammo has
     * @param effect - the new effect the ammo has
     */
    public void setEffect(String effect) {
        this.effect = effect;
    }

    /**
     * Gets the rarity for the ammo
     * @return rarity - how rare the ammo is
     */
    public int getRarity() {
        return rarity;
    }

    /**
     * Sets the rarity for the ammo
     * @param rarity - new rarity for the ammo
     */
    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    /**
     * get the attribute given the attribute name
     * @param attribute - name of the attribute to be set
     * @return String - value of the attribute passed or empty string if the attribute does not exist
     */
    public String getAttributeString(String attribute){
        switch(attribute){
            case "name":
            case "Name":
                return name;
            case "quantityFound":
            case "QuantityFound":
            case "Quantity Found":
                return quantityFound;
            case "effect":
            case "Effect":
                return effect;
        }

        return "";
    }

    /**
     * get the attribute given the attribute name
     * @param attribute - name of the attribute to be set
     * @return int - value of the attribute passed or -1 the attribute does not exist
     */
    public int getAttribute(String attribute){
        switch(attribute){
            case "weight":
            case "Weight":
                return weight;
            case "cost":
            case "Cost":
                return cost;
            case "rarity":
            case "Rarity":
                return this.rarity;
        }

        return -1;
    }

    /**
     * Sets the attribute given the attribute name and value
     * @param attribute - name of the attribute to be set
     * @param value - value the attribute should be set to
     */
    public void setAttribute(String attribute, String value){
        switch(attribute){
            case "name":
            case "Name":
            case "Ammunition Type":
                this.name = value;
                break;
            case "quantityFound":
            case "QuantityFound":
            case "Quantity Found":
                this.quantityFound = value;
                break;
            case "effect":
            case "Effect":
                this.effect = value;
                break;
            case "weight":
            case "Weight":
                try {
                    this.weight = Integer.parseInt(value);                    
                } catch (Exception e) {
                    this.weight = 0;
                }
                break;
            case "cost":
            case "Cost":
                try {
                    this.cost = Integer.parseInt(value);                    
                } catch (Exception e) {
                    this.cost = 0;
                }
                break;
            case "rarity":
            case "Rarity":
                try {
                    this.rarity = Integer.parseInt(value);                    
                } catch (Exception e) {
                    this.rarity = 0;
                }
                break;
        }
    }

    @Override
    public void addItem(Connection conn, PreparedStatement statement) {
        try{
            statement.setString(1, name);
            statement.setString(2, quantityFound);
            statement.setString(3, effect);
            statement.setInt(4, weight);
            statement.setInt(5, cost);
            statement.setInt(6, rarity);
            statement.addBatch();
        } catch(SQLException e){
            System.err.println("An error has occurred setting one of the ammo values in the SQL prepared statement\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
        }
    }
}
