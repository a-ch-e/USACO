//lol @ black friday

import java.util.*;
import java.io.*;

public class JUMP {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		long need = Long.parseLong(st.nextToken());
		long curr = Long.parseLong(st.nextToken());
		long[] grants = new long[N];
		for(int i = 0; i < N; i++){
			grants[i] = Long.parseLong(read.readLine());
		}
		read.close();

		Arrays.sort(grants);
		int count = 0;
		for(count = 0; count < N; count++){
			if(curr >= need){
				System.out.println(count);
				System.exit(0);
			}
			curr += grants[N - 1 - count];
		}
		if(curr >= need){
			System.out.println(count);
		} else {
			System.out.println("-1");
		}
		
		System.exit(0);
		
	}
	
}
