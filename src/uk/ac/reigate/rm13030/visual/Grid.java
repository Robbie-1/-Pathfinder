package uk.ac.reigate.rm13030.visual;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;

import uk.ac.reigate.rm13030.utils.SimpleLogger;
import uk.ac.reigate.rm13030.utils.SimpleLogger.MessageType;

/**
 * 
 * @author Robbie <http://reigate.ac.uk/>
 *
 */

public class Grid extends JLabel implements MouseListener, MouseMotionListener {


	private static final long serialVersionUID = 7502520018478040429L;

    private static Bitmap grid;

    public Grid() {
        //grid = Bitmap.load("/png_grid_area.png");
        grid = Bitmap.load("/map_v1_img.png");
    }

    public void render(MainScreen screen) {
        if (grid == null) {
            //System.out.println("Please initialise the Grid bitmap before attempting to render it!");
        	SimpleLogger.log(Grid.class, MessageType.ERROR, "Please initialise the Grid bitmap before attempting to render it!");
            return;
        }
        screen.render(grid, 0, 0);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    	
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}