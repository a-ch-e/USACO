/*
ID: ayc22032
LANG: JAVA
TASK: pprime
 */

/*

1.5.2 Prime Palindromes

The number 151 is a prime palindrome because it is both a prime number and a palindrome (it is
the same number when read forward as backward). Write a program that finds all prime palindromes
in the range of two supplied numbers a and b (5 <= a, < b <= 100,000,000); both a and b are
considered to be within the range.

PROGRAM NAME: pprime

INPUT FORMAT

Line 1: Two integers, a and b

SAMPLE INPUT (file pprime.in)

5 500

OUTPUT FORMAT

The list of palindromic primes in numerical order, one per line.

SAMPLE OUTPUT (file pprime.out)

5
7
11
101
131
151
181
191
313
353
373
383

 */

import java.io.*;
import java.util.*;

public class pprime {
	
	private static ArrayList<Integer> solutions;
	private static ArrayList<Integer> primes;
	private static int a;
	private static int b;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("pprime.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("pprime.out")));
		StringTokenizer st = new StringTokenizer(read.readLine());
		a = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());
		int minD = digits(a);
		int maxD = digits(b);
		read.close();
		
		solutions = new ArrayList<Integer>();
		primes = new ArrayList<Integer>();
		buildPrimes();
//		System.out.println(isPrime(2));
		for(int i = minD; i <= maxD; i++){
			addAll(i, true, 0);
		}
		
		for(int i : solutions){
			out.println(i);
			System.out.println(i);
		}
//		System.out.println(primes);
		
		out.close();
		System.exit(0);
		
	}
	
	private static void buildPrimes(){
		for(int i = 2; i <= Math.sqrt(b); i++){
			if(isPrime(i))
				primes.add(i);
		}
	}
	
	private static boolean isPrime(int p){
		
		for(int i = 0; i < primes.size() && primes.get(i) <= Math.sqrt(p); i++){
			if(p % primes.get(i) == 0)
				return false;
		}
		return true;
	}
	
	private static void addAll(int depth, boolean first, int soFar){
		
//		if(depth > 1)
//			System.out.println(depth);
		int inc = 1;
		if(first)
			inc++;
		for(int i = inc - 1; i < 10; i += inc){
			int temp = soFar;
			temp = temp * 10 + i;
			if(digits(temp) == (depth + 1) / 2){
				temp = mirror(depth % 2 != 0, temp);
				if(isPrime(temp) && temp >= a && temp <= b)
					solutions.add(temp);
				continue;
			}
			addAll(depth, false, temp);
		}
		
	}
	
	private static int mirror(boolean odd, int half){
		
		int other = 0;
		int temp = half;
		if(odd) temp /= 10;
		while(temp > 0){
			other *= 10;
			other += temp % 10;
			temp /= 10;
			half *= 10;
		}
		half += other;
		return half;
		
	}
	
	private static int digits(int num){
		
		int count = 0;
		while(num > 0){
			num /= 10;
			count++;
		}
		return count;
	}
	
}
