/*
Allen Cheng
USACO Silver Division
January 2014 Contest Problem 1: Bessie Slows Down
 */

/*
Problem 1: Bessie Slows Down [Brian Dean, 2014]

Bessie the cow is competing in a cross-country skiing event at the winter
Moolympic games.  She starts out at a speed of 1 meter per second. 
However, as she becomes more tired over time, she begins to slow down. 
Each time Bessie slows down, her speed decreases: she moves at 1/2 meter
per second after slowing down once, then 1/3 meter per second after slowing
down twice, and so on.

You are told when and where Bessie slows down, in terms of a series of
events.  An event like this:

T 17

means that Bessie slows down at a specific time -- here, 17 seconds into
the race.  An event like this:

D 10

means that Bessie slows down at a specific distance from the start -- in
this case, 10 meters.

Given a list of N such events (1 <= N <= 10,000), please compute the amount
of time, in seconds, for Bessie to travel an entire kilometer.  Round your
answer to the nearest integer second (0.5 rounds up to 1).

PROBLEM NAME: slowdown

INPUT FORMAT:

* Line 1: The value of N.

* Lines 2..1+N: Each line is of the form "T x" or "D x", indicating a
        time event or a distance event.  In both cases, x is an
        integer that is guaranteed to place the event before Bessie
        reaches one kilometer of total distance.  It is possible for
        multiple events to occur simultaneously, causing Bessie to
        slow down quite a bit all at once.  Events may not be listed
        in order.

SAMPLE INPUT (file slowdown.in):

2
T 30
D 10

INPUT DETAILS:

Bessie slows down at time t = 30 and at distance d = 10.

OUTPUT FORMAT:

* Line 1: The total time required for Bessie to travel 1 kilometer.

SAMPLE OUTPUT (file slowdown.out):

2970

OUTPUT DETAILS:

Bessie travels the first 10 meters at 1 meter/second, taking 10 seconds. 
She then slows down to 1/2 meter/second, taking 20 seconds to travel the
next 10 meters.  She then reaches the 30 second mark, where she slows down
again to 1/3 meter/second.  The remaining 980 meters therefore take her
980 * 3 = 2940 seconds.  The total time is therefore 10 + 20 + 2940 = 2970.
 */

import java.io.*;
import java.util.*;

public class slowdown {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("slowdown.in"));
		int N = Integer.parseInt(read.readLine());
		ArrayList<Integer> timeList = new ArrayList<Integer>(N);
		ArrayList<Integer> distList = new ArrayList<Integer>(N);
		for(int i = 0; i < N; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			if(st.nextToken().charAt(0) == 'T')
				timeList.add(Integer.parseInt(st.nextToken()));
			else
				distList.add(Integer.parseInt(st.nextToken()));
		}
		read.close();
		Integer[] times = new Integer[timeList.size()];
		times = timeList.toArray(times);
		Integer[] dists = new Integer[distList.size()];
		dists = distList.toArray(dists);
		Arrays.sort(times);
		Arrays.sort(dists);
//		for(int i = 0; i < times.length; i++){
//			System.out.print(times[i] + " ");
//		}
//		System.out.println();
//		for(int i = 0; i < dists.length; i++){
//			System.out.print(dists[i] + " ");
//		}
//		System.out.println();
		
		int timeInd = 0;
		int distInd = 0;
		int inverseSpeed = 1;
		double currentDistance = 0;
		double currentTime = 0;
		
		while(timeInd < times.length || distInd < dists.length){
//			System.out.println("Distance " + currentDistance + ", Time " + currentTime);
			
			double speed = 1.0 / inverseSpeed;
			if(timeInd >= times.length){
				int nextSlowdown = dists[distInd];
//				System.out.println(nextSlowdown);
				currentTime += (nextSlowdown - currentDistance) / speed;
				currentDistance = nextSlowdown;
				distInd++;
				inverseSpeed++;
				continue;
			}
			if(distInd >= dists.length){
				double nextTime = times[timeInd];
				double nextDistance = currentDistance + speed * (nextTime - currentTime);
//				System.out.println("Distance " + nextDistance + ", Time " + nextTime);
				currentDistance = nextDistance;
				currentTime = nextTime;
				timeInd++;
				inverseSpeed++;
				continue;
			}
			double nextTime = times[timeInd];
			double nextDistance = currentDistance + speed * (nextTime - currentTime);
			int nextSlowdown = dists[distInd];
			if(nextSlowdown < nextDistance){
				currentTime += (nextSlowdown - currentDistance) / speed;
				currentDistance = nextSlowdown;
				distInd++;
			} else {
				currentDistance = nextDistance;
				currentTime = nextTime;
				timeInd++;
			}
			inverseSpeed++;
		}
//		System.out.println("Distance " + currentDistance + ", Time " + currentTime);
		currentTime += (1000.0 - currentDistance) * inverseSpeed;
		long ans = Math.round(currentTime);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("slowdown.out")));
		out.println(ans);
		System.out.println(ans);
		out.close();
		System.exit(0);
		
	}
	
}
