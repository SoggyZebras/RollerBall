package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

public class Invite {

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
