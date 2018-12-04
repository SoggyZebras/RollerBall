package edu.colostate.cs.cs414.soggyZebras.rollerball.Database.Database;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.*;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.*;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

public class Database {

    String user = "cntorres";
    String password = "830429517";

    private static Object fromString(String gameState) throws IOException, ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode(gameState);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    private static String toString(Object o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public void insertGame(int id, Game gameState) throws IOException {
        String sql = "INSERT INTO Game (`id`,`GameState`)" +
                "VALUES ("+id + ", \"" + toString(gameState) +"\")";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query


            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {
                query.executeUpdate(sql);
            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
    }

    public Game getGame(int id)   {
        String sql = "Select * FROM Game WHERE `id`=" + id;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query


            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {
                    ResultSet rs = query.executeQuery(sql);
                    rs.next();
                    return (Game)fromString(rs.getString("gameState"));

            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public User getUser(int id) throws SQLException {
        String sql = "Select * FROM user WHERE `id`=" + id;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query


            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {
                ResultSet rs= query.executeQuery(sql);
                rs.next();
                return (User)fromString(rs.getString("user"));
            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<User> getAllUser() throws SQLException {
        String sql = "Select * FROM user";
        ArrayList<User> users = new ArrayList<User>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query


            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {
                ResultSet rs = query.executeQuery(sql);
                while(rs.next()) {
                    users.add((User)fromString(rs.getString("user")));
                }

            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
        return users;


    }

    public void insertUser(int id, User u) throws IOException {
        String sql = "INSERT INTO user (id, user) " +
                "VALUES ("+ id  + ", \"" + toString(u) + "\")";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query


            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {
                query.executeUpdate(sql);
            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void removeUser(int id){
        String sql = "DELETE FROM `user` WHERE `id` = \"" + id + "\"";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query


            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {
                query.executeUpdate(sql);
            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
    }

    public Invite getInvite(String from, String to) throws SQLException {
        String sql = "Select * FROM Invites WHERE `from` =\"" + from+ "\" && `to`=\""+to+"\"";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query


            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {
                ResultSet rs = query.executeQuery(sql);
                rs.next();
                int id = rs.getInt("id");
                return new Invite(to, from, id);

            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public void insertInvite(int id, String from, String to)  {
        String sql = "INSERT INTO Invites (`id`, `from`, `to`) " +
                "VALUES (\'"+ id + "\', \'"+ from + "\', \'" + to + "\')";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query


            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {
                query.executeUpdate(sql);
            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
//        Database d = new Database();
//        d.insertUser(1, new User(1, "tester", "", ""));
//        d.getUser(1);
    }


}
