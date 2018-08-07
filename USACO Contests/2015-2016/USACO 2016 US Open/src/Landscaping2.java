/*
Allen Cheng, ID ayc22032
USACO US Open 2016
Platinum 1: Landscaping
*/

/*
Farmer John is building a nicely-landscaped garden, and needs to move a large amount of dirt in
the process.
The garden consists of a sequence of N flowerbeds (1<=N<=100,000), where flowerbed i initially
contains A_i units of dirt. Farmer John would like to re-landscape the garden so that each
flowerbed i instead contains B_i units of dirt. The A_i's and B_i's are all integers in the range
0...10.

To landscape the garden, Farmer John has several options: he can purchase one unit of dirt and
place it in a flowerbed of his choice for X units of money. He can remove one unit of dirt from a 
flowerbed of his choice and have it shipped away for Y units of money. He can also transport one
unit of dirt from flowerbed i to flowerbed j at a cost of Z times |i-j|. Please compute the
minimum total cost for Farmer John to complete his landscaping project.

INPUT FORMAT (file landscape.in):

The first line of input contains N, X, Y, and Z (0<=X,Y<=10^8;0<=Z<=1000). Line i+1 contains the
integers A_i and B_i.

OUTPUT FORMAT (file landscape.out):

Please print the minimum total cost FJ needs to spend on landscaping.

SAMPLE INPUT:

4 100 200 1
1 4
2 3
3 2
4 0

SAMPLE OUTPUT:

210
Note that this problem has been asked in a previous USACO contest, at the silver level; however,
the limits in the present version have been raised considerably, so one should not expect many
points from the solution to the previous, easier version.

Problem credits: Brian Dean
 */


import java.io.*;
import java.util.*;

public class Landscaping2 {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("landscape.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int X = Integer.parseInt(st.nextToken());
		int Y = Integer.parseInt(st.nextToken());
		int Z = Integer.parseInt(st.nextToken());
		Pile[] piles = new Pile[N];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(read.readLine());
			piles[i] = new Pile(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), N);
		}
		read.close();
		Edge.piles = piles;
		
		int[] edges = new int[N];
		ArrayList<ArrayList<Integer>> links = new ArrayList<ArrayList<Integer>>(N);
		for(int i = 0; i < N; i++){
			links.add(new ArrayList<Integer>(2));
		}
		
		int len = N;
		Edge[] tempH = new Edge[N];
		tempH[0] = new Edge(0, 1);
		for(int i = 0; i < N; i++){
			if(i > 0)
				tempH[i] = new Edge(i, i - 1);
			tempH[i] = tempH[i].nextEdge();
			links.get(tempH[i].b).add(i);
		}
		Arrays.sort(tempH);
		System.out.println(Arrays.toString(tempH));
		Edge[] heap = new Edge[N + 1];
		for(int i = 1; i <= len; i++){
			heap[i] = tempH[i - 1];
			edges[heap[i].a] = i;
		}
		
		long cost = 0;
		while(len > 0){
			while(downHeap(heap, 1, len, edges)){
				;
			}
			Edge best = heap[1];
			System.out.println(best);
			int a = best.a;
			int b = best.b;
			if(Z*(best.b - best.a) > X + Y || best.qty() <= 0)
				break;
			cost += best.update();
			while(downHeap(heap, 1, len, edges)){
				;
			}
			for(int i = 1; i <= len; i++){
				System.out.print(heap[i] + " ");
			}
			System.out.println();
			if(piles[a].start == piles[a].goal){
				for(int i : links.get(a)){
					heap[edges[i]] = heap[edges[i]].nextEdge();
					if(heap[edges[i]] == null)
						break;
					downHeap(heap, edges[i], len, edges);
				}
				heap[edges[a]] = heap[len--];
				if(heap[edges[a]] == null)
					break;
				downHeap(heap, edges[a], len, edges);
			} else {
				heap[edges[a]] = heap[edges[a]].nextEdge();
				if(heap[edges[a]] == null)
					break;
				System.out.println(heap[edges[a]]);
				downHeap(heap, edges[a], len, edges);
			}
			
			if(piles[b].start == piles[b].goal){
				for(int i : links.get(b)){
					heap[edges[i]] = heap[edges[i]].nextEdge();
					if(heap[edges[i]] == null)
						break;
					downHeap(heap, edges[i], len, edges);
				}
				heap[edges[b]] = heap[len--];
				if(heap[edges[b]] == null)
					break;
				downHeap(heap, edges[b], len, edges);
			} else {
				heap[edges[b]] = heap[edges[b]].nextEdge();
				if(heap[edges[b]] == null)
					break;
				//System.out.println(heap[edges[b]]);
				downHeap(heap, edges[b], len, edges);
			}
			for(int i = 1; i <= len; i++){
				System.out.print(heap[i] + " ");
			}
			System.out.println();
			System.out.println();
		}

		for(int i = 0; i < N; i++){
			if(piles[i].start > piles[i].goal)
				cost += Y * (piles[i].start - piles[i].goal);
			else 
				cost += X * (piles[i].goal - piles[i].start);
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("landscape.out"));
		out.println(cost);
		System.out.println(cost);
		out.close();
		System.exit(0);;
		
	}
	
	private static boolean upHeap(Edge[] pq, int ind, int len, int[] edges) {

		boolean done = false;
		while(ind / 2 >= 1 && pq[ind].compareTo(pq[ind / 2]) < 0){
			done = true;
			int swap = ind/2;
			int tempA = pq[ind].a;
			int tempB = pq[ind].b;
			pq[ind].a = pq[swap].a;
			pq[ind].b = pq[swap].b;
			edges[pq[ind].a] = ind;
			ind = swap;
			pq[ind].a = tempA;
			pq[ind].b = tempB;
			edges[pq[ind].a] = ind;
		}
		return done;
		
	}
	
	private static boolean downHeap(Edge[] pq, int ind, int len, int[] edges) {

		boolean done = false;
		while((2*ind <= len && pq[ind].compareTo(pq[2*ind]) > 0) ||
				(2*ind + 1 <= len && pq[ind].compareTo(pq[2*ind + 1]) > 0)){
			done = true;
			int swap = 2*ind;
			if(pq[swap].compareTo(pq[swap+1]) > 0)
				swap++;
			int tempA = pq[ind].a;
			int tempB = pq[ind].b;
			pq[ind].a = pq[swap].a;
			pq[ind].b = pq[swap].b;
			edges[pq[ind].a] = ind;
			ind = swap;
			pq[ind].a = tempA;
			pq[ind].b = tempB;
			edges[pq[ind].a] = ind;
		}
		return done;
		
	}
	
}

