package model.mosquito;


/**
 * Human is a class which represents 
 * the basic attributes of a human in terms
 * of movement. It extends the Empty class, 
 * inheriting all of its methods as well as
 * implementing the Space interface. It allows 
 * for setting the initial x and y positions as
 * well as setting the average distance for 
 * which the organism will move (dist).
 * 
 * @author	Zachary L. Brown
 * @version	0.1.0
 * @since	0.1.0
 */
public class Human extends Organism {
	
	private double mean_dist;
	private boolean infected;
	
	/**
	 * Class constructor which when called, defines 
	 * the originating x & y position.
	 * 
	 * @param xpos <code>int</code> value of x position of the organism
	 * @param ypos <code>int</code> value of y position of the organism
	 * @param dist <code>double</code> value of mean distance for which the Human can travel
	 */
	public Human(int xpos, int ypos, double dist) {
		super(xpos, ypos);
		mean_dist = dist;
		infected = false;
	}
	
	/**
	 * Returns whether or not this object is "empty"
	 * @return	<code>true</code> if the space is empty;
	 * 			<code>false</code> if the space is occupied;
	 */
	public boolean isEmpty() {
		return false;
	}
	
	/**
	 * Returns whether or not this object is a "human"
	 * @return	<code>true</code> if the space is a human;
	 * 			<code>false</code> if the space is a human;
	 */
	public boolean isHuman() {
		return true;
	}
	
	/**
	 * Returns the double value of the mean distance 
	 * the Human object can travel
	 * 
	 * @return <code>double</code> value of the mean distance.
	 */
	public double getMeanDist() {
		return mean_dist;
	}
	
	/**
	 * Returns the boolean value of whether the mosquito is
	 * infected.
	 * 
	 * @return <code>boolean</code> value of the infection state.
	 */
	public boolean isInfected() {
		return infected;
	}
	
	/**
	 * Sets the infection state of the mosquito.
	 * @param val <code>boolean</code> value of infection state
	 */
	public void setInfected(boolean val) {
		infected = val;
	}
}
