package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    //Local variables
    private int userID;
    private String username;
    private String password;
    private String email;
    private Invite[] sentInvites;
    private Invite[] gotInvites;
    private Game[] games;

    //Constructors
    public User(int uID, String username, String password,String email){
        this.userID = uID;
        this.username = username;
        this.password = password;
        this.email = email;
        sentInvites = new Invite[0];
        gotInvites = new Invite[0];
        games = new Game[0];
    }

    //Get and Set

    public void setUserID(int uID){ this.userID = uID; }

    public int getUserID(){ return userID; }

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
        Invite[] tmp = new Invite[this.sentInvites.length + 1];
        for(int i = 0; i < sentInvites.length;i++){
            tmp[i] = sentInvites[i];
        }
        tmp[tmp.length-1] = inv;
        this.sentInvites = tmp;
    }

    public void addInviteGot(Invite inv){
        Invite[] tmp = new Invite[this.gotInvites.length + 1];
        for(int i = 0; i < gotInvites.length;i++){
            tmp[i] = gotInvites[i];
        }
        tmp[tmp.length-1] = inv;
        this.gotInvites = tmp;
    }

    public void addGame(Game g){
        Game[] tmp = new Game[this.games.length + 1];
        for(int i = 0; i < games.length;i++){
            tmp[i] = games[i];
        }
        tmp[tmp.length-1] = g;
        this.games = tmp;
    }


    public void removeInviteSent(int id){

        Invite[] tmp = new Invite[sentInvites.length-1];

        for(int i=0,j=0; i < sentInvites.length;i++){
            if(sentInvites[i].getInviteID() == id){

            }
            else{
                tmp[j] = sentInvites[i];
                j++;
            }
        }
        sentInvites = tmp;
    }

    public void removeInviteGot(int id){
        Invite[] tmp = new Invite[gotInvites.length-1];

        for(int i=0,j=0; i < gotInvites.length;i++){
            if(gotInvites[i].getInviteID() == id){

            }
            else{
                tmp[j] = gotInvites[i];
                j++;
            }
        }
        gotInvites = tmp;
    }

    public void removeGame(Game g){

        Game[] tmp = new Game[games.length-1];

        for(int i=0,j=0; i < games.length-1;i++){
            if(games[i].getGameID() == g.getGameID()){

            }
            else{
                tmp[j] = games[i];
                j++;
            }
        }
        games = tmp;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail() { return email;}

    public Invite[] getSentInvites() { return sentInvites;}

    public Invite[] getGotInvites() { return gotInvites;}

    public Game[] getGames() { return games;}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSentInvites(Invite[] sentInvites) { this.sentInvites = sentInvites; }

    public void setGotInvites(Invite[] gotInvites) { this.gotInvites = gotInvites; }

    public void setGames(Game[] games) { this.games = games; }

}
