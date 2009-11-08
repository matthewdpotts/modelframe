package model.pollination;

import java.util.*;

import model.util.MersenneTwisterFast;

public class Plant {

	//Plant class with many attributed some unused in current version.
	
	public int id, plant_type, poevolve; 
	public int attract_a, attract_b, fit_a, fit_b, poratio, MAX_LOCI, PO_MAX_LOCI,  num_pollen_grains, num_st_pollen_grains, initial_pollen_grains, num_pollen_lost_con, num_pollen_lost_het, num_pollen_on_pollinator, num_pollen_right, num_pollen_wrong;
	public static MersenneTwisterFast mt = new MersenneTwisterFast();
	private ArrayList<PollenGrain> pollen; 
	private ArrayList<PollenGrain> st_pollen;
	public int num_flowers;
	public int num_ovules;
	public int num_pollen_right_st;
	public double min_attract, max_attract, total_pollen, n;

// Construct a plant instance with different plant attributes.  Right now max_loci and po_max_loci are hard coded.
	
	public Plant(int poevolve, int attract_a, int attract_b, int fit_a, int  fit_b, int poratio, int id, int plant_type, int num_ovules, int num_flowers, int num_pollen_grains, double min_attract, double max_attract, double total_pollen, double n) {
		this.id = id;
		this.attract_a = attract_a;
		this.attract_b = attract_b;
		this.fit_a = fit_a;
		this.fit_b = fit_b;
		this.poratio = poratio;
		this.plant_type = plant_type;
		this.MAX_LOCI = 10;
		this.PO_MAX_LOCI = 50;
		this.num_flowers = num_flowers;
		this.poevolve = poevolve;
		this.num_ovules = num_ovules;
		this.num_pollen_grains = num_pollen_grains;
		this.n = n;
		if(this.poevolve == 1)
		{
			this.num_pollen_grains = (int) (this.poratio * total_pollen)/this.PO_MAX_LOCI;
			this.num_ovules = (int) ((total_pollen - this.num_pollen_grains)/n);
		}
		
		this.initial_pollen_grains = this.num_pollen_grains;
		this.num_st_pollen_grains = 0;
		this.num_pollen_lost_con = 0;
		this.num_pollen_lost_het = 0;
		this.num_pollen_wrong = 0;
		this.num_pollen_right = 0;
		this.num_pollen_right_st = 0;
		this.num_pollen_on_pollinator = 0;
		this.min_attract = min_attract;
		this.max_attract = max_attract;
		this.total_pollen = total_pollen;
		pollen = new ArrayList<PollenGrain>(0);
		initPollen();
		st_pollen = new ArrayList<PollenGrain> (0);
	}
	
	public Plant reproduce(ArrayList<Plant> plant, int pnum, int grain_id) {
		//Method for plant sexual reproduction - calls makeOvule and makePollen methods. 
		Ovule new_ovule;
		Pollen new_pollen;
		int a1sum_a, a2sum_a, a1sum_b, a2sum_b, f1sum_a, f2sum_a, f1sum_b, f2sum_b, o_poratio, p_poratio;
		Plant planto = plant.get(this.id);
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
		o_poratio = new_ovule.poratio;
		p_poratio = new_pollen.poratio;
		return new Plant(planto.poevolve, a1sum_a + a2sum_a, a1sum_b+a2sum_b, f1sum_a+f2sum_a,f1sum_b+f2sum_b, o_poratio + p_poratio, pnum, planto.plant_type, planto.num_ovules, planto.num_flowers, planto.initial_pollen_grains, planto.min_attract, planto.max_attract, planto.total_pollen, planto.n );
	}
	
	public Plant self(ArrayList<Plant> plant, int pnum) {
		//Method for plant asexual reproduction (selfing) - calls makeOvule and makePollen methods. 
		Ovule new_ovule;
		Pollen new_pollen;
		int a1sum_a, a2sum_a, a1sum_b, a2sum_b, f1sum_a, f2sum_a, f1sum_b, f2sum_b, o_poratio, p_poratio;
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
		o_poratio = new_ovule.poratio;
		p_poratio = new_pollen.poratio;
		return new Plant(planto.poevolve, a1sum_a + a2sum_a, a1sum_b+a2sum_b, f1sum_a+f2sum_a,f1sum_b+f2sum_b, o_poratio + p_poratio, pnum, planto.plant_type, planto.num_ovules, planto.num_flowers, planto.initial_pollen_grains, planto.min_attract, planto.max_attract, planto.total_pollen, planto.n);
	}
	
