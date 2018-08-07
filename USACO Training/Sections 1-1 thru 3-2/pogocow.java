/*
Allen Cheng
USACO Silver, November 2013
Problem 3 - Pogo-Cow

Problem 3: Pogo-Cow [Brian Dean, 2013]

In an ill-conceived attempt to enhance the mobility of his prize cow
Bessie, Farmer John has attached a pogo stick to each of Bessie's legs. 
Bessie can now hop around quickly throughout the farm, but she has not yet
learned how to slow down.

To help train Bessie to hop with greater control, Farmer John sets up a
practice course for her along a straight one-dimensional path across his
farm.  At various distinct positions on the path, he places N targets on
which Bessie should try to land (1 <= N <= 1000).  Target i is located at
position x(i), and is worth p(i) points if Bessie lands on it.  Bessie
starts at the location of any target of her choosing and is allowed to move
in only one direction, hopping from target to target.  Each hop must cover
at least as much distance as the previous hop, and must land on a target.

Bessie receives credit for every target she touches (including the initial
target on which she starts).  Please compute the maximum number of points
she can obtain.  

PROBLEM NAME: pogocow

INPUT FORMAT:

* Line 1: The integer N.

* Lines 2..1+N: Line i+1 contains x(i) and p(i), each an integer in
        the range 0..1,000,000.

SAMPLE INPUT (file pogocow.in):

6
5 6
1 1
10 5
7 6
4 8
8 10

INPUT DETAILS:

There are 6 targets.  The first is at position x=5 and is worth 6 points,
and so on.

OUTPUT FORMAT:

* Line 1: The maximum number of points Bessie can receive.

SAMPLE OUTPUT (file pogocow.out):

25

OUTPUT DETAILS:

Bessie hopes from position x=4 (8 points) to position x=5 (6 points) to
position x=7 (6 points) to position x=10 (5 points).

 */

import java.io.*;
import java.util.*;

public class pogocow {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("pogocow.in"));
		int n = Integer.parseInt(read.readLine());
		int[][] targets = new int[n][2];
		for(int i = 0; i < n; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			targets[i][0] = Integer.parseInt(st.nextToken());
			targets[i][1] = Integer.parseInt(st.nextToken());
		}
		read.close();
//		for(int i = 0; i < targets.length; i++){
//			System.out.println("Target at " + targets[i][0] + ", value " + targets[i][1]);
//		}
		
		int ind = 0;
		PogoJump[] jumps = new PogoJump[n * (n - 1) / 2];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(targets[i][0] < targets[j][0])
					jumps[ind++] = new PogoJump(targets[i][0], targets[j][0], targets[j][1], targets[i][1]);
			}
		}
		Arrays.sort(jumps);
//		for(PogoJump j : jumps)
//			System.out.println(j);
		
		Map<Integer, Integer> best = new TreeMap<Integer, Integer>();
		for(int i = 0; i < n; i++)
			best.put(targets[i][0], targets[i][1]);
		for(int i = 0; i < jumps.length; i++){
			jumps[i].maxval += best.get(jumps[i].start);
			if(jumps[i].maxval > best.get(jumps[i].end))
				best.put(jumps[i].end, jumps[i].maxval);
		}
//		System.out.println("-------------------------");
//		for(PogoJump j : jumps)
//			System.out.println(j);
		
		Set<Integer> keyset = best.keySet();
		int max = 0;
		for(int i : keyset)
			max = Math.max(max, best.get(i));
//		System.out.println(max);
		
		//DOUBLE OVERRRR!!!!!!!
		//old version
		
		ind = 0;
		PogoJump[] temp = new PogoJump[n * (n - 1) / 2];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(targets[i][0] > targets[j][0])
					temp[ind++] = new PogoJump(targets[i][0], targets[j][0], targets[j][1], targets[i][1]);
			}
		}
		Arrays.sort(temp);
		
		
		ArrayList<Integer> seps = new ArrayList<Integer>();
		int prevlen = 0;
		for(int i = 0; i < jumps.length; i++){
			if(jumps[i].end - jumps[i].start != prevlen){
				prevlen = jumps[i].end - jumps[i].start;
				seps.add(i);
			}
		}
		seps.add(jumps.length);
