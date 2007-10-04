package model.pollination;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String args[]) throws FileNotFoundException {
		Model_MDP test = new Model_MDP(new String("output-pollination-all.txt"), 100);
		test.run(1);
		System.out.println("Done.");
	}
	
}
