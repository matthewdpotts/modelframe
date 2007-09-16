package model.pollination;

public class HaploidGene {
	
	public static final int MAX_LOCI = 5;
	private int haploidTotal;

	public HaploidGene(int haploidTotal) {
		this.haploidTotal = haploidTotal;
	}
	
	public int getGeneSum() {
		return haploidTotal;
	}
}
