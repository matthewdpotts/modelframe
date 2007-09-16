package model.pollination;

import model.BagCollection;

public class Pollinator {

	private BagCollection<Pollen> pollen;
	private boolean first_visit;
	
	public int type, xdim, ydim; 
	public double pollen_loss_rate, amount_pollen, pollen_loss_pickup;
	
	
	public Pollinator(int type) {
		this.type = type;
		this.pollen_loss_rate = .2;
		first_visit = true;
		pollen = new BagCollection<Pollen>();
	}
	
	public void move(BagCollection<Plant> plants) {
		Plant visit = null;
		if(!plants.isEmpty())
			visit = plants.get();
		else 
			return;
		boolean visited = visit_plant(visit);
		if(visited) {
			depositPollen(visit);
		}
		else {
			move(plants);
		}
	}
	
	private boolean visit_plant(Plant visit) {
		double a1sum_a = (double)visit.attract_a.getGeneSum(1) * 0.1;
		double a1sum_b = (double)visit.attract_b.getGeneSum(1) * 0.1;
		double a2sum_a = (double)visit.attract_a.getGeneSum(2) * 0.1;
		double a2sum_b = (double)visit.attract_b.getGeneSum(2) * 0.1;
		
		if(type == 1 && a1sum_a > a2sum_a && a1sum_b > a2sum_b) {
			return true;
		}
		else if(type == 2 && a1sum_a < a2sum_a && a1sum_b < a2sum_b) {
			return true;
		}
		return false;
	}
	
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
	
	private void depositPollen(Plant visit) {
		int f1sum_a = visit.fit_a.getGeneSum(1);
		int f2sum_a = visit.fit_a.getGeneSum(2);
		int f1sum_b = visit.fit_b.getGeneSum(1);
		int f2sum_b = visit.fit_b.getGeneSum(2);
		
		if(first_visit == true) {
			return;
		}
		else if(type == 1) {
			for(int i = 0; i < (f1sum_a + f1sum_b)/2 && !pollen.isEmpty(); i++) {
				if(!pollen.isEmpty())
						first_visit = true;
				visit.receivePollen(pollen.getAndRemove());
				pollen.add(visit.givePollen());
			}
		}
		else if(type == 2) {
			for(int i = 0; i < (f2sum_a + f2sum_b)/2 && !pollen.isEmpty(); i++) {
				if(!pollen.isEmpty())
						first_visit = true;
				visit.receivePollen(pollen.getAndRemove());
				pollen.add(visit.givePollen());
			}
		}
		if(pollen.size() > 0) {
			int num_lose = (int)((double)pollen.size() * pollen_loss_pickup);
			for(int i = 0; i < num_lose && !pollen.isEmpty(); i++)
				pollen.getAndRemove();
		}
		
	}
	
}
