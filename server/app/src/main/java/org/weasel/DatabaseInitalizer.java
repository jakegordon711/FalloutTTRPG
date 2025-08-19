package org.weasel;

/*
 * Used to initalize the database schema as intended by the fallout guide and will be updated as so
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitalizer{

    private String databaseDrive = "jdbc:sqlite";
    private String dbName = "fallout_ttrpg";

    public DatabaseInitalizer(){
        databaseDrive = databaseDrive.concat(":"+dbName+".db");
        initalizeDatabase();
    }

    public DatabaseInitalizer(String dbName){
        this.dbName = dbName; //Needs saved to a config file to allow reusing
        databaseDrive = databaseDrive.concat(":"+dbName+".db");
        initalizeDatabase();
    }

    private void initalizeDatabase(){
        try(Connection conn = DriverManager.getConnection(databaseDrive)){
            System.out.println("Database has been created: "+dbName);
            createPerkTable(conn);
            createAmmoTable(conn);
            createWeaponTable(conn);
            createWeaponModTable(conn);
            createApparelTable(conn);
            createApparelModTable(conn);
            createArmourLocationTable(conn);
            createAidTable(conn);
            createChemTable(conn);
            createMiscTable(conn);
            createEffectTable(conn);

            //Create m2m tables
            createWeaponEffectTable(conn);
            createWeaponModEffectTable(conn);
            createApparelEffectTable(conn);
            createApparelModEffectTable(conn);
            createArmourLocationApparelTable(conn);
            createAidEffectTable(conn);
            createChemEffectTable(conn);
            createMiscEffectTable(conn);

        }catch(SQLException e){
            System.out.println("An exception has occured connecting to the database "+databaseDrive+"\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
        }
    }

    private void createPerkTable(Connection conn){
        String query = """
                CREATE TABLE Perk (
                    perkId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    effect VARCHAR(1600)
                );
                """;
        executeQuery(conn, query, "Error creating the Perk table");
    }

    private void createAmmoTable(Connection conn){
        String query = """
                CREATE TABLE Ammo (
                    ammoId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    weight int,
                    cost int,
                    rarity int
                );
                """;
        executeQuery(conn, query, "Error creating the Ammo table");
    }

    private void createWeaponTable(Connection conn){
        String query = """
                CREATE TABLE Weapon (
                    ammoId int NOT NULL,
                    weaponId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    type VARCHAR(255),
                    damage VARCHAR(255),
                    damageType VARCHAR(255),
                    fireRate int,
                    range VARCHAR(255),
                    weight int,
                    cost int,
                    rarity int,
                    FOREIGN KEY (ammoId) REFERENCES Ammo (ammoId)
                );
                """;
        executeQuery(conn, query, "Error creating the Weapon table");
    }

    private void createWeaponModTable(Connection conn){
        String query = """
                CREATE TABLE WeaponMod (
                    perkId int NOT NULL,
                    weaponModId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    namePrefix VARCHAR(255),
                    weight int,
                    cost int,
                    FOREIGN KEY (perkId) REFERENCES Perk (perkId)
                );
                """;
        executeQuery(conn, query, "Error creating the WeaponMod table");
    }

    private void createApparelTable(Connection conn){
        String query = """
                CREATE TABLE Apparel (
                    apparelId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    type VARCHAR(255),
                    physicalDr int,
                    energyDr int,
                    radiationDr int,
                    health int,
                    weight int,
                    cost int,
                    rarity int
                );
                """;
        executeQuery(conn, query, "Error creating the Apparel table");
    }

    private void createApparelModTable(Connection conn){
        String query = """
                CREATE TABLE ApparelMod (
                    perkId int NOT NULL,
                    apparelModId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    physicalDr int,
                    energyDr int,
                    radiationDr int,
                    weight int,
                    cost int,
                    FOREIGN KEY (perkId) REFERENCES Perk (perkId)
                );
                """;
        executeQuery(conn, query, "Error creating the ApparelMod table");
    }

    private void createAidTable(Connection conn){
        String query = """
                CREATE TABLE Aid (
                    aidId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    type VARCHAR(255),
                    health int,
                    radiationDamage int,
                    weight int,
                    cost int,
                    rarity int
                );
                """;
        executeQuery(conn, query, "Error creating the Aid table");
    }

    private void createChemTable(Connection conn){
        String query = """
                CREATE TABLE Chem (
                    chemId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    duration VARCHAR(255),
                    addictiveLevel int,
                    weight int,
                    cost int,
                    rarity int
                );
                """;
        executeQuery(conn, query, "Error creating the Chem table");
    }

    private void createMiscTable(Connection conn){
        String query = """
                CREATE TABLE Misc (
                    miscId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    weight int,
                    cost int,
                    rarity int
                );
                """;
        executeQuery(conn, query, "Error creating the Misc table");
    }

    private void createEffectTable(Connection conn){
        String query = """
                CREATE TABLE Effect (
                    effectId int AUTO_INCREMENT PRIMARY KEY,
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
                    armourLocationId int AUTO_INCREMENT PRIMARY KEY,
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
                    weaponId int NOT NULL,
                    effectId int NOT NULL,
                    weaponEffectId int AUTO_INCREMENT PRIMARY KEY,
                    FOREIGN KEY (weaponId) REFERENCES Weapon (weaponId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the WeaponEffectM2M table");
    }

    private void createWeaponModEffectTable(Connection conn){
        String query = """
                CREATE TABLE WeaponModEffectM2M (
                    weaponModId int NOT NULL,
                    effectId int NOT NULL,
                    weaponModEffectId int AUTO_INCREMENT PRIMARY KEY,
                    FOREIGN KEY (weaponModId) REFERENCES WeaponMod (weaponModId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the WeaponModEffectM2M table");
    }

    private void createApparelEffectTable(Connection conn){
        String query = """
                CREATE TABLE ApparelEffectM2M (
                    apparelId int NOT NULL,
                    effectId int NOT NULL,
                    apparelEffectId int AUTO_INCREMENT PRIMARY KEY,
                    FOREIGN KEY (apparelId) REFERENCES Apparel (apparelId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the ApparelEffectM2M table");
    }

    private void createArmourLocationApparelTable(Connection conn){
        String query = """
                CREATE TABLE ArmourLocationApparelM2M (
                    apparelId int NOT NULL,
                    armourLocationId int NOT NULL,
                    armourLocationApparelId int AUTO_INCREMENT PRIMARY KEY,
                    FOREIGN KEY (apparelId) REFERENCES Apparel (apparelId),
                    FOREIGN KEY (armourLocationId) REFERENCES ArmourLocation (armourLocationId)
                );
                """;
        executeQuery(conn, query, "Error creating the ArmourLocationApparelM2M table");
    }

    private void createApparelModEffectTable(Connection conn){
        String query = """
                CREATE TABLE ApparelModEffectM2M (
                    apparelModId int NOT NULL,
                    effectId int NOT NULL,
                    apparelModEffectId int AUTO_INCREMENT PRIMARY KEY,
                    FOREIGN KEY (apparelModId) REFERENCES ApparelMod (apparelModId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the ApparelModEffectM2M table");
    }

    private void createAidEffectTable(Connection conn){
        String query = """
                CREATE TABLE AidEffectM2M (
                    aidId int NOT NULL,
                    effectId int NOT NULL,
                    aidEffectId int AUTO_INCREMENT PRIMARY KEY,
                    FOREIGN KEY (aidId) REFERENCES Aid (aidId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the AidEffectM2M table");
    }

    private void createChemEffectTable(Connection conn){
        String query = """
                CREATE TABLE ChemEffectM2M (
                    chemId int NOT NULL,
                    effectId int NOT NULL,
                    chemEffectId int AUTO_INCREMENT PRIMARY KEY,
                    FOREIGN KEY (chemId) REFERENCES Chem (chemId),
                    FOREIGN KEY (effectId) REFERENCES Effect (effectId)
                );
                """;
        executeQuery(conn, query, "Error creating the ChemEffectM2M table");
    }

    private void createMiscEffectTable(Connection conn){
        String query = """
                CREATE TABLE MiscEffectM2M (
                    miscId int NOT NULL,
                    effectId int NOT NULL,
                    miscEffectId int AUTO_INCREMENT PRIMARY KEY,
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