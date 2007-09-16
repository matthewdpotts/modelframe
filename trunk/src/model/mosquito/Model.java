package model.mosquito;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import model.*;
import model.mosquito.threaded.ModelArrsThreaded;

import java.text.DecimalFormat;

public class Model {
	
	private static final int BITE_NUM = 0;
	private static final int WAIT_AVG = 1;
	private static final int BITE_AVG = 2;
	private static final int TOT_BITE_AVG = 3;
	private static final double human_mean = 6.0;
	private static final double mosquito_mean = 2.0;
	private static final int[] choicex = {1,0,-1,0};
	private static final int[] choicey = {0,1,0,-1};
	
	public int width, length;
	public static boolean human_mat[][];
	public Human human_arr[];
	public Mosquito mosq_arr[];
	
	private Printer printer_m, printer_s;
	private DecimalFormat formatter;
	private Runtime runner;
	private MersenneTwisterFast mt;
	
	private int i, j, rand;
	private double data[][];
	
	public Model(int xdim, int ydim, String[] file_main, String[] file_sec) throws FileNotFoundException	{
		width = xdim;
		length = ydim;
		human_mat = new boolean[width][length];
		runner = Runtime.getRuntime();
		mt = new MersenneTwisterFast();
		printer_m = new Printer(file_main[0], file_main[1]);
		printer_s = new Printer(file_sec[0], file_sec[1]);
		formatter = new DecimalFormat("#####.####");
	}
	
	public Model(int xdim, int ydim, String[] file_main, String[] file_sec, boolean firstRun) throws FileNotFoundException	{
		width = xdim;
		length = ydim;
		human_mat = new boolean[width][length];
		runner = Runtime.getRuntime();
		mt = new MersenneTwisterFast();
		printer_m = new Printer(file_main[0], file_main[1]);
		printer_s = new Printer(file_sec[0], file_sec[1]);
		formatter = new DecimalFormat("#####.####");
		printer_m.printHeader();
		printer_s.printHeader();
	}
	
	public void Run(int steps, int num_h, int num_m) throws IOException	{
		int arr[] = new int[2];
		data = new double[4][steps];
		
		human_arr = new Human[num_h];
		mosq_arr = new Mosquito[num_m];
		arr[0] = num_h;
		arr[1] = num_m;
		
		populate(arr);
		
		for(i = 0; i < steps; i++) {
			
			for(j = 0; j < human_arr.length; j++) {
				move(human_arr[j]);
			}
			
			for(j = 0; j < mosq_arr.length; j++) {
				if(mosq_arr[j].waitBite() < 1 && bite(mosq_arr[j]))	{
					data[ModelArrsThreaded.BITE_NUM][i]++;
					data[ModelArrsThreaded.BITE_AVG][i]++;
					rand = mt.nextInt(6);
					data[ModelArrsThreaded.WAIT_AVG][i] += (double)rand;
					data[ModelArrsThreaded.TOT_BITE_AVG][i]++;
					mosq_arr[j].setBiteTime(0); //modify this for bite time changes
				}
				move(mosq_arr[j]);
			}
			
			if(i % 1000 == 0) {
				runner.gc();
			}
		}

		processData(data, steps, num_h, num_m);
	}
	
	public void RunPotts(int steps, int num_h, int num_m) throws IOException {
		int arr[] = new int[2];
		data = new double[4][steps];
		int counter = 0;
		
		human_arr = new Human[num_h];
		mosq_arr = new Mosquito[num_m];
		arr[0] = num_h;
		arr[1] = num_m;
		
		populate(arr);
		
		HashSet<Integer> mosq_ind = new HashSet<Integer>();
		HashSet<Integer> human_ind = new HashSet<Integer>();
		for(i = 0; i < mosq_arr.length; i++)
			mosq_ind.add(Integer.valueOf(i));
		for(i = 0; i < human_arr.length; i++)
			human_ind.add(Integer.valueOf(i));
		
		for(i = 0; i < steps; i++) {
			Iterator<Integer> mosq_itr = mosq_ind.iterator();
			Iterator<Integer> human_itr = human_ind.iterator();
			
			while(mosq_itr.hasNext() || human_itr.hasNext()) {
				boolean choice_act = mt.nextBoolean();
				if(choice_act && mosq_itr.hasNext())
					mosq_action(mosq_arr[mosq_itr.next().intValue()], data, i, counter++);
				else if(choice_act && !mosq_itr.hasNext() && human_itr.hasNext())
					move(human_arr[human_itr.next().intValue()]);
				else if(!choice_act && human_itr.hasNext())
					move(human_arr[human_itr.next().intValue()]);
				else if(!choice_act && !human_itr.hasNext() && mosq_itr.hasNext())
					mosq_action(mosq_arr[mosq_itr.next().intValue()], data, i, counter++);
				else
					break;
			}
			
			if(i % 1000 == 0) {
				runner.gc();
			}
		}
		
		processData(data, steps, num_h, num_m);
		
	}  
	
