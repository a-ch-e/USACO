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

public class Landscaping {
	
	public static void main2(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("landscape.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int X = Integer.parseInt(st.nextToken());
		int Y = Integer.parseInt(st.nextToken());
		int Z = Integer.parseInt(st.nextToken());
		Pile2[] Pile2s = new Pile2[N];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(read.readLine());
			Pile2s[i] = new Pile2(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), N);
		}
		read.close();
		Edge2.Pile2s = Pile2s;
		
		int len = N*(N-1)/2;
		Edge2[] Edge2s = new Edge2[len];
		int ind = 0;
		for(int i = 0; i < N; i++){
			for(int j = i + 1; j < N; j++){
				Edge2s[ind++] = new Edge2(i, j);
			}
		}
		Arrays.sort(Edge2s);
		Edge2[] heap = new Edge2[len + 1];
		for(int i = 1; i <= len; i++){
			heap[i] = Edge2s[i - 1];
		}
		
		long cost = 0;
		while(len > 0){
			while(downHeap(heap, len)){
				;
			}
			for(int i = 1; i <= len; i++){
				System.out.print(heap[i] + " ");
			}
			System.out.println();
			Edge2 best = heap[1];
			System.out.println(best);
			if(Z*(best.b - best.a) > X + Y || best.qty() <= 0)
				break;
			cost += best.update();
			heap[1] = heap[len--];
		}
//		for(int i = 1; i < N && Z*i < X + Y; i++){
//			for(int j = 0; j < N - i; j++){
//				if(Pile2s[j].start > Pile2s[j].goal && Pile2s[j + i].start < Pile2s[j + i].goal){
//					int qty = Math.min(Pile2s[j].start - Pile2s[j].goal, Pile2s[j + i].goal - Pile2s[j + i].start);
//					Pile2s[j].start -= qty;
//					Pile2s[j + i].goal -= qty;
//					cost += qty * Z * i;
//				}
//				if(Pile2s[j].start < Pile2s[j].goal && Pile2s[j + i].start > Pile2s[j + i].goal){
//					int qty = Math.min(Pile2s[j].goal - Pile2s[j].start, Pile2s[j + i].start - Pile2s[j + i].goal);
//					Pile2s[j].goal -= qty;
//					Pile2s[j + i].start -= qty;
//					cost += qty * Z * i;
//				}
//			}
//		}
		for(int i = 0; i < N; i++){
			if(Pile2s[i].start > Pile2s[i].goal)
				cost += Y * (Pile2s[i].start - Pile2s[i].goal);
			else 
				cost += X * (Pile2s[i].goal - Pile2s[i].start);
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("landscape.out"));
		out.println(cost);
		System.out.println(cost);
		out.close();
		System.exit(0);;
		
	}
	
	private static boolean downHeap(Edge2[] pq, int len) {

		int ind = 1;
		
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
			ind = swap;
			pq[ind].a = tempA;
			pq[ind].b = tempB;
		}
		return done;
		
	}
	
}

class Pile2{
	
	int start, goal;
	
	public Pile2(int a, int b, int N){
		start = a;
		goal = b;
	}
	
}

class Edge2 implements Comparable<Edge2>{
	
	int a, b;
	public static Pile2[] Pile2s;
	
	public Edge2(int a1, int a2){
		a = a1;
		b = a2;
	}
	
	public Edge2 nextEdge2(){
		int start = Math.abs(b - a);
		for(int i = start; i < Pile2s.length; i++){
			Edge2 best = null;
			if(a - start >= 0){
				best = new Edge2(a, a - start);
			}
			if(a + start < Pile2s.length){
				Edge2 temp = new Edge2(a, a + start);
				if(best == null || temp.qty() > best.qty())
					best = temp;
			}
			if(best == null || best.qty() > 0)
				return best;
		}
		return null;
	}
	
	public int qty(){
		int qty = 0;
		if(Pile2s[a].start > Pile2s[a].goal && Pile2s[b].start < Pile2s[b].goal){
			qty = Math.min(Pile2s[a].start - Pile2s[a].goal, Pile2s[b].goal - Pile2s[b].start);
		} else if(Pile2s[a].start < Pile2s[a].goal && Pile2s[b].start > Pile2s[b].goal){
			qty = Math.min(Pile2s[a].goal - Pile2s[a].start, Pile2s[b].start - Pile2s[b].goal);
		}
		return qty;
	}
	
	public int update(){
		int qty = 0;
		if(Pile2s[a].start > Pile2s[a].goal && Pile2s[b].start < Pile2s[b].goal){
			qty = Math.min(Pile2s[a].start - Pile2s[a].goal, Pile2s[b].goal - Pile2s[b].start);
			Pile2s[a].start -= qty;
			Pile2s[b].goal -= qty;
		} else if(Pile2s[a].start < Pile2s[a].goal && Pile2s[b].start > Pile2s[b].goal){
			qty = Math.min(Pile2s[a].goal - Pile2s[a].start, Pile2s[b].start - Pile2s[b].goal);
			Pile2s[a].goal -= qty;
			Pile2s[b].start -= qty;
		}
		return qty * Math.abs(b - a);
	}
	
	public int compareTo(Edge2 e){

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