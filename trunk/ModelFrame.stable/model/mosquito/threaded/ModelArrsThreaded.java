
package model.mosquito.threaded;

import java.io.*;
import java.util.*;
import model.*;
import model.mosquito.Human;
import model.mosquito.Mosquito;

import java.util.Random;
import java.text.DecimalFormat;

public class ModelArrsThreaded {
	
	public static final int BITE_NUM = 0;
	public static final int WAIT_AVG = 1;
	public static final int BITE_AVG = 2;
	public static final int TOT_BITE_AVG = 3;
	
	private static final double human_mean = 6.0;
	private static final double mosquito_mean = 2.0;
	
	public static int index = 0;
	
	public int width, length;
	public volatile boolean human_mat[][];
	public Human human_arr[];
	public Mosquito mosq_arr[];
	public double data[][];
	
	private Printer printer_m, printer_s;
	private DecimalFormat formatter;
	private Runtime runner;
	private Random mt;
	private int i, j;
	
	public ModelArrsThreaded(int xdim, int ydim, String[] file_main, String[] file_sec) throws FileNotFoundException	{
		width = xdim;
		length = ydim;
		human_mat = new boolean[width][length];
		runner = Runtime.getRuntime();
		mt = new Random();
		printer_m = new Printer(file_main[0], file_main[1]);
		printer_s = new Printer(file_sec[0], file_sec[1]);
		formatter = new DecimalFormat("#####.####");
	}
	
	public ModelArrsThreaded(int xdim, int ydim, String[] file_main, String[] file_sec, boolean firstRun) throws FileNotFoundException	{
		width = xdim;
		length = ydim;
		human_mat = new boolean[width][length];
		runner = Runtime.getRuntime();
		mt = new Random();
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

		MosqProc mosq_threads[] = new MosqProc[4];
		HumanProc human_threads[] = new HumanProc[4];
		
		int indices[] = new int[4];
		int procSize;
		for(i = 0; i < arr.length; i++) {
			
			procSize = arr[i] / 4; // This will give us the ability to split into 4 threads.
			indices[0] = 0;
			indices[1] = arr[i] / 4;
			indices[2] = arr[i] / 2;
			indices[3] = arr[i] * 3 / 4;
			
			for(j = 0; j < 4; j++) {
				if(i == 0) {
					if(arr[i] % 2 == 0)
						human_threads[j] = new HumanProc(human_arr, indices[j], procSize, human_mat, width, length);
					else {
						if(j < 3)
							human_threads[j] = new HumanProc(human_arr, indices[j], procSize, human_mat, width, length);
						else
							human_threads[j] = new HumanProc(human_arr, indices[j], procSize + 1, human_mat, width, length);
					}
				}
				else {
					if(arr[i] % 2 == 0)
						mosq_threads[j] = new MosqProc(this, mosq_arr, indices[j], procSize, human_mat, width, length);
					else {
						if(j < 3)
							mosq_threads[j] = new MosqProc(this, mosq_arr, indices[j], procSize, human_mat, width, length);
						else
							mosq_threads[j] = new MosqProc(this, mosq_arr, indices[j], procSize+1, human_mat, width, length);
					}
				}
			}
		}
		
		
		for(ModelArrsThreaded.index = 0; ModelArrsThreaded.index < steps; ModelArrsThreaded.index++) {
			for(i = 0; i < 4; i++) {
				new Thread(human_threads[i]).start();
				new Thread(mosq_threads[i]).start();
			}
			if(ModelArrsThreaded.index % 100 == 0) {
				runner.gc();
			}			
		}
		
		processData(data, steps, num_h, num_m);
	}
	
	private void processData(double data[][], int steps, int num_h, int num_m) {
		
		for(j = 0; j < steps; j++) {
			data[WAIT_AVG][j] = data[WAIT_AVG][j] / (double)num_m;
			if(j % 100 == 0) {
				data[BITE_AVG][j] = data[BITE_AVG][i] / 100.0;
				printer_m.printData(Integer.toString(j)+"\t"+Double.toString(data[BITE_NUM][j])+"\t"+formatter.format(data[WAIT_AVG][j])+"\t"+Double.toString(data[BITE_AVG][j]));
			}
			else {
				data[ModelArrsThreaded.BITE_AVG][i] = -100.0;
				printer_m.printData(Integer.toString(j)+"\t"+Double.toString(data[BITE_NUM][j])+"\t"+formatter.format(data[WAIT_AVG][j])+"\t"+Double.toString(data[BITE_AVG][j]));
			}
		}
		
		printer_s.printData(Integer.toString(num_h)+"\t"+Double.toString(data[TOT_BITE_AVG][steps-1]/(double)steps));
		
		printer_m.closeFile();
		printer_s.closeFile();

	}
	
	private void populate(int numSpecs[]) {
		int x, y;
		Coord tempPos;
		HashSet<Coord> coords = new HashSet<Coord>();
		
		index = 0;
		
		for(x = 0; x < width; x++) {
			for(y = 0; y < length; y++) {
				coords.add(new Coord(x, y));
			}
		}
		
		Iterator<Coord> itr = coords.iterator();
		
		while(i < numSpecs[0] && itr.hasNext()) {
			tempPos = (Coord) itr.next();
			x = tempPos.getX();
			y = tempPos.getY();
			human_mat[x][y] = true;
			human_arr[i++] = new Human(x, y, human_mean);
		}
		
		for(i = 0; i < numSpecs[1]; i++) {
			x = mt.nextInt(width);
			y = mt.nextInt(length);
			mosq_arr[i] = new Mosquito(x, y, mosquito_mean);
		}
		
		runner.gc();
	}
	
	
	
	public int getIndex(int x, int y) {
		return x + y*width + 1;
	}
}