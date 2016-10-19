package uk.ac.reigate.rm13030.algorithm;

import java.awt.Point;
import java.util.ArrayList;
import uk.ac.reigate.rm13030.core.Tile;

public class AStar_v2 implements Algorithm, AStarHeuristic {
	
	ArrayList<Tile> openNodes;
	ArrayList<Tile> closedNodes;
	
	ArrayList<Step> steps;
	
	Tile[][] tileMap;
	
	private static final int DIAGONAL_COST = 45;
	private static final int V_H_COST = 32;
	
	Tile currentNode;
	
	public AStar_v2(Tile[][] tileMap) {
		this.tileMap = tileMap;
		this.openNodes = new ArrayList<Tile>();
		this.closedNodes = new ArrayList<Tile>();
	}
	
	@Override
	public Path findPath(Tile startNode, Tile endNode) {
		Path path = null;
		
		openNodes.add(startNode);
		
		while(true) {
			currentNode = getLowestFCost(openNodes);
			
			if (currentNode == null) {
				System.out.println("currentNode == null -> breaking");
				break;
			}
			
			openNodes.remove(currentNode);
			closedNodes.add(currentNode);
			
			if (currentNode.equals(endNode)) {
				path = new Path(steps);
				System.out.println("PATH HAS BEEN FOUND!");
				return path;
			}
			
			/* [IMPORTANT]: Use steps.add(step) for every step in the path! */
			
		}
		return path;
	}
	
	/**
	 * @param list The list to iterate through
	 * @return The tile with the lowest F-Cost for the given list.
	 */
	private Tile getLowestFCost(ArrayList<Tile> list) {
		Tile lowest = new Tile(null);
		for (int i=0; i<list.size(); i++) {
			if (list.get(i).getFCost() < lowest.getFCost()) {
				lowest = list.get(i);
			}
		}
		return lowest;
	}

	@Override
	public double getCost(Tile[][] tileMap, Point currentPoint, Point endPoint) {
		
		return 0;
	}
	
}
