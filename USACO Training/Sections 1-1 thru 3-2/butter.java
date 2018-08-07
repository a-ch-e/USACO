/*
ID: ayc22032
LANG: JAVA
TASK: butter
*/

/*

Sweet Butter

Greg Galperin -- 2001
Farmer John has discovered the secret to making the sweetest butter in all of Wisconsin: sugar.
By placing a sugar cube out in the pastures, he knows the N (1 <= N <= 500) cows will lick it
and thus will produce super-sweet butter which can be marketed at better prices. Of course,
he spends the extra money on luxuries for the cows.

FJ is a sly farmer. Like Pavlov of old, he knows he can train the cows to go to a certain pasture
when they hear a bell. He intends to put the sugar there and then ring the bell in the middle of
the afternoon so that the evening's milking produces perfect milk.

FJ knows each cow spends her time in a given pasture (not necessarily alone). Given the pasture
location of the cows and a description of the paths the connect the pastures, find the pasture in
which to place the sugar cube so that the total distance walked by the cows when FJ rings the bell
is minimized. FJ knows the fields are connected well enough that some solution is always possible.

PROGRAM NAME: butter

INPUT FORMAT

Line 1: Three space-separated integers: N, the number of pastures: P (2 <= P <= 800), and the number 
of connecting paths: C (1 <= C <= 1,450). Cows are uniquely numbered 1..N. Pastures are uniquely
numbered 1..P.
Lines 2..N+1: Each line contains a single integer that is the pasture number in which a cow is
grazing. Cow i's pasture is listed on line i+1.
Lines N+2..N+C+1: Each line contains three space-separated integers that describe a single path that
connects a pair of pastures and its length. Paths may be traversed in either direction. No pair of
pastures is directly connected by more than one path. The first two integers are in the range 1..P;
the third integer is in the range (1..225).

SAMPLE INPUT (file butter.in)

3 4 5
2
3
4
1 2 1
1 3 5
2 3 7
2 4 3
3 4 5

INPUT DETAILS

This diagram shows the connections geometrically:
          P2  
 P1 @--1--@ C1
     \    |\
      \   | \
       5  7  3
        \ |   \
         \|    \ C3
       C2 @--5--@
          P3    P4
          
OUTPUT FORMAT

Line 1: A single integer that is the minimum distance the cows must walk to a pasture with a sugar cube.
SAMPLE OUTPUT (file butter.out)

8

OUTPUT DETAILS:

Putting the cube in pasture 4 means: cow 1 walks 3 units; cow 2 walks 5
units; cow 3 walks 0 units -- a total of 8.

 */

import java.io.*;
import java.util.*;

public class butter {

	public static void main(String[] args) throws IOException{
		
		long t = System.nanoTime();
		BufferedReader read = new BufferedReader(new FileReader("butter.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int numPastures = Integer.parseInt(st.nextToken());
		int edges = Integer.parseInt(st.nextToken());
//		int[][] graph = new int[numPastures][numPastures];
		int[] cows = new int[N];
		for(int i = 0; i < N; i++){
			cows[i] = Integer.parseInt(read.readLine()) - 1;
		}
//		for(int i = 0; i < numPastures; i++){
//			for(int j = 0; j < numPastures; j++){
//				if(i != j)
//					graph[i][j] = Integer.MAX_VALUE;
//			}
//		}
		DijkstraSB.initialize(numPastures);
		for(int i = 0; i < edges; i++){
			st = new StringTokenizer(read.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
//			graph[a - 1][b - 1] = d;
//			graph[b - 1][a - 1] = d;
			DijkstraSB.addEdge(a - 1, b - 1, d, true);
		}
//		DijkstraSB.finish();
		read.close();
		
//		for(int v = 0; v < graph.length; v++){
//			for(int i = 0; i < graph.length; i++){
//				if(graph[i][v] == Integer.MAX_VALUE)
//					continue;
//				for(int j = 0; j < graph.length; j++){
//					if(graph[v][j] == Integer.MAX_VALUE)
//						continue;
//					if(graph[i][v] + graph[v][j] < graph[i][j]){
//						graph[i][j] = graph[i][v] + graph[v][j];
//						graph[j][i] = graph[i][j];
//					}
//				}
//			}
//		}
		
		double[][] graph = DijkstraSB.allDistances();
		
//		for(int i = 0; i < graph.length; i++){
//			for(int j = 0; j < graph.length; j++){
//				System.out.print(graph[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		
		double min = Double.MAX_VALUE;
		for(int i = 0; i < numPastures; i++){
			int dist = 0;
			for(int j = 0; j < N; j++){
				dist += graph[i][cows[j]];
			}
			if(dist < min)
				min = dist;
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("butter.out")));
		out.println((int)min);
		System.out.println((int)min);
		out.close();
		System.out.println("Time: " + (System.nanoTime() - t) / 1e9);
		System.exit(0);
		
	}
	
}

class DijkstraSB{
	
//	static double[][] dists;
	static int[][] edges;
	static double[][] weights;
	static int[] inds;
	static ArrayList<List<Integer>> tempEdges;
	static ArrayList<List<Double>> tempWeights;
	static int N;
	private static int len;
	
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
		len = N;
		edges = new int[N][N];
		weights = new double[N][N];
		inds = new int[N];
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				edges[i][j] = -1;
			}
		}
		
