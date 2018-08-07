/*
ID: ayc22032
LANG: JAVA
TASK: contact
 */

/*

Contact
IOI'98
The cows have developed a new interest in scanning the universe outside their farm with
radiotelescopes. Recently, they noticed a very curious microwave pulsing emission sent
right from the centre of the galaxy. They wish to know if the emission is transmitted
by some extraterrestrial form of intelligent life or if it is nothing but the usual
heartbeat of the stars.
Help the cows to find the Truth by providing a tool to analyze bit patterns in the files
they record. They are seeking bit patterns of length A through B inclusive (1 <= A <= B
<= 12) that repeat themselves most often in each day's data file. They are looking for
the patterns that repeat themselves most often. An input limit tells how many of the most
frequent patterns to output.
Pattern occurrences may overlap, and only patterns that occur at least once are taken into
account.

PROGRAM NAME: contact

INPUT FORMAT

Line 1:	Three space-separated integers: A, B, N; (1 <= N < 50)
Lines 2 and beyond:	A sequence of as many as 200,000 characters, all 0 or 1; the characters
are presented 80 per line, except potentially the last line.

SAMPLE INPUT (file contact.in)

2 4 10
01010010010001000111101100001010011001111000010010011110010000000

In this example, pattern 100 occurs 12 times, and pattern 1000 occurs 5 times. The most
frequent pattern is 00, with 23 occurrences.
OUTPUT FORMAT
Lines that list the N highest frequencies (in descending order of frequency) along with
the patterns that occur in those frequencies. Order those patterns by shortest-to-longest
and increasing binary number for those of the same frequency. If fewer than N highest
frequencies are available, print only those that are.
Print the frequency alone by itself on a line. Then print the actual patterns space
separated, six to a line (unless fewer than six remain).

SAMPLE OUTPUT (file contact.out)
23
00
15
01 10
12
100
11
11 000 001
10
010
8
0100
7
0010 1001
6
111 0000
5
011 110 1000
4
0001 0011 1100


 */

import java.io.*;
import java.util.*;

public class contact {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("contact.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int a = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		int len = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
		while(true){
			String s = read.readLine();
			if(s == null)
				break;
			sb.append(s);
//			System.out.println(s);
		}
		boolean[] string = new boolean[sb.length()];
		for(int i = 0; i < string.length; i++){
			string[i] = sb.charAt(i) == '1';
		}
//		for(int i = 0; i < string.length; i++){
//			if(string[i])
//				System.out.print("1");
//			else
//				System.out.print("0");
//		}
//		System.out.println();
		read.close();
		
		BitPattern[] patterns = new BitPattern[pow(a) * (pow(b - a + 1) - 1)];
		for(int i = a; i <= b; i++){
			for(int j = 0; j < pow(i); j++){
				patterns[pow(a) * (pow(i - a) - 1) + j] = new BitPattern(i, j);
			}
		}
		for(int i = 0; i < string.length - a + 1; i++){
			for(int j = a; j <= b && i < string.length - j + 1; j++){
				int temp = 0;
				for(int k = 0; k < j; k++){
					temp *= 2;
					if(string[i + k])
						temp += 1;
				}
//				System.out.print(temp + " ");
				int ind = pow(a) * (pow(j - a) - 1) + temp;
				patterns[ind].count++;
			}
//			System.out.println();
		}
		Arrays.sort(patterns);
//		for(int i = 0; i < patterns.length; i++){
//			System.out.print(patterns[i] + " ");
//		}
//		System.out.println();
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("contact.out")));
		int i = 0;
		for(int j = 0; j < len && i < patterns.length; j++){
			int occ = patterns[i].count;
			if(occ <= 0)
				break;
			System.out.println(occ);
			out.println(occ);
			int c = 0;
			StringBuilder output = new StringBuilder();
			while(patterns[i].count == occ){
				c++;
				output.append(patterns[i]);
				if(c < 6)
					output.append(" ");
				else{
					output.append("\n");
					c = 0;
				}
				i++;
				if(i >= patterns.length)
					break;
			}
			String s = output.toString().substring(0, output.length() - 1);
			System.out.println(s);
			out.println(s);
		}
		out.close();
		System.exit(0);
		
	}
	
	public static int pow(int exp){
		int ans = 1;
		for(int i = 0; i < exp; i++){
			ans *= 2;
		}
		return ans;
	}
	
	private static int index(int a, int b){
		
		return pow(a) * (pow(b - a + 1) - 1);
		
	}
	
}

class BitPattern implements Comparable<BitPattern>{
	
//	boolean[] bits;
	int bits;
	int size;
	int count;
	
	public BitPattern(int s, int b){
//		bits = new boolean[s];
//		for(int i = s - 1; i >= 0; i++){
//			bits[i] = b % 2 == 1;
//			b /= 2;
//		}
		bits = b;
		size = s;
		count = 0;
	}
	
	public int compareTo(BitPattern other){
		
		if(count != other.count)
			return other.count - count;
		if(size != other.size)
			return size - other.size;
		return bits - other.bits;
		
	}
	
	public String toString(){
		String s = "";
		int temp = bits;
		for(int i = 0; i < size; i++){
			s = temp % 2 + s;
			temp /= 2;
		}
		return s;
//		return "[" + bits + ", " + size + ", " + count + "]";
//		return "" + bits;
	}
	
}
