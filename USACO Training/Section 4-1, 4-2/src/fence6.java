/*
ID: ayc22032
LANG: JAVA
TASK: fence6
*/

import java.io.*;
import java.util.*;

public class fence6 {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("fence6.in"));
		int N = Integer.parseInt(read.readLine());
		Map<Integer, Integer> fences = new TreeMap<Integer, Integer>();
		TreeSet<FencePost> posts = new TreeSet<FencePost>();
		for(int i = 0; i < N; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			int fenceNum = Integer.parseInt(st.nextToken());
			fences.put(fenceNum, Integer.parseInt(st.nextToken()));
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int[] aFences = new int[A + 1];
			st = new StringTokenizer(read.readLine());
			for(int j = 0; j < A; j++){
				aFences[j] = Integer.parseInt(st.nextToken());
			}
			aFences[A] = fenceNum;
			int[] bFences = new int[B + 1];
			st = new StringTokenizer(read.readLine());
			for(int j = 0; j < B; j++){
				bFences[j] = Integer.parseInt(st.nextToken());
			}
			bFences[B] = fenceNum;
			posts.add(new FencePost(aFences));
			posts.add(new FencePost(bFences));
		}
		read.close();
		
//		for(FencePost fp : posts){
//			System.out.println(fp);
//		}
		
		int M = posts.size();
		FencePost[] vert = new FencePost[M];
		vert = posts.toArray(vert);
		
		for(int i = 0; i < M; i++){
			for(int j = 0; j < vert[i].fences.length; j++){
				for(int k = i + 1; k < M; k++){
					int ind = Arrays.binarySearch(vert[k].fences, vert[i].fences[j]);
					if(ind >= 0){
						vert[i].dests[j] = k;
						vert[k].dests[ind] = i;
					}
				}
			}
		}
		
		int[][] dists = new int[M][M];
		for(int i = 0; i < M; i++){
			for(int j = 0; j < M; j++){
				dists[i][j] = -1;
			}
		}
		dists[0][0] = 0;
		
		int minCycle = Integer.MAX_VALUE;
		for(int i = 1; i < vert.length; i++){
			dists[i][i] = 0;
			FencePost fp = vert[i];
			for(int j = 0; j < fp.fences.length; j++){
				for(int k = j + 1; k < fp.fences.length; k++){
					if(dists[fp.dests[j]][fp.dests[k]] >= 0){
						minCycle = Math.min(dists[fp.dests[j]][fp.dests[k]] + fences.get(fp.fences[j]) + fences.get(fp.fences[k]), minCycle);
					}
				}
			}
			for(int j = 0; j < fp.fences.length; j++){
				for(int k = 0; k < i; k++){
					if(dists[fp.dests[j]][k] >= 0 && (dists[i][k] < 0 || dists[fp.dests[j]][k] + fences.get(fp.fences[j]) < dists[i][k])){
						dists[i][k] = dists[fp.dests[j]][k] + fences.get(fp.fences[j]);
						dists[k][i] = dists[i][k];
					}
				}
			}
			for(int j = 0; j < i; j++){
				for(int k = j + 1; k < i; k++){
					if(dists[j][i] >= 0 && dists[i][k] >= 0 && (dists[j][k] < 0 || dists[j][k] > dists[j][i] + dists[i][k])){
						dists[j][k] = dists[j][i] + dists[i][k];
						dists[k][j] = dists[j][k];
					}
				}
			}
		}
//		printArr(dists);
		PrintWriter out = new PrintWriter(new FileWriter("fence6.out"));
		out.println(minCycle);
		System.out.println(minCycle);
		out.close();
		System.exit(0);
		
	}
	
	private static void printArr(int[][] arr){
		
		System.out.print("[" + Arrays.toString(arr[0]));
		for(int i = 1; i < arr.length; i++){
			System.out.print(", " + Arrays.toString(arr[i]));
		}
		System.out.println();
		
	}
	
}

class FencePost implements Comparable<FencePost>{
	
	int[] fences;
	int[] dests;
	
	public FencePost(int[] f){
		fences = f;
		Arrays.sort(fences);
		dests = new int[fences.length];
	}
	
	public int compareTo(FencePost o){
		if(this.fences.length == o.fences.length){
			for(int i = 0; i < this.fences.length; i++){
				if(fences[i] == o.fences[i])
					continue;
				return fences[i] - o.fences[i];
			}
			return 0;
		}
		return this.fences.length - o.fences.length;
	}
	
	public boolean equals(FencePost o){
		return this.compareTo(o) == 0;
	}
	
	public String toString(){
		return Arrays.toString(fences);
	}
	
}