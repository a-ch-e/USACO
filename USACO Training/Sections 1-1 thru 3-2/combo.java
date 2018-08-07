/*
ID: ayc22032
LANG: JAVA
TASK: combo
 */
import java.io.*;
import java.util.*;

public class combo {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("combo.in"));
		int N = Integer.parseInt(read.readLine());
		int[] c1 = new int[3];
		int[] c2 = new int[3];
		StringTokenizer st = new StringTokenizer(read.readLine());
		for(int i = 0; i < 3; i++){
			c1[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(read.readLine());
		for(int i = 0; i < 3; i++){
			c2[i] = Integer.parseInt(st.nextToken());
		}
		read.close();
		
		int count = 0;
		for(int i = 1; i <= N; i++){
			for(int j = 1; j <= N; j++){
				for(int k = 1; k <= N; k++){
					if((Math.abs(c1[0] - i) <= 2 || Math.abs(c1[0] - i) >= N - 2) && (Math.abs(c1[1] - j) <= 2 || Math.abs(c1[1] - j) >= N - 2) && (Math.abs(c1[2] - k) <= 2 || Math.abs(c1[2] - k) >= N - 2))
						count++;
					else if((Math.abs(c2[0] - i) <= 2 || Math.abs(c2[0] - i) >= N - 2) && (Math.abs(c2[1] - j) <= 2 || Math.abs(c2[1] - j) >= N - 2) && (Math.abs(c2[2] - k) <= 2 || Math.abs(c2[2] - k) >= N - 2))
						count++;
				}
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("combo.out"));
		out.println(count);
		System.out.println(count);
		out.close();
		
	}
	
}
