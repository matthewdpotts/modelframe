package model.pollination;

public class Pollen {
	
	public HaploidGene attract_a, attract_b, fit_a, fit_b;
	public int orig_flower_id;
	
	public Pollen(int flower_id, HaploidGene attract_a, HaploidGene attract_b, HaploidGene fit_a, HaploidGene fit_b) {
		this.orig_flower_id = flower_id;
		this.attract_a = attract_a;
		this.attract_b = attract_b;
		this.fit_a = fit_a;
		this.fit_b = fit_b;
	}
}
