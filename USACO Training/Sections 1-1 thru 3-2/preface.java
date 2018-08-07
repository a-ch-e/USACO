/*
ID: ayc22032
LANG: JAVA
TASK: preface
 */

/*

Preface Numbering
A certain book's prefaces are numbered in upper case Roman numerals. Traditional Roman numeral values use a single letter to represent a
certain subset of decimal numbers. Here is the standard set: 
        I   1     L   50    M  1000
        V   5     C  100
        X  10     D  500
As many as three of the same marks that represent 10n may be placed consecutively to form other numbers: 
	-III is 3 
	-CCC is 300 
Marks that have the value 5x10n are never used consecutively. 
Generally (with the exception of the next rule), marks are connected together and written in descending order to form even more numbers: 
	-CCLXVIII = 100+100+50+10+5+1+1+1 = 268 
Sometimes, a mark that represents 10^n is placed before a mark of one of the two next higher values (I before V or X; X before L or C; etc.).
In this case, the value of the smaller mark is SUBTRACTED from the mark it precedes: 
	-IV = 4 
	-IX = 9 
	-XL = 40 
This compound mark forms a unit and may not be combined to make another compound mark (e.g., IXL is wrong for 39; XXXIX is correct). 
Compound marks like XD, IC, and XM are not legal, since the smaller mark is too much smaller than the larger one. For XD (wrong for 490), one
would use CDXC; for IC (wrong for 99), one would use XCIX; for XM (wrong for 990), one would use CMXC. 90 is expressed XC and not LXL, since L
followed by X connotes that successive marks are X or smaller (probably, anyway). 
Given N (1 <= N < 3,500), the number of pages in the preface of a book, calculate and print the number of I's, V's, etc. (in order from lowest
to highest) required to typeset all the page numbers (in Roman numerals) from 1 through N. Do not print letters that do not appear in the page
numbers specified. 
If N = 5, then the page numbers are: I, II, III, IV, V. The total number of I's is 7 and the total number of V's is 2. 

PROGRAM NAME: preface

INPUT FORMAT

A single line containing the integer N. 

SAMPLE INPUT (file preface.in) 

5

OUTPUT FORMAT

The output lines specify, in ascending order of Roman numeral letters, the letter, a single space, and the number of times that letter appears
on preface page numbers. Stop printing letter totals after printing the highest value letter used to form preface numbers in the specified set. 

SAMPLE OUTPUT (file preface.out)

I 7
V 2


 */

import java.util.*;
import java.io.*;

public class preface {

	private static PrintWriter out;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("preface.in"));
		int num = Integer.parseInt(read.readLine());
		read.close();
		
		int[] total = new int[7];
		for(int i = 1; i <= num; i++){
			int[] temp = toRoman(i);
			for(int j = 0; j < temp.length; j++){
				total[j] += temp[j];
			}
		}
		out = new PrintWriter(new BufferedWriter(new FileWriter("preface.out")));
		print(total);
		out.close();
		System.exit(0);
		
	}
	
	private static void print(int[] nums){
		
		String s = "";
		if(nums[6] > 0)
			s = "M " + nums[6] + "\n" + s;
		if(nums[5] > 0 || s.length() > 0)
			s = "D " + nums[5] + "\n" + s;
		if(nums[4] > 0 || s.length() > 0)
			s = "C " + nums[4] + "\n" + s;
		if(nums[3] > 0 || s.length() > 0)
			s = "L " + nums[3] + "\n" + s;
		if(nums[2] > 0 || s.length() > 0)
			s = "X " + nums[2] + "\n" + s;
		if(nums[1] > 0 || s.length() > 0)
			s = "V " + nums[1] + "\n" + s;
		if(nums[0] > 0 || s.length() > 0)
			s = "I " + nums[0] + "\n" + s;
		System.out.println(s);
		out.print(s);
		
	}
	
	private static int[] toRoman(int num){
		
		int[] ans = new int[7];
		while(num >= 1000){
			num -= 1000;
			ans[6]++;
		}
		if(num >= 900){
			ans[6]++;
			ans[4]++;
			num -= 900;
		}
		if(num >= 500){
			ans[5]++;
			num -= 500;
		}
		if(num >= 400){
			ans[5]++;
			ans[4]++;
			num -= 400;
		}
		while(num >= 100){
			num -= 100;
			ans[4]++;
		}
		if(num >= 90){
			num -= 90;
			ans[4]++;
			ans[2]++;
		}
		if(num >= 50){
			num -= 50;
			ans[3]++;
		}
		if(num >= 40){
			num -= 40;
			ans[3]++;
			ans[2]++;
		}
		while(num >= 10){
			num -= 10;
			ans[2]++;
		}
		if(num >= 9){
			num -= 9;
			ans[2]++;
			ans[0]++;
		}
		if(num >= 5){
			num -= 5;
			ans[1]++;
		}
		if(num >= 4){
			num -= 4;
			ans[1]++;
			ans[0]++;
		}
		while(num > 0){
			num--;
			ans[0]++;
		}
		return ans;
		
	}
	
	

}
