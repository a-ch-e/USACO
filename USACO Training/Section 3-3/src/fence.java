/*
ID: ayc22032
LANG: JAVA
TASK: fence
 */

/*

 Riding the Fences

 Farmer John owns a large number of fences that must be repaired annually. He traverses
 the fences by riding a horse along each and every one of them (and nowhere else) and
 fixing the broken parts. Farmer John is as lazy as the next farmer and hates to ride
 the same fence twice. Your program must read in a description of a network of fences
 and tell Farmer John a path to traverse each fence length exactly once, if possible.
 Farmer J can, if he wishes, start and finish at any fence intersection.


 Every fence connects two fence intersections, which are numbered inclusively from 1
 through 500 (though some farms have far fewer than 500 intersections). Any number of
 fences (>=1) can meet at a fence intersection. It is always possible to ride from any
 fence to any other fence (i.e., all fences are "connected").


 Your program must output the path of intersections that, if interpreted as a base 500
 number, would have the smallest magnitude.


 There will always be at least one solution for each set of input data supplied to your
 program for testing.

 PROGRAM NAME: fence

 INPUT FORMAT

 Line 1: The number of fences, F (1 <= F <= 1024) 
 Line 2..F+1: A pair of integers (1 <= i,j <= 500) that tell which pair of intersections this fence connects. 

 SAMPLE INPUT (file fence.in)

 9
 1 2
 2 3
 3 4
 4 2
 4 5
 2 5
 5 6
 5 7
 4 6

 OUTPUT FORMAT

 The output consists of F+1 lines, each containing a single integer. Print the number of the starting intersection on the first line, the next intersection's number on the next line, and so on, until the final intersection on the last line. There might be many possible answers to any given input set, but only one is ordered correctly. 

 SAMPLE OUTPUT (file fence.out)

 1
 2
 3
 4
 2
 5
 4
 6
 5
 7
 */

import java.io.*;
import java.util.*;

public class fence {

	private static int[] ans;
	private static int pos, count;
	private static int absc;
	private static ArrayList<ArrayList<Integer>> adjList;
	private static int[] sizes, visited;

	public static void main(String[] args) throws IOException {

		BufferedReader read = new BufferedReader(new FileReader("fence.in"));
		// BufferedReader read = new BufferedReader(new
		// InputStreamReader(System.in));
		int F = Integer.parseInt(read.readLine());
		adjList = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 500; i++) {
			adjList.add(new ArrayList<Integer>());
		}
		sizes = new int[500];
		visited = new int[500];
		for (int i = 0; i < F; i++) {
			StringTokenizer st = new StringTokenizer(read.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			adjList.get(a).add(b);
			adjList.get(b).add(a);
		}
		for (int i = 0; i < 500; i++) {
			if (adjList.get(i).size() > 1) {
				Collections.sort(adjList.get(i));
			}
			sizes[i] = adjList.get(i).size();
		}
		read.close();

		int start = 0;
		for (int i = 0; i < 500; i++) {
			if (adjList.get(i).size() % 2 == 1) {
				start = i;
				break;
			}
		}
		ans = new int[F + 1];
		pos = 0;
		count = 0;
		absc = 0;
		// tour(start);
		ArrayList<Integer> path = new ArrayList<Integer>(F + 1);
		int curr = start;
		while(!adjList.get(curr).isEmpty()){
			path.add(curr + 1);
			int next = adjList.get(curr).remove(0);
			adjList.get(next).remove(((Integer)curr));
			curr = next;
		}
		path.add(curr + 1);
		for(int i = path.size() - 1; i >= 0; ){
			int point = path.get(i) - 1;
			if(adjList.get(point).isEmpty()){
				i--;
				continue;
			}
			curr = point;
			while(!adjList.get(curr).isEmpty()){
				int next = adjList.get(curr).remove(0);
				adjList.get(next).remove(((Integer)curr));
				curr = next;
				path.add(++i, curr + 1);
			}
			i--;
		}
		// if(pos != F)
		// System.out.println("sumpin rong");
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"fence.out")));
		// PrintWriter out = new PrintWriter(new BufferedWriter(new
		// OutputStreamWriter(System.out)));
//		for (int i = pos - 1; i >= 0; i--) {
//			out.println(ans[i]);
//			// System.out.println(ans[i]);
//		}
		System.out.println(path.size());
		for(int i : path){
			out.println(i);
//			System.out.println(i);
		}
		out.close();
		System.exit(0);

	}

	private static boolean tour(int i) {

		absc++;
//		if(visited[i] > sizes[i]){
//			System.out.println("happenin with " + i + ", " + absc);	
//			return false;
//		}
		
//		System.out.println();
		if (adjList.get(i).isEmpty()) {
			ans[pos++] = i + 1;
//			System.out.println("ping");
			return count == ans.length - 1;
		}
		int bad = 0;
		int len = adjList.get(i).size();
		for(int k = 0; !adjList.get(i).isEmpty(); k++) {
//			if (i == 201 || i == 281) {
//				System.out.println(adjList.get(i));
//				System.out.println();
//			}
			if(bad > 1)
				return false;
			System.out.println(i + 1 + ", " + bad);
			System.out.println("Count: " + count);
			System.out.println(adjList.get(i));
			
			int j = adjList.get(i).remove(0);
			int ind = adjList.get(j).indexOf(i);
			System.out.println(ind);
			System.out.println();
			adjList.get(j).remove(ind);
			count++;
			boolean res = tour(j);

			if (res){
				ans[pos++] = i + 1;
			} else if(adjList.get(i).isEmpty()){
				System.out.println("!");
				adjList.get(i).add(j);
				adjList.get(j).add(ind, i);
				count--;
				return false;
			} else {
				System.out.println("!");
				adjList.get(i).add(j);
				System.out.println(j + ": " + adjList.get(j));
				System.out.println();
				adjList.get(j).add(ind, i);
				count--;
				bad++;
			}
		}
//		if(bad > -1){
//			if (i == 201 || i == 281) {
//				System.out.println(adjList.get(i));
//			}
//			boolean temp = tour(bad);
//			if(!temp)
//				return false;
//			ans[pos++] = i + 1;
//		}
		return true;

	}

}