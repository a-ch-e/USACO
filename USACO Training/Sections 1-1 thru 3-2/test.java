/*
ID: ayc22031
LANG: JAVA
TASK: test
 */

import java.io.*;
import java.util.StringTokenizer;


public class test {
	
	public static void main(String[] args) throws IOException{
		BufferedReader read = new BufferedReader(new FileReader("test.in"));
		StringTokenizer tokens = new StringTokenizer(read.readLine());
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("test.out")));
	    int a = Integer.parseInt(tokens.nextToken());
	    int b = Integer.parseInt(tokens.nextToken());
	    out.println((a + b));
	    out.close();
	    System.exit(0);
	
	}
	
}
