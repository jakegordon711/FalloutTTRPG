package org.weasel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Weapon extends ItemBase{
    
    public Weapon(){
        super();
    }

    private String name;
    private String type;
    //private Ammo ammo;
    private String damage;
    private int damageType;
    private int fireRate;
    private String range;
    private String ammoIdList;
    private int rarity;

    private LinkedList<Ammo> ammo;

    /**
     * @param headers[] - headers provided via spreadsheet import and should match
     * Name
     * Weapon Type
     * Damage Rating
     * Damage Effects
     * Damage Type
     * Fire Rate
     * Range
     * Qualities
     * Weight
     * Cost
     * Rarity
     */
    public void validateHeaders(String headers[]){

    }

    /********************************/
    /** Standard Setter and Getters */
    /********************************/

    /**
     * Gets the name of the weapon
     * @return - name of the weapon
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the name of the weapon
     * @param name - new name for the weapon
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the type of the weapon
     * @return String type - type of the weapon
     */
    public String getType(){
        return type;
    }

    /**
     * Sets the type of the weapon
     * @param type - new type for the weapon
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Gets the damage of the weapon
     * @return String damage - damage of the weapon
     */
    public String getDamage(){
        return damage;
    }

    /**
     * Sets the damage of the weapon
     * @param damage - new damage for the weapon
     */
    public void setDamage(String damage){
        this.damage = damage;
    }

    /**
     * Gets the damage type of the weapon
     * @return int damage type - damage type of the weapon
     */
    public int getDamageType(){
        return damageType;
    }

    /**
     * Sets the damage type of the weapon
     * @param damage type - new damage type for the weapon
     */
    public void setDamageType(int damageType){
        this.damageType = damageType;
    }

    /**
     * Gets the fire rate of the weapon
     * @return int fire rate - fire rate of the weapon
     */
    public int getFireRate(){
        return fireRate;
    }

    /**
     * Sets the fire rate of the weapon
     * @param fire rate - new fire rate for the weapon
     */
    public void setFireRate(int fireRate){
        this.fireRate = fireRate;
    }

    /**
     * Gets the range of the weapon
     * @return String range of the weapon
     */
    public String getRange(){
        return range;
    }

    /**
     * Sets the range of the weapon
     * @param range - new range for the weapon
     */
    public void setRange(String range){
        this.range = range;
    }

    /**
     * Gets the weight of the weapon
     * @return int weight - weight of the weapon
     */
    public int getWeight(){
        return weight;
    }

    /**
     * Sets the weight of the weapon
     * @param weight - new weight for the weapon
     */
    public void setWeight(int weight){
        this.weight = weight;
    }

    /**
     * Gets the cost of the weapon
     * @return int cost - cost of the weapon
     */
    public int getCost(){
        return cost;
    }

    /**
     * Sets the cost of the weapon
     * @param cost - new cost for the weapon
     */
    public void setCost(int cost){
        this.cost = cost;
    }

    /**
     * Gets the rarity rate of the weapon
     * @return int rarity - rarity of the weapon
     */
    public int getRarity(){
        return rarity;
    }

    /**
     * Sets the rarity of the weapon
     * @param rarity - new rarity for the weapon
     */
    public void setRarity(int rarity){
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
            case "type":
            case "Type":
            case "weaponType":
            case "Weapon Type":
                return type;
            case "damageRating":
            case "Damage Rating":
                return damage;
            case "range":
            case "Range":
                return range;
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
            case "damageType":
            case "Damage Type":
                return this.damageType;
            case "fireRate":
            case "Fire Rate":
                return this.fireRate;
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
                this.name = value;
                break;
            case "type":
            case "Type":
            case "weaponType":
            case "Weapon Type":
                this.type = value;
                break;
            case "ammo":
            case "Ammo":
                this.ammoIdList = value;
                break;
            case "damageRating":
            case "Damage Rating":
                this.damage = value;
                break;
            case "damageType":
            case "Damage Type":
                try {
                    this.damageType = Integer.parseInt(value);                    
                } catch (Exception e) {
                    this.damageType = 0;
                }
                break;
            case "fireRate":
            case "Fire Rate":
                try {
                    this.fireRate = Integer.parseInt(value);                    
                } catch (Exception e) {
                    this.fireRate = 0;
                }
                break;
            case "range":
            case "Range":
                this.range = value;
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
            convertAmmoIdList(conn);
            statement.setString(1, name);
            statement.setString(2, type);
            statement.setString(3, damage);
            statement.setInt(4, damageType);
            statement.setInt(5, fireRate);
            statement.setString(6, range);
            statement.setString(7, ammoIdList);
            statement.setInt(8, weight);
            statement.setInt(9, cost);
            statement.setInt(10, rarity);
            statement.addBatch();
        } catch(SQLException e){
            System.err.println("An error has occurred setting one of the weapons values in the SQL prepared statement\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
        }
    }

    /**
     * Converts the ammoIdList when set to a list of ammo names to their Ids - if an ammo is not found it is omitted
     * @param conn - connection object to the db
     */
    public void convertAmmoIdList(Connection conn){
        String regex = ",\s?"; //Split on comma and whitespace if included
        String[] ammoNames = ammoIdList.split(regex);
        String[] ammoIds = new String[ammoNames.length];

        String sql = """
            SELECT ammoId FROM Ammo WHERE name=?
        """;

        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setFetchSize(100);

            for(int ammoIndex = 0; ammoIndex < ammoNames.length; ammoIndex++){
                statement.setString(1, ammoNames[ammoIndex]);
                ResultSet set = statement.executeQuery();

                while(set.next()){
                    ammoIds[ammoIndex] = String.valueOf(set.getInt(1));
                }
            }

            ammoIdList = String.join(",", ammoIds);
        }catch(SQLException e){
            System.err.println("An error has occurred getting one of the ammoIds in the SQL prepared statement\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
        }
    }
}
