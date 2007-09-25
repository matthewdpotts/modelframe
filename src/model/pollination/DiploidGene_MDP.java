package model.pollination;

public class DiploidGene {
	
	public static final int MAX_LOCI = 10;
	private int sum_loci_1, sum_loci_2;
	private int [] locus_value, locus_position;
	
	public DiploidGene(int sum_loci_1, int sum_loci_2) {
		this.sum_loci_1 = sum_loci_1;
		this.sum_loci_2 = sum_loci_2;
		this.locus_position = new int[MAX_LOCI];
		this.locus_value = new int[MAX_LOCI];
	}
	
	public int getGeneSum() {
		return sum_loci_1 + sum_loci_2;
	}
	
	public int getGeneSum(int type) {
		if(type == 1) {
			return sum_loci_1;
		}
		else if(type == 2) {
			return sum_loci_2;
		}
		return(-20);
		
	}
	
	public HaploidGene produceHaploid() {
		int haploidGeneSum = 0;
		
		for(int i = 0; i < this.getGeneSum(); i++) {
			this.locus_value[i] = 1;
			this.locus_position[i] = Model.mt.next();
		}
		for(int i = this.getGeneSum(); i < MAX_LOCI; i++) {
			this.locus_value[i] = 0;
			this.locus_position[i] = Model.mt.next();
		}
		
		// How do you sort the object on this.locus_positionmust be a built in method
		
		for(int i = 0; i < HaploidGene.MAX_LOCI; i++) {
			haploidGeneSum += this.locus_value[i];
		}
		
		return new HaploidGene(haploidGeneSum);
	}
}
