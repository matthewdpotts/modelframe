package model.pollination;

import java.io.FileNotFoundException;

import model.BagCollection;
import java.util.*;
import model.Printer;
import model.MersenneTwisterFast;

public class Model_MDP {

	public static MersenneTwisterFast mt = new MersenneTwisterFast();
	private ArrayList<Plant_MDP> plants;
	private Pollinator_MDP bee_a, bee_b;
	private Printer output;
	
	public Model_MDP(String file, int num_plants) throws FileNotFoundException {
		plants = new ArrayList<Plant_MDP>(0);
		bee_a = new Pollinator_MDP(1);
		bee_b = new Pollinator_MDP(2);
		output = new Printer(file, "time\tpid\tptype\tfit_a\tfit_b\tattract_a\tattract_b");
		output.printHeader();
		initPlants(num_plants);
	}
	
	public void run(int steps) {
		ArrayList<Plant_MDP> new_plants;
		ArrayList<Integer> good_plants;
		Plant_MDP plant_temp;
		
		for(int i = 0; i < steps; i++) {
			for(int j = 0; j < 500; j++) {
			//	System.out.println(i + "\t" + j);
				if(mt.nextInt(2)+2 % 2 == 0)
					bee_a.move(plants);
				else
					bee_b.move(plants);
			}
			
			System.out.println("got to here");
			
			if(i % 100 == 0) { // change to modify sample rate in time
				for(int k = 0; k < 100; k++) {
					plant_temp = plants.get(k);
	//				if(plant_temp.id == 1) // change this to getjust one plant's pid in file, or remove for to get all plants
						output.printData(Integer.toString(i) + "\t" + Integer.toString(plant_temp.id) + "\t" + Integer.toString(plant_temp.plant_type) 
							+ "\t" + Integer.toString(plant_temp.fit_a) + "\t" + Integer.toString(plant_temp.fit_b) + "\t"
							+ Integer.toString(plant_temp.attract_a) + "\t" + Integer.toString(plant_temp.attract_b));
				}
			}
		
			good_plants = new ArrayList<Integer>(0);
			new_plants = new ArrayList<Plant_MDP>(0);
			int num_plants = 0;
			for(int k = 0;k < 100;k++)
			{ 
				plant_temp = plants.get(k);
				
				Plant_MDP temp = plants.get(k);
				
			//	temp.PrintPlant();
						
				for(int j = 0; j < Math.min(3,plant_temp.num_pollen_grains); j++)
				{
					good_plants.add(plant_temp.id);
					num_plants++;
				}
			}
			
			int num_new_plants = 0;
			
		//	System.out.println(num_plants);
			
			while(num_new_plants < 100) {
				int rannum = mt.nextInt(num_plants);
				int tempid = good_plants.get(rannum);
				Plant_MDP temp = plants.get(tempid);
				new_plants.add(plants.get(tempid).reproduce(plants));
				new_plants.get(num_new_plants).PrintPlant();
				num_new_plants++;
				
			}
			this.plants = new_plants;
		}
	}
	
	private void initPlants(int num_plants) {
		for(int i = 0; i < num_plants/2; i++) 
			{
				plants.add(new Plant_MDP(mt.nextInt(11), mt.nextInt(11), mt.nextInt(11),mt.nextInt(11), i, 1));
			}
		for(int i = num_plants/2; i < num_plants; i++) 
			{
				plants.add(new Plant_MDP(mt.nextInt(11), mt.nextInt(11), mt.nextInt(11),mt.nextInt(11), i, 2));
			}
			
		/*	
		for(int i = 0; i < 100;i++) 
		{
			Plant_MDP temp = plants.get(i);
				
				System.out.println(temp.id + "\t" + temp.plant_type + "\t" + temp.num_pollen_grains + 
						"\t"+ temp.attract_a + "\t" +  temp.attract_b + 
						"\t" + temp.fit_a + "\t" + temp.fit_b
						);
		}*/
	}
	
	
}
