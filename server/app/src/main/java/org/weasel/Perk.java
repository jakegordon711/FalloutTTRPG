package org.weasel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Perk extends ItemBase{

    private String name;
    private int rank;
    private String requirement;
    private String description;

    @Override
    public String getAttributeString(String attribute) {
        switch(attribute){
            case "name":
            case "Name":
            case "Perk Name":
                return name;
            case "requirement":
            case "Requirement":
            case "requirements":
            case "Requirements":
                return requirement;
            case "description":
            case "Description":
                return description;
        }

        return "";
    }

    @Override
    public int getAttribute(String attribute) {
        switch(attribute){
            case "rank":
            case "Rank":
                return rank;
        }

        return -1;
    }

    @Override
    public void setAttribute(String attribute, String value) {
        switch(attribute){
            case "name":
            case "Name":
            case "Perk Name":
                this.name = value;
                break;
            case "rank":
            case "Rank":
                try {
                    this.rank = Integer.parseInt(value);                    
                } catch (Exception e) {
                    this.rank = 1;
                }
            case "requirement":
            case "Requirement":
            case "requirements":
            case "Requirements":
                this.requirement = value;
                break;
            case "description":
            case "Description":
                this.description = value;
                break;
        }
    }

    @Override
    public void addItem(Connection conn, PreparedStatement statement) {
        try{
            statement.setString(1, name);
            statement.setInt(2, rank);
            statement.setString(3, requirement);
            statement.setString(4, description);
            statement.addBatch();
        } catch(SQLException e){
            System.err.println("An error has occurred setting one of the ammo values in the SQL prepared statement\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
        }
    }
    
}
