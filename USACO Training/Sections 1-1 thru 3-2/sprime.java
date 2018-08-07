/*
ID: ayc22032
LANG: JAVA
TASK: sprime
 */

/*

1.5.3 Superprime Rib

Butchering Farmer John's cows always yields the best prime rib. You can tell prime ribs by
looking at the digits lovingly stamped across them, one by one, by FJ and USDA. Farmer John
ensures that a purchaser of his prime ribs gets really prime ribs because when sliced from
the right, the numbers on the ribs continue to stay prime right down to the last rib, e.g.:

	7   3   3   1
	
The set of ribs denoted by 7331 is prime; the three ribs 733 are prime; the two ribs 73 are
prime, and, of course, the last rib, 7, is prime. The number 7331 is called a superprime of
length 4.

Writer a program the accepts a number N (1 <= N < = 8) and prints all the superprimes of that
length.

The number 1 (by itself) is not a prime number.

PROGRAM NAME: sprime

INPUT FORMAT

A single line with the number N.

SAMPLE INPUT (file sprime.in)

4

OUTPUT FORMAT

The superprime ribs of length N, printed in ascending order one per line.

SAMPLE OUTPUT (file sprime.out)

2333
2339
2393
2399
2939
3119
3137
3733
3739
3793
3797
5939
7193
7331
7333
7393

 */

import java.io.*;
import java.util.*;

public class sprime {
	
	private static ArrayList<Integer> solutions;
	private static int len;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("sprime.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sprime.out")));
		len = Integer.parseInt(read.readLine());
		read.close();
		solutions = new ArrayList<Integer>();
		addAll(0, 0);
		for(int i : solutions){
			System.out.println(i);
			out.println(i);
		}
		out.close();
		System.exit(0);
		
	}
	
	private static void addAll(int count, int soFar){
		
		if(len == 1){
			solutions.add(2);
			solutions.add(3);
			solutions.add(5);
			solutions.add(7);
		} else if(count == 0){
			addAll(1, 2);
			addAll(1, 3);
			addAll(1, 5);
			addAll(1, 7);
		} else {
			count++;
			for(int i = 1; i < 10; i += 2){
				int temp = soFar;
				temp *= 10;
				temp += i;
				if(!isPrime(temp))
					continue;
				if(count == len)
					solutions.add(temp);
				else
					addAll(count, temp);
			}
		}
		
	}
	
	private static boolean isPrime(int p){
		
		for(int i = 2; i <= Math.sqrt(p); i++){
			if(p % i == 0)
				return false;
		}
		return true;
	}
}
