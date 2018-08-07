/*
 * Allen Cheng
 * US Open 2013, Silver Division
 * Problem 1: What's Up With Gravity?
 */
/*
 * 
 Problem 1: What's Up With Gravity? [Mark Gordon, 2013]

 Captain Bovidian is on an adventure to rescue her crew member, Doctor
 Beefalo.  Like all great adventures, this story plays out in a two
 dimensional N by M grid (1 <= N, M <= 500), representing a side view of the
 captain's world.  Some grid cells are empty while others are blocked and
 cannot be traversed.  

 Unfortunately, Captain Bovidian cannot jump.  She must obey the following
 rules of physics while traversing her world.

 1) If there is no cell directly underneath Captain Bovidian (that is, if
 she is at the edge of the grid), then she flies out into space and fails
 her mission.
 2) If the cell directly underneath Captain Bovidian is empty, then she
 falls into that cell.
 3) Otherwise:
 a) Captain Bovidian may move left or right if the corresponding cell
 exists and is empty.
 b) Or, Captain Bovidian may flip the direction of gravity.

 When Captain Bovidian changes the direction of gravity, the cell that's
 'underneath' her (as mentioned in rules 1 and 2) toggles between the cell
 with one higher row index and the cell with one lower row index (the first
 row in the input has index 1, and the last row has index N). Initially, the
 cells with one higher row index are underneath Captain Bovidian.

 Doctor Beefalo is lost somewhere in this world.  Help Captain Bovidian
 arrive at her cell using the least number of gravity flips as possible.  If
 it is impossible to reach Doctor Beefalo, please output -1.

 PROBLEM NAME: gravity

 INPUT FORMAT:

 * Line 1: Two space-separated integers N and M.

 * Lines 2..1+N: Line i+1 describes the ith row of Captain Bovidian's
 world: '.' indicates an empty cell, '#' indicates a blocked
 cell, 'C' indicates Captain Bovidian's starting position, and
 'D' indicates Doctor Beefalo's starting position.

 SAMPLE INPUT (file gravity.in):

 5 5
 #####
 #...#
 #...D
 #C...
 ##.##

 OUTPUT FORMAT:

 * Line 1: A single integer indicating the minimum number of times
 Captain Bovidian must flip gravity to reach Doctor Beefalo, or
 -1 if it is impossible to reach Doctor Beefalo.

 SAMPLE OUTPUT (file gravity.out):

 3

 OUTPUT DETAILS:

 The captain starts at position (4, 2).  She flips gravity and falls to
 position (2, 2) and then moves right twice to arrive at (2, 4).  She flips
 gravity again and falls to position (4, 4) and then moves right once to
 position (4, 5).  Finally she flips gravity again to fall to Doctor
 Beefalo's position at (3, 5).
 */

import java.util.*;
import java.io.*;

public class Gravity {

