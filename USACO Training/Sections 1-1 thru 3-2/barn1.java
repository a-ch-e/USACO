/*
ID: ayc22031
LANG: JAVA
PROG: barn1
 */

/*
 * Barn Repair

It was a dark and stormy night that ripped the roof and gates off the stalls that hold Farmer John's cows. Happily, many of the cows were on vacation, so the barn was not completely full.

The cows spend the night in stalls that are arranged adjacent to each other in a long line. Some stalls have cows in them; some do not. All stalls are the same width.

Farmer John must quickly erect new boards in front of the stalls, since the doors were lost. His new lumber supplier will supply him boards of any length he wishes, but the supplier can only deliver a small number of total boards. Farmer John wishes to minimize the total length of the boards he must purchase.

Given M (1 <= M <= 50), the maximum number of boards that can be purchased; S (1 <= S <= 200), the total number of stalls; C (1 <= C <= S) the number of cows in the stalls, and the C occupied stall numbers (1 <= stall_number <= S), calculate the minimum number of stalls that must be blocked in order to block all the stalls that have cows in them.

Print your answer as the total number of stalls blocked.

PROGRAM NAME: barn1

INPUT FORMAT

Line 1:	M, S, and C (space separated)
Lines 2-C+1:	 Each line contains one integer, the number of an occupied stall.
SAMPLE INPUT (file barn1.in)

4 50 18
3
4
6
8
14
15
16
17
21
25
26
27
30
31
40
41
42
43
OUTPUT FORMAT

A single line with one integer that represents the total number of stalls blocked.
SAMPLE OUTPUT (file barn1.out)

25
[One minimum arrangement is one board covering stalls 3-8, one covering 14-21, one covering 25-31, and one covering 40-43.] 
 */

import java.io.*;
import java.util.StringTokenizer;

public class barn1 {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("barn1.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barn1.out")));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int boards = Integer.parseInt(st.nextToken());
		Stall[] stalls = new Stall[Integer.parseInt(st.nextToken())];
		int occ = Integer.parseInt(st.nextToken());
		int min = stalls.length - 1;
		int max = 0;
		for(int i = 0; i < occ; i++){
			int ind = Integer.parseInt(read.readLine()) - 1;
			stalls[ind] = new Stall(true);
			min = Math.min(min, ind);
			max = Math.max(max, ind);
		}
		for(int i = 0; i < stalls.length; i++){
			if(stalls[i] == null) stalls[i] = new Stall();
		}
		for(int i = min; i <= max; i++){
			stalls[i].cov = true;
		}
		while(boards > 1){
			gapPlace(stalls, min, max);
			boards--;
		}
		int count = 0;
		for(int i = 0; i < stalls.length; i++){
			System.out.println((i + 1) + ":\t" + stalls[i]);
			if(stalls[i].cov == true) count++;
		}
		out.println(count);
		out.close();
		System.exit(0);
		
	}
	
	private static void gapPlace(Stall[] stalls, int min, int max){
		
		int min2 = 0;
		int max2 = 0;
		for(int i = min; i < max; i++){
			int temp = i;
			int temp2 = temp;
			while(stalls[i].occ == false && stalls[i].cov == true && i < max){
				temp2++;
				i++;
			}
			if(max2 - min2 < temp2 - temp){
				min2 = temp;
				max2 = temp2;
			}
		}
		for(int i = min2; i < max2; i++){
			stalls[i].cov = false;
		}
	}
}

class Stall{
	
	boolean occ;
	boolean cov;
	
	public Stall(){
		occ = false;
		cov = false;
	}
	
	public Stall(boolean oc){
		occ = oc;
		cov = false;
	}
	
	public String toString(){
		String s = "";
		if(!occ) s += "Uno";
		else s += "O";
		s += "ccupied, ";
		if(!cov) s += "un";
		s += "covered";
		return s;
	}
	
}

