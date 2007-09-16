package model.pollination;

import java.io.FileNotFoundException;

import model.BagCollection;
import model.Printer;
import model.MersenneTwisterFast;

public class Model {

	public static MersenneTwisterFast mt = new MersenneTwisterFast();
	private BagCollection<Plant> plants;
	private Pollinator bee_a, bee_b;
	private Printer output;
	
	public Model() throws FileNotFoundException {
		plants = new BagCollection<Plant>();
		bee_a = new Pollinator(1);
		bee_b = new Pollinator(2);
		output = new Printer("output3.txt", "time\tpid\tptype\tfit_a\tfit_b\tattract_a\tattract_b");
		output.printHeader();
		initPlants();
	}
	
	public void run() {
		BagCollection<Plant> new_plants, temp_plants;
		Plant plant_temp;
		
		for(int i = 0; i < 100000; i++) {
			for(int j = 0; j < 500; j++) {
				if(mt.nextInt(2)+2 % 2 == 0)
					bee_a.move(plants);
				else
					bee_b.move(plants);
			}
			
			if(i % 100 == 0) { // change to modify sample rate in time
				temp_plants = (BagCollection<Plant>) plants.clone();
				while(!temp_plants.isEmpty()) {
					plant_temp = temp_plants.getAndRemove();
					if(plant_temp.id == 1)
						output.printData(Integer.toString(i) + "\t" + Integer.toString(plant_temp.id) + "\t" + Integer.toString(plant_temp.plant_type) 
							+ "\t" + Integer.toString(plant_temp.fit_a.getGeneSum()) + "\t" + Integer.toString(plant_temp.fit_b.getGeneSum()) + "\t"
							+ Integer.toString(plant_temp.attract_a.getGeneSum()) + "\t" + Integer.toString(plant_temp.attract_b.getGeneSum()));
				}
			}
			
			new_plants = new BagCollection<Plant>();
			while(!plants.isEmpty()) {
				new_plants.add(plants.getAndRemove().reproduce());
			}
			this.plants = new_plants;
		}
	}
	
	private void initPlants() {
		for(int i = 0; i < 100; i++) { //Apparently it blows stack at "i > 57"
			if(i % 2 == 0) {
				plants.add(new Plant(new DiploidGene(mt.nextInt(11), mt.nextInt(11)), new DiploidGene(mt.nextInt(11), mt.nextInt(11)),new DiploidGene(mt.nextInt(11), mt.nextInt(11)), new DiploidGene(mt.nextInt(11), mt.nextInt(11)), i, 1));
			}
			else {
				plants.add(new Plant(new DiploidGene(mt.nextInt(11), mt.nextInt(11)), new DiploidGene(mt.nextInt(11), mt.nextInt(11)), new DiploidGene(mt.nextInt(11), mt.nextInt(11)), new DiploidGene(mt.nextInt(11), mt.nextInt(11)), i, 2));
			}
		}
	}
	
	
}
