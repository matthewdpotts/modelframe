package model.pollination;

import model.BagCollection;

public class Plant {

	public int id, plant_type; 
	public DiploidGene attract_a, attract_b, fit_a, fit_b;
	
	
	private BagCollection<Ovule> ovules;
	private BagCollection<Pollen> pollen;
	private int num_flowers, num_pollen_grains, num_ovules;
//	private int[] num_pollen_types;  -- Do we need this if we have the pollenTypes method?
	
	public Plant(DiploidGene attract_a, DiploidGene attract_b, DiploidGene fit_a, DiploidGene fit_b, int id, int plant_type) {
		this.id = id;
		this.attract_a = attract_a;
		this.attract_b = attract_b;
		this.fit_a = fit_a;
		this.fit_b = fit_b;
		this.plant_type = plant_type;
		num_flowers = 1;
		num_pollen_grains = 100;
		num_ovules = 3;
		pollen = new BagCollection<Pollen>();
		ovules = new BagCollection<Ovule>();
		initPollen();
		initOvules();
	}
	
	/** Problems may arise here! **/
	public Plant reproduce() {
		Ovule new_ovule = null;
		Pollen new_pollen = null;
		int a1sum_a, a2sum_a, a1sum_b, a2sum_b, f1sum_a, f2sum_a, f1sum_b, f2sum_b;
		
		if(!ovules.isEmpty() && !pollen.isEmpty()) {
			new_ovule = ovules.getAndRemove();
			new_pollen = pollen.getAndRemove();
			a1sum_a = new_ovule.attract_a.getGeneSum();
			a2sum_a = new_pollen.attract_a.getGeneSum();
			a1sum_b = new_ovule.attract_b.getGeneSum();
			a2sum_b = new_pollen.attract_b.getGeneSum();
			f1sum_a = new_ovule.fit_a.getGeneSum();
			f2sum_a = new_pollen.fit_a.getGeneSum();
			f1sum_b = new_ovule.fit_b.getGeneSum();
			f2sum_b = new_pollen.fit_b.getGeneSum();
			return new Plant(new DiploidGene(a1sum_a, a2sum_a), new DiploidGene(a1sum_b, a2sum_b), 
					new DiploidGene(f1sum_a, f2sum_a), new DiploidGene(f1sum_b, f2sum_b), id, plant_type);
		}
		
		return null;
		
	}
	
	public Pollen givePollen() {
		Pollen temp = null;
		if(!pollen.isEmpty())
			temp = pollen.getAndRemove();
		return temp;
	}
	
	public void receivePollen(Pollen temp) {
		pollen.add(temp);
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

	private void initPollen() {
		for(int i = 0; i < num_pollen_grains; i++) {
			pollen.add(new Pollen(id, attract_a.produceHaploid(), attract_b.produceHaploid(), fit_a.produceHaploid(), fit_b.produceHaploid()));
		}
	}
	
	private void initOvules() {
		for(int i = 0; i < num_ovules; i++) {
			ovules.add(new Ovule(id, attract_a.produceHaploid(), attract_b.produceHaploid(), fit_a.produceHaploid(), fit_b.produceHaploid()));
		}
		
	}
}
