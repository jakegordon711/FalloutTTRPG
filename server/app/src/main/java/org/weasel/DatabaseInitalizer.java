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
            createWeaponTable(conn);
            createApparelTable(conn);
            createAidTable(conn);
            createChemTable(conn);
            createMiscTable(conn);
            createPerkTable(conn);
            createEffectTable(conn);
        }catch(SQLException e){
            System.out.println("An exception has occured connecting to the database "+databaseDrive+"\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
        }
    }

    private void createWeaponTable(Connection conn){
        String query = """
                CREATE TABLE weapon (
                    weaponId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    type VARCHAR(255),
                    damage VARCHAR(255),
                    damageType VARCHAR(255),
                    fireRate int,
                    range VARCHAR(255),
                    weight int,
                    cost int,
                    rarity int
                );
                """;
        executeQuery(conn, query, "Error creating the weapons table");
    }

    private void createApparelTable(Connection conn){
        String query = """
                CREATE TABLE apparel (
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
        executeQuery(conn, query, "Error creating the apparel table");
    }

    private void createAidTable(Connection conn){
        String query = """
                CREATE TABLE aid (
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
        executeQuery(conn, query, "Error creating the weapons table");
    }

    private void createChemTable(Connection conn){
        String query = """
                CREATE TABLE chem (
                    chemId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    duration VARCHAR(255),
                    addictiveLevel int,
                    weight int,
                    cost int,
                    rarity int
                );
                """;
        executeQuery(conn, query, "Error creating the weapons table");
    }

    private void createMiscTable(Connection conn){
        String query = """
                CREATE TABLE misc (
                    miscId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    weight int,
                    cost int,
                    rarity int
                );
                """;
        executeQuery(conn, query, "Error creating the weapons table");
    }

    private void createPerkTable(Connection conn){
        String query = """
                CREATE TABLE perk (
                    perkId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    effect VARCHAR(1600),
                );
                """;
        executeQuery(conn, query, "Error creating the weapons table");
    }

    private void createEffectTable(Connection conn){
        String query = """
                CREATE TABLE effect (
                    effectId int AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    type VARCHAR(255),
                    effect VARCHAR(1600),
                );
                """;
        executeQuery(conn, query, "Error creating the weapons table");
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