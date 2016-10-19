package uk.ac.reigate.rm13030.algorithm;

import java.awt.Point;
import java.util.ArrayList;

import uk.ac.reigate.rm13030.core.Tile;

/**
 * G Cost = distance from current node to starting node
 * H cost (Heuristic) = distance from current node to end node
 * F cost = G cost + H cost
 * 
 * Heuristic: enabling a person to discover or learn something for themselves.
 */
public interface AStarHeuristic {
	
	//H cost (Heuristic): found by calculating the distance from the current node to the end node.
	public double getCost(Tile[][] tileMap, Point currentPoint, Point endPoint);

}
