/*
ID: ayc22032
LANG: JAVA
TASK: range
*/

/*
Home on the Range
Farmer John grazes his cows on a large, square field N (2 <= N <= 250) miles on a side (because,
for some reason, his cows will only graze on precisely square land segments). Regrettably, the
cows have ravaged some of the land (always in 1 mile square increments). FJ needs to map the
remaining squares (at least 2x2 on a side) on which his cows can graze (in these larger squares,
no 1x1 mile segments are ravaged).

Your task is to count up all the various square grazing areas within the supplied dataset and
report the number of square grazing areas (of sizes >= 2x2) remaining. Of course, grazing areas
may overlap for purposes of this report.

PROGRAM NAME: range

INPUT FORMAT

Line 1:	N, the number of miles on each side of the field.
Line 2..N+1:	N characters with no spaces. 0 represents "ravaged for that block; 1 represents
"ready to eat".

SAMPLE INPUT (file range.in)

6
101111
001111
111111
001111
101101
111001

OUTPUT FORMAT

Potentially several lines with the size of the square and the number of such squares that exist.
Order them in ascending order from smallest to largest size.

SAMPLE OUTPUT (file range.out)

2 10
3 4
4 1  

 */

import java.io.*;
import java.util.*;

public class range {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("range.in"));
		int N = Integer.parseInt(read.readLine());
		boolean[][] grid = new boolean[N][N];
		for(int i = 0; i < N; i++){
			String s = read.readLine();
			for(int j = 0; j < N; j++){
				grid[i][j] = s.charAt(j) == '1';
			}
		}
		read.close();
		
		int[] counts = new int[N + 1];
		for(int s = 2; s <= N; s++){
			for(int i = 0; i < N + 1 - s; i++){
				for(int j = 0; j < N + 1 - s; j++){
					grid[i][j] = grid[i][j] && grid[i][j + 1] && grid[i + 1][j] && grid[i + 1][j + 1];
					if(grid[i][j])
						counts[s]++;
				}
			}
			if(counts[s] == 0)
				break;
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("range.out")));
		for(int s = 2; s <= N && counts[s] > 0; s++){
			out.println(s + " " + counts[s]);
//			System.out.println(s + " " + counts[s]);
		}
		out.close();
		System.exit(0);
		
	}
	
}
