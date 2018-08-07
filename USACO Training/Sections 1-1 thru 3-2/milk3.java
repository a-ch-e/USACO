/*
ID: ayc22032
LANG: JAVA
TASK: milk3
 */

/*

 */

import java.util.*;
import java.io.*;

public class milk3 {

	private static boolean[][][] locs;
	private static boolean[] cases;
	private static int[] capacities;

	public static void main(String[] args) throws IOException {

		BufferedReader read = new BufferedReader(new FileReader("milk3.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"milk3.out")));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int[] buckets = new int[3];
		capacities = new int[3];
		for (int i = 0; i < buckets.length; i++) {
			capacities[i] = Integer.parseInt(st.nextToken());
		}
		buckets[2] = capacities[2];
		read.close();

		locs = new boolean[capacities[0] + 1] [capacities[1] + 1]
				[capacities[2] + 1];
		cases = new boolean[capacities[2] + 1];
		cases[cases.length - 1] = true;
		locs[buckets[0]][buckets[1]][buckets[2]] = true;
		dfs(buckets);
		String s = "";
		for (int i = 0; i < cases.length; i++) {
			if (cases[i])
				s += "" + i + " ";
		}
		s = s.substring(0, s.length() - 1);
		out.println(s);
		System.out.println(s);
		out.close();
		System.exit(0);

	}

	private static void dfs(int[] buckets) {

		for (int i = 0; i < buckets.length; i++) {
			if (buckets[i] == 0)
				continue;
			for (int j = 0; j < buckets.length; j++) {
				if (j == i)
					continue;
				int[] temp = Arrays.copyOf(buckets, buckets.length);
				for(int k = 0; k < temp.length; k++){
					
				}
				pour(temp, i, j);
				if (locs[temp[0]][temp[1]][temp[2]])
					continue;
				locs[temp[0]][temp[1]][temp[2]] = true;
				if (temp[0] == 0)
					cases[temp[2]] = true;
				dfs(temp);
			}
		}

	}

	private static void pour(int[] buckets, int from, int to) {

		int min = Math.min(buckets[from], capacities[to]
				- buckets[to]);
		buckets[from] -= min;
		buckets[to] += min;
//		System.out.format("pour from %d to %d, %d -> %d = %d\n", from, to, buckets[from], min, buckets[to]);
	}

	private static int toInt(MilkBucket[] buckets) {
		int a = 0;
		for (int i = 0; i < buckets.length; i++) {
			int x = buckets[i].cont;
			for (int j = i + 1; j < buckets.length; j++)
				x *= buckets[j].max + 1;
			a += x;
		}
		return a;
	}

}

class MilkBucket {
	int max;
	int cont;

	public MilkBucket(int q, boolean c) {
		max = q;
		cont = 0;
		if (c)
			cont = q;
	}

}