//		System.out.println(seps);
		for(int i = 0; i < seps.size() - 1; i++){
			PogoJump.reverse(jumps, seps.get(i), seps.get(i + 1));
		}
//		for(int i = 0; i < jumps.length; i++){
//			System.out.println(jumps[i]);
//		}
//		for(int i = 0; i < 800 && i < seps.size(); i++){
//			System.out.print(seps.get(i) + " ");
//		}
//		System.out.println();
//		System.out.println("\nORIGINAL RE-SORTED:\t\t\tREVERSED:\n");
//		for(int i = 0; i < jumps.length; i++){
//			System.out.println(temp[i] + "       \t\t" + jumps[i]);
//		}
		
//		for(int i = 0; i < 1000 && i < jumps.length; i++){
//			if(temp[i].compareTo(jumps[i]) != 0){
//				System.out.print(/*"Different at index " + */i + " "/*+ " with values " + temp[i] + " and " + jumps[i]*/);
//			}
//		}
//		System.out.println();
		
//		for(PogoJump j : jumps)
//			System.out.println(j);
		
		for(int i = 0; i < n; i++)
			best.put(targets[i][0], targets[i][1]);
		
//		for (PogoJump j : jumps)
//			System.out.println(j);
		
		for(int i = 0; i < jumps.length; i++){
//			System.out.println(best);
			jumps[i].maxval += best.get(jumps[i].start);
//			System.out.println(jumps[i].maxval);
			if(jumps[i].maxval > best.get(jumps[i].end))
				best.put(jumps[i].end, jumps[i].maxval);
//			System.out.println(jumps[i].maxval);
		}
//		System.out.println("-------------------------");
//		for(PogoJump j : jumps)
//			System.out.println(j);
		
		
		for(int i : keyset)
			max = Math.max(max, best.get(i));
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("pogocow.out")));
		System.out.println(max);
		out.println(max);
		out.close();
		System.exit(0);
		
	}
	
}

class PogoJump implements Comparable<PogoJump>{
	
	int start, end;
	int endvalue, startvalue, maxval;
	
	public PogoJump(int a, int b, int p2, int p1){
		
		start = a;
		end = b;
		startvalue = p1;
		endvalue = p2;
		maxval = p2;
		
	}
	
	public String toString(){
		
		return "" + start + " --> " + end + " (" + startvalue + ", " + endvalue + ") Max: " + maxval;
		
	}
	
	public int compareTo(PogoJump other){
		if(start - end == other.start - other.end){
			if(end > start)
				return start - other.start;
			return other.start - start;
		}
		return Math.abs(end - start) - Math.abs(other.end - other.start);
	}
	
	public static void reverse(PogoJump[] jumps, int start, int end){
		
		for(int i = start; i < end; i++){
			jumps[i].maxval = jumps[i].startvalue;
			
			int temp = jumps[i].start;
			jumps[i].start = jumps[i].end;
			jumps[i].end = temp;
			temp = jumps[i].startvalue;
			jumps[i].startvalue = jumps[i].endvalue;
			jumps[i].endvalue = temp;
//			System.out.println(jumps[i]);
		}
		
		for(int i = start; i < (start + end) / 2; i++){
			
//			System.out.println("Swapping " + i + ", " + (end + start - i - 1));
			
			PogoJump temp2 = jumps[i];
			jumps[i] = jumps[end + start - i - 1];
			jumps[end + start - i - 1] = temp2;
			
		}
//		for(int i = start; i < end; i++){
//			System.out.println(jumps[i]);
//		}
		
	}
}