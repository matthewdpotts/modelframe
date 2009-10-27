package model.pollination;

import java.io.FileNotFoundException;

import java.util.*;
import model.util.Printer;
import model.util.MersenneTwisterFast;

public class Model {

	public static MersenneTwisterFast mt = new MersenneTwisterFast();
	private ArrayList<Plant> plants;
	private ArrayList<Pollinator> bees;
	private Printer output;
	private Printer output1;
	private String outstring;
	
	public Model(String mfile, String file, String outstring2, int poevolve,  int num_boots, int num_steps, int num_plants, int num_plants_1, int num_plants_2, int num_pollinator_a, int num_pollinator_b,  int num_visits_a, int num_visits_b, int num_ovules_1, int num_ovules_2, int num_flowers_1,int num_flowers_2, int num_pollen_grain_1, int num_pollen_grain_2, double pollen_loss_rate_a, double pollen_loss_rate_b,double conv_tol, double min_attract, double max_attract, double total_pollen, double n, double deposit_rate_a, double deposit_rate_b, double receive_rate_a, double receive_rate_b)
 throws FileNotFoundException {
		plants = new ArrayList<Plant>(0);
		bees = new ArrayList<Pollinator>(0);
	//	output = new Printer(file, "bootn\ttime\tpid\tptype\tfit_a\tfit_b\tattract_a\tattract_b\tporatio", true);
		output1 = new Printer(mfile, "", true);
		outstring = outstring2;
		initPlants(poevolve, num_plants_1,num_plants_2,num_ovules_1, num_ovules_2, num_flowers_1, num_flowers_2, num_pollen_grain_1, num_pollen_grain_2, min_attract, max_attract, total_pollen, n);
		}
	

