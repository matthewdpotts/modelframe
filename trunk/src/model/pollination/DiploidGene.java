package model.pollination;

public class DiploidGene {
	
	public static final int MAX_LOCI = 10;
	private int sum_loci_1, sum_loci_2;
	
	public DiploidGene(int sum_loci_1, int sum_loci_2) {
		this.sum_loci_1 = sum_loci_1;
		this.sum_loci_2 = sum_loci_2;
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
		
		for(int i = 0; i < HaploidGene.MAX_LOCI; i++) {
			haploidGeneSum += Model.mt.nextInt(2);
		}
		
		return new HaploidGene(haploidGeneSum);
	}
}
