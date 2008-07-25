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
	private Printer output1;
	private String outstring;
	
	public Model(String mfile, String file, String outstring2,  int num_boots, int num_steps, int num_plants, int num_plants_1, int num_plants_2, int num_visits_a, int num_visits_b, int num_ovules_1, int num_ovules_2, int num_flowers_1,int num_flowers_2, int num_pollen_grain_1, int num_pollen_grain_2, double pollen_loss_rate_a, double pollen_loss_rate_b)
 throws FileNotFoundException {
		plants = new ArrayList<Plant>(0);
		bee_a = new Pollinator(1,pollen_loss_rate_a);
		bee_b = new Pollinator(2,pollen_loss_rate_b);
		output = new Printer(file, "bootn\ttime\tpid\tptype\tfit_a\tfit_b\tattract_a\tattract_b", true);
		output1 = new Printer(mfile, "", true);
	//	output.printHeader();
		outstring = outstring2;
		initPlants(num_plants_1,num_plants_2,num_ovules_1, num_ovules_2, num_flowers_1, num_flowers_2, num_pollen_grain_1, num_pollen_grain_2);
	}
	
	public void run( int num_boots, int steps, int num_plants_1, int num_plants_2, int num_visits_a, int num_visits_b, double conv_tol) 
	{
		ArrayList<Plant> new_plants;
		ArrayList<Integer> good_plants_1;
		ArrayList<Integer> good_plants_2;
		Plant plant_temp;
		Plant temp;
		int conv_i = steps;
		
		//for(int i = 0;i < 10; i++)
			//System.out.println(i + "\t" + (mt.nextInt(2)+2 % 2) );
		
		for(int i = 0; i < steps; i++) 
		{
		//	System.out.println(i + "\t" + "plants.size(): " + plants.size());
			for(int j = 0; j < (num_visits_a+num_visits_b); j++) 
			{
				if((mt.nextInt(2)+2 % 2) == 0)
					bee_a.move(plants, (num_plants_1+num_plants_2));
				else
					bee_b.move(plants, (num_plants_1+num_plants_2));
			}
			
			if(i % 100 == 0) 
			{ 
				for(int k = 0; k < (num_plants_1+num_plants_2); k++) 
				{
					plant_temp = plants.get(k);
				//	plant_temp.PrintPlant();
	//				if(plant_temp.id == 1) // change this to getjust one plant's pid in file, or remove for to get all plants
						output.printData(Integer.toString(num_boots) + "\t" + Integer.toString(i) + "\t" + Integer.toString(plant_temp.id) + "\t" + Integer.toString(plant_temp.plant_type) 
							+ "\t" + Integer.toString(plant_temp.num_pollen_grains) + "\t" + Integer.toString(plant_temp.num_st_pollen_grains) + "\t" + Integer.toString(plant_temp.fit_a) + "\t" + Integer.toString(plant_temp.fit_b) + "\t"
							+ Integer.toString(plant_temp.attract_a) + "\t" + Integer.toString(plant_temp.attract_b));
				}

			}
				
			good_plants_1 = new ArrayList<Integer>(0);
			good_plants_2 = new ArrayList<Integer>(0);
			new_plants = new ArrayList<Plant>(0);
			int n1 = 0;
			int n2 = 0;
			
			for(int k = 0; k < (num_plants_1 + num_plants_2); k++)
			{ 
				temp = plants.get(k);
				for(int j = 0; j < Math.min(3,temp.num_st_pollen_grains); j++)
				{					
					if(temp.plant_type == 1)
					{
						good_plants_1.add(temp.id);
						n1++;
					}
					else if(temp.plant_type == 2)
					{
						good_plants_2.add(temp.id);
						n2++;
					}
				}
			}
			
			int num_new_plants = 0;
			int num_new_plants_1 = 0;
			int num_new_plants_2 = 0;
			
			int num_self_1 = num_plants_1 - n1;
			int num_self_2 = num_plants_2 - n2;
			
		//	System.out.println(i  + "\t" + n1 + "\t" + n2 + "\t" + num_self_1 + "\t" + num_self_2);
			
		//	System.out.println(i  + "\t" + n1 + "\t" + n2);

			while(num_new_plants_1 < num_plants_1 && !good_plants_1.isEmpty()) 
			{
				int rannum = mt.nextInt(n1);
				int tempid = good_plants_1.get(rannum);
			//	temp = plants.get(tempid);
				new_plants.add(plants.get(tempid).reproduce(plants, num_new_plants));
				good_plants_1.remove(rannum);
				n1--;
				num_new_plants_1++;
				num_new_plants++;
			}
		
			for(int iii = 0; iii < num_self_1;iii++)
			{
				int rannum = mt.nextInt(num_plants_1);
			//	temp = plants.get(rannum);
				new_plants.add(plants.get(rannum).self(plants, num_new_plants));
				num_new_plants_1++;
				num_new_plants++;
			}
			
		//	PrintPlants(new_plants, num_new_plants_1, 0);
					
			while(num_new_plants_2 < num_plants_2  && !good_plants_2.isEmpty()) 
			{
				int rannum = mt.nextInt(n2);
				int tempid = good_plants_2.get(rannum);
			//	temp = plants.get(tempid);
				new_plants.add(plants.get(tempid).reproduce(plants, num_new_plants));
				good_plants_2.remove(rannum);
				n2--;
				num_new_plants_2++;
				num_new_plants++;
			}
			
			for(int iii = 0; iii < num_self_2;iii++)
			{
				int rannum = mt.nextInt(num_plants_2) + num_plants_1;
			//	temp = plants.get(rannum);
				new_plants.add(plants.get(rannum).self(plants, num_new_plants));
				num_new_plants_2++;
				num_new_plants++;
			}
			
			
		//	System.out.println(i + "\t" + num_new_plants_1 + "\t" + num_self_1 + "\t" + num_new_plants_2 + "\t" + num_self_2 + "\t" + num_new_plants);
			
			plants = new_plants;
			
		//	PrintPlants(plants, num_new_plants_1, num_new_plants_2);
		
			if(converge(plants, num_plants_1, num_plants_2, conv_tol))
			{
				conv_i = i;
				break;
			}
		
		}
		PrintPlants(plants, num_plants_1, num_plants_2, outstring, output1, num_boots, steps, conv_i);
		
		
		
		
	}
	
	private void initPlants(int num_plants_1, int num_plants_2, int num_ovules_1, int num_ovules_2, int num_flowers_1,int num_flowers_2, int num_pollen_grain_1, int num_pollen_grain_2) 
	{
		for(int i = 0; i < num_plants_1; i++) 
		{
			plants.add(new Plant(mt.nextInt(11), mt.nextInt(11), mt.nextInt(11),mt.nextInt(11), i, 1, num_ovules_1, num_flowers_1, num_pollen_grain_1));
		}
		for(int i = num_plants_1; i < num_plants_1+num_plants_2; i++) 
		{
			plants.add(new Plant(mt.nextInt(11), mt.nextInt(11), mt.nextInt(11),mt.nextInt(11), i, 2,  num_ovules_2, num_flowers_2, num_pollen_grain_2));
		}	
	//	System.out.println("Intialized");
	}



