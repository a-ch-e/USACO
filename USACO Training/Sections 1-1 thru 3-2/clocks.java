/*
ID: ayc22032
LANG: JAVA
PROG: clocks
*/

/*
The Clocks
IOI'94 - Day 2
Consider nine clocks arranged in a 3x3 array thusly:

|-------|    |-------|    |-------|    
|       |    |       |    |   |   |    
|---O   |    |---O   |    |   O   |          
|       |    |       |    |       |           
|-------|    |-------|    |-------|    
    A            B            C

|-------|    |-------|    |-------|
|       |    |       |    |       |
|   O   |    |   O   |    |   O   |
|   |   |    |   |   |    |   |   |
|-------|    |-------|    |-------|
    D            E            F

|-------|    |-------|    |-------|
|       |    |       |    |       |
|   O   |    |   O---|    |   O   |
|   |   |    |       |    |   |   |
|-------|    |-------|    |-------|
    G            H            I

The goal is to find a minimal sequence of moves to return all the dials to 12 o'clock. Nine different ways to turn the dials on the clocks are supplied via a table below; each way is called a move. Select for each move a number 1 through 9 which will cause the dials of the affected clocks (see next table) to be turned 90 degrees clockwise.
Move 	Affected clocks
1 	ABDE
2 	ABC
3 	BCEF
4 	ADG
5 	BDEFH
6 	CFI
7 	DEGH
8 	GHI
9 	EFHI
Example
Each number represents a time accoring to following table:

9 9 12       9 12 12       9 12 12        12 12 12      12 12 12 
6 6 6  5 ->  9  9  9  8->  9  9  9  4 ->  12  9  9  9-> 12 12 12 
6 3 6        6  6  6       9  9  9        12  9  9      12 12 12 

[But this might or might not be the `correct' answer; see below.]
PROGRAM NAME: clocks
INPUT FORMAT
Lines 1-3: 	Three lines of three space-separated numbers; each number represents the start time of one clock, 3, 6, 9, or 12. The ordering of the numbers corresponds to the first example above.
SAMPLE INPUT (file clocks.in)

9 9 12
6 6 6
6 3 6

OUTPUT FORMAT

A single line that contains a space separated list of the shortest sequence of moves (designated by numbers) which returns all the clocks to 12:00. If there is more than one solution, print the one which gives the lowest number when the moves are concatenated (e.g., 5 2 4 6 < 9 3 1 1).
SAMPLE OUTPUT (file clocks.out)

4 5 8 9
*/

import java.io.*;
import java.util.*;

public class clocks{
	
	private static boolean[] locs;
	
