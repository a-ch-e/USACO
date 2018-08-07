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


public class FencedIn2 {
	
	private static int[] xLens, yLens;
	private static int[][] xLookup, yLookup;
	
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
		
		xLookup = new int[N + 2][];
		for(int i = 1; i < xLookup.length; i++){
			xLookup[i] = new int[N + 2 - i];
			for(int j = 0; j < xLookup[i].length; j++){
				if(i == 1)
					xLookup[i][j] = j;
				else {
					xLookup[i][j] = xLookup[i - 1][j];
					if(xLens[xLookup[i - 1][j + 1]] < xLens[xLookup[i - 1][j]])
						xLookup[i][j] = xLookup[i - 1][j + 1];
				}
			}
//			System.out.println(Arrays.toString(xLookup[i]));
		}
		yLookup = new int[M + 2][];
		for(int i = 1; i < yLookup.length; i++){
			yLookup[i] = new int[M + 2 - i];
			for(int j = 0; j < yLookup[i].length; j++){
				if(i == 1)
					yLookup[i][j] = j;
				else {
					yLookup[i][j] = yLookup[i - 1][j];
					if(yLens[yLookup[i - 1][j + 1]] < yLens[yLookup[i - 1][j]])
						yLookup[i][j] = yLookup[i - 1][j + 1];
				}
			}
//			System.out.println(Arrays.toString(yLookup[i]));
		}
		
		int xInd = xLookup[N + 1][0];
		int xMin = xLens[xInd];
		
		int yInd = yLookup[M + 1][0];
		int yMin = yLens[yInd];
		
		long totalLength = 0;
		if(yMin < xMin){
			totalLength = N * yMin + cost(0, N + 1, 0, yInd) + cost(0, N + 1, yInd + 1, M + 1);
		} else {
			totalLength = M * xMin + cost(0, xInd, 0, M + 1) + cost(xInd + 1, N + 1, 0, M + 1);
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("fencedin.out"));
		out.println(totalLength);
		System.out.println(totalLength);
		out.close();
		System.exit(0);
		
	}
	
	private static long cost(int x1, int x2, int y1, int y2) {

		if(x1 == x2 || y1 == y2)
			return 0;
		
		int xInd = xLookup[x2 - x1][x1];
		int xMin = xLens[xInd];
		
		int yInd = yLookup[y2 - y1][y1];
		int yMin = yLens[yInd];
		
		long ans = 0;
		if(yMin < xMin){
			ans = (x2 - x1) * yMin + cost(x1, x2, y1, yInd) + cost(x1, x2, yInd + 1, y2);
			if(x2 - x1 == xLens.length)
				ans += xMin - yMin;
		} else {
			ans = (y2 - y1) * xMin + cost(x1, xInd, y1, y2) + cost(xInd + 1, x2, y1, y2);
			if(y2 - y1 == yLens.length)
				ans += yMin - xMin;
		}
//		ans = Math.min(xMin, yMin) + (y2 - y1 - 1) * xMin + (x2 - x1 - 1) * yMin +
//				cost(x1, xInd, y1, yInd) + cost(xInd + 1, x2, y1, yInd) +
//				cost(x1, xInd, yInd + 1, y2) + cost(xInd + 1, x2, yInd + 1, y2);
//		System.out.format("(%d, %d, %d, %d): %d\n", x1, x2, y1, y2, ans);
		return ans;

	}
	
}
