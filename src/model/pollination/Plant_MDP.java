package model.pollination;

import java.util.*;

import model.MersenneTwisterFast;

public class Plant_MDP {

	public int id, plant_type; 
	public int attract_a, attract_b, fit_a, fit_b, MAX_LOCI, num_pollen_grains, num_st_pollen_grains;
	public static MersenneTwisterFast mt = new MersenneTwisterFast();
	private ArrayList<Integer> pollen; 
	private ArrayList<Integer> st_pollen;
	private int num_flowers, num_ovules;
//	private int[] num_pollen_types;  -- Do we need this if we have the pollenTypes method?
	
	public Plant_MDP(int attract_a, int attract_b, int fit_a, int  fit_b, int id, int plant_type) {
		this.id = id;
		this.attract_a = attract_a;
		this.attract_b = attract_b;
		this.fit_a = fit_a;
		this.fit_b = fit_b;
		this.plant_type = plant_type;
		this.MAX_LOCI = 10;
		num_flowers = 1;
		this.num_pollen_grains = 100;
		this.num_st_pollen_grains = 0;
		num_ovules = 3;
		pollen = new ArrayList<Integer>(0);
		initPollen();
		st_pollen = new ArrayList<Integer> (0);
	}
	
	/** Problems may arise here! **/
	public Plant_MDP reproduce(ArrayList<Plant_MDP> plant, int pnum) {
		Ovule_MDP new_ovule;
		Pollen_MDP new_pollen;
		int a1sum_a, a2sum_a, a1sum_b, a2sum_b, f1sum_a, f2sum_a, f1sum_b, f2sum_b, grain_id;
		grain_id = giveStPollen();
		
		/** DO I NEED TO CHANGE BELOW TO STIGMA POLLEN ALSO? **/
		Plant_MDP planto = plant.get(this.id);
		Plant_MDP plantp = plant.get(grain_id);
	//	System.out.println(planto.id + "\t" + plantp.id + "\t" + grain_id );
	//	System.out.println(planto.id + "\t" + plantp.id + "\t" + grain_id );
	//	planto.PrintPlant();
	//	plantp.PrintPlant();
		new_ovule = makeOvule(planto);
		new_pollen = makePollen(plantp);
		a1sum_a = new_ovule.attract_a;
		a2sum_a = new_pollen.attract_a;
		a1sum_b = new_ovule.attract_b;
		a2sum_b = new_pollen.attract_b;
		f1sum_a = new_ovule.fit_a;
		f2sum_a = new_pollen.fit_a;
		f1sum_b = new_ovule.fit_b;
		f2sum_b = new_pollen.fit_b;
		return new Plant_MDP(a1sum_a + a2sum_a, a1sum_b+a2sum_b, f1sum_a+f2sum_a,f1sum_b+f2sum_b, pnum, plant_type);
	}
	
	public Plant_MDP self(ArrayList<Plant_MDP> plant, int pnum) {
		Ovule_MDP new_ovule;
		Pollen_MDP new_pollen;
		int a1sum_a, a2sum_a, a1sum_b, a2sum_b, f1sum_a, f2sum_a, f1sum_b, f2sum_b;
		Plant_MDP planto = plant.get(this.id);
		Plant_MDP plantp = plant.get(this.id);
		new_ovule = makeOvule(planto);
		new_pollen = makePollen(plantp);
		a1sum_a = new_ovule.attract_a;
		a2sum_a = new_pollen.attract_a;
		a1sum_b = new_ovule.attract_b;
		a2sum_b = new_pollen.attract_b;
		f1sum_a = new_ovule.fit_a;
		f2sum_a = new_pollen.fit_a;
		f1sum_b = new_ovule.fit_b;
		f2sum_b = new_pollen.fit_b;
		return new Plant_MDP(a1sum_a + a2sum_a, a1sum_b+a2sum_b, f1sum_a+f2sum_a,f1sum_b+f2sum_b, pnum, plant_type);
	}
	
	
	
	public int givePollen() {
		int temp = 0;
		if(this.num_pollen_grains != 0)
		{
			int rannum = mt.nextInt(this.num_pollen_grains);
			temp = pollen.get(rannum);
			pollen.remove(rannum);
			this.num_pollen_grains--;
			return temp;
		}
			return -1;
	/** PROBLEM HERE (RETURNING -1) **/
	}
	
