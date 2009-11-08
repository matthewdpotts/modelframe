package model.pollination;

import java.util.*;

import model.util.MersenneTwisterFast;

public class Pollinator {

	//pollinator class.  Has many attributes - not all used in current model formulation.
	//Refer to paper for full details. 
	
	public static MersenneTwisterFast mt = new MersenneTwisterFast();
	private ArrayList<PollenGrain> pollen;
	private int num_grains ;
	private boolean first_visit;
	public int type_pollinator, xdim, ydim, id, num_visits; 
	public double pollen_loss_rate, amount_pollen, pollen_loss_pickup, deposit_rate, receive_rate;
	
	public Pollinator(int id, int type, double pollen_loss_rate, int nv, double deposit_rate, double receive_rate) {
		//Construct a pollinator
		this.type_pollinator = type;
		this.pollen_loss_rate = pollen_loss_rate;
		this.deposit_rate = deposit_rate;
		this.receive_rate = receive_rate;
		this.num_visits = nv;
		this.id = id;
		first_visit = true;
		num_grains = 0;
		pollen = new  ArrayList<PollenGrain>(0);
	}
	
	public void move(ArrayList<Plant> plants, int num_plants) {
		//Method to pick a random plant and check if a visit occurs (visit_plant) and if so receive (receivePollen) and give (depositPollen)
		// occur as well as grooms off after a visit (losePollen).  
		int i = mt.nextInt(num_plants); 
		Plant visit = plants.get(i);
		boolean visited = visit_plant(visit);
		if(visited) 
		{
			depositPollen(visit, plants);
			receivePollen(visit, plants);
			losePollen(plants, visit);
			this.num_visits--;
		}
		else 
		{
			this.num_visits--;
		}
	}
	
	private boolean visit_plant(Plant visit) {
		//Method that checks whether or not a given pollinator visits a given plant.  Allows for the inclusion of filter
		//traits if desired - i.e. plant attractions to a pollinator cannot increase/decrease above/below a certain
		//amount. 
		double rannum = mt.nextDouble();
		double t_attract;
		
		t_attract= visit.attract_a;
		if(t_attract > visit.max_attract)
			t_attract = visit.max_attract;
		if(t_attract < visit.min_attract)
			t_attract = visit.min_attract;
			
		if(type_pollinator == 1)
		{
			t_attract= visit.attract_a;
			if(t_attract > visit.max_attract)
				t_attract = visit.max_attract;
			if(t_attract < visit.min_attract)
				t_attract = visit.min_attract;
			if((double) (t_attract) *.1 > rannum)
			{
				return true;
			}
		}
		else if (type_pollinator == 2)	
			{
				t_attract= visit.attract_b;
				if(t_attract > visit.max_attract)
					t_attract = visit.max_attract;
				if(t_attract < visit.min_attract)
					t_attract = visit.min_attract;
				if((double) (t_attract) *.1 > rannum)
				{
					return true;
				}
			}
		return false;
	}

	
	private void depositPollen(Plant visit, ArrayList<Plant> plants) 
	{
		// Method to deposit a set number of pollen grain (deposit rate) based on pollinator type. 
		// Pollinator deposits the lesser of the receive rate or amount of pollen it is currently carrying
		int sum_a = (int) this.deposit_rate;
		int sum_b = (int) this.deposit_rate;
		
		int rannum;
		PollenGrain grain;
		
		if(first_visit == true) 
		{
			return;
		}
		else if(type_pollinator  == 1) 
		{
			for(int i = 0; i <  sum_a && !pollen.isEmpty(); i++) 
			{
				if(pollen.isEmpty())
						first_visit = true;
				rannum = mt.nextInt(num_grains);
				grain = pollen.get(rannum);
				pollen.remove(rannum);
				num_grains--;
				visit.receivePollen(grain);
				if(grain.plant_type == visit.plant_type)
				{
					plants.get(grain.plant_id).num_pollen_right++;
					plants.get(grain.plant_id).num_pollen_on_pollinator--;
				}
				else
				{
					plants.get(grain.plant_id).num_pollen_wrong++;
					plants.get(grain.plant_id).num_pollen_on_pollinator--;
				}
			}
		}
		else if(type_pollinator  == 2) 
		{
			for(int i = 0; i < sum_b && !pollen.isEmpty(); i++) 
			{
				if(pollen.isEmpty())
					first_visit = true;
			rannum = mt.nextInt(num_grains);
			grain = pollen.get(rannum);
			pollen.remove(rannum);
			num_grains--;
			visit.receivePollen(grain);
			if(grain.plant_type == visit.plant_type)
			{
				plants.get(grain.plant_id).num_pollen_right++;
				plants.get(grain.plant_id).num_pollen_on_pollinator--;
			}
			else
			{
				plants.get(grain.plant_id).num_pollen_wrong++;
				plants.get(grain.plant_id).num_pollen_on_pollinator--;
			}
			}
		}
	}


	private void receivePollen(Plant visit, ArrayList<Plant> plants) {
		//Method to have pollinator get pollen from a plant based on receive rate.
		//Pollinator gets the lesser of the receive rate or amount of pollen on the plant
		int sum_a = (int) this.receive_rate;
		int sum_b = (int) this.receive_rate;
		PollenGrain grain;
		
		if(type_pollinator == 1) {
			for(int i = 0; i <  sum_a && visit.num_pollen_grains != 0; i++) {
				grain = visit.givePollen();
				plants.get(grain.plant_id).num_pollen_on_pollinator++;
				pollen.add(grain);
				num_grains++;
			}
		}
		else if(type_pollinator == 2) {
		for(int i = 0; i < sum_b && visit.num_pollen_grains != 0; i++) {
				grain = visit.givePollen();
				plants.get(grain.plant_id).num_pollen_on_pollinator++;
				pollen.add(grain);
				num_grains++;
			}
		}
		
		if(!pollen.isEmpty())
			first_visit = false;
}
	

	private void losePollen(ArrayList<Plant> plants, Plant visit)
	{
		//Method to model pollen loss caused by grooming after a visit. 
		int rannum;
		PollenGrain tempgrain;
		if(num_grains > 0) 
		{
			int num_lose = (int)((double)num_grains * pollen_loss_rate);
			for(int i = 0; i < num_lose && !pollen.isEmpty(); i++) 
			{
				rannum = mt.nextInt(num_grains);
				tempgrain = pollen.get(rannum);
				if(tempgrain.plant_type == visit.plant_type)
				{
					plants.get(tempgrain.plant_id).num_pollen_lost_con++;
				}
				else
				{
					plants.get(tempgrain.plant_id).num_pollen_lost_het++;
				}
				plants.get(tempgrain.plant_id).num_pollen_on_pollinator--;
				pollen.remove(rannum);
				num_grains--;
			}
		}
	}
}