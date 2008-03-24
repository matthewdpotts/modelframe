package model.pollination;


import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.BatchReader;

public class PollinationBatchReader implements BatchReader {

	private ArrayList<RunParamObj> commands;
		
	public PollinationBatchReader () {
		commands = new ArrayList<RunParamObj>();
	}
	
	public void readConfig(String filename) throws IOException {
		BufferedReader buff = null;
		StringTokenizer strtok = null;
		String line = null;
		try {
			buff = new BufferedReader ( new FileReader(filename) );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		line = buff.readLine();
		while ( line != null ) {
			if (!line.startsWith(new String("#")))
			{
				strtok = new StringTokenizer ( line );
				String temp_filename = null;
				int temp_steps = 0;
				int temp_plants = 0;
			
				if (strtok.hasMoreTokens())
					temp_filename = strtok.nextToken();
				else
				{
					System.out.println("ERROR: could not get filename in line: " + line);
					return;
				}
				if (strtok.hasMoreTokens())
					temp_steps = Integer.valueOf(strtok.nextToken()).intValue();
				else
				{
					System.out.println("ERROR: could not get number of steps in line: " + line);
					return;
				}
				if (strtok.hasMoreTokens())
					temp_plants = Integer.valueOf(strtok.nextToken()).intValue();
				else
				{
					System.out.println("ERROR: could not get number of plants in line: " + line);
					return;
				}
			
				commands.add(new RunParamObj(temp_filename, temp_steps, temp_plants));
			}
			line = buff.readLine();
		}
	}

	public void run() {
		Model test = null;
		for ( int i = 0; i < commands.size(); i++ ) {
			RunParamObj temp = commands.get(i);
			try {
				test = new Model (temp.get_filename(), temp.get_num_plants());
				test.run(temp.get_num_steps());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
