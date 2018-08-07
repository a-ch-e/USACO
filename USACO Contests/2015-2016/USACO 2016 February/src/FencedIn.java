/*
Allen Cheng, ID ayc22032
USACO, February 2016
Platinum 2: Fenced In
*/

/*
Farmer John has realized that many of his cows are strangely agoraphobic (being fearful of large
open spaces). To try and make them less afraid of grazing, he partitions his large field into a
number of smaller regions by building vertical (north-south) and horizontal (east-west) fences.

The large field is a rectangle with corner points at (0,0) and (A,B). FJ builds n vertical fences
(0 <= n <= 25,000) at distinct locations a_1...a_n (0 < a_i < A); each fence runs from (a_i, 0) to
(a_i, B). He also builds m horizontal fences (0 <= m <= 25,000) at locations b_1...b_m (0 < b_i < B);
each such fence runs from (0, b_i) to (A, b_i). Each vertical fence crosses through each horizontal
fence, subdividing the large field into a total of (n+1)(m+1) regions.

Unfortunately, FJ completely forgot to build gates into his fences, making it impossible for cows
to leave their enclosing region and travel around the entire field! He wants to remedy this
situation by removing pieces of some of his fences to allow cows to travel between adjacent
regions. He wants to select certain pairs of adjacent regions and remove the entire length of fence
separating them; afterwards, he wants cows to be able to wander through these openings so they can
travel anywhere in his larger field.

For example, FJ might take a fence pattern looking like this:

+---+--+
|   |  |
+---+--+
|   |  |  
|   |  |
+---+--+
and open it up like so:

+---+--+
|      |  
+---+  +  
|      |  
|      |
+---+--+
Please help FJ determine the minimum total length of fencing he must remove to accomplish his goal.

INPUT FORMAT (file fencedin.in):

The first line of input contains A, B, n, and m (1 <= A,B <= 1,000,000,000). The next n lines
contain a_1 ... a_n, and the next m lines after that contain b_1 ... b_m.

OUTPUT FORMAT (file fencedin.out):

Please write the minimum length of fencing FJ must remove. Note that this might be too large to fit
into a standard 32-bit integer, so you may need to use 64-bit integer types (e.g., "long long" in
C/C++).

SAMPLE INPUT:

15 15 5 2
2
5
10
6
4
11
3

SAMPLE OUTPUT:

44

Problem credits: Brian Dean
 */
import java.io.*;
import java.util.*;


public class FencedIn {
	
	private static int[] xLens, yLens;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("fencedin.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int A = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[] xs = new int[N];
		int[] ys = new int[M];
		for(int i = 0; i < N; i++){
			xs[i] = Integer.parseInt(read.readLine());
		}
		for(int i = 0; i < M; i++){
			ys[i] = Integer.parseInt(read.readLine());
		}
		read.close();
		
		Arrays.sort(xs);
		xLens = new int[N + 1];
		xLens[0] = xs[0];
		xLens[N] = A - xs[N - 1];
		for(int i = 1; i < N; i++){
			xLens[i] = xs[i] - xs[i - 1];
		}
		Arrays.sort(ys);
		yLens = new int[M + 1];
		yLens[0] = ys[0];
		yLens[M] = B - ys[M - 1];
		for(int i = 1; i < M; i++){
			yLens[i] = ys[i] - ys[i - 1];
		}
		
		boolean[] added = new boolean[(N+1) * (M+1)];
		long totalLength = 0;
		TreeSet<Edge> perimeter = new TreeSet<Edge>();
		int numEdges = 0;
		
		perimeter.addAll(perimeter(0));
		added[0] = true;
		
		while(!perimeter.isEmpty() && numEdges < (N+1)*(M+1) - 1){
//			System.out.println("" + totalLength + ": " + perimeter);
			Edge e = perimeter.pollFirst();
			if(added[e.a] && added[e.b])
				continue;
			int adding = e.a;
			if(added[e.a])
				adding = e.b;
//			System.out.println(adding);
			added[adding] = true;
			numEdges++;
			totalLength += e.len;
			for(Edge newE : perimeter(adding)){
//				if(newE.a == 4 && newE.b == 10)
//					System.out.println(newE);
				if(added[newE.a] && added[newE.b]){
					perimeter.remove(newE);
					continue;
				}				
//				for(Edge edge : perimeter){
//					System.out.println("" + edge + " equals newE: " + edge.equals(newE));
//				}
//				System.out.println(perimeter.contains(newE));
				perimeter.add(newE);
			}
//			System.out.println(perimeter);
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("fencedin.out"));
		out.println(totalLength);
		System.out.println(totalLength);
		out.close();
		System.exit(0);
		
	}
	
	private static List<Edge> perimeter(int a) {

		ArrayList<Edge> list = new ArrayList<Edge>(4);
		int N = xLens.length;
		int M = yLens.length;
		int x = a % N;
		int y = a / N;
		if (x > 0) {
			list.add(new Edge(a - 1, a, yLens[y]));
		}
		if (x < N - 1) {
			list.add(new Edge(a, a + 1, yLens[y]));
		}
		if (y > 0) {
			list.add(new Edge(a - N, a, xLens[x]));
		}
		if (y < M - 1) {
			list.add(new Edge(a, a + N, xLens[x]));
		}
		return list;

	}
	
}

class Edge implements Comparable<Edge>{
	
	int a, b, len;
	
	public Edge(int c1, int c2, int l){
		a = Math.min(c1, c2);
		b = Math.max(c1, c2);
		len = l;
	}
	
	public int compareTo(Edge other){
		
		if(this.len == other.len){
			if(this.a == other.a)
				return this.b - other.b;
			return this.a - other.a;
		}
		return this.len - other.len;
		
	}
	
	public boolean equals(Edge other){
		return this.len == other.len && this.a == other.a && this.b == other.b;
	}
	
	public String toString(){
		
		return String.format("(%d, %d, %d)", a, b, len);
		
	}
	
}
