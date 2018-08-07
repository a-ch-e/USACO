/*
 * Allen Cheng
 * ID: ayc22032
 * Lang: JAVA
 * Task: cowfind
 */

import java.io.*;

public class cowfind {
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("cowfind.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowfind.out")));
		String s = read.readLine();
		int hinds = 0;
		int matches = 0;
		while(s.length() > 1){
			if(s.substring(0, 2).equals("(("))
				++hinds;
			else if(s.substring(0, 2).equals("))"))
				matches += hinds;
			s = s.substring(1);
		}
		out.println("" + matches);
		out.close();
		System.exit(0);
		
	}
	
}
