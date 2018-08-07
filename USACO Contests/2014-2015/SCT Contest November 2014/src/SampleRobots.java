import java.util.*;
import java.io.*;

public class SampleRobots {
	
	public static void main(String[] args) throws IOException{
		
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(read.readLine());
		int[] avail = new int[N];
		StringTokenizer st = new StringTokenizer(read.readLine());
		for(int i = 0; i < N; i++){
			avail[i] = Integer.parseInt(st.nextToken());
		}
		
		int[] need = new int[N];
		st = new StringTokenizer(read.readLine());
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < N; i++){
			need[i] = Integer.parseInt(st.nextToken());
			if(need[i] == 0)
				continue;
			min = Math.min(avail[i] / need[i], min);
		}
		
		read.close();
		
		System.out.println(min);
		System.exit(0);
		
	}
	
	
}
