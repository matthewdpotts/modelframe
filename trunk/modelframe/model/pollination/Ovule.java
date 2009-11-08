package model.pollination;

public class Ovule {
	
	//Ovule class. An ovule is created during the reproduction process.  The ovule class has attributes
	//(genes) which keep track of a plant's attraction to pollinator types a & b as well as fit to a & b
	//(fit is not used in current model).  It also has a poratio gene which is allowed to evolve
	//in some model variants. 
	
	public int attract_a, attract_b, fit_a, fit_b, poratio;
	
	public Ovule(int attract_a, int attract_b, int fit_a, int fit_b, int poratio) {
		this.attract_a = attract_a;
		this.attract_b = attract_b;
		this.fit_a = fit_a;
		this.fit_b = fit_b;
		this.poratio = poratio;
	}
	
}





