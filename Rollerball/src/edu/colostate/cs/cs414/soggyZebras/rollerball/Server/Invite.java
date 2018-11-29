package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

public class Invite {

    private User invitee;
    private User inviter;
    private int inviteID;

    public Invite(User invtee, User invtr, int id){
        this.invitee = invtee;
        this.inviter = invtr;
        this.inviteID = id;
    }

    public User getInvitee(){
        return this.invitee;
    }

    public User getInviter(){
        return this.inviter;
    }

    public int getInviteID(){
        return this.inviteID;
    }

}
