package model.pollination;

import java.io.FileNotFoundException;

import java.util.*;
import model.util.Printer;
import model.util.MersenneTwisterFast;

public class Model {

	public static MersenneTwisterFast mt = new MersenneTwisterFast();
	private ArrayList<Plant> plants;
	private Pollinator bee_a, bee_b;
	private Printer output;
	
	public Model(String file, int num_steps, int num_plants, int num_plants_1, int num_plants_2, int num_visits_a, int num_visits_b, int num_ovules_1, int num_ovules_2, int num_flowers_1,int num_flowers_2, int num_pollen_grain_1, int num_pollen_grain_2, double pollen_loss_rate_a, double pollen_loss_rate_b)
 throws FileNotFoundException {
		plants = new ArrayList<Plant>(0);
		bee_a = new Pollinator(1,pollen_loss_rate_a);
		bee_b = new Pollinator(2,pollen_loss_rate_b);
		output = new Printer(file, "time\tpid\tptype\tfit_a\tfit_b\tattract_a\tattract_b");
		output.printHeader();
		initPlants(num_plants_1,num_plants_2,num_ovules_1, num_ovules_2, num_flowers_1, num_flowers_2, num_pollen_grain_1, num_pollen_grain_2);
	}
	
	public void run(int steps, int num_plants_1, int num_plants_2, int num_visits_a, int num_visits_b) 
	{
		ArrayList<Plant> new_plants;
		ArrayList<Integer> good_plants;
		ArrayList<Integer> good_plants_1;
		ArrayList<Integer> good_plants_2;
		Plant plant_temp;
		Plant temp;
		
		for(int i = 0; i < steps; i++) 
		{
			System.out.println("plants.size(): " + plants.size());
			for(int j = 0; j < (num_visits_a+num_visits_b); j++) 
			{
				if(mt.nextInt(2)+2 % 2 == 0)
					bee_a.move(plants, (num_plants_1+num_plants_2));
				else
					bee_b.move(plants, (num_plants_1+num_plants_2));
			}

			
			if(i % 100 == 0) 
			{ 
				for(int k = 0; k < (num_plants_1+num_plants_2); k++) 
				{
					plant_temp = plants.get(k);
	//				if(plant_temp.id == 1) // change this to getjust one plant's pid in file, or remove for to get all plants
						output.printData(Integer.toString(i) + "\t" + Integer.toString(plant_temp.id) + "\t" + Integer.toString(plant_temp.plant_type) 
							+ "\t" + Integer.toString(plant_temp.fit_a) + "\t" + Integer.toString(plant_temp.fit_b) + "\t"
							+ Integer.toString(plant_temp.attract_a) + "\t" + Integer.toString(plant_temp.attract_b));
				}
			}
		
			good_plants = new ArrayList<Integer>(0);
			good_plants_1 = new ArrayList<Integer>(0);
			good_plants_2 = new ArrayList<Integer>(0);
			new_plants = new ArrayList<Plant>(0);
			int num_plants = 0;
			int other_num_plants_1 = num_plants_1;
			int other_num_plants_2 = num_plants_2;
			num_plants_1 = 0;
			num_plants_2 = 0;
					
			for(int k = 0; k < (other_num_plants_1 + other_num_plants_2); k++)
			{ 
				temp = plants.get(k);
				for(int j = 0; j < Math.min(3,temp.num_st_pollen_grains); j++)
				{					
					if(temp.plant_type == 1)
					{
						good_plants_1.add(temp.id);
						num_plants_1++;
					}
					else if(temp.plant_type == 2)
					{
						good_plants_2.add(temp.id);
						num_plants_2++;
					}
					good_plants.add(temp.id);
					num_plants++;
				}
			}
			
			int num_new_plants = 0;
			int num_new_plants_1 = 0;
			int num_new_plants_2 = 0;
			
			System.out.println(i + "\t" + num_plants + "\t" + num_plants_2 + "\t" + num_plants_2);
		/*	
			while(num_new_plants < 100) {
				int rannum = mt.nextInt(num_plants);
				int tempid = good_plants.get(rannum);
			//	System.out.println(num_new_plants + "\t" + rannum + "\t" + tempid );
				Plant temp = plants.get(tempid);
			//	temp.PrintPlant();
				new_plants.add(plants.get(tempid).reproduce(plants, num_new_plants));
				good_plants.remove(rannum);
				num_plants--;
			//	new_plants.get(num_new_plants).PrintPlant();
				num_new_plants++;
				
			}
			*/
			while(num_new_plants_1 < num_plants_1 & !good_plants_1.isEmpty()) 
			{
				int rannum = mt.nextInt(num_plants_1);
				int tempid = good_plants_1.get(rannum);
				temp = plants.get(tempid);
				new_plants.add(plants.get(tempid).reproduce(plants, num_new_plants));
				good_plants_1.remove(rannum);
				num_plants_1--;
				num_new_plants_1++;
				num_new_plants++;
			}
			
//			int num_self_1 = 50 - num_new_plants_1;
	
			while(num_new_plants_2 < num_plants_2  & !good_plants_2.isEmpty()) 
			{
				int rannum = mt.nextInt(num_plants_2);
				int tempid = good_plants_2.get(rannum);
				temp = plants.get(tempid);
				new_plants.add(plants.get(tempid).reproduce(plants, num_new_plants));
				good_plants_2.remove(rannum);
				num_plants_2--;
				num_new_plants_2++;
				num_new_plants++;
			}
	
//			int num_self_2 = 50 - num_new_plants_2;
			
		//	System.out.println(i + "\t" + num_new_plants_1 + "\t" + num_self_1 + "\t" + num_new_plants_2 + "\t" + num_self_2);
			
			
			this.plants = new_plants;
		}
	}
	
	private void initPlants(int num_plants_1, int num_plants_2, int num_ovules_1, int num_ovules_2, int num_flowers_1,int num_flowers_2, int num_pollen_grain_1, int num_pollen_grain_2) 
	{
		for(int i = 0; i < num_plants_1; i++) 
		{
			plants.add(new Plant(mt.nextInt(11), mt.nextInt(11), mt.nextInt(11),mt.nextInt(11), i, 1, num_ovules_1, num_flowers_1, num_pollen_grain_1));
		}
		for(int i = 0; i < num_plants_2; i++) 
		{
			plants.add(new Plant(mt.nextInt(11), mt.nextInt(11), mt.nextInt(11),mt.nextInt(11), i, 2,  num_ovules_2, num_flowers_2, num_pollen_grain_2));
		}	
		
		System.out.println("plants.size(): " + plants.size());
	}
	
	
}
