package model.mosquito.threaded;

import java.util.*;

import model.mosquito.Mosquito;

public class MosqProc implements Runnable {

	private static int[] choicex = {1,0,-1,0};
	private static int[] choicey = {0,1,0,-1};
	
	private int startIndex, procLength, width, length;
	private Mosquito[] array;
	private boolean[][] human_mat;
	private Random mt;
	private double data[][]; 
	
	public MosqProc(ModelArrsThreaded model, Mosquito[] array, int startIndex, int procLength, boolean[][] human_mat, int width, int length) {
		this.startIndex = startIndex;
		this.procLength = procLength;
		this.width = width;
		this.length = length;
		this.array = array;
		this.human_mat = human_mat;
		this.data = model.data;
		mt = new Random();
	}
	
	public void run() {
		int rand;
		for(int i = startIndex; i < procLength; i++) {
			if(array[i].waitBite() < 1 && bite(array[i]))	{
				data[ModelArrsThreaded.BITE_NUM][ModelArrsThreaded.index]++;
				data[ModelArrsThreaded.BITE_AVG][ModelArrsThreaded.index]++;
				rand = mt.nextInt(6);
				data[ModelArrsThreaded.WAIT_AVG][ModelArrsThreaded.index] += (double)rand;
				data[ModelArrsThreaded.TOT_BITE_AVG][ModelArrsThreaded.index]++;
				array[i].setBiteTime(0); //modify this for bite time changes
			}
			move(array[i]);
		}
	}
	
	private void move(Mosquito mosq) {
		int x, y, oldX, oldY;
		double mean = mosq.getMeanDist();		
		
		oldX = mosq.getXpos();
		oldY = mosq.getYpos();
		
		x = getNewCoord(oldX, mean, width, true);
		y = getNewCoord(oldY, mean, length, false);
		
		mosq.updatePos(x, y);
		
	}
	
	public boolean bite(Mosquito mosq)
	{
		int x, y, newX, newY;
		
		x = mosq.getXpos();
		y = mosq.getYpos();
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				newX = (x + choicex[i] + width) % width;
				newY = (y + choicey[j] + length) % length; 
				
				if(human_mat[newX][newY] == true) {
					mosq.bitecount++;
					return true;
				}
			}
		}
		
		return false;
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
