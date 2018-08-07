/*
Allen Cheng, ID ayc22032
2014-15 USACO December
 */

/*
Problem 2: Marathon [Nick Wu, 2014]

An avid marathon runner herself, Bessie enjoys creating marathon
courses for her fellow cows to run.  She has most recently designed a
route specified by N checkpoints (1 <= N <= 100,000) that must be
visited in sequence.

Unfortunately, Bessie realizes that the other cows may not have the
stamina to run the full route. She therefore wants to know how long
certain sub-routes take to run, where a sub-route is a contiguous
subsequence of the checkpoints from the full route.  To make matters
more complicated, Bessie knows that the other cows, being lazy, might
choose to skip one checkpoint any time they run a sub-route --
whichever one results in a minimum total travel time.  They are not
allowed to skip the first or last checkpoints in a sub-route, however.

In order to build the best possible marathon course, Bessie wants to
investigate the ramifications of making changes to the checkpoint
locations in her current course.  Please help her determine how
certain changes to checkpoint locations will affect the time required
to run different sub-routes (taking into account that the cows might
choose to omit one checkpoint any time they run a sub-route).

Since the course is set in a downtown area with a grid of streets, the
distance between two checkpoints at locations (x1, y1) and (x2, y2) is
given by |x1-x2| + |y1-y2|.

INPUT: (file marathon.in)

The first line gives N and Q (1 <= Q <= 100,000).

The next N lines contain (x, y) locations of N checkpoints in the
sequence they must be visited along the route.  All coordinates
are in the range -1000 .. 1000.

The next Q lines consist of updates and queries, one per line, to be
processed in the order they are given. Lines will either take the form
"U I X Y" or "Q I J".  A line of the form "U I X Y" indicates that the
location of checkpoint I (1 <= I <= N) is to be changed to location
(X, Y).  A line of the form "Q I J" asks for the minimum travel time
of the sub-route from checkpoint I to checkpoint J (I <= J), given
that the cows choose to skip one checkpoint along this sub-route (but
not the endpoints I and J).

SAMPLE INPUT:

5 5
-4 4
-5 -3
-1 5
-3 4
0 5
Q 1 5
U 4 0 1
U 4 -1 1
Q 2 4
Q 1 4

OUTPUT: (file marathon.out)

For each sub-route length request, output on a single line the desired
length.

SAMPLE OUTPUT:

11
8
8
 */

import java.util.*;
import java.io.*;

public class marathon {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("marathon.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken());
		int[][] points = new int[N][2];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(read.readLine());
			points[i][0] = Integer.parseInt(st.nextToken());
			points[i][1] = Integer.parseInt(st.nextToken());
		}
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("marathon.out")));

		for(int q = 0; q < Q; q++){
			st = new StringTokenizer(read.readLine());
			boolean update = st.nextToken().equals("U");
			
			if(update){
				int i = Integer.parseInt(st.nextToken());
				points[i][0] =  Integer.parseInt(st.nextToken());
				points[i][1] = Integer.parseInt(st.nextToken());
			} else {
				int a = Integer.parseInt(st.nextToken()) - 1;
				int b = Integer.parseInt(st.nextToken()) - 1;
				int minDist = 0;
				for(int i = a; i < b; i++){
					minDist += Math.abs(points[i + 1][0] - points[i][0]) + Math.abs(points[i + 1][1] - points[i][1]);
				}
				for(int v = a + 1; v < b; v++){
					int dist = 0;
					for(int i = a; i < b; i++){
						if(i == v)
							continue;
						if(i == v - 1){
							dist += Math.abs(points[i + 2][0] - points[i][0]) + Math.abs(points[i + 2][1] - points[i][1]);
						} else {
							dist += Math.abs(points[i + 1][0] - points[i][0]) + Math.abs(points[i + 1][1] - points[i][1]);
						}
					}
					minDist = Math.min(dist, minDist);
				}
				out.println(minDist);
			}
			
		}
		read.close();
		
		
		out.close();
		System.exit(0);
		
	}
	
}
