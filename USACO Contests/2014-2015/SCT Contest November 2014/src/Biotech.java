// lol @ black friday

import java.io.*;
import java.util.*;

public class Biotech {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int S = Integer.parseInt(st.nextToken());
		String[] rules = new String[S];
		int[] diffs = new int[S];
		st = new StringTokenizer(read.readLine());
		int maxLen = 0;
		for(int i = 0; i < S; i++){
			rules[i] = st.nextToken();
			maxLen += rules[i].length();
			if(i < S - 1){
				diffs[i + 1] = Integer.parseInt(st.nextToken());
				maxLen += diffs[i + 1];
			}
		}
		
		boolean[] pages = new boolean[N];
		for(int i = 0; i < N; i++){
			
			String page = read.readLine();
			boolean works = true;
			for(int j = 0; j < page.length() - rules[0].length() && works; j++){
				
				if(page.substring(j, rules[0].length() + j).equals(rules[0])){
					works = fitsRule(page.substring(j + rules[0].length(), Math.min(page.length(), j + maxLen)), rules, diffs, 1);
				}
			}
			
			if(works)
				pages[i] = true;
			
		}
		read.close();
		boolean any = false;
		for(int i = 0; i < N; i++){
			if(any)
				System.out.print(" ");
			if(pages[i])
				System.out.print(i + 1);
			any = true;
		}
		System.out.println();
		System.exit(0);
	}
	
	private static boolean fitsRule(String str, String[] rules, int[] diffs, int level){
		
//		System.out.println(str);
		if(level == diffs.length)
			return true;
		if(str.length() <= diffs[level])
			return true;
		for(int i = 0; i <= diffs[level]; i++){
			for(int j = 0; j < rules[level].length() && j < str.length() - i; j++){
				if(str.charAt(j + i) != rules[level].charAt(j)){
//					System.out.println(j);
					break;
				}
				if(j + 1 == str.length() - i)
					return true;
				if(j + 1 < rules[level].length())
					continue;
				if(level == diffs.length - 1)
					return true;
				if(fitsRule(str.substring(i), rules, diffs, level + 1))
					return true;
			}	
		}
		
		return false;
		
	}
	
}
