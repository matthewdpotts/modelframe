package model.pollination;

public class PollenGrain {
	
	//Pollen grain class.  Each pollen grain has info on plant_type and unique plant identifier
	//(plant_id) in that generation. Rather than keep track of the genetics of each pollen grain,
	//we keep track of which plant it belonged too and then use plant genetic info if/when the pollen
	//grain fertilizes an ovule. 
	public int plant_type, plant_id;
	
	public PollenGrain( int plant_type, int plant_id) {
		this.plant_type = plant_type;
		this.plant_id = plant_id;

	}


}
