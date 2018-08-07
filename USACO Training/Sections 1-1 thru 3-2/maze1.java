/*
ID: ayc22032
LANG: JAVA
TASK: maze1
 */

/*
Overfencing
Kolstad and Schrijvers

Farmer John went crazy and created a huge maze of fences out in a field. Happily,
he left out two fence segments on the edges, and thus created two "exits" for the
maze. Even more happily, the maze he created by this overfencing experience is a
`perfect' maze: you can find a way out of the maze from any point inside it.

Given W (1 <= W <= 38), the width of the maze; H (1 <= H <= 100), the height of
the maze; 2*H+1 lines with width 2*W+1 characters that represent the maze in a
format like that shown later - then calculate the number of steps required to
exit the maze from the `worst' point in the maze (the point that is `farther'
from either exit even when walking optimally to the closest exit). Of course,
cows walk only parallel or perpendicular to the x-y axes; they do not walk on
a diagonal. Each move to a new square counts as a single unit of distance
(including the move "out" of the maze.

Here's what one particular W=5, H=3 maze looks like:

+-+-+-+-+-+
|         |
+-+ +-+ + +
|     | | |
+ +-+-+ + +
| |     |  
+-+ +-+-+-+
Fenceposts appear only in odd numbered rows and and odd numbered columns (as
in the example). The format should be obvious and self explanatory. Each maze
has exactly two blank walls on the outside for exiting.

PROGRAM NAME: maze1

INPUT FORMAT

Line 1:	W and H, space separated
Lines 2 through 2*H+2:	2*W+1 characters that represent the maze

SAMPLE INPUT (file maze1.in)

5 3
+-+-+-+-+-+
|         |
+-+ +-+ + +
|     | | |
+ +-+-+ + +
| |     |  
+-+ +-+-+-+

OUTPUT FORMAT

A single integer on a single output line. The integer specifies the minimal number
of steps that guarantee a cow can exit the maze from any possible point inside the maze.

SAMPLE OUTPUT (file maze1.out)

9
The lower left-hand corner is *nine* steps from the closest exit. 

*/

import java.io.*;
import java.util.*;

public class maze1 {
	
