package model;

/**
 * Coord is a basic class for an
 * object which holds just an x coordinate
 * and a y coordinate.
 * 
 * @author	Zachary L. Brown
 * @version	0.1.0
 * @since	0.1.0
 */


public class Coord {
	
	private int x, y;
	
	/**
	 * Class constructor which when called, will
	 * allow an x value and y value to be set.
	 * @param x <code>int</code> value of the x coordinate
	 * @param y <code>int</code> value of the y coordinate
	 */
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the x position of the organism.
	 * @return <code>int</code> value of the x position
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y position of the organism.
	 * @return <code>int</code> value of the y position
	 */
	public int getY() {
		return y;
	}
}
