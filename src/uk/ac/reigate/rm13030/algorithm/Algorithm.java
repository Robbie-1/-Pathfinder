package uk.ac.reigate.rm13030.algorithm;

import uk.ac.reigate.rm13030.core.Tile;

public interface Algorithm {
	
	public Path findPath(Tile startNode, Tile endNode);

}
