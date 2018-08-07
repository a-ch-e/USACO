import java.io.*;
import java.util.*;

public class Waffle {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int[] charTotals = new int[M];
		int[][] options = new int[N][K];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(read.readLine());
			for(int k = 0; k < K; k++){
				options[i][k] = Integer.parseInt(st.nextToken()) - 1;
				charTotals[options[i][k]]++;
			}
		}
		read.close();		
		
		
		
		System.exit(0);
		
	}
	
}
