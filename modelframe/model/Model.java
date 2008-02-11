package model;

public interface Model {

	/**
	 * Basic model foundation.
	 * 
	 * @author	Zachary L. Brown
	 * @version	0.1.1
	 * @since	0.1.1
	 */
	
	/**
	 * Start model simulation
	 */
	public void run();
	
	/**
	 * Setup model simulation
	 */
	public void setup();
	
	/**
	 * Process model simulation data
	 */
	public void processData();
	
	/**
	 * Stop model simulation prematurely
	 */
	public void stop();
	
}
