package uk.ac.reigate.rm13030.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import uk.ac.reigate.rm13030.algorithm.AStarAlgorithm;
import uk.ac.reigate.rm13030.algorithm.AStarHeuristic;
import uk.ac.reigate.rm13030.core.Tile.TileType;
import uk.ac.reigate.rm13030.menus.About;
import uk.ac.reigate.rm13030.menus.Grid;
import uk.ac.reigate.rm13030.menus.MainScreen;
import uk.ac.reigate.rm13030.menus.PreferencesManager;
import uk.ac.reigate.rm13030.storage.LocalStorage;
import uk.ac.reigate.rm13030.utils.Utils;

import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.JProgressBar;
import javax.swing.JCheckBox;

public class Application extends Canvas implements Runnable, MouseListener, MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 5987426229634579399L;
	
	private static JFrame frame;
    private boolean running = false;
    
    public static boolean createNew;
    private static LocalStorage localStorage;
    
    private boolean renderDebug;
    private int lastFps = -1;
    
    private static String status;

    private static Application instance;
    private MainScreen screen;
    private static Grid grid;
    private static Preferences preferences;
    private static Tile mousePressed, mouseReleased;
    private static boolean isDragging;
    private static Tile tile_instance;
    private static BidiMap<Tile, Tile.TileType> staticTiles;

    /**
     * Static tiles are non-highlight tiles that are permanently rendered onto the grid (e.g. as Start/End Tiles, or Obstruction Tiles)
     * 
     * TODO:
     * 	-> Fix static tile re-rendering (for the same tile)
     */

    public Application() {
        setBackground(Color.BLACK);
    }

    /**
     * @wbp.parser.entryPoint
     */
    public static void main(String[] args) {
    	System.out.println(Paths.get(System.getProperty("user.home")).getFileName());
    	
        grid = new Grid();
        
        tile_instance = new Tile(new Point(-1,-1));
        tile_instance.mapValues();
        
        localStorage = new LocalStorage();
        preferences = retrievePreferences();
        
        status = "Please place the START tile.";
        
        //AStarAlgorithm ASA = new AStarAlgorithm(tile_instance.getTileMap(), 500, true);

        instance = new Application();
        instance.setSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));

        frame = new JFrame(Constants.NAME);
        addMenuBar();
        frame.getContentPane().add(instance, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setMinimumSize(new Dimension(frame.getWidth(), 448 + 62)); //grid bounds = 608x448, +62 = frame height offset (menubar, title)
        frame.setVisible(true);
        
        instance.start();
    }

	/* TODO: this */
    private static Preferences retrievePreferences() {
    	/**
			TODO:
    	 * Load options from file.
    	 */
    	// if (!file.exists()) {
    	//		temp.setDefaults();
    	//	}
    	//temp = new Preferences(UIManager.getLookAndFeel(), Color.BLUE, Color.GREEN, Color.RED, Color.DARK_GRAY, true);
    	//temp = new Preferences(UIManager.getLookAndFeel(), "#0000FF", "#00FF00", "#FF0000", "#404040", true);
    	
    	//temp = new Preferences(UIManager.getLookAndFeel(), "#0000FF", "#00FF00", "#FF0000", "#404040", true);
    	
    	//return temp;
    	preferences = localStorage.readPreferences();
    	
    	//set defaults
    	preferences.getThemeMap().put("Set theme", preferences.getUITheme());
        
    	preferences.getColourMap().put("Highlight colour", preferences.getHighlightColour());
    	preferences.getColourMap().put("Start node colour", preferences.getStartNodeColour());
    	preferences.getColourMap().put("End node colour", preferences.getEndNodeColour());
    	preferences.getColourMap().put("Obstruction colour", preferences.getObstructionColour());
        
    	preferences.getDraggingMap().put("Snap to Tile", preferences.isSnapToTile());
        
    	//System.out.println(prefs.getHighlightColour());
    	return preferences;
    }

    private static void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu viewMenu = new JMenu("View");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.setActionCommand("New");

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new ActionListener() {
        	@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
        		ArrayList<Object> objects = localStorage.getObjects(localStorage.readBytes());
        		
        		Tile[][] tileMap = (Tile[][]) objects.get(0);
        		BidiMap<Tile, Tile.TileType> statics = (BidiMap<Tile, TileType>) objects.get(1);
        		Preferences prefs = (Preferences) objects.get(2);
        		
        		/**
        		 * Node colours are retrieved through the Preferences file, NOT the tile object itself.
        		 */
        		
        		//debug
        		System.out.println("Tile [0][1] type: "+tileMap[0][1].getTileType());
        		System.out.println("static tiles: "+statics.toString());
        		System.out.println("Highlight node colour: "+preferences.getHighlightColour());
        		
        		//System.out.println("End node colour: "+statics.getKey(TileType.END).getColour());
        	}
        });
        openMenuItem.setMnemonic(KeyEvent.VK_O);
        openMenuItem.setActionCommand("Open");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		ArrayList<Object> toStore = new ArrayList<Object>();
        		
        		toStore.add(tile_instance.getTileMap());
        		toStore.add(staticTiles);
        		toStore.add(preferences);
        		
        		localStorage.store(toStore);
        	}
        });
        saveMenuItem.setMnemonic(KeyEvent.VK_S);
        saveMenuItem.setActionCommand("Save");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        exitMenuItem.setActionCommand("Exit");

        JMenuItem cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.setMnemonic(KeyEvent.VK_X);
        cutMenuItem.setActionCommand("Cut");

        JMenuItem copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.setMnemonic(KeyEvent.VK_C);
        copyMenuItem.setActionCommand("Copy");

        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        pasteMenuItem.setMnemonic(KeyEvent.VK_V);
        pasteMenuItem.setActionCommand("Paste");

        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new About().setVisible(true);
            }
        });
        aboutMenuItem.setActionCommand("About");

        JMenuItem settingsItem = new JMenuItem("Options");
        settingsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new PreferencesManager().setVisible(true);
            }
        });

        //add menu items to menus
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        helpMenu.add(aboutMenuItem);

        viewMenu.add(settingsItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);
    }

    private void init() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        staticTiles = new DualHashBidiMap<Tile, Tile.TileType>();

        screen = new MainScreen(Constants.WIDTH, Constants.HEIGHT);
        //input = new InputHandler(this);
        //DynamicsLoader.init(this, input);
        //level = Level.levels.get(floor);
        //audioPlayer = new AudioPlayer();
        //audioPlayer.startBackgroundMusic(floor);
        //UI.track(level.player);
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        init();
        int fps = 0;
        int tick = 0;
        double fpsTimer = System.currentTimeMillis();
        double nsPerTick = 1000000000.0 / 60;
        double then = System.nanoTime();
        double unp = 0;
        while (running) {
            double now = System.nanoTime();
            unp += (now - then) / nsPerTick;
            then = now;
            while (unp >= 1) {
                tick++;
                tick();
                --unp;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fps++;
            render();
            if (System.currentTimeMillis() - fpsTimer > 1000) {
                //System.out.printf("%d fps, %d tick%n", fps, tick);
                lastFps = fps;
                fps = 0;
                tick = 0;
                fpsTimer += 1000;
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            requestFocus();
            return;
        }
        screen.clear(0);

        Graphics g = bs.getDrawGraphics();

        /* Grid */
        grid.render(screen);
        /* End of Grid */

        Point currentMouseLoc = this.getMousePosition();

        //if (currentMouseLoc == null) {
        //    return;
        //}

        /* Tile Highlighting */
        tile_instance.render(screen, currentMouseLoc, false); //false since the tile we're painting is NOT static : (we're painting the highlight tile)
        //System.out.println("Tile type: "+tile_instance.getTile(tile_instance.snapToTile(currentMouseLoc).getPoint()).getType());
        /* End of Tile Highlighting */

        /* Static Tiles */
        for (MapIterator<Tile, TileType> it = staticTiles.mapIterator(); it.hasNext();) {
            Tile obj = it.next();
            if (canPlace(tile_instance.snapToTile(currentMouseLoc))) {
            	tile_instance.render(screen, obj.getPoint(), true);
                //Tile.render(screen, obj.getPoint(), true);
                //remove the current element from the iterator and the list.        
                //it.remove();
            } else {
                return;
                //System.out.println("you cannot place a tile here!");
            }
        }
        /*if (canPlace(Tile.snapToTile(currentMouseLoc))) {
        	for (Tile t : staticTiles.keySet()) {
        		Tile.render(screen, t.getPoint(), true);
        	}
        } else {
        	//System.out.println("You cannot place a tile here!");
        	return;
        }*/
        /* End of Static Tiles */



        //if (renderDebug) {
        //	Tile.paintDebug(screen);
        //}

        //Random r = new Random();
        //Color[] colourArray = {Color.MAGENTA, Color.CYAN, Color.BLACK, Color.BLUE, Color.GRAY, Color.GREEN};
        //g.setColor(colourArray[r.nextInt(colourArray.length-1)]);
        //g.setColor(Color.GRAY);
        //g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        /* Information Box */
        g.setColor(Color.black);
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        /*if (staticTiles.size() >= 3) {
            g.setColor(Color.gray);
            g.drawString("\nSTART: " + staticTiles.getKey(TileType.START).getPoint(), 250, 470);
            g.drawString("END: " + staticTiles.getKey(TileType.END).getPoint(), 250, 485);
            g.drawString("OBSTRUCTION: " + staticTiles.getKey(TileType.OBSTRUCTION).getPoint(), 250, 500);

            Point p1 = staticTiles.getKey(TileType.END).getPoint();
            Point p2 = staticTiles.getKey(TileType.OBSTRUCTION).getPoint();
            double distance = Point.distance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            g.setColor(Color.GREEN);
            g.drawString("--> Distance between end and obstruction: " + Math.round(distance), 250, 520);
        }*/
        
        //g.drawString(staticTiles.size() == 0 ? "No static tiles have been placed." : String.valueOf(staticTiles.getKey(Tile.TileType.START).getPoint()), 250, 500);
        /* Information Box */


        g.drawImage(screen.image, 0, 0, Constants.WIDTH, Constants.HEIGHT, null);
        
        //System.out.println("isDragging="+isDragging);
        if (isDragging == true) {
            Point p1 = mousePressed.getPoint();//Tile.snapToTile(mousePressed.getPoint()).getPoint();
            Point p2 = (Boolean) preferences.getDraggingMap().get("Snap to Tile") == true ? tile_instance.snapToTile(currentMouseLoc).getPoint() : currentMouseLoc;//Tile.snapToTile(currentMouseLoc).getPoint(); //current mouse loc
            
            g.setColor(Color.red);
            if (!Utils.isOutOfBounds(p2)) {
            	g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
            }
        }
        
        g.setColor(Utils.hexToColor("#00ff00"));
        g.drawString("Status: "+getStatus(), 75, 500);

        
        if (renderDebug) {
            g.setFont(new Font("Bookman Old Style", Font.PLAIN, 32));
            g.setColor(Utils.hexToColor("#0fffff"));
        	g.drawString("FPS: "+lastFps, 465, 470);
        	
        	if (Utils.isOutOfBounds(currentMouseLoc)) {
        		return;
        	}
            g.setFont(new Font("Systema", Font.BOLD, 10));
        	g.setColor(Utils.hexToColor("#9e0000"));
        	g.drawString("("+(int)currentMouseLoc.getX()+","+(int)currentMouseLoc.getY()+")", (int)currentMouseLoc.getX(), (int)currentMouseLoc.getY());
        }
        
        g.dispose();
        bs.show();
    }

    private boolean canPlace(Tile tile) {

        if (isDragging) {
            return true;
        }

        //System.out.println("Tile type@@@: " + tile.getType());
        if (tile.getType().equals(TileType.OPEN)) {
            //can place start tile
            return true;
        } else if (tile.getType().equals(TileType.START) && staticTiles.containsKey(tile)) {
            //start tile already placed.
            return false;
        } else if (tile.getType().equals(TileType.END) && staticTiles.containsKey(tile)) {
            //end tile already placed.
            return false;
        } else if (tile.getType().equals(TileType.OBSTRUCTION)) {
            return true;
        }
        return true;
    }

    public static String getColour(TileType type) {
        switch (type) {

            case OPEN:
            	return "#000000";

            case START:
            	return preferences.getStartNodeColour();
            	//return (String) preferences.getColourPreferencesMap().get("Start node colour");
            	//return "#00FF00";

            case END:
            	return preferences.getEndNodeColour();
            	//return (String) preferences.getColourPreferencesMap().get("End node colour");
            	//return "#FF0000";

            case OBSTRUCTION:
            	return preferences.getObstructionColour();
            	//return (String) preferences.getColourPreferencesMap().get("Obstruction colour");
            	//return "#404040";

            default:
            	return preferences.getHighlightColour();
            	//return (String) preferences.getColourPreferencesMap().get("Highlight colour");
            	//return "#0000FF"; //Highlight Colour

        }
    }

    private TileType findType() {
    	//    private static BidiMap<Tile, Tile.TileType> staticTiles;
    	if (staticTiles.size() == 0) {
    		return TileType.START;
    	}
    	
    	if (staticTiles.containsValue(TileType.START) && !staticTiles.containsValue(TileType.END)) {
    		return TileType.END;
    	} else if (staticTiles.containsValue(TileType.END)) {
    		return TileType.OBSTRUCTION;
    	} else {
    		return TileType.OBSTRUCTION;
    	}
    		
        /*TileType type;
        switch (staticTiles.size()) {
            case 0:
                type = TileType.START;
                break;

            case 1:
                type = TileType.END;
                break;

            default:
                type = TileType.OBSTRUCTION;
                break;
        }
        return type;*/
    }

    /**
     * Dragging Logic
     * -> Click and drag tile you wish to drag
     * -> Release mouse on tile you wish to drag to (ensure this new tile is not a static (start/end/obstruction) tile)
     * -> set the co-ordinates (Point) of this tile to its new coordinates
     */

    private void handleDrag(MouseEvent drag) {

        /*if (!isDragging) {
            System.out.println("\nMouse Pressed at: " + mousePressed.getPoint() + "\nMouse Released at: " + mouseReleased.getPoint());

            mouseReleased.setPoint(Tile.snapToTile(mousePressed.getPoint()).getPoint());
            mouseReleased.setType(mousePressed.getType());

            mousePressed.setType(TileType.REGULAR);
        } else {
            System.out.println("You are currently dragging a tile...");
        }*/
        /*Tile startingTile = mousePressed; //original tile location
			Tile finishingTile = mouseReleased;
			TileType startingType = startingTile.getType();
		
			System.out.println("\ntile old point: "+startingTile.getPoint()+"\ntile old type: "+startingTile.getType());
			finishingTile.setPoint(Tile.snapToTile(drag.getPoint()).getPoint());
			//t.setType(oldType);
			finishingTile.setType(finishingTile, startingType);
			//startingTile.setType(startingTile, TileType.REGULAR);
			System.out.println("tile new point: "+finishingTile.getPoint()+"\ntile new type: "+finishingTile.getType());
			System.out.println("new tile type@@: "+finishingTile.getType());*/

        //if (newTile.getType() == TileType.REGULAR) {
        //newTile.setType(originalTile.getType());
        //originalTile.setPoint(newTile.getPoint());
        //}

        //System.out.println(originalTile.getPoint()+" -> "+newTile.getPoint());
    }

    private void handleClick(MouseEvent click) {
    	//lastTile = Tile.snapToTile(click.getPoint());
        Tile t = tile_instance.snapToTile(click.getPoint());

        if (canPlace(t)) {
            t.setType(findType());
            //t.setColour(getColour(t.getType()));
            staticTiles.put(t, findType());
            setStatus(findType() == TileType.OBSTRUCTION ? "Ready to path-find." : "Please place the "+findType()+" tile.");
        } else {
            return;
        }

        //staticTiles.add(Tile.snapToTile(click.getPoint()), findType());

        //Tile.render(screen, click.getPoint(), true);
        //System.out.println("UUID: "+Tile.snapToTile(click.getPoint()).getUUID());
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub

        handleDrag(arg0);

        /*System.out.println("mouse dragged!");
        isDragging = true;
        handleDrag(arg0);*/
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
        handleClick(arg0);
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
        //mousePressed = Tile.snapToTile(arg0.getPoint());
    	mousePressed = tile_instance.snapToTile(arg0.getPoint());
        
        if (mousePressed.getType() != TileType.OPEN) {
            isDragging = true;
        }

        //mousePressed.setType(Tile.snapToTile(arg0.getPoint()).getType());
        //mousePressed.setPoint(Tile.snapToTile(arg0.getPoint()).getPoint());

        //mousePressed = Tile.snapToTile(arg0.getPoint());

        //staticTiles.removeValue(mousePressed.getType()); //remove the TileType from currently "staticTiles" BidiMap
        //staticTiles.put(Tile.snapToTile(arg0.getPoint()), mousePressed.getType()); //map the mouse's tile onto its type.

        /*mousePressed = Tile.snapToTile(arg0.getPoint());
        //mousePressed.setType(mousePressed, TileType.REGULAR);
        System.out.println("mouse pressed");
        isDragging = true;*/
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
        mouseReleased = tile_instance.snapToTile(arg0.getPoint());
        //mouseReleased.setType(mousePressed.getType());
        //mousePressed.setType(TileType.REGULAR);

        isDragging = false;
        /*mouseReleased = Tile.snapToTile(arg0.getPoint());
		
		
  		mouseReleased.setType(mousePressed.getType());
		
  		System.out.println("mouse released");
  		isDragging = false;*/
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.isControlDown()) {
        	renderDebug = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    	if (arg0.getKeyCode() == KeyEvent.VK_CONTROL) {
    		renderDebug = false;
    	}
    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }
    
    public static Preferences getPreferences() {
		return preferences;
	}

	public static BidiMap<Tile, Tile.TileType> getStaticTiles() {
        return staticTiles;
    }
	
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
    public static Tile getTileInstance() {
		return tile_instance;
	}

	private void tick() {

    }

}