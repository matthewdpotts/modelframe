package model.pollination;

import java.io.IOException;

public class Main {

	public static void main(String args[]) throws IOException {
		/*
		Model test = new Model(new String("output-pollination-all.txt"), 100);
		test.run(1000);
		*/
		PollinationBatchReader runner = new PollinationBatchReader();
		runner.readConfig(new String("run.txt"));
		runner.run();
		System.out.println("Done.");
	}
	
}
