/*
 * Allen Cheng
 * TJHSST
 * Addepar Hackathon 2014 Problem 2: Special Numbers
 */

import java.io.*;
import java.util.*;

public class Special {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(read.readLine());
		read.close();
		
		boolean[] prime = new boolean[N + 1];
		int count = 0;
		prime[1] = false;
		for(int i = 2; i <= N; i++){
			double test = Math.sqrt(i);
			prime[i] = true;
			for(int j = 2; j <= test; j++){
				if(i % j == 0){
					prime[i] = false;
					break;
				}
			}
			if(prime[i]) count++;
		}
		int[] primes = new int[count];
		for(int i = 1, ind = 0; i <= N; i++){
			if(prime[i]) primes[ind++] = i;
		}
		
//		int[][] arr = new int[][]{new int[]{1, 2}};
//		int[][] test = new int[1][];
//		System.arraycopy(arr, 0, test, 0, 1);
//		test[0][1]++;
//		System.out.println(arr[0][1]);
		
		long sum = 1;
		boolean[] special = new boolean[N + 1];
		int[][][] facs = new int[N + 1][][];
		special[1] = true;
		
		for(int i = 2; i < facs.length; i++){
			if(prime[i]){
				facs[i] = new int[][]{new int[]{i, 1}};
			}
			int factors = 1;
//			print(facs[i]);
			for(int j = 0; j < facs[i].length; j++){
				factors *= facs[i][j][1] + 1;
			}
			if(!special[factors]){
				special[factors] = true;
				sum += i;
			}
			
			for(int j = 0; j < count && i * primes[j] <= N; j++){
				if(i % primes[j] == 0){
					facs[i * primes[j]] = new int[facs[i].length][2];
					for(int k = 0; k < facs[i].length; k++){
						System.arraycopy(facs[i][k], 0, facs[i * primes[j]][k], 0, facs[i][k].length);
						if(facs[i][k][0] == primes[j]){
//							System.out.println(facs[i * primes[j]][k][1]);
							facs[i * primes[j]][k][1] += 1;
//							System.out.println(facs[i * primes[j]][k][1]);
//							System.out.println("inc " + i * primes[j] + ", " + primes[j]);
//							print(facs[i * primes[j]]);
						}
					}
				} else {
					facs[i * primes[j]] = new int[facs[i].length + 1][2];
					for(int k = 0; k < facs[i].length; k++){
						System.arraycopy(facs[i][k], 0, facs[i * primes[j]][k], 0, facs[i][k].length);
					}
					facs[i * primes[j]][facs[i].length] = new int[]{primes[j], 1};
				}
			}
		}
		
		
		
		
		System.out.println(sum);
		System.exit(0);
		
	}
	
	private static void print(int[][] comp){
		
		System.out.print("{" + Arrays.toString(comp[0]));
		for(int i = 1; i < comp.length; i++){
			System.out.print(", " + Arrays.toString(comp[i]));
		}
		System.out.println("}");
		
	}
	
}
