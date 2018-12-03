package edu.colostate.cs.cs414.soggyZebras.rollerball.Database.Database;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.*;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.*;
import java.sql.*;
import java.util.ArrayList;

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

    public Game getGame(String p1, String p2) throws SQLException {
        String sql = "Select * FROM Game WHERE `Player1`=" + p1+ " && `Player2`="+p2;
        ResultSet rs= query(sql, "select");
        rs.next();
        int id = rs.getInt("id");
        return new Game(id, getUser(p1), getUser(p2));
    }

    public User getUser(String name) throws SQLException {
        String sql = "Select * FROM user WHERE `user`=\"" + name+ "\"";
        ResultSet rs= query(sql, "select");
        rs.next();
        int id = rs.getInt("id");
        String username = rs.getString("name");
        String password = rs.getString("password");
        String e = rs.getString("email");
        return new User(id, username, password, e);
    }

    public ArrayList<User> getAllUser() throws SQLException {
        String sql = "Select * FROM user";
        ArrayList<User> users = new ArrayList<User>();
        ResultSet rs= query(sql, "select");
        while(rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("name");
            String password = rs.getString("password");
            String e = rs.getString("email");
            users.add(new User(id, username, password, e));
        }
        return users;
    }

    public void insertUser(int id, String name, String password, String email, Invite[] sentInvites, Invite[] gotInvites, Game[] games){
        String sql = "INSERT INTO user (id, user, password, email, sentInvites, gotInvites, games) " +
                "VALUES ("+ id  + ", \"" + name + "\", \"" + password+"\", \""+ email + "\", \""  +  sentInvites  + "\", \"" +    gotInvites + "\", \"" +    games + "\")";
        query(sql, "insert");
    }

    public void removeUser(int id){
        String sql = "DELETE FROM `user` WHERE `id` = \"" + id + "\"";
        query(sql, "insert");
    }
    
    public Invite getInvite(String from, String to) throws SQLException {
        String sql = "Select * FROM Invites WHERE `from` =\"" + from+ "\" && `to`=\""+to+"\"";
        ResultSet rs= query(sql, "select");
        rs.next();
        int id = rs.getInt("id");
        return new Invite(to, from, id);
    }

    public void insertInvite(int id, String from, String to)  {
        String sql = "INSERT INTO Invites (`id`, `from`, `to`) " +
                "VALUES (\'"+ id + "\', \'"+ from + "\', \'" + to + "\')";
        query(sql, "insert");
    }

    public static void main(String[] args){
    }


}
