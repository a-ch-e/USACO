/*
 * Allen Cheng
 * ID: ayc22032
 * Lang: JAVA
 * Task: typo
 */

import java.io.*;

public class typo {

	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("typo.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("typo.out")));
		String s = read.readLine();
		int plus = 0;
		int open = 0;
		int rem = 0;
		int bal = 0;
		int poss = 0;
		int temp = s.length();
		for(int i = 0; i < temp; i++){
			if(s.charAt(i) == ')') --bal;
			else {
				if(bal < 2) plus++;
				if(bal == 1 && s.charAt(i + 1) == '(') plus--;
				++bal;
				++open;
			}
			if(bal < 0){
				poss++;
				if(poss == 1) rem = i - open;
			}
			if(bal < -2){
				out.println(0);
				out.close();
				System.exit(0);
			}
		}
		if(temp % 2 == 1){
			out.println(0);
			out.close();
			System.exit(0);
		}
		if(Math.abs(temp / 2 - open) != 1){
			out.println(0);
			out.close();
			System.exit(0);
		}
		if(temp / 2 < open){
			out.println(open - plus);
			out.close();
			System.exit(0);
		}
		if(poss > 0){
			if(poss <= 2){
				out.println(rem + 1);
				out.close();
				System.exit(0);
			} else if(poss % 2 == 1){
				rem++;
				out.println(rem);
				out.close();
				System.exit(0);
			} else {
				out.println(0);
				out.close();
				System.exit(0);
			}
		}
		poss = temp - open - 1;
		out.println(poss);
		out.close();
		System.exit(0);
		
	}
	
}
