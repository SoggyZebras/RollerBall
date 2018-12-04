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

    private static Object fromString( String gameState ) throws IOException ,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode(gameState);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    private static String toString( Game o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public void insertGame(String p1, String p2, Game gameState, String turn, String winner, String loser, Boolean inprogress) throws IOException {
        String sql = "INSERT INTO Game (`Player1`, `Player2`, `GameState`, `Turn`, `Winner`, `Loser`, `InProgress`)" +
                "VALUES (\"" + p1 + "\", \"" + p2 +"\", \""+ toString(gameState) + "\", \""  + turn + "\", \"" + winner + "\", \"" + loser+ "\", \""+  inprogress +"\")";
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

    public Game getGame(String p1, String p2) throws SQLException {
        String sql = "Select * FROM Game WHERE `Player1`=" + p1+ " && `Player2`="+p2;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query


            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {

                    ResultSet rs = query.executeQuery(sql);
                    rs.next();
                    int id = rs.getInt("id");
                    return new Game(id, getUser(p1), getUser(p2));

            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public User getUser(String name) throws SQLException {
        String sql = "Select * FROM user WHERE `user`=\"" + name+ "\"";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            // connect to the database and query


            try (Connection conn = DriverManager.getConnection("jdbc:mysql://faure.cs.colostate.edu/cntorres", user, password);
                 Statement query = conn.createStatement();
            ) {
                ResultSet rs= query.executeQuery(sql);
                rs.next();
                int id = rs.getInt("id");
                String username = rs.getString("name");
                String password = rs.getString("password");
                String e = rs.getString("email");
                return new User(id, username, password, e);
            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<User> getAllUser(String name, String email) throws SQLException {
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
                    int id = rs.getInt("id");
                    String username = rs.getString("name");
                    String password = rs.getString("password");
                    String e = rs.getString("email");
                    users.add(new User(id, username, password, e));
                }

            }
        } catch (Exception e) {

            System.err.println("Exception: " + e.getMessage());
        }
        return users;


    }

    public void insertUser(int id, String name, String password, String email, Array sentInvites, Array gotInvites, Array games){
        String sql = "INSERT INTO user (id, user, password, email, sentInvites, gotInvites, games) " +
                "VALUES ("+ id  + ", \"" + name + "\", \"" + password+"\", \""+ email + "\", \""  +  sentInvites  + "\", \"" +    gotInvites + "\", \"" +    games + "\")";
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

    public static void main(String[] args) throws IOException {
        Database d = new Database();
        d.insertGame("test", "test", new Game(1, new User(1, "test", "test", "test", null), new User(1, "test", "test", "test", null)), null, null, null, true);
    }


}
