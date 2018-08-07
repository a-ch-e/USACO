/*
ID: ayc22032
LANG: JAVA
TASK: nuggets
*/

import java.io.*;
import java.util.*;

public class nuggets {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("nuggets.in"));
		int N = Integer.parseInt(read.readLine());
		int[] units = new int[N];
		for(int i = 0; i < N; i++){
			units[i] = Integer.parseInt(read.readLine());
		}
		read.close();
		
		Arrays.sort(units);
		boolean relPrime = true;
		for(int i = 2; i <= units[0]; i++){
			boolean all = true;
			for(int j = 0; j < units.length; j++){
				if(units[j] % i != 0){
					all = false;
					break;
				}
			}
			if(all){
				relPrime = false;
				break;
			}
		}
		if(!relPrime || N == 1 || units[0] == 1){
			PrintWriter out = new PrintWriter(new FileWriter("nuggets.out"));
			out.println(0);
			System.out.println(0);
			out.close();
			System.exit(0);
		}
		
//		System.out.println("here");
		LinkedList<Boolean> poss = new LinkedList<Boolean>();
		for(int i = 0; i < units[N - 1] - 1; i++){
			poss.add(false);
		}
		poss.add(true);
		
		int lastFalse = units[N - 1] - 2;
		int max = 0;
		for(int j = 1; lastFalse >= 0; j++){
			boolean pack = false;
			for(int i : units){
				if(poss.get(units[N - 1] - i)){
					pack = true;
					break;
				}
			}
			if(!pack) {
				lastFalse = units[N - 1] - 1;
				max = Math.max(j, max);
//				System.out.println(j);
			} else
				lastFalse--;
			poss.pollFirst();
			poss.add(pack);
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("nuggets.out"));
		out.println(max);
		System.out.println(max);
		out.close();
		System.exit(0);
		
	}
	
}
