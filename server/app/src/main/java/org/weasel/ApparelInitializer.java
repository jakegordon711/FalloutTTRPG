package org.weasel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ApparelInitializer extends ItemBaseInitializer<Apparel> {

    public ApparelInitializer(Connection conn) {
        super(Apparel::new, "./src/main/resources/armor.xlsx");
        
        itemSqlCheck = """
            SELECT apparelId FROM Apparel LIMIT 10        
        """;

        itemSqlCreation = """
            INSERT INTO Apparel ("name", "type", "physicalDr", "energyDr", "radiationDr", "health", "locations", "weight", "cost", "rarity")
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)        
        """;

        try{
            Statement statement = conn.createStatement();
            if(!statement.execute(itemSqlCheck));
        }catch(SQLException e){
            System.out.println("Could not execute ammo check\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
            createApparelTable(conn);
            initalizeTable(conn);
        }
    }
 
    private void createApparelTable(Connection conn){
        String query = """
            CREATE TABLE Apparel (
                apparelId INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(255),
                type VARCHAR(255),
                physicalDr INTEGER,
                energyDr INTEGER,
                radiationDr INTEGER,
                health INTEGER,
                locations VARCHAR(255),
                weight INTEGER,
                cost INTEGER,
                rarity INTEGER
            );
        """;
        executeQuery(conn, query, "Error creating the Apparel table");
    }
}
