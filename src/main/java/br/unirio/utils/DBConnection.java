package br.unirio.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.joda.time.DateTime;

import br.unirio.models.*;

public class DBConnection{
    private static Connection conn = null;

    //TODO: Colocar aqui a função para escrever o tweet etiquetado no banco de dados.
    /* TODO: Utilizar este site para fazer a query de inserção
        https://alvinalexander.com/java/java-mysql-insert-example-preparedstatement
    */ 
    public static void writeToDB(Tweet tweet){
        try{
            conn = DriverManager.getConnection(ToolProperties.getInstance().getDbConnectionString()); 
        
                                                    
            String query = "INSERT INTO tweet" + 
                            "(tweetid, createdbyid, body, createdat, latitude, longitude, taggedbody)" + 
                            "values (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pStatement = conn.prepareStatement(query);
            pStatement.setLong(1, tweet.getId());
            pStatement.setLong(2, tweet.getCreatedById());
            pStatement.setString(3, tweet.getText());
            DateTime dt = DateTime.parse(tweet.getCreatedAt());
            Date date = new Date(dt.getMillis());
            pStatement.setDate(4, date);
            pStatement.setDouble(5, tweet.getLatitude());
            pStatement.setDouble(6, tweet.getLongitude());
            pStatement.setString(7, tweet.getTaggedText());
            
            pStatement.execute();

            conn.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

}