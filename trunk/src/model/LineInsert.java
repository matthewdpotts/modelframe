package model;

import java.io.*;

public class LineInsert {
		
		public void run() throws Exception {
			String file, header;
			header = new String("t\tb\tw\tba");
			
			for(int i = 2; i < 101; i++) {
				file = new String("output_m"+i+".txt");
				insertStringInFile(new File(file), 1, header);
			}
		}
	     

	   public void insertStringInFile(File inFile, int lineno, String lineToBeInserted) 
	       throws Exception {
	     // temp file
	     File outFile = new File("$$$$$$$$.tmp");
	     
	     // input
	     FileInputStream fis  = new FileInputStream(inFile);
	     BufferedReader in = new BufferedReader
	         (new InputStreamReader(fis));

	     // output         
	     FileOutputStream fos = new FileOutputStream(outFile);
	     PrintWriter out = new PrintWriter(fos);

	     String thisLine = "";
	     int i =1;
	     while ((thisLine = in.readLine()) != null) {
	       if(i == lineno) out.println(lineToBeInserted);
	       out.println(thisLine);
	       i++;
	       }
	    out.flush();
	    out.close();
	    in.close();
	    
	    inFile.delete();
	    outFile.renameTo(inFile);
	  }
}
