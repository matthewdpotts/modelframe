package model;

public class Environment {

	/**
	 * Basic model foundation.
	 * 
	 * @author	Zachary L. Brown
	 * @version	0.1.1
	 * @since	0.1.1
	 */
	
	private int width, length;
	
	/**
	 * Class constructor which when called, will
	 * allow an x value and y value to be set.
	 * @param width <code>int</code> x dimension size of the environment
	 * @param length <code>int</code> y dimension size of the environment
	 */
	public Environment(int width, int length) {
		this.width = width;
		this.length = length;
	}
	
	/**
	 * Returns the width of the environment
	 * @return <code>int</code> width of the environment
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the length of the environment
	 * @return <code>int</code> length of the environment
	 */
	public int getLength() {
		return length;
	}
	
}
