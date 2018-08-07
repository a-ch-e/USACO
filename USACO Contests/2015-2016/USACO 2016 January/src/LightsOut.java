/*
Allen Cheng, ID ayc22032
USACO, January 2016
Gold 3: Lights Out
*/

/*
Farmer John has installed a fancy new milking machine in his barn, but it draws so much power that
it occasionally causes the power to go out! This happens so often that Bessie has memorized a map
of the barn, making it easier for her to find the exit of the barn in the dark. She is curious
though about the impact of power loss on her ability to exit the barn quickly. For example, she
wonders how much farther she might need to walk find the exit in the dark.
The barn is described by a simple (non self-intersecting) polygon with integer vertices (x1,y1)...
(xn,yn) listed in clockwise order. Its edges alternate between horizontal (parallel to the x-axis)
and vertical (parallel to the y-axis); the first edge can be of either type. The exit is located at
(x1,y1). Bessie starts inside the barn located at some vertex (xi,yi) for i>1. She can walk only
around the perimeter of the barn, either clockwise or counterclockwise, Her goal is to travel a
minimum distance to reach the exit. This is relatively easy to do with the lights on, of course,
since she will travel either clockwise or counterclockwise from her current location to the exit
-- whichever direction is shorter.

One day, the lights go out, causing Bessie to panic and forget which vertex she is standing at.
Fortunately, she still remembers the exact map of the barn, so she can possibly figure out her
position by walking around and using her sense of touch. Whenever she is standing at a vertex
(including at her initial vertex), she can feel the exact interior angle at that vertex, and
she can tell if that vertex is the exit. When she walks along an edge of the barn, she can
determine the exact length of the edge after walking along the entire edge. Bessie decides on
the following strategy: she will move clockwise around the perimeter of the barn until she has
felt enough angles and edges to deduce the vertex at which she is currently located. At that
point, she can easily figure out how to get to the exit by traveling a minimum amount of
remaining distance, either by continuing to move clockwise or by switching direction and
moving counter-clockwise.

Please help Bessie determine the largest amount by which her travel distance will increase in the
worst case (over all possibilities for her starting vertex) for travel in the dark versus in a lit
barn.

INPUT FORMAT (file lightsout.in):

The first line of the input contains N (4<=N<=200). Each of the next N lines contains two integers,
describing the points (xi,yi) in clockwise order around the barn. These integers are in the range
-100,000...100,000.

OUTPUT FORMAT (file lightsout.out):

Output the largest amount that Bessie's travel distance will increase in the worst case starting
position using the strategy in the problem statement.

SAMPLE INPUT:

4
0 0
0 10
1 10
1 0

SAMPLE OUTPUT:

2
In this example, Bessie can feel that she is initially standing at a 90-degree angle, but she cannot
tell if she is initially standing at vertex 2, 3, or 4. After taking a step along one edge in the
clockwise direction, Bessie either reaches the exit or can uniquely determine her location based on
the length of this edge. The distances she obtains are:

If starting at vertex 2: she travels 12 units in the dark (1 unit to reach vertex 3, then 11 units
to continue to the exit). She only needs to travel 10 units in a lit barn. This is an extra
distance of 2 for this vertex.

If starting at vertex 3: she travels 11 units in both cases.

If starting at vertex 4: she travels 1 unit in both cases.

The worst-case difference over all starting points is therefore 12 - 10 = 2. That is, Bessie can
guarantee that using her strategy, no matter where she starts, she will travel at most 2 extra
units of distance farther in the dark than in the light.

Problem credits: Brian Dean
 */

import java.io.*;
import java.util.*;

public class LightsOut {

	public static void main(String[] args) throws IOException {

		BufferedReader read = new BufferedReader(new FileReader("lightsout.in"));
		int N = Integer.parseInt(read.readLine());
		int[] xs = new int[N];
		int[] ys = new int[N];
		for(int i = 0; i < N; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			xs[i] = Integer.parseInt(st.nextToken());
			ys[i] = Integer.parseInt(st.nextToken());
		}
		read.close();
		
		boolean[] convex = new boolean[N];
		int[] lengths = new int[N];
		int totalLength = 0;
		for(int i = 0; i < N; i++){
			int prev = (i + N - 1) % N;
			int next = (i + 1) % N;
			lengths[i] = Math.abs(xs[next] - xs[i]) + Math.abs(ys[next] - ys[i]);
			totalLength += lengths[i];
			if(ys[next] > ys[i] && xs[i] < xs[prev])
				convex[i] = true;
			else if(xs[next] < xs[i] && ys[i] < ys[prev])
				convex[i] = true;
			else if(ys[next] < ys[i] && xs[i] > xs[prev])
				convex[i] = true;
			else if(xs[next] > xs[i] && ys[i] > ys[prev])
				convex[i] = true;
		}
//		System.out.println(Arrays.toString(convex));
//		System.out.println(Arrays.toString(lengths));
		
		int[] minDists = new int[N];
		for(int i = 0; i < N; i++){
			for(int j = 0; j < i; j++){
				minDists[i] += lengths[j];
			}
			minDists[i] = Math.min(minDists[i], totalLength - minDists[i]);
		}
//		System.out.println(Arrays.toString(minDists));
		
		int[] worstCaseDist = new int[N];
		for(int i = 1; i < N; i++){
			for(int j = 1; j < N; j++){
				if(j == i)
					continue;
				int cost1 = 0;
				int cost2 = 0;
				if(convex[i] != convex[j])
					continue;
				int k;
				for(k = 1; k < N; k++){
					cost1 += lengths[(i + k - 1) % N];
					cost2 += lengths[(j + k - 1) % N];
					if(convex[(i + k) % N] != convex[(j + k) % N] || cost1 != cost2)
						break;
					if((i + k) % N == 0 || (j + k) % N == 0){
						break;
					}
				}
//				System.out.format("(%d, %d): (%d, %d) \n", i, j, cost1, cost2);
				worstCaseDist[i] = Math.max(minDists[(i + k) % N] + cost1, worstCaseDist[i]);
				worstCaseDist[j] = Math.max(minDists[(j + k) % N] + cost2, worstCaseDist[j]);
			}
		}
		
		int worst = worstCaseDist[0];
		for(int i = 1; i < N; i++){
			worstCaseDist[i] -= minDists[i];
//			System.out.println("" + i + ": " + worstCaseDist[i]);
			worst = Math.max(worst, worstCaseDist[i]);
		}

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lightsout.out")));
		out.println(worst);
		
		System.out.println(worst);
		out.close();
		System.exit(0);

	}

}
