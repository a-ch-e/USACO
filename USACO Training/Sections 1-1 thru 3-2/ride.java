/*
ID: ayc22031
LANG: JAVA
PROG: ride
 */
import java.io.*;

public class ride {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("ride.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ride.out")));
		int a = 1;
		int b = 1;
		String x = read.readLine();
		String y = read.readLine();
		for(int i = 0; i < x.length(); i++){
			a *= ((int)x.charAt(i)) - (int)'A' + 1;
		}
		for(int i = 0; i < y.length(); i++){
			b *= ((int)y.charAt(i)) - (int)'A' + 1;
		}
		if(a % 47 == b % 47){
			out.println("GO");
			out.close();
			System.exit(0);
		}
		out.println("STAY");
		out.close();
		System.exit(0);
		
	}
}
