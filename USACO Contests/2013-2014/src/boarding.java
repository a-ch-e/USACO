/*
Allen Cheng
USACO Gold Division
February Contest 2014 Problem 3: Airplane Boarding
 */

/*
Problem 3: Airplane Boarding [Travis Hance]

FJ's cows have decided to take a vacation, and have miraculously managed to
find an airline willing to sell them tickets.  When they arrive at the
airport and start boarding their plane, they face an interesting problem,
however.

The airplane has N seats, which we model as the points x=1 through x=N on
the number line.  All N cows (1 <= N <= 200,000) are standing in line 
waiting to get to their seats.  Cow N is at position x=0, Cow N-1 is at
position x=-1, and so on.  Cow i has been assigned to Seat S_i, where
S_1,...,S_N is a permutation of 1,...,N.

At each time step, each cow takes a step to the right if she can. When cow
i reaches her seat S_i, she will stop to put her baggage in the overhead
bin, which takes T_i seconds, and then sit down. For those T_i steps, the
cow behind her (if there is one) is blocked from moving forward.  If there
is a line of cows behind her, the line is effectively blocked as well.

How long will it take for all the cows to sit down?

The sum of T_i for all cows will be less than 1,000,000,000. 

PROBLEM NAME: boarding

INPUT FORMAT:

* Line 1: A single integer, N.

* Lines 2..N+1: Two space-separated integers, S_i and T_i.

SAMPLE INPUT (file boarding.in):

3
2 5
3 10
1 5

INPUT DETAILS:

Initially, the cows are situated like this:

cows -> 123
           123 <- seats

with cow 1 trying to get to seat 2, cow 2 trying to get to seat 3, and cow
3 trying to get to seat 1.

OUTPUT FORMAT:

* Line 1: A single line indicating the amount of time it takes to seat
        all cows.

SAMPLE OUTPUT (file boarding.out):

19

OUTPUT DETAILS:

After one step, they will all move 1 to the right and cow 3 will reach her
seat:

 123
   123

Cow 3 takes 5 seconds to sit down, at which point she effectively 
disappears.

 12
   123

It takes 3 more seconds for cows 1 and 2 to reach their desired seats:

    12
   123

It takes 5 seconds for cow 1 to sit down and 10 seconds for cow 2 to sit
down, so that's 10 seconds total.

In total this took 1 + 5 + 3 + 10 = 19 seconds.
 */

import java.io.*;
import java.util.*;

public class boarding {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("boarding.in"));
		int N = Integer.parseInt(read.readLine());
		PlaneCow[] cows = new PlaneCow[N];
		for(int i = 0; i < N; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			int d = Integer.parseInt(st.nextToken()) - (i - (N - 1));
			int t = Integer.parseInt(st.nextToken());
			cows[i] = new PlaneCow(d, t);
		}
		read.close();
		
		Arrays.sort(cows);
//		System.out.println(Arrays.toString(cows));
		
		int distance = 0;
		int time = 0;
		
		for(int i = 0; i < N; i++){
			distance = cows[i].dist;
			int sitDown = cows[i].time;
			while(i < cows.length - 1 && cows[i + 1].dist == cows[i].dist){
				i++;
				sitDown = cows[i].time;
			}
			time += sitDown;
		}
		int ans = distance + time;
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("boarding.out")));
		out.println(ans);
		System.out.println(ans);
		out.close();
		System.exit(0);
		
	}
	
}

class PlaneCow implements Comparable<PlaneCow>{
	
	int dist, time;
	
	public PlaneCow(int d, int t){
		
		dist = d;
		time = t;
		
	}
	
	public int compareTo(PlaneCow other){
		
		if(dist == other.dist)
			return time - other.time;
		return dist - other.dist;
	}
	
	public String toString(){
		
		return "Distance " + dist + " time " + time;
		
	}
	
}