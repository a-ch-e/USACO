/*
ID: ayc22032
LANG: JAVA
TASK: heritage
*/

import java.io.*;

public class heritage {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("heritage.in"));
		String inOrd = read.readLine();
		String preOrd = read.readLine();
		read.close();
		
		String postOrd = postOrd(inOrd, preOrd);
		
		PrintWriter out = new PrintWriter(new FileWriter("heritage.out"));
		out.println(postOrd);
		System.out.println(postOrd);
		out.close();
		System.exit(0);
		
	}
	
	private static String postOrd(String in, String pre){
		
		if(in.length() <= 1)
			return in;
//		System.out.println("in: " + in);
//		System.out.println("pre: " + pre);
		char root = pre.charAt(0);
		int ind = in.indexOf(root);
		String left = in.substring(0, ind);
		String right = in.substring(ind + 1);
		return postOrd(left, pre.substring(1, 1 + left.length())) + postOrd(right, pre.substring(1 + left.length())) + root;
		
	}
	
}
