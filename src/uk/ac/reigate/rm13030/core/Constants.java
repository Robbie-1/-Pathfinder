package uk.ac.reigate.rm13030.core;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
	
    public static final String NAME = "<Pathfinder>";
	
    //608x448
	public static final int WIDTH = 608, HEIGHT = 552;
	
	public static final int TILE_SIZE = 32;
	
	//public static final Path DIR_PATH = Paths.get(System.getProperty("user.home")+"\\.pathfinder");
	public static final Path DIR_PATH = 
			Paths.get(System.getProperty("user.home")).getFileName().toString().equals("rm13030") ? 
					Paths.get("X:\\My Documents\\.pathfinder") : 
						Paths.get(System.getProperty("user.home")+"\\.pathfinder");
	
	public static final Path FILE_PATH = DIR_PATH.resolve(Constants.DIR_PATH+"\\data_store.ser");
	
	//public static final Path LOCAL_PATH =  Paths.get("C:\\Users\\rm13030\\file.ser");
	//public static final String LOCAL_PATH = System.getProperty("user.home");
	
	//public static final int OFFSET_X = 17, OFFSET_Y = 4;

}
