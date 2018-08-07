/*
ID: ayc22032
LANG: JAVA
TASK: camelot
*/

/*
Camelot
IOI 98
Centuries ago, King Arthur and the Knights of the Round Table used to meet every year on New Year's
Day to celebrate their fellowship. In remembrance of these events, we consider a board game for one
player, on which one chesspiece king and several knight pieces are placed on squares, no two
knights on the same square.

This example board is the standard 8x8 array of squares:

The King can move to any adjacent square from  to  as long as it does not fall off the board:

A Knight can jump from  to , as long as it does not fall off the board:


During the play, the player can place more than one piece in the same square. The board squares are
assumed big enough so that a piece is never an obstacle for any other piece to move freely.

The player's goal is to move the pieces so as to gather them all in the same square- in the minimal
number of moves. To achieve this, he must move the pieces as prescribed above. Additionally,
whenever the king and one or more knights are placed in the same square, the player may choose to
move the king and one of the knights together from that point on, as a single knight, up to the
final gathering point. Moving the knight together with the king counts as a single move.

Write a program to compute the minimum number of moves the player must perform to produce the
gathering. The pieces can gather on any square, of course.

PROGRAM NAME: camelot

INPUT FORMAT

Line 1:	Two space-separated integers: R,C, the number of rows and columns on the board. There will
be no more than 26 columns and no more than 30 rows.
Line 2..end:	The input file contains a sequence of space-separated letter/digit pairs, 1 or more
per line. The first pair represents the board position of the king; subsequent pairs represent
positions of knights. There might be 0 knights or the knights might fill the board. Rows are
numbered starting at 1; columns are specified as upper case characters starting with `A'.

SAMPLE INPUT (file camelot.in)

8 8
D 4
A 3 A 8
H 1 H 8

The king is positioned at D4. There are four knights, positioned at A3, A8, H1, and H8.

OUTPUT FORMAT

A single line with the number of moves to aggregate the pieces.
SAMPLE OUTPUT (file camelot.out)

10

SAMPLE OUTPUT ELABORATION

They gather at B5. 
Knight 1: A3 - B5 (1 move) 
Knight 2: A8 - C7 - B5 (2 moves) 
Knight 3: H1 - G3 - F5 - D4 (picking up king) - B5 (4 moves) 
Knight 4: H8 - F7 - D6 - B5 (3 moves) 
1 + 2 + 4 + 3 = 10 moves. 
 */

import java.io.*;
import java.util.*;

public class camelot {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("camelot.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int R = Integer.parseInt(st.nextToken()); int C = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(read.readLine());
		King king = new King((st.nextToken().charAt(0) - 'A'), Integer.parseInt(st.nextToken()) - 1, R, C);
		ArrayList<Knight> knights = new ArrayList<Knight>(Math.max(R, C));
		while(true){
			String temp = read.readLine();
			if(temp == null) break;
			st = new StringTokenizer(temp);
			while(st.hasMoreTokens()){
				knights.add(new Knight((st.nextToken().charAt(0) - 'A'), Integer.parseInt(st.nextToken()) - 1, R, C));
//				printArr(knights.get(knights.size() - 1).dists);
			}
		}
		read.close();
		
//		for(int i = 0; i < 5; i++){
//			for(int j = 0; j < 5; j++){
//				if(king.neighbors[i][j] != null){
//					System.out.format("(%d, %d): ", king.r + i - 2, king.c + j - 2);
//					printArr(king.neighbors[i][j].dists);
//				}
//			}
//		}
		
		
		int[][] board = new int[R][C];
		int[][] noKing = new int[R][C];
		boolean[][] reach = new boolean[R][C];
//		for(int i = 0; i < R; i++){
//			for(int j = 0; j < C; j++){
//				board[i][j] = Integer.MAX_VALUE;
//			}
//		}
		for(int k = 0; k < knights.size(); k++){
			for(int i = 0; i < R; i++){
				for(int j = 0; j < C; j++){
					int minCost = board[i][j] + knights.get(k).dists[i][j];
					if(knights.get(k).dists[i][j] < 0){
						reach[i][j] = true;
						continue;
					}
					if(k == 0){
						minCost += Math.max(Math.abs(i - king.r), Math.abs(j - king.c));
					}
					int tempCost = noKing[i][j];
					for(int x = 0; x < 5; x++){
						for(int y = 0; y < 5; y++){
							if(king.neighbors[x][y] == null)
								continue;
							int kingCost = Math.max(Math.abs(x - 2), Math.abs(y - 2));
							if(knights.get(k).dists[king.neighbors[x][y].r][king.neighbors[x][y].c] < 0 || king.neighbors[x][y].dists[i][j] < 0)
								continue;
							minCost = Math.min(minCost, tempCost + kingCost + knights.get(k).dists[king.neighbors[x][y].r][king.neighbors[x][y].c] + king.neighbors[x][y].dists[i][j]);
							if(i == 4 && j == 1 && k == 2){
//								System.out.format("(%d, %d)\n", x, y);
//								System.out.format("(%d, %d)\n", king.neighbors[x][y].r, king.neighbors[x][y].c);
//								System.out.println("Knight " + k);
//								System.out.println("King Cost " + kingCost);
//								System.out.println("Temp Cost " + tempCost);
//								System.out.println("To square cost " + knights.get(k).dists[king.neighbors[x][y].r][king.neighbors[x][y].c]);
//								System.out.println("From square cost " + king.neighbors[x][y].dists[i][j]);
							}
						}
					}
					board[i][j] = minCost;
					noKing[i][j] += knights.get(k).dists[i][j];
				}
			}
//			printArr(board);
		}
		int minDist = Integer.MAX_VALUE;
		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){
				if(reach[i][j])
					continue;
				minDist = Math.min(minDist, board[i][j]);
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("camelot.out"));
		out.println(minDist);
		System.out.println(minDist);
		out.close();
		System.exit(0);
		
	}
	
	private static void printArr(int[][] arr){
		
		System.out.print("[" + Arrays.toString(arr[0]));
		for(int i = 1; i < arr.length; i++){
			System.out.print(", " + Arrays.toString(arr[i]));
		}
		System.out.println();
		
	}
	
}

