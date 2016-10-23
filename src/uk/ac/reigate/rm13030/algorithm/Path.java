package uk.ac.reigate.rm13030.algorithm;

import java.util.ArrayList;

/**
 * @author Robbie <http://reigate.ac.uk/>
 */

public class Path {
	
	ArrayList<Step> steps;
	
	public Path() {
		this(new ArrayList<Step>());
	}
	
	public Path(ArrayList<Step> steps) {
		this.steps = steps;
	}
	
	/**
	 * @return The length of this path (Double)
	 */
	public double getLength() {
		return steps.size();
	}
	
	public Step getStep(int index) {
		return steps.get(index);
	}

	public ArrayList<Step> getSteps() {
		return steps;
	}

	public void setSteps(ArrayList<Step> steps) {
		this.steps = steps;
	}
	
}