class Pile{
	
	int start, goal;
	
	public Pile(int a, int b, int N){
		start = a;
		goal = b;
	}
	
}

class Edge implements Comparable<Edge>{
	
	int a, b;
	public static Pile[] piles;
	
	public Edge(int a1, int a2){
		a = a1;
		b = a2;
	}
	
	public Edge nextEdge(){
		int start = Math.abs(b - a);
		for(int i = start; i < piles.length; i++){
			Edge best = null;
			if(a - i >= 0){
				best = new Edge(a, a - i);
			}
			if(a + i < piles.length){
				Edge temp = new Edge(a, a + i);
				if(best == null || temp.qty() > best.qty())
					best = temp;
			}
//			System.out.println(best);
			if(best == null || best.qty() > 0)
				return best;
		}
//		System.out.println("!!");
		return null;
	}
	
	public int qty(){
		int qty = 0;
		if(piles[a].start > piles[a].goal && piles[b].start < piles[b].goal){
			qty = Math.min(piles[a].start - piles[a].goal, piles[b].goal - piles[b].start);
		} else if(piles[a].start < piles[a].goal && piles[b].start > piles[b].goal){
			qty = Math.min(piles[a].goal - piles[a].start, piles[b].start - piles[b].goal);
		}
		return qty;
	}
	
	public int update(){
		int qty = 0;
		if(piles[a].start > piles[a].goal && piles[b].start < piles[b].goal){
			qty = Math.min(piles[a].start - piles[a].goal, piles[b].goal - piles[b].start);
			piles[a].start -= qty;
			piles[b].goal -= qty;
		} else if(piles[a].start < piles[a].goal && piles[b].start > piles[b].goal){
			qty = Math.min(piles[a].goal - piles[a].start, piles[b].start - piles[b].goal);
			piles[a].goal -= qty;
			piles[b].start -= qty;
		}
		return qty * Math.abs(b - a);
	}
	
	public int compareTo(Edge e){

		if (Math.abs(b - a) == Math.abs(e.b - e.a)){
			if(qty() < e.qty())
				return 1;
			else if(qty() > e.qty())
				return -1;
			return a - e.a;
		}
		return (Math.abs(b - a) - Math.abs(e.b - e.a));
	}
	
	public String toString(){
		return "(" + a + ", " + b + ")";
	}
	
}