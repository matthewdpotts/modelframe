package model.pollination;

public class RunParamObj {

	private int num_steps;
	private int num_plants;
	private String filename;
	
	public RunParamObj ( String filename, int num_steps, int num_plants ) {
		this.filename = filename;
		this.num_steps = num_steps;
		this.num_plants = num_plants;
	}
	
	public String get_filename () {
		return filename;
	}
	
	public int get_num_steps () {
		return num_steps;
	}
	
	public int get_num_plants () {
		return num_plants;
	}
	
}
