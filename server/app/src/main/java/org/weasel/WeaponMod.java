package org.weasel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WeaponMod extends ItemBase{

    private String name;
    private String prefix;
    private String weaponId;
    private String weaponType;
    private String type;
    private String effect;
    private int weight;
    private int cost;
    private String perk;


    @Override
    public String getAttributeString(String attribute) {
        switch(attribute){
            case "name":
            case "Name":
                return name;
            case "prefix":
            case "Prefix":
                    return prefix;
            case "weapon":
            case "Weapon":
            case "weaponId":
            case "WeaponId":
            case "weaponID":
            case "WeaponID":
                return weaponId;
            case "weaponType":
            case "Weapon Type":
                    return weaponType;
            case "type":
            case "Type":
                return type;
            case "effect":
            case "Effect":
                return effect;
            case "perk":
            case "Perk":
                return perk;
        }

        return "";
    }

    @Override
    public int getAttribute(String attribute) {
        switch(attribute){
            case "weight":
            case "Weight":
                return weight;
            case "cost":
            case "Cost":
                return cost;
        }

        return -1;
    }

    @Override
    public void setAttribute(String attribute, String value) {
        switch(attribute){
            case "name":
            case "Name":
            case "Mod Name":
                this.name = value;
                break;
                case "prefix":
                case "Prefix":
                    this.prefix = value;
                    break;
                case "weapon":
                case "Weapon":
                case "weaponId":
                case "WeaponId":
                case "weaponID":
                case "WeaponID":
                    this.weaponId = value;
                    break;
                case "type":
                case "Type":
                    this.type = value;
                    break;
                case "weaponType":
                case "Weapon Type":
                    this.weaponType = value;
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
            case "perk":
            case "Perk":
                this.perk = value;
                break;
        }
    }

    @Override
    public void addItem(Connection conn, PreparedStatement statement) {
        try{
            int weaponID = getWeaponId(conn);
            getPerkIdList(conn);

            statement.setString(1, name);
            statement.setString(2, prefix);
            statement.setInt(3, weaponID);
            statement.setString(4, weaponType);
            statement.setString(5, type);
            statement.setString(6, effect);
            statement.setInt(7, weight);
            statement.setInt(8, cost);
            statement.setString(9, perk);
            statement.addBatch();
        } catch(SQLException e){
            System.err.println("An error has occurred setting one of the ammo values in the SQL prepared statement\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
        }
    }

    private int getWeaponId(Connection conn){
        try{
            String query = """
                SELECT weaponId FROM Weapon WHERE name=?        
            """;
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, weaponId);
            ResultSet set = statement.executeQuery();

            if(set.next()){
                weaponId = String.valueOf(set.getInt(1));
                return set.getInt(1);
            }            
        }catch(SQLException e){
            System.err.println("An error has occurred getting the weaponId in the SQL prepared statement\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
        }

        return -1;
    }

    private void getPerkIdList(Connection conn){
        String regex = ",\s?"; //Split on comma and whitespace if included
        String[] perks = perk.split(regex);
        String perkIdList = "";

        String sql = """
            SELECT perkId FROM Perk WHERE name=? AND rank=?
        """;

        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setFetchSize(100);

            for(int perkIndex = 0; perkIndex < perks.length; perkIndex++){
                //Each perk should be in the format of name\srank
                String[] perkParts = perks[perkIndex].split("\s+(?!\\S*\s)"); //Regex to split at last space only
                if(perkParts.length > 1){
                    statement.setString(1, perkParts[0]);
                    statement.setString(2, perkParts[1]);
                    ResultSet set = statement.executeQuery();

                    if(set.next()){
                        perkIdList = (perkIdList == "") ? perkIdList.concat(String.valueOf(set.getInt(1))) : perkIdList.concat(","+String.valueOf(set.getInt(1)));
                    }
                }
            }

            perk = perkIdList;
        }catch(SQLException e){
            System.err.println("An error has occurred getting the perkId in the SQL prepared statement\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
        }
    }
    
}
