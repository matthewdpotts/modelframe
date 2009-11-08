package model.pollination;

import java.io.IOException;

public class Main {

	//Main model class.  When the program is run - it invokes the script file supplied on the command line
	//See readme file for direction on how to run model.
	//Also outputs information on run progress to standard output. 
	
	public static void main(String args[]) throws IOException {
		System.out.println("File\tBoot_Number");	
		PollinationBatchReader runner = new PollinationBatchReader();
		runner.readConfig(args[0]);
		runner.run();
		System.out.println("Done.");
	}
	
}
