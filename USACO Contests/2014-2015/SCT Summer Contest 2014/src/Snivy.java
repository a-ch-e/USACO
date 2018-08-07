//yolo

import java.io.*;
import java.util.*;

public class Snivy {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int R = Integer.parseInt(st.nextToken());
		int I = Integer.parseInt(st.nextToken());
		long M = Long.parseLong(st.nextToken());
		long[] current = new long[I];
		long[] costs = new long[I];
		int[][] recipes = new int[R][I + 1];
		st = new StringTokenizer(read.readLine());
		for(int i = 0; i < I; i++){
			current[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(read.readLine());
		for(int i = 0; i < I; i++){
			costs[i] = Integer.parseInt(st.nextToken());
		}
		for(int i = 0; i < R; i++){
			st = new StringTokenizer(read.readLine());
			for(int j = 0; j <= I; j++){
				recipes[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		read.close();
		
		long best = 0;
		for(int r = 0; r < R; r++){
			int[] recipe = recipes[r];
			long puffs = 0;
			long[] remaining = new long[I];
			System.arraycopy(current, 0, remaining, 0, I);
			Long start = Long.MAX_VALUE;
			for(int i = 0; i < I; i++){
				start = Math.min(start, (remaining[i] / recipe[i + 1]));
			}
			puffs = start;
			int stillHas = I;
			for(int i = 0; i < I; i++){
				remaining[i] -= puffs * recipe[i + 1];
				if(remaining[i] == 0){
					stillHas--;
				}
			}
			long cost = 0;
			while(stillHas > 0 && cost <= M){
				for(int i = 0; i < I; i++){
					cost += costs[i] * Math.max(recipe[i + 1] - remaining[i], 0);
					long temp = Math.max(0, (remaining[i] - recipe[i + 1]));
					if(temp == 0 && remaining[i] != 0)
						stillHas--;
					remaining[i] = temp;
				}
				if(cost <= M)
					puffs++;
			}
			long recipeCost = 0;
			for(int i = 0; i < I; i++){
				recipeCost += recipe[i + 1] * costs[i];
			}
			puffs += ((M - cost) / recipeCost);
//			System.out.println(puffs);
			best = Math.max(best, puffs * recipe[0]);
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		out.println(best);
		out.close();
		System.exit(0);
		
	}
	
}
