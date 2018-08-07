/*
 * Allen Cheng
 * TJHSST
 * Addepar Hackathon, Problem 1: Upstairs
 * JAVA
 */

import java.io.*;
import java.util.*;

public class Upstairs {
	
	 public static void main(String[] args) throws IOException{
		 
		 BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		 int N = Integer.parseInt(read.readLine());
		 int[] classes = new int[N];
		 StringTokenizer st = new StringTokenizer(read.readLine());
		 for(int i = 0; i < N; i++){
			 classes[i] = Integer.parseInt(st.nextToken());
		 }
		 read.close();
		 
		 int best = -1;
		 int raw = 0;
		 for(int i = 0; i < N - 1; i++){
			 raw += Math.max(0, classes[i + 1] - classes[i]);
		 }
//		 System.out.println(raw);
		 int min = raw;
		 for(int i = 0; i < N - 1; i++){
			 int diff = 0;
			 if(i > 0)
				 diff += Math.max(0, classes[i + 1] - classes[i - 1]) - Math.max(0, classes[i] - classes[i - 1]);
			 if(i < N - 2)
				 diff += Math.max(0, classes[i + 2] - classes[i]) - Math.max(0, classes[i + 2] - classes[i + 1]);
			 diff -= (classes[i + 1] - classes[i]);
			 if(raw + diff < min){
				 best = i + 1;
				 min = raw + diff;
			 }
//			 System.out.println(raw + diff);
		 }
		 System.out.println(best);
		 System.exit(0);
		 
	 }
}
