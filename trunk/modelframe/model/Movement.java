package model;

import model.Coord;
import model.Environment;
import model.util.MersenneTwisterFast;

public class Movement {
	
	/**
	 * Basic model foundation.
	 * 
	 * @author	Zachary L. Brown
	 * @version	0.1.1
	 * @since	0.1.1
	 */
	
	private double moveDist, moveProb;
	private Environment env;
	private MersenneTwisterFast mt;
	
	/**
	 * Class constructor which when called, will
	 * allow an x value and y value to be set.
	 * @param moveDist <code>double</code> value of the movement distance
	 */
	public Movement(Environment env, double moveDist) {
		this.moveDist = moveDist;
		this.moveProb = 1.0;
		this.env = env;
		mt = new MersenneTwisterFast();
	}
	
	/**
	 * Class constructor which when called, will
	 * allow an x value and y value to be set.
	 * @param moveDist <code>double</code> value of the movement distance
	 * @param moveProb <code>double</code> value of the probability that movement will occur
	 */
	public Movement(Environment env, double moveDist, double moveProb) {
		this.moveDist = moveDist;
		this.moveProb = moveProb;
		this.env = env;
		mt = new MersenneTwisterFast();
	}
	
	/**
	 * Get the coordinates for the new position
	 * @return	<code>Coord</code> object of new location;
	 */
	public Coord move(Coord currPos) {
		int x_new, y_new;
		x_new = getNewCoord(currPos.getX(), moveDist, env.getWidth(), true);
		y_new = getNewCoord(currPos.getX(), moveDist, env.getLength(), false);
		return new Coord(x_new, y_new);
	}
	
	/*
	 * produce the new coordinate
	 */
	private int getNewCoord(int old, double mean, int dim, boolean xORy) {
		int s;
		double r, t;
		
		r = mean * mt.nextGaussian();
		t = (double) mt.nextInt(3);
		if(xORy)
			s = (int) (old + r * Math.cos(t));
		else
			s = (int) (old + r * Math.sin(t));
		
		if(s > dim)
			return s % dim;
		else if(s < 0)
			return dim % s;
		else 
			return s;
	}
	
	
}
