/*
Allen Cheng, ID ayc22032
USACO, December 2015
Gold 3: Bessie's Dream
*/

/*
After eating too much fruit in Farmer John's kitchen, Bessie the cow is getting some very strange
dreams! In her most recent dream, she is trapped in a maze in the shape of an N x M grid of tiles
(1 <= N,M <= 1,000). She starts on the top-left tile and wants to get to the bottom-right tile.
When she is standing on a tile, she can potentially move to the adjacent tiles in any of the four
cardinal directions.
But wait! Each tile has a color, and each color has a different property! Bessie's head hurts just
thinking about it:

If a tile is red, then it is impassable.
If a tile is pink, then it can be walked on normally.
If a tile is orange, then it can be walked on normally, but will make Bessie smell like oranges.
If a tile is blue, then it contains piranhas that will only let Bessie pass if she smells like
oranges.
If a tile is purple, then Bessie will slide to the next tile in that direction (unless she is
unable to cross it). If this tile is also a purple tile, then Bessie will continue to slide until
she lands on a non-purple tile or hits an impassable tile. Sliding through a tile counts as a
move. Purple tiles will also remove Bessie's smell.
(If you're confused about purple tiles, the example will illustrate their use.)

Please help Bessie get from the top-left to the bottom-right in as few moves as possible.

INPUT FORMAT (file dream.in):

The first line has two integers N and M, representing the number of rows and columns of the maze.
The next N lines have M integers each, representing the maze:

The integer '0' is a red tile
The integer '1' is a pink tile
The integer '2' is an orange tile
The integer '3' is a blue tile
The integer '4' is a purple tile
The top-left and bottom-right integers will always be '1'.

OUTPUT FORMAT (file dream.out):

A single integer, representing the minimum number of moves Bessie must use to cross the maze, or -1
if it is impossible to do so.

SAMPLE INPUT:

4 4
1 0 2 1
1 1 4 1
1 0 4 0
1 3 1 1

SAMPLE OUTPUT:

10
In this example, Bessie walks one square down and two squares to the right (and then slides one
more square to the right). She walks one square up, one square left, and one square down (sliding
two more squares down) and finishes by walking one more square right. This is a total of 10 moves
(DRRRULDDDR).

Problem credits: Nathan Pinsker
*/

import java.io.*;
import java.util.*;

public class Dream {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("dream.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		ArrayList<Integer> or = new ArrayList<Integer>(Math.max(N, M));
		ArrayList<Integer> bl = new ArrayList<Integer>(Math.max(N, M));
		int[][] grid = new int[N][M];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(read.readLine());
			for(int j = 0; j < M; j++){
				grid[i][j] = Integer.parseInt(st.nextToken());
				if(grid[i][j] == 2)
					or.add(M * i + j);
				if(grid[i][j] == 3)
					bl.add(M * i + j);
			}
		}
		read.close();
		
		int[] lens1 = new int[N * M];
		int[] lens2 = new int[N * M];
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){
				lens1[M*i + j] = 4;
				lens2[M*i + j] = 4;
				if(grid[i][j] == 2)
					lens2[M*i + j] += bl.size();
			}
		}
		
		DH overall = new DH(N * M, lens2);
		
		DH orange = new DH(N * M, lens1);
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){
				if(grid[i][j] != 0 && grid[i][j] != 4){
					if(j < M - 1)
						orange.addEdge(M*i + j, M*i + j+1, 1, true);
					if(i < N - 1)
						orange.addEdge(M*i + j, M*(i+1) + j, 1, true);
				}
			}
		}
		for(int i : or){
			for(int j : bl){
				overall.addEdge(i, j, orange.shortestDist(i, j), false);
			}
		}
		
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){
				if(grid[i][j] == 0)
					continue;
				if (j < M - 1) {
					if (grid[i][j + 1] == 1 || grid[i][j + 1] == 2)
						overall.addEdge(M*i + j, M*i + j+1, 1, false);
					else if (grid[i][j + 1] == 4) {
						int col = j + 1;
						while (grid[i][col] == 4 && col + 1 < M && grid[i][col + 1] != 0 && grid[i][col + 1] != 3)
							col++;
						overall.addEdge(M*i + j, M*i + col, col - j, false);
					}
				}
				if (i < N - 1) {
					if (grid[i + 1][j] == 1 || grid[i + 1][j] == 2)
						overall.addEdge(M*i + j, M*(i+1) + j, 1, false);
					else if (grid[i + 1][j] == 4) {
						int row = i + 1;
						while (grid[row][j] == 4 && row + 1 < N && grid[row + 1][j] != 0 && grid[row + 1][j] != 3)
							row++;
						overall.addEdge(M*i + j, M*row + j, row - i, false);
					}
				}
				if (j > 0) {
					if (grid[i][j - 1] == 1 || grid[i][j - 1] == 2)
						overall.addEdge(M*i + j, M*i + j-1, 1, false);
					else if (grid[i][j - 1] == 4) {
						int col = j - 1;
						while (grid[i][col] == 4 && col - 1 >= 0 && grid[i][col - 1] != 0 && grid[i][col - 1] != 3)
							col--;
						overall.addEdge(M*i + j, M*i + col, j - col, false);
					}
				}
				if (i > 0) {
					if (grid[i - 1][j] == 1 || grid[i - 1][j] == 2)
						overall.addEdge(M*i + j, M*(i-1) + j, 1, false);
					else if (grid[i - 1][j] == 4) {
						int row = i - 1;
						while (grid[row][j] == 4 && row - 1 >= 0 && grid[row - 1][j] != 0 && grid[row - 1][j] != 3)
							row--;
						overall.addEdge(M*i + j, M*row + j, i - row, false);
					}
				}
			}
		}
		int ans = overall.shortestDist(0, N*M - 1);
		if(ans == Integer.MAX_VALUE)
			ans = -1;
		
		PrintWriter out = new PrintWriter(new FileWriter("dream.out"));
		out.println(ans);
		System.out.println(ans);
		out.close();
		System.exit(0);
		
	}
	
}

