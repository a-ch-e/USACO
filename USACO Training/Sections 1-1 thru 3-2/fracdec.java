/*
ID: ayc22032
LANG: JAVA
TASK: fracdec
 */

/*

Fractions to Decimals
Write a program that will accept a fraction of the form N/D, where N is the numerator and D is
the denominator and print the decimal representation. If the decimal representation has a
repeating sequence of digits, indicate the sequence by enclosing it in brackets. For example,
1/3 = .33333333...is denoted as 0.(3), and 41/333 = 0.123123123...is denoted as 0.(123). Use
xxx.0 to denote an integer. Typical conversions are:

1/3     =  0.(3)
22/5    =  4.4
1/7     =  0.(142857)
2/2     =  1.0
3/8     =  0.375
45/56   =  0.803(571428)

PROGRAM NAME: fracdec

INPUT FORMAT

A single line with two space separated integers, N and D, 1 <= N,D <= 100000.

SAMPLE INPUT (file fracdec.in)

45 56

OUTPUT FORMAT

The decimal expansion, as detailed above. If the expansion exceeds 76 characters in length,
print it on multiple lines with 76 characters per line.

SAMPLE OUTPUT (file fracdec.out)

0.803(571428)

 */

import java.io.*;
import java.util.StringTokenizer;

public class fracdec {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("fracdec.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int num = Integer.parseInt(st.nextToken());
		int den = Integer.parseInt(st.nextToken());
		read.close();
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fracdec.out")));
		
		int pre = 0;
		if(num >= den){
			pre = num / den;
			num = num % den;
		}
		if(num == 0){
			System.out.println(pre + ".0");
			out.println(pre + ".0");
			out.close();
			System.exit(0);
		}
		
		int[] locs = new int[den];
		StringBuilder sb = new StringBuilder();
//		locs[num] = 1;
//		int rem = num * 10;
//		boolean mult = false;
//		for(int i = 2; ; i++){
//			for(int j = 0; j < locs.length; j++){
//				System.out.print(locs[j] + " ");
//			}
//			System.out.println();
//			if(locs[rem % den] > 0){
//				s += rem / den;
//				System.out.print(pre + ".");
//				out.print(pre + ".");
//				System.out.print(s.substring(0, locs[rem % den] - 1) + "(" + s.substring(locs[rem % den] - 1) + ")");
//				out.print(s.substring(0, locs[rem % den] - 1) + "(" + s.substring(locs[rem % den] - 1) + ")");
//				out.close();
//				System.exit(0);
//			}
//			if(!mult)
//				locs[rem % den] = i;
//			mult = false;
//			if(rem < den){
//				rem *= 10;
//				s += "0";
//				mult = true;
//				i--;
//			} else if(rem % den == 0){
//				s += rem / den;
//				break;
//			} else {
//				s += rem / den;
//				rem = rem % den;
//				rem *= 10;
//			}
//		}
		int rem = num * 10;
		for(int i = 1; ; i++){
			if(locs[rem / 10] > 0){
				String str = "";
				str += pre + ".";
				String s = sb.toString();
				str += s.substring(0, locs[rem / 10] - 1) + "(" + s.substring(locs[rem / 10] - 1) + ")";
				for(int j = 0; j <= str.length() / 76; j++){
					System.out.println(str.substring(j * 76, Math.min(str.length(), (j + 1) * 76)));
					out.println(str.substring(j * 76, Math.min(str.length(), (j + 1) * 76)));
				}
				out.close();
				System.exit(0);
			}
			locs[rem / 10] = i;
			if(rem < den){
				rem *= 10;
				sb.append("0");
			} else if(rem % den == 0){
				sb.append(rem / den);
				break;
			} else {
				sb.append(rem / den);
				rem %= den;
				rem *= 10;
			}
		}
		
		String str = "";
		str += pre + ".";
		str += sb.toString();
		for(int j = 0; j <= str.length() / 76; j++){
			System.out.println(str.substring(j * 76, Math.min(str.length(), (j + 1) * 76)));
			out.println(str.substring(j * 76, Math.min(str.length(), (j + 1) * 76)));
		}
		
		out.close();
		System.exit(0);
		
	}
	
}
