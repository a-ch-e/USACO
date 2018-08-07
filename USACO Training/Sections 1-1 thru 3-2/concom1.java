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

public class concom1 {
	
	private static boolean[] visited;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("concom.in"));
		int num = Integer.parseInt(read.readLine());
		
		//recursive
		/*
		int max = 0;
		int[][] edgeList = new int[num][3];
		for(int i = 0; i < num; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			for(int j = 0; j < 3; j++){
				edgeList[i][j] = Integer.parseInt(st.nextToken());
				if(j < 2)
					max = Math.max(edgeList[i][j], max);
			}
		}
		int[][] g = new int[max][max];
		visited = new boolean[max];
		for(int i = 0; i < num; i++){
			g[edgeList[i][0] - 1][edgeList[i][1] - 1] = edgeList[i][2];
		}
		
		for(int i = 0; i < g.length; i++){
			System.out.print(i + ":\t");
			for(int j = 0; j < g[i].length; j++){
				System.out.print(g[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------");
		
		for(int k = 0; k < g.length; k++){
			recur(g, k);
			visited = new boolean[max];
//			for(int i = 0; i < g.length; i++){
//				System.out.print(i + ":\t");
//				for(int j = 0; j < g[i].length; j++){
//					System.out.print(g[i][j] + " ");
//				}
//				System.out.println();
//			}
			System.out.println("-----------------------------------------------------------");
		}
		*/
		//original iterative
		
		
		int max = 0;
		for(int i = 0; i < num; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			max = Math.max(Integer.parseInt(st.nextToken()), max);
			max = Math.max(Integer.parseInt(st.nextToken()), max);
		}
		Scanner sc = new Scanner(new File("concom.in"));
		int[][] g = new int[max][max];
		for(int i = 0; i < g.length; i++){
			for(int j = 0; j < g.length; j++){
				if(j != i)
					g[i][j] = -1;
			}
		}
		sc.nextInt();
		for(int i = 0; i < num; i++){
			int a = sc.nextInt();
			int b = sc.nextInt();
			int d = sc.nextInt();
			g[a - 1][b - 1] = d;
		}
		read.close();
		
//		for(int i = 0; i < g.length; i++){
//			for(int j = 0; j < g[i].length; j++)
//				System.out.print(g[i][j] + " ");
//			System.out.println();
//		}
		boolean change = true;
//		int count = 0;
		while(change){
			change = false;
//			count++;
			for(int v = 0; v < g.length; v++){
				for(int i = 0; i < g.length; i++){
//					if(i == v || (g[v][i] > 50) )
//						continue;
					int sum = 0;
					for(int j = 0; j < g[i].length; j++){
						if(g[v][j] > 50 && g[j][i] > 0)
							sum += g[j][i];
					}
					if(g[v][i] > 0)
						sum += g[v][i];
					if(sum > 50){
						g[v][i] = sum;
						change = true;
					}
				}
			}
//			if(n % 20 != 0)
//				continue;
//			System.out.println();
//			for(int i = 0; i < g.length; i++){
//				System.out.print(i + "\t");
//				for(int j = 0; j < g[i].length; j++)
//					System.out.print(g[i][j] + " ");
//				System.out.println();
//			}
		}

		
		System.out.println();
		for(int i = 0; i < g.length; i++){
			System.out.print(i + "\t");
			for(int j = 0; j < g[i].length; j++)
				System.out.print(g[i][j] + " ");
			System.out.println();
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("concom.out")));
		for(int i = 0; i < g.length; i++){
			for(int j = 0; j < g[i].length; j++){
//				if(g[i][j] < 0)
//					throw new IOException();
				if(j != i && g[i][j] > 50){
					System.out.println((i + 1) + " "+ (j + 1));
					out.println((i + 1) + " "+ (j + 1));
				}
			}
		}
		
		
//		for(int i = 0; i < g.length; i++){
//			for(int j = 0; j < g[i].length; j++){
//				System.out.print(g[i][j] + " ");
//			}
//			System.out.println();
//		}
//		
//		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("concom.out")));
//		for(int i = 0; i < g.length; i++){
//			for(int j = 0; j < g[i].length; j++){
//				if(j != i && g[i][j] > 50){
//					System.out.println((i + 1) + " "+ (j + 1));
//					out.println((i + 1) + " "+ (j + 1));
//				}
//			}
//		}		
		out.close();
		System.exit(0);
		
	}
	
	private static void recur(int[][] g, int start){
		
//		System.out.println(start);
//		for(int i = 0; i < g.length; i++){
//			System.out.print(i + ":\t");
//			for(int j = 0; j < g[i].length; j++){
//				System.out.print(g[i][j] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println("-----------------------------------------------------------");
		if(visited[start])
			return;
		visited[start] = true;
		int count = 0;
		int ind = 0;
		boolean[] locs = new boolean[g[start].length];
		while(count < g[start].length){
			if(g[start][ind] > 50 && !locs[ind]){
//				recur(g, i);
				count = 0;
				locs[ind] = true;
				for(int j = 0; j < g[ind].length; j++){
//					if(g[start][j] <= 50)
						g[start][j] += g[ind][j];
				}
				recur(g, ind);
			}
			ind++;
			ind %= g[start].length;
			count++;
		}
		
		
	}
	
}

//class ShareOwn implements Comparable<ShareOwn>{
//	int coA;
//	int coB;
//	int percent;
//	
//	public ShareOwn(StringTokenizer st){
//		
//		coA = Integer.parseInt(st.nextToken());
//		coB = Integer.parseInt(st.nextToken());
//		percent = Integer.parseInt(st.nextToken());
//		
//	}
//	
//	public int compareTo(ShareOwn other){
//		
//		if(coA == other.coA)
//			return coB - other.coB;
//		return coA - other.coA;
//		
//	}
//}
