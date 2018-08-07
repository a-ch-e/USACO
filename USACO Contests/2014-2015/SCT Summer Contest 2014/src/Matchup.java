//yolo

import java.io.*;
import java.util.*;

public class Matchup {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(read.readLine());
		double[][] types = new double[N][N];
		for(int i = 0; i < N; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			for(int j = 0; j < N; j++){
				types[i][j] = Double.parseDouble(st.nextToken());
			}
		}
		read.close();
		
		double best = 0;
		double[] temp = new double[N];
		for(int i = 0; i < N; i++){
			for(int t = 0; t < N; t++){
				temp[t] = types[i][t];
			}
			Arrays.sort(temp);
			best = Math.max(temp[temp.length - 1], best);
			if(N > 1)
				best = Math.max(best, temp[temp.length - 1] * temp[temp.length - 2]);
			for(int j = i + 1; j < N; j++){
				for(int t = 0; t < N; t++){
					temp[t] = types[i][t] * types[j][t];
				}
				Arrays.sort(temp);
				best = Math.max(temp[temp.length - 1], best);
				if(N > 1)
					best = Math.max(best, temp[temp.length - 1] * temp[temp.length - 2]);
			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		out.println(best);
		out.close();
		System.exit(0);
		
	}
	
}