/*
	ID: ayc22032
LANG: JAVA
TASK: ttwo
 */

/*

The Tamworth Two
BIO '98 - Richard Forster

A pair of cows is loose somewhere in the forest. Farmer John is lending his expertise to their capture. Your
task is to model their behavior.

The chase takes place on a 10 by 10 planar grid. Squares can be empty or they can contain:

an obstacle,
the cows (who always travel together), or
Farmer John.
The cows and Farmer John can occupy the same square (when they `meet') but neither the cows nor Farmer John
can share a square with an obstacle.
Each square is
represented
as follows:

. Empty square
* Obstacle
C Cows
F Farmer
Here is a sample grid:
*...*.....
......*...
...*...*..
..........
...*.F....
*.....*...
...*......
..C......*
...*.*....
.*.*......
The cows wander around the grid in a fixed way. Each minute, they either move forward or rotate. Normally,
they move one square in the direction they are facing. If there is an obstacle in the way or they would leave
the board by walking `forward', then they spend the entire minute rotating 90 degrees clockwise.

Farmer John, wise in the ways of cows, moves in exactly the same way.

The farmer and the cows can be considered to move simultaneously during each minute. If the farmer and the
cows pass each other while moving, they are not considered to have met. The chase ends when Farmer John and
the cows occupy the same square at the end of a minute.

Read a ten-line grid that represents the initial state of the cows, Farmer John, and obstacles. Each of the
ten lines contains exactly ten characters using the coding above. There is guaranteed to be only one farmer
and one pair of cows. The cows and Farmer John will not initially be on the same square.

Calculate the number of minutes until the cows and Farmer John meet. Assume both the cows and farmer begin
the simulation facing in the `north' direction. Print 0 if they will never meet.

PROGRAM NAME: ttwo

INPUT FORMAT

Lines 1-10:	Ten lines of ten characters each, as explained above

SAMPLE INPUT (file ttwo.in)

*...*.....
......*...
...*...*..
..........
...*.F....
*.....*...
...*......
..C......*
...*.*....
.*.*......

OUTPUT FORMAT

A single line with the integer number of minutes until Farmer John and the cows meet. Print 0 if they will
never meet.

SAMPLE OUTPUT (file ttwo.out)

49

 */

import java.io.*;
import java.util.*;

public class ttwo {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("ttwo.in"));
		boolean[][] forest = new boolean[10][10];
		int[][] ppl = new int[2][3];
		int ind = 0;
		for(int i = 0; i < forest.length; i++){
			String in = read.readLine();
			for(int j = 0; j < in.length(); j++){
				char c = in.charAt(j);
				if(c == '*')
					forest[i][j] = true;
				else if(c == 'C'){
					ppl[ind][0] = i;
					ppl[ind][1] = j;
				} else if(c == 'F'){
					ppl[1][0] = i;
					ppl[1][1] = j;
				}
			}
		}
		read.close();
		//original with booleans:
/*		boolean[][] locs = new boolean[3][400];
		int time = 0;
		while(true){
//			for(int i = 0; i < ppl.length; i++){
//				for(int j = 0; j < ppl[i].length; j++){
//					System.out.print(ppl[i][j] + " ");
//				}
//				System.out.println();
//			}
//			System.out.println();
			boolean same = true;
			for(int i = 0; i < ppl.length; i++){
				int index =  100 * ppl[i][2] + 10 * ppl[i][0] + ppl[i][1];
				if(!locs[i][index]){
					same = false;
					locs[i][index] = true;
				}
			}
			if(same){
				time = 0;
				
				break;
			}
			move(ppl, forest);
			time++;
			if(ppl[0][0] == ppl[1][0] && ppl[0][1] == ppl[1][1])
				break;
		}*/
		
		//version 2 with integers and frequencies
		int[][] locs = new int[2][400];
		for(int i = 0; i < locs.length; i++){
			for(int j = 0; j < locs[i].length; j++){
				locs[i][j] = -1;
			}
		}
		int time = 0;
		int[] freqs = new int[2];
		freqs[0] = -1;
		freqs[1] = -1;
		int lim = Integer.MAX_VALUE;
		while(time < lim){
//			System.out.println(freqs[0] +  " " + freqs[1]);
//			for (int i = 0; i < ppl.length; i++) {
//				for (int j = 0; j < ppl[i].length; j++) {
//					System.out.print(ppl[i][j] + " ");
//				}
//				System.out.println();
//			}
//			System.out.println(lim);
//			System.out.println();
			for(int i = 0; i < ppl.length; i++){
				int index =  100 * ppl[i][2] + 10 * ppl[i][0] + ppl[i][1];
				if(locs[i][index] >= 0){
					freqs[i] = time - locs[i][index];
					if(freqs[0] >= 0 && freqs[1] >= 0 && lim == Integer.MAX_VALUE)
						lim = time + freqs[0] * freqs[1];
				}
				locs[i][index] = time;
		
			}
			move(ppl, forest);
//			for (int i = 0; i < ppl.length; i++) {
//				for (int j = 0; j < ppl[i].length; j++) {
//					System.out.print(ppl[i][j] + " ");
//				}
//				System.out.println();
//			}
//			System.out.println(lim);
//			System.out.println("------------------------------------------------------");
			time++;
			if(ppl[0][0] == ppl[1][0] && ppl[0][1] == ppl[1][1])
				break;
		}
		if(time == lim)
			time = 0;
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ttwo.out")));
		out.println(time);
		System.out.println(time);
		out.close();
		System.exit(0);
		
	}
	
	private static void move(int[][] ppl, boolean[][] forest){
		
		for(int i = 0; i < ppl.length; i++){
			int nX = ppl[i][0];
			int nY = ppl[i][1];
			switch(ppl[i][2]){
			case 0:
				nX--;
				break;
			case 1:
				nY++;
				break;
			case 2:
				nX++;
				break;
			case 3:
				nY--;
				break;
			}
			if(nX >= forest.length || nY >= forest.length || nX < 0 || nY < 0 || forest[nX][nY]){
				ppl[i][2] += 1;
				ppl[i][2] %= 4;
			} else {
				ppl[i][0] = nX;
				ppl[i][1] = nY;
			}
		}
		
	}
	
	
	
}





