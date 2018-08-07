/*
ID: ayc22032
LANG: JAVA
TASK: sort3
 */

/*
Sorting a Three-Valued Sequence 
IOI'96 - Day 2

Sorting is one of the most frequently performed computational tasks. Consider the special sorting problem in which the records to be sorted have at most three different key values.
This happens for instance when we sort medalists of a competition according to medal value, that is, gold medalists come first, followed by silver, and bronze medalists come last. 
In this task the possible key values are the integers 1, 2 and 3. The required sorting order is non-decreasing. However, sorting has to be accomplished by a sequence of exchange
operations. An exchange operation, defined by two position numbers p and q, exchanges the elements in positions p and q. 

You are given a sequence of key values. Write a program that computes the minimal number of exchange operations that are necessary to make the sequence sorted. 

PROGRAM NAME: sort3

INPUT FORMAT

Line 1: 	N (1 <= N <= 1000), the number of records to be sorted
Lines 2-N+1: 	A single integer from the set {1, 2, 3}

SAMPLE INPUT (file sort3.in) 

9
2
2
1
3
3
3
2
3
1

OUTPUT FORMAT

A single line containing the number of exchanges required 

SAMPLE OUTPUT (file sort3.out)

4

 */

import java.io.*;
import java.util.*;

public class sort3 {
	
	private static int count;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("sort3.in"));
		int len = Integer.parseInt(read.readLine());
		int[] array = new int[len];
		int[] nums = new int[3];
		for(int i = 0; i < len; i++){
			array[i] = Integer.parseInt(read.readLine());
			if(array[i] == 1)
				nums[0]++;
			else if(array[i] == 2)
				nums[1]++;
			else
				nums[2]++;
		}
		read.close();
		count = 0;
		
		print(array);
		int ind = nums[0];
		for(int i = 0; i < nums[0]; i++){
			if(array[i] == 2){
				while(array[ind] != 1 && ind < nums[0] + nums[1] - 1)
					ind++;
				if(array[ind] != 1)
					break;
				swap(array, i, ind);
			}
		}
		print(array);
		System.out.println(count);
		ind = nums[0] + nums[1];
		for(int i = 0; i < nums[0]; i++){
			if(array[i] == 3){
				while(array[ind] != 1 && ind < len - 1)
					ind++;
				if(array[ind] != 1)
					break;
				swap(array, i, ind);
			}
		}
		print(array);
		System.out.println(count);
		for(int i = 0; i < nums[0]; i++){
			if(array[i] != 1){
				ind = Math.max(i, nums[0]);
				while(array[ind] != 1 && ind < len - 1)
					ind++;
				swap(array, i, ind);
			}
		}
		print(array);
		System.out.println(count);
		for(int i = nums[0]; i < nums[1] + nums[0]; i++){
			if(array[i] != 2)
				count++;
		}
		
		System.out.println(count);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sort3.out")));
		out.println(count);
		out.close();
		System.exit(0);
		
	}
	
	private static void swap(int[] array, int a, int b){
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
		System.out.println("Swapped " + a + " and " + b);
		count++;
	}
	
	private static void print(int[] array){
		for(int i : array)
			System.out.print(i + " ");
		System.out.println();
	}
	
}
