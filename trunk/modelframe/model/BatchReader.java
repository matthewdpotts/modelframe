package model;

import java.io.IOException;

public interface BatchReader {

	public void readConfig ( String filename ) throws IOException;
	public void run ( );
	
}
