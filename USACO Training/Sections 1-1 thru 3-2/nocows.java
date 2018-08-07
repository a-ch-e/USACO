/*
ID: ayc22032
LANG: JAVA
TASK: nocows
 */

/*

Cow Pedigrees

Silviu Ganceanu -- 2003
Farmer John is considering purchasing a new herd of cows. In this new herd, each mother cow gives birth to two children.
The relationships among the cows can easily be represented by one or more binary trees with a total of N (3 <= N < 200)
nodes. The trees have these properties:

The degree of each node is 0 or 2. The degree is the count of the node's immediate children.
The height of the tree is equal to K (1 < K <100). The height is the number of nodes on the longest path from the root
to any leaf; a leaf is a node with no children.
How many different possible pedigree structures are there? A pedigree is different if its tree structure differs from
that of another pedigree. Output the remainder when the total number of different possible pedigrees is divided by 9901.

PROGRAM NAME: nocows

INPUT FORMAT

Line 1: Two space-separated integers, N and K.

SAMPLE INPUT (file nocows.in)

5 3
OUTPUT FORMAT

Line 1: One single integer number representing the number of possible pedigrees MODULO 9901.

SAMPLE OUTPUT (file nocows.out)

2
OUTPUT DETAILS

Two possible pedigrees have 5 nodes and height equal to 3:
           @                   @      
          / \                 / \
         @   @      and      @   @
        / \                     / \
       @   @                   @   @


 */

import java.io.*;
import java.util.*;

public class nocows {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("nocows.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int n = Integer.parseInt(st.nextToken());
		int h = Integer.parseInt(st.nextToken());
		read.close();
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nocows.out")));
		if(n % 2 == 0|| h > n / 2 + 1){
			System.out.println("0");
			out.println("0");
			out.close();
			System.exit(0);
		}
			
		
		int[][] poss = new int[n / 2 + 1][n / 2 + 3];
		poss[0][1] = 1;
		for(int i = 3; i < n; i += 2){
			for(int j = log(i + 1) - 1; j < i / 2 + 2; j++){
				for(int k = 1; k < i; k += 2){
					int sum = 0;
					for(int m = 1; m < j - 1; m++){
						sum += poss[(i - 1 - k) / 2][m];
						sum %= 9901;
					}
					poss[i / 2][j] += 2 * poss[k / 2][j - 1] * sum;
					poss[i / 2][j] += poss[k / 2][j - 1] * poss[(i - 1 - k) / 2][j - 1];
					poss[i / 2][j] %= 9901;
				}
			}
		}
		for(int k = 1; k < n - 1; k += 2){
			int sum = 0;
			for(int m = 1; m < h - 1; m++){
//				try{
					sum += poss[(n - 1 - k) / 2][m];
					sum %= 9901;
//				} catch(Exception e){
//					System.out.println(m);
//					throw e;
//				}
			}
			poss[n / 2][h] += 2 * poss[k / 2][h - 1] * sum;
			poss[n / 2][h] += poss[k / 2][h - 1] * poss[(n - 1 - k) / 2][h - 1];
			poss[n / 2][h] = poss[n / 2][h] % 9901;
		}
		for(int i = 0; i < poss.length; i++){
			for(int j = 0; j < poss[i].length; j++)
				System.out.print(poss[i][j] + " ");
			System.out.println();
		}
		System.out.println(poss[n / 2][h]);
		out.println(poss[n / 2][h]);
		out.close();
		System.exit(0);
		
	}
	
	private static int log(int num){
		int count = 0;
		while(num > 0){
			num /= 2;
			count++;
		}
		return count;
		
	}
	
	
	
}
