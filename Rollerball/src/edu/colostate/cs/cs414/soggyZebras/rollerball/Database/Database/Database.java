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

    /**
     * Converts serialized string to an object
     * @param gameState
     * @return Object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static Object fromString(String gameState) throws IOException, ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode(gameState);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * Converts object to serialized string
     * @param o
     * @return
     * @throws IOException
     */
    private static String toString(Object o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * Inserts a serialized game into the database
     * @param id
     * @param gameState
     * @throws IOException
     */
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

    /**
     * Gets a game
     * @param id
     * @return Game
     */
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

    /**
     * Gets a user
     * @param id
     * @return User
     * @throws SQLException
     */
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

    /**
     * Gets all the users
     * @return User
     * @throws SQLException
     */
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

    /**
     * Inserts a user into the database
     * @param id
     * @param u
     * @throws IOException
     */
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

    /**
     * Unregisters a user
     * @param id
     */
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

    /**
     * Gets the invite for user
     * @param
     * @param id
     * @return Invite
     * @throws SQLException
     */
    public Invite getInvite(int id) throws SQLException {
        String sql = "Select * FROM Invites WHERE `id` =" + id;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query


            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {
                ResultSet rs = query.executeQuery(sql);
                rs.next();
                String to = rs.getString("to");
                String from = rs.getString("from");
                return new Invite(to, from, id);

            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    /**
     * Inserts an invite into the database
     * @param id
     * @param from
     * @param to
     */
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
    }


}
