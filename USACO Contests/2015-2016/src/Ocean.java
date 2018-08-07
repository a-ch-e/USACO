import java.io.*;
import java.util.*;

public class Ocean {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(read.readLine());
		int[] depths = new int[N];
		StringTokenizer st = new StringTokenizer(read.readLine());
		int maxD = 0;
		for(int i = 0; i < N; i++){
			depths[i] = Integer.parseInt(st.nextToken());
			maxD = Math.max(maxD, depths[i]);
		}
		read.close();		
		
		int[] curr = new int[N];
		int max = 0;
		for(int i = 1; i <= maxD; i++){
			for(int j = 0; j < N; j++){
				if(depths[j] < i - 1)
					continue;
				int next = j;
				while(depths[next] >= i)
					next++;
				for(int k = next - 1; k >= j; k--){
					curr[k] += next - j;
					//max = Math.max(curr[k], max);
				}
				j = next;
			}
			//System.out.println(Arrays.toString(mass[i]));
		}
		for(int j = 0; j < N; j++){
			max = Math.max(max, curr[j]);
		}
		System.out.println(max);
		System.exit(0);
		
	}
	
}
