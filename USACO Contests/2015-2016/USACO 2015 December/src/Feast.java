/*
Allen Cheng, ID ayc22032
USACO, December 2015
Gold 2: Feast
*/

/*
Bessie has broken into Farmer John's house again! She has discovered a pile of lemons and a pile of
oranges in the kitchen (effectively an unlimited number of each), and she is determined to eat as
much as possible.
Bessie has a maximum fullness of T (1 <= T <= 5,000,000). Eating an orange increases her fullness by A,
and eating a lemon increases her fullness by B (1 <= A,B <= T). Additionally, if she wants, Bessie can
drink water at most one time, which will instantly decrease her fullness by half (and will round
down).

Help Bessie determine the maximum fullness she can achieve!

INPUT FORMAT (file feast.in):

The first (and only) line has three integers T, A, and B.

OUTPUT FORMAT (file feast.out):

A single integer, representing the maximum fullness Bessie can achieve.

SAMPLE INPUT:

8 5 6

SAMPLE OUTPUT:

8
Problem credits: Nathan Pinsker
*/

import java.io.*;
import java.util.*;

public class Feast {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("feast.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		read.close();
		
		int T = Integer.parseInt(st.nextToken());
		int A = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());
		
		boolean[] dp = new boolean[T + 1];
		dp[0] = true;
		//no drinking
		for(int i = 1; i <= T; i++){
			if((i - A >= 0 && dp[i - A]) || (i - B >= 0 && dp[i - B]))
				dp[i] = true;
		}
		//drinking
		for(int i = 1; i <= T; i++){
			if(dp[i])
				dp[i / 2] = true;
		}
		for(int i = 1; i <= T; i++){
			if((i - A >= 0 && dp[i - A]) || (i - B >= 0 && dp[i - B]))
				dp[i] = true;
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("feast.out"));
		for(int i = T; i >= 0; i--){
			if(dp[i]){
				out.println(i);
				System.out.println(i);
				break;
			}
		}
		out.close();
		System.exit(0);
		
	}
	
}
