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

public class LocalStorage {
	
	/**
	 *  -> Local storage:
	 * 		Index [0]: tileMap (Tile[][])
	 *  	Index [1]: staticTiles (BidiMap<Tile, Tile.TileType>)
	 *  	Index [2]: preferences (Preferences)
	 *         
	 *         	Tile[][] tileMap = (Tile[][]) objects.get(0);
        		BidiMap<Tile, Tile.TileType> statics = (BidiMap<Tile, TileType>) objects.get(1);
        		Preferences prefs = (Preferences) objects.get(2);
	 */
	
	private ArrayList<Object> readData;
	
	public LocalStorage() {}
	
	public void store(ArrayList<Object> serializableObjects) {
		write(serializableObjects);
		//System.out.println("[debug]: "+(getObjects(readBytes())));
	}
	
	public Preferences readPreferences() {
		if (getObjects(readBytes()) == null || readData == null)			//load default.
			return new Preferences();
		
		return (Preferences) readData.get(2);
	}
	
	public Tile[][] readTileMap() {
		if (getObjects(readBytes()) == null || readData == null) {			//load default.
			Tile instance = Application.getTileInstance();
			return instance.getTileMap();
		}
		
		return (Tile[][]) readData.get(0);
	}
	
	public BidiMap<Tile, Tile.TileType> readStaticTiles() {
		if (getObjects(readBytes()) == null || readData == null)			//load default
			return Application.getStaticTiles();
		
		return (BidiMap<Tile, TileType>) readData.get(1); 
	}
	
	public ArrayList<Object> getObjects(byte[] fileBytes) {
		if (fileBytes == null) {
			//program launches for the first time -> load defaults
			return null;
		}
		return SerializationUtils.deserialize(fileBytes);
	}

	/**
	 * Ensure the parsed object(s) are Serializable!
	 * @param objects The Serializable objects to be written.
	 */
	public void write(ArrayList<Object> objects/*byte[] data*/) {
		
		byte[] data = SerializationUtils.serialize((Serializable) objects);
		try {
			//System.out.println(Constants.DIR_PATH);
			Files.createDirectories(Constants.DIR_PATH);
			Files.write(Constants.FILE_PATH, data, StandardOpenOption.CREATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] readBytes() {
		byte[] readBytes = null;
		try {
			readBytes = Files.exists(Constants.FILE_PATH) ? Files.readAllBytes(Constants.FILE_PATH) : null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readBytes;
	}
	
}
