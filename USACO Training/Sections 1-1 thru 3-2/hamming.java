/*
ID: ayc22032
LANG: JAVA
TASK: hamming
 */

/*

Hamming Codes
Rob Kolstad

Given N, B, and D: Find a set of N codewords (1 <= N <= 64), each of length B bits (1 <= B <= 8), such that each of the codewords is at least
Hamming distance of D (1 <= D <= 7) away from each of the other codewords. The Hamming distance between a pair of codewords is the number of
binary bits that differ in their binary notation. Consider the two codewords 0x554 and 0x234 and their differences (0x554 means the hexadecimal
number with hex digits 5, 5, and 4):

        0x554 = 0101 0101 0100
        0x234 = 0010 0011 0100
Bit differences: xxx  xx

Since five bits were different, the Hamming distance is 5.

PROGRAM NAME: hamming

INPUT FORMAT

N, B, D on a single line

SAMPLE INPUT (file hamming.in)

16 7 3

OUTPUT FORMAT

N codewords, sorted, in decimal, ten per line. In the case of multiple solutions, your program should output the solution which, if interpreted
as a base 2^B integer, would have the least value.

SAMPLE OUTPUT (file hamming.out)

0 7 25 30 42 45 51 52 75 76
82 85 97 102 120 127


 */

import java.io.*;
import java.util.*;

public class hamming {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("hamming.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int len = Integer.parseInt(st.nextToken());
		int bits = Integer.parseInt(st.nextToken());
		int dist = Integer.parseInt(st.nextToken());
		read.close();
		
		Codeword[] poss = new Codeword[(int)Math.pow(2, bits)];
		for(int i = 0; i < poss.length; i++){
			poss[i] = new Codeword(i, bits);
		}
		int[] ans = new int[len];
		
		int[][] distances = new int[poss.length][poss.length];
		for(int i = 0; i < distances.length; i++){
			for(int j = 0; j < distances.length; j++){
				if(j == i)
					continue;
				distances[i][j] = poss[i].distance(poss[j]);
			}
		}
//		for(int i = 0; i < distances.length; i++){
//			for(int j = 0; j < distances.length; j++){
//				System.out.print(distances[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		int ind = 1;
		for(int i = 1; i < ans.length; i++){
			while(true){
				boolean works = true;
				for(int j = 0; j < i; j++){
					if(distances[ind][ans[j]] < dist){
						works = false;
						break;
					}
				}
				if(works)
					break;
				ind++;
				if(ind == poss.length)
					ind = 1;
			}
			ans[i] = ind;
//			System.out.println(ind);
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hamming.out")));
		for(int i = 0; i <= ans.length / 10; i++){
			if(ans.length <= 10 * i)
				break;
			out.print(ans[10 * i]);
			System.out.print(ans[10 * i]);
			for(int j = 1; j < 10 && 10 * i + j < len; j++){
				out.print(" " + ans[10 * i + j]);
				System.out.print(" " + ans[10 * i + j]);
			}
			System.out.println();
			out.println();
		}
//		out.print(ans[0]);
//		System.out.print(ans[0]);
//		for(int i = 1; i < ans.length; i++){
//			out.print(" " + ans[i]);
//			System.out.print(" " + ans[i]);
//		}
//		System.out.println();
		out.close();
		System.exit(0);
		
	}
	
}

class Codeword{
	
	private int num;
	private boolean[] bits;
	
	public Codeword(int n, int b){
		
		num = n;
		bits = new boolean[b];
		int ind = bits.length - 1;
		while(n > 0){
			bits[ind] = n % 2 == 1;
			n /= 2;
			ind--;
		}
		
	}
	
	public int distance(Codeword other){
		
		int count = 0;
		for(int i = 0; i < bits.length; i++){
			if(bits[i] ^ other.bits[i])
				count++;
		}
		return count;
	}
	
	public String toString(){
		return "" + num;
	}
	
}