package model;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.FileOutputStream;

/**
 * Printer defines an object which can be used
 * for simple generic output to flat text files
 * which are appropriate for importing into statistical
 * analysis tools (ie: R, SAS, Matlab).
 * 
 * @author	Zachary L. Brown
 * @version	0.1.0
 * @since	0.1.0
 */
public class Printer {

	private FileOutputStream f_stream;
	private PrintStream p_writer;
	private String header;	
	
	/**
	 * Class constructor for Printer which when called, requires
	 * setting an output file, and the header for the file.
	 * 
	 * @param file <code>String</code> object which defines the output file
	 * @param header <code>String</code> object which defines the header for the output file
	 * @throws FileNotFoundException
	 */
	public Printer(String file, String header) throws FileNotFoundException {
		f_stream = new FileOutputStream(file, true);
		p_writer = new PrintStream(f_stream);
		this.header = header;
	}
	
	/**
	 * Closes the file to which the Printer object is printing to.
	 */
	public void closeFile() {
		p_writer.close();
	}
	
	/**
	 * Generic printing function which can print a single line of 
	 * text in any specified format by the user in the parameter "text".
	 * @param text <code>String</code> object which defines the line to be printed
	 * to the output file
	 */
	public void printData(String text) {
		p_writer.println(text);
	}
	
	/**
	 * Prints the header to the output file if such is required.
	 */
	public void printHeader() {
		p_writer.println(header);
	}
	
}
