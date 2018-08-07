/*
ID: ayc22032
LANG: JAVA
TASK: kimbits
 */

/*
Stringsobits
Kim Schrijvers

Consider an ordered set S of strings of N (1 <= N <= 31) bits. Bits,
of course, are either 0 or 1.

This set of strings is interesting because it is ordered and contains
all possible strings of length N that have L (1 <= L <= N) or fewer
bits that are `1'.

Your task is to read a number I (1 <= I <= sizeof(S)) from the input
and print the Ith element of the ordered set for N bits with no more
than L bits that are `1'.

PROGRAM NAME: kimbits

INPUT FORMAT

A single line with three space separated integers: N, L, and I.

SAMPLE INPUT (file kimbits.in)

5 3 19

OUTPUT FORMAT

A single line containing the integer that represents the Ith element
from the order set, as described.
 
SAMPLE OUTPUT (file kimbits.out)

10011
 */

import java.io.*;
import java.util.*;

public class kimbits {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("kimbits.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int n = Integer.parseInt(st.nextToken());
		int l = Integer.parseInt(st.nextToken());
		int I = (int)(Long.parseLong(st.nextToken()) - 1);
		read.close();
		
		int[][] pascal = pascalTriangle(n);
		long[] powers = pow(n);
		int[] ans = new int[n];
//		for(int i = 0; i < pascal.length; i++){
//			for(int j = 0; j <= i; j++){
//				System.out.print(pascal[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		while(l > 0 && I > 0){
//			System.out.println(l);
			int prev = 0;
			for(int i = 1; i <= n; i++){
				long a = powers[i];
				for(int j = 0; j < i - l; j++){
					a -= pascal[i][j];
//					System.out.println(pascal[i][j]);
				}
//				System.out.println(i + " " + a + " " + I);
				if(a > I){
					ans[n - i] = 1;
					I = I - prev;
					l--;
					break;
				}
				prev = (int)a;
			}
			if(I <= 0)
				break;
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("kimbits.out")));
		for(int i = 0; i < ans.length; i++){
			System.out.print(ans[i]);
			out.print(ans[i]);
		}
		out.println();
		out.close();
		System.exit(0);
		
	}
	
	private static int[][] pascalTriangle(int n){
		
		int[][] pascal = new int[n + 1][n + 1];
		for(int i = 0; i < pascal.length; i++){
			pascal[i][0] = 1;
		}
		for(int i = 1; i < pascal.length; i++){
			for(int j = 1; j <= i; j++){
				pascal[i][j] = pascal[i - 1][j - 1] + pascal[i - 1][j];
			}
		}
		return pascal;
		
	}
	
	private static long[] pow(int n){
		long ans[] = new long[n + 1];
		ans[0] = 1;
		for(int i = 1; i < ans.length; i++)
			ans[i] = ans[i - 1] * 2;
		return ans;
	}
		
}
