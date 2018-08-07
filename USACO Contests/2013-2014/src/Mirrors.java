import java.io.*;
import java.util.*;
/*
 * Problem 1: Mirrors [Brian Dean and Travis Hance, 2013]

Farmer John's cows have been causing too much trouble around the farm, and
FJ therefore wants to keep a more watchful eye on them.  By installing N
reflective fences (1 <= N <= 200) at various locations on the farm, he
hopes to be able to see from his house at location (0,0) to the barn at
location (a,b).

On a 2D map of FJ's farm, fence i appears as a short line segment centered
at integer location (x_i, y_i) and tilted 45 degrees (either like '/' or
like '\').  For example, a fence oriented like '/' at position (3,5) could
be described as a line segment from (2.9,4.9) to (3.1,5.1).  Each fence
(and also the location of the barn) lies at a different position with
integer coordinates in the range -1,000,000...1,000,000.  No fence lies at
(0,0) or (a,b).

FJ plans to sit at his house at position (0,0) and look directly to the
right (in the +x direction).  With his gaze bouncing off some of the
reflective fences on his farm, he hopes to be able to see the point (a,b). 
Unfortunately, FJ thinks he oriented one of his fences incorrectly (e.g.,
'\' instead of '/').  Please output the index of the first fence in FJ's
list such that by toggling its direction (between '/' and '\' or vice
versa), FJ will be able to see the point (a,b).  

If FJ can already see the point (a,b) without toggling any fence, please
output 0.  If it is still impossible for him to see (a,b) even after
toggling up to a single fence, output -1.

PROBLEM NAME: mirrors

INPUT FORMAT:

* Line 1: Three space-separated integers, N, a, and b.

* Lines 2..1+N: Line i+1 describes fence i and contains either "x_i
        y_i /" or "x_i y_i \", where (x_i, y_i) is the location of the
        center of the fence, and \ or / refers to its orientation.

SAMPLE INPUT (file mirrors.in):

5 6 2
3 0 /
0 2 /
1 2 /
3 2 \
1 3 \

INPUT DETAILS:

A map of the farm looks like this (with H denoting FJ's house and B
denoting the barn):
3 .\.....
2 //.\..B
1 .......
0 H../...
  0123456

OUTPUT FORMAT:

* Line 1: The index of the first fence for which toggling that fence
        allows FJ to see the point (a,b).  If FJ can already see the
        point (a,b), please output 0, or if there is no way he can see
        (a,b) even after toggling up to one fence, please output -1.

SAMPLE OUTPUT (file mirrors.out):

4

OUTPUT DETAILS:

By toggling the fence at position (3,2), FJ can see the point (a,b).  On
the map:
3 .\.....
2 //./--B
1 ...|...
0 H--/...
  0123456
 */
public class Mirrors {
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("mirrors.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("mirrors.out")));
		StringTokenizer st = new StringTokenizer(read.readLine());
		Mirror[] list = new Mirror[Integer.parseInt(st.nextToken()) + 1];
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		list[0] = new Mirror(x, y, true, true);
		for(int i = 1; i < list.length; i++){
			st = new StringTokenizer(read.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			boolean c = st.nextToken().equals("/");
			list[i] = new Mirror(a, b, c, false);
		}
		
		Mirror[] listX = Arrays.copyOf(list, list.length);
		Mirror[] listY = Arrays.copyOf(list, list.length);
		Arrays.sort(listX, new XComp());
		Arrays.sort(listY, new  YComp());
		
		System.out.println("List contents:");
		System.out.println("Checkpoint - " + list[0]);
		for(int i = 1; i < list.length; i++){
			System.out.println(list[i]);
		}
		
		System.out.println("listY contents:");
		System.out.println("Checkpoint - " + list[0]);
		for(int i = 1; i < listY.length; i++){
			System.out.println( i + ": " + listY[i]);
		}
		
		System.out.println("Checking current:");
		Direction curDir = new Direction(1, 0, 0);
		Mirror prev = new Mirror(0, 0, true, false);
		while(true){
			prev = curDir.ray(listX, listY, prev);
			if(prev == null){
				break;
			}
			if (prev.isBarn()){
				out.println("0");
				out.close();
				System.exit(0);
			}
			if(curDir.getDirection() == 1 && curDir.getX() < 0 && curDir.getY() == 0) break;
			System.out.println(prev);
			curDir.reflect(prev);
		}
		
		for(int i = 1; i < list.length; i++){
			System.out.println();
			System.out.println("Flipping mirror " + (i - 1));
			prev = new Mirror(0, 0, true, false);
			curDir = new Direction(1, 0, 0);
			list[i].flip();
			while(true){
				prev = curDir.ray(listX, listY, prev);
				if(prev == null){
					break;
				}
				if (prev.isBarn()){
					out.println(i);
					out.close();
					System.exit(0);
				}
				if(curDir.getDirection() == 1 && curDir.getX() < 0 && curDir.getY() == 0) break;
				System.out.println(prev + "\n");
				curDir.reflect(prev);
			}
			list[i].flip();
		}
		out.println("-1");
		out.close();
		System.exit(0);
		
	}
	
}

class Point{
	
