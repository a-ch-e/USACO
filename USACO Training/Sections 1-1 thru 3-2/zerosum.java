/*
ID: ayc22032
LANG: JAVA
TASK: zerosum
 */

/*

Zero Sum

Consider the sequence of digits from 1 through N (where N=9) in increasing order: 1 2 3 ... N.

Now insert either a `+' for addition or a `-' for subtraction or a ` ' [blank] to run the digits
together between each pair of digits (not in front of the first digit). Calculate the result that
of the expression and see if you get zero.

Write a program that will find all sequences of length N that produce a zero sum.

PROGRAM NAME: zerosum

INPUT FORMAT

A single line with the integer N (3 <= N <= 9).

SAMPLE INPUT (file zerosum.in)

7

OUTPUT FORMAT

In ASCII order, show each sequence that can create 0 sum with a `+', `-', or ` ' between each pair
of numbers.

SAMPLE OUTPUT (file zerosum.out)

1+2-3+4-5-6+7
1+2-3-4+5+6-7
1-2 3+4+5+6+7
1-2 3-4 5+6 7
1-2+3+4-5+6-7
1-2-3-4-5+6+7

 */

import java.io.*;
import java.util.*;

public class zerosum {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("zerosum.in"));
		int len = Integer.parseInt(read.readLine());
		read.close();
		
		int[][] poss = new int[pow(len - 1)][len - 1];
		boolean[] works = new boolean[poss.length];
		
		for(int i = 0; i < poss.length; i++){
			int temp = i;
			for(int j = len - 2; j >= 0; j--){
				poss[i][j] = (temp % 3) - 1;
				temp /= 3;
			}
			if(works(poss[i]))
				works[i] = true;
		}
//		System.out.println(works(poss[343]));
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("zerosum.out")));
		for(int i = 0; i < works.length; i++){
			if(works[i]){
				System.out.println(string(poss[i]));
//				for(int j : poss[i]){
//					System.out.print(j + " ");
//				}
//				System.out.println();
				out.println(string(poss[i]));
			}
		}
		out.close();
		
	}
	
	private static String string(int[] signs){
		
		String s = "1";
		for(int i = 0; i < signs.length; i++){
			if(signs[i] == -1)
				s += " ";
			else if(signs[i] == 0)
				s += "+";
			else
				s += "-";
			s += (i + 2);
		}
		return s;
		
	}
	
	public static boolean works(int[] signs){
		
		int con = 0;
		for(int i : signs)
			if(i == -1)
				con++;
		int[] adds = new int[signs.length + 1 - con];
//		for(int i : signs){
//			System.out.print(i + " ");
//		}
//		System.out.println();
		int ind = 0;
		adds[0] = 1;
		for(int i = 2; i < signs.length + 2; i++){
			if(signs[i - 2] == -1){
				adds[ind] *= 10;
				adds[ind] += Math.abs(adds[ind]) / adds[ind] * i;
			} else if(signs[i - 2] == 0)
				adds[++ind] = i;
			else
				adds[++ind] = i * -1;
		}
		int sum = 0;
		for(int i : adds){
			sum += i;
//			System.out.print(i + " ");
		}
//		System.out.println();
		return sum == 0;
		
	}
	
	private static int pow(int p){
		int a = 1;
		for(int i = 0; i < p; i++){
			a *= 3;
		}
		return a;
	}

}

//class CSequence{
//	
//	int len;
//	int[] signs;
//	
//	public CSequence(int l, int ind){
//		len = l;
//		signs = new int[len - 1];
//		for(int i = len - 2; i >= 0; i--){
//			signs[i] = (ind % 3) - 1;
//			ind /= 3;
//		}
//	}
//	
//	public boolean works(){
//		
//		int con = 0;
//		for(int i : signs)
//			if(i == -1)
//				con++;
//		int[] adds = new int[len - con];
//		int ind = 0;
//		adds[0] = 1;
//		for(int i = 2; i < signs.length + 2; i++){
//			if(signs[i - 2] == -1){
//				adds[ind] *= 10;
//				adds[ind] += Math.abs(adds[ind]) / adds[ind] * i;
//			} else if(signs[i - 2] == 0)
//				adds[++ind] = i;
//			else
//				adds[++ind] = i * -1;
//		}
//		int sum = 0;
//		for(int i : adds)
//			sum += i;
//		return sum == 0;
//		
//	}
//	
//}
