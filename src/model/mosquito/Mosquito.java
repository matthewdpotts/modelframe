package model.mosquito;


/**
 * Mosquito is a class which represents 
 * the basic attributes of a mosquito in terms
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
public class Mosquito extends Organism {

	public int bitecount; 
	private int bitetime;
	private double mean_dist;
	private boolean bitten;
	private boolean infected;

	/**
	 * Class constructor which when called, defines 
	 * the originating x & y position.
	 * 
	 * @param xpos the x position of the organism
	 * @param ypos the y position of the organism
	 * @param dist the mean distance for which the Mosquito can travel
	 */
	
	public Mosquito(int xpos, int ypos, double dist) {
		super(xpos, ypos);
		mean_dist = dist;
		bitecount = 0;
		bitetime = 0;
		bitten = false;
		infected = false;
	}
	
	
	/**
	 * Sets the bite wait time, an integer, after the mosquito
	 * has fed.
	 * @param time <code>int</code> amount of time to wait
	 * before attempting to bite again
	 */
	public void setBiteTime(int time) {
		bitetime = time;
	}
	
	
	/**
	 * Returns the time left before the mosquito will attempt to
	 * bite again.
	 * @return <code>int</code> value of the remaining time
	 */
	public int waitBite() {
		return bitetime--;
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
	 * Returns whether or not this object is a "mosquito"
	 * @return	<code>true</code> if the space is a mosquito;
	 * 			<code>false</code> if the space is a mosquito;
	 */
	public boolean isMosquito() {
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
	
	/**
	 * Returns the boolean value of whether the mosquito has
	 * bitten.
	 * 
	 * @return <code>boolean</code> value of the bitten state.
	 */
	public boolean hasBitten() {
		return bitten;
	}
	
	/**
	 * Sets the infection state of the mosquito.
	 * @param val <code>boolean</code> value of bitten state
	 */
	public void setBitten() {
		bitten = true;
	}
}
