package uk.ac.reigate.rm13030.core;

import java.io.Serializable;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class Preferences implements Serializable {

	private static final long serialVersionUID = 6776357359705414395L;
    
    private static BidiMap<String, Object> themeMap = new DualHashBidiMap<String, Object>();//String = name of variable (present this on JTree), Object = value of variable
    private static BidiMap<String, Object> colourMap = new DualHashBidiMap<String, Object>();
    private static BidiMap<String, Object> draggingMap = new DualHashBidiMap<String, Object>();

    public Preferences() {setDefaults();}

    private void setDefaults() {
        /*UITheme = UIManager.getLookAndFeel();

        highlightColour = String.BLUE;
        startNodeColour = String.GREEN;
        endNodeColour = String.RED;
        obstructionColour = String.DARK_GRAY;

        snapToTile = true;*/
        
        themeMap.put("Set theme", UIManager.getLookAndFeel());
        
        colourMap.put("Highlight colour", "#0000FF");
        colourMap.put("Start node colour", "#00FF00");
        colourMap.put("End node colour", "#FF0000");
        colourMap.put("Obstruction colour", "#404040");
        
        draggingMap.put("Snap to Tile", Boolean.TRUE);
    }
    
    public BidiMap<String, Object> getThemeMap() {
		return themeMap;
	}

	public BidiMap<String, Object> getColourMap() {
		return colourMap;
	}

	public BidiMap<String, Object> getDraggingMap() {
		return draggingMap;
	}

	public LookAndFeel getUITheme() {
        return (LookAndFeel) themeMap.get("Set theme");
    }

    public String getHighlightColour() {
    	return (String) colourMap.get("Highlight colour");
        //return highlightColour;
    }

    public String getStartNodeColour() {
    	return (String) colourMap.get("Start node colour");
        //return startNodeColour;
    }

    public String getEndNodeColour() {
    	return (String) colourMap.get("End node colour");
        //return endNodeColour;
    }

    public String getObstructionColour() {
    	return (String) colourMap.get("Obstruction colour");
        //return obstructionColour;
    }

    public boolean isSnapToTile() {
    	return (Boolean) draggingMap.get("Snap to Tile");
        //return snapToTile;
    }

}