	public static void main(String[] args) throws IOException {

		BufferedReader read = new BufferedReader(new FileReader("gravity.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"gravity.out")));
		ArrayList<Level> levels = new ArrayList<Level>();
		StringTokenizer st = new StringTokenizer(read.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int cx = 0;
		int cy = 0;
		int dx = 0;
		int dy = 0;
		for (int i = 1; i <= n; i++) {
			String in = read.readLine();
			for (int j = 1; j <= m; j++) {
				char c = in.charAt(j - 1);
				if (c == '#') {
					int a = j;
					while (c == '#' && j < in.length()) {
						j++;
						c = in.charAt(j - 1);
					}
					if (j >= in.length())
						j++;
					levels.add(new Level(a, j - 1, i));
					j--;
				} else if (c == 'C') {
					cx = j;
					cy = i;
				} else if (c == 'D') {
					dy = i;
					dx = j;
				}
			}
		}
		read.close();
//		System.out.println(dx + " " + dy);
		// for(Level l : levels)
		// System.out.println(l);
		// System.out.println("-----------------------------------------------------------------------");
		int captain = -1;
		int doctor = -1;
		for (int i = 0; i < levels.size(); i++) {
			Level l = levels.get(i);
			if (l.left <= cx && l.right >= cx && Math.abs(cy - l.y) == 1) {
				if (captain == -1
						|| l.left - l.right > levels.get(captain).left
								- levels.get(captain).right)
					captain = i;
			}
			if (l.left <= dx && l.right >= dx && Math.abs(dy - l.y) == 1) {
				if (doctor == -1
						|| l.left - l.right > levels.get(doctor).left
								- levels.get(doctor).right)
					doctor = i;
			}
		}
//		System.out.println(captain + " " + doctor);
		if (captain == -1 || levels.get(captain).y < cy || doctor == -1) {
			out.println("-1");
			out.close();
			System.exit(0);
		}
		boolean dAbove = levels.get(doctor).y > dy;
		// System.out.println(levels.get(captain));
//		 System.out.println(doctor);
		// System.out.println(dAbove);
		for (int i = 0; i < levels.size(); i++) {
			Level temp = levels.get(i);
			for (int j = i - 1; j >= 0; j--) {
				if (levels.get(j).y == temp.y)
					continue;
				if (!(levels.get(j).left > temp.right || levels.get(j).right < temp.left)) {
					int lbound = Math.max(temp.left, levels.get(j).left);
					int rbound = Math.min(temp.right, levels.get(j).right);
					boolean add = false;
					for (int k = lbound - temp.left; k <= rbound - temp.left; k++) {
						if (!temp.aboveRanges[k])
							add = true;
						temp.aboveRanges[k] = true;
					}
					if (add && Math.abs(temp.y - levels.get(j).y) > 1)
						temp.above.add(j);
				}
			}
			for (int j = i + 1; j < levels.size(); j++) {
				if (levels.get(j).y == temp.y)
					continue;
				if (!(levels.get(j).left > temp.right || levels.get(j).right < temp.left)) {
					int lbound = Math.max(temp.left, levels.get(j).left);
					int rbound = Math.min(temp.right, levels.get(j).right);
					boolean add = false;
					for (int k = lbound - temp.left; k <= rbound - temp.left; k++) {
						if (!temp.belowRanges[k])
							add = true;
						temp.belowRanges[k] = true;
					}
					if (add && Math.abs(temp.y - levels.get(j).y) > 1)
						temp.below.add(j);
				}
			}
		}
		// for(Level l : levels){
		// System.out.println(l + "\n" + l.above);
		// }
		for (int i = 0; i < levels.size(); i++)
			levels.get(i).makeDist(levels.size(), i);
		for(Level l : levels){
			for(int i : l.above)
				l.belowDistances[i] = 1;
			for(int i : l.below)
				l.aboveDistances[i] = 1;
		}
		for (int i = 2; i <= levels.size()
				&& (dAbove && levels.get(captain).aboveDistances[doctor] == -1 || !dAbove
						&& levels.get(captain).belowDistances[doctor] == -1); i++) {
			for(Level l : levels){
				for(int j : l.above){
					for(int k = 0; k < levels.size(); k++){
						if(levels.get(j).belowDistances[k] == i - 1 && l.belowDistances[k] == -1)
							l.belowDistances[k] = i;
						if(levels.get(j).aboveDistances[k] == i - 1 && l.aboveDistances[k] == -1)
							l.aboveDistances[k] = i;
					}
				}
				for(int j : l.below){
					for(int k = 0; k < levels.size(); k++){
						if(levels.get(j).belowDistances[k] == i - 1 && l.belowDistances[k] == -1)
							l.belowDistances[k] = i;
						if(levels.get(j).aboveDistances[k] == i - 1 && l.aboveDistances[k] == -1)
							l.aboveDistances[k] = i;
					}
				}
				// System.out.print("Above: ");
				// for(int j : l.aboveDistances) System.out.print(j + " ");
				// System.out.print("Below: ");
				// for(int j : l.belowDistances) System.out.print(j + " ");
				// System.out.println();
			}
//			System.out.println("-------------------------------------");
		}
		if(dAbove) out.println(levels.get(captain).aboveDistances[doctor]);
		else out.println(levels.get(captain).belowDistances[doctor]);
		out.close();
		System.exit(0);

	}

}

class Level {

	int left, right, y;
	ArrayList<Integer> above;
	boolean[] aboveRanges;
	ArrayList<Integer> below;
	boolean[] belowRanges;
	int[] aboveDistances;
	int[] belowDistances;

	public Level(int l, int r, int h) {
		left = l;
		right = r;
		y = h;
		above = new ArrayList<Integer>();
		aboveRanges = new boolean[r - l + 1];
		below = new ArrayList<Integer>();
		belowRanges = new boolean[r - l + 1];
	}

	public String toString() {
		String s = "From (" + left + ", " + y + ") to (" + right + ", " + y
				+ ")";
		return s;
	}

	public void makeDist(int s, int ind) {
		aboveDistances = new int[s];
		belowDistances = new int[s];
		for (int i = 0; i < s; i++) {
			if (i != ind) {
				aboveDistances[i] = -1;
				belowDistances[i] = -1;
			}
		}
	}

}