package edu.colostate.cs.cs414.soggyZebras.rollerball.Database.Database;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query
            String user = "cntorres";
            String password = "830429517";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement stCount = conn.createStatement();
                 Statement stQuery = conn.createStatement();
                 ResultSet rs = stCount.executeQuery("select * from userDatabase");
            ) {
                System.out.println(rs.getString("user"));
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        Database d = new Database();
        d.connect();
    }

}
