/*
Allen Cheng, ID ayc22032
USACO, January 2016
Gold 2: Radio Contact
*/

/*
Farmer John has lost his favorite cow bell, and Bessie the cow has agreed to help him find it!
They both fan out and search the farm along different paths, but stay in contact via radio so
they can keep in touch with each-other. Unfortunately, the batteries in their radios are running
low, so they want to plan their movements so as to conserve power, by trying to stay always within
a short distance apart.

Farmer John starts at location (fx,fy) and plans to follow a path consisting of N steps, each of
which is either 'N' (north), 'E' (east), 'S' (south), or 'W' west. Bessie starts at location
(bx,by) and follows a similar path consisting of M steps. Both paths may share points in common.
At each time step, Farmer John can either stay put at his current location, or take one step
forward along his path, in whichever direction happens to be next (assuming he has not yet reached
the final location in his path). Bessie can make a similar choice. At each time step (excluding the
first step where they start at their initial locations), their radios consume energy equal to the
square of the distance between them.

Please help FJ and Bessie plan a joint movement strategy that will minimize the total amount of
energy consumed up to and including the final step where both of them first reach the final
locations on their respective paths.

INPUT FORMAT (file radio.in):

The first line of input contains N and M (1<=N,M<=1000). The second line contains integers fx and
fy, and the third line contains bx and by (0<=fx,fy,bx,by<=1000). The next line contains a string
of length N describing FJ's path, and the final line contains a string of length M describing
Bessie's path.

It is guaranteed that Farmer John and Bessie's coordinates are always in the range (0<=x,y<=1000)
throughout their journey. Note that East points in the positive x direction and North points
in the positive y direction.

OUTPUT FORMAT (file radio.out):

Output a single integer specifying the minimum energy FJ and Bessie can use during their travels.

SAMPLE INPUT:

2 7
3 0
5 0
NN
NWWWWWN

SAMPLE OUTPUT:

28
Problem credits: Brian Dean
 */

import java.io.*;
import java.util.*;

public class RadioContact {

	public static void main(String[] args) throws IOException {

		BufferedReader read = new BufferedReader(new FileReader("radio.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[] fx = new int[N + 1];
		int[] fy = new int[N + 1];
		int[] bx = new int[M + 1];
		int[] by = new int[M + 1];
		st = new StringTokenizer(read.readLine());
		fx[0] = Integer.parseInt(st.nextToken());
		fy[0] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(read.readLine());
		bx[0] = Integer.parseInt(st.nextToken());
		by[0] = Integer.parseInt(st.nextToken());
		char[] fPath = read.readLine().toCharArray();
		char[] bPath = read.readLine().toCharArray();
		read.close();
		
		for(int i = 0; i < N; i++){
			fx[i + 1] = fx[i];
			fy[i + 1] = fy[i];
			if(fPath[i] == 'N')
				fy[i + 1]++;
			else if(fPath[i] == 'E')
				fx[i + 1]++;
			else if(fPath[i] == 'S')
				fy[i + 1]--;
			else
				fx[i + 1]--;
		}
		for(int i = 0; i < M; i++){
			bx[i + 1] = bx[i];
			by[i + 1] = by[i];
			if(bPath[i] == 'N')
				by[i + 1]++;
			else if(bPath[i] == 'E')
				bx[i + 1]++;
			else if(bPath[i] == 'S')
				by[i + 1]--;
			else
				bx[i + 1]--;
		}
		
		long[][] dp = new long[N + 1][M + 1];
		for(int i = 0; i <= N; i++){
			for(int j = 0; j <= M; j++){
				if(i == 0 && j == 0)
					continue;
				int cost = (fx[i] - bx[j]) * (fx[i] - bx[j]) + (fy[i] - by[j]) * (fy[i] - by[j]);
				dp[i][j] = Long.MAX_VALUE;
				if(i > 0)
					dp[i][j] = Math.min(dp[i][j], cost + dp[i - 1][j]);
				if(j > 0)
					dp[i][j] = Math.min(dp[i][j], cost + dp[i][j - 1]);
				if(i > 0 && j > 0)
					dp[i][j] = Math.min(dp[i][j], cost + dp[i - 1][j - 1]);
			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("radio.out")));
		out.println(dp[N][M]);
		System.out.println(dp[N][M]);
		out.close();
		System.exit(0);

	}

}
