/*
Allen Cheng
2015 US Open, Gold Division
Problem 3: Trapped in the Haybales
 */

/*
Farmer John has received a shipment of N large hay bales (1<=N<=100,000), and placed them at various
locations along the road leading to his barn. Unfortunately, he completely forgets that Bessie the
cow is out grazing along the road, and she may now be trapped within the bales!

Each bale j has a size Sj and a position Pj giving its location along the one-dimensional road.
Bessie the cow can move around freely along the road, even up to the position at which a bale is
located, but she cannot cross through this position. As an exception, if she runs in the same
direction for D units of distance, she builds up enough speed to break through and permanently
eliminate any hay bale of size strictly less than D. Of course, after doing this, she might open
up more space to allow her to make a run at other hay bales, eliminating them as well.

Bessie can escape to freedom if she can eventually break through either the leftmost or rightmost
hay bale. Please compute the total area of the road consisting of real-valued starting positions
from which Bessie cannot escape.

INPUT FORMAT (file trapped.in):

The first line of input contains N. Each of the next N lines describes a bale, and contains two
integers giving its size and position, each in the range 1...10^9. All positions are distinct.

OUTPUT FORMAT (file trapped.out):

Print a single integer, giving the area of the road from which Bessie cannot escape.

SAMPLE INPUT:

5
8 1
1 4
8 8
7 15
4 20

SAMPLE OUTPUT:

14

[Problem credits: Brian Dean, 2015]
 */

import java.io.*;
import java.util.*;

public class Trapped {

	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("trapped.in"));
		TreeSet<Bale> bales = new TreeSet<Bale>();
		TreeSet<Integer> locs = new TreeSet<Integer>();
		int N = Integer.parseInt(read.readLine());
		for(int i = 0; i < N; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			int s = Integer.parseInt(st.nextToken());
			int l = Integer.parseInt(st.nextToken());
			bales.add(new Bale(s, l));
			locs.add(l);
		}
		read.close();
		
		TreeSet<Interval> temp = new TreeSet<Interval>();
		for(Bale b : bales){
			int bot = locs.ceiling(b.loc - b.size);
			int top = locs.floor(b.loc + b.size);
			locs.remove(b.loc);
			if(bot == top)
				continue;
			temp.add(new Interval(bot, top));
		}
		ArrayList<Interval> intervals = new ArrayList<Interval>(temp);
		for(int i = 0; i < intervals.size(); i++){
			if(i > 0 && intervals.get(i).a <= intervals.get(i - 1).b){
				int newA = intervals.get(i - 1).a;
				int newB = Math.max(intervals.get(i - 1).b, intervals.get(i).b);
				intervals.set(i - 1, new Interval(newA, newB));
				intervals.remove(i);
				i--;
			}
		}
		
		int area = 0;
		for(Interval i : intervals){
			area += i.b - i.a;
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("trapped.out"));
		out.println(area);
		System.out.println(area);
		out.close();
		System.exit(0);
		
	}
	
}

class Bale implements Comparable<Bale>{
	int size, loc;
	public Bale(int s, int l){
		size = s; loc = l;
	}
	
	public int compareTo(Bale o){
		if(this.size == o.size)
			return this.loc - o.loc;
		return this.size - o.size;
	}
}

class Interval implements Comparable<Interval>{
	int a, b;
	public Interval(int start, int end){
		a = start;
		b = end;
	}
	
	public int compareTo(Interval o){
		if(a == o.a)
			return b - o.b;
		return a - o.a;
	}
}

/*class endComp implements Comparator<Interval>{
	
	public int compare(Interval x, Interval y){
		if(x.b == y.b)
			return x.a - y.a;
		return x.b - y.b;
	}
	
}
*/