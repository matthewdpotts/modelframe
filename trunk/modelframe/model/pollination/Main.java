package model.pollination;

import java.io.IOException;

public class Main {

	public static void main(String args[]) throws IOException {
		
		Model test = new Model(new String("output-pollination-all.txt"), 1000, 100,50,50,250,250,3,3,1,1,100,100,.2,.2);
		test.run(1000, 50, 50, 250, 250);
		//PollinationBatchReader runner = new PollinationBatchReader();
	//	runner.readConfig(new String("run.txt"));
	//	runner.run();
		System.out.println("Done.");
	}
	
}