	public PollenGrain givePollen() {
		//Method for plant to give pollen to a pollinator. 
		PollenGrain temp;
		if(this.num_pollen_grains != 0)
		{
			int rannum = mt.nextInt(this.num_pollen_grains);
			temp = pollen.get(rannum);
			pollen.remove(rannum);
			this.num_pollen_grains--;
			return temp;
		}
			return new PollenGrain(-1,-1);
	}
	
	public PollenGrain giveStPollen() {
		//Method to choose random pollen grain on the stigma for fertilization of an ovule. 
		PollenGrain temp;
		if(this.num_st_pollen_grains != 0)
		{
			int rannum = mt.nextInt(this.num_st_pollen_grains);
			temp = st_pollen.get(rannum);
			st_pollen.remove(rannum);
			this.num_st_pollen_grains--;
			return temp;
		}
		return new PollenGrain(-1,-1);
	}
	
	public void receivePollen(PollenGrain tempgrain) 
	{
		//Method for plant to receive pollen from a pollinator. 
		if(this.poevolve == 2)
		{
			st_pollen.add(tempgrain);
			this.num_st_pollen_grains++;
			this.num_pollen_right_st++;
		}
		else if( tempgrain.plant_type   ==  this.plant_type)
		{
			st_pollen.add(tempgrain);
			this.num_st_pollen_grains++;
			this.num_pollen_right_st++;
		}
	};
	
	public Ovule makeOvule(Plant plant)
	{
		//Method to make an ovule (haploid).. 
		int hattract_a = makeHaploid(plant.attract_a, plant.MAX_LOCI);
		int hattract_b = makeHaploid(plant.attract_b, plant.MAX_LOCI);
		int hfit_a = makeHaploid(plant.fit_a, plant.MAX_LOCI);
		int hfit_b = makeHaploid(plant.fit_b, plant.MAX_LOCI);
		int poratio = makeHaploid(plant.poratio, plant.PO_MAX_LOCI);
		return new Ovule(hattract_a, hattract_b, hfit_a, hfit_b, poratio);
	}
	
	public Pollen makePollen(Plant plant)
	{
		//Method to make an pollen grain (haploid)
		int hattract_a = makeHaploid(plant.attract_a, plant.MAX_LOCI);
		int hattract_b = makeHaploid(plant.attract_b, plant.MAX_LOCI);
		int hfit_a = makeHaploid(plant.fit_a, plant.MAX_LOCI);
		int hfit_b = makeHaploid(plant.fit_b, plant.MAX_LOCI);
		int poratio = makeHaploid(plant.poratio, plant.PO_MAX_LOCI);
		return new Pollen(hattract_a, hattract_b, hfit_a, hfit_b, poratio);
	}

	
	public int makeHaploid(int GeneSum, int MAX_LOCI) 
	{
		//Method to make a haploid for use in reproduction.  
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
		//Method to print out info on a specific plant. 
		System.out.println(this.id + "\t" + this.plant_type + "\t" + this.num_pollen_grains + "\t" + this.num_st_pollen_grains +
			"\t" + this.num_pollen_right + "\t" + this.num_pollen_wrong +"\t"+ this.num_pollen_lost_con + "\t" + this.num_pollen_lost_het +"\t"+
			this.attract_a + "\t" +  this.attract_b + "\t" + this.fit_a + "\t" + this.fit_b + "\t" + this.poratio);
	}
	
	private void initPollen() 
	{
		//Method to intialize pollen grain on a plant with info on plant type and unique plant id. 
		for(int i = 0; i < num_pollen_grains; i++) 
		{
			pollen.add(new PollenGrain(this.plant_type, this.id));
		}
	}
}

