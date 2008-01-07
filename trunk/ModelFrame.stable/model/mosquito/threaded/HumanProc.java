package model.mosquito.threaded;

import java.util.Random;

import model.mosquito.Human;

public class HumanProc implements Runnable {

	private int startIndex, procLength, width, length;
	private Human[] array;
	private boolean[][] human_mat;
	private Random mt;
	
	public HumanProc(Human[] array, int startIndex, int procLength, boolean[][] human_mat, int width, int length) {
		this.startIndex = startIndex;
		this.procLength = procLength;
		this.width = width;
		this.length = length;
		this.array = array;
		this.human_mat = human_mat;
		mt = new Random();
	}
	
	public void run() {
		for(int i = startIndex; i < procLength; i++) {
			move(array[i]);
		}
	}
	
	private void move(Human human) {
		int x, y, oldX, oldY;
		double mean = human.getMeanDist();
		
		oldX = human.getXpos();
		oldY = human.getYpos();
		
		x = getNewCoord(oldX, mean, width, true);
		y = getNewCoord(oldY, mean, length, false);
		
		if(x < width && y < width && x > 0 && y > 0 && human_mat[x][y] == false) {
			human.updatePos(x, y);
			synchronized(this) {
				human_mat[x][y] = true;
				human_mat[oldX][oldY] = false;
			}
		}
	}
	
	private int getNewCoord(int old, double mean, int dim, boolean xORy) //Using x = r*cos(t) or y = r*sin(t) parametric formula.
	{
		int s;
		double r, t;
		
		r = mean * mt.nextGaussian();
		t = (double) mt.nextInt(3);
		if(xORy) {
			s = (int) (old + r * Math.cos(t));
		}
		else {
			s = (int) (old + r * Math.sin(t));
		}
		
		if(s > dim)
		{
			return s % dim;
		}
		else if(s < 0)
		{
			return dim % s;
		}
		else 
		{
			return s;
		}
	}
	
	public int getIndex(int x, int y) {
		return x + y*width + 1;
	}
	
}
