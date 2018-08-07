/*
ID: ayc22032
LANG: JAVA
TASK: runround
 */

/*
Runaround Numbers
Runaround numbers are integers with unique digits, none of which is zero (e.g., 81362) that also have an interesting
property, exemplified by this demonstration: 

	-If you start at the left digit (8 in our number) and count that number of digits to the right (wrapping back to
	the first digit when no digits on the right are available), you'll end up at a new digit (a number which does not
	end up at a new digit is not a Runaround Number). Consider: 8 1 3 6 2 which cycles through eight digits: 1 3 6 2
	8 1 3 6 so the next digit is 6.
	-Repeat this cycle (this time for the six counts designed by the `6') and you should end on a new digit: 2 8 1 3
	6 2, namely 2. 
	-Repeat again (two digits this time): 8 1 
	-Continue again (one digit this time): 3 
	-One more time: 6 2 8 and you have ended up back where you started, after touching each digit once. If you don't
	end up back where you started after touching each digit once, your number is not a Runaround number. 
	
Given a number M (that has anywhere from 1 through 9 digits), find and print the next runaround number higher than M,
which will always fit into an unsigned long integer for the given test data. 

PROGRAM NAME: runround

INPUT FORMAT

A single line with a single integer, M 

SAMPLE INPUT (file runround.in) 

81361

OUTPUT FORMAT

A single line containing the next runaround number higher than the input value, M. 

SAMPLE OUTPUT (file runround.out)

81362

 */

import java.io.*;
import java.util.*;

public class runround {
	
	public static void main(String[] args) throws IOException{
		
		long a = System.currentTimeMillis();
		BufferedReader read = new BufferedReader(new FileReader("runround.in"));
		int start = Integer.parseInt(read.readLine());
		read.close();
		System.out.println(start);
		int ans = 0;
		for(int i = start + 1; ans == 0; i++){
//			if(i % 10 == 1)
//				break;
//			System.out.println(i);
			int check = isAllowed(i);
//			System.out.println(check);
			if(check > 0){
				int temp = pow(check);
				i += temp;
				i /= temp;
				i *= temp;
			}
			if(check == -1 && isRunround(i))
				ans = i;
//			if(check == -1)
//				ans = i;
//			System.out.println(i);
//			System.out.println("--------------------------------");
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("runround.out")));
		System.out.println(ans);
		out.println(ans);
		out.close();
		System.out.println(System.currentTimeMillis() - a);
		System.exit(0);
		
	}
	
	private static int pow(int i){
		int a = 1; 
		for(int j = 0; j < i; j++){
			a *= 10;
		}
		return a;
	}
	
	private static boolean isRunround(int num){
		
		int digits = 0;
		int temp = num;
		while(temp > 0){
			temp /= 10;
			digits++;
		}
		int[] digs = new int[digits];
		temp = num;
		for(int i = digits - 1; i >= 0; i--){
			digs[i] = num % 10;
			num /= 10;
		}
//		for(int i : digs){
//			System.out.print(i + " ");
//		}
//		System.out.println();
		boolean[] locs = new boolean[digits];
		int ind = 0;
		for(int i = 0; i < digits; i++){
			if(locs[ind])
				return false;
			locs[ind] = true;
			ind = (ind + digs[ind]) % digits;
		}
//		if(temp == 134259)
//			System.out.println("!");
		return ind == 0;
		
	}
	
	private static int isAllowed(int num){
		
//		System.out.println("!");
		int[] digs = new int[10];
		for(int i = 0; i < digs.length; i++)
			digs[i] = -1;
		int count = 0;
		while(num > 0){
			int temp = num % 10;
//			System.out.println(temp);
			if(temp == 0)
				return count;
			if(digs[temp] >= 0)
				return digs[temp];
			digs[(int)temp] = count;
			num /= 10;
			count++;
		}
		return -1;
		
	}
	
}
//203239824
//213456789

