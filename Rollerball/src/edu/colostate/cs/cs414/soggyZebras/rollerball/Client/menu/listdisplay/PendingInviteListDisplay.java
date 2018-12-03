package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.listdisplay;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Invite;

import java.util.Objects;

/**
 * a wrapper class used to display an invite in the list of pending invites
 */
public class PendingInviteListDisplay {
    public Invite invite;

    public PendingInviteListDisplay(Invite invite) {
        this.invite = invite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PendingInviteListDisplay that = (PendingInviteListDisplay) o;
        return invite.getInviteID() == that.invite.getInviteID();
    }

    @Override
    public int hashCode() {
        return invite.getInviteID();
    }

    @Override
    public String toString() {
        return "Invite from: " + invite.getInviter();
    }
}
