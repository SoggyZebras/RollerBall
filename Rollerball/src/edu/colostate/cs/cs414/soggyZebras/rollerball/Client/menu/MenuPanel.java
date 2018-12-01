package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * base class for all menus
 */
public abstract class MenuPanel extends JPanel {
    private String menuName;
    private MenuGUI menuGUI;

    public MenuPanel(String menuName, MenuGUI menuGUI) {
        this.menuName = menuName;
        this.menuGUI = menuGUI;
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        revalidate();
        repaint();
        //setLayout(layout);
    }

    /**
     * create a button to go to the specified menu
     * @param menu a String that holds the identifier of the menu to go to when button is clicked
     * @param text a String that holds the text in the button
     * @return a button that will take the user to the menu identified by menu
     */
    public JButton createLinkedButton(String text, String menu) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGUI.setMenu(menu);
            }
        });
        return button;
    }

    /**
     * create a button that does some action
     * @param listener an ActionListener that defines what to do when the button is clicked
     * @param text a String that holds the text in the button
     * @return a button that will cause some action to be performed
     */
    public JButton createLinkedActionButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }

    /**
     * clear all of the text fields in this menu panel
     */
    public void clearTextFields() {
        for (Component comp : getComponents()) {
            if (comp instanceof TextField) {
                ((TextField) comp).setText("");
            }
        }
    }

    public String getMenuName() {
        return menuName;
    }

    public MenuGUI getMenuGUI() {
        return menuGUI;
    }
}
