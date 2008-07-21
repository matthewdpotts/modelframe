package model.pollination;

import java.util.*;

import model.util.MersenneTwisterFast;

public class Plant {

	public int id, plant_type; 
	public int attract_a, attract_b, fit_a, fit_b, MAX_LOCI, num_pollen_grains, num_st_pollen_grains, initial_pollen_grains;
	public static MersenneTwisterFast mt = new MersenneTwisterFast();
	private ArrayList<Integer> pollen; 
	private ArrayList<Integer> st_pollen;
	public int num_flowers;
	public int num_ovules;

	
	public Plant(int attract_a, int attract_b, int fit_a, int  fit_b, int id, int plant_type, int num_ovules, int num_flowers, int num_pollen_grains) {
		this.id = id;
		this.attract_a = attract_a;
		this.attract_b = attract_b;
		this.fit_a = fit_a;
		this.fit_b = fit_b;
		this.plant_type = plant_type;
		this.MAX_LOCI = 10;
		this.num_flowers = num_flowers;
		this.num_pollen_grains = num_pollen_grains;
		this.initial_pollen_grains = num_pollen_grains;
		this.num_st_pollen_grains = 0;
		this.num_ovules = num_ovules;
		pollen = new ArrayList<Integer>(0);
		initPollen();
		st_pollen = new ArrayList<Integer> (0);
	}
	
	public Plant reproduce(ArrayList<Plant> plant, int pnum) {
		Ovule new_ovule;
		Pollen new_pollen;
		int a1sum_a, a2sum_a, a1sum_b, a2sum_b, f1sum_a, f2sum_a, f1sum_b, f2sum_b, grain_id;
		grain_id = giveStPollen();
		Plant planto = plant.get(this.id);
	//	System.out.println(grain_id);
		Plant plantp = plant.get(grain_id);
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
		return new Plant(a1sum_a + a2sum_a, a1sum_b+a2sum_b, f1sum_a+f2sum_a,f1sum_b+f2sum_b, pnum, planto.plant_type, planto.num_ovules, planto.num_flowers, planto.initial_pollen_grains );
	}
	
	public Plant self(ArrayList<Plant> plant, int pnum) {
		Ovule new_ovule;
		Pollen new_pollen;
		int a1sum_a, a2sum_a, a1sum_b, a2sum_b, f1sum_a, f2sum_a, f1sum_b, f2sum_b;
		Plant planto = plant.get(this.id);
		Plant plantp = plant.get(this.id);
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
		return new Plant(a1sum_a + a2sum_a, a1sum_b+a2sum_b, f1sum_a+f2sum_a,f1sum_b+f2sum_b, pnum, plant_type, num_ovules, num_flowers, num_pollen_grains );
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
	}
	
	public int giveStPollen() {
		int temp = 0;
		if(this.num_st_pollen_grains != 0)
		{
			int rannum = mt.nextInt(this.num_st_pollen_grains);
			temp = st_pollen.get(rannum);
			st_pollen.remove(rannum);
			this.num_st_pollen_grains--;
			if(this.plant_type == 1)
				return temp/7907;
			else if (this.plant_type == 2)
				return temp/7919;
		}
			return -1;
	}
	
	public void receivePollen(int temp) 
	{
		if(temp % 7907 == 0 && this.plant_type == 1)
		{
			st_pollen.add(temp);
			this.num_st_pollen_grains++;
		}
		else if(temp % 7919 == 0 && this.plant_type == 2)
		{
			st_pollen.add(temp);
			this.num_st_pollen_grains++;
			
		}
	};
	
	public Ovule makeOvule(Plant plant)
	{
		int hattract_a = makeHaploid(plant.attract_a, plant.MAX_LOCI);
		int hattract_b = makeHaploid(plant.attract_b, plant.MAX_LOCI);
		int hfit_a = makeHaploid(plant.fit_a, plant.MAX_LOCI);
		int hfit_b = makeHaploid(plant.fit_b, plant.MAX_LOCI);
		return new Ovule(hattract_a, hattract_b, hfit_a, hfit_b);
	}
	
	public Pollen makePollen(Plant plant)
	{
		int hattract_a = makeHaploid(plant.attract_a, plant.MAX_LOCI);
		int hattract_b = makeHaploid(plant.attract_b, plant.MAX_LOCI);
		int hfit_a = makeHaploid(plant.fit_a, plant.MAX_LOCI);
		int hfit_b = makeHaploid(plant.fit_b, plant.MAX_LOCI);
		return new Pollen(hattract_a, hattract_b, hfit_a, hfit_b);
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
							"\t" + this.fit_a + "\t" + this.fit_b);
	}
	

	
	private void initPollen() 
	{
		for(int i = 0; i < num_pollen_grains; i++) 
		{
			if(this.plant_type == 1)
			{
				pollen.add(this.id * 7907);
			}
			else if (this.plant_type == 2)
			{
				pollen.add(this.id*7919);
			}
		}
		}
}

