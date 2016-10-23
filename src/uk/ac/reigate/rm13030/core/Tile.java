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
    
    private Tile[][] tileMap;
    
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

    public void render(MainScreen screen, Point mouseLoc, boolean isStatic) {
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
            currentTile = snapToTile(mouseLoc);
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

            float alpha = 0.5f;
            int type = AlphaComposite.SRC_OVER;

            g2.setFont(new Font("Serif", Font.BOLD, 15));
            //width - metrics.stringWidth(text)) / 2

            g2.setColor(Utils.hexToColor("#000000")); /* Draw the TileType text */
            //g2.drawString(currentTile.getType().toString(), (int) currentTile.getPoint().getX(), (int) currentTile.getPoint().getY() + 14);
            g2.drawString(currentTile.getType().toString(), (int) currentTile.getPoint().getX(), (int) currentTile.getPoint().getY() + 14);
            //System.out.println("painted static tile.");

            g2.setComposite(AlphaComposite.getInstance(type, alpha));
            //g2.setColor(Application.getColour(currentTile.getType())); /* Draw the static tile */
            g2.setColor(Utils.hexToColor(Application.getColour((currentTile.getType()))));
            g2.fillRect((int) currentTile.getPoint().getX(), (int) currentTile.getPoint().getY(), Constants.TILE_SIZE, Constants.TILE_SIZE);

            return;
        }

        if (currentTile == null /*&& lastTile == null*/ ) { /* first run */
            currentTile = snapToTile(mouseLoc);
            //lastTile = getTile(new Point((int) currentTile.getPoint().getX() + 32, (int) currentTile.getPoint().getY() + 32));
        }

        currentTile = snapToTile(mouseLoc);

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
        g2.fillRect((int) currentTile.getPoint().getX() /*- Constants.OFFSET_X*/ , (int) currentTile.getPoint().getY() /*- Constants.OFFSET_Y*/ , Constants.TILE_SIZE, Constants.TILE_SIZE);

        //System.out.println(currentTile.getType());
        //System.out.println(Application.getStaticTiles().values());
    }

    /**
     * - create a Tile object to represent every tile on the map
     * - each tile gets assigned a unique ID (UUID.randomUUID()) to it
     * - when checking to see if the client has switched tiles (do not re-render), check to see if the UUID of the current hovered tile is the same/different.
     */

    //	snapToTile/getTile
    public Tile snapToTile(Point p) {
        double newX, newY;
    	
    	if (Utils.isOutOfBounds(p)) {
    		return new Tile(new Point(0,0));
    	}

        newX = p.getX() - (p.getX() % Constants.TILE_SIZE) /*+ Constants.OFFSET_X*/ ;
        newY = p.getY() - (p.getY() % Constants.TILE_SIZE) /*+ Constants.OFFSET_Y*/ ;

        Tile t = getTile(new Point((int) newX, (int) newY));

        return t;
    }
    
    public void mapValues() {
    	tileMap = new Tile[19][19];
    	for (int count=0; count<361; count++) {
    		for (int x=0; x<19; x++) {
    			for (int y=0; y<19; y++) {
    				tileMap[x][y] = new Tile(new Point(x*32, y*32));
    				//tileMap[x][y] = new Tile(UUID.randomUUID(), new Point(x*32, y*32), TileType.OPEN, Color.WHITE, -1, -1);
    			}
    		}
    	}
        /*tileMap = new ArrayList < Tile > (361); /* 361 tile total */
        /*for (int i = 0; i < 1024; i += 32) { //19x19 grid (28 * 28 = 784)
            for (int j = 0; j < 1024; j += 32) { //19x19 grid (28 * 28 = 784)
            	//    public Tile(UUID ID, Point pt, TileType type, Color colour, int gCost, int hCost) {
                tileMap.add(new Tile(UUID.randomUUID(), new Point(i, j), TileType.OPEN, Color.WHITE, -1, -1));
                //each tile has a unique identifier (UUID)
            }
        }*/
    }

    public Tile getTile(Point p) {
    	Tile t = null;
    	for (int x=0; x<tileMap.length; x++) {
    		for (int y=0; y<tileMap.length; y++) {
    			if (tileMap[x][y].getPoint().equals(p)) {
    				t = tileMap[x][y];
    			}
    		}
    	}
    	
        /*Tile t = null;
        for (int i = 0; i < tileMap.size(); i++) {
            if (tileMap.get(i).getPoint().equals(p)) {
                t = tileMap.get(i);
            }
        }*/
    	
        //if (t == null) {
        //System.out.println("unable to find Tile for given point :c");
        //}// else {
        //	System.out.println("tile: "+t);
        //}

        return t;
    }

    public Tile[][] getTileMap() {
    	if (tileMap.length == 0) {
    		//System.out.println("tileMap length == 0: mapping values before returning!");
    		SimpleLogger.log(Tile.class, MessageType.INFO, "tileMap length = 0", "mapping values before returning!");
    		mapValues();
    	}
        return tileMap;
    }

    public void setTileMap(Tile[][] tileMap) {
    	this.tileMap = tileMap;
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