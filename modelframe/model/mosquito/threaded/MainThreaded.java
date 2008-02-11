package model.mosquito.threaded;

import java.io.IOException;


public class MainThreaded {
	
	public static void main(String[] args) throws IOException {			

		int dim = 100;
		double[] perc = { 0.01, 0.02, 0.03, 0.04, 0.05, 0.06, 0.07, 0.08, 0.09, 0.1 };
		String header_m = new String("t\tb\tw\tba");
		String header_s = new String("h\ttb");
		Runtime garb_var = Runtime.getRuntime();
		ModelArrsThreaded test;
	
		int main_output = 1;
		
		for(int j = 0; j < perc.length; j++) {
			for(int i = 0; i < perc.length; i++) {
				
				long start_time = System.currentTimeMillis();
				
				int num_h = (int)(perc[j] * dim * dim);
				int num_m = (int)(perc[i] * dim * dim);
					
				System.out.println("Number of humans: "+num_h);
				System.out.println("Number of mosqs: "+num_m);
				System.out.println("Dimensions: "+dim+"x"+dim+"\n");
				String[] temp_m = { new String("output_m"+(main_output++)+".txt"), header_m };
				String[] temp_s = { new String("output_hvsba"+(j+1)+".txt"), header_s };
					
				if(i == 0) {
					test = new ModelArrsThreaded(dim, dim, temp_m, temp_s, true);
				}
				else {
					test = new ModelArrsThreaded(dim, dim, temp_m, temp_s);
				}
				test.Run(10000, num_h, num_m);
				garb_var.gc();
				
				System.out.println("Time taken: "+(System.currentTimeMillis()-start_time));
			}
		}
		System.out.println("Done.");				
	}
}
