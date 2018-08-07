/*
ID: ayc22032
LANG: JAVA
TASK: rockers
*/

import java.io.*;
import java.util.*;

public class rockers {

	private static int[] pow2;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("rockers.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int T = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[] songs = new int[N];
		st = new StringTokenizer(read.readLine());
		for(int i = 0; i < N; i++){
			songs[i] = Integer.parseInt(st.nextToken());
		}
		read.close();
		
		pow2 = new int[N + 1];
		pow2[0] = 1;
		for(int i = 1; i < pow2.length; i++){
			pow2[i] = 2 * pow2[i - 1];
		}
		
		boolean[] poss = new boolean[pow2[N]];
		int best = 0;
		for(int i = 0; i < poss.length; i++){
			int disks = 0;
			int onDisk = T;
			int count = 0;
			boolean bad = false;
			for(int j = 0; j < N; j++){
				if((pow2[j] & i) != pow2[j])
					continue;
				if(songs[j] > T){
					bad = true;
					break;
				}
				count++;
				if(songs[j] <= T - onDisk){
					onDisk += songs[j];
				} else {
					disks++;
					onDisk = songs[j];
					if(disks > M){
						bad = true;
						break;
					}
				}
			}
			if(bad) continue;
//			if(count == 5)
//				System.out.println(Arrays.toString(toArr(i, N)));
			best = Math.max(best, count);
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("rockers.out"));
		out.println(best);
		System.out.println(best);
		out.close();
		System.exit(0);
		
	}
	
	private static boolean[] toArr(int a, int N){
		boolean[] arr = new boolean[N];
		for(int i = N - 1; i >= 0; i--){
			arr[i] = a % 2 == 1;
			a /= 2;
		}
		return arr;
	}
	
}
