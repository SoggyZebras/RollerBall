package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Client;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest {
    private MenuGUI menuGUI;
    CardContainer mainMenu;

    @BeforeEach
    void setUp() {
        menuGUI = new MenuGUI("127.0.0.1");
        mainMenu = menuGUI.getCardContainer();
    }

    @Test
    void testMenuStartsGame() {
        RegLoginPanel regLoginPanel = (RegLoginPanel) mainMenu.getComponent(0);
        JButton regButton = (JButton) regLoginPanel.getComponents()[0];
        regButton.doClick();
        RegPanel regPanel = (RegPanel) mainMenu.getComponent(1);
        ((JButton)regPanel.getComponent(3)).doClick();
    }
}