class Knight{
	
	int[][] dists;
	int r, c;
	
	public Knight(int col, int row, int R, int C){
		
		dists = new int[R][C];
		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){
				dists[i][j] = -1;
			}
		}
		c = col;
		r = row;
		dists[r][c] = 0;
		
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(C * r + c);
		for(int dist = 1; !q.isEmpty(); dist++){
			Queue<Integer> temp = new LinkedList<Integer>();
//			System.out.println(dist);
			for(int k : q){
				int i = k / C; int j = k % C;
//				System.out.println(i + " " + j);
				if(i + 1 < R && j + 2 < C && dists[i + 1][j + 2] < 0){
					dists[i + 1][j + 2] = dist;
					temp.add((i + 1) * C + j + 2);
				}
				if(i + 2 < R && j + 1 < C && dists[i + 2][j + 1] < 0){
					dists[i + 2][j + 1] = dist;
					temp.add((i + 2) * C + j + 1);
				}
				if(i + 2 < R && j - 1 >= 0 && dists[i + 2][j - 1] < 0){
					dists[i + 2][j - 1] = dist;
					temp.add((i + 2) * C + j - 1);
				}
				if(i + 1 < R && j - 2 >= 0 && dists[i + 1][j - 2] < 0){
					dists[i + 1][j - 2] = dist;
					temp.add((i + 1) * C + j - 2);
				}
				if(i - 1 >= 0 && j - 2 >= 0 && dists[i - 1][j - 2] < 0){
					dists[i - 1][j - 2] = dist;
					temp.add((i - 1) * C + j - 2);
				}
				if(i - 2 >= 0 && j - 1 >= 0 && dists[i - 2][j - 1] < 0){
					dists[i - 2][j - 1] = dist;
					temp.add((i - 2) * C + j - 1);
				}
				if(i - 2 >= 0 && j + 1 < C && dists[i - 2][j + 1] < 0){
					dists[i - 2][j + 1] = dist;
					temp.add((i - 2) * C + j + 1);
				}
				if(i - 1 >= 0 && j + 2 < C && dists[i - 1][j + 2] < 0){
					dists[i - 1][j + 2] = dist;
					temp.add((i - 1) * C + j + 2);
				}
			}
			q = temp;
		}
		
	}
}

class King{
	
	Knight[][] neighbors;
	int r, c;
	
	public King(int col, int row, int R, int C){
		
		r = row; c = col;
		neighbors = new Knight[5][5];
		for(int i = 0; i < 5; i++){
			if(r + i - 2 < 0)
				continue;
			if(r + i - 2 >= R)
				break;
			for(int j = 0; j < 5; j++){
				if(c + j - 2 < 0)
					continue;
				if(c + j - 2 >= C)
					break;
				neighbors[i][j] = new Knight(c + j - 2, r + i - 2, R, C);
			}
		}
		
	}
	
}
