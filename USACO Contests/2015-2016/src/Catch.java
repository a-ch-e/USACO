import java.io.*;
import java.util.*;

public class Catch {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int W = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		DH.initialize(W);
		int[] edgeA = new int[C];
		int[] edgeB = new int[C];
		for(int i = 0; i < C; i++){
			st = new StringTokenizer(read.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken());
			DH.addEdge(a, b, d, true);
			edgeA[i] = a;
			edgeB[i] = b;
		}
		read.close();
		
		int bad = 0;
		for (int i = 0; i < C; i++) {
			if(!DH.shortestPath(edgeA[i], edgeB[i]))
				bad++;
		}
		System.out.println(bad);

		System.exit(0);
		
	}
	
}

class Edge implements Comparable<Edge>{
	
	int a, b;
	public Edge(int x, int y){
		a = Math.min(x, y);
		b = Math.max(x, y);
	}
	public int compareTo(Edge e){
		if(a == e.a)
			return b - e.b;
		return a - e.a;
	}
	public boolean equals(Edge e){
		return this.compareTo(e) == 0;
	}
	
}


class DH {

	// static double[][] dists;
	static int[][] edges;
	static double[][] weights;
	static int[] inds;
	static int N;
	private static int len;

	public static void initialize(int n) {

		N = n;
		len = N;
		edges = new int[N][N];
		weights = new double[N][N];
		inds = new int[N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				edges[i][j] = -1;
			}
		}

	}

	public static void addEdge(int a, int b, double d, boolean unweighted) {

		if(a == b)
			return;
		edges[a][inds[a]] = b;
		weights[a][inds[a]++] = d;
		if (unweighted) {
			edges[b][inds[b]] = a;
			weights[b][inds[b]++] = d;
		}

	}

	public static boolean shortestPath(int a, int b) {

		double[] dists = new double[N];
		for (int i = 0; i < dists.length; i++) {
			dists[i] = Double.MAX_VALUE;
		}
		len = N;

		DHNode[] pq = new DHNode[N + 1];
		int[] inds = new int[N];
		inds[a] = 1;
		pq[1] = new DHNode(a, 0);
		for (int i = 0; i < edges.length; i++) {
			if (i != a) {
				int ind = i + 1;
				if (i < a)
					ind++;
				pq[ind] = new DHNode(i, Double.MAX_VALUE);
				inds[i] = ind;
			}
		}
		
		int[] prev = new int[N];
		
		for (int v = 0; v < N; v++) {
			DHNode node = dqMin(pq, inds);
			dists[node.index] = Math.min(dists[node.index], node.distance);
			for (int i = 0; i < N && edges[node.index][i] >= 0; i++) {
				int neighbor = edges[node.index][i];
				if (inds[neighbor] < 1)
					continue;
				if (dists[node.index] + weights[node.index][i] < pq[inds[neighbor]].distance) {
					downHeap(pq, neighbor, dists[node.index] + weights[node.index][i], inds);
					prev[neighbor] = node.index;
					if(neighbor == b && node.index != a)
						return false;
				}
			}
		}
		return true;

	}

	private static void downHeap(DHNode[] pq, int i, double d, int[] inds) {

		int ind = inds[i];
		pq[inds[i]].distance = d;

		while (ind / 2 > 0 && d < pq[ind / 2].distance) {
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

	private static void upHeap(DHNode[] pq, int i, int[] inds) {

		int ind = i;

		while (ind * 2 <= len) {
			int b = ind * 2;
			if (b + 1 <= len && pq[b + 1].distance < pq[b].distance)
				b++;
			if (pq[b].distance > pq[ind].distance)
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

	private static DHNode dqMin(DHNode[] pq, int[] inds) {

		DHNode min = pq[1];
		pq[1] = pq[len--];
		upHeap(pq, 1, inds);
		inds[min.index] = -1;
		return min;

	}

}

class DHNode implements Comparable<DHNode> {

	int index;
	double distance;

	public DHNode(int i, double d) {
		index = i;
		distance = d;
	}

	public int compareTo(DHNode other) {

		if (distance == other.distance)
			return index - other.index;
		return (int) (distance - other.distance);

	}

	public String toString() {

		return "Ind " + index + ", dist " + distance;
	}

}
