package model.pollination;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String args[]) throws FileNotFoundException {
		Model test = new Model(new String("output-pollination-all.txt"), 100);
		test.run(1000);
		System.out.println("Done.");
	}
	
}
