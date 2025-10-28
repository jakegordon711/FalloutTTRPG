package org.weasel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Apparel extends ItemBase{

    private String name;
    private String type;
    private int physicalDr;
    private int energyDr;
    private int radiationDr;
    private int health;
    private String locations;
    private int rarity;

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPhysicalDr() {
        return physicalDr;
    }

    public void setPhysicalDr(int physicalDr) {
        this.physicalDr = physicalDr;
    }

    public int getEnergyDr() {
        return energyDr;
    }

    public void setEnergyDr(int energyDr) {
        this.energyDr = energyDr;
    }

    public int getRadiationDr() {
        return radiationDr;
    }

    public void setRadiationDr(int radiationDr) {
        this.radiationDr = radiationDr;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    @Override
    public String getAttributeString(String attribute) {
        switch(attribute){
            case "name":
            case "Name":
            case "armor name":
            case "Armor Name":
                return name;
            case "Type":
            case "type":
                return type;
            case "Locations":
            case "locations":
                return locations;
        }

        return "";
    }

    @Override
    public int getAttribute(String attribute) {
        switch(attribute){
            case "Physical DR":
            case "Physical Dr":
            case "physical dr":
            case "physicalDr":
                return physicalDr; 
            case "Energy DR":
            case "Energy Dr":
            case "energy dr":
            case "energyDr":
                return energyDr;
            case "Radiation DR":
            case "radiation Dr":
            case "radiation dr":
            case "radiationDr":
                return radiationDr;
            case "health":
            case "Health":
                return health;
            case "weight":
            case "Weight":
                return weight;
            case "cost":
            case "Cost":
                return cost;
            case "rarity":
            case "Rarity":
                return rarity;
        }

        return -1;
    }

    @Override
    public void setAttribute(String attribute, String value) {
        switch(attribute){
            case "name":
            case "Name":
            case "armor name":
            case "Armor Name":
                this.name = value;
                break;
            case "Type":
            case "type":
                this.type = value;
            case "Physical DR":
            case "Physical Dr":
            case "physical dr":
            case "physicalDr":
                try {
                    this.physicalDr = Integer.parseInt(value);                    
                } catch (Exception e) {
                    this.physicalDr = 0;
                }
                break;  
            case "Energy DR":
            case "Energy Dr":
            case "energy dr":
            case "energyDr":
                try {
                    this.energyDr = Integer.parseInt(value);                    
                } catch (Exception e) {
                    this.energyDr = 0;
                }
                break;
            case "Radiation DR":
            case "radiation Dr":
            case "radiation dr":
            case "radiationDr":
                try {
                    this.physicalDr = Integer.parseInt(value);                    
                } catch (Exception e) {
                    this.physicalDr = 0;
                }
                break; 
            case "Locations":
            case "locations":
                this.locations = value;
                break;  
            case "health":
            case "Health":
                try {
                    this.health = Integer.parseInt(value);                    
                } catch (Exception e) {
                    this.health = 0;
                }
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
            statement.setString(2, type);
            statement.setInt(3, physicalDr);
            statement.setInt(4, energyDr);
            statement.setInt(5, radiationDr);
            statement.setInt(6, health);
            statement.setString(7, locations);
            statement.setInt(8, weight);
            statement.setInt(9, cost);
            statement.setInt(10, rarity);
            statement.addBatch();
        }catch(SQLException e){

        }
    }
    
}
