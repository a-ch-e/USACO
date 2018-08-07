/*
ID: ayc22032
LANG: JAVA
TASK: frac1
 */

/*

Ordered Fractions

Consider the set of all reduced fractions between 0 and 1 inclusive with denominators less than or equal to N. 
Here is the set when N = 5: 

0/1 1/5 1/4 1/3 2/5 1/2 3/5 2/3 3/4 4/5 1/1

Write a program that, given an integer N between 1 and 160 inclusive, prints the fractions in order of increasing magnitude. 

PROGRAM NAME: frac1

INPUT FORMAT

One line with a single integer N. 

SAMPLE INPUT (file frac1.in) 

5

OUTPUT FORMAT

One fraction per line, sorted in order of magnitude.

SAMPLE OUTPUT (file frac1.out)

0/1
1/5
1/4
1/3
2/5
1/2
3/5
2/3
3/4
4/5
1/1

 */

import java.io.*;
import java.util.*;

public class frac1 {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("frac1.in"));
		int n = Integer.parseInt(read.readLine());
		read.close();
		
		TreeSet<Fraction1> set = new TreeSet<Fraction1>();
		for(int i = 1; i <= n; i++){
			for(int j = 0; j <= i; j++){
				set.add(new Fraction1(j, i));
			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("frac1.out")));
		print(set, out);
		out.close();
		System.exit(0);
		
	}
	
	private static void print(TreeSet<Fraction1> set, PrintWriter out){
		for(Fraction1 f : set){
			System.out.println(f);
			out.println(f);
		}
	}

}

class Fraction1 implements Comparable<Fraction1>{
	
	private int num;
	private int den;
	
	public Fraction1(int a, int b){
		
		num = a;
		den = b;
		
	}
	
	public int compareTo(Fraction1 other){
		
		return (num * other.den) - (other.num * den);
		
	}
	
	public boolean equals(Fraction1 other){
		return compareTo(other) == 0;
	}
	
	public boolean equals(Object other){
		return other instanceof Fraction1 && equals(other);
	}
	
	public String toString(){
		return "" + num + "/" + den;
	}
	
}