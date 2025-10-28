package org.weasel;

/*
 * Used to initalize the database schema as INTEGERended by the fallout guide and will be updated as so
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer{

    private String databaseDrive = "jdbc:sqlite";
    private String dbName = "fallout_ttrpg";

    public DatabaseInitializer(){
        databaseDrive = databaseDrive.concat(":"+dbName+".db");
        initalizeDatabase();
    }

    public DatabaseInitializer(String dbName){
        this.dbName = dbName; //Needs saved to a config file to allow reusing
        databaseDrive = databaseDrive.concat(":"+dbName+".db");
        initalizeDatabase();
    }

    private void initalizeDatabase(){
        try(Connection conn = DriverManager.getConnection(databaseDrive)){
            System.out.println("Database has been created: "+dbName);
            // createPerkTable(conn);
            // createAmmoTable(conn);
            // createWeaponTable(conn);
            // createWeaponModTable(conn);
            // createApparelTable(conn);
            // createApparelModTable(conn);
            // createArmourLocationTable(conn);
            // createAidTable(conn);
            // createChemTable(conn);
            // createMiscTable(conn);
            // createEffectTable(conn);

            // //Create m2m tables
            // createWeaponEffectTable(conn);
            // createWeaponModEffectTable(conn);
            // createApparelEffectTable(conn);
            // createApparelModEffectTable(conn);
            // createArmourLocationApparelTable(conn);
            // createAidEffectTable(conn);
            // createChemEffectTable(conn);
            // createMiscEffectTable(conn);

            new PerkInitializer(conn);
            new AmmoInitalizer(conn);
            new WeaponInitializer(conn);
            new ApparelInitializer(conn);
            new WeaponModInitializer(conn);
        }catch(SQLException e){
            System.out.println("An exception has occured connecting to the database "+databaseDrive+"\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
        }
    }

    private void createAmmoTable(Connection conn){
        String query = """
                CREATE TABLE Ammo (
                    ammoId INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(255),
                    weight INTEGER,
                    cost INTEGER,
                    rarity INTEGER
                );
                """;
        executeQuery(conn, query, "Error creating the Ammo table");
    }

    private void createApparelModTable(Connection conn){
        String query = """
                CREATE TABLE ApparelMod (
                    perkId INTEGER NOT NULL,
                    apparelModId INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(255),
                    physicalDr INTEGER,
                    energyDr INTEGER,
                    radiationDr INTEGER,
                    weight INTEGER,
                    cost INTEGER,
                    FOREIGN KEY (perkId) REFERENCES Perk (perkId)
                );
                """;
        executeQuery(conn, query, "Error creating the ApparelMod table");
    }

    private void createAidTable(Connection conn){
        String query = """
                CREATE TABLE Aid (
                    aidId INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(255),
                    type VARCHAR(255),
                    health INTEGER,
                    radiationDamage INTEGER,
                    weight INTEGER,
                    cost INTEGER,
                    rarity INTEGER
                );
                """;
        executeQuery(conn, query, "Error creating the Aid table");
    }

    private void createChemTable(Connection conn){
        String query = """
                CREATE TABLE Chem (
                    chemId INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(255),
                    duration VARCHAR(255),
                    addictiveLevel INTEGER,
                    weight INTEGER,
                    cost INTEGER,
                    rarity INTEGER
                );
                """;
        executeQuery(conn, query, "Error creating the Chem table");
    }

    private void createMiscTable(Connection conn){
        String query = """
                CREATE TABLE Misc (
                    miscId INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(255),
                    weight INTEGER,
                    cost INTEGER,
                    rarity INTEGER
                );
                """;
        executeQuery(conn, query, "Error creating the Misc table");
    }

    private void createEffectTable(Connection conn){
        String query = """
                CREATE TABLE Effect (
                    effectId INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(255),
                    type VARCHAR(255),
                    effect VARCHAR(1600)
                );
                """;
        executeQuery(conn, query, "Error creating the Misc table");
    }

    private void createArmourLocationTable(Connection conn){
        String query = """
                CREATE TABLE ArmourLocation (
                    armourLocationId INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(255)
                );
                """;
        executeQuery(conn, query, "Error creating the ArmourLocation table");
    }


    /**
     * Create M2M tables to things like multiple effects per armour, weapon or aid etc
     */

     private void createWeaponEffectTable(Connection conn){
        String query = """
                CREATE TABLE WeaponEffectM2M (
                    weaponId INTEGER NOT NULL,
                    effectId INTEGER NOT NULL,
                    weaponEffectId INTEGER PRIMARY KEY AUTOINCREMENT,
                    FOREIGN KEY (weaponId) REFERENCES Weapon (weaponId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the WeaponEffectM2M table");
    }

    private void createWeaponModEffectTable(Connection conn){
        String query = """
                CREATE TABLE WeaponModEffectM2M (
                    weaponModId INTEGER NOT NULL,
                    effectId INTEGER NOT NULL,
                    weaponModEffectId INTEGER PRIMARY KEY AUTOINCREMENT,
                    FOREIGN KEY (weaponModId) REFERENCES WeaponMod (weaponModId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the WeaponModEffectM2M table");
    }

    private void createApparelEffectTable(Connection conn){
        String query = """
                CREATE TABLE ApparelEffectM2M (
                    apparelId INTEGER NOT NULL,
                    effectId INTEGER NOT NULL,
                    apparelEffectId INTEGER PRIMARY KEY AUTOINCREMENT,
                    FOREIGN KEY (apparelId) REFERENCES Apparel (apparelId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the ApparelEffectM2M table");
    }

    private void createArmourLocationApparelTable(Connection conn){
        String query = """
                CREATE TABLE ArmourLocationApparelM2M (
                    apparelId INTEGER NOT NULL,
                    armourLocationId INTEGER NOT NULL,
                    armourLocationApparelId INTEGER PRIMARY KEY AUTOINCREMENT,
                    FOREIGN KEY (apparelId) REFERENCES Apparel (apparelId),
                    FOREIGN KEY (armourLocationId) REFERENCES ArmourLocation (armourLocationId)
                );
                """;
        executeQuery(conn, query, "Error creating the ArmourLocationApparelM2M table");
    }

    private void createApparelModEffectTable(Connection conn){
        String query = """
                CREATE TABLE ApparelModEffectM2M (
                    apparelModId INTEGER NOT NULL,
                    effectId INTEGER NOT NULL,
                    apparelModEffectId INTEGER PRIMARY KEY AUTOINCREMENT,
                    FOREIGN KEY (apparelModId) REFERENCES ApparelMod (apparelModId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the ApparelModEffectM2M table");
    }

    private void createAidEffectTable(Connection conn){
        String query = """
                CREATE TABLE AidEffectM2M (
                    aidId INTEGER NOT NULL,
                    effectId INTEGER NOT NULL,
                    aidEffectId INTEGER PRIMARY KEY AUTOINCREMENT,
                    FOREIGN KEY (aidId) REFERENCES Aid (aidId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the AidEffectM2M table");
    }

    private void createChemEffectTable(Connection conn){
        String query = """
                CREATE TABLE ChemEffectM2M (
                    chemId INTEGER NOT NULL,
                    effectId INTEGER NOT NULL,
                    chemEffectId INTEGER PRIMARY KEY AUTOINCREMENT,
                    FOREIGN KEY (chemId) REFERENCES Chem (chemId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the ChemEffectM2M table");
    }

    private void createMiscEffectTable(Connection conn){
        String query = """
                CREATE TABLE MiscEffectM2M (
                    miscId INTEGER NOT NULL,
                    effectId INTEGER NOT NULL,
                    miscEffectId INTEGER PRIMARY KEY AUTOINCREMENT,
                    FOREIGN KEY (miscId) REFERENCES Misc (miscId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the MiscEffectM2M table");
    }

    private void executeQuery(Connection conn, String query, String baseLog){
        try{
            Statement statement = conn.createStatement();
            statement.execute(query);
        }catch(SQLException e){
            System.out.println(baseLog+"\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
        }
    }
}