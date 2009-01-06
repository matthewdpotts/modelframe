package model.pollination;

import java.util.*;

import model.util.MersenneTwisterFast;

public class Pollinator {

	public static MersenneTwisterFast mt = new MersenneTwisterFast();
	private ArrayList<PollenGrain> pollen;
	private int num_grains ;
	private boolean first_visit;
	public int type_pollinator, xdim, ydim, id, num_visits; 
	public double pollen_loss_rate, amount_pollen, pollen_loss_pickup;
	
	
	public Pollinator(int id, int type, double pollen_loss_rate, int nv) {
		this.type_pollinator = type;
		this.pollen_loss_rate = pollen_loss_rate;
		this.num_visits = nv;
		this.id = id;
		first_visit = true;
		num_grains = 0;
		pollen = new  ArrayList<PollenGrain>(0);
	}
	
	public void move(ArrayList<Plant> plants, int num_plants) {
		int i = mt.nextInt(num_plants); // should not hard code number of plants //
		Plant visit = plants.get(i);
	//	visit.PrintPlant();
		boolean visited = visit_plant(visit);
		if(visited) 
		{
			depositPollen(visit, plants);
			receivePollen(visit, plants);
			this.num_visits--;
	//		visit.PrintPlant();
		}
		else 
		{
			move(plants, num_plants);
		}
		losePollen(plants);
	}
	
	/** Check here to ensure visiting is working as desired **/
	private boolean visit_plant(Plant visit) {
		double rannum = mt.nextDouble();
		double scaler = 10./(visit.max_attract - visit.min_attract);
		//System.out.println(((double) (visit.attract_a/scaler) + visit.min_attract)*0.1);
		
		if(type_pollinator == 1 && ((double) (visit.attract_a/scaler) + visit.min_attract)*0.1 > rannum) {
			return true;
		}
		else if(type_pollinator == 2 && ((double) (visit.attract_b/scaler) + visit.min_attract)*0.1  > rannum) {
			return true;
		}
		return false;
	}

	/** Take a look here to make sure pollen depositing is going as expected. **/
	private void depositPollen(Plant visit, ArrayList<Plant> plants) 
	{
		//int sum_a = visit.fit_a;
		//int sum_b = visit.fit_b;
		int sum_a = 5;
		int sum_b = 5;
		
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

		//int sum_a = visit.fit_a;
		//int sum_b = visit.fit_b;
		int sum_a = 5;
		int sum_b = 5;
		PollenGrain grain;
		
		if(type_pollinator == 1) {
			for(int i = 0; i <  sum_a*10 && visit.num_pollen_grains != 0; i++) {
				grain = visit.givePollen();
				plants.get(grain.plant_id).num_pollen_on_pollinator++;
				pollen.add(grain);
				num_grains++;
			}
		}
		else if(type_pollinator == 2) {
			for(int i = 0; i < sum_b*10 && visit.num_pollen_grains != 0; i++) {
				grain = visit.givePollen();
				plants.get(grain.plant_id).num_pollen_on_pollinator++;
				pollen.add(grain);
				num_grains++;
			}
		}
		
		if(!pollen.isEmpty())
			first_visit = false;
}
	
	
	private void losePollen(ArrayList<Plant> plants)
	{
		int rannum;
		PollenGrain tempgrain;
		if(num_grains > 0) 
		{
			int num_lose = (int)((double)num_grains * pollen_loss_rate);
			for(int i = 0; i < num_lose && !pollen.isEmpty(); i++) 
			{
				rannum = mt.nextInt(num_grains);
				tempgrain = pollen.get(rannum);
				plants.get(tempgrain.plant_id).num_pollen_lost++;
				plants.get(tempgrain.plant_id).num_pollen_on_pollinator--;
				pollen.remove(rannum);
				num_grains--;
			}
		}
	}
}