	public void run(int poevolve, int num_boots, int steps, int num_plants_1, int num_plants_2, int num_polliantor_a, int num_polliantor_b, int num_visits_a, int num_visits_b, double pollen_loss_rate_a, double pollen_loss_rate_b, double conv_tol, double deposit_rate_a, double deposit_rate_b, double receive_rate_a, double receive_rate_b) 
	{
		ArrayList<Plant> new_plants;
		ArrayList<Integer> good_plants_1;
		ArrayList<Integer> good_plants_2;
		ArrayList<Integer> self_plants_1;
		ArrayList<Integer> self_plants_2;
		
		
		Plant plant_temp;
		Plant temp;
		int conv_i = steps;
		int num_plants = num_plants_1+num_plants_2;
		
		
		for(int i = 0; i < steps; i++) 
		{
		
		initBees(num_polliantor_a,num_polliantor_b,num_visits_a, num_visits_b, pollen_loss_rate_a, pollen_loss_rate_b, deposit_rate_a, deposit_rate_b, receive_rate_a, receive_rate_b);	

		int num_pollinators = num_polliantor_a + num_polliantor_b;
			
			while(!bees.isEmpty())
			{
				int rannum = mt.nextInt(num_pollinators);
				bees.get(rannum).move(plants, num_plants);
				if(bees.get(rannum).num_visits == 0)
					{
						bees.remove(rannum);
						num_pollinators--;
						}
			}
		
		/*	if(i % 50 == 0) 
			{ 
				for(int k = 0; k < (num_plants_1+num_plants_2); k++) 
				{
					plant_temp = plants.get(k);
				//	plant_temp.PrintPlant();
	//				if(plant_temp.id == 1) // change this to getjust one plant's pid in file, or remove for to get all plants
						output.printData(Integer.toString(poevolve) + "\t" + Integer.toString(num_boots) + "\t" + Integer.toString(i) + "\t" + Integer.toString(plant_temp.id) + "\t" + Integer.toString(plant_temp.plant_type) 
							+ "\t" + Integer.toString(plant_temp.num_pollen_grains) + "\t" + Integer.toString(plant_temp.num_st_pollen_grains) + 
							"\t" + Integer.toString(plant_temp.num_pollen_right) + "\t" + Integer.toString(plant_temp.num_pollen_wrong) +"\t"+ Integer.toString(plant_temp.num_pollen_lost_con) + "\t"
							+ Integer.toString(plant_temp.num_pollen_lost_het) + "\t"
							+ Integer.toString(plant_temp.num_pollen_on_pollinator) +"\t"
							+ Integer.toString(plant_temp.fit_a) + "\t" + Integer.toString(plant_temp.fit_b) + "\t"
							+ Integer.toString(plant_temp.attract_a) + "\t" + Integer.toString(plant_temp.attract_b) + "\t" + Integer.toString(plant_temp.poratio)
							+  "\t" + Integer.toString(plant_temp.num_ovules) + "\t" + Integer.toString(plant_temp.initial_pollen_grains)
						);
				}

			}*/
			
			good_plants_1 = new ArrayList<Integer>(0);
			good_plants_2 = new ArrayList<Integer>(0);
			self_plants_1 = new ArrayList<Integer>(0);
			self_plants_2 = new ArrayList<Integer>(0);
			new_plants = new ArrayList<Plant>(0);
			int n1 = 0;
			int n2 = 0;
			int sn1 = 0;
			int sn2 = 0;
			
			for(int k = 0; k < (num_plants_1 + num_plants_2); k++)
			{ 
				temp = plants.get(k);
				for(int j = 0; j < Math.min(temp.num_ovules,temp.num_st_pollen_grains); j++)
				{					
					if(temp.plant_type == 1)
					{
						good_plants_1.add(temp.id);
					//	System.out.println("Plant 1" + "\t" + j  + "\t" + temp.id + "\t" + temp.num_ovules + "\t" + temp.num_st_pollen_grains);
						n1++;
					}
					else if(temp.plant_type == 2)
					{
						good_plants_2.add(temp.id);
					//	System.out.println("Plant 2" + "\t" + j  + "\t" + temp.id + "\t" + temp.num_ovules + "\t" + temp.num_st_pollen_grains);
						n2++;
					}
				}
			}
		
			for(int k = 0; k < (num_plants_1 + num_plants_2); k++)
			{ 
				temp = plants.get(k);				
					if(temp.plant_type == 1 && temp.num_ovules > 0)
					{
						self_plants_1.add(temp.id);
						sn1++;
					}
					else if(temp.plant_type == 2 && temp.num_ovules > 0)
					{
						self_plants_2.add(temp.id);
						sn2++;
					}
			}
			
				
			int num_new_plants = 0;
			int num_new_plants_1 = 0;
			int num_new_plants_2 = 0;
			PollenGrain grain_id;
			
		
			
			if((n1 ==0) | (n2 == 0) | (sn1 == 0) | (sn2 ==0)) 
			{ 
				System.out.println(i  + "\t" + n1 + "\t" + n2 + "\t" + sn1 + "\t" + sn2);
				System.out.println(i  + "\t" + num_plants_1 + "\t" + num_plants_2+ "\t" + sn1 + "\t" + sn2);
				for(int k = 0; k < (num_plants_1+num_plants_2); k++) 
				{
					plant_temp = plants.get(k);
				//	plant_temp.PrintPlant();
	//				if(plant_temp.id == 1) // change this to getjust one plant's pid in file, or remove for to get all plants
						System.out.println(Integer.toString(poevolve) + "\t" + Integer.toString(num_boots) + "\t" + Integer.toString(i) + "\t" + Integer.toString(plant_temp.id) + "\t" + Integer.toString(plant_temp.plant_type) 
							+ "\t" + Integer.toString(plant_temp.num_pollen_grains) + "\t" + Integer.toString(plant_temp.num_st_pollen_grains) + 
							"\t" + Integer.toString(plant_temp.num_pollen_right) + "\t" + Integer.toString(plant_temp.num_pollen_wrong) +"\t"+ Integer.toString(plant_temp.num_pollen_lost_con) + "\t"
							+ Integer.toString(plant_temp.num_pollen_lost_het) + "\t"
							+ Integer.toString(plant_temp.num_pollen_on_pollinator) +"\t"
							+ Integer.toString(plant_temp.fit_a) + "\t" + Integer.toString(plant_temp.fit_b) + "\t"
							+ Integer.toString(plant_temp.attract_a) + "\t" + Integer.toString(plant_temp.attract_b) + "\t" + Integer.toString(plant_temp.poratio)
							+  "\t" + Integer.toString(plant_temp.num_ovules) + "\t" + Integer.toString(plant_temp.initial_pollen_grains)
						);
				}

			}
				
			
		//	System.out.println(1 + "\t" + i  + "\t" + n1 + "\t" + n2 + "\t" + sn1 + "\t" + sn2);
			
		//	System.out.println(2 + "\t" + i  + "\t" + n1 + "\t" + n2);

			while((num_new_plants_1 < num_plants_1) && (!good_plants_1.isEmpty())) 
			{
				int rannum = mt.nextInt(n1);
				int tempid = good_plants_1.get(rannum);
				grain_id =  plants.get(tempid).giveStPollen();
				
			//	System.out.println(rannum  + "\t" + tempid + "\t" + grain_id.plant_type+ "\t" + plants.get(tempid).plant_type);
				
				if(grain_id.plant_type == plants.get(tempid).plant_type)
				{
					new_plants.add(plants.get(tempid).reproduce(plants, num_new_plants,grain_id.plant_id));
					good_plants_1.remove(rannum);
					n1--;
					num_new_plants_1++;
					num_new_plants++;
				}
				else 
				{
					good_plants_1.remove(rannum);
					n1--;
				}
				
			}
			int num_self_1 = num_plants_1 - num_new_plants_1;
			
		//	System.out.println("Plant 1" + "\t" + i  + "\t" + n1 + "\t" + n2 + "\t" + sn1 + "\t" + sn2 + "\t" +  num_new_plants_1 + "\t" + num_self_1);
			
			
			for(int iii = 0; iii < num_self_1;iii++)
			{
				int rannum = mt.nextInt(sn1);
				int tempid = self_plants_1.get(rannum);
				new_plants.add(plants.get(tempid).reproduce(plants, num_new_plants, tempid));
				num_new_plants_1++;
				num_new_plants++;
			}
			
		//	System.out.println("Plant 1" + "\t" + i  + "\t" + n1 + "\t" + n2 + "\t" + sn1 + "\t" + sn2 + "\t" +  num_new_plants_1 + "\t" + num_self_1);
			
			
		//	PrintPlants(new_plants, num_new_plants_1, 0);
					
			while((num_new_plants_2 < num_plants_2)  && (!good_plants_2.isEmpty())) 
			{
				int rannum = mt.nextInt(n2);
				int tempid = good_plants_2.get(rannum);
				grain_id =  plants.get(tempid).giveStPollen();
			//	System.out.println(rannum  + "\t" + tempid + "\t" + grain_id.plant_type+ "\t" + plants.get(tempid).plant_type);
				if(grain_id.plant_type == plants.get(tempid).plant_type)
				{
					new_plants.add(plants.get(tempid).reproduce(plants, num_new_plants, grain_id.plant_id));
					good_plants_2.remove(rannum);
					n2--;
					num_new_plants_2++;
					num_new_plants++;
				}
				else
				{
					good_plants_2.remove(rannum);
					n2--;
				}
			}
			
			int num_self_2 = num_plants_2 - num_new_plants_2;
			
		//	System.out.println("Plant 2" + "\t" + i  + "\t" + n1 + "\t" + n2 + "\t" + sn1 + "\t" + sn2 + "\t" +  num_new_plants_2 + "\t" + num_self_2);
			
			
			for(int iii = 0; iii < num_self_2;iii++)
			{
				int rannum = mt.nextInt(sn2);
				int tempid = self_plants_2.get(rannum);
				new_plants.add(plants.get(tempid).reproduce(plants, num_new_plants,tempid));
				num_new_plants_2++;
				num_new_plants++;
			}
			
		//	System.out.println("Plant 2" + "\t" + i  + "\t" + n1 + "\t" + n2 + "\t" + sn1 + "\t" + sn2 + "\t" +  num_new_plants_2 + "\t" + num_self_2);
			
		//	System.out.println(i + "\t" + num_new_plants_1 + "\t" + num_self_1 + "\t" + num_new_plants_2 + "\t" + num_self_2 + "\t" + num_new_plants);
			
		//	System.out.println(i  + "\t" + n1 + "\t" + n2 + "\t" + sn1 + "\t" + sn2 + "\t" +  num_self_1 + "\t" + num_self_2);
				
			
			plants = new_plants;
			
		//	PrintPlants(plants, num_new_plants_1, num_new_plants_2);
		
		//	if(converge(plants, num_plants_1, num_plants_2, conv_tol))
		//	{
		//		conv_i = i;
		//		break;
		//	}
		
		}
		

		PrintPlants(plants, num_plants_1, num_plants_2, outstring, output1, num_boots, steps, conv_i);
		
		
		
		
	}
	
