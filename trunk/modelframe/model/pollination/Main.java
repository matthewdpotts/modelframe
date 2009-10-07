package model.pollination;

import java.io.IOException;

public class Main {

	public static void main(String args[]) throws IOException {
		
		//Model test = new Model(new String("output-pollination-all.txt"), 1000, 100,50,50,1,0,3,3,1,1,100,100,.2,.2);
	//	test.run(100, 50, 50, 250, 250);
		System.out.println("File\tE\t#Boots\tSteps\t#P\t#P1\t#P2\t#Pa\t#Pb\t#Va\t#Vb\t#O1\t#O2\t#F1\t#F2\t#Po1\t#Po2\tlossa\tlossb\tconvergence_tolerance\tmin_attract\tmax_attract\ttotal_pollen\tn");	
		PollinationBatchReader runner = new PollinationBatchReader();
		runner.readConfig(new String("OVN925E_3T.txt"));
	//	runner.readConfig(args[0]);
		runner.run();
		System.out.println("Done.");
	}
	
}
