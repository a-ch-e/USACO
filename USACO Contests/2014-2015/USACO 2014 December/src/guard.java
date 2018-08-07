/*
Allen Cheng, ID ayc22032
2014-15 USACO December
 */

/*
Problem 1: Guard Mark [Bill Cooperman, 2014]

Farmer John and his herd are playing frisbee.  Bessie throws the
frisbee down the field, but it's going straight to Mark the field hand
on the other team!  Mark has height H (1 <= H <= 1,000,000,000), but
there are N cows on Bessie's team gathered around Mark (2 <= N <= 20).
They can only catch the frisbee if they can stack up to be at least as
high as Mark.  Each of the N cows has a height, weight, and strength.
A cow's strength indicates the maximum amount of total weight of the
cows that can be stacked above her.  

Given these constraints, Bessie wants to know if it is possible for
her team to build a tall enough stack to catch the frisbee, and if so,
what is the maximum safety factor of such a stack.  The safety factor
of a stack is the amount of weight that can be added to the top of the
stack without exceeding any cow's strength.

INPUT: (file guard.in)

The first line of input contains N and H.

The next N lines of input each describe a cow, giving its height,
weight, and strength.  All are positive integers at most 1 billion.

SAMPLE INPUT:

4 10
9 4 1
3 3 5
5 5 10
4 4 5

OUTPUT: (file guard.out)

If Bessie's team can build a stack tall enough to catch the frisbee,
please output the maximum achievable safety factor for such a stack.
Otherwise output "Mark is too tall" (without the quotes).

SAMPLE OUTPUT:

2
 */

import java.io.*;
import java.util.*;

public class guard {
	
	private static long[] safeties;
	private static long[] weights;
	private static long[] heights;
	private static int N;
	
	private static long[] hgts;
	private static long[] wgts;
	private static long[] strs;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("guard.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		N = Integer.parseInt(st.nextToken());
		long H = Long.parseLong(st.nextToken());
		hgts = new long[N];
		wgts = new long[N];
		strs = new long[N];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(read.readLine());
			hgts[i] = Long.parseLong(st.nextToken());
			wgts[i] = Long.parseLong(st.nextToken());
			strs[i] = Long.parseLong(st.nextToken());
		}
		read.close();
		
		safeties = new long[pow2(N)];
		weights = new long[safeties.length];
		heights = new long[safeties.length];
		
		safeties[0] = Long.MAX_VALUE;
		weights[0] = 0;
		
		for(int len = 1; len <= N; len++){
			generate(len, 0, N);
		}
		long max = -1;
		for(int i = 0; i < safeties.length; i++){
			if(heights[i] > H && safeties[i] > max)
				max = safeties[i];
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("guard.out"));
		if(max < 0){
			System.out.println("Mark is too tall");
			out.println("Mark is too tall");
		} else {
			System.out.println(max);
			out.println(max);
		}
		out.close();
		System.exit(0);
		
		
	}
	
	private static void generate(int len, int curr, int n){
		
		if(len == 0){
			curr *= pow2(n);
			int sub = 1;
			safeties[curr] = -1;
			for(int i = 0; i < N; i++){
				int ind = curr - sub;
				sub *= 2;
				if((ind | curr) != curr)
					continue;
				if(safeties[ind] == -1)
					continue;
				long comp = Math.min(strs[i] - weights[ind], safeties[ind]);
				if(comp > safeties[curr]){
					safeties[curr] = comp;
					heights[curr] = hgts[i] + heights[ind];
					weights[curr] = wgts[i] + weights[ind];
				}
				
			}

			return;
		}
		for(int i = 0; i <= n - len; i++){
			curr *= 2;
			generate(len - 1, curr + 1, n - i - 1);
		}
		
	}
	
	private static int pow2(int n){
		int a = 1;
		for(int i = 0; i < n; i++){
			a *= 2;
		}
		return a;
	}
	
}
