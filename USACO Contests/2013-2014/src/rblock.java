/*
Allen Cheng
USACO Gold Division
February Contest 2014 Problem 2: Roadblock
 */

/*
Problem 1: Roadblock [Brian Dean]

Every morning, FJ wakes up and walks across the farm from his house to the
barn.  The farm is a collection of N fields (1 <= N <= 250) connected by M
bidirectional pathways (1 <= M <= 25,000), each with an associated length. 
FJ's house is in field 1, and the barn is in field N.  No pair of fields is
joined by multiple redundant pathways, and it is possible to travel between
any pair of fields in the farm by walking along an appropriate sequence of
pathways.  When traveling from one field to another, FJ always selects a
route consisting of a sequence of pathways having minimum total length.

Farmer John's cows, up to no good as always, have decided to interfere with
his morning routine.  They plan to build a pile of hay bales on exactly one
of the M pathways on the farm, doubling its length.  The cows wish to
select a pathway to block so that they maximize the increase in FJ's
distance from the house to the barn.  Please help the cows determine
by how much they can lengthen FJ's route.

PROBLEM NAME: rblock

INPUT FORMAT:

* Line 1: Two space-separated integers, N and M.

* Lines 2..1+M: Line j+1 describes the jth bidirectional pathway in
        terms of three space-separated integers: A_j B_j L_j, where
        A_j and B_j are indices in the range 1..N indicating the
        fields joined by the pathway, and L_j is the length of the
        pathway (in the range 1...1,000,000).

SAMPLE INPUT (file rblock.in):

5 7
2 1 5
1 3 1
3 2 8
3 5 7
3 4 3
2 4 7
4 5 2

INPUT DETAILS:

There are 5 fields and 7 pathways.  Currently, the shortest path from the
house (field 1) to the barn (field 5) is 1-3-4-5 of total length 1+3+2=6.

OUTPUT FORMAT:

* Line 1: The maximum possible increase in the total length of FJ's
        shortest route made possible by doubling the length of a
        single pathway.

SAMPLE OUTPUT (file rblock.out):

2

OUTPUT DETAILS:

If the cows double the length of the pathway from field 3 to field 4
(increasing its length from 3 to 6), then FJ's shortest route is now 1-3-5,
of total length 1+7=8, larger by two than the previous shortest route length.
 */

import java.io.*;
import java.util.*;

public class rblock {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("rblock.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		RBDij.initialize(N);
		for(int i = 0; i < M; i++){
			st = new StringTokenizer(read.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken());
			RBDij.addEdge(a, b, d, true);
		}
		RBDij.finish();
		read.close();
		
		ArrayList<Integer> tempPath = RBDij.shortestPath(0, N - 1);
		Integer[] path = tempPath.toArray(new Integer[tempPath.size()]);
//		System.out.println(Arrays.toString(path));
		
		int original = RBDij.shortestDist(0, N - 1);
		int max = 0;
		
		for(int i = 0; i < path.length - 1; i++){
			RBDij.doubleEdge(path[i], path[i + 1]);
			max = Math.max(max, RBDij.shortestDist(0, N - 1) - original);
			RBDij.halveEdge(path[i], path[i + 1]);
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("rblock.out")));
		out.println(max);
		System.out.println(max);
		out.close();
		System.exit(0);
		
	}
	
}

class RBDij{
	
//	static double[][] dists;
	static Integer[][] edges;
	static Integer[][] weights;
	static ArrayList<List<Integer>> tempEdges;
	static ArrayList<List<Integer>> tempWeights;
	static int N;
	
	public static void initialize(int n){
		
//		edges = new double[n][n];
//		for(int i = 0; i < n; i++){
//			for(int j = 0; j < n; j++){
//				if(j != i){
//					edges[i][j] = Double.MAX_VALUE;
//				}
//			}
//		}
		N = n;
		tempEdges = new ArrayList<List<Integer>>(n);
		tempWeights = new ArrayList<List<Integer>>(n);
		for(int i = 0; i < n; i++){
			tempEdges.add(new ArrayList<Integer>(n));
			tempWeights.add(new ArrayList<Integer>(n));
		}
		
	}
	
	public static void addEdge(int a, int b, int d, boolean undirected){
		
//		edges[a][b] = d;
		tempEdges.get(a).add(b);
		tempWeights.get(a).add(d);
		if(undirected){
//			edges[b][a] = d;
//			Double[] edge2 = {(double)a, d};
//			tempEdges.get(b).add(edge2);
			tempEdges.get(b).add(a);
			tempWeights.get(b).add(d);
		}
		
	}
	
