/*
 * Allen Cheng
 * TJHSST
 * Addepar Hackathon 2014: Problem 5
 * JAVA
 */

import java.io.*;
import java.util.*;

public class Analytics {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken());
		boolean[][] cache = new boolean[N][N];
		for(int i = 0; i < C; i++){
			st = new StringTokenizer(read.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			cache[a][b] = true;
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		for(int m = 0; m < Q; m++){
			st = new StringTokenizer(read.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
		}
		
		read.close();
		out.close();
		System.exit(0);
		
	}
	
}
