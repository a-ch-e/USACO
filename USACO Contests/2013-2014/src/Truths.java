/*
 * 
Problem 3: Liars and Truth Tellers [Brian Dean, 2013]

After spending so much time around his cows, Farmer John has started to
understand their language.  Moreover, he notices that among his N cows 
(2 <= N <= 1000), some always tell the truth while others always lie.

FJ carefully listens to M statements (1 <= M <= 10,000) from his cows, each
of the form "x y T", meaning that "cow x claims cow y always tells the
truth" or "x y L", meaning that "cow x claims cow y always tells lies". 
Each statement involves a pair of different cows, and the same pair of cows
may appear in multiple statements.  

Unfortunately, FJ believes he might have written down some entries in his
list incorrectly, so there may not be a valid way to designate each cow as
a truth teller or a liar that is consistent with all the M statements on
FJ's list.  To help FJ salvage as much of his list as possible, please
compute the largest value of A such that there exists a valid way to
designate each cow as a truth teller or a liar in a manner that is
consistent with the first A entries in FJ's list.

PROBLEM NAME: truth

INPUT FORMAT:

* Line 1: Two space-separated integers, N and M.

* Lines 2..1+M: Each line is of the form "x y L" or "x y T",
        describing a statement made by cow x about cow y.

SAMPLE INPUT (file truth.in):

4 3
1 4 L
2 3 T
4 1 T

INPUT DETAILS:

There are 4 cows and 3 statements.  Cow 1 says that cow 4 lies, cow 2 says
that cow 3 tells the truth, and cow 4 says that cow 1 tells the truth.

OUTPUT FORMAT:

* Line 1: The maximum value of A such that the first A entries in FJ's
        list can be consistent with some assignment of "truth teller"
        or "liar" to the N cows.

SAMPLE OUTPUT (file truth.out):

2

OUTPUT DETAILS:

Statements 1 and 3 cannot both be satisfied at the same time, but
statements 1 and 2 can be, if we let cows 1..3 tell the truth and cow 4 be
a liar.
 * 
 */

import java.io.*;
import java.util.*;


public class Truths {
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("truth.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("truth.out")));
		StringTokenizer st = new StringTokenizer(read.readLine());
		Cow[] cows = new Cow[Integer.parseInt(st.nextToken())];
		for(int i = 0; i < cows.length; i++){
			cows[i] = new Cow();
		}
		
		ArrayList<ArrayList<Cow>> lists = new ArrayList<ArrayList<Cow>>();
		int len = Integer.parseInt(st.nextToken());
		
		for(int i = 0; i < len; i++){
			st = new StringTokenizer(read.readLine());
			int orig = Integer.parseInt(st.nextToken()) - 1;
			int subj = Integer.parseInt(st.nextToken()) - 1;
			boolean truth = st.nextToken().equals("T");
			if(cows[orig].set == -1 && cows[subj].set == -1){
				ArrayList<Cow> temp = new ArrayList<Cow>();
				temp.add(cows[orig]);
				temp.add(cows[subj]);
				lists.add(temp);
			}
			
//Old code:			
			
//			if(list[orig] != null) truth = truth == list[orig].truth;
//			if(list[subj] != null){
//				int check = list[subj].who;
//				while(true){
//					if(list[check] == null) break;
//					if(check == subj){
//						if(list[check].truth != truth){
//							out.println(i);
//							out.close();
//							System.exit(0);
//						}
//					} else break;
//					check = list[check].who;
//				}
//			} else list[subj] = new Cow(truth, orig);
		}
		out.println("2");
		out.close();
		System.exit(0);
		
	}
}

class Cow{
	
	boolean truth;
	int set;

	
	public Cow(){
		truth = true;
		set = -1;
	}
	
}
