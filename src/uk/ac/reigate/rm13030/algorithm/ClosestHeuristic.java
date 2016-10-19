package uk.ac.reigate.rm13030.algorithm;

import java.awt.Point;
import java.util.ArrayList;

import uk.ac.reigate.rm13030.core.Tile;

/**
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile.
 */
public class ClosestHeuristic implements AStarHeuristic {
	/**
	 * @see AStarHeuristic#getCost(TileBasedMap, Mover, int, int, int, int)
	 */
	public double getCost(Tile[][] tileMap, Point currentPoint, Point endPoint) {
		
		double dx = endPoint.getX() - currentPoint.getX();
		double dy = endPoint.getY() - currentPoint.getY();
		
		double result = (double) (Math.sqrt((dx*dx)+(dy*dy)));
		
		//float dx = tx - x;
		//float dy = ty - y;
		
		//float result = (float) (Math.sqrt((dx*dx)+(dy*dy)));
		
		return result;
	}

}