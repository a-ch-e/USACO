/*
ID: ayc22032
LANG: JAVA
TASK: agrinet
 */
/*
Agri-Net
Russ Cox

Farmer John has been elected mayor of his town! One of his campaign promises was to bring internet
connectivity to all farms in the area. He needs your help, of course.

Farmer John ordered a high speed connection for his farm and is going to share his connectivity with
the other farmers. To minimize cost, he wants to lay the minimum amount of optical fiber to connect
his farm to all the other farms.

Given a list of how much fiber it takes to connect each pair of farms, you must find the minimum
amount of fiber needed to connect them all together. Each farm must connect to some other farm
such that a packet can flow from any one farm to any other farm.

The distance between any two farms will not exceed 100,000.

PROGRAM NAME: agrinet

INPUT FORMAT

Line 1:	The number of farms, N (3 <= N <= 100).
Line 2..end:	The subsequent lines contain the N x N connectivity matrix, where each element shows
the distance from on farm to another. Logically, they are N lines of N space-separated integers.
Physically, they are limited in length to 80 characters, so some lines continue onto others. Of course,
the diagonal will be 0, since the distance from farm i to itself is not interesting for this problem.

SAMPLE INPUT (file agrinet.in)

4
0 4 9 21
4 0 8 17
9 8 0 16
21 17 16 0

OUTPUT FORMAT

The single output contains the integer length that is the sum of the minimum length of fiber required
to connect the entire set of farms.

SAMPLE OUTPUT (file agrinet.out)

28
 */

import java.io.*;
import java.util.*;

public class agrinet {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("agrinet.in"));
		int len = Integer.parseInt(read.readLine());
		int[][] g = new int[len][len];
		for(int i = 0; i < len; i++){
			int j = 0;
			StringTokenizer st = new StringTokenizer(read.readLine());
			while(j < len){
				if(!st.hasMoreTokens())
					st = new StringTokenizer(read.readLine());
				g[i][j++] = Integer.parseInt(st.nextToken());
			}
			
			
		}
		read.close();
		
		int dist = 0;
		boolean[] inTree = new boolean[len];
		int[] tDists = new int[len];
		for(int i = 0; i < len; i++){
			tDists[i] = g[0][i];
		}
		inTree[0] = true;
		for(int i = 1; i < len; i++){
			int ind = 0;
			for(int j = 0; j < len; j++){
				if(!inTree[j] && (tDists[j] < tDists[ind] || tDists[ind] == 0)){
					ind = j;
				}
			}
			inTree[ind] = true;
			dist += tDists[ind];
			for(int j = 0; j < len; j++){
//				System.out.print(tDists[j] + " ");
				tDists[j] = Math.min(tDists[j], g[ind][j]);
			}
//			System.out.println();
//			System.out.println(dist);
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("agrinet.out")));
		out.println(dist);
		System.out.println(dist);
		out.close();
		System.exit(0);
		
	}
	
}
