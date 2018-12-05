package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;

import java.net.Socket;
import java.util.ArrayList;

public class TCPServerCache {

    private ArrayList<User> cache;
    private ArrayList<TCPConnection> connections;

    public TCPServerCache(){
        //initializes the arraylist
        this.cache = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public boolean UserLoggedIn(int id){
        for(TCPConnection c: connections){
            if(c.getConID() == id){
                return true;
            }
        }
        return false;
    }

    public void addUser(User u) {
        //add TCPConnection to the cache
        this.cache.add(u);
    }

    public TCPConnection getConnection(int id){
        for(TCPConnection c : connections){
            if(c.getConID() == id){
                return c;
            }
        }
        return null;
    }

    public boolean containsConID(int id){
        for(TCPConnection c : connections){
            if(c.getConID() == id){
                return true;
            }
        }
        return false;
    }

    public boolean containsUserID(int id){
        for(User u : getAllUsers()){
            if(u.getUserID() == id){
                return true;
            }
        }
        return false;
    }

    public boolean matchPassword(String username, String pass){
        User tmp = getUser(username);
        if(tmp != null){
            System.out.println(pass);
            System.out.println(tmp.getPassword());
            if(tmp.getPassword().equals(pass)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param s
     * @return TCPConnection
     */
    public User getUser(Socket s) {
        //access TCPConnection from the cache
        for(User u: getAllUsers()){
            TCPConnection t = getConnection(u.getUserID());
            if(t != null) {
                if (getConnection(u.getUserID()).getSocket() == s) {
                    return u;
                }
            }
        }
        return null;
    }

    public TCPConnection getUserCon(Socket s){
        //access TCPConnection from the cache
        for(TCPConnection c : connections){
            if(c.getSocket() == s){
                return c;
            }
        }

        return null;
    }

    public User getUser(int i){
        for(User u : getAllUsers()){
            if(u.getUserID() == i){
                return u;
            }
        }
        return null;
    }

    public User getUser(String s){
        for(User u : getAllUsers()){
            if(u.getUsername().equals(s)){
                return u;
            }
        }
        return null;
    }

    public void addConnection(TCPConnection c){
        connections.add(c);
    }

    public void removeConnection(int id){
        for(TCPConnection c: connections){
            if(c.getConID() == id){
                connections.remove(c);
                break;
            }
        }
    }

    public void removeUser(int id){
        for(User u : getAllUsers()){
            if(u.getUserID() == id){
                cache.remove(u);
                break;
            }
        }
    }

    public ArrayList<User> getAllUsers(){
        return cache;
    }

    public synchronized void setCache(ArrayList<User> c){this.cache = c;}

}
