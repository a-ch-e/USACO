/*
ID: ayc22032
LANG: JAVA
TASK: game1
*/

/*
A Game
IOI'96 - Day 1
Consider the following two-player game played with a sequence of N positive integers (2 <= N <=
100) laid onto a game board. Player 1 starts the game. The players move alternately by selecting a
number from either the left or the right end of the sequence. That number is then deleted from the
board, and its value is added to the score of the player who selected it. A player wins if his sum
is greater than his opponents.

Write a program that implements the optimal strategy. The optimal strategy yields maximum points
when playing against the "best possible" opponent. Your program must further implement an optimal
strategy for player 2.

PROGRAM NAME: game1

INPUT FORMAT

Line 1:	N, the size of the board
Line 2-etc:	N integers in the range (1..200) that are the contents of the game board, from left to
right

SAMPLE INPUT (file game1.in)

6
4 7 2 9
5 2

OUTPUT FORMAT

Two space-separated integers on a line: the score of Player 1 followed by the score of Player 2.

SAMPLE OUTPUT (file game1.out)

18 11
 */

import java.io.*;
import java.util.*;

public class game1 {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("game1.in"));
		int N = Integer.parseInt(read.readLine());
		int[] board = new int[N];
		StringTokenizer st = new StringTokenizer(read.readLine());
		for(int i = 0; i < N; i++){
			if(!st.hasMoreTokens())
				st = new StringTokenizer(read.readLine());
			board[i] = Integer.parseInt(st.nextToken());
		}
		read.close();
		
		int[][] options = new int[N+1][2 * N];
		for(int i = 0; i < N; i++){
			options[N][2 * i] = (N % 2) * board[i];
			options[N][2 * i + 1] = ((N + 1) % 2) * board[i];
		}
		for(int i = N - 1; i > 0; i--){
			for(int j = 0; j < i; j++){
				if(i % 2 == 0){
					if(board[j + N - i] + options[i + 1][2 * j + 1] > board[j] + options[i + 1][2 * j + 3])
						options[i][2 * j] = options[i + 1][2 * j];
					else
						options[i][2 * j] = options[i + 1][2 * j + 2];
					options[i][2 * j + 1] = Math.max(board[j + N - i] + options[i + 1][2 * j + 1], board[j] + options[i + 1][2 * j + 3]);
				} else {
//					System.out.println(i + " " + j);
					if(board[j + N - i] + options[i + 1][2 * j] > board[j] + options[i + 1][2 * j + 2])
						options[i][2 * j + 1] = options[i + 1][2 * j + 1];
					else
						options[i][2 * j + 1] = options[i + 1][2 * j + 3];
					options[i][2 * j] = Math.max(board[j + N - i] + options[i + 1][2 * j], board[j] + options[i + 1][2 * j + 2]);
				}
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("game1.out"));
		
//		for(int i = 1; i <= N; i++){
//			System.out.println(Arrays.toString(options[i]));
//		}
		
		out.println(options[1][0] + " " + options[1][1]);
		System.out.println(options[1][0] + " " + options[1][1]);
		out.close();
		System.exit(0);
		
	}
	
	
}
