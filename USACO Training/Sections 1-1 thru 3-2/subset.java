/*
ID: ayc22032
LANG: JAVA
TASK: subset
 */

/*

Subset Sums
JRM
For many sets of consecutive integers from 1 through N (1 <= N <= 39), one can partition the set into two sets whose sums are identical.

For example, if N=3, one can partition the set {1, 2, 3} in one way so that the sums of both subsets are identical:

{3} and {1,2}

This counts as a single partitioning (i.e., reversing the order counts as the same partitioning and thus does not increase the count of partitions).

If N=7, there are four ways to partition the set {1, 2, 3, ... 7} so that each partition has the same sum:

{1,6,7} and {2,3,4,5}
{2,5,7} and {1,3,4,6}
{3,4,7} and {1,2,5,6}
{1,2,4,7} and {3,5,6}

Given N, your program should print the number of ways a set containing the integers from 1 through N can be partitioned into two sets whose sums are
identical. Print 0 if there are no such ways.

Your program must calculate the answer, not look it up from a table.

PROGRAM NAME: subset

INPUT FORMAT

The input file contains a single line with a single integer representing N, as above.
SAMPLE INPUT (file subset.in)

7
OUTPUT FORMAT

The output file contains a single line with a single integer that tells how many same-sum partitions can be made from the set {1, 2, ..., N}. The output
file should contain 0 if there are no ways to make a same-sum partition.

SAMPLE OUTPUT (file subset.out)

4

 */

import java.io.*;
import java.util.*;

public class subset {
		
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("subset.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("subset.out")));
		int N = Integer.parseInt(read.readLine());
		read.close();
		
		if(N % 4 == 1 || N % 4 == 2){
			System.out.println("0");
			out.println("0");
			out.close();
			System.exit(0);
		}
		
		int half = N * (N + 1) / 4;
		long[] counts = new long[half * 2 + 1];
		for(int i = 1; i <= N; i++){
			for(int j = counts.length - 1 - i; j > 0; j--){
				if(counts[j] > 0)
					counts[j + i]+= counts[j];
			}
			counts[i]++;
//			for(int j = 0 ; j < counts.length; j++){
//				System.out.print(counts[j] + " ");
//			}
//			System.out.println();
		}
//		for(int i = 0 ; i < counts.length; i++){
//			System.out.print(counts[i] + " ");
//		}
//		System.out.println();
		
//		int count = find(half - N, N);
		System.out.println(counts[half] / 2);
		out.println(counts[half] / 2);
		out.close();
		System.exit(0);
		
	}
	
	private static int find(int target, int last){
		
//		System.out.println("Target: " + target + ", added " + last + " last.");
		if(target == 0)
			return 1;
		int count = 0;
		for(int i = Math.min(last - 1, target); i > 0; i--){
			count += find(target - i, i);
		}
		return count;
		
	}
	
}
