package uk.ac.reigate.rm13030.algorithm;

import uk.ac.reigate.rm13030.core.Tile;

public class Step {
	
	private Tile oldTile, newTile;

	public Step(Tile oldTile, Tile newTile) {
		this.oldTile = oldTile;
		this.newTile = newTile;
	}

	public Tile getOldTile() {
		return oldTile;
	}

	public void setOldTile(Tile oldTile) {
		this.oldTile = oldTile;
	}

	public Tile getNewTile() {
		return newTile;
	}

	public void setNewTile(Tile newTile) {
		this.newTile = newTile;
	}
	
}
