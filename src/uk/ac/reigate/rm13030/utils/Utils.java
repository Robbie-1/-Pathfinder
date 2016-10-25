package uk.ac.reigate.rm13030.utils;

import java.awt.Color;
import java.awt.Point;
import java.sql.Time;
import java.time.LocalTime;

import uk.ac.reigate.rm13030.core.Constants;

/**
 * Utils.java; A selection of useful functions
 * 
 * @author Robbie <http://reigate.ac.uk/>
 */

public class Utils {
	
	public static String colorToHex(Color c) {
		return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
	}
	
	public static Color hexToColor(String hex) {
		return hex.startsWith("#") ? Color.decode(hex) : Color.decode("#"+hex);
	}
	
    public static boolean isOutOfBounds(Point pt) {
    	if (pt == null)
    		return true;
    	
    	if (Constants.GRID_RECT.contains(pt)) {
    		return false;
    	}
    	
    	return true;
        //if (pt == null || pt.getX() < 0 || pt.getX() > Constants.WIDTH || pt.getY() < 0 || pt.getY() > 447) {
            //grid bounds = 608x448
        //	return true;
        //}
       // return false;
    }
    
	public static Time getCurrentTime() {
		return Time.valueOf(LocalTime.now());
	}

}
