package edu.colostate.cs.cs414.soggyZebras.rollerball.Driver;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.MainMenuPanel;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.MenuGUI;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.PendingInvitesPanel;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.RegPanel;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class BypassMenuStartGameDriver {
    MenuGUI player1GUI;
    MenuGUI player2GUI;
    private String player1Username = "bypass_player_1";
    private String player2Username = "bypass_player_2";

    public BypassMenuStartGameDriver() {
        player1GUI = new MenuGUI();
        player2GUI = new MenuGUI();
    }

    public void run() {
        setupGame();
    }

    private void setupGame() {
        try {
            player1Username = "test_player_1";
            player1GUI.client.register(player1Username, "password", "email@email.com");
            player2GUI.client.register(player2Username, "password", "email@email.com");

            player1GUI.client.sendInvite(player2Username);
            PendingInvitesPanel invitesPanel = (PendingInvitesPanel) player2GUI.getCardContainer().menuPanels.get("pending_invites");
            int inviteID = -1;
            // loop until appears
            while (inviteID == -1) {
                inviteID = invitesPanel.getFirstInviteID();
            }
            player2GUI.client.respondInvite(player1Username, inviteID);

            // open game windows
            loopUntilCanOpenGame(player1GUI);
            loopUntilCanOpenGame(player2GUI);

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private void loopUntilCanOpenGame(MenuGUI playerGUI) {
        // loop until game appears
        MainMenuPanel menu1 = (MainMenuPanel) playerGUI.getCardContainer().menuPanels.get("main_menu");

        int gameID = -1;
        while (gameID == -1) {
            gameID = menu1.getFirstGameID();
        }
        System.err.println(gameID);
        playerGUI.openGameGUI(gameID);
    }

    public static void main(String[] args) {
        new BypassMenuStartGameDriver().run();
    }
}
