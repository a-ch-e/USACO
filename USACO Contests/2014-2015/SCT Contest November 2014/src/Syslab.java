//lol @ black friday

import java.util.*;
import java.io.*;

public class Syslab {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		Dijkstra.initialize(N);
		for(int i = 0; i < C; i++){
			st = new StringTokenizer(read.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			Dijkstra.addEdge(a, b, 1, true);
		}
		read.close();
		
		for(int i = 1; i < N - 1; i++){
			double pathlen = Dijkstra.shortestDistNoV(i, 0, N - 1);
			System.out.println(pathlen);
			if(pathlen > d){
				System.out.println("NO");
				System.exit(0);
			}
		}
		System.out.println("YES");
		System.exit(0);
		
	}
	
}


class Dijkstra{
	
//	static double[][] dists;
	static int[][] edges;
	static double[][] weights;
	static int[] inds;
	static int N;
	private static int len;
	
	public static void initialize(int n){
		
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
	
public static double shortestDistNoV(int v, int a, int b){
		
		PriorityQueue<DijkstraNode> pq = new PriorityQueue<DijkstraNode>();
		double[] dists = new double[edges.length];
		for(int i = 0; i < dists.length; i++){
			dists[i] = Double.MAX_VALUE;
		}
		for(int i = 0; i < edges.length; i++){
			if(i == v)
				continue;
			DijkstraNode node = new DijkstraNode(i, Double.MAX_VALUE);
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
			DijkstraNode node = pq.remove();
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
				if(neighbor == v)
					continue;
				if(neighbor == -1)
					break;
//				System.out.println("Index " + node.index + " i " + i + " neighbor " + neighbor);
				if(dists[node.index] + weights[node.index][i] < dists[neighbor]){
					dists[neighbor] = dists[node.index] + weights[node.index][i];
//					System.out.println("Distance at " + neighbor + " changed to " + dists[neighbor]);
					pq.add(new DijkstraNode(neighbor, dists[neighbor]));
				}
			}
			
//			System.out.println(Arrays.toString(dists));
		}
		return Double.MAX_VALUE;
		
		
		
	}

	
	public static double shortestDist(int a, int b){
		
		PriorityQueue<DijkstraNode> pq = new PriorityQueue<DijkstraNode>();
		double[] dists = new double[edges.length];
		for(int i = 0; i < dists.length; i++){
			dists[i] = Double.MAX_VALUE;
		}
		for(int i = 0; i < edges.length; i++){
			DijkstraNode node = new DijkstraNode(i, Double.MAX_VALUE);
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
			DijkstraNode node = pq.remove();
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
					pq.add(new DijkstraNode(neighbor, dists[neighbor]));
				}
			}
			
//			System.out.println(Arrays.toString(dists));
		}
		return Double.MAX_VALUE;
		
		
		
	}

}

class DijkstraNode implements Comparable<DijkstraNode>{
	
	int index;
	double distance;
	
	public DijkstraNode(int i, double d){
		index = i;
		distance = d;
	}
	
	public int compareTo(DijkstraNode other){
		
		if(distance == other.distance)
			return index - other.index;
		return (int)(distance - other.distance);
		
	}
	
	public String toString(){
		
		return "Ind " + index + ", dist " + distance;
	}
	
}