	public int giveStPollen() {
		int temp = 0;
		if(this.num_st_pollen_grains != 0)
		{
			int rannum = mt.nextInt(this.num_st_pollen_grains);
			temp = st_pollen.get(rannum);
			st_pollen.remove(rannum);
			this.num_st_pollen_grains--;
			return temp;
		}
			return -1;
	}
	
	public void receivePollen(int temp) 
	{
		
	//	System.out.println("receive " + temp);
		st_pollen.add(temp);
		this.num_st_pollen_grains++;
	};
	
	public Ovule_MDP makeOvule(Plant_MDP plant)
	{
		int hattract_a = makeHaploid(plant.attract_a, plant.MAX_LOCI);
		int hattract_b = makeHaploid(plant.attract_b, plant.MAX_LOCI);
		int hfit_a = makeHaploid(plant.fit_a, plant.MAX_LOCI);
		int hfit_b = makeHaploid(plant.fit_b, plant.MAX_LOCI);
		return new Ovule_MDP(hattract_a, hattract_b, hfit_a, hfit_b);
	}
	
	public Pollen_MDP makePollen(Plant_MDP plant)
	{
		int hattract_a = makeHaploid(plant.attract_a, plant.MAX_LOCI);
		int hattract_b = makeHaploid(plant.attract_b, plant.MAX_LOCI);
		int hfit_a = makeHaploid(plant.fit_a, plant.MAX_LOCI);
		int hfit_b = makeHaploid(plant.fit_b, plant.MAX_LOCI);
		return new Pollen_MDP(hattract_a, hattract_b, hfit_a, hfit_b);
	}

	
	public int makeHaploid(int GeneSum, int MAX_LOCI) 
	{
		int haploidGeneSum = 0;
		
		ArrayList<Integer> locus_value = new ArrayList<Integer>(MAX_LOCI);
		
		for(int i = 0; i < GeneSum; i++) {
			locus_value.add(new Integer(1));
		}
		for(int i = GeneSum; i < MAX_LOCI; i++) {
			locus_value.add(new Integer(0));
		};
		
		Collections.shuffle(locus_value);
		
		for(int i = 0; i < MAX_LOCI/2; i++) 
		{
			haploidGeneSum += locus_value.get(i);
		}
		
		return haploidGeneSum;
	}
	
	public void PrintPlant()
	{
					System.out.println(this.id + "\t" + this.plant_type + "\t" + this.num_pollen_grains + "\t" + this.num_st_pollen_grains +
						"\t"+ this.attract_a + "\t" +  this.attract_b + 
							"\t" + this.fit_a + "\t" + this.fit_b
							);
	}
	
/*	public int[] numEachPollenGrainType() {
		int[] num_types = numPollenTypes();
		int max_id = num_types[1];
		int[] temp_arr = new int[max_id];
		
		while(!pollen.isEmpty()) {
			temp_arr[pollen.get().orig_flower_id]++;
		}
		
		return temp_arr;
	}
	*/
	/*
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
	
	private void initPollen() {
		for(int i = 0; i < num_pollen_grains; i++) {
			pollen.add(this.id);
		}
	/*
		Pollen temp;
		temp = pollen.get();
		
		System.out.println(temp.orig_flower_id + "\t" + temp.plant_type + "\t" + temp.attract_a.getGeneSum(1) + "\t" +  temp.attract_a.getGeneSum(2) + 
					"\t"+ temp.attract_b.getGeneSum(1) + "\t" +  temp.attract_b.getGeneSum(2) + 
		//			"\t" + temp.fit_a.getGeneSum(1) + "\t" + temp.fit_a.getGeneSum(2) +
		//			"\t" + temp.fit_b.getGeneSum(1) + "\t" + temp.fit_b.getGeneSum(2)
		//			);
		
					this.orig_flower_id = flower_id;
		this.attract_a = attract_a;
		this.attract_b = attract_b;
		this.fit_a = fit_a;
		this.fit_b = fit_b;
	*/				
	}
}
	

/*
	private void initOvules() {
		for(int i = 0; i < num_ovules; i++) {
			ovules.add(new Ovule(id, attract_a.produceHaploid(), attract_b.produceHaploid(), fit_a.produceHaploid(), fit_b.produceHaploid()));
		}
		
	}
}
*/
