package uk.ac.reigate.rm13030.algorithm;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import uk.ac.reigate.rm13030.core.Constants;
import uk.ac.reigate.rm13030.core.Tile;
import uk.ac.reigate.rm13030.core.Tile.TileType;

public class AStarAlgorithm implements Algorithm, AStarHeuristic {
	
	/**
	 * G Cost = distance from current node to starting node
	 * H cost (Heuristic) = distance from current node to end node
	 * F cost = G cost + H cost
	 * 
	 * Heuristic: enabling a person to discover or learn something for themselves.
	 */
	
	private Tile[][] map;
	private Tile[][] nodes;
	
	private Tile startTile;
	private Tile currentNode;
	
	private int distanceTravelled;
	
	private int maxSearchDistance;
	private boolean allowDiagonalMovement;
	private AStarHeuristic heuristic;

	private ArrayList<Tile> closedNodes = new ArrayList<Tile>();
	private ArrayList<Tile> openNodes = new ArrayList<Tile>();
	
	public AStarAlgorithm(Tile[][] tileMap, int maxSearchDistance, boolean allowDiagonalMovement) {
		this(tileMap, maxSearchDistance, allowDiagonalMovement, new ClosestHeuristic());
	}
	
	public AStarAlgorithm(Tile[][] tileMap, int maxSearchDistance,
							boolean allowDiagonalMovement, AStarHeuristic heuristic) {
		this.map = tileMap;
		this.maxSearchDistance = maxSearchDistance;
		this.allowDiagonalMovement = allowDiagonalMovement;
		this.heuristic = heuristic;
		
		//nodes = map.toArray(nodes);
		this.nodes = map;
		//System.out.println("[debug]: "+nodes[5][0].getPoint().getX());
		
		/*nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
		for (int x=0;x<map.getWidthInTiles();x++) {
			for (int y=0;y<map.getHeightInTiles();y++) {
				nodes[x][y] = new Node(x,y);
			}
		}*/
	}
	
	/**
	 * G Cost = distance from current node to starting node
	 * H cost (Heuristic) = distance from current node to end node
	 * F cost = G cost + H cost
	 * 
	 * Heuristic: enabling a person to discover or learn something for themselves.
	 */

	@Override
	public Path findPath(Tile startNode, Tile endNode) {
		
		currentNode = null;
		
		Point startPt = startNode.getPoint();
		Point endPt = endNode.getPoint();
		
		this.startTile = new Tile(startPt);
		//this.startTile.setType(TileType.START);
		this.distanceTravelled = 0;
		
		int startX = (int) startPt.getX(), startY = (int) startPt.getY();
		int endX = (int) endPt.getX(), endY = (int) endPt.getY();
		
		//Check if the end tile is valid
		if (!startTile.isValidDestination()) {
			System.out.println("[debug] - Start tile is NOT a valid destination!");
			return null;
		}
		
		/* Set initial A* Algorithm values */
		nodes[startX][startY].setGCost(0);
		nodes[startX][startY].setFCost(0); //testing purposes
		nodes[startX][startY].setSearchDepth(0);
		nodes[startX][startY].setType(TileType.OPEN);
		
		openNodes.clear();
		closedNodes.clear();
		
		openNodes.add(nodes[startX][startY]);
		
		nodes[endX][endY].setParent(null);
		
		/* While we haven't found the goal node, and haven't exceeded max search depth */
		int maxDepth = 0;
		while ((maxDepth < maxSearchDistance) && (openNodes.size() != 0)) {
			/* Step One: Retrieve the first node in the open list -> this has been determined
			 * to be the most likely to be the next step based on our heuristic*/
			int currentX = startX;
			int currentY = startY;
			
			if (currentNode != null) {
				currentX = (int) currentNode.getPoint().getX();
				currentY = (int) currentNode.getPoint().getY();
			}
			
			currentNode = openNodes.get(0);
			distanceTravelled = currentNode.getSearchDepth();
			
			if (currentNode.equals(nodes[endX][endY])) {
				if (isValidLocation(currentX, currentY, endX, endY)) {
					break;
				}
			}
			
			openNodes.remove(currentNode);
			closedNodes.add(currentNode);
			
			// search through all the neighbours of the current node evaluating
			// them as next steps
			for (int x=-1; x<2; x++) {
				for (int y=-1; y<2; y++) {
					//not a neighbour, it's the current tile
					if ((x==0) && (y==0)) {
						continue;
					}
					
					// if we're not allowing diagonal movement, then only 
					// one of x or y can be set
					if (!allowDiagonalMovement) {
						if ((x != 0) && (y != 0)) {
							continue;
						}
					}
					
					// determine the location of the neighbour and evaluate it
					int neighbourX = x + currentX;
					int neighbourY = y + currentY;
					
					if (isValidLocation(currentX, currentY, neighbourX, neighbourY)) {
						// the cost to get to this node is cost the current plus the movement
						// cost to reach this node. Note that the Heuristic value is only used
						// in the sorted open list
						
						//double nextStepCost = currentNode.getFCost() + ;
					}
					
				}
			}
			
		}
		
		
		
		
		
		Path path = new Path();
		return path;
	}

	private boolean isValidLocation(int startX, int startY, int endX, int endY) {
		
		boolean invalid = (endX < 0) || (endY < 0) || 
						  	(endX > Constants.WIDTH) ||(endY > 447);
		
		if ((!invalid) && ((startX != endX) || (startY != endY))) {
			this.startTile.setPoint(new Point(startX, startY));
			invalid = startTile.isValidDestination();
		}
		
		return !invalid;
	}

	//H cost
	@Override
	public double getCost(Tile[][] tileMap, Point currentPoint, Point endPoint) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Tile[][] getMap() {
		return map;
	}

	public void setMap(Tile[][] map) {
		this.map = map;
	}

	public Tile[][] getNodes() {
		return nodes;
	}

	public void setNodes(Tile[][] nodes) {
		this.nodes = nodes;
	}

	public Tile getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Tile currentNode) {
		this.currentNode = currentNode;
	}

	public int getMaxSearchDistance() {
		return maxSearchDistance;
	}

	public void setMaxSearchDistance(int maxSearchDistance) {
		this.maxSearchDistance = maxSearchDistance;
	}

	public boolean isAllowDiagonalMovement() {
		return allowDiagonalMovement;
	}

	public void setAllowDiagonalMovement(boolean allowDiagonalMovement) {
		this.allowDiagonalMovement = allowDiagonalMovement;
	}

	public AStarHeuristic getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(AStarHeuristic heuristic) {
		this.heuristic = heuristic;
	}

	public ArrayList<Tile> getClosedNodes() {
		return closedNodes;
	}

	public void setClosedNodes(ArrayList<Tile> closedNodes) {
		this.closedNodes = closedNodes;
	}

	public ArrayList<Tile> getOpenNodes() {
		return openNodes;
	}

	public void setOpenNodes(ArrayList<Tile> openNodes) {
		this.openNodes = openNodes;
	}
	
}
