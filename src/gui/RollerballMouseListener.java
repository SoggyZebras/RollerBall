package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RollerballMouseListener implements MouseListener{

    private GUI gui;

    public RollerballMouseListener(GUI gui) {
        super();
        this.gui = gui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       int x = e.getX();
       int y = e.getY();
       gui.onClick(y / gui.SQUARE_HEIGHT, x / gui.SQUARE_WIDTH);
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
