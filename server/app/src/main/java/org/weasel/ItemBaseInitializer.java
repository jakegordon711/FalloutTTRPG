package org.weasel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.function.Supplier;

abstract class ItemBaseInitializer<T extends ItemBase> {
    private final Supplier<T> supplier;
    private final String importFile;
 
    protected String itemSqlCheck; 
    protected String itemSqlCreation;

    public ItemBaseInitializer(Supplier<T> supplier, String importFile){
        this.supplier = supplier;
        this.importFile = importFile;
    }

    protected void initalizeTable(Connection conn){
        ExcelLoader loader = new ExcelLoader();

        try{
            LinkedList<String> rows = loader.processAllSheets(importFile);
            //Need to do some validation - to be fair if we send in a validation function to vet and make this dynamic
            LinkedList<T> items = new LinkedList<T>();
            ListIterator<String> iter = rows.listIterator();
            String headers[] = iter.next().split("\t\\|\t"); //Pass over the heading column - we could exlude this once the validation function above has been added

            PreparedStatement statement = conn.prepareStatement(itemSqlCreation);

            while(iter.hasNext()){
                String values[] = iter.next().split("\t\\|\t");
                T item = supplier.get();
                for(int i = 0; i < headers.length; i++){
                    item.setAttribute(headers[i], values[i]);
                }
                item.addItem(conn, statement);
                items.add(item);
            }

            statement.executeBatch();
        }catch(Exception e){
            System.err.println("An exception has occurred loading the excel file\n"+e.getMessage());
        }
    }

    protected void executeQuery(Connection conn, String query, String baseLog){
        try{
            Statement statement = conn.createStatement();
            statement.execute(query);
        }catch(SQLException e){
            System.out.println(baseLog+"\nSQL state: "+e.getSQLState()+"\nError Code: "+e.getErrorCode()+"\nMessage: "+e.getMessage());
        }
    }
}