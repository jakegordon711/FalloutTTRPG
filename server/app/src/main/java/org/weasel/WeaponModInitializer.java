package org.weasel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class WeaponModInitializer extends ItemBaseInitializer<WeaponMod>{

    public WeaponModInitializer(Connection conn) {
        super(WeaponMod::new, "./src/main/resources/weapon_mod.xlsx");

        itemSqlCheck = """
            SELECT weaponModId FROM WeaponMod LIMIT 10        
        """;

        itemSqlCreation = """
            INSERT INTO WeaponMod ("name", "prefix", "weaponId", "weaponType", "type", "effect", "weight", "cost", "perkIdList")
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)        
        """;

        try{
            Statement statement = conn.createStatement();
            if(!statement.execute(itemSqlCheck));
        }catch(SQLException e){
            System.out.println("Could not execute ammo check\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
            createWeaponModTable(conn);
            initalizeTable(conn);
        }
    }
    
    private void createWeaponModTable(Connection conn){
        String query = """
                CREATE TABLE WeaponMod (
                    weaponModId INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(255),
                    prefix VARCHAR(255),
                    weaponId INTEGER,
                    weaponType VARCHAR(255),
                    type VARCHAR(255),
                    effect VARCHAR(1600),
                    weight INTEGER,
                    cost INTEGER,
                    perkIdList VARCHAR(255),
                    FOREIGN KEY (weaponId) REFERENCES Weapon (weaponId)
                );
                """;
        executeQuery(conn, query, "Error creating the WeaponMod table");
    }
}
