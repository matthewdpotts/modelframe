package model.mosquito;

import model.Empty;

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
public abstract class Organism extends Empty {
	
	/**
	 * Class constructor which when called, defines 
	 * the originating x & y position.
	 * 
	 * @param xpos <code>int</code> value of x position of the organism
	 * @param ypos <code>int</code> value of y position of the organism
	 */
	public Organism(int xpos, int ypos) {
		super(xpos, ypos);
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
	 * Returns whether or not this object is occupied
	 * @return	<code>true</code> if the space is occupied;
	 * 			<code>false</code> if the space is occupied;
	 */
	public boolean isOccupied() {
		return true;
	}
	
	/**
	 * Returns whether or not this object is a "human"
	 * @return	<code>true</code> if the space is a human;
	 * 			<code>false</code> if the space is a human;
	 */
	public boolean isHuman() {
		return false;
	}
	
	/**
	 * Returns whether or not this object is a "mosquito"
	 * @return	<code>true</code> if the space is a mosquito;
	 * 			<code>false</code> if the space is a mosquito;
	 */
	public boolean isMosquito() {
		return false;
	}
	
}