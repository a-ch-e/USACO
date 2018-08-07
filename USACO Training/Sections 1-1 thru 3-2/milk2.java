/*
ID: ayc22031
LANG: JAVA
PROG: milk2
 */

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * Three farmers rise at 5 am each morning and head for the barn to milk three cows. The first farmer begins milking his cow at time 300 (measured in seconds after 5 am) and ends at time 1000. The second farmer begins at time 700 and ends at time 1200. The third farmer begins at time 1500 and ends at time 2100. The longest continuous time during which at least one farmer was milking a cow was 900 seconds (from 300 to 1200). The longest time no milking was done, between the beginning and the ending of all milking, was 300 seconds (1500 minus 1200).

Your job is to write a program that will examine a list of beginning and ending times for N (1 <= N <= 5000) farmers milking N cows and compute (in seconds):

The longest time interval at least one cow was milked.
The longest time interval (after milking starts) during which no cows were being milked.
 */


public class milk2 {
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("milk2.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk2.out")));
		MilkTime[] times = new MilkTime[Integer.parseInt(read.readLine())];
		for(int i = 0; i < times.length; i++){
			StringTokenizer tok = new StringTokenizer(read.readLine());
			int a = Integer.parseInt(tok.nextToken());
			int b = Integer.parseInt(tok.nextToken());
			times[i] = new MilkTime(a, b);
		}
		Arrays.sort(times);
//		for(int i = 0; i < times.length; i++){
//			System.out.println(times[i]);
//		}
		int start = times[0].startTime();
		int end = times[0].endTime();
		int maxA = 0;
		int maxB = 0;
		if(times.length == 1){
			maxA = times[0].endTime() - times[0].startTime();
		}
		for(int i = 1; i < times.length; i++){
			maxA = Math.max(maxA, end - start);
			if(times[i].startTime() > end){
				start = times[i].startTime();
				maxB = Math.max(maxB, start - end);
				//System.out.println(i);
				end = times[i].endTime();
			} else {
				end = Math.max(end, times[i].endTime());
			}
		}
//		for(int i = 0; i < times.length - 1; i++){
//			maxA = Math.max(maxA, times[i].endTime() - times[i].startTime());
//			if(times[i].within(outer)){
//				continue;
//			}
//			outer = times[i];
//			if(timeA == 0) timeA = times[i].startTime();
//			if(times[i + 1].startTime() <= times[i].endTime()){
//				if(timeA == 0) timeA = times[i].startTime();
//			} else {
//				if(timeA != 0){
//					maxA = Math.max(maxA, times[i].endTime() - timeA);
//					timeA = 0;
//				}
//				maxB = Math.max(maxB, times[i + 1].startTime() - times[i].endTime());
//				
//			}
//		}
		out.print(maxA + " ");
		out.println(maxB);
		out.close();
		System.exit(0);
		
	}
	
}

class MilkTime implements Comparable<MilkTime>{
	
	private int start;
	private int end;
	
	public MilkTime(int a, int b){
		start = a;
		end = b;
	}
	
	public int startTime(){
		return start;
	}
	
	public int endTime(){
		return end;
	}
	
	public int compareTo(MilkTime other){
		return start - other.startTime();
	}
	
	public boolean within(MilkTime outer){
		return start > outer.startTime() && end < outer.endTime();
	}
	
	public String toString(){
		String s = "(";
		s += start + ", ";
		s += end + ")";
		return s;
	}
	
}