	private static int cols;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader read = new BufferedReader(new FileReader("maze1.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		cols = Integer.parseInt(st.nextToken());
		int rows = Integer.parseInt(st.nextToken());
		boolean[][] wallsH = new boolean[rows + 1][cols];
		boolean[][] wallsV = new boolean[rows][cols + 1];
		for(int i = 0; i < 2 * rows + 1; i++){
			String s = read.readLine();
			if(i % 2 == 0){
				for(int j = 0; j < cols; j++){
					if(s.charAt(2 * j + 1) == '-')
						wallsH[i / 2][j] = true;
				}
			} else {
				for(int j = 0; j <= cols; j++){
					if(s.charAt(2 * j) == '|')
						wallsV[i / 2][j] = true;
				}
			}
		}
		read.close();
		//initial solution with floyd-warshall
/*		int[] exits = new int[2];
		int ind = 0;
		int[][] dist = new int[rows * cols][rows * cols];
		for(int i = 0; i < dist.length; i++){
			for(int j = 0; j < dist[i].length; j++){
				if(i != j)
					dist[i][j] = Integer.MAX_VALUE;
			}
		}
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				if((i == 0 && !wallsH[i][j]) || (j == cols - 1 && !wallsV[i][j + 1]) || (i == rows - 1 && !wallsH[i + 1][j]) || (j == 0 && !wallsV[i][j])){
					exits[ind] = i * cols + j;
					ind++;
				}
				if(i > 0 && !wallsH[i][j])
					dist[i * cols + j][(i - 1) * cols + j] = 1;
				if(j < cols - 1 && !wallsV[i][j + 1])
					dist[i * cols + j][i * cols + j + 1] = 1;
				if(i < rows - 1 && !wallsH[i + 1][j])
					dist[i * cols + j][(i + 1) * cols + j] = 1;
				if(j > 0 && !wallsV[i][j])
					dist[i * cols + j][i * cols + j - 1] = 1;
			}
		}
		if(ind == 1){
			exits[1] = exits[0];
		}
		
		for(int v = 0; v < dist.length; v++){
			for(int i = 0; i < dist.length; i++){
				if(dist[i][v] < Integer.MAX_VALUE){
					for(int j = 0; j < dist[i].length; j++){
						if(dist[v][j] < Integer.MAX_VALUE)
							dist[i][j] = Math.min(dist[v][j] + dist[i][v], dist[i][j]);;
					}
				}
			}
		}
//		for(int i = 0; i < dist.length; i++){
//			for(int j = 0; j < dist.length; j++){
//				System.out.print(dist[i][j] + " ");
//			}
//			System.out.println();
//		}
		int max = 0;
		for(int i = 0; i < dist.length; i++){
			int min = Math.min(dist[i][exits[1]], dist[i][exits[0]]);
			max = Math.max(max, min);
		}
		max++;*/
		
		int[][] exits = new int[2][2];
		int ind = 0;
		boolean[][] dist = new boolean[rows * cols][rows * cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				if((i == 0 && !wallsH[i][j]) || (j == cols - 1 && !wallsV[i][j + 1]) || (i == rows - 1 && !wallsH[i + 1][j]) || (j == 0 && !wallsV[i][j])){
					exits[ind][0] = i;
					exits[ind][1] = j;
					ind++;
				}
				if(i > 0 && !wallsH[i][j])
					dist[i * cols + j][(i - 1) * cols + j] = true;
				if(j < cols - 1 && !wallsV[i][j + 1])
					dist[i * cols + j][i * cols + j + 1] = true;
				if(i < rows - 1 && !wallsH[i + 1][j])
					dist[i * cols + j][(i + 1) * cols + j] = true;
				if(j > 0 && !wallsV[i][j])
					dist[i * cols + j][i * cols + j - 1] = true;
			}
		}
		if(ind == 1){
			exits[1] = exits[0];
		}
		
		int[][] lens = new int[2][dist.length];
		for(int i = 0; i < lens.length; i++){
			for(int j = 0; j < lens[i].length; j++){
				lens[i][j] = -1;
			}
		}
		for(int i = 0; i < lens.length; i++)
			floodFill(dist, exits[i][0], exits[i][1], 0, lens[i]);
//		for(int i = 0; i < rows; i++){
//			for(int j = 0; j < cols; j++){
//				System.out.print(lens[0][cols * i + j] + " ");
//			}
//			System.out.println();
//		}
		int max = 0;
		for(int i = 0; i < lens[0].length; i++){
			int min = Math.min(lens[0][i], lens[1][i]);
			max = Math.max(max, min);
		}
		max++;
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maze1.out")));
		System.out.println(max);
		out.println(max);
		out.close();
		System.exit(0);
		
	}
	
	private static void floodFill(boolean[][] g, int sX, int sY, int count, int[] dist){
		
		int x = sX * cols + sY;
		if(dist[x] < 0 || count < dist[x])
			dist[x] = count++;
		else
			return;
		if(x - cols >= 0 && g[x][x - cols])
			floodFill(g, sX - 1, sY, count, dist);
		if(x + 1 < g.length && g[x][x + 1])
			floodFill(g, sX, sY + 1, count, dist);
		if(x + cols < g.length && g[x][x + cols])
			floodFill(g, sX + 1, sY, count, dist);
		if(x - 1 >= 0 && g[x][x - 1])
			floodFill(g, sX, sY - 1, count, dist);
		
	}
	
}
//
//class MCell1{
//	boolean[] walls;
//	public MCell1(boolean n, boolean e, boolean s, boolean w){
//		
//		walls = new boolean[4];
//		walls[0] = n;
//		walls[1] = e;
//		walls[2] = s;
//		walls[3] = w;
//		
//	}
//	
//	public String toString(){
//		String s = "";
//		if(walls[0])
//			s += "1";
//		else
//			s += "0";
//		for(int i = 1; i < walls.length; i++){
//			s += " ";
//			if(walls[i])
//				s += "1";
//			else
//				s += "0";
//		}
//		return s;
//	}
//	
//}
