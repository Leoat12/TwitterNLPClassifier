package br.unirio.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.unirio.models.*;

public class DBConnection{
    private static Connection conn = null;

    //TODO: Colocar aqui a função para escrever o tweet etiquetado no banco de dados.
    /* TODO: Utilizar este site para fazer a query de inserção
        https://alvinalexander.com/java/java-mysql-insert-example-preparedstatement
    */ 
    public static void writeToDB(Tweet tweet){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //jdbc:mysql://devtestdb.ccj3d9slsftz.us-east-2.rds.amazonaws.com/tweetsdb?user=leoat12&password=2glo1gg4
            conn = DriverManager.getConnection(ToolProperties.getInstance().getDbConnectionString()); 
                                                    
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from tweet where id = 1");
            
            while(rs.next()){
                System.out.println(rs.getString("body"));
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

}