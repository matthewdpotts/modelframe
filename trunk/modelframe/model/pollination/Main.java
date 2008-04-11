package model.pollination;

import java.io.IOException;

public class Main {

	public static void main(String args[]) throws IOException {
		
		//Model test = new Model(new String("output-pollination-all.txt"), 1000, 100,50,50,1,0,3,3,1,1,100,100,.2,.2);
	//	test.run(100, 50, 50, 250, 250);
		System.out.println("File\tSteps\t#P\t#P1\t#P2\t#Va\t#Vb\t#O1\t#O2\t#F1\t#F2\t#Po1\t#Po2\tlossa\tlossb");	
		PollinationBatchReader runner = new PollinationBatchReader();
		runner.readConfig(new String("run.txt"));
		runner.run();
		System.out.println("Done.");
	}
	
}
