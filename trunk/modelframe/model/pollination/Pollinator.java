package model.pollination;

import java.util.*;

import model.util.MersenneTwisterFast;

public class Pollinator {

	public static MersenneTwisterFast mt = new MersenneTwisterFast();
	private ArrayList<Integer> pollen;
	private int num_grains;
	private boolean first_visit;
	public int type, xdim, ydim; 
	public double pollen_loss_rate, amount_pollen, pollen_loss_pickup;
	
	
	public Pollinator(int type, double pollen_loss_rate) {
		this.type = type;
		this.pollen_loss_rate = pollen_loss_rate;
		first_visit = true;
		num_grains = 0;
		pollen = new  ArrayList<Integer>(0);
	}
	
	public void move(ArrayList<Plant> plants, int num_plants) {
		int i = mt.nextInt(num_plants); // should not hard code number of plants //
		Plant visit = plants.get(i);
		boolean visited = visit_plant(visit);
		if(visited) 
			{
			depositPollen(visit);
			receivePollen(visit);
			}
		else 
			{
			move(plants, num_plants);
			}
		losePollen();
	}
	
	/** Check here to ensure visiting is working as desired **/
	private boolean visit_plant(Plant visit) {
		double rannum = mt.nextDouble();
		if(type == 1 && ((double) visit.attract_a)*0.1 > rannum) {
			return true;
		}
		else if(type == 2 && ((double) visit.attract_b)*0.1  > rannum) {
			return true;
		}
		return false;
	}

	/** Take a look here to make sure pollen depositing is going as expected. **/
	private void depositPollen(Plant visit) {
		int sum_a = visit.fit_a;
		int sum_b = visit.fit_b;
		int rannum;
		int grain;
		
		
		if(first_visit == true) 
		{
			return;
		}
		else if(type == 1) {
			for(int i = 0; i <  sum_a && !pollen.isEmpty(); i++) {
				if(pollen.isEmpty())
						first_visit = true;
				rannum = mt.nextInt(num_grains);
				grain = pollen.get(rannum);
				pollen.remove(rannum);
				num_grains--;
				visit.receivePollen(grain, type);
			}
		}
		else if(type == 2) {
			for(int i = 0; i < sum_b && !pollen.isEmpty(); i++) {
				if(pollen.isEmpty())
					first_visit = true;
			rannum = mt.nextInt(num_grains);
			grain = pollen.get(rannum);
			pollen.remove(rannum);
			num_grains--;
			visit.receivePollen(grain, type);
			}
		}
}
	
	private void receivePollen(Plant visit) {
		int sum_a = visit.fit_a;
		int sum_b = visit.fit_b;
		int grain;
		
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