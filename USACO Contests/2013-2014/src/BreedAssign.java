/*
 * Allen Cheng
 * Bronze Division
 * USACO March Contest
 * Problem 3 - Breed Assignment
 */
/*
 Problem 3: Breed Assignment [Brian Dean, 2013]

 Farmer John has N cows (2 <= N <= 15) that are each one of three different
 breeds: Holsteins, Jerseys, or Guernseys.  

 Unfortunately, FJ cannot remember the exact breeds of his cows!  He does, 
 however, remember a list of K (1 <= K <= 50) relationships between pairs of
 cows; for example, he might remember that cows 1 and 2 have the same breed,
 or that cows 1 and 5 have different breeds.

 Give FJ's list of relationships between pairs of cows, please help him
 compute the number of different possible assignments of breeds to his cows
 (this number could be zero if his list contains contradictory information).

 PROBLEM NAME: assign

 INPUT FORMAT:

 * Line 1: Two space-separated integers: N and K.

 * Lines 2..1+K: Each line describes the relationship between a pair of
 cows x and y (1 <= x,y <= N, x != y).  It is either of the form 
 "S x y", meaning that x and y have the same breed, or "D x y",
 meaning that x and y have different breeds.

 SAMPLE INPUT (file assign.in):

 4 2
 S 1 2
 D 1 3

 INPUT DETAILS:

 There are 4 cows.  Cows 1 and 2 have the same breed, and cows 1 and 3 have
 different breeds.

 OUTPUT FORMAT:

 * Line 1: The number of possible breed assignments.

 SAMPLE OUTPUT (file assign.out):

 18

 OUTPUT DETAILS:

 The following six breed assignments are possible for the first 3 cows: HHG,
 HHJ, GGH, GGJ, JJH, JJG.  For each of these, we can have 3 possible breed
 assignments for the 4th cow, giving a total of 18 different assignments
 consistent with FJ's list.

 */

import java.util.*;
import java.io.*;

public class BreedAssign {

	private static int count;
	private static SetCow[] smallList;

	public static void main(String[] args) throws IOException {

		BufferedReader read = new BufferedReader(new FileReader("assign.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"assign.out")));
		StringTokenizer st = new StringTokenizer(read.readLine());
		SetCow[] cows = new SetCow[Integer.parseInt(st.nextToken())];
		count = 0;
		for (int i = 0; i < cows.length; i++) {
			cows[i] = new SetCow(i);
		}
		int len = Integer.parseInt(st.nextToken());
		for (int i = 0; i < len; i++) {
			st = new StringTokenizer(read.readLine());
			boolean same = st.nextToken().equals("S");
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			if (same) {
				cows[a].addSame(cows[b]);
				cows[b].addSame(cows[a]);
			} else {
				cows[a].addDiff(cows[b]);
				cows[b].addDiff(cows[a]);
			}
		}
		for (int i = 0; i < cows.length; i++)
			cows[i].updateSame(i);
		for (int i = 0; i < cows.length; i++)
			cows[i].updateDiff();
		for (int i = 0; i < cows.length; i++)
			cows[i].checkSets();
		for (int i = 0; i < cows.length; i++) {
			if (!cows[i].safe()) {
				out.println("0");
				read.close();
				out.close();
				System.exit(0);
			}
		}
//		for (int i = 0; i < cows.length; i++)
//			System.out.println(cows[i]);

		TreeSet<SetCow> temp = new TreeSet<SetCow>();
		for (SetCow c : cows)
			temp.add(c);
//		System.out.println(temp.size());
		smallList = new SetCow[temp.size()];
		Iterator<SetCow> it = temp.iterator();
		for (int i = 0; i < smallList.length; i++) {
			smallList[i] = it.next();
			smallList[i].num = i;
//			System.out.println(smallList[i]);
		}
		genPoss(new int[smallList.length], 0);
		out.println(count);
		read.close();
		out.close();
		System.exit(0);

	}

	private static void genPoss(int[] regions, int ind) {
//		System.out.println(ind);
		if(ind >= regions.length){
			if(SetCow.works(regions, smallList)){
				count++;
//				for(int n : regions)
//					System.out.print(n + " ");
//				System.out.println();
			}
			return;
		}
		int[] temp1 = Arrays.copyOf(regions, regions.length);
		int[] temp2 = Arrays.copyOf(regions, regions.length);
		genPoss(regions, ind + 1);
		temp1[ind] = 1;
		genPoss(temp1, ind + 1);
		temp2[ind] = 2;
		genPoss(temp2, ind + 1);
	}

}

class SetCow implements Comparable<SetCow> {

	public int num;
	private TreeSet<SetCow> same;
	private TreeSet<SetCow> diff;
	private boolean updatedS;
	private boolean updatedD;

	public SetCow(int ind) {
		num = ind;
		same = new TreeSet<SetCow>();
		diff = new TreeSet<SetCow>();
		updatedS = false;
		updatedD = false;
	}

	public void addSame(SetCow other) {
		same.add(other);
	}

	public void addDiff(SetCow other) {
		diff.add(other);
	}

	public TreeSet<SetCow> updateSame(int num) {
		if (updatedS)
			return same;
		this.num = num;
		updatedS = true;
		for (SetCow c : same) {
			TreeSet<SetCow> list = c.updateSame(num);
			for (SetCow s : list) {
				same.add(s);
			}
		}
		return same;
	}

	public TreeSet<SetCow> updateDiff() {
		if (updatedD)
			return diff;
		updatedD = true;
		for (SetCow c : same) {
			TreeSet<SetCow> list = c.updateDiff();
			for (SetCow s : list) {
				diff.add(s);
			}
		}
		return diff;
	}

	public void checkSets() {
		TreeSet<SetCow> temp = new TreeSet<SetCow>();
		for (SetCow c : same)
			temp.add(c);
		same = temp;
		TreeSet<SetCow> temp2 = new TreeSet<SetCow>();
		for (SetCow c : diff)
			temp2.add(c);
		diff = temp2;
	}

	public boolean safe() {
		if (diff.size() > 2)
			return false;
		for (SetCow c : diff)
			if (same.contains(c))
				return false;
		return true;
	}

	public int compareTo(SetCow other) {
		if (this.equals(other))
			return 0;
		return num - other.num;
	}

	public String toString() {
		String s = "" + num;
		s += ", Same: [";
		for (SetCow c : same) {
			s += c.num + " ";
		}
		s += "], Different: [";
		for (SetCow c : diff) {
			s += c.num + " ";
		}
		s += "]";
		return s;
	}

	public boolean equals(SetCow other) {
		return num == other.num;
	}
	
	public static boolean works(int[] regions, SetCow[] cows){
		for(SetCow c : cows){
			for(SetCow s : c.diff){
				if(regions[s.num] == regions[c.num]) return false;
			}
		}
		return true;
	}
}
