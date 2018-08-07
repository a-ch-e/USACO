/*
ID: ayc22031
LANG: JAVA
PROG: palsquare 
 */

/*
 * Palindromes are numbers that read the same forwards as backwards. The number 12321 is a typical palindrome.

Given a number base B (2 <= B <= 20 base 10), print all the integers N (1 <= N <= 300 base 10) such that the square of N is palindromic when expressed in base B; also print the value of that palindromic square. Use the letters 'A', 'B', and so on to represent the digits 10, 11, and so on.

Print both the number and its square in base B.

PROGRAM NAME: palsquare

INPUT FORMAT

A single line with B, the base (specified in base 10).
SAMPLE INPUT (file palsquare.in)

10
OUTPUT FORMAT

Lines with two integers represented in base B. The first integer is the number whose square is palindromic; the second integer is the square itself.
SAMPLE OUTPUT (file palsquare.out)

1 1
2 4
3 9
11 121
22 484
26 676
101 10201
111 12321
121 14641
202 40804
212 44944
264 69696

 */

import java.io.*;

public class palsquare {
	
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("palsquare.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("palsquare.out")));
		int base = Integer.parseInt(read.readLine());
		for(int i = 1; i <= 300; i++){
			if(isPalindrome(i*i, base)){
				out.println(changeBase(i, base) + " " + changeBase(i*i, base));
			}
		}
//		System.out.println(changeBase(15, 16));
		out.close();
		System.exit(0);
		
	}
	
	private static boolean isPalindrome(int num, int base){
		
		return helper(changeBase(num, base));
	}
	
	private static boolean helper(String s){
		if(s.length() < 2)
			return true;
		return s.charAt(0) == s.charAt(s.length() - 1) && helper(s.substring(1, s.length() -1));
	}
	
	private static String changeBase(int num, int base){
		String result = "";
		while(num > 0){
			int temp = num % base;
			System.out.println(temp);
			if(temp < 10)
				result = temp + result;
			else
				result = ((char)('A' + temp - 10)) + result;
			num = num / base;
			System.out.println(result + "\n");
		}
		return result;
	}
	
}