	public int x, y;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
}

class Direction{
		
	private int dir;
	private int x, y;
	
	public Direction(int d, int x, int y){
		dir = d;
		this.setX(x);
		this.setY(y);
	}
	
	public void reflect(Mirror m){
		
		setX(m.x);
		setY(m.y);
		boolean r = m.orient();
		if(r){
			if(dir % 2 == 0){
				dir += 1;
				dir = dir % 4;
			} else {
				dir += 3;
				dir = dir % 4;
			}
		} else {
			if(dir % 2 == 1){
				dir += 1;
				dir = dir % 4;
			} else {
				dir += 3;
				dir = dir % 4;
			}
		}
		
		
	}
	
	public Mirror ray(Mirror[] x, Mirror[] y, Mirror prev){
		
		System.out.println(dir);
		Mirror close = null;
		Mirror[] list;
		Comparator<Mirror> comp;
		if(dir % 2 == 0){
			list = x;
			comp = new XComp();
		} else {
			list = y;
			comp = new YComp();
		}
		int check = Arrays.binarySearch(list, prev, comp);
		System.out.println(check);
		int fw = check + 1;
		int bw = check - 1;
		if(check < 0){
			check = check*-1 - 1;
			fw = check;
			bw = check - 1;
		}
		System.out.println("Next: " + fw);
		System.out.println("Prev: " + bw);
		switch(dir){
		case 0:
			if(fw >= list.length) return close;
			Mirror m = list[fw];
			if(m.x == this.getX()) close = m;
			break;
		case 1:
			if(fw >= list.length) return close;
			m = list[fw];
			if(m.y == this.getY()) close = m;
			break;
		case 2:
			if(bw < 0) return close;
			m = list[bw];
			if(m.x == this.getX()) close = m;
			break;
		default:
			if(bw < 0) return close;
			m = list[bw];
			if(m.y == this.getY()) close = m;
			break;		
		}
		return close;
	}
	
	public int getDirection(){
		return dir;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}

class Mirror{
	
	public int x, y;
	private boolean right;
	private boolean barn;
	
	public Mirror(int a, int b, boolean angle, boolean barn){
		x = a;
		y = b;
		right = angle;
		this.barn = barn;
	}
	
	public void flip(){
		right = !right;
	}
	
	public boolean orient(){
		return right;
	}
	
	public boolean isBarn(){
		return barn;
	}
	
	public String toString(){
		String s = "[";
		s += x;
		s += ", ";
		s += y;
		s += "]";
		if(right) s += " RIGHT";
		else s += " LEFT";
		return s;
	}
	
}

class XComp implements Comparator<Mirror>{

	@Override
	public int compare(Mirror o1, Mirror o2) {
		int comp1 = o1.x - o2.x;
		if(comp1 != 0) return comp1;
		return o1.y - o2.y;
	}
	
}

class YComp implements Comparator<Mirror>{

	@Override
	public int compare(Mirror o1, Mirror o2) {
		int comp1 = o1.y - o2.y;
		if(comp1 != 0) return comp1;
		return o1.x - o2.x;
	}
	
}