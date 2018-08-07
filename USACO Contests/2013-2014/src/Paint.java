

import java.io.*;
import java.util.*;


/*
 * Problem 2: Painting the Fence (Bronze) [Brian Dean, 2013]

Farmer John has devised a brilliant method to paint the long fence next to
his barn (think of the fence as a one-dimensional number line).  He simply
attaches a paint brush to his favorite cow Bessie, and then retires to
drink a cold glass of water as Bessie walks back and forth across the
fence, applying paint to any segment of the fence that she walks past.

Bessie starts at position 0 on the fence and follows a sequence of N moves
(1 <= N <= 100,000).  Example moves might be "10 L", meaning   moves
10 units to the left, or "15 R", meaning Bessie moves 15 units to the
right.  Given a list of all of Bessie's moves, FJ would like to know what
area of the fence gets painted with at least two coats of paint (since
areas painted with only one coat of paint may wash off during heavy rain).
Bessie will move at most 1,000,000,000 units away from the origin during
her walk.

PROBLEM NAME: paint

INPUT FORMAT:

* Line 1: The integer N.

* Lines 2..1+N: Each line describes one of Bessie's N moves (for
        example, "15 L").

SAMPLE INPUT (file paint.in):

6
2 R
6 L
1 R
8 L
1 R
2 R

INPUT DETAILS:

Bessie starts at position 0 and moves 2 units to the right, then 6 to the
left, 1 to the right, 8 to the left, and finally 3 to the right.  

OUTPUT FORMAT:

* Line 1: The total area covered by at least 2 coats of paint.

SAMPLE OUTPUT (file paint.out):

6

OUTPUT DETAILS:

6 units of area are covered by at least 2 coats of paint.  This includes
the intervals [-11,-8], [-4,-3], and [0,2].
 */

public class Paint {
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("paint.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("paint.out")));
		int len = Integer.parseInt(read.readLine());
		int ind = 0;
		Interval log = new Interval(0, 0);
		ArrayList<Interval> list = new ArrayList<Interval>();
		for(int i = 0; i < len; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			int move = Integer.parseInt(st.nextToken());
			if(st.nextToken().equals("L")) move *= -1;
			Interval in = new Interval(ind, ind + move);
			System.out.println("Log: " + log);
			System.out.println(in);
			boolean add = true;
			if(move > 0){
				if(ind == log.end()){
					log = Interval.merge(log, in);
					add = false;
				} else if(ind + move > log.end()){
					Interval temp = in;
					in = new Interval(ind, log.end());
					log = Interval.merge(log, temp);
				}
			} else {
				if(ind == log.start()){
					log = Interval.merge(log, in);
					add = false;
				} else if(ind + move < log.start()){
					Interval temp = in;
					in = new Interval(log.start(), ind);
					log = Interval.merge(log, temp);
				}
			}
			if(add) in.add(list);
			System.out.println(list + "\n");
			ind += move;
		}
		System.out.println(list);
		int count = 0;
		for(Interval i : list){
			count += i.length();
		}
		out.println(count);
		out.close();
		System.exit(0);
	}
	
}

class Interval implements Comparable<Interval>{
	
	private int start, end;
	
	public Interval(int a, int b){
		start = a;
		end = b;
		if(start > end) swap();
	}
	
	public static Interval merge(Interval x, Interval y){
		
		if(x.start > y.end || y.start > x.end){
			return null;
		}
		return new Interval(Math.min(x.start, y.start), Math.max(x.end, y.end));
	}
	
	public int compareTo(Interval other){
		int comp1 = start - other.start;
		if(comp1 == 0){
			return end - other.end;
		}
		return comp1;
	}
	
	public void add(ArrayList<Interval> list){
		
		if(list.size() == 0){
			list.add(this);
			return;
		}
		int ind = Collections.binarySearch(list, this);
		if(ind >= 0) return;
		ind = -1 * (ind + 1);
		Interval guess = null;
		if(ind > 0) guess = Interval.merge(this, list.get(ind - 1));
		if(guess == null){
			list.add(ind, this);
		} else {
			list.remove(--ind);
			list.add(ind, guess);
		}
		System.out.println(ind);
		while(ind + 1 < list.size()){
			guess = Interval.merge(list.get(ind), list.get(ind + 1));
			if(guess == null){
				return;
			}
			list.set(ind, guess);
			list.remove(ind + 1);
		}
		
	}
	
	public int start(){return start;}
	public int end(){return end;}
	
	public int length(){
		return end - start;
	}
	
	private void swap(){
		int temp = start;
		start = end;
		end = temp;
	}
	
	public String toString(){
		String s = "[";
		s += start;
		s += ", ";
		s += end;
		s += "]";
		return s;
	}
	
}
