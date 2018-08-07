/*
ID: ayc22032
LANG: JAVA
TASK: fact4
 */

/*

Factorials
The factorial of an integer N, written N!, is the product of all the integers from 1
through N inclusive. The factorial quickly becomes very large: 13! is too large to
store in a 32-bit integer on most computers, and 70! is too large for most floating-
point variables. Your task is to find the rightmost non-zero digit of n!. For example,
5! = 1 * 2 * 3 * 4 * 5 = 120, so the rightmost non-zero digit of 5! is 2. Likewise, 7!
= 1 * 2 * 3 * 4 * 5 * 6 * 7 = 5040, so the rightmost non-zero digit of 7! is 4.

PROGRAM NAME: fact4

INPUT FORMAT

A single positive integer N no larger than 4,220.

SAMPLE INPUT (file fact4.in)

7

OUTPUT FORMAT

A single line containing but a single digit: the right most non-zero digit of N! .

SAMPLE OUTPUT (file fact4.out)

4

 */

import java.io.*;
//import java.util.*;

public class fact4 {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("fact4.in"));
		int f = Integer.parseInt(read.readLine());
		long dig = 1;
		for(int i = 1; i <= f; i++){
			dig *= i;
			while(dig % 10 == 0)
				dig /= 10;
			dig %= 1000000;
//			System.out.println(i);
		}
		dig %= 10;
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fact4.out")));
		System.out.println(dig);
		out.println(dig);
		out.close();
		System.exit(0);
		
	}
	
}
