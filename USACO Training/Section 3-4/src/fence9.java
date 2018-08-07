/*
ID: ayc22032
LANG: JAVA
TASK: fence9
*/

import java.io.*;
import java.util.*;

public class fence9 {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("fence9.in"));;
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int P = Integer.parseInt(st.nextToken());
		read.close();
		
		int ans = 0;
		for(int i = 1; i < M; i++){
			double leftX = ((double)N * i) / M;
			double rightX = ((double)(N - P)) * i / M + P;
			ans += Math.ceil(rightX - 1) + 1 - Math.floor(leftX + 1);
//			System.out.println(ans);
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("fence9.out"));
		out.println(ans);
		System.out.println(ans);
		out.close();
		System.exit(0);
		
	}
	
}
