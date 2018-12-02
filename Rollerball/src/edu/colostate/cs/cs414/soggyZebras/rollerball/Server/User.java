package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;

import java.util.ArrayList;

public class User {

    //Local variables
    private int userID;
    private String username;
    private String password;
    private String email;
    private TCPConnection userConnection;
    private ArrayList<Invite> sentInvites;
    private ArrayList<Invite> gotInvites;
    private ArrayList<Game> games;

    //Constructors
    public User(int uID, String username, String password,String email ,TCPConnection con){
        this.userID = uID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userConnection = con;
        sentInvites = new ArrayList<>();
        gotInvites = new ArrayList<>();
        games = new ArrayList<>();
    }

    //Get and Set

    public void setUserID(int uID){
        this.userID = uID;
    }

    public int getUserID(){
        return userID;
    }

    public void setUserConnection(TCPConnection con){
        this.userConnection = con;
    }

    public TCPConnection getUserConnection(){
        return userConnection;
    }

    //Wrapper wrapper for TCPConnection send method
    public void sendData(String data){
        this.userConnection.sendData(data);
    }

    public boolean containsInviteID(int id){
        for(Invite i : gotInvites){
            if(i.getInviteID() == id){
                return true;
            }
        }

        for(Invite i : sentInvites){
            if(i.getInviteID() == id){
                return true;
            }
        }

        return false;
    }

    public void addInviteSent(Invite inv){
        this.sentInvites.add(inv);
    }

    public void addInviteGot(Invite inv){
        this.gotInvites.add(inv);
    }

    public void removeInviteSent(int id){
        for(Invite i : sentInvites){
            if(i.getInviteID() == id){
                sentInvites.remove(i);
            }
        }
    }

    public void removeInviteGot(int id){
        for (Invite i : gotInvites){
            if(i.getInviteID() == id){
                gotInvites.remove(i);
            }
        }
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail() { return email;}

    public ArrayList<Invite> getSentInvites() { return sentInvites;}

    public ArrayList<Invite> getGotInvites() { return gotInvites;}

    public ArrayList<Game> getGames() { return games;}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSentInvites(ArrayList<Invite> sentInvites) {
        this.sentInvites = sentInvites;
    }

    public void setGotInvites(ArrayList<Invite> gotInvites) {
        this.gotInvites = gotInvites;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }
}
