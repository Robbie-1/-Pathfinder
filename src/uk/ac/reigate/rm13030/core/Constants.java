package uk.ac.reigate.rm13030.core;

import java.awt.Rectangle;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Robbie <http://reigate.ac.uk/>
 */

public class Constants {
	
    public static final String NAME = "<Pathfinder>";
    
    public static final double VERSION = 0.1;
	
    //608x448
	public static final int WIDTH = 608, HEIGHT = 552;
	
	public static final int TILE_SIZE = 32;
	
	public static final Rectangle GRID_RECT = new Rectangle(WIDTH, 448);
	
	public static final Rectangle TUTORIAL_RECT = new Rectangle(0, 448, WIDTH, HEIGHT);
	
	public static final Path DIR_PATH = 
			Paths.get(System.getProperty("user.home")).getFileName().toString().equals("rm13030") ? 
					Paths.get("X:\\My Documents\\.pathfinder") : 
						Paths.get(System.getProperty("user.home")+"\\.pathfinder");
        
        public static final Path PREFERENCES_PATH = DIR_PATH.resolve(Constants.DIR_PATH+"\\preferences.ser");
        
        public static final Path TILE_MAP_PATH = DIR_PATH.resolve(Constants.DIR_PATH+"\\tileMap.ser");
        
        public static final Path STATIC_TILES_PATH = DIR_PATH.resolve(Constants.DIR_PATH+"\\staticTiles.ser");

}