	public static void main(String[] args) throws IOException{
		
		long a = System.currentTimeMillis();
		BufferedReader read = new BufferedReader(new FileReader("clocks.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("clocks.out")));
		int[][] clocks = new int[3][3];
		for(int i = 0; i < clocks.length; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			for(int j = 0; j < clocks[i].length; j++)
				clocks[i][j] = Integer.parseInt(st.nextToken());
		}
		read.close();
		
//		clocks[0][0] = 12;
//		clocks[0][1] = 12;
//		clocks[0][2] = 12;
//		clocks[1][0] = 12;
//		clocks[1][1] = 9;
//		clocks[1][2] = 9;
//		clocks[2][0] = 3;
//		clocks[2][1] = 12;
//		clocks[2][2] = 12;
		
// 		Move[] moves = new Move[10];
// 		moves[1] = new Move1();
// 		moves[2] = new Move2();
// 		moves[3] = new Move3();
// 		moves[4] = new Move4();
// 		moves[5] = new Move5();
// 		moves[6] = new Move6();
// 		moves[7] = new Move7();
// 		moves[8] = new Move8();
// 		moves[9] = new Move9();
		locs = new boolean[(int)Math.pow(4, 9)];
		
// 		Queue<ArrayList<Integer>> paths = new LinkedList<ArrayList<Integer>>();
//		Queue<int[][]> grids = new LinkedList<int[][]>();
		ArrayList<Integer> ans = new ArrayList<Integer>();
// 		paths.add(ans);
//		grids.add(clocks);
//		boolean c = true;
// 		while(!paths.isEmpty() && c){
// 			ArrayList<Integer> dq = paths.remove();
//			int[][] temp = grids.remove();
// 			for(int i = 1; i <= 9; i++){
//				ArrayList<Integer> next = (ArrayList<Integer>)dq.clone();
// 				next.add(i);
//				int[][] temp2 = copyOf(temp);
//				Move.move(i, temp2);
//				if(works(temp2)){
//					c = false;
//					ans = next;
//					break;
//				}
//				if(locs[toInt(temp2)]) continue;
//				locs[toInt(temp2)] = true;
// 				paths.add(next);
//				grids.add(temp2);
// 			}
// 		}
		
//		for(int i = 1; i <= 27; i++){
//			System.out.println(i + "--------------------------------------------------------------------");
//			idDepth(ans, i, clocks, out);
//		}
		
//		int count = 0;
//		Queue<ClockWatcher> curr = new LinkedList<ClockWatcher>();
//		Queue<ClockWatcher> next = new LinkedList<ClockWatcher>();
//		curr.add(new ClockWatcher(ans, copyOf(clocks)));
//		while(!curr.isEmpty()){
//			while(!curr.isEmpty()){
//				ClockWatcher dq = curr.remove();
//				int k = 1;
//				if(dq.path.size() != 0)
//					k = dq.path.get(dq.path.size() - 1);
//				if(dq.path.size() > 2 && dq.path.get(dq.path.size() - 3) == k)
//					k++;
//				for(int i = k; i <= 9; i++){
//					ArrayList<Integer> temp = (ArrayList<Integer>)dq.path.clone();
//					int[][] grid = copyOf(dq.clocks);
//					Move.move(i, grid);
//					if(works(grid)){
//						for(int j = 0; j < temp.size(); j++)
//							out.print(temp.get(j) + " ");
//						out.println(i);
//						out.close();
//						System.out.println((System.currentTimeMillis() - a));
//						System.out.println(count);
//						System.exit(0);
//					}
//					temp.add(i);
//					next.add(new ClockWatcher(temp, grid));
//					count++;
//				}
//			}
//			curr = next;
//			next = new LinkedList<ClockWatcher>();
//		}
		ans = idDepth2(clocks, 1);
		out.print(ans.get(0));
		for(int j = 1; j < ans.size(); j++)
			out.print(" " + ans.get(j));
		out.println();
		out.close();
		System.out.println((System.currentTimeMillis() - a));
		System.exit(0);
		
	}
	
	private static ArrayList<Integer> idDepth2(int[][] clocks, int level){
		
		ArrayList<Integer> ans = new ArrayList<Integer>();
		if(level > 9){
//			System.out.println("!");
			return ans;
		}
		for(int i = 0; i < 4; i++){
			int[][] temp = copyOf(clocks);
			for(int j = 0; j < i; j++)
				Move.move(level, temp);
			ArrayList<Integer> comp = new ArrayList<Integer>();
			if(!works(temp))
				comp = idDepth2(temp, level + 1);
//			if(level == 9 && i == 1){
//				print(temp);
//				System.out.println();
//			}
			for(int j : comp)
				Move.move(j, temp);
			for(int j = 0; j < i; j++)
				comp.add(0, level);
			if((ans.size() == 0 || comp.size() <= ans.size()) && works(temp))
				ans = comp;
		}
//		if(level == 9 && ans.size() > 0) System.out.println(ans);
		return ans;
		
	}

	
	private static void idDepth(ArrayList<Integer> soFar, int depth, int[][] clocks, PrintWriter out) throws IOException{
		for(int i = 1; i <= 9 && depth > 0; i++){
			int[][] temp = copyOf(clocks);
			Move.move(i, temp);
			print(temp);
			System.out.println();
			if(works(temp)){
				for(int j = 0; j < soFar.size(); j++){
					out.print(soFar.get(j) + " ");
				}
				out.println(i);
				out.close();
				System.exit(0);
			}
			if(depth == 0 && locs[toInt(temp)]) continue;
			locs[toInt(temp)] = true;
			soFar.add(i);
			idDepth(soFar, depth - 1, temp, out);
			soFar.remove(soFar.size() - 1);
		}
	}
	
// 	private static void idSearch(ArrayList<Integer> soFar, Move[] moves, int depth, int[][] clocks, PrintWriter out) throws IOException{
// 		
// 		int[][] temp = copyOf(clocks);
// 		for(int i : soFar)
// 			moves[i].move(temp);
// 		if(works(temp)){
// 			out.print(soFar.get(0));
// 			for(int i = 1; i < soFar.size(); i++)
// 				out.print(" " + soFar.get(i));
// 			out.println();
// 			out.close();
// 			System.exit(0);
// 		}
// 		if(depth == 0) return;
// 		for(int i = 1; i < moves.length; i++){
// 			soFar.add(i);
// 			idSearch(soFar, moves, depth - 1, clocks, out);
// 			soFar.remove(soFar.size() - 1);
// 		}
// 		
// 	}
	
 	private static void print(int[][] clocks){
 		for(int i = 0; i < clocks.length; i++){
 			for(int j = 0; j < clocks[i].length; j++)
 				System.out.print(clocks[i][j] + " ");
 			System.out.println();
 		}
 	}
	
	private static int[][] copyOf(int[][] mat){
		int[][] copy = new int[mat.length][mat[0].length];
		for(int i = 0; i < copy.length; i++)
			for(int j = 0; j < copy[i].length; j++)
				copy[i][j] = mat[i][j];
		return copy;
	}
	
	private static boolean works(int[][] clocks){
		for(int i = 0; i < clocks.length; i++){
			for(int j = 0; j < clocks[i].length; j++){
				if(clocks[i][j] != 12) return false;
			}
		}
		return true;
	}
	
	private static int toInt(int[][] clocks){
		int ans = 0;
		for(int i = 0; i < clocks.length; i++){
			for(int j = 0; j < clocks[i].length; j++){
				int temp = (clocks[i][j] % 12) / 3;
				ans += Math.pow(4, 8 - 3 * i - j) * temp;
			}
		}
		return ans;
	}
	
}

 class ClockWatcher{
 	
 	ArrayList<Integer> path;
 	int[][] clocks;
 	
 	public ClockWatcher(ArrayList<Integer> x, int[][] y){
 		
 		path = x;
 		clocks = y;
 		
 	}
 	
 	public String toString(){
 		return path.toString();
 	}
 	
 }

// abstract class Move{
// 	public abstract void move(int[][] clocks);
// 	public abstract String toString();
// }

class Move{
	public static void move(int a, int[][] clocks){
		switch(a){
			case 1: move1(clocks);
			break;
			case 2: move2(clocks);
			break;
			case 3: move3(clocks);
			break;
			case 4: move4(clocks);
			break;
			case 5: move5(clocks);
			break;
			case 6: move6(clocks);
			break;
			case 7: move7(clocks);
			break;
			case 8: move8(clocks);
			break;
			default: move9(clocks);
		}
	}
	
	private static void move1(int[][] clocks){
		clocks[0][0] = (clocks[0][0] - 21) % 12 + 12;
		clocks[0][1] = (clocks[0][1] - 21) % 12 + 12;
		clocks[1][0] = (clocks[1][0] - 21) % 12 + 12;
		clocks[1][1] = (clocks[1][1] - 21) % 12 + 12;
	}
	private static void move2(int[][] clocks){
		for(int i = 0; i < clocks.length; i++)
			clocks[0][i] = (clocks[0][i] - 21) % 12 + 12;
	}
	private static void move3(int[][] clocks){
		clocks[0][1] = (clocks[0][1] - 21) % 12 + 12;
		clocks[0][2] = (clocks[0][2] - 21) % 12 + 12;
		clocks[1][1] = (clocks[1][1] - 21) % 12 + 12;
		clocks[1][2] = (clocks[1][2] - 21) % 12 + 12;
	}
	private static void move4(int[][] clocks){
		for(int i = 0; i < clocks.length; i++)
			clocks[i][0] = (clocks[i][0] - 21) % 12 + 12;
	}
	private static void move5(int[][] clocks){
		clocks[0][1] = (clocks[0][1] - 21) % 12 + 12;
		clocks[1][0] = (clocks[1][0] - 21) % 12 + 12;
		clocks[1][1] = (clocks[1][1] - 21) % 12 + 12;
		clocks[1][2] = (clocks[1][2] - 21) % 12 + 12;
		clocks[2][1] = (clocks[2][1] - 21) % 12 + 12;
	}
	private static void move6(int[][] clocks){
		for(int i = 0; i < clocks.length; i++)
			clocks[i][2] = (clocks[i][2] - 21) % 12 + 12;
	}
	private static void move7(int[][] clocks){
		clocks[1][0] = (clocks[1][0] - 21) % 12 + 12;
		clocks[1][1] = (clocks[1][1] - 21) % 12 + 12;
		clocks[2][0] = (clocks[2][0] - 21) % 12 + 12;
		clocks[2][1] = (clocks[2][1] - 21) % 12 + 12;
	}
	private static void move8(int[][] clocks){
		for(int i = 0; i < clocks.length; i++)
			clocks[2][i] = (clocks[2][i] - 21) % 12 + 12;
	}
	private static void move9(int[][] clocks){
		clocks[1][1] = (clocks[1][1] - 21) % 12 + 12;
		clocks[1][2] = (clocks[1][2] - 21) % 12 + 12;
		clocks[2][1] = (clocks[2][1] - 21) % 12 + 12;
		clocks[2][2] = (clocks[2][2] - 21) % 12 + 12;
	}
	
}

// class Move1 extends Move{
// 	public String toString(){
// 		return "1";
// 	}
// 	public void move(int[][] clocks){
// 		clocks[0][0] = (clocks[0][0] - 21) % 12 + 12;
// 		clocks[0][1] = (clocks[0][1] - 21) % 12 + 12;
// 		clocks[1][0] = (clocks[1][0] - 21) % 12 + 12;
// 		clocks[1][1] = (clocks[1][1] - 21) % 12 + 12;
// 	}
// }
// 
// class Move2 extends Move{
// 	public String toString(){
// 		return "2";
// 	}
// 	public void move(int[][] clocks){
// 		for(int i = 0; i < clocks.length; i++)
// 			clocks[0][i] = (clocks[0][i] - 21) % 12 + 12;
// 	}
// }
// 
// class Move3 extends Move{
// 	public String toString(){
// 		return "3";
// 	}
// 	public void move(int[][] clocks){
// 		clocks[0][1] = (clocks[0][1] - 21) % 12 + 12;
// 		clocks[0][2] = (clocks[0][2] - 21) % 12 + 12;
// 		clocks[1][1] = (clocks[1][1] - 21) % 12 + 12;
// 		clocks[1][2] = (clocks[1][2] - 21) % 12 + 12;
// 	}
// }
// 
// class Move4 extends Move{
// 	public String toString(){
// 		return "4";
// 	}
// 	public void move(int[][] clocks){
// 		for(int i = 0; i < clocks.length; i++)
// 			clocks[i][0] = (clocks[i][0] - 21) % 12 + 12;
// 	}
// }
// 
// class Move5 extends Move{
// 	public String toString(){
// 		return "5";
// 	}
// 	public void move(int[][] clocks){
// 		clocks[0][1] = (clocks[0][1] - 21) % 12 + 12;
// 		clocks[1][0] = (clocks[1][0] - 21) % 12 + 12;
// 		clocks[1][1] = (clocks[1][1] - 21) % 12 + 12;
// 		clocks[1][2] = (clocks[1][2] - 21) % 12 + 12;
// 		clocks[2][1] = (clocks[2][1] - 21) % 12 + 12;
// 	}
// }
// 
// class Move6 extends Move{
// 	public String toString(){
// 		return "6";
// 	}
// 	public void move(int[][] clocks){
// 		for(int i = 0; i < clocks.length; i++)
// 			clocks[i][2] = (clocks[i][2] - 21) % 12 + 12;
// 	}
// }
// 
// class Move7 extends Move{
// 	public String toString(){
// 		return "7";
// 	}
// 	public void move(int[][] clocks){
// 		clocks[1][0] = (clocks[1][0] - 21) % 12 + 12;
// 		clocks[1][1] = (clocks[1][1] - 21) % 12 + 12;
// 		clocks[2][0] = (clocks[2][0] - 21) % 12 + 12;
// 		clocks[2][1] = (clocks[2][1] - 21) % 12 + 12;
// 	}
// }
// 
// class Move8 extends Move{
// 	public String toString(){
// 		return "8";
// 	}
// 	public void move(int[][] clocks){
// 		for(int i = 0; i < clocks.length; i++)
// 			clocks[2][i] = (clocks[2][i] - 21) % 12 + 12;
// 	}
// }
// 
// class Move9 extends Move{
// 	public String toString(){
// 		return "9";
// 	}
// 	public void move(int[][] clocks){
// 		clocks[1][1] = (clocks[1][1] - 21) % 12 + 12;
// 		clocks[1][2] = (clocks[1][2] - 21) % 12 + 12;
// 		clocks[2][1] = (clocks[2][1] - 21) % 12 + 12;
// 		clocks[2][2] = (clocks[2][2] - 21) % 12 + 12;
// 	}
// }