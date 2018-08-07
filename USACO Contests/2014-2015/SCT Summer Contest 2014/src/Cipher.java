//yolo

import java.io.*;
import java.util.*;

public class Cipher {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		if(N == 0){
			System.out.println();
			System.exit(0);
		}
		int K = Integer.parseInt(st.nextToken());
		int[] scale = new int[N];
		scale[0] = 1;
		String raw = read.readLine();
		char[] str = raw.toCharArray();
		read.close();
		
		for(int i = 1; i < scale.length; i++){
			scale[i] = (scale[i - 1] * K) % 26;
			if(scale[i] < 0){
				scale[i] += 26;
			}
		}
//		System.out.println(Arrays.toString(scale));
		
		StringBuilder ans = new StringBuilder();
		//97-122
		for(int i = 0; i < N; i++){
			int temp = (int)str[i] - 97;
			temp += scale[i] + 52;
			temp %= 26;
			temp += 97;
			ans.append((char)temp);
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		out.println(ans);
		out.close();
		System.exit(0);
		
	}
	
}
