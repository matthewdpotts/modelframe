package model.pollination;

import java.util.*;

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
	/*
	public HaploidGene produceHaploid() 
	{
		int haploidGeneSum = 0;
		
		//ArrayList<Integer> locus_value = new ArrayList<Integer>(MAX_LOCI);
		
	//	for(int i = 0; i < this.getGeneSum(); i++) {
	//		locus_value.add(new Integer(1));
	//	}
	//	for(int i = this.getGeneSum(); i < MAX_LOCI; i++) {
	//		locus_value.add(new Integer(0));
	//	};
		
	//	Collections.shuffle(locus_value);
		// How do you sort the object on this.locus_positionmust be a built in method
		
	//	for(int i = 0; i < HaploidGene.MAX_LOCI; i++) {
	//		haploidGeneSum += locus_value.get(i);
	//	}
		
		return new HaploidGene(haploidGeneSum);
	}
	
	*/
	
	public HaploidGene produceHaploid() {
		int haploidGeneSum = 5;
		
	//	for(int i = 0; i < HaploidGene.MAX_LOCI; i++) {
	//		haploidGeneSum += Model.mt.nextInt(2);
	//	}
		
		return new HaploidGene(haploidGeneSum);
	}
	
}
