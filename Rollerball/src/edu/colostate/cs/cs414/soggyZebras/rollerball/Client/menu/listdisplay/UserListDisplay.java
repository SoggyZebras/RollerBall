package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.listdisplay;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Invite;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

/**
 * a wrapper class used to display an user in the list of available users
 */
public class UserListDisplay {
    public User user;

    public UserListDisplay(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserListDisplay that = (UserListDisplay) o;
        return user.getUserID() == that.user.getUserID();
    }

    @Override
    public int hashCode() {
        return user.getUserID();
    }

    @Override
    public String toString() {
        return user.getUsername();
    }
}
