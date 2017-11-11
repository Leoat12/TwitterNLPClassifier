package br.unirio.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import br.unirio.models.*;

public class DBConnection{
    private static Connection conn = null;

    //TODO: Colocar aqui a função para escrever o tweet etiquetado no banco de dados. 
    public static void writeToDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://devtestdb.ccj3d9slsftz.us-east-2.rds.amazonaws.com/tweetsdb?" + 
                                                    "user=leoat12&password=2glo1gg4"); 
                                                    
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