class DH {

	int[][] edges;
	int[][] weights;
	int[] inds;
	int N;
	private int len;

	public DH(int n, int[] lens) {

		N = n;
		len = N;
		edges = new int[N][];
		weights = new int[N][];
		inds = new int[N];
		for (int i = 0; i < N; i++) {
			edges[i] = new int[lens[i]];
			weights[i] = new int[lens[i]];
			for (int j = 0; j < edges[i].length; j++) {
				edges[i][j] = -1;
			}
		}

	}

	public void addEdge(int a, int b, int d, boolean unweighted) {

		if(a == b)
			return;
		edges[a][inds[a]] = b;
		weights[a][inds[a]++] = d;
		if (unweighted) {
			edges[b][inds[b]] = a;
			weights[b][inds[b]++] = d;
		}

	}

	public int shortestDist(int a, int b) {

		int[] dists = new int[N];
		for (int i = 0; i < dists.length; i++) {
			dists[i] = Integer.MAX_VALUE;
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
				pq[ind] = new DHNode(i, Integer.MAX_VALUE);
				inds[i] = ind;
			}
		}

		for (int v = 0; v < N; v++) {
			DHNode node = dqMin(pq, inds);
			if (node.index == b)
				return node.distance;
			dists[node.index] = Math.min(dists[node.index], node.distance);
			if(dists[node.index] == Integer.MAX_VALUE)
				return Integer.MAX_VALUE;
			for (int i = 0; i < edges[node.index].length && edges[node.index][i] >= 0; i++) {
				int neighbor = edges[node.index][i];
				if (inds[neighbor] < 1)
					continue;
				if (dists[node.index] + weights[node.index][i] < pq[inds[neighbor]].distance) {
					downHeap(pq, neighbor, dists[node.index]
							+ weights[node.index][i], inds);
				}
			}
		}

		return Integer.MAX_VALUE;

	}


	private void downHeap(DHNode[] pq, int i, int d, int[] inds) {

		int ind = inds[i];
		pq[inds[i]].distance = d;

		while (ind / 2 > 0 && d < pq[ind / 2].distance) {
			int b = ind / 2;
			int i_a = pq[ind].index;
			int d_a = pq[ind].distance;
			pq[ind].index = pq[b].index;
			pq[ind].distance = pq[b].distance;
			pq[b].index = i_a;
			pq[b].distance = d_a;

			inds[pq[b].index] = b;
			inds[pq[ind].index] = ind;

			ind = b;
		}

	}

	private void upHeap(DHNode[] pq, int i, int[] inds) {

		int ind = i;

		while (ind * 2 <= len) {
			int b = ind * 2;
			if (b + 1 <= len && pq[b + 1].distance < pq[b].distance)
				b++;
			if (pq[b].distance > pq[ind].distance)
				break;
			int i_a = pq[ind].index;
			int d_a = pq[ind].distance;
			pq[ind].index = pq[b].index;
			pq[ind].distance = pq[b].distance;
			pq[b].index = i_a;
			pq[b].distance = d_a;

			inds[pq[b].index] = b;
			inds[pq[ind].index] = ind;

			ind = b;
		}

	}

	private DHNode dqMin(DHNode[] pq, int[] inds) {

		DHNode min = pq[1];
		pq[1] = pq[len--];
		upHeap(pq, 1, inds);
		inds[min.index] = -1;
		return min;

	}

}

class DHNode implements Comparable<DHNode> {

	int index;
	int distance;

	public DHNode(int i, int d) {
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
