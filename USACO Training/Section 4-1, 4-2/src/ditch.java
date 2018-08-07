/*
ID: ayc22032
LANG: JAVA
TASK: ditch
*/

/*
Drainage Ditches
Hal Burch
Every time it rains on Farmer John's fields, a pond forms over Bessie's favorite clover patch. This
means that the clover is covered by water for awhile and takes quite a long time to regrow. Thus,
Farmer John has built a set of drainage ditches so that Bessie's clover patch is never covered in
water. Instead, the water is drained to a nearby stream. Being an ace engineer, Farmer John has
also installed regulators at the beginning of each ditch, so he can control at what rate water
flows into that ditch.

Farmer John knows not only how many gallons of water each ditch can transport per minute but also
the exact layout of the ditches, which feed out of the pond and into each other and stream in a
potentially complex network. Note however, that there can be more than one ditch between two
intersections.

Given all this information, determine the maximum rate at which water can be transported out of
the pond and into the stream. For any given ditch, water flows in only one direction, but there
might be a way that water can flow in a circle.

PROGRAM NAME: ditch

INPUT FORMAT

Line 1:	Two space-separated integers, N (0 <= N <= 200) and M (2 <= M <= 200). N is the number of
ditches that Farmer John has dug. M is the number of intersections points for those ditches.
Intersection 1 is the pond. Intersection point M is the stream.
Line 2..N+1:	Each of N lines contains three integers, Si, Ei, and Ci. Si and
Ei (1 <= Si, Ei <= M) designate the intersections between which this ditch flows. Water will flow
through this ditch from Si to Ei. Ci (0 <= Ci <= 10,000,000) is the maximum rate at which water
will flow through the ditch.

SAMPLE INPUT (file ditch.in)

5 4
1 2 40
1 4 20
2 4 20
2 3 30
3 4 10

OUTPUT FORMAT

One line with a single integer, the maximum rate at which water may emptied from the pond.

SAMPLE OUTPUT (file ditch.out)

50
 */

import java.io.*;
import java.util.*;

public class ditch {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("ditch.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[][] matr = new int[M][M];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(read.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			matr[a][b] += Integer.parseInt(st.nextToken());
		}
		read.close();
		
		int[][] edges = new int[M][];
		for(int i = 0; i < M; i++){
			int count = 0;
			for(int j = 0; j < M; j++){
				if(matr[i][j] > 0 || matr[j][i] > 0)
					count++;
			}
			edges[i] = new int[count];
			for(int j = 0; j < M; j++){
				if(matr[i][j] > 0 || matr[j][i] > 0){
					count--;
					edges[i][count] = j;
				}
			}
		}
		
		int total = 0;
				
		while(true){
			//System.out.println(total);
			int[] prevNodes = new int[M];
			int[] flows = new int[M];
			boolean[] visited = new boolean[M];
			for(int i = 0; i < M; i++){
				prevNodes[i] = -1;
			}
			flows[0] = Integer.MAX_VALUE;
			
			boolean done = false;
			while(true){
				int maxFlow = 0;
				int maxLoc = -1;
				for(int i = 0; i < M; i++){
					if(flows[i] > maxFlow && !visited[i]){
						maxFlow = flows[i];
						maxLoc = i;
					}
				}
				//System.out.println(maxLoc);
				//System.out.println(Arrays.toString(flows));
				if(maxLoc == -1){
					done = true;
					break;
				}
				if(maxLoc == M - 1)
					break;
				visited[maxLoc] = true;
				for(int i :edges[maxLoc]){
					if(flows[i] < Math.min(maxFlow, matr[maxLoc][i])){
						prevNodes[i] = maxLoc;
						flows[i] = Math.min(maxFlow, matr[maxLoc][i]);
					}
				}
			}
			if(done)
				break;
			int pathCap = flows[M - 1];
			total += pathCap;
			
			int curr = M - 1;
			while(curr != 0){
				int next = prevNodes[curr];
				matr[next][curr] -= pathCap;
				matr[curr][next] += pathCap;
				curr = next;
			}
			
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("ditch.out"));
		out.println(total);
		System.out.println(total);
		out.close();
		System.exit(0);
		
	}
	
}
