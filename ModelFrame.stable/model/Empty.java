package model;


/**
 * Organism is the abstract class for all organisms
 * that are to be run in a model. No actual Empty 
 * exists except to provide a general way of writing 
 * methods that need to access a "generic" organism.
 * <p>
 * Empty implements the Space interface which defines
 * necessary features for all organisms which are created
 * from the Empty class.
 * 
 * @author	Zachary L. Brown
 * @version	0.1.0 
 * @since	0.1.0
 */

public abstract class Empty implements Space {

	private int x, y;
	
	/**
	 * Class constructor which when called, defines 
	 * the originating x & y position.
	 * 
	 * @param xpos <code>int</code> value of x position of the organism
	 * @param ypos <code>int</code> value of y position of the organism
	 */
	public Empty(int xpos, int ypos) {
		x = xpos;
		y = ypos;
	}
	
	/**
	 * Returns the x position of the organism.
	 * @return <code>int</code> value of the x position
	 */
	public int getXpos() {
		return x;
	}
	
	/**
	 * Returns the y position of the organism.
	 * @return <code>int</code> value of the y position
	 */
	public int getYpos() {
		return y;
	}
	
	/**
	 * Updates the x and y positions of the organism.
	 * @param newX <code>int</code> value of new x position
	 * @param newY <code>int</code> value of new y position
	 */
	public void updatePos(int newX, int newY) {
		x = newX;
		y = newY;
	}

	/**
	 * Returns whether or not this object is "empty"
	 * @return	<code>true</code> if the space is empty;
	 * 			<code>false</code> if the space is occupied;
	 */
	public boolean isEmpty() {
		return true;
	}
	
	/**
	 * Returns whether or not this object is occupied
	 * @return	<code>true</code> if the space is occupied;
	 * 			<code>false</code> if the space is occupied;
	 */
	public boolean isOccupied() {
		return false;
	}
	
	
}
