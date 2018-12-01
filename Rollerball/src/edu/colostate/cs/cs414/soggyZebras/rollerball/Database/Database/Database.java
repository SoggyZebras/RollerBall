package edu.colostate.cs.cs414.soggyZebras.rollerball.Database.Database;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class Database {

    private ResultSet query(String stmt){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query
            String user = "cntorres";
            String password = "830429517";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {
                query.executeUpdate(stmt);
            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public void insertGame(String p1, String p2, Map<Location, Piece> gameState, String turn, String winner, String loser){
        String sql = "INSERT INTO Game " +
                "VALUES (" + p1 + ", " + p2 +", "+ gameState + ", "  + turn + ", " + winner + ", " + loser+ ")";

        query(sql);
    }

    public void getGame(String p1, String p2){
        String sql = "Select * FROM Game WHERE Player1=" + p1+ ", Player2="+p2;
        query(sql);
    }

    public void getUser(String name, String email){
        String sql = "Select * FROM Game WHERE user=" + name+ ", email="+email;
        query(sql);
    }

    public void insertUser(String name, String password, String email){
        String sql = "INSERT INTO Game (user, password,email)" +
                "VALUES (\"" + name + "\", \"" + password+"\", \""+ email + "\")";

        query(sql);
    }

    public void getInvite(String from, String to){
        String sql = "Select * FROM Game WHERE from=" + from+ ", to="+to;
        query(sql);
    }

    public void insertInvite(String from, String to) throws SQLException {
        String sql = "INSERT INTO Game " +
                "VALUES (" + from + ", " + to + ")";
        query(sql);
    }


    public static void main(String[] args){
        Database d = new Database();
        d.insertUser("name", "password", "email");
    }

}
