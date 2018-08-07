/*
ID: ayc22032
LANG: JAVA
TASK: money
 */

/*
Money Systems

The cows have not only created their own government but they have chosen to create their
own money system. In their own rebellious way, they are curious about values of coinage.
Traditionally, coins come in values like 1, 5, 10, 20 or 25, 50, and 100 units, sometimes
with a 2 unit coin thrown in for good measure.

The cows want to know how many different ways it is possible to dispense a certain amount
of money using various coin systems. For instance, using a system of {1, 2, 5, 10, ...} it
is possible to create 18 units several different ways, including: 18x1, 9x2, 8x2+2x1, 3x5+2+1,
and many others.

Write a program to compute how many ways to construct a given amount of money using supplied
coinage. It is guaranteed that the total will fit into both a signed long long (C/C++) and
Int64 (Free Pascal).

PROGRAM NAME: money

INPUT FORMAT

The number of coins in the system is V (1 <= V <= 25).

The amount money to construct is N (1 <= N <= 10,000).

Line 1:	Two integers, V and N
Lines 2..:	V integers that represent the available coins (no particular number of integers per line)

SAMPLE INPUT (file money.in)

3 10
1 2 5

OUTPUT FORMAT

A single line containing the total number of ways to construct N money units using V coins.

SAMPLE OUTPUT (file money.out)

10
 */

import java.io.*;
import java.util.*;

public class money {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("money.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int clen = Integer.parseInt(st.nextToken());
		int[] coins = new int[clen];
		int n = Integer.parseInt(st.nextToken());
		long[] poss = new long[n + 1];
		for(int i = 0; ;){
			String s = read.readLine();
			if(s == null)
				break;
			st = new StringTokenizer(s);
			while(st.hasMoreTokens()){
				coins[i] = Integer.parseInt(st.nextToken());
				i++;
			}
		}
		read.close();
//		for(int i : coins)
//			System.out.println(i);
		
		Arrays.sort(coins);
		poss[0] = 1;
//		for(int i = 1; i <= n; i++){
//			for(int j = 0; j < coins.length; j++){
//				if(coins[j] <= i){
//					poss[i] += poss[i - coins[j]];
//					System.out.print(poss[i] + " ");
//				}
//			}
//			System.out.println();
//		}
		for(int i = 0; i < coins.length; i++){
			long[] temp = Arrays.copyOf(poss, poss.length);
			for(int j = 1; j <= n / coins[i]; j++){
				for(int k = j * coins[i]; k <= n; k++){
					temp[k] += poss[k - j * coins[i]];
				}
			}
			poss = temp;
//			for(int j : poss)
//				System.out.print(j + " ");
//			System.out.println();
		}
//		for(int i : poss)
//			System.out.print(i + " ");
//		System.out.println();
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("money.out")));
		System.out.println(poss[n]);
		out.println(poss[n]);
		out.close();
		System.exit(0);
		
	}
	
}
