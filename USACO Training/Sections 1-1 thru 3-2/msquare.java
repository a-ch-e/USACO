/*
ID: ayc22032
LANG: JAVA
TASK: msquare
 */

/*

Magic Squares
IOI'96
Following the success of the magic cube, Mr. Rubik invented its planar version, called
magic squares. This is a sheet composed of 8 equal-sized squares:

1	2	3	4
8	7	6	5
In this task we consider the version where each square has a different color. Colors are
denoted by the first 8 positive integers. A sheet configuration is given by the sequence
of colors obtained by reading the colors of the squares starting at the upper left corner
and going in clockwise direction. For instance, the configuration of Figure 3 is given by
the sequence (1,2,3,4,5,6,7,8). This configuration is the initial configuration.

Three basic transformations, identified by the letters `A', `B' and `C', can be applied
to a sheet:

'A': exchange the top and bottom row,
'B': single right circular shifting of the rectangle,
'C': single clockwise rotation of the middle four squares.
Below is a demonstration of applying the transformations to the initial squares given above:

A:
8	7	6	5
1	2	3	4

B:	
4	1	2	3
5	8	7	6

C:	
1	7	2	4
8	6	3	5

All possible configurations are available using the three basic transformations.

You are to write a program that computes a minimal sequence of basic transformations that
transforms the initial configuration above to a specific target configuration.

PROGRAM NAME: msquare

INPUT FORMAT

A single line with eight space-separated integers (a permutation of (1..8)) that are the
target configuration.

SAMPLE INPUT (file msquare.in)

2 6 8 4 5 7 3 1 

OUTPUT FORMAT

Line 1:	A single integer that is the length of the shortest transformation sequence.
Line 2:	 The lexically earliest string of transformations expressed as a string of characters,
60 per line except possibly the last line.

SAMPLE OUTPUT (file msquare.out)

7
BCABCCB

 */

import java.io.*;
import java.util.*;

public class msquare {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("msquare.in"));
		int[] target = new int[8];
		StringTokenizer st = new StringTokenizer(read.readLine());
		for(int i = 0; i < target.length; i++){
			target[i] = Integer.parseInt(st.nextToken());
		}
		read.close();
		
		Queue<MagicSquare> boards = new LinkedList<MagicSquare>();
		Map<MagicSquare, Boolean> visited = new TreeMap<MagicSquare, Boolean>();
		int[] startPos = new int[8];
		for(int i = 0; i < startPos.length; i++){
			startPos[i] = i + 1;
		}
		MagicSquare startSquare = new MagicSquare(startPos, "");
		boards.add(startSquare);
		visited.put(startSquare, true);
		if(Arrays.equals(target, startPos)){
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("msquare.out")));
			out.println(0);
			System.out.println(0);
			out.println();
			out.close();
			System.exit(0);
		}
		
		String ans = null;
		while(true){
			
//			System.out.println(boards);
			MagicSquare dq = boards.remove();
			MagicSquare a = dq.moveA();
			if(!visited.containsKey(a)){
				if(Arrays.equals(a.board, target)){
					ans = a.path;
					break;
				}
				visited.put(a, true);
				boards.add(a);
			}
			MagicSquare b = dq.moveB();
			if(!visited.containsKey(b)){
				if(Arrays.equals(b.board, target)){
					ans = b.path;
					break;
				}
				visited.put(b, true);
				boards.add(b);
			}
			MagicSquare c = dq.moveC();
			if(!visited.containsKey(c)){
				if(Arrays.equals(c.board, target)){
					ans = c.path;
					break;
				}
				visited.put(c, true);
				boards.add(c);
			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("msquare.out")));
		out.println(ans.length());
		System.out.println(ans.length());
		for(int i = 0; i <= ans.length() / 60; i++){
			System.out.println(ans.substring(i * 60, Math.min((i + 1) * 60, ans.length())));
			out.println(ans.substring(i * 60, Math.min((i + 1) * 60, ans.length())));
		}
		out.close();
		System.exit(0);
		
	}
	
}

class MagicSquare implements Comparable<MagicSquare>{
	
	int[] board;
	String path;
	
	public MagicSquare(int[] rect, String s){
		
		board = rect;
		path = s;
		
	}
	
	public MagicSquare moveA(){
		
		int[] rect = Arrays.copyOf(board, board.length);
		for(int i = 0; i < rect.length / 2; i++){
			int temp = rect[i];
			rect[i] = rect[rect.length - i - 1];
			rect[rect.length - i - 1] = temp;
		}
		return new MagicSquare(rect, path + "A");
		
	}
	
	public MagicSquare moveB(){
		
		int[] rect = Arrays.copyOf(board, board.length);
		int temp = rect[3];
		for(int i = 3; i >= 1; i--){
			rect[i] = rect[i - 1];
		}
		rect[0] = temp;
		temp = rect[4];
		for(int i = 4; i <= 6; i++){
			rect[i] = rect[i + 1];
		}
		rect[7] = temp;
		return new MagicSquare(rect, path + "B");
		
	}
	
	public MagicSquare moveC(){
		
		int[] rect = Arrays.copyOf(board, board.length);
		int temp = rect[1];
		rect[1] = rect[6];
		rect[6] = rect[5];
		rect[5] = rect[2];
		rect[2] = temp;
		return new MagicSquare(rect, path + "C");
		
	}
	
	public int compareTo(MagicSquare other){
		
		if(board[0] != other.board[0])
			return board[0] - other.board[0];
		if(board[1] != other.board[1])
			return board[1] - other.board[1];
		if(board[2] != other.board[2])
			return board[2] - other.board[2];
		if(board[3] != other.board[3])
			return board[3] - other.board[3];
		if(board[4] != other.board[4])
			return board[4] - other.board[4];
		if(board[5] != other.board[5])
			return board[5] - other.board[5];
		if(board[6] != other.board[6])
			return board[6] - other.board[6];
		return board[7] - other.board[7];
		
		
	}
	
	public String toString(){
		
		return "Board: " + Arrays.toString(board) + "\tPath: " + path;
		
	}
	
}