		tempEdges = new ArrayList<List<Integer>>(n);
		tempWeights = new ArrayList<List<Double>>(n);
//		for(int i = 0; i < n; i++){
//			tempEdges.add(new ArrayList<Integer>(n));
//			tempWeights.add(new ArrayList<Double>(n));
//		}
		
	}
	
	public static void addEdge(int a, int b, double d, boolean unweighted){
		
//		edges[a][b] = d;
//		tempEdges.get(a).add(b);
//		tempWeights.get(a).add(d);
		edges[a][inds[a]] = b;
		weights[a][inds[a]++] = d;
		if(unweighted){
//			edges[b][a] = d;
//			Double[] edge2 = {(double)a, d};
//			tempEdges.get(b).add(edge2);
//			tempEdges.get(b).add(a);
//			tempWeights.get(b).add(d);
			edges[b][inds[b]] = a;
			weights[b][inds[b]++] = d;
		}
		
	}
	
//	public static void finish(){
//		
//		for(int i = 0; i < N; i++){
//			edges[i] = tempEdges.get(i).toArray(new Integer[tempEdges.get(i).size()]);
//			weights[i] = tempWeights.get(i).toArray(new Double[tempWeights.get(i).size()]);
//		}
//		
//	}
	
/*	public static double shortestDist(int a, int b){
		
		ArrayList<DijkstraSBNode> queue = new ArrayList<DijkstraSBNode>(edges.length);
		for(int i = 0; i < edges.length; i++){
			DijkstraSBNode node = new DijkstraSBNode(i, Double.MAX_VALUE);
			if(i == a)
				node.distance = 0;
			queue.add(node);
		}
		Collections.sort(queue);
		
		boolean seenLast = false;
		double ans = Double.MAX_VALUE;
		while(!queue.isEmpty() && !seenLast && queue.get(0).distance < Double.MAX_VALUE){
			DijkstraSBNode near = queue.remove(0);
			if(near.index == b){
				seenLast = true;
			}
			for(int i = 0; i < queue.size(); i++){
				if(edges[near.index][queue.get(i).index] < Double.MAX_VALUE && edges[near.index][queue.get(i).index] > 0 && (edges[near.index][queue.get(i).index] + near.distance < queue.get(i).distance))
					queue.get(i).distance = edges[near.index][queue.get(i).index] + near.distance;	
			}
			Collections.sort(queue);
//			System.out.println(queue);
			if(seenLast)
				ans = near.distance;
		}
		
		return ans;
		
	}*/
	
	public static double shortestDist(int a, int b){
		
		PriorityQueue<DijkstraSBNode> pq = new PriorityQueue<DijkstraSBNode>();
		double[] dists = new double[edges.length];
		for(int i = 0; i < dists.length; i++){
			dists[i] = Double.MAX_VALUE;
		}
		for(int i = 0; i < edges.length; i++){
			DijkstraSBNode node = new DijkstraSBNode(i, Double.MAX_VALUE);
			if(i == a)
				node.distance = 0;
			pq.add(node);
		}
		
//		for(int i = 0; i < edgeList.size(); i++){
//			System.out.print(i + ":  ");
//			for(Double[] d : edgeList.get(i)){
//				System.out.print(Arrays.toString(d) + ", ");
//			}
//			System.out.println();
//		}
		
		while(!pq.isEmpty()){
			DijkstraSBNode node = pq.remove();
//			System.out.println("Dequeuing " + node.index + ": " + Arrays.toString(dists));
			if(node.index == b)
				return node.distance;
			if(node.distance == Double.MAX_VALUE)
				break;
			if(dists[node.index] < node.distance)
				continue;
			dists[node.index] = node.distance;
			
			for(int i = 0; i < edges[node.index].length; i++){
				int neighbor = edges[node.index][i];
				if(dists[node.index] + weights[node.index][i] < dists[neighbor]){
					dists[neighbor] = dists[node.index] + weights[node.index][i];
//					System.out.println("Distance at " + neighbor + " changed to " + dists[neighbor]);
					pq.add(new DijkstraSBNode(neighbor, dists[neighbor]));
				}
			}
			
//			System.out.println(Arrays.toString(dists));
		}
		return Double.MAX_VALUE;
		
		
		
	}
	
	
	public static ArrayList<Integer> shortestPath(int a, int b){
		
		ArrayList<DijkstraSBNode> queue = new ArrayList<DijkstraSBNode>(N);
		for(int i = 0; i < N; i++){
			DijkstraSBNode node = new DijkstraSBNode(i, Double.MAX_VALUE);
			if(i == a)
				node.distance = 0;
			queue.add(node);
		}
		Collections.sort(queue);
		
		boolean seenLast = false;
		int[] prev = new int[N];
		for(int i = 0; i < prev.length; i++)
			prev[i] = -1;
		while(!queue.isEmpty() && !seenLast && queue.get(0).distance < Double.MAX_VALUE){
			DijkstraSBNode near = queue.remove(0);
			if(near.index == b){
				seenLast = true;
			}
			for(int i = 0; i < queue.size(); i++){
				if(edges[near.index][queue.get(i).index] < Double.MAX_VALUE && edges[near.index][queue.get(i).index] > 0 && (edges[near.index][queue.get(i).index] + near.distance < queue.get(i).distance)){
					queue.get(i).distance = edges[near.index][queue.get(i).index] + near.distance;
					prev[queue.get(i).index] = near.index;
				}
			}
			Collections.sort(queue);
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
	
public static double[][] allDistances(){
		
		double[][] ans = new double[N][N];
		for(int i = 0; i < ans.length; i++){
			ans[i] = allDistancesFromOneHeap(i);
//			System.out.println("------------------------------------------");
			for(int j = 0; j < i; j++){
				ans[i][j] = ans[j][i];
			}
		}
		return ans;
	}
	
/*	public static double[] allDistancesFromOne(int a){
		
		ArrayList<DijkstraSBNode> queue = new ArrayList<DijkstraSBNode>(edges.length);
		for(int i = 0; i < edges.length; i++){
			DijkstraSBNode node = new DijkstraSBNode(i, Double.MAX_VALUE);
			if(i == a)
				node.distance = 0;
			queue.add(node);
		}
		Collections.sort(queue);
		
		double[] ans = new double[edges.length];
		for(int i = 0; i < ans.length; i++)
			ans[i] = Double.MAX_VALUE;
		while(!queue.isEmpty() && queue.get(0).distance < Double.MAX_VALUE){
			DijkstraSBNode near = queue.remove(0);
			for(int i = 0; i < queue.size(); i++){
				if(edges[near.index][queue.get(i).index] < Double.MAX_VALUE && edges[near.index][queue.get(i).index] > 0 && (edges[near.index][queue.get(i).index] + near.distance < queue.get(i).distance))
					queue.get(i).distance = edges[near.index][queue.get(i).index] + near.distance;	
			}
			Collections.sort(queue);
			ans[near.index] = near.distance;
//			System.out.println(queue);
		}
		
		return ans;
		
	}*/
	
	public static double[] allDistancesFromOne(int a){
		
//		PriorityQueue<DijkstraSBNode> pq = new PriorityQueue<DijkstraSBNode>();
		DijkstraSBNode[] pq = new DijkstraSBNode[N];
		double[] dists = new double[edges.length];
		for(int i = 0; i < dists.length; i++){
			dists[i] = Double.MAX_VALUE;
		}
		for(int i = 0; i < edges.length; i++){
			DijkstraSBNode node = new DijkstraSBNode(i, Double.MAX_VALUE);
			if(i == a)
				node.distance = 0;
			pq[i] = node;
		}
		Arrays.sort(pq);
//		for(int i = 0; i < edgeList.size(); i++){
//			System.out.print(i + ":  ");
//			for(Double[] d : edgeList.get(i)){
//				System.out.print(Arrays.toString(d) + ", ");
//			}
//			System.out.println();
//		}
		
		int count = 0;
		
		while(pq.length > 0 && count <= edges.length - a){
			
			DijkstraSBNode node = pq[0];
			DijkstraSBNode[] temp = new DijkstraSBNode[pq.length - 1];
			System.arraycopy(pq, 1, temp, 0, pq.length - 1);
			pq = temp;
			
			if(dists[node.index] < node.distance)
				continue;
//			System.out.println("Dequeuing " + node.index + ": " + Arrays.toString(dists));
			if(node.index >= a){
				count++;
			}
			if(node.distance == Double.MAX_VALUE)
				break;
			
			dists[node.index] = node.distance;
			
			int changes = 0;
			temp = new DijkstraSBNode[pq.length + N];
			for(int i = 0; i < N && edges[node.index][i] >= 0; i++){
				int neighbor = edges[node.index][i];
//				System.out.println(node + " Neighbor: " + neighbor);
				if(dists[node.index] + weights[node.index][i] < dists[neighbor]){
					dists[neighbor] = dists[node.index] + weights[node.index][i];
					temp[pq.length + changes++] = new DijkstraSBNode(neighbor, dists[neighbor]);
//					System.out.println("Distance at " + neighbor + " changed to " + dists[neighbor]);
				}
			}
			System.arraycopy(pq, 0, temp, 0, pq.length);
			pq = new DijkstraSBNode[pq.length + changes];
			System.arraycopy(temp, 0, pq, 0, pq.length);
			Arrays.sort(pq);
			
//			System.out.println(Arrays.toString(dists));
		}
		return dists;
		
		
	}
	
	public static double[] allDistancesFromOneHeap(int a){
		
		double[] dists = new double[N];
		for(int i = 0; i < dists.length; i++){
			dists[i] = Double.MAX_VALUE;
		}		
		len = N;
		
		DijkstraSBNode[] pq = new DijkstraSBNode[N + 1];
		int[] inds = new int[N];
		inds[a] = 1;
		pq[1] = new DijkstraSBNode(a, 0);
		for(int i = 0; i < edges.length; i++){
			if(i != a){
				int ind = i + 1;
				if(i < a)
					ind++; 
				pq[ind] = new DijkstraSBNode(i, Double.MAX_VALUE);
				inds[i] = ind;
			}
		}
		
		for(int v = 0; v < N; v++){
			DijkstraSBNode node = dqMin(pq, inds);
			dists[node.index] = Math.min(dists[node.index], node.distance);
//			System.out.println("Length " + len + ": " + Arrays.toString(pq));
//			System.out.println(Arrays.toString(inds));

			for(int i = 0; i < N && edges[node.index][i] >= 0; i++){
				int neighbor = edges[node.index][i];
				if(inds[neighbor] < 1) continue;
//				System.out.println(node + " Neighbor: " + neighbor);
				if(dists[node.index] + weights[node.index][i] < pq[inds[neighbor]].distance){
//					System.out.println(pq[inds[neighbor]]);
					downHeap(pq, neighbor, dists[node.index] + weights[node.index][i], inds);
//					System.out.println(Arrays.toString(inds));
//					System.out.println("Distance at " + neighbor + " changed to " + pq[inds[neighbor]].distance);
				}
			}
//			System.out.println("Length " + len + ": " + Arrays.toString(pq));
		}
		
//		System.out.println(Arrays.toString(dists));
		
		return dists;
		
	}
	
	private static void downHeap(DijkstraSBNode[] pq, int i, double d, int[] inds){
		
		int ind = inds[i];
		pq[inds[i]].distance = d;
		
		while(ind / 2 > 0 && d < pq[ind / 2].distance){
			int b = ind / 2;
			int i_a = pq[ind].index;
			double d_a = pq[ind].distance;
			pq[ind].index = pq[b].index;
			pq[ind].distance = pq[b].distance;
			pq[b].index = i_a;
			pq[b].distance = d_a;
			
			inds[pq[b].index] = b;
			inds[pq[ind].index] = ind;
			
			ind = b;
		}
		
	}
	
	private static void upHeap(DijkstraSBNode[] pq, int i, int[] inds){
		
		int ind = i;
		
		while(ind * 2 <= len){
			int b = ind * 2;
			if(b + 1 <= len && pq[b + 1].distance < pq[b].distance)
				b++;
			if(pq[b].distance > pq[ind].distance)
				break;
			int i_a = pq[ind].index;
			double d_a = pq[ind].distance;
			pq[ind].index = pq[b].index;
			pq[ind].distance = pq[b].distance;
			pq[b].index = i_a;
			pq[b].distance = d_a;
			
			inds[pq[b].index] = b;
			inds[pq[ind].index] = ind;
			
			ind = b;
		}
		
	}
	
	private static DijkstraSBNode dqMin(DijkstraSBNode[] pq, int[] inds){
		
		DijkstraSBNode min = pq[1];
		pq[1] = pq[len--];
		upHeap(pq, 1, inds);
		inds[min.index] = -1;
		return min;
		
	}

}

class DijkstraSBNode implements Comparable<DijkstraSBNode>{
	
	int index;
	double distance;
	
	public DijkstraSBNode(int i, double d){
		index = i;
		distance = d;
	}
	
	public int compareTo(DijkstraSBNode other){
		
		if(distance == other.distance)
			return index - other.index;
		return (int)(distance - other.distance);
		
	}
	
	public String toString(){
		
		return "Ind " + index + ", dist " + distance;
	}
	
}

/*
0.0 109.0 75.0 45.0 220.0 21.0 239.0 116.0 20.0 83.0 118.0 72.0 32.0 158.0 38.0 74.0 47.0 54.0 90.0 142.0 94.0 119.0 56.0 52.0 18.0 172.0 167.0 178.0 118.0 101.0 43.0 
109.0 0.0 109.0 64.0 267.0 116.0 258.0 145.0 126.0 109.0 200.0 135.0 114.0 205.0 133.0 130.0 66.0 83.0 116.0 85.0 189.0 10.0 90.0 85.0 91.0 209.0 186.0 260.0 109.0 120.0 90.0 
75.0 109.0 0.0 120.0 255.0 96.0 197.0 132.0 55.0 139.0 129.0 107.0 43.0 198.0 113.0 135.0 122.0 110.0 146.0 67.0 169.0 119.0 19.0 127.0 93.0 207.0 242.0 189.0 43.0 176.0 95.0 
45.0 64.0 120.0 0.0 265.0 52.0 194.0 136.0 65.0 45.0 163.0 107.0 77.0 203.0 69.0 72.0 2.0 74.0 52.0 149.0 125.0 74.0 101.0 21.0 27.0 207.0 122.0 222.0 163.0 56.0 88.0 
220.0 267.0 255.0 265.0 0.0 241.0 371.0 306.0 224.0 253.0 189.0 222.0 212.0 62.0 258.0 217.0 267.0 274.0 246.0 241.0 237.0 257.0 236.0 272.0 238.0 321.0 182.0 129.0 217.0 215.0 177.0 
21.0 116.0 96.0 52.0 241.0 0.0 246.0 95.0 41.0 62.0 121.0 86.0 53.0 179.0 17.0 95.0 54.0 33.0 69.0 163.0 73.0 106.0 77.0 31.0 39.0 157.0 174.0 181.0 139.0 108.0 64.0 
239.0 258.0 197.0 194.0 371.0 246.0 0.0 243.0 252.0 211.0 293.0 301.0 240.0 309.0 263.0 262.0 192.0 240.0 204.0 178.0 319.0 268.0 216.0 215.0 221.0 401.0 312.0 353.0 154.0 246.0 282.0 
116.0 145.0 132.0 136.0 306.0 95.0 243.0 0.0 136.0 91.0 216.0 181.0 148.0 244.0 112.0 190.0 138.0 62.0 98.0 113.0 168.0 135.0 129.0 126.0 134.0 187.0 169.0 222.0 89.0 192.0 159.0 
20.0 126.0 55.0 65.0 224.0 41.0 252.0 136.0 0.0 103.0 98.0 52.0 12.0 178.0 58.0 94.0 67.0 74.0 110.0 122.0 114.0 136.0 36.0 72.0 38.0 152.0 187.0 158.0 98.0 121.0 63.0 
83.0 109.0 139.0 45.0 253.0 62.0 211.0 91.0 103.0 0.0 183.0 148.0 115.0 191.0 79.0 117.0 47.0 29.0 7.0 194.0 135.0 102.0 135.0 66.0 72.0 219.0 167.0 243.0 180.0 101.0 119.0 
118.0 200.0 129.0 163.0 189.0 121.0 293.0 216.0 98.0 183.0 0.0 150.0 86.0 127.0 104.0 178.0 165.0 154.0 190.0 163.0 48.0 210.0 110.0 152.0 136.0 132.0 209.0 60.0 139.0 143.0 138.0 
72.0 135.0 107.0 107.0 222.0 86.0 301.0 181.0 52.0 148.0 150.0 0.0 64.0 160.0 69.0 85.0 109.0 119.0 155.0 174.0 125.0 125.0 88.0 117.0 90.0 100.0 229.0 210.0 150.0 163.0 45.0 
32.0 114.0 43.0 77.0 212.0 53.0 240.0 148.0 12.0 115.0 86.0 64.0 0.0 167.0 70.0 92.0 79.0 86.0 122.0 110.0 126.0 124.0 24.0 84.0 50.0 164.0 199.0 146.0 86.0 133.0 52.0 
158.0 205.0 198.0 203.0 62.0 179.0 309.0 244.0 178.0 191.0 127.0 160.0 167.0 0.0 196.0 155.0 205.0 212.0 184.0 179.0 175.0 195.0 191.0 210.0 176.0 259.0 120.0 67.0 155.0 153.0 115.0 
38.0 133.0 113.0 69.0 258.0 17.0 263.0 112.0 58.0 79.0 104.0 69.0 70.0 196.0 0.0 112.0 71.0 50.0 86.0 180.0 56.0 123.0 94.0 48.0 56.0 140.0 191.0 164.0 156.0 125.0 81.0 
74.0 130.0 135.0 72.0 217.0 95.0 262.0 190.0 94.0 117.0 178.0 85.0 92.0 155.0 112.0 0.0 70.0 128.0 124.0 202.0 168.0 120.0 116.0 93.0 92.0 185.0 188.0 222.0 178.0 122.0 40.0 
47.0 66.0 122.0 2.0 267.0 54.0 192.0 138.0 67.0 47.0 165.0 109.0 79.0 205.0 71.0 70.0 0.0 76.0 54.0 151.0 127.0 76.0 103.0 23.0 29.0 209.0 120.0 224.0 165.0 54.0 90.0 
54.0 83.0 110.0 74.0 274.0 33.0 240.0 62.0 74.0 29.0 154.0 119.0 86.0 212.0 50.0 128.0 76.0 0.0 36.0 168.0 106.0 73.0 110.0 64.0 72.0 190.0 196.0 214.0 151.0 130.0 97.0 
90.0 116.0 146.0 52.0 246.0 69.0 204.0 98.0 110.0 7.0 190.0 155.0 122.0 184.0 86.0 124.0 54.0 36.0 0.0 201.0 142.0 109.0 128.0 73.0 79.0 226.0 174.0 250.0 187.0 108.0 126.0 
142.0 85.0 67.0 149.0 241.0 163.0 178.0 113.0 122.0 194.0 163.0 174.0 110.0 179.0 180.0 202.0 151.0 168.0 201.0 0.0 211.0 95.0 86.0 139.0 160.0 274.0 271.0 223.0 24.0 205.0 162.0 
94.0 189.0 169.0 125.0 237.0 73.0 319.0 168.0 114.0 135.0 48.0 125.0 126.0 175.0 56.0 168.0 127.0 106.0 142.0 211.0 0.0 179.0 150.0 104.0 112.0 84.0 161.0 108.0 187.0 95.0 137.0 
119.0 10.0 119.0 74.0 257.0 106.0 268.0 135.0 136.0 102.0 210.0 125.0 124.0 195.0 123.0 120.0 76.0 73.0 109.0 95.0 179.0 0.0 100.0 95.0 101.0 199.0 196.0 262.0 119.0 130.0 80.0 
56.0 90.0 19.0 101.0 236.0 77.0 216.0 129.0 36.0 135.0 110.0 88.0 24.0 191.0 94.0 116.0 103.0 110.0 128.0 86.0 150.0 100.0 0.0 108.0 74.0 188.0 223.0 170.0 62.0 157.0 76.0 
52.0 85.0 127.0 21.0 272.0 31.0 215.0 126.0 72.0 66.0 152.0 117.0 84.0 210.0 48.0 93.0 23.0 64.0 73.0 139.0 104.0 95.0 108.0 0.0 48.0 188.0 143.0 201.0 163.0 77.0 95.0 
18.0 91.0 93.0 27.0 238.0 39.0 221.0 134.0 38.0 72.0 136.0 90.0 50.0 176.0 56.0 92.0 29.0 72.0 79.0 160.0 112.0 101.0 74.0 48.0 0.0 190.0 149.0 196.0 136.0 83.0 61.0 
172.0 209.0 207.0 207.0 321.0 157.0 401.0 187.0 152.0 219.0 132.0 100.0 164.0 259.0 140.0 185.0 209.0 190.0 226.0 274.0 84.0 199.0 188.0 188.0 190.0 0.0 245.0 192.0 250.0 179.0 145.0 
167.0 186.0 242.0 122.0 182.0 174.0 312.0 169.0 187.0 167.0 209.0 229.0 199.0 120.0 191.0 188.0 120.0 196.0 174.0 271.0 161.0 196.0 223.0 143.0 149.0 245.0 0.0 151.0 258.0 66.0 210.0 
178.0 260.0 189.0 222.0 129.0 181.0 353.0 222.0 158.0 243.0 60.0 210.0 146.0 67.0 164.0 222.0 224.0 214.0 250.0 223.0 108.0 262.0 170.0 201.0 196.0 192.0 151.0 0.0 199.0 203.0 182.0 
118.0 109.0 43.0 163.0 217.0 139.0 154.0 89.0 98.0 180.0 139.0 150.0 86.0 155.0 156.0 178.0 165.0 151.0 187.0 24.0 187.0 119.0 62.0 163.0 136.0 250.0 258.0 199.0 0.0 219.0 138.0 
101.0 120.0 176.0 56.0 215.0 108.0 246.0 192.0 121.0 101.0 143.0 163.0 133.0 153.0 125.0 122.0 54.0 130.0 108.0 205.0 95.0 130.0 157.0 77.0 83.0 179.0 66.0 203.0 219.0 0.0 144.0 
43.0 90.0 95.0 88.0 177.0 64.0 282.0 159.0 63.0 119.0 138.0 45.0 52.0 115.0 81.0 40.0 90.0 97.0 126.0 162.0 137.0 80.0 76.0 95.0 61.0 145.0 210.0 182.0 138.0 144.0 0.0 
*/