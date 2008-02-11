package model.tree;

import java.util.Arrays;
import model.util.MersenneTwisterFast;


public class Tree {
	
	public int matrix1[][], matrix2[][];
	public int A, L, theta;
	public static int newest;
	public static final MersenneTwisterFast mt = new MersenneTwisterFast();
	public static final int[] choicex = {1,0,-1,0};
	public static final int[] choicey = {0,1,0,-1};
	
	public static void main(String args[])
	{
		
		newest = 1;
				
		Tree model = new Tree(256, 64);
		
		for(int time = 0; time < 500000; time++)
		{
			model.run();
			
			if(time % 1000 == 0)
			{
				System.out.println(time);
			}
		}
		
		System.out.println("Number of species at end of simulation: "+model.sortCount());
		
	}
	
	public Tree(int side, int T) 
	{
		L = side;
		A = L*L;
		theta = T;
		matrix1 = new int[L][L];
		matrix2 = new int[L][L];
		initialize();
	}
	
	public static int[] nearReplace(int x, int y, int xdim, int ydim)
	{
		int flip = mt.nextInt(4);
		int[] rl = new int[2];
		rl[0] = (x + choicex[flip] + xdim)%xdim;
		rl[1]= (y + choicey[flip] + ydim)%ydim; 
		return rl;
	}
	
	public void run()
	{
		int x, y;
		
		for(int i = 0; i < A; i++)
		{
			x = pickCoord();
			y = pickCoord();
					
			if(matrix1[x][y] > 0)
			{
				matrix2[x][y] = matrix1[pickCoord()][pickCoord()]; // <-- global speciation
			}	
		}
		
		for(int j = 0; j < theta; j++)
		{
			matrix2[pickCoord()][pickCoord()] = lastSpec();
		}
		
		for(x = 0; x < L; x++)
		{
			for(y = 0; y < L; y++)
			{
				matrix1[x][y] = matrix2[x][y];
			}
		}
		
	}
	
	private int lastSpec()
	{
		return ++newest;
	}
	
	public int sortCount()
	{
		int[] sortedList = new int[A];
		
		for(int x = 0; x < L; x++)
		{
			for(int y = 0; y < L; y++)
			{
				sortedList[x*y] = matrix1[x][y];
			}
		}
		
		Arrays.sort(sortedList);
		int lastSpec = sortedList[0];
		int count = 0;
		
		for(int j = 1; j < sortedList.length; j++)
		{
			if(sortedList[j] != lastSpec)
			{
				lastSpec = sortedList[j];
				count++;
			}
		}
		
		return count;
	}
	
	
	private int pickCoord()
	{
		return mt.nextInt(L);
	}
	
	private void initialize() 
	{
		for(int i = 0; i < L; i++)
		{
			for(int j = 0; j < L; j++)
			{
				matrix1[i][j] = 1;
			}
		}
	}
	
}
