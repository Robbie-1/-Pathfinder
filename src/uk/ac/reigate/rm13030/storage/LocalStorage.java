package uk.ac.reigate.rm13030.storage;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.lang3.SerializationUtils;

import uk.ac.reigate.rm13030.core.Application;
import uk.ac.reigate.rm13030.core.Constants;
import uk.ac.reigate.rm13030.core.Preferences;
import uk.ac.reigate.rm13030.core.Tile;
import uk.ac.reigate.rm13030.core.Tile.TileType;


/**
 * 
 * @author Robbie <http://reigate.ac.uk/>
 *
 */

public class LocalStorage {
	
	/**
	          	Tile[][] tileMap;
        		BidiMap<Tile, Tile.TileType> staticTiles;
        		Preferences preferences;
	 */
    
    public LocalStorage() { }
    
    public void storePreferences(Preferences prefs) {
        writeObject(prefs, Constants.PREFERENCES_PATH);
        //System.out.println("Written preferences to file!");
    }
    
    public void storeTileMap(Tile[][] tileMap) {
        writeObject(tileMap, Constants.TILE_MAP_PATH);
        //System.out.println("Written tile map to file!");
    }
    
    public void storeStaticTiles(ArrayList<Tile> staticTiles) {
        writeObject(staticTiles, Constants.STATIC_TILES_PATH);
        //System.out.println("Written static tiles to file!");
    }
    
    private void writeObject(Object obj, Path path) {
        byte[] data = SerializationUtils.serialize((Serializable) obj);
        try {
            //System.out.println(Constants.DIR_PATH);
            Files.createDirectories(Constants.DIR_PATH);
            Files.write(path, data, StandardOpenOption.CREATE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public Preferences readPrefences() {
    
        byte[] read = readBytes(Constants.PREFERENCES_PATH);
    
        if (read == null) {
            return null;
        } else {
        	Preferences prefs = (Preferences) SerializationUtils.deserialize(read);
        	//System.out.println(prefs.getHighlightColour());
        	return prefs;
        }
    
    }
    
    public Tile[][] readTileMap() {
    	
    	byte[] read = readBytes(Constants.TILE_MAP_PATH);
    	
    	if (read == null) {
    		return null;
    	} else {
    		return (Tile[][]) SerializationUtils.deserialize(read);
    	}
    }
    
    public ArrayList<Tile> readStaticTiles() {
    	
    	byte[] read = readBytes(Constants.STATIC_TILES_PATH);
    	
    	if (read == null) {
    		return null;
    	} else {
    		return (ArrayList<Tile>) SerializationUtils.deserialize(read);
    	}
    			
    }
    
    public byte[] readBytes(Path path) {
        byte[] readBytes = null;
		try {
			readBytes = Files.exists(path) ? Files.readAllBytes(path) : null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readBytes;
    }
	
}
