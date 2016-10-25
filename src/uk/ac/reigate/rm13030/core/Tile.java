package uk.ac.reigate.rm13030.core;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.UUID;

import uk.ac.reigate.rm13030.menus.PreferencesManager;
import uk.ac.reigate.rm13030.utils.SimpleLogger;
import uk.ac.reigate.rm13030.utils.Utils;
import uk.ac.reigate.rm13030.visual.MainScreen;
import uk.ac.reigate.rm13030.utils.SimpleLogger.MessageType;

/**
 * 
 * @author Robbie <http://reigate.ac.uk/>
 *
 */

public class Tile implements Serializable {

	private static final long serialVersionUID = -2154722770800205802L;
	
	private UUID id;
    private Point pt;
    private TileType type;
    private String hexColour;
    private Tile parent;
    private int searchDepth;
    private int gCost, hCost, fCost;
    
    public Tile(Point pt) {
    	this(UUID.randomUUID(), pt, TileType.OPEN, "#FF0000", null, 0, 0, 0);
    }

    public Tile(UUID ID, Point pt, TileType type, String hexColour, Tile parent, int searchDepth, int gCost, int hCost) {
        this.id = ID;
        this.pt = pt;
        this.type = type;
        this.hexColour = hexColour;
        this.parent = parent;
        this.searchDepth = searchDepth;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = gCost+hCost;
    }

    public enum TileType {

        OPEN,
        CLOSED,
        START,
        END,
        OBSTRUCTION;

    }
    
    public boolean isValidDestination() {
    	if (this.getType() != TileType.START) {
    		return false;
    	}
    	return true;
    }

    public static void render(MainScreen screen, Point mouseLoc, boolean isStatic) {
        Tile currentTile = null;
        //Tile lastTile = null;
        /**
         * Logic:
         * 
         * if (currentTile is the same as the lastTile):
         * 		do not re-render the tile
         * else (if currentTile != lastTile):
         * 		lastTile = currentTile;
         * 		re-render.
         * 
         */

        if (mouseLoc == null || Utils.isOutOfBounds(mouseLoc)) {
            //grid bounds = 608x448
            return;
        }

        if (isStatic) {
            currentTile = Application.snapToTile(mouseLoc);
            
            if (currentTile == null)
            	return;
            //if (Application.getStaticTiles().contains(currentTile)) {
            //	System.out.println("not repainting");
            //	return;
            //}

            //if (Application.getStaticTiles().get(currentTile)) {
            //	System.out.println("already drawn");
            //	return;
            //}

            Graphics context = screen.image.getGraphics().create();
            Graphics2D g2 = (Graphics2D) context;

            g2.setFont(new Font("Serif", Font.BOLD, 15));
            //width - metrics.stringWidth(text)) / 2

            g2.setColor(Utils.hexToColor("#000000")); /* Draw the TileType text */
            //g2.drawString(currentTile.getType().toString(), (int) currentTile.getPoint().getX(), (int) currentTile.getPoint().getY() + 14);
            g2.drawString(currentTile.getType().toString(), (int) currentTile.getPoint().getX(), (int) currentTile.getPoint().getY() + 14);
            //System.out.println("painted static tile.");

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            //g2.setColor(Application.getColour(currentTile.getType())); /* Draw the static tile */
            g2.setColor(Utils.hexToColor(Application.getColour((currentTile.getType()))));
            g2.fillRect((int) currentTile.getPoint().getX(), (int) currentTile.getPoint().getY(), Constants.TILE_SIZE, Constants.TILE_SIZE);

            return;
        }

        if (currentTile == null /*&& lastTile == null*/ ) { /* first run */
            currentTile = Application.snapToTile(mouseLoc);
            //lastTile = getTile(new Point((int) currentTile.getPoint().getX() + 32, (int) currentTile.getPoint().getY() + 32));
        }

        currentTile = Application.snapToTile(mouseLoc);

        /* TODO: fix */
        //if (currentTile.getID().equals(lastTile.getID())) {
        //do not re-render
        //	return;
        //}


        //lastTile = currentTile;

        //System.out.println(mouseLoc); /* Debug */

        Graphics context = screen.image.getGraphics().create();
        Graphics2D g2 = (Graphics2D) context;
        //Random rand = new Random();
        //Color[] colours = {Color.CYAN, Color.GREEN, Color.pink, Color.RED, Color.MAGENTA};

        float alpha = 0.5f;
        int type = AlphaComposite.SRC_OVER;
        g2.setComposite(AlphaComposite.getInstance(type, alpha));
        //g2.setColor(colours[rand.nextInt(colours.length-1)]);
        g2.setColor(Utils.hexToColor(Application.getPreferences().getHighlightColour())); /* Highlight Colour */
        if (currentTile == null)
        	return;
        g2.fillRect((int) currentTile.getPoint().getX() /*- Constants.OFFSET_X*/ , (int) currentTile.getPoint().getY() /*- Constants.OFFSET_Y*/ , Constants.TILE_SIZE, Constants.TILE_SIZE);

        //System.out.println(currentTile.getType());
        //System.out.println(Application.getStaticTiles().values());
    }
    
    public TileType getType() {
        return type;
    }
    
    public TileType getTileType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
        //tileMap.get(getIndexByTile(this)).type = type;
        //tileMap.get(getIndexByTile(t)).type = type)); //update type in tileMap ArrayList
    }

    /*public int getIndexByTile(Tile t) {
        int index = -1;
        for (int i = 0; i < tileMap.size(); i++) {
            if (tileMap.get(i) == t) {
                index = i;
            } else {
                continue;
            }
        }
        return index;
    }*/

    public void setPoint(Point pt) {
        this.pt = pt;
    }
    
    public int getGCost() {
		return gCost;
	}

	public void setGCost(int gCost) {
		this.gCost = gCost;
	}

	public int getHCost() {
		return hCost;
	}

	public void setHCost(int hCost) {
		this.hCost = hCost;
	}

	public int getFCost() {
		return fCost;
	}

	public void setFCost(int fCost) {
		this.fCost = fCost;
	}

	public Point getPoint() {
        return pt;
    }

    public UUID getID() {
        return id;
    }

    public UUID getUUID() {
        return id;
    }

	public int getSearchDepth() {
		return searchDepth;
	}

	public void setSearchDepth(int searchDepth) {
		this.searchDepth = searchDepth;
	}

	public Tile getParent() {
		return parent;
	}

	public void setParent(Tile parent) {
		this.parent = parent;
	}
	
}