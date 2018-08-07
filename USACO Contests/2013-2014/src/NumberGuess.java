/*
 * Allen Cheng
 * TJHSST
 * Addepar Hackathon 2014 Problem 4: Guessing Numbers
 */

import java.io.*;
import java.util.*;

public class NumberGuess {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());		DumbLuck[] p = new DumbLuck[N];
		st = new StringTokenizer(read.readLine());
		for(int i = 0; i < N; i++){
			p[i] = new DumbLuck(i, Double.parseDouble(st.nextToken()));
		}
		read.close();
		Arrays.sort(p);
		
		System.exit(0);
		
	}
	
	private static double prob(DumbLuck[] p, int start, int end, int level, int K){
		
		int best = 0;
		for(int i = p.length - level - 1, c = 0; i >= 0; i--){
			if(p[i].n < start || p[i].n >= end)
				continue;
			c++;
			if(c > level){
				best = i;
				break;
			}
		}
		
		return 0;
	}
	
}

class DumbLuck implements Comparable<DumbLuck>{
	
	int n;
	double p;
	
	public DumbLuck(int num, double prob){
		n = num;
		p = prob;
	}
	
	public int compareTo(DumbLuck other){
		if(p == other.p) return n - other.n;
		return (int)(1e9 * (p - other.p));
	}
}

/*
import java.io.*;
import java.util.*;

public class NumberGuess {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		double[] p = new double[N];
		st = new StringTokenizer(read.readLine());
		double prev = 0;
		for(int i = 0; i < N; i++){
			p[i] = prev + Double.parseDouble(st.nextToken());
			prev = p[i];
		}
		read.close();
		int TRIALS = 40001000;
		double success = 0;
		
		for(int m = 0; m < TRIALS; m++){
			double r = Math.random();
			int target = Arrays.binarySearch(p, r);
			if(target < 0){
				target = -(target + 1);
			}
			int first = 0;
			int last = N - 1;
			for(int i = 0; i < K; i++){
				int temp = (last - first) / 2;
				if(temp == target){
					success++;
					break;
				} else {
					if(target < temp){
						first = temp;
					} else {
						last = temp;
					}
				}
			}
			
		}
		System.out.println(success / TRIALS);
		System.exit(0);
		
	}
	
	
}
*/