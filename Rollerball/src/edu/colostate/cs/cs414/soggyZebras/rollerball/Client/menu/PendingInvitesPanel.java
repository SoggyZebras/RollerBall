package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

public class PendingInvitesPanel extends MenuPanel {

    public PendingInvitesPanel(MenuGUI menuGUI) {
        super("pending_invites", menuGUI);
        // TODO: show list of games player has been invited to, maybe invites they've sent too
        add(createLinkedButton("Back", "main_menu"));
    }
}
