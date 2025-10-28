package org.weasel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PerkInitializer extends ItemBaseInitializer<Perk>{

    public PerkInitializer(Connection conn) {
        super(Perk::new, "./src/main/resources/perk.xlsx");
        
        itemSqlCheck = """
            SELECT perkId * FROM Perk        
        """;

        itemSqlCreation = """
            INSERT INTO Perk (name, rank, requirement, description)
            VALUES (?, ?, ?, ?)        
        """;

        try{
            Statement statement = conn.createStatement();
            if(!statement.execute(itemSqlCheck));
        }catch(SQLException e){
            System.out.println("Could not execute ammo check\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
            createPerkTable(conn);
            initalizeTable(conn);
        }
    }

    private void createPerkTable(Connection conn){
        String query = """
                CREATE TABLE Perk (
                    perkId INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(255),
                    rank INTEGER,
                    requirement VARCHAR(255),
                    description VARCHAR(1600)
                );
                """;
        executeQuery(conn, query, "Error creating the Perk table");
    }
    
}
