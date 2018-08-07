/*
ID: ayc22032
LANG: JAVA
TASK: stall4
*/

/*
The Perfect Stall
Hal Burch
Farmer John completed his new barn just last week, complete with all the latest milking technology.
Unfortunately, due to engineering problems, all the stalls in the new barn are different. For the
first week, Farmer John randomly assigned cows to stalls, but it quickly became clear that any
given cow was only willing to produce milk in certain stalls. For the last week, Farmer John has
been collecting data on which cows are willing to produce milk in which stalls. A stall may be only
assigned to one cow, and, of course, a cow may be only assigned to one stall.

Given the preferences of the cows, compute the maximum number of milk-producing assignments of cows
to stalls that is possible.

PROGRAM NAME: stall4

INPUT FORMAT

Line 1:	One line with two integers, N (0 <= N <= 200) and M (0 <= M <= 200). N is the number of
cows that Farmer John has and M is the number of stalls in the new barn.
Line 2..N+1:	N lines, each corresponding to a single cow. The first integer (Si) on the line is
the number of stalls that the cow is willing to produce milk in (0 <= Si <= M). The subsequent Si
integers on that line are the stalls in which that cow is willing to produce milk. The stall
numbers will be integers in the range (1..M), and no stall will be listed twice for a given cow.

SAMPLE INPUT (file stall4.in)

5 5
2 2 5
3 2 3 4
2 1 5
3 1 2 5
1 2 

OUTPUT FORMAT

A single line with a single integer, the maximum number of milk-producing stall assignments that can be made.

SAMPLE OUTPUT (file stall4.out)

4
 */

import java.io.*;
import java.util.*;

public class stall4 {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("stall4.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int size = M + N + 2;
		int[][] matr = new int[size][size];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(read.readLine());
			int len = Integer.parseInt(st.nextToken());
			for(int j = 0; j < len; j++){
				int b = Integer.parseInt(st.nextToken());
				matr[i + 1][N + b] = 1;
			}
			matr[0][1 + i] = 1;
		}
		read.close();
		for(int i = 0; i < M; i++){
			matr[1 + N + i][size - 1] = 1;
		}
		
		int[][] edges = new int[size][];
		for(int i = 0; i < size; i++){
			int count = 0;
			for(int j = 0; j < size; j++){
				if(matr[i][j] > 0 || matr[j][i] > 0)
					count++;
			}
			edges[i] = new int[count];
			for(int j = 0; j < size; j++){
				if(matr[i][j] > 0 || matr[j][i] > 0){
					count--;
					edges[i][count] = j;
				}
			}
		}
		
		int total = 0;
				
		while(true){
			int[] prevNodes = new int[size];
			int[] flows = new int[size];
			boolean[] visited = new boolean[size];
			for(int i = 0; i < size; i++){
				prevNodes[i] = -1;
			}
			flows[0] = Integer.MAX_VALUE;
			
			boolean done = false;
			while(true){
				int maxFlow = 0;
				int maxLoc = -1;
				for(int i = 0; i < size; i++){
					if(flows[i] > maxFlow && !visited[i]){
						maxFlow = flows[i];
						maxLoc = i;
					}
				}
				if(maxLoc == -1){
					done = true;
					break;
				}
				if(maxLoc == size - 1)
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
			int pathCap = flows[size - 1];
			total += pathCap;
			
			int curr = size - 1;
			while(curr != 0){
				int next = prevNodes[curr];
				matr[next][curr] -= pathCap;
				matr[curr][next] += pathCap;
				curr = next;
			}
			
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("stall4.out"));
		out.println(total);
		System.out.println(total);
		out.close();
		System.exit(0);
		
	}
	
}
