package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;

public class User {

    //Local variables
    private int userID;
    private TCPConnection userConnection;

    //Constructors
    public User(int uID, TCPConnection con){
        this.userID = uID;
        this.userConnection = con;
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
}
