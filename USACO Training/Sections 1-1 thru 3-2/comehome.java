/*
ID: ayc22032
LANG: JAVA
TASK: comehome
 */

/*

Bessie Come Home
Kolstad & Burch

It's dinner time, and the cows are out in their separate pastures. Farmer John rings the bell so they will
start walking to the barn. Your job is to figure out which one cow gets to the barn first (the supplied test
data will always have exactly one fastest cow).

Between milkings, each cow is located in her own pasture, though some pastures have no cows in them. Each
pasture is connected by a path to one or more other pastures (potentially including itself). Sometimes, two
(potentially self-same) pastures are connected by more than one path. One or more of the pastures has a path
to the barn. Thus, all cows have a path to the barn and they always know the shortest path. Of course, cows
can go either direction on a path and they all walk at the same speed.

The pastures are labeled `a'..`z' and `A'..`Y'. One cow is in each pasture labeled with a capital letter. No
cow is in a pasture labeled with a lower case letter. The barn's label is `Z'; no cows are in the barn, though.

PROGRAM NAME: comehome

INPUT FORMAT

Line 1:	 Integer P (1 <= P <= 10000) the number of paths that interconnect the pastures (and the barn)
Line 2..P+1:	Space separated, two letters and an integer: the names of the interconnected pastures/barn and
the distance between them (1 <= distance <= 1000)

SAMPLE INPUT (file comehome.in)

5
A d 6
B d 3
C e 9
d Z 8
e Z 3

OUTPUT FORMAT

A single line containing two items: the capital letter name of the pasture of the cow that arrives first back at
the barn, the length of the path followed by that cow.

SAMPLE OUTPUT (file comehome.out)

B 11

 */

import java.io.*;
import java.util.*;

public class comehome {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("comehome.in"));
		int len = Integer.parseInt(read.readLine());
		int[][] g = new int[52][52];
		for(int i = 0; i < g.length; i++){
			for(int j = 0; j < g[i].length; j++){
				if(i != j)
					g[i][j] = -1;
			}
		}
		for(int i = 0; i < len; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			char a = st.nextToken().charAt(0);
			int x = a - 'A';
			if(x > 25){
				x = 26 + a - 'a';
			}
			a = st.nextToken().charAt(0);
			int y = a - 'A';
			if(y > 25){
				y = 26 + a - 'a';
			}
			int d = Integer.parseInt(st.nextToken());
			if(g[x][y] < 0 || d < g[x][y])
				g[x][y] = d;
			g[y][x] = g[x][y];
		}
		read.close();
		
//		for(int i = 0; i < g.length; i++){
//			System.out.print(i + ":\t");
//			for(int j = 0; j < g[i].length; j++){
//				System.out.print(g[i][j] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
		
		for(int v = 0; v < g.length; v++){
			for(int i = 0; i < g.length; i++){
				if(g[i][v] > 0){
					for(int j = 0; j < g.length; j++){
						if(g[v][j] > 0 && (g[i][j] < 0 || g[i][v] + g[v][j] < g[i][j]))
							g[i][j] = g[i][v] + g[v][j];
					}
				}
			}
		}
		
//		for(int i = 0; i < g.length; i++){
//			System.out.print(i + ":\t");
//			for(int j = 0; j < g[i].length; j++){
//				System.out.print(g[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		int min = 0;
		for(int i = 1; i < 25; i++){
			if(g[min][25] < 0 || (g[i][25] < g[min][25] && g[i][25] >= 0))
				min = i;
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("comehome.out")));
		out.println((char)(min + 'A') + " " + g[min][25]);
		System.out.println((char)(min + 'A') + " " + g[min][25]);
		out.close();
		System.exit(0);
		
	}
	
}
