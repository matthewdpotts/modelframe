package model.pollination;

import java.util.HashSet;
import java.util.Iterator;

public class DiploidGene2 {
	
	public static final int MAX_LOCI = 10;
	private int sum_loci_1, sum_loci_2, sum; 
	
	public DiploidGene2(int sum_loci_1, int sum_loci_2) {
		this.sum_loci_1 = sum_loci_1;
		this.sum_loci_2 = sum_loci_2;
		sum = sum_loci_1 + sum_loci_2;
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
		int temp_sum = sum;
		
		int bag[] = new int[10];
		HashSet<Integer> indices = new HashSet<Integer>();
		
		for(int i = 0; i < DiploidGene2.MAX_LOCI; i++) {
			if(temp_sum > 0) {
				bag[i] = 1;
				temp_sum--;
			}
			else {
				bag[i] = 0;
			}
			indices.add(Integer.valueOf(i));
		}
		
		Iterator<Integer> itr = indices.iterator();
		
		for(int i = 0; i < HaploidGene.MAX_LOCI; i++) {
			int nextCoord = itr.next();
			haploidGeneSum += bag[nextCoord];
		}
		
		return new HaploidGene(haploidGeneSum);
	}
}
