/*
ID: ayc22031
LANG: JAVA
PROG: dualpal
 */

/*
 * 
 * A number that reads the same from right to left as when read from left to right is called a palindrome. The number 12321 is a palindrome; the number 77778 is not. Of course, palindromes have neither leading nor trailing zeroes, so 0220 is not a palindrome.

The number 21 (base 10) is not palindrome in base 10, but the number 21 (base 10) is, in fact, a palindrome in base 2 (10101).

Write a program that reads two numbers (expressed in base 10):

N (1 <= N <= 15)
S (0 < S < 10000)
and then finds and prints (in base 10) the first N numbers strictly greater than S that are palindromic when written in two or more number bases (2 <= base <= 10).
Solutions to this problem do not require manipulating integers larger than the standard 32 bits.

PROGRAM NAME: dualpal

INPUT FORMAT

A single line with space separated integers N and S.

SAMPLE INPUT (file dualpal.in)

3 25
OUTPUT FORMAT

N lines, each with a base 10 number that is palindromic when expressed in at least two of the bases 2..10. The numbers should be listed in order from smallest to largest.
SAMPLE OUTPUT (file dualpal.out)

26
27
28
 * 
 */

import java.io.*;
import java.util.StringTokenizer;

public class dualpal {
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("dualpal.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dualpal.out")));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int len = Integer.parseInt(st.nextToken());
		int start = Integer.parseInt(st.nextToken());
		for(int i = start + 1; len > 0; i++){
			int count = 0;
			boolean works = false;
			for(int j = 2; j <= 10; j++){
				if(isPalindrome(i, j))
					count++;
				if(count > 1){
					works = true;
					break;
				}
			}
			if(works){
				out.println(i);
				len--;
			}
			
		}
		out.close();
		System.exit(0);
		
	}
	
	private static boolean isPalindrome(int num, int base){
		
		return helper(Integer.toString(num, base));
	}
	
	private static boolean helper(String s){
		if(s.length() < 2)
			return true;
		return s.charAt(0) == s.charAt(s.length() - 1) && helper(s.substring(1, s.length() -1));
	}
	
}
