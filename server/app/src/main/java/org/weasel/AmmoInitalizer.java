package org.weasel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AmmoInitalizer extends ItemBaseInitializer<Ammo> {

    public AmmoInitalizer(Connection conn){
        super(Ammo::new, "./src/main/resources/ammo.xlsx");

        this.itemSqlCheck = """
            SELECT "ammoId" FROM Ammo LIMIT 10        
        """;

        this.itemSqlCreation = """
            INSERT INTO Ammo (name, quantityFound, effect, weight, cost, rarity)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try{
            Statement statement = conn.createStatement();
            if(!statement.execute(itemSqlCheck));
        }catch(SQLException e){
            System.out.println("Could not execute ammo check\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
            createAmmoTable(conn);
            initalizeTable(conn);
        }
    }
    
    private void createAmmoTable(Connection conn){
        String query = """
            CREATE TABLE Ammo (
                ammoId INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(255),
                quantityFound VARCHAR(255),
                effect VARCHAR(255),
                weight INTEGER,
                cost INTEGER,
                rarity INTEGER
            );
        """;

        executeQuery(conn, query, "Error creating the Ammo table");
    }

}
