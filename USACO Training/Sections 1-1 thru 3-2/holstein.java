/*
ID: ayc22032
LANG: JAVA
TASK: holstein
 */

/*

Healthy Holsteins

Burch & Kolstad

Farmer John prides himself on having the healthiest dairy cows in the world. He knows the vitamin content for one scoop of each feed type
and the minimum daily vitamin requirement for the cows. Help Farmer John feed his cows so they stay healthy while minimizing the number of
scoops that a cow is fed.
Given the daily requirements of each kind of vitamin that a cow needs, identify the smallest combination of scoops of feed a cow can be fed
in order to meet at least the minimum vitamin requirements.
Vitamins are measured in integer units. Cows can be fed at most one scoop of any feed type. It is guaranteed that a solution exists for all
contest input data.

PROGRAM NAME: holstein

INPUT FORMAT

Line 1:	integer V (1 <= V <= 25), the number of types of vitamins
Line 2:	V integers (1 <= each one <= 1000), the minimum requirement for each of the V vitamins that a cow requires each day
Line 3:	integer G (1 <= G <= 15), the number of types of feeds available
Lines 4..G+3:	V integers (0 <= each one <= 1000), the amount of each vitamin that one scoop of this feed contains. The first line of these G lines describes feed #1; the second line describes feed #2; and so on.

SAMPLE INPUT (file holstein.in)

4
100 200 300 400
3
50   50  50  50
200 300 200 300
900 150 389 399

OUTPUT FORMAT

The output is a single line of output that contains:
	-the minimum number of scoops a cow must eat, followed by:
	-a SORTED list (from smallest to largest) of the feed types the cow is given
	
If more than one set of feedtypes yield a minimum of scoops, choose the set with the smallest feedtype numbers.

SAMPLE OUTPUT (file holstein.out)

2 1 3


 */

import java.io.*;
import java.util.*;

public class holstein {
	
	private static ArrayList<Integer> solution;
	private static int[][] feeds;
	private static int[] goal;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("holstein.in"));
		int vNum = Integer.parseInt(read.readLine());
		goal = new int[vNum];
		StringTokenizer st = new StringTokenizer(read.readLine());
		for(int i = 0; i < vNum; i++)
			goal[i] = Integer.parseInt(st.nextToken());
		int fNum = Integer.parseInt(read.readLine());
		feeds = new int[fNum][vNum];
		for(int i = 0; i < feeds.length; i++){
			st = new StringTokenizer(read.readLine());
			for(int j = 0; j < feeds[i].length; j++){
				feeds[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		read.close();
		solution = new ArrayList<Integer>();
		
		for(int i = 1; i <= fNum; i++){
			if(findAns(i, new int[vNum]))
				break;
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("holstein.out")));
		System.out.print(solution.size() + " ");
		out.print(solution.size() + " ");
		String s = "" + (solution.get(0) + 1);
		for(int i = 1; i < solution.size(); i++)
			s += " " + (solution.get(i) + 1);
		System.out.println(s);
		out.println(s);
		
		out.close();
		System.exit(0);
		
	}
	
	private static boolean findAns(int level, int[] state){
		
		int start = 0;
		if(solution.size() > 0)
			start = solution.get(solution.size() - 1) + 1;
		for(int i = start; i < feeds.length; i++){
			int[] temp = Arrays.copyOf(state, state.length);
			for(int j = 0; j < feeds[i].length; j++)
				temp[j] += feeds[i][j];
			solution.add(i);
			boolean works = true;
			for(int j = 0; j < temp.length; j++){
				if(temp[j] < goal[j]){
					works = false;
					break;
				}
			}
			if(works)
				return true;
			boolean a = false;
			if(level > 1)
				a = findAns(level - 1, temp);
			if(a)
				return true;
			solution.remove(solution.size() - 1);
		}
		return false;
	}
	
}
