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
		line = buff.readLine();
		while ( line != null ) {
			if (!line.startsWith(new String("#")))
			{
				strtok = new StringTokenizer ( line );
				String temp_filename = null;
				int temp_steps = 0;
				int temp_plants = 0;
				int temp_num_plants_1 = 0;
				int temp_num_plants_2 = 0;
				int temp_num_visits_a = 0;
				int temp_num_visits_b = 0;
				int temp_num_ovules_1 = 0;
				int temp_num_ovules_2 = 0;
				int temp_num_flowers_1 = 0;
				int temp_num_flowers_2 = 0;
				int temp_num_pollen_grain_1 = 0; 
				int temp_num_pollen_grain_2 = 0;
				double temp_pollen_loss_rate_a = 0;
				double temp_pollen_loss_rate_b = 0;
		
				temp_filename = strtok.nextToken();
				temp_steps = Integer.valueOf(strtok.nextToken()).intValue();
				temp_plants = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_plants_1 = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_plants_2 = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_visits_a = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_visits_b = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_ovules_1 = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_ovules_2 = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_flowers_1 = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_flowers_2 = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_pollen_grain_1 = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_pollen_grain_2 = Integer.valueOf(strtok.nextToken()).intValue();
				temp_pollen_loss_rate_a = Double.valueOf(strtok.nextToken()).doubleValue();
				temp_pollen_loss_rate_b = Double.valueOf(strtok.nextToken()).doubleValue();
				
				commands.add(new RunParamObj(temp_filename, temp_steps,temp_plants, temp_num_plants_1, temp_num_plants_2, temp_num_visits_a, temp_num_visits_b, temp_num_ovules_1, temp_num_ovules_2,temp_num_flowers_1,temp_num_flowers_2, temp_num_pollen_grain_1, temp_num_pollen_grain_2,temp_pollen_loss_rate_a, temp_pollen_loss_rate_b ));
			}
			line = buff.readLine();
		}
	}

	public void run() {
		Model test = null;
		for ( int i = 0; i < commands.size(); i++ ) {
			RunParamObj temp = commands.get(i);
			try {
				test = new Model (temp.get_filename(), 
						temp.get_num_steps(), 
						temp.get_num_plants(),
						temp.get_num_plants_1(),
						temp.get_num_plants_2(), 
						temp.get_num_visits_a(), 
						temp.get_num_visits_b(), 
						temp.get_num_ovules_1(), 
						temp.get_num_ovules_2(), 
						temp.get_num_flowers_1(), 
						temp.get_num_flowers_2(), 
						temp.get_num_pollen_grain_1(), 
						temp.get_num_pollen_grain_2(),
						temp.get_pollen_loss_rate_a(), 
						temp.get_pollen_loss_rate_b());
				} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			
			System.out.println(temp.get_filename() +"\t"+ temp.get_num_steps()+"\t"+ temp.get_num_plants()+"\t"+temp.get_num_plants_1()+"\t"+ temp.get_num_plants_2()+"\t"+temp.get_num_visits_a()+"\t"+temp.get_num_visits_b()+"\t"+temp.get_num_ovules_1()+"\t"+temp.get_num_ovules_2()+"\t"+temp.get_num_flowers_1()+"\t"+temp.get_num_flowers_2()+"\t"+temp.get_num_pollen_grain_1()+"\t"+ temp.get_num_pollen_grain_2()+"\t"+temp.get_pollen_loss_rate_a()+"\t"+temp.get_pollen_loss_rate_b());
			test.run(temp.get_num_steps(), temp.get_num_plants_1(), temp.get_num_plants_2(),temp.get_num_visits_a(), temp.get_num_visits_b());	
		}
	}
}
