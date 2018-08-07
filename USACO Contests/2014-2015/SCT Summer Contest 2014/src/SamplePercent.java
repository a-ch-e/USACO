//yolo

import java.io.*;
import java.util.*;

public class SamplePercent {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		double P = Double.parseDouble(st.nextToken());
		int[] stats = new int[N];
		st = new StringTokenizer(read.readLine());
		for(int i = 0; i < N; i++){
			stats[i] = Integer.parseInt(st.nextToken());
		}
		read.close();
		
		int rat = stats[0];
		Arrays.sort(stats);
		double ind = (double)Arrays.binarySearch(stats, rat);
		String yes = "NO";
		if(ind / N < 1 - P / 100){
			yes = "YES";
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		out.println(yes);
		out.close();
		System.exit(0);
		
	}
	
}
