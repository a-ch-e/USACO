/*
Allen Cheng
USACO Gold Division, February 2015
Problem 1 : Cow Hopscotch
 */

/*
Just like humans enjoy playing the game of Hopscotch, Farmer John's cows have invented a variant of the game for themselves to play.
Being played by clumsy animals weighing nearly a ton, Cow Hopscotch almost always ends in disaster, but this has surprisingly not
deterred the cows from attempting to play nearly every afternoon.

The game is played on an R by C grid (2 <= R <= 750, 2 <= C <= 750), where each square is labeled with an integer in the range 1..K
(1 <= K <= R*C). Cows start in the top-left square and move to the bottom-right square by a sequence of jumps, where a jump is valid
if and only if

1) You are jumping to a square labeled with a different integer than your current square,

2) The square that you are jumping to is at least one row below the current square that you are on, and

3) The square that you are jumping to is at least one column to the right of the current square that you are on.

Please help the cows compute the number of different possible sequences of valid jumps that will take them from the top-left square
to the bottom-right square.

INPUT FORMAT: (hopscotch.in)

The first line contains the integers R, C, and K. The next R lines will each contain C integers, each in the range 1..K.

OUTPUT FORMAT: (hopscotch.out)

Output the number of different ways one can jump from the top-left square to the bottom-right square, mod 1000000007.

SAMPLE INPUT:

4 4 4
1 1 1 1
1 3 2 1
1 2 4 1
1 1 1 1

SAMPLE OUTPUT:

5
 */

import java.io.*;
import java.util.*;

public class Hopscotch {
	
	private static final int M = 1000000007;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("hopscotch.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int R = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int[][] grid = new int[R][C];
		for(int i = 0; i < R; i++){
			st = new StringTokenizer(read.readLine());
			for(int j = 0; j < C; j++){
				grid[i][j] = Integer.parseInt(st.nextToken()) - 1;
			}
		}
		read.close();
		
		
		int[][] prevDiag = new int[Math.min(R, C)][K + 1];
		int[][] currDiag = new int[Math.min(R, C)][K + 1];
		int[][] nextDiag = new int[Math.min(R, C)][K + 1];
		int prevLen = 0;
		int currLen = 1;
		int nextLen = 0;
		currDiag[0][K] = 1;
		
		for(int s = 1; s < R + C - 1; s++){
			for(int i = Math.max(s - C + 1, 0); s - i >= 0 && i < R; i++){
				int j = s - i;
				for(int k = 0; k < K; k++){
					if(i > 0){
						nextDiag[i][k] = currDiag[i - 1][k];
					}
					if(j > 0){
						nextDiag[i][k] = (nextDiag[i][k] + currDiag[i][k]) % M;
					}
					if(i > 0 && j > 0){
						nextDiag[i][k] = (nextDiag[i][k] - prevDiag[i - 1][k] + M) % M;
						if(grid[i - 1][j - 1] != k)
							nextDiag[i][k] = (nextDiag[i][k] + prevDiag[i - 1][K]) % M;
					}
					if(k == grid[i][j])
						nextDiag[i][K] = nextDiag[i][k];
//					if(i == 4 && j == 2 && k == 0){
//						System.out.println(Arrays.toString(currDiag[i - 1]));
//						System.out.println(Arrays.toString(prevDiag[i - 1]));
//						System.out.println(Arrays.toString(currDiag[i]));
//					}
				}
				nextLen++;
//				System.out.format("(%d, %d), %d: ", i, j, grid[i][j]);
//				System.out.println(Arrays.toString(nextDiag[i]));
			}
			for(int i = 0; i < currDiag.length; i++){
				System.arraycopy(currDiag[i], 0, prevDiag[i], 0, K + 1);
			}
			prevLen = currLen;
			for(int i = 0; i < nextDiag.length; i++){
				System.arraycopy(nextDiag[i], 0, currDiag[i], 0, K + 1);
			}
			currLen = nextLen;
			nextLen = 0;
		}

		/*
		int[][][] ways = new int[R][C][K + 1];
		ways[0][0][K] = 1;
		
		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){
				for(int k = 0; k < K; k++){
					if(i > 0){
						ways[i][j][k] = ways[i - 1][j][k];
					}
					if(j > 0){
						ways[i][j][k] = (ways[i][j][k] + ways[i][j - 1][k]) % M;
					}
					if(i > 0 && j > 0){
						ways[i][j][k] = (ways[i][j][k] - ways[i - 1][j - 1][k] + M) % M;
						if(grid[i - 1][j - 1] != k)
							ways[i][j][k] = (ways[i][j][k] + ways[i - 1][j - 1][K]) % M;
					}
					if(k == grid[i][j])
						ways[i][j][K] = (ways[i][j][K] + ways[i][j][k]) % M;
				}
				System.out.print(grid[i][j] + ": " + Arrays.toString(ways[i][j]) + "\t");
			}
			System.out.println();
		}
		*/
		
		PrintWriter out = new PrintWriter(new FileWriter("hopscotch.out"));
		out.println(currDiag[R - 1][K]);
		System.out.println(currDiag[R - 1][K]);
//		out.println(ways[R - 1][C - 1][K]);
//		System.out.println(ways[R - 1][C - 1][K]);
		out.close();
		System.exit(0);
	
	}
	
}