	public static void finish(){
		
		edges = new Integer[N][];
		weights = new Integer[N][];
		for(int i = 0; i < N; i++){
			edges[i] = tempEdges.get(i).toArray(new Integer[tempEdges.get(i).size()]);
			weights[i] = tempWeights.get(i).toArray(new Integer[tempWeights.get(i).size()]);
		}
		
	}
	
	public static void doubleEdge(int a, int b){
		
		for(int i = 0; i < edges[a].length; i++){
			if(edges[a][i] == b){
				weights[a][i] *= 2;
				break;
			}
		}
		for(int i = 0; i < edges[b].length; i++){
			if(edges[b][i] == a){
				weights[b][i] *= 2;
				break;
			}
		}
		
	}
	
	public static void halveEdge(int a, int b){
		
		for(int i = 0; i < edges[a].length; i++){
			if(edges[a][i] == b){
				weights[a][i] /= 2;
				break;
			}
		}
		for(int i = 0; i < edges[b].length; i++){
			if(edges[b][i] == a){
				weights[b][i] /= 2;
				break;
			}
		}
		
	}
	
	public static int shortestDist(int a, int b){
		
		PriorityQueue<RBDijNode> pq = new PriorityQueue<RBDijNode>();
		int[] dists = new int[N];
		for(int i = 0; i < dists.length; i++){
			dists[i] = Integer.MAX_VALUE;
		}
		for(int i = 0; i < N; i++){
			RBDijNode node = new RBDijNode(i, Integer.MAX_VALUE);
			if(i == a)
				node.distance = 0;
			pq.add(node);
		}
		
//		for(int i = 0; i < tempEdges.size(); i++){
//			System.out.print(i + ":  ");
//			for(Double[] d : tempEdges.get(i)){
//				System.out.print(Arrays.toString(d) + ", ");
//			}
//			System.out.println();
//		}
		
		while(!pq.isEmpty()){
			RBDijNode node = pq.remove();
//			System.out.println("Dequeuing " + node.index + ": " + Arrays.toString(dists));
			if(node.index == b)
				return node.distance;
			if(node.distance == Integer.MAX_VALUE)
				break;
			if(dists[node.index] < node.distance)
				continue;
			dists[node.index] = node.distance;
			
			for(int i = 0; i < edges[node.index].length; i++){
				int neighbor = edges[node.index][i];
				if(dists[node.index] + weights[node.index][i] < dists[neighbor]){
					dists[neighbor] = dists[node.index] + weights[node.index][i];
//					System.out.println("Distance at " + neighbor + " changed to " + dists[neighbor]);
					pq.add(new RBDijNode(neighbor, dists[neighbor]));
				}
			}
			
//			System.out.println(Arrays.toString(dists));
		}
		return Integer.MAX_VALUE;
		
	}
	
	
public static ArrayList<Integer> shortestPath(int a, int b){
		
		PriorityQueue<RBDijNode> pq = new PriorityQueue<RBDijNode>();
		int[] dists = new int[N];
		for(int i = 0; i < dists.length; i++){
			dists[i] = Integer.MAX_VALUE;
		}
		for(int i = 0; i < N; i++){
			RBDijNode node = new RBDijNode(i, Integer.MAX_VALUE);
			if(i == a)
				node.distance = 0;
			pq.add(node);
		}
		
		int[] prev = new int[N];
		for(int i = 0; i < prev.length; i++)
			prev[i] = -1;
		
		while(!pq.isEmpty()){
			RBDijNode node = pq.remove();
			if(node.index == b)
				break;
			if(node.distance == Integer.MAX_VALUE)
				break;
			if(dists[node.index] < node.distance)
				continue;
			dists[node.index] = node.distance;
			
			for(int i = 0; i < edges[node.index].length; i++){
				int neighbor = edges[node.index][i];
				if(dists[node.index] + weights[node.index][i] < dists[neighbor]){
					dists[neighbor] = dists[node.index] + weights[node.index][i];
//					System.out.println("Distance at " + neighbor + " changed to " + dists[neighbor]);
					prev[neighbor] = node.index;
					pq.add(new RBDijNode(neighbor, dists[neighbor]));
				}
			}
//			System.out.println(queue);
		}
		if(prev[b] < 0)
			return null;
		ArrayList<Integer> path = new ArrayList<Integer>();
		path.add(b);
		while(path.get(0) != a){
			path.add(0, prev[path.get(0)]);
		}
		return path;
		
	}

}
class RBDijNode implements Comparable<RBDijNode>{
	
	int index;
	int distance;
	
	public RBDijNode(int i, int d){
		index = i;
		distance = d;
	}
	
	public int compareTo(RBDijNode other){
		
		if(distance == other.distance)
			return index - other.index;
		return (int)(distance - other.distance);
		
	}
	
	public String toString(){
		
		return "Ind " + index + ", dist " + distance;
	}
	
}