	private void initPlants(int poevolve, int num_plants_1, int num_plants_2, int num_ovules_1, int num_ovules_2, int num_flowers_1,int num_flowers_2, int num_pollen_grain_1, int num_pollen_grain_2, double min_attract, double max_attract, double total_pollen, double n) 
	{
	//	System.out.println(poevolve);
		for(int i = 0; i < num_plants_1; i++) 
		{
			plants.add(new Plant(poevolve, mt.nextInt(11), mt.nextInt(11), mt.nextInt(11),mt.nextInt(11), mt.nextInt(51),  i, 1, num_ovules_1, num_flowers_1, num_pollen_grain_1, min_attract, max_attract, total_pollen, n));
		}
		for(int i = num_plants_1; i < num_plants_1+num_plants_2; i++) 
		{
			plants.add(new Plant(poevolve, mt.nextInt(11), mt.nextInt(11), mt.nextInt(11),mt.nextInt(11), mt.nextInt(51), i, 2,  num_ovules_2, num_flowers_2, num_pollen_grain_2, min_attract, max_attract, total_pollen, n));
		}	
	//	System.out.println("Intialized");
	}

	private void initBees( int npa, int npb, int nva, int nvb, double pla, double plb, double dpra, double dprb, double rra, double rrb)	
	{
		for(int i = 0; i < npa; i++) 
		{
			bees.add(new Pollinator(i,1, pla, nva, dpra,rra));
		}
		for(int i = npa; i < npa+npb; i++) 
		{
			bees.add(new Pollinator(i,2, plb, nvb, dprb,rrb));
		}	
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
		double poratio1 = 0;
		double poratio2 = 0;
		double ove1 = 0;
		double ove2 = 0;
		double pg1 = 0;
		double pg2 = 0;
		
	
		for(int i = 0; i < n1+n2; i++) 
		{
			if(plantlist.get(i).plant_type == 1)
			{
				mp1fa += plantlist.get(i).fit_a;
				mp1fb += plantlist.get(i).fit_b;
				mp1Aa += plantlist.get(i).attract_a;
				mp1Ab += plantlist.get(i).attract_b;
				poratio1 += plantlist.get(i).poratio;
				ove1 += plantlist.get(i).num_ovules;
				pg1 += plantlist.get(i).initial_pollen_grains;
			}
			
			if(plantlist.get(i).plant_type == 2)
			{
				mp2fa += plantlist.get(i).fit_a;
				mp2fb += plantlist.get(i).fit_b;
				mp2Aa += plantlist.get(i).attract_a;
				mp2Ab += plantlist.get(i).attract_b;
				poratio2 += plantlist.get(i).poratio;
				ove2 += plantlist.get(i).num_ovules;
				pg2 += plantlist.get(i).initial_pollen_grains;
			}
		}
		
		output1.printData(outstring + "\t" + num_boots + "\t" + steps + "\t" + actsteps +  "\t" + mp1Aa/n1 + "\t" + mp2Aa/n2 + "\t" + mp1Ab/n1 + "\t" + mp2Ab/n2 + "\t" + mp1fa/n1 + "\t" + mp2fa/n2 + "\t" + mp1fb/n1 + "\t" + mp2fb/n2 + "\t" + poratio1/n1 + "\t" + poratio2/n2 + "\t" + ove1/n1 + "\t" + ove2/n2 + "\t" + pg1/n1 + "\t" + pg2/n2);
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