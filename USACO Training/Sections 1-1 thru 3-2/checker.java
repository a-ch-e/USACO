/*
ID: ayc22032
LANG: JAVA
TASK: checker
 */

/*

1.5.4 Checker Challenge

Examine the 6 x 6 checkerboard below and note that the six checkers are arranged on the board so
that one and only one is placed in each row and each column, and there is never more than one in
any diagonal. (Diagonals run from southeast to northwest and southwest to northeast and include
all diagonals, not just the major 2.)

    1   2   3   4   5   6
  -------------------------
1 |   | O |   |   |   |   |
  -------------------------
2 |   |   |   | O |   |   |
  -------------------------
3 |   |   |   |   |   | O |
  -------------------------
4 | O |   |   |   |   |   |
  -------------------------
5 |   |   | O |   |   |   |
  -------------------------
6 |   |   |   |   | O |   |
  -------------------------

The solution shown is described by the sequence 2 4 6 1 3 5, which gives the column position of
the checkers for each each row from 1 to 6:

		ROW		1 2 3 4 5 6
		COLUMN	2 4 6 1 3 5
		
This is one solution to the checker challenge. Write a program that finds all unique solution
sequences to the Checker Challenge (with ever growing values of N). Print the solutions using
the column notation described above. Print the first three solutions in numerical order, as if
the checker positions form the digits of a large number, and then a line with the total number of
solutions.

Special note: the larger values of N require your program to be especially efficient. Do not
precalculate the value and print it(or even find a formula for it); that's cheating. Work on your
program until it can solve the problem properly. IF you insist on cheating, your login to the
USACO training pages will be removed and you will be disqualified from all USACO competitions.
YOU HAVE BEEN WARNED.

TIME LIMIT: 1 CPU second

PROGRAM NAME: checker

INPUT FORMAT

A single line that contains a single integer N (6 <= N <= 13) that is the dimension of the N x N
checkerboard.

SAMPLE INPUT (file checker.in)

6

OUTPUT FORMAT

The first three lines show the first three solutions found, presented as N numbers with a single space
between them. The fourth line shows the total number of solutions found.

SAMPLE OUTPUT

2 4 6 1 3 5
3 6 2 5 1 4
4 1 5 2 6 3
4

 */

import java.io.*;
import java.util.*;

public class checker {
	
	private static PrintWriter out;
	private static int[] path;
	private static boolean[] cols;
	private static int count;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("checker.in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter("checker.out")));
		int len = Integer.parseInt(read.readLine());
		path = new int[len];
		for(int i = 0; i < len; i++)
			path[i] = -1;
		cols = new boolean[len];
		count = 0;
		read.close();
		recur(0);
		System.out.println(count);
		out.println(count);
		out.close();
		System.exit(0);
		
	}
	
	private static void recur(int r){
		
//		print(path);
//		System.out.println("----------------------");
//		if(path[0] == 2 && path[1] == 4)
//			print();
		for(int i = 0; i < cols.length; i++){
			if(cols[i])
				continue;
			boolean bad = false;
			for(int j = 0; j < r; j++){
				if(path[j] - j == i - r || path[j] + j == r + i){
					bad = true;
					break;
				}
			}
			if(bad)
				continue;
			path[r] = i;
			cols[i] = true;
			if(path[path.length - 1] > -1){
				count++;
				if(count <= 3)
					print();
			} else
				recur(r + 1);
			
			path[r] = -1;
			cols[i] = false;
		}
		
	}
	
	private static void print(){
		System.out.print((path[0] + 1));
		out.print((path[0] + 1));
		for(int i = 1; i < path.length; i++){
			System.out.print(" " + (path[i] + 1));
			out.print(" " + (path[i] + 1));
		}
		System.out.println();
		out.print("\n");
		
	}
	
}
