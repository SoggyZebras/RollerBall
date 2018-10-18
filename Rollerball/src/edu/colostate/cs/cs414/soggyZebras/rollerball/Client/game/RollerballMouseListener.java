package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RollerballMouseListener implements MouseListener{

    private RollerballPanel panel;

    public RollerballMouseListener(RollerballPanel panel) {
        super();
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       panel.onClick(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
