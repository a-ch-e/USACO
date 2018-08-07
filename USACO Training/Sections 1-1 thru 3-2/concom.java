/*
ID: ayc22032
LANG: JAVA
TASK: concom
 */

/*

Controlling Companies

Some companies are partial owners of other companies because they have acquired part of their
total shares of stock. For example, Ford owns 12% of Mazda. It is said that a company A controls
company B if at least one of the following conditions is satisfied:

	-Company A = Company B
	-Company A owns more than 50% of Company B
	-Company A controls K (K >= 1) companies denoted C1, ..., CK with each company Ci owning xi%
	of company B and x1 + .... + xK > 50%.

Given a list of triples (i,j,p) which denote company i owning p% of company j, calculate all the
pairs (h,s) in which company h controls company s. There are at most 100 companies.

Write a program to read the list of triples (i,j,p) where i, j and p are positive integers all in
the range (1..100) and find all the pairs (h,s) so that company h controls company s.

PROGRAM NAME: concom

INPUT FORMAT

Line 1:	 n, the number of input triples to follow
Line 2..n+1:	 Three integers per line as a triple (i,j,p) described above.

SAMPLE INPUT (file concom.in)

3
1 2 80
2 3 80
3 1 20

OUTPUT FORMAT

List 0 or more companies that control other companies. Each line contains two integers that
denote that the company whose number is the first integer controls the company whose number
is the second integer. Order the lines in ascending order of the first integer (and ascending
order of the second integer to break ties). Do not print that a company controls itself.

SAMPLE OUTPUT (file concom.out)

1 2
1 3
2 3

 */

import java.io.*;
import java.util.*;

public class concom {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("concom.in"));
		int num = Integer.parseInt(read.readLine());
		int max = 0;
		for(int i = 0; i < num; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			max = Math.max(Integer.parseInt(st.nextToken()), max);
			max = Math.max(Integer.parseInt(st.nextToken()), max);
		}
		Scanner sc = new Scanner(new File("concom.in"));
		int[][] g = new int[max][max];
		boolean[][] c = new boolean[max][max];
		for(int i = 0; i < g.length; i++){
			c[i][i] = true;
//			for(int j = 0; j < g.length; j++){
//				if(j != i)
//					g[i][j] = -1;
//			}
		}
		sc.nextInt();
		for(int i = 0; i < num; i++){
			int a = sc.nextInt();
			int b = sc.nextInt();
			int d = sc.nextInt();
			g[a - 1][b - 1] = d;
			if(d > 50)
				c[a - 1][b - 1] = true;
		}
		read.close();
		
//		for(int i = 0; i < g.length; i++){
//			System.out.print(i + "\t");
//			for(int j = 0; j < g[i].length; j++)
//				System.out.print(g[i][j] + " ");
//			System.out.println();
//		}
		
		boolean change = true;
		while(change){
			change = false;
			for(int v = 0; v < g.length; v++){
				for(int i = 0; i < g[v].length; i++){
					if(i == v || c[v][i])
						continue;
					int sum = 0;
					for(int j = 0; j < g.length; j++){
						if(c[v][j])
							sum += g[j][i];
					}
//					if(g[v][i] > 0)
//						sum += g[v][i];
					if(sum > 50){
						c[v][i] = true;
						change = true;
					}
				}
			}
//			System.out.println();
//			for(int i = 0; i < g.length; i++){
//				System.out.print(i + "\t");
//				for(int j = 0; j < g[i].length; j++)
//					System.out.print(c[i][j] + " ");
//				System.out.println();
//			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("concom.out")));
		for(int i = 0; i < g.length; i++){
			for(int j = 0; j < g[i].length; j++){
				if(j != i && c[i][j]){
					System.out.println((i + 1) + " "+ (j + 1));
					out.println((i + 1) + " "+ (j + 1));
				}
			}
		}
		out.close();
		System.exit(0);
		
	}
	
}
