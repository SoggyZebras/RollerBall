package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

import java.io.Serializable;

public class Invite implements Serializable{
    private static final long serialVersionUID = 6529098456275732690L;
    private String invitee;
    private String inviter;
    private int inviteID;
    private boolean accepted;

    public Invite(String invtee, String invtr, int id){
        this.invitee = invtee;
        this.inviter = invtr;
        this.inviteID = id;
    }

    public String getInvitee(){
        return this.invitee;
    }

    public String getInviter(){
        return this.inviter;
    }

    public int getInviteID(){
        return this.inviteID;
    }

}
