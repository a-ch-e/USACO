/*
ID: ayc22032
LANG: JAVA
TASK: numtri
 */

/*
1.5.1 Number Triangles


Consider the number triangle shown below. Write a program that calculates the highest sum of
numbers that can be passed on a route that starts at the top and ends somewhere on the base.
Each step can go either diagonally down to the left or diagonally down to the right.

     7
    
    3 8
   
   8 1 0
  
  2 7 4 4
 
 4 5 2 6 5

In the sample above, the route from 7 to 3 to 8 to 7 to 5 produces the highest sum: 30.

PROGRAM NAME: numtri

INPUT FORMAT

The first line contains R (1 <= R <= 1000), the number of rows. Each subsequent line contains the
integers for that particular row of the triangle. All the supplied integers are non-negative and
no larger than 100.

SAMPLE INPUT (file numtri.in)

5
7
3 8
8 1 0
2 7 4 4
4 5 2 6 5

OUTPUT FORMAT

A single line containing the largest sum using the traversal specified.

SAMPLE OUTPUT (file numtri.out)

30

 */

import java.io.*;
import java.util.StringTokenizer;

public class numtri {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("numtri.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("numtri.out")));
		int len = Integer.parseInt(read.readLine());
		TriNum[][] triangle = new TriNum[len][len];
		for(int i = 1; i <= len; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			for(int j = 0; j < i; j++){
				triangle[i - 1][j] = new TriNum(Integer.parseInt(st.nextToken()));
			}
		}
		read.close();
		
		triangle[0][0].max = triangle[0][0].num;
		for(int i = 1; i < len; i++){
			for(int j = 0; j <= i; j++){
				int a = 0;
				if(j > 0)
					a = triangle[i - 1][j - 1].max;
				int b = 0;
				if( j != i)
					b = triangle[i - 1][j].max;
				triangle[i][j].max = Math.max(a, b) + triangle[i][j].num;	
			}
		}
		int max = triangle[len - 1][0].max;
		for(int i = 1; i < len; i++){
			max = Math.max(triangle[len - 1][i].max, max);
		}
		System.out.println(max);
		out.println(max);
		out.close();
		System.exit(0);
		
	}
	
}

class TriNum{
	
	int num;
	int max;
	
	public TriNum(int n){
		num = n;
		max = 0;
	}
	
}
