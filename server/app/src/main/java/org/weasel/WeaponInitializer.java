package org.weasel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Checks if a weapon table exists, if it does then it will load all weapons, else it will load from a spreadsheet and create the table
*/
public class WeaponInitializer extends ItemBaseInitializer<Weapon>{

    public WeaponInitializer(Connection conn){
        super(Weapon::new, "./src/main/resources/weapon.xlsx");

        itemSqlCheck = """
            SELECT "weaponId" FROM Weapon LIMIT 10;    
        """;

        itemSqlCreation = """
            INSERT INTO Weapon ("name", "type", "damage", "damageType", "fireRate", "range", "ammoIdList", "weight", "cost", "rarity") 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)     
        """;

        try{
            Statement statement = conn.createStatement();
            if(!statement.execute(itemSqlCheck));
        }catch(SQLException e){
            System.out.println("Could not execute weapon check\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
            createWeaponTable(conn);
            initalizeTable(conn);
        }
    }

    private void createWeaponTable(Connection conn){
        String query = """
            CREATE TABLE Weapon (
                weaponId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(255),
                type VARCHAR(255),
                damage VARCHAR(255),
                damageType VARCHAR(255),
                fireRate INTEGER,
                range VARCHAR(255),
                ammoIdList VARCHAR(255),
                weight INTEGER,
                cost INTEGER,
                rarity INTEGER
            );
        """;

        executeQuery(conn, query, "Error creating the Weapon table");
    }
}
