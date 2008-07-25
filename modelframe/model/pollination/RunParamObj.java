package model.pollination;

public class RunParamObj {

	private int num_boots;
	private int num_steps;
	private int num_plants;
	private int num_plants_1;
	private int num_plants_2;
	private int num_pollinator_a;
	private int num_pollinator_b;
	private int num_visits_a;
	private int num_visits_b;
	private int num_ovules_1;
	private int num_ovules_2;
	private int num_flowers_1;
	private int num_flowers_2;
	private int num_pollen_grain_1;
	private int num_pollen_grain_2;
	private double pollen_loss_rate_a;
	private double pollen_loss_rate_b;
	private double convergence_tolerance;
	
	
	private String filename;
	private String masterfile;
	
	public RunParamObj ( String masterfile, String filename, int num_boots, int num_steps, int num_plants, int num_plants_1, int num_plants_2, int num_pollinator_a, int num_pollinator_b, int num_visits_a, int num_visits_b, int num_ovules_1, int num_ovules_2, int num_flowers_1,int num_flowers_2, int num_pollen_grain_1, int num_pollen_grain_2, double pollen_loss_rate_a, double pollen_loss_rate_b, double conv_tol)
 {
		this.masterfile = masterfile;
		this.filename = filename;
		this.num_boots = num_boots;
		this.num_steps = num_steps;
		this.num_plants = num_plants;
		this.num_plants_1 = num_plants_1;
		this.num_plants_2 = num_plants_2;
		this.num_pollinator_a = num_pollinator_a;
		this.num_pollinator_b = num_pollinator_b;
		this.num_visits_a = num_visits_a;
		this.num_visits_b = num_visits_b;
		this.num_ovules_1 = num_ovules_1;
		this.num_ovules_2 = num_ovules_2;
		this.num_flowers_1 =num_flowers_1;
		this.num_flowers_2 = num_flowers_2;
		this.num_pollen_grain_1 = num_pollen_grain_1;
		this.num_pollen_grain_2 = num_pollen_grain_2;
		this.pollen_loss_rate_a = pollen_loss_rate_a;
		this.pollen_loss_rate_b = pollen_loss_rate_b;
		this.convergence_tolerance = conv_tol;
	}
	
	public String get_masterfile () {
		return masterfile;
	}
	
	public String get_filename () {
		return filename;
	}
	
	public int get_num_boots () {
		return num_boots;
	}
	
	public int get_num_steps () {
		return num_steps;
	}
	
	public int get_num_plants () {
		return num_plants;
	}
	
	public int get_num_plants_1 () {
		return num_plants_1;
	}
	
	public int get_num_pollinator_a () {
		return num_pollinator_a;
	}
	
	public int get_num_pollinator_b () {
		return num_pollinator_b;
	}
	
	public int get_num_plants_2 () {
		return num_plants_2;
	}
	
	public int get_num_visits_a () {
		return num_visits_a;
	}
	
	public int get_num_visits_b () {
		return num_visits_b;
	}
	
	public int get_num_ovules_1 () {
		return num_ovules_1;
	}
	
	public int get_num_ovules_2 () {
		return num_ovules_2;
	}
	
	public int get_num_flowers_1 () {
		return num_flowers_1;
	}
	
	public int get_num_flowers_2 () {
		return num_flowers_2;
	}
	
	public int get_num_pollen_grain_1 () {
		return num_pollen_grain_1;
	}
	
	public int get_num_pollen_grain_2 () {
		return num_pollen_grain_2;
	}
	
	public double get_pollen_loss_rate_a () {
		return pollen_loss_rate_a;
	}
	
	public double get_pollen_loss_rate_b () {
		return pollen_loss_rate_b;
	}
	public double get_conv_tol() {
		return convergence_tolerance;
	}
	
}
