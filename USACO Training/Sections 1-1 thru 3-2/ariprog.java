/*
ID: ayc22032
LANG: JAVA
TASK: ariprog

/*
An arithmetic progression is a sequence of the form a, a+b, a+2b, ..., a+nb where n=0,1,2,3,... .
For this problem, a is a non-negative integer and b is a positive integer.

Write a program that finds all arithmetic progressions of length n in the set S of bisquares.
The set of bisquares is defined as the set of all integers of the form p2 + q2
(where p and q are non-negative integers).
TIME LIMIT: 5 secs
PROGRAM NAME: ariprog
INPUT FORMAT
Line 1: 	N (3 <= N <= 25), the length of progressions for which to search
Line 2: 	M (1 <= M <= 250), an upper bound to limit the search to the bisquares with 0 <= p,q <= M.
SAMPLE INPUT (file ariprog.in)

5
7

OUTPUT FORMAT

If no sequence is found, a singe line reading `NONE'. Otherwise, output one or more lines,
each with two integers: the first element in a found sequence and the difference between consecutive elements
in the same sequence. The lines should be ordered with smallest-difference sequences first and smallest
starting number within those sequences first.

There will be no more than 10,000 sequences.
SAMPLE OUTPUT (file ariprog.out)

1 4
37 4
2 8
29 8
1 12
5 12
13 12
17 12
5 20
2 24
*/

import java.io.*;
import java.util.*;

public class ariprog{
	
	public static void main(String[] args) throws IOException{
		
		long time = System.currentTimeMillis();
		BufferedReader read = new BufferedReader(new FileReader("ariprog.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));
		int len = Integer.parseInt(read.readLine());
		int m = Integer.parseInt(read.readLine());
		boolean small = m < 20;
		read.close();
		
//		int[] nums = new int[(m + 1) * (m + 2) / 2];
//		int ind = 0;
//		for(int i = 0; i <= m; i++){
//			for(int j = i; j <= m; j++){
//				nums[ind] = i * i + j * j;
//				ind++;
//			}
//		}
//		Arrays.sort(nums);
//		for(int i : nums){
//			System.out.print(i + " ");
//		}
		boolean[] nums = new boolean[2 * (m + 1) * (m + 1)];
		for(int i = 0; i <= m; i++){
			for(int j = i; j <= m; j++)
				nums[i * i + j * j] = true;
		}
		ArrayList<ASeq> list = new ArrayList<ASeq>();
		for(int i = 0; i < nums.length; i++){
			if(!nums[i])
				continue;
			for(int j = i + 1; j < nums.length && (small || j <= (nums.length - i) / len + i); j++){
				if(!nums[j]) 
					continue;
				ASeq seq = new ASeq(i, j);
				while(seq.len < len){
					if(seq.nextTerm() >= nums.length || !nums[seq.nextTerm()])
						break;
					seq.len++;
				}
				if(seq.len == len)
					list.add(seq);
			}
		}
		Collections.sort(list);
		for(ASeq a : list){
			System.out.println(a);
			out.println(a);
		}
		if(list.isEmpty()){
			out.println("NONE");
			System.out.println("NONE");
		}
		
		out.close();
		System.out.println(System.currentTimeMillis() - time);
		System.exit(0);
		
	}
	
}

class ASeq implements Comparable<ASeq>{
	int a;
	int len;
	int d;
	public ASeq(int n, int b){
		a = n;
		len = 2;
		d = b - n;
	}
	public int nextTerm(){
		return a + len * d;
	}
	public int compareTo(ASeq other){
		if(d == other.d)
			return a - other.a;
		return d - other.d;
	}
	public String toString(){
		return "" + a + " " + d;
	}
	
}