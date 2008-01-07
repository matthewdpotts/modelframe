package model;

/**
 * The Space interface is used to define objects 
 * which require those properties of an organism.
 * 
 * @author zbrown
 * @version	0.1.0
 * @since	0.1.0
 */

public interface Space {
	/**
	 * Returns the x position of the organism.
	 * @return the integer value of the x position
	 */
	public int getXpos();
	
	/**
	 * Returns the y position of the organism.
	 * @return the integer value of the y position
	 */
	public int getYpos();
	
	/**
	 * Updates the x and y positions of the organism.
	 * @param newX the new x position
	 * @param newY the new y position
	 */
	public void updatePos(int newX, int newY);
	
	/**
	 * Returns whether or not this object is "empty"
	 * @return	<code>true</code> if the space is empty;
	 * 			<code>false</code> if the space is occupied;
	 */
	public boolean isEmpty();
	
	/**
	 * Returns whether or not this object is occupied
	 * @return	<code>true</code> if the space is occupied;
	 * 			<code>false</code> if the space is occupied;
	 */
	public boolean isOccupied();
	
}
