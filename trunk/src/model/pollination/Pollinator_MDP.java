package model.pollination;

import java.util.*;

import model.MersenneTwisterFast;

public class Pollinator_MDP {

	public static MersenneTwisterFast mt = new MersenneTwisterFast();
	private ArrayList<Integer> pollen;
	private int num_grains;
	private boolean first_visit;
	
	public int type, xdim, ydim; 
	public double pollen_loss_rate, amount_pollen, pollen_loss_pickup;
	
	
	public Pollinator_MDP(int type) {
		this.type = type;
		this.pollen_loss_rate = .2;
		first_visit = true;
		num_grains = 0;
		pollen = new  ArrayList<Integer>(0);
	}
	
	public void move(ArrayList<Plant_MDP> plants) {
		int i = mt.nextInt(100); // should not hard code number of plants //
	//	System.out.println(i);
		Plant_MDP visit = plants.get(i);
		boolean visited = visit_plant(visit);
	//	System.out.println("In visit  "+ visited);
		if(visited) {
			depositPollen(visit);
			receivePollen(visit);}
		losePollen();
		//System.out.println("Move\t" + type + "\t" + num_grains + "\t" + visited);
	//	visit.PrintPlant();
		}
	
	
	/** Check here to ensure visiting is working as desired **/
	private boolean visit_plant(Plant_MDP visit) {
		double rannum = mt.nextDouble();
		if(type == 1 && ((double) visit.attract_a)*0.1 > rannum) {
			return true;
		}
		else if(type == 2 && ((double) visit.attract_b)*0.1  > rannum) {
			return true;
		}
		return false;
	}
	/*
	public int[] numEachPollenGrainType() {
		int[] num_types = numPollenTypes();
		int max_id = num_types[1];
		int[] temp_arr = new int[max_id];
		
		while(!pollen.isEmpty()) {
			temp_arr[pollen.get().orig_flower_id]++;
		}
		
		return temp_arr;
	}
	
	private int[] numPollenTypes() {
		BagCollection<Integer> temp_nums = new BagCollection<Integer>();
		int max_id = 0;
		
		while(!pollen.isEmpty()) {
			Pollen temp_pollen = pollen.get();
			if(temp_pollen.orig_flower_id > max_id) 
				max_id = temp_pollen.orig_flower_id;
			temp_nums.add(Integer.valueOf(temp_pollen.orig_flower_id));
		}
		
		int[] temp_return = {temp_nums.size(), max_id};
		
		return temp_return;
	}
	*/
	
	/** Take a look here to make sure pollen depositing is going as expected. **/
	private void depositPollen(Plant_MDP visit) {
		int sum_a = visit.fit_a;
		int sum_b = visit.fit_b;
		int rannum;
		int grain;
		
	//	System.out.println(first_visit + "\t" + num_grains + "\t" + sum_a +  "\t" + sum_b + "\t" + pollen.isEmpty());
		
		if(first_visit == true) 
		{
			return;
		}
		else if(type == 1) {
			for(int i = 0; i <  sum_a && !pollen.isEmpty(); i++) {
			//	System.out.println(num_grains + "\t" + sum_a +  "\t" + sum_b);
				if(pollen.isEmpty())
						first_visit = true;
				rannum = mt.nextInt(num_grains);
				grain = pollen.get(rannum);
				pollen.remove(rannum);
				num_grains--;
			//	System.out.println(grain);
				visit.receivePollen(grain);
			}
		}
		else if(type == 2) {
			for(int i = 0; i < sum_b && !pollen.isEmpty(); i++) {
			//	System.out.println(num_grains + "\t" + sum_a +  "\t" + sum_b);
				if(pollen.isEmpty())
					first_visit = true;
			rannum = mt.nextInt(num_grains);
			grain = pollen.get(rannum);
			pollen.remove(rannum);
			num_grains--;
		//	System.out.println(grain);
			visit.receivePollen(grain);
			}
		}
}
	
	private void receivePollen(Plant_MDP visit) {
		int sum_a = visit.fit_a;
		int sum_b = visit.fit_b;
		int grain;
	//	visit.PrintPlant();
		
		if(type == 1) {
			for(int i = 0; i <  sum_a*10 && visit.num_pollen_grains != 0; i++) {
				grain = visit.givePollen();
				pollen.add(grain);
				num_grains++;
			}
		}
		else if(type == 2) {
			for(int i = 0; i < sum_b*10 && visit.num_pollen_grains != 0; i++) {
				grain = visit.givePollen();
				pollen.add(grain);
				num_grains++;
			}
		}
		
		if(!pollen.isEmpty())
			first_visit = false;
	//	System.out.println("Receive\t" + type + "\t" + num_grains + "\t" + sum_a +  "\t" + sum_b + "\t" + pollen.isEmpty());
	//	visit.PrintPlant();	
}
	
	
	private void losePollen()
	{
		int rannum;
		if(num_grains > 0) 
		{
			int num_lose = (int)((double)num_grains * pollen_loss_rate);
			for(int i = 0; i < num_lose && !pollen.isEmpty(); i++) 
			{
				rannum = mt.nextInt(num_grains);
				pollen.remove(rannum);
				num_grains--;
			}
		}
	}
}