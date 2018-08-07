/*
Allen Cheng, ID ayc22032
USACO, January 2015
Gold 1: Cow Rectangles
 */
/*
Problem 1: Cow Rectangles [Nick Wu, 2015]

The locations of Farmer John's N cows (1 <= N <= 500) are described by
distinct points in the 2D plane.  The cows belong to two different
breeds: Holsteins and Guernseys.  Farmer John wants to build a
rectangular fence with sides parallel to the coordinate axes enclosing
only Holsteins, with no Guernseys (a cow counts as enclosed even if it
is on the boundary of the fence).  Among all such fences, Farmer John
wants to build a fence enclosing the maximum number of Holsteins.  And
among all these fences, Farmer John wants to build a fence of minimum
possible area.  Please determine this area.  A fence of zero width or
height is allowable.

INPUT: (file cowrect.in)

The first line of input contains N.  Each of the next N lines
describes a cow, and contains two integers and a character. The
integers indicate a point (x,y) (0 <= x, y <= 1000) at which the cow
is located. The character is H or G, indicating the cow's breed.  No
two cows are located at the same point, and there is always at least
one Holstein.

SAMPLE INPUT:

5
1 1 H
2 2 H
3 3 G
4 4 H
6 6 H

OUTPUT: (file cowrect.out)

Print two integers. The first line should contain the maximum number
of Holsteins that can be enclosed by a fence containing no Guernseys,
and second line should contain the minimum area enclosed by such a
fence.

SAMPLE OUTPUT:

2
1
 */

import java.io.*;
import java.util.*;

public class cowrect {
		
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("cowrect.in"));
		int N = Integer.parseInt(read.readLine());
		int[][] cowList = new int[N][2];
		boolean[] hs = new boolean[N];
		TreeSet<Coord> holsteins = new TreeSet<Coord>();
		int lX = Integer.MAX_VALUE; int uX = -1;
		int lY = Integer.MAX_VALUE; int uY = -1;
		for(int i = 0; i < N; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			cowList[i][0] = Integer.parseInt(st.nextToken());
			cowList[i][1] = Integer.parseInt(st.nextToken());
			if(st.nextToken().charAt(0) == 'H'){
				hs[i] = true;
			}
			lX = Math.min(lX, cowList[i][0]);
			uX = Math.max(uX, cowList[i][0]);
			lY = Math.min(lY, cowList[i][1]);
			uY = Math.max(uY, cowList[i][1]);
		}
		read.close();
		
		int dimX = uX - lX + 1; int dimY = uY - lY + 2;
		int[][] cows = new int[dimX][dimY];
		
		for(int i = 0; i < N; i++){
			cowList[i][0] -= lX;
			cowList[i][1] -= lY;
			cows[cowList[i][0]][cowList[i][1]] = -1;
			if(hs[i]){
				cows[cowList[i][0]][cowList[i][1]] = 1;
				holsteins.add(new Coord(cowList[i][0], cowList[i][1]));
			}
		}
		for(int i = 0; i < dimX; i++){
			cows[i][dimY - 1] = -1;
		}

		
		int x0 =  0; int y0 =  0;
		int x1 = -1; int y1 = -1;
		int numHs = 0; int area = Integer.MAX_VALUE;
		int[] width = new int[dimY];
		Stack<Integer> ys, ws;
		for(int x = dimX - 1; x >= 0; x--){
			ys = new Stack<Integer>();
			ws = new Stack<Integer>();
			for(int y = 0; y < dimY; y++){
				if(cows[x][y] == -1)
					width[y] = 0;
				else
					width[y]++;
			}
			int currW = 0;
			for(int y = 0; y < dimY; y++){
				if(width[y] > currW && width[y] > 0){
					ys.push(y);
					currW = width[y];
					ws.push(currW);
				}
//				System.out.println("(" + x + ", " + y + "): " + ws);
//				System.out.println(width[y]);
				while(width[y] < currW){
//					System.out.println(ws);
					y--;
					int yB = ys.pop();
//					System.out.println(yB);
//					System.out.println(tempW);
					int xR = currW + x - 1;
					
					int count = 0;
					int minX = Integer.MAX_VALUE; int minY = Integer.MAX_VALUE;
					int maxX = -1; int maxY = -1;
//					System.out.println(holsteins.subSet(new Coord(x, yB), true, new Coord(xR, y), true));
					for(Coord c : holsteins.subSet(new Coord(x, yB), true, new Coord(xR, y), true)){
						if(x <= c.x && c.x <= xR && yB <= c.y && c.y <= y){
							count++;
							minX = Math.min(minX, c.x);
							minY = Math.min(minY, c.y);
							maxX = Math.max(maxX, c.x);
							maxY = Math.max(maxY, c.y);
						}
					}
					if(count > 0){
						System.out.format("(%d, %d) to (%d, %d), count %d \n", x, yB, xR, y, count);
					}
					int temp = (maxX - minX) * (maxY - minY);
					if(count > numHs || (numHs > 0 && count == numHs && temp < area)){
						x0 = minX; x1 = maxX;
						y0 = minY; y1 = maxY;
						numHs = count;
						area = temp;
//						System.out.println(width[y]);
//						System.out.format("(%d, %d) to (%d, %d), count %d \n", x0, y0, x1, y1, numHs);
//						System.out.format("(%d, %d) to (%d, %d), count %d \n", x, yB, xR, y, numHs);
//						System.out.format("(%d, %d)\n", x, y);
					}
					y++;
					
					ws.pop();
					if(ws.isEmpty())
						break;
					currW = ws.peek();
				}
				currW = width[y];
				if(width[y] > 0 && (ws.isEmpty() || ws.peek() != currW)){
					ys.push(y);
					ws.push(width[y]);
				}
			}
		}
		System.out.format("(%d, %d) to (%d, %d), count %d \n", x0, y0, x1, y1, numHs);
		
		PrintWriter out = new PrintWriter(new FileWriter("cowrect.out"));
		out.println(numHs);
		out.println(area);
		System.out.println(numHs);
		System.out.println(area);
		out.close();
		System.exit(0);
		
		
	}
	
}

class Coord implements Comparable<Coord>{
	
	int x, y;
	
	public Coord(int a, int b){
		x = a;
		y = b;
	}
	
	public int compareTo(Coord other){
		if(this.x == other.x)
			return this.y - other.y;
		return this.x - other.x;
	}
	
	public String toString(){
		
		return String.format("(%d, %d)", x, y);
	}
	
}

/*
16
0 0 H
1 0 G
1 1 H
2 1 G
2 2 H
3 2 G
0 3 H
1 3 H
2 3 H
3 3 H
4 3 G
0 4 H
2 4 H
3 4 G
1 5 H
2 5 G
*/