package edu.colostate.cs.cs414.soggyZebras.rollerball.Database.Database;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.*;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.*;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class Database {

    private ResultSet query(String stmt, String insert){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query
            String user = "cntorres";
            String password = "830429517";

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {
                if(insert == "insert") {
                    query.executeUpdate(stmt);
                    return null;
                 }
                 else{
                     return query.executeQuery(stmt);
                }
            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public void insertGame(String p1, String p2, Game gameState, String turn, String winner, String loser, Boolean inprogress){
        String sql = "INSERT INTO Game (`Player1`, `Player2`, `GameState`, `Turn`, `Winner`, `Loser`, `InProgress`)" +
                "VALUES (\"" + p1 + "\", \"" + p2 +"\", \""+ gameState + "\", \""  + turn + "\", \"" + winner + "\", \"" + loser+ "\", \""+  inprogress +"\")";
        System.out.println(sql);
        query(sql, "insert");
    }

    public ResultSet getGame(String p1, String p2){
        String sql = "Select * FROM Game WHERE `Player1`=" + p1+ " && `Player2`="+p2;
        return query(sql, "select");
    }

    public ResultSet getUser(String name, String email){
        String sql = "Select * FROM userDatabase WHERE `user`=" + name+ "&& `email`="+email;
        return query(sql, "select");
    }

    public ResultSet getAllUser(String name, String email){
        String sql = "Select * FROM userDatabase";
        return query(sql, "select");
    }

    public void insertUser(int id, String name, String password, String email, TCPConnection userCon, TCPConnection serverCon, ArrayList<Invite> sentInvites, ArrayList<Invite> gotInvites, ArrayList<Game> games){
        String sql = "INSERT INTO userDatabase (id, user, password, email, userCon, serverCon, sentInvites, gotInvites, games) " +
                "VALUES (\""+ id  + "\", \"" + name + "\", \"" + password+"\", \""+ email + "\", \"" +    userCon  +  "\", \"" +   serverCon + "\", \"" +  sentInvites  + "\", \"" +    gotInvites + "\", \"" +    games + "\")";
        query(sql, "insert");
    }

    public ResultSet getInvite(String from, String to){
        String sql = "Select * FROM Invites WHERE `from` =\"" + from+ "\" && `to`=\""+to+"\"";
        return query(sql, "select");
    }

    public void insertInvite(int id, String from, String to)  {
        String sql = "INSERT INTO Invites (`id`, `from`, `to`) " +
                "VALUES (\'"+ id + "\', \'"+ from + "\', \'" + to + "\')";
        query(sql, "insert");
    }


}
