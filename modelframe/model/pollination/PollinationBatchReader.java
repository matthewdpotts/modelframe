package model.pollination;


import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.BatchReader;
import model.util.Printer;

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
		strtok = new StringTokenizer ( line );
		String temp_filename1 = null;
		Printer output;
		temp_filename1 = strtok.nextToken();
		output = new Printer(temp_filename1,"E\tnP\tnP1\tnP2\tnPa\tnPb\tnVa\tnVb\tnOv1\tnOv2\tnFl1\tnFl2\tnPo1\tnPo2\tlossa\tlossb\tconv_tol\tmin_A\tmax_A\ttotal_pollen\tn\tdepost_rate_a\tdeposit_rate_b\tbn\tSteps\tASteps\tMP1Aa\tMP2Aa\tMP1Ab\tMP2Ab\tMP1Fa\tMP2Fa\tMP1Fb\tMP2Fb\tratio1\tratio2\tave_ove1\tave_ove2\tave_pg1\tave_pg2", false);
		output.printHeader();
		line = buff.readLine();
		line = buff.readLine();
		while ( line != null ) {
			if (!line.startsWith(new String("#")))
			{
				strtok = new StringTokenizer ( line );
				String temp_filename = null;
				int temp_poevolve = 0;
				int temp_num_boots = 0;
				int temp_steps = 0;
				int temp_plants = 0;
				int temp_num_plants_1 = 0;
				int temp_num_plants_2 = 0;
				int temp_num_pollinator_a = 0;
				int temp_num_pollinator_b = 0;
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
				double temp_convergence_tolerance = 0;
				double temp_min_attract = 0;
				double temp_max_attract = 0;
				double temp_total_pollen = 0;
				double temp_n = 0;
				double temp_deposit_rate_a = 0;
				double temp_deposit_rate_b = 0;
				double temp_receive_rate_a = 0;
				double temp_receive_rate_b = 0;
		
				temp_filename = strtok.nextToken();
				temp_poevolve =  Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_boots = Integer.valueOf(strtok.nextToken()).intValue();
				temp_steps = Integer.valueOf(strtok.nextToken()).intValue();
				temp_plants = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_plants_1 = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_plants_2 = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_pollinator_a = Integer.valueOf(strtok.nextToken()).intValue();
				temp_num_pollinator_b = Integer.valueOf(strtok.nextToken()).intValue();
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
				temp_convergence_tolerance = Double.valueOf(strtok.nextToken()).doubleValue();
				temp_min_attract = Double.valueOf(strtok.nextToken()).doubleValue();
				temp_max_attract = Double.valueOf(strtok.nextToken()).doubleValue();
				temp_total_pollen = Double.valueOf(strtok.nextToken()).doubleValue();
				temp_n = Double.valueOf(strtok.nextToken()).doubleValue();
				temp_deposit_rate_a = Double.valueOf(strtok.nextToken()).doubleValue();
				temp_deposit_rate_b = Double.valueOf(strtok.nextToken()).doubleValue();
				temp_receive_rate_a = Double.valueOf(strtok.nextToken()).doubleValue();
				temp_receive_rate_b = Double.valueOf(strtok.nextToken()).doubleValue();
					
					
				commands.add(new RunParamObj(temp_filename1, temp_filename, temp_poevolve, temp_num_boots, temp_steps,temp_plants, temp_num_plants_1, temp_num_plants_2, temp_num_pollinator_a, temp_num_pollinator_b, temp_num_visits_a, temp_num_visits_b, temp_num_ovules_1, temp_num_ovules_2,temp_num_flowers_1,temp_num_flowers_2, temp_num_pollen_grain_1, temp_num_pollen_grain_2,temp_pollen_loss_rate_a, temp_pollen_loss_rate_b, temp_convergence_tolerance, temp_min_attract, temp_max_attract, temp_total_pollen, temp_n, temp_deposit_rate_a, temp_deposit_rate_b, temp_receive_rate_a, temp_receive_rate_b));
			}
			line = buff.readLine();
		}
	}

	public void run() {
		Model test = null;
		String outstring;
		Printer outfile;
		int n;
		for ( int i = 0; i < commands.size(); i++ ) {
			RunParamObj temp = commands.get(i);
			try {
				outfile = new Printer(temp.get_filename(),"E\tbootn\ttime\tpid\tptype\tnum_pollen\tnum_st_pollen\tnum_pollen_right\tnum_pollen_wrong\tnum_pollen_lost_con\tnum_pollen_lost_het\tnum_pollen_on_pollinator\tfit_a\tfit_b\tattract_a\tattract_b\tratio\tactual_ovule\tactual_pollen", false);
				outfile.printHeader();
				outstring = String.valueOf(temp.get_poevolve()) + "\t" + String.valueOf(temp.get_num_plants()) +"\t" + String.valueOf(temp.get_num_plants_1()) +"\t" + 
				String.valueOf(temp.get_num_plants_2()) +"\t"  + String.valueOf(temp.get_num_pollinator_a()) +"\t"  + 
				String.valueOf(temp.get_num_pollinator_b()) +"\t"  + String.valueOf(temp.get_num_visits_a()) + "\t"  + 
				String.valueOf(temp.get_num_visits_b()) +"\t"  + String.valueOf(temp.get_num_ovules_1()) + "\t"  + 
				String.valueOf(temp.get_num_ovules_2()) +"\t"  + String.valueOf(temp.get_num_flowers_1()) + "\t" + 
				String.valueOf(temp.get_num_flowers_2()) +"\t" + String.valueOf(temp.get_num_pollen_grain_1()) +"\t"  + 
				String.valueOf(temp.get_num_pollen_grain_2()) +"\t" + String.valueOf(temp.get_pollen_loss_rate_a()) +"\t"  + 
				String.valueOf(temp.get_pollen_loss_rate_b()) + "\t" + String.valueOf(temp.get_conv_tol()) + "\t" + String.valueOf(temp.get_min_attract())+ "\t" +
				String.valueOf(temp.get_max_attract())+ "\t" + String.valueOf(temp.get_total_pollen()) + "\t" + String.valueOf(temp.get_n())+ "\t" + String.valueOf(temp.get_deposit_rate_a()) + "\t" + String.valueOf(temp.get_deposit_rate_b())+"\t" +  String.valueOf(temp.get_receive_rate_a()) + "\t" + String.valueOf(temp.get_receive_rate_b());
				n = temp.get_num_boots();
				for(int ii = 0; ii < n; ii++)
				{	
					test = new Model (temp.get_masterfile(),
							temp.get_filename(), outstring,
							temp.get_poevolve (),
							temp.get_num_boots(), 
							temp.get_num_steps(), 
							temp.get_num_plants(),
							temp.get_num_plants_1(),
							temp.get_num_plants_2(), 
							temp.get_num_pollinator_a(),
							temp.get_num_pollinator_b(), 
							temp.get_num_visits_a(), 
							temp.get_num_visits_b(), 
							temp.get_num_ovules_1(), 
							temp.get_num_ovules_2(), 
							temp.get_num_flowers_1(), 
							temp.get_num_flowers_2(), 
							temp.get_num_pollen_grain_1(), 
							temp.get_num_pollen_grain_2(),
							temp.get_pollen_loss_rate_a(), 
							temp.get_pollen_loss_rate_b(),
							temp.get_conv_tol(),
							temp.get_min_attract(),
							temp.get_max_attract(),
							temp.get_total_pollen(),
							temp.get_n(), 
							temp.get_deposit_rate_a(), 
							temp.get_deposit_rate_b(),
							temp.get_receive_rate_a(), 
							temp.get_receive_rate_b()		
					
					);
					System.out.println(temp.get_filename() +"\t" + temp.get_poevolve() + "\t" + ii +"\t"+ temp.get_num_steps()+"\t"+ temp.get_num_plants()+"\t"+temp.get_num_plants_1()+"\t"+ temp.get_num_plants_2()+"\t"+ temp.get_num_pollinator_a() + "\t" + temp.get_num_pollinator_b() + "\t" + temp.get_num_visits_a()+"\t"+temp.get_num_visits_b()+"\t"+temp.get_num_ovules_1()+"\t"+temp.get_num_ovules_2()+"\t"+temp.get_num_flowers_1()+"\t"+temp.get_num_flowers_2()+"\t"+temp.get_num_pollen_grain_1()+"\t"+ temp.get_num_pollen_grain_2()+"\t"+temp.get_pollen_loss_rate_a()+"\t"+temp.get_pollen_loss_rate_b() + "\t" + temp.get_conv_tol()
							+ "\t" + temp.get_min_attract() + "\t" + temp.get_max_attract() + "\t" + temp.get_total_pollen() + "\t" + temp.get_n() + "\t" +  temp.get_deposit_rate_a() +"\t"+ temp.get_deposit_rate_b()+ "\t" +  temp.get_receive_rate_a() +"\t"+ temp.get_receive_rate_b()); 
					test.run(temp.get_poevolve(), ii, temp.get_num_steps(), temp.get_num_plants_1(), temp.get_num_plants_2(), temp.get_num_pollinator_a(), temp.get_num_pollinator_b(), temp.get_num_visits_a(), temp.get_num_visits_b(),temp.get_pollen_loss_rate_a(), temp.get_pollen_loss_rate_b(), temp.get_conv_tol(), temp.get_deposit_rate_a(), temp.get_deposit_rate_b(), temp.get_receive_rate_a(), temp.get_receive_rate_b());	
				}
				} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		
			}
	}
}