private void PrintPlants(ArrayList<Plant> plantlist, int n1, int n2, String outstring, Printer output1, int num_boots, int steps, int actsteps)
	{
		
		double mp1fa = 0;
		double mp1fb = 0;
		double mp1Aa = 0;
		double mp1Ab = 0;
		double mp2Aa = 0;
		double mp2Ab = 0;
		double mp2fa = 0;
		double mp2fb = 0;
	
		for(int i = 0; i < n1+n2; i++) 
		{
			if(plantlist.get(i).plant_type == 1)
			{
				mp1fa += plantlist.get(i).fit_a;
				mp1fb += plantlist.get(i).fit_b;
				mp1Aa += plantlist.get(i).attract_a;
				mp1Ab += plantlist.get(i).attract_b;
			}
			
			if(plantlist.get(i).plant_type == 2)
			{
				mp2fa += plantlist.get(i).fit_a;
				mp2fb += plantlist.get(i).fit_b;
				mp2Aa += plantlist.get(i).attract_a;
				mp2Ab += plantlist.get(i).attract_b;
			}
		}
		
		output1.printData(outstring + "\t" + num_boots + "\t" + steps + "\t" + actsteps +  "\t" + mp1Aa/n1 + "\t" + mp2Aa/n2 + "\t" + mp1Ab/n1 + "\t" + mp2Ab/n2 + "\t" + mp1fa/n1 + "\t" + mp2fa/n2 + "\t" + mp1fb/n1 + "\t" + mp2fb/n2);
	}




private boolean converge(ArrayList<Plant> plantlist, int n1, int n2, double conv_tol)
{
	

	double mp1Aa = 0;
	double mp1Ab = 0;
	double mp2Aa = 0;
	double mp2Ab = 0;


	for(int i = 0; i < n1+n2; i++) 
	{
		if(plantlist.get(i).plant_type == 1)
		{
			mp1Aa += plantlist.get(i).attract_a;
			mp1Ab += plantlist.get(i).attract_b;
		}
		
		if(plantlist.get(i).plant_type == 2)
		{
			mp2Aa += plantlist.get(i).attract_a;
			mp2Ab += plantlist.get(i).attract_b;
		}
	}
	
	if((mp1Aa/n1 < conv_tol) && (mp2Aa/n2 < conv_tol) || (mp1Ab/n1 < conv_tol) && (mp2Ab/n2 < conv_tol))
	{
		return true;
	}
	
	return false;
}

}