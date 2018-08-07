/*
Allen Cheng, ID ayc22032
2014-15 USACO December
 */

/*
Problem 3: Cow Jog [Mark Gordon, 2014]

Farmer John's N cows (1 <= N <= 100,000) are out exercising their
hooves again, jogging along an infinite track.  Each cow starts at a
distinct position on the track, and some cows run at different speeds.

The track is divided into lanes so that cows may move past each other.
No two cows in the same lane may ever occupy the same position.
Farmer John doesn't want any cow to have to change lanes or adjust
speed, and he wonders how many lanes he will need to accomplish this
if the cows are going to run for T minutes (1 <= T <= 1,000,000,000).

INPUT: (file cowjog.in)

The first line of input contains N and T.

The following N lines each contain the initial position and speed of a
single cow.  Position is a nonnegative integer and speed is a positive
integer; both numbers are at most 1 billion.  All cows start at
distinct positions, and these will be given in increasing order in the
input.

SAMPLE INPUT:

5 3
0 1
1 2
2 3
3 2
6 1

OUTPUT: (file cowjog.out)

A single integer indicating the minimum number of lanes necessary so
that no two cows in the same lane ever occupy the same location
(including at time T).

SAMPLE OUTPUT:

3
 */

import java.io.*;
import java.util.*;

public class cowjog {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("cowjog.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		long T = Long.parseLong(st.nextToken());
		JogCow[] cows = new JogCow[N];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(read.readLine());
			long loc = Integer.parseInt(st.nextToken());
			loc += T * Long.parseLong(st.nextToken());
			cows[i] = new JogCow(i, loc);
		}
		read.close();

		Arrays.sort(cows);

		Integer[] lanes = new Integer[N];
		lanes[0] = cows[0].ind;
		int len = 1;
		for(int i = 1; i < N; i++){
			if(cows[i].ind < lanes[len - 1]){
				lanes[len++] = cows[i].ind;
			} else {
				int ind = 0;
				ind = -(Arrays.binarySearch(lanes, 0, len, cows[i].ind, new ReverseComp()) + 1);
				lanes[ind] =  cows[i].ind;
			}
		}
		PrintWriter out = new PrintWriter(new FileWriter("cowjog.out"));
		out.println(len);
		System.out.println(len);
		out.close();
		System.exit(0);
		
	}
	
}

class JogCow implements Comparable<JogCow>{
	
	int ind;
	long loc;
	
	public JogCow(int i, long l){
		ind = i;
		loc = l;
	}
	
	public int compareTo(JogCow other){
		if(this.loc == other.loc){
			return (other.ind - this.ind);
		}
		if(this.loc > other.loc)
			return 1;
		return -1;
	}
	
}

class ReverseComp implements Comparator<Integer>{
	
	public int compare(Integer a, Integer b){
		return b - a;
	}
}