	public void RunKapitanski(int steps, int num_h, int num_m) throws IOException {
		
	}
	
	
	private void processData(double data[][], int steps, int num_h, int num_m) {
		
		for(j = 0; j < steps; j++) {
			data[WAIT_AVG][j] = data[WAIT_AVG][j] / (double)num_m;
			if(j % 100 == 0) {
				data[BITE_AVG][j] = data[BITE_AVG][j] / 100.0;
				printer_m.printData(Integer.toString(j)+"\t"+Double.toString(data[BITE_NUM][j])+"\t"+formatter.format(data[WAIT_AVG][j])+"\t"+Double.toString(data[BITE_AVG][j]));
			}
			else {
				data[ModelArrsThreaded.BITE_AVG][j] = -100.0;
				printer_m.printData(Integer.toString(j)+"\t"+Double.toString(data[BITE_NUM][j])+"\t"+formatter.format(data[WAIT_AVG][j])+"\t"+Double.toString(data[BITE_AVG][j]));
			}
		}	
		
		printer_s.printData(Integer.toString(num_h)+"\t"+Double.toString(data[TOT_BITE_AVG][steps-1]/(double)steps));
		
		printer_m.closeFile();
		printer_s.closeFile();

	}	
	
	private void populate(int numSpecs[]) {
		int x, y, index;
		Coord tempPos;
		HashSet<Coord> coords = new HashSet<Coord>();
		
		index = 0;
		
		for(x = 0; x < width; x++) {
			for(y = 0; y < length; y++) {
				coords.add(new Coord(x, y));
			}
		}
		
		Iterator<Coord> itr = coords.iterator();
		
		while(index < numSpecs[0] && itr.hasNext()) {
			tempPos = (Coord) itr.next();
			x = tempPos.getX();
			y = tempPos.getY();
			human_mat[x][y] = true;
			human_arr[index++] = new Human(x, y, human_mean);
		}
		
		for(int i = 0; i < numSpecs[1]; i++) {
			x = mt.nextInt(width);
			y = mt.nextInt(length);
			mosq_arr[i] = new Mosquito(x, y, mosquito_mean);
		}
		
		runner.gc();
	}

	private void mosq_action(Organism mosquito, double data[][], int m, int q) {
		int move_rand = mt.nextInt(3);
		boolean bite_rand = mt.nextBoolean();
		
		if(move_rand == 0) {
			move(mosquito);
			if(mosq_arr[m].waitBite() < 1 && bite(mosq_arr[m]) && bite_rand)	{
				data[ModelArrsThreaded.BITE_NUM][q]++;
				data[ModelArrsThreaded.BITE_AVG][q]++;
				rand = mt.nextInt(6);
				data[ModelArrsThreaded.WAIT_AVG][q] += (double)rand;
				data[ModelArrsThreaded.TOT_BITE_AVG][q]++;
				mosq_arr[m].setBiteTime(0); //modify this for bite time changes
			}
		}
		else if(move_rand == 1) {
			if(mosq_arr[m].waitBite() < 1 && bite(mosq_arr[m]) && bite_rand)	{
				data[ModelArrsThreaded.BITE_NUM][q]++;
				data[ModelArrsThreaded.BITE_AVG][q]++;
				rand = mt.nextInt(6);
				data[ModelArrsThreaded.WAIT_AVG][q] += (double)rand;
				data[ModelArrsThreaded.TOT_BITE_AVG][q]++;
				mosq_arr[m].setBiteTime(0); //modify this for bite time changes
			}
			move(mosquito);
		}
		else if(move_rand == 2) {
			return;
		}
	}
	
	private void move(Organism organism) {
		int x, y, oldX, oldY;
		double mean = 0.0;
		
		oldX = organism.getXpos();
		oldY = organism.getYpos();
		
		if(organism.isMosquito()) {
			mean = 2.0;
		}
		else if(organism.isHuman()) {
			mean = 6.0;
		}
		else if(organism.isEmpty()) {
			return;
		}
		
		x = getNewCoord(oldX, mean, width, true);
		y = getNewCoord(oldY, mean, length, false);
		
		if(organism.isMosquito()) {
			organism.updatePos(x, y);
		}
		else if(x < width && y < width && x > 0 && y > 0 && human_mat[x][y] == false) {
			organism.updatePos(x, y);
			human_mat[x][y] = true;
			human_mat[oldX][oldY] = false;
		}
		
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
				
				if(human_mat[newX][newY] == true)
				{
					mosq.bitecount++;
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int getNewCoord(int old, double mean, int dim, boolean xORy) //Using x = r*cos(t) or y = r*sin(t) parametric formula.
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
	
}