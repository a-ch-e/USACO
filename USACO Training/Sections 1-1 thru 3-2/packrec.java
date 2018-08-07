/*
ID: ayc22031
LANG: JAVA
PROG: packrec
*/

/*
Packing Rectangles
IOI 95

The six basic layouts of four rectangles

Four rectangles are given. Find the smallest enclosing (new) rectangle into which these four may be fitted without overlapping. By smallest rectangle, we mean the one with the smallest area.

All four rectangles should have their sides parallel to the corresponding sides of the enclosing rectangle. Figure 1 shows six ways to fit four rectangles together. These six are the only possible basic layouts, since any other layout can be obtained from a basic layout by rotation or reflection. Rectangles may be rotated 90 degrees during packing.

There may exist several different enclosing rectangles fulfilling the requirements, all with the same area. You must produce all such enclosing rectangles.
PROGRAM NAME: packrec
INPUT FORMAT

Four lines, each containing two positive space-separated integers that represent the lengths of a rectangle's two sides. Each side of a rectangle is at least 1 and at most 50.
SAMPLE INPUT (file packrec.in)

1 2
2 3
3 4
4 5

OUTPUT FORMAT
The output file contains one line more than the number of solutions. The first line contains a single integer: the minimum area of the enclosing rectangles. Each of the following lines contains one solution described by two numbers p and q with p<=q. These lines must be sorted in ascending order of p, and must all be different.
SAMPLE OUTPUT (file packrec.out)

40
4 10
5 8
*/

import java.io.*;
import java.util.*;

public class packrec{
	
	private static final int NUM = 4;
	private static final int WAYS = 16;
	private static int maxA;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("packrec.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("packrec.out")));
		StringTokenizer st;
		Rectangle[][] rects = new Rectangle[WAYS][NUM];
		for(int i = 0; i < NUM; i++){
			st = new StringTokenizer(read.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			for(int j = 0; j < WAYS; j++)	
				rects[j][i] = new Rectangle(a, b);
			maxA += a * b;
		}
		read.close();
		for(int i = 0; i < WAYS; i++){
			if(i > 7)
				rects[i][0].rotate();
			if(i / 4 % 2 == 1)
				rects[i][1].rotate();
			if(i / 2 % 2 == 1)
				rects[i][2].rotate();
			if(i % 2 == 1)
				rects[i][3].rotate();
		}
		
		Rectangle[] temp = case5(rects[4]);
		for(Rectangle r : rects[4])
			System.out.println(r);
		System.out.println();
		for(Rectangle r : temp)
			System.out.println(r);
		
		System.out.println();
		Rectangle[] master = new Rectangle[WAYS + WAYS * 4 + WAYS * 24 + WAYS * 3];
		for(int i = 0; i < WAYS; i++){
			master[i] = case1(rects[i]);
			merge(master, case2(rects[i]), 16 + 4 * i);
			merge(master, case3(rects[i]), 80 + 12 * i);
			merge(master, case4(rects[i]), 272 + 12 * i);
			merge(master, case5(rects[i]), 464 + 3 * i);
//			for(int j = 0; j < master.length; j++){
//				if(master[j] != null && master[j].area() == 88)
//					System.out.println(i);
//			}
		}
		for(int i = 0; i < master.length; i++){
			if(master[i].width == 6 && master[i].length == 6)
				System.out.println(i);
		}
		Arrays.sort(master);
		ArrayList<Rectangle> ans = new ArrayList<Rectangle>();
		for(int i = 0; i < master.length && master[i].area() == master[0].area(); i++){
			if(!ans.contains(master[i])){
				ans.add(master[i]);
			}
		}
		System.out.println();
		out.println(ans.get(0).area());
		for(int i = 0; i < ans.size(); i++){
			out.println(ans.get(i));
		}
		out.close();
		System.exit(0);
		
	}
	
	private static Rectangle case1(Rectangle[] rects){
		
		int width = 0;
		int height = 0;
		for(int i = 0; i < rects.length; i++){
			width += rects[i].width;
			height = Math.max(rects[i].length, height);
		}
		return new Rectangle(width, height);
		
	}
	
	private static Rectangle[] case2(Rectangle[] rects){
		
		Rectangle ans[] = new Rectangle[rects.length];
		for(int i = 0; i < rects.length; i++){
			int width = 0;
			int height = 0;
			for(int j = 0; j < rects.length; j++){
				if(j == i)
					continue;
				width += rects[j].width;
				height = Math.max(rects[j].length, height);
			}
			ans[i] = new Rectangle(Math.max(width, rects[i].width), height + rects[i].length);
		}
		return ans;
	}
	
	private static Rectangle[] case3(Rectangle[] rects){
		Rectangle ans[] = new Rectangle[12];
		int ind = 0;
		for(int i = 0; i < rects.length; i++){
			for(int j = 0; j < rects.length; j++){
				if(i == j)
					continue;
				int height = rects[j].length;
				int width = 0;
				for(int k = 0; k < rects.length; k++){
					if(k == i || k == j)
						continue;
					width += rects[k].width;
					height = Math.max(height, rects[i].length + rects[k].length);
				}
				width = Math.max(width, rects[i].width);
				width += rects[j].width;
				ans[ind] = new Rectangle(width, height);
				ind++;
			}
		}
		return ans;
	}
	
	private static Rectangle[] case4(Rectangle[] rects){
		Rectangle ans[] = new Rectangle[12];
		int ind = 0;
		for(int i = 0; i < rects.length; i++){
			for(int j = 0; j < rects.length; j++){
				if(i == j)
					continue;
				int height = rects[i].length + rects[j].length;
				int width = Math.max(rects[i].width, rects[j].width);
				for(int k = 0; k < rects.length; k++){
					if(k == i || k == j)
						continue;
					width += rects[k].width;
					height = Math.max(rects[k].length, height);
				}
				ans[ind] = new Rectangle(height, width);
				ind++;
			}
		}
		return ans;
	}
	
	private static Rectangle[] case5(Rectangle[] rects){
		Rectangle[] ans = new Rectangle[3];
		int width;
		int length;
		
		width = Math.max(rects[0].width + rects[1].width, rects[2].width + rects[3].width);
		width = Math.max(width, widthOfLonger(rects[0], rects[1]) + widthOfLonger(rects[2], rects[3]));
		length = Math.min(rects[0].length, rects[1].length) + Math.min(rects[2].length, rects[3].length) + Math.max(Math.abs(rects[0].length - rects[1].length), Math.abs(rects[2].length - rects[3].length));
		ans[0] = new Rectangle(width, length);
		
		width = Math.max(rects[0].width + rects[2].width, rects[1].width + rects[3].width);
		width = Math.max(width, widthOfLonger(rects[0], rects[2]) + widthOfLonger(rects[1], rects[3]));
		length = Math.min(rects[0].length, rects[2].length) + Math.min(rects[1].length, rects[3].length) + Math.max(Math.abs(rects[0].length - rects[2].length), Math.abs(rects[1].length - rects[3].length));
		ans[1] = new Rectangle(width, length);
		
		width = Math.max(rects[0].width + rects[3].width, rects[2].width + rects[1].width);
		width = Math.max(width, widthOfLonger(rects[0], rects[3]) + widthOfLonger(rects[2], rects[1]));
		length = Math.min(rects[0].length, rects[3].length) + Math.min(rects[1].length, rects[2].length) + Math.max(Math.abs(rects[0].length - rects[3].length), Math.abs(rects[1].length - rects[2].length));
		ans[2] = new Rectangle(width, length);
		
		return ans;
	}
	
	private static int widthOfLonger(Rectangle a, Rectangle b){
		if(a.length > b.length)
			return a.width;
		return b.width;
	}
	
	private static int widthOfShorter(Rectangle a, Rectangle b){
		if(a.length <= b.length)
			return a.width;
		return b.width;
	}
	
	private static void merge(Rectangle[] orig, Rectangle[] add, int ind){
		
		for(int i = 0; i < add.length; i++){
			orig[i + ind] = add[i];
		}
		
	}
	
}

class Rectangle implements Comparable<Rectangle>{
	
	int length;
	int width;
	
	public Rectangle(int a, int b){
		length = Math.max(a, b);
		width = Math.min(a, b);
	}
	
	public int compareTo(Rectangle other){
		if(area() != other.area()) return area() - other.area();
		if(width != other.width) return width - other.width;
		return length - other.length;
	}
	
	public int area(){
		return length*width;
	}
	
	public boolean equals(Object other){
		return compareTo((Rectangle) other) == 0;
	}
	
	public void rotate(){
		int temp = width;
		width = length;
		length = temp;
	}
	
	public String toString(){
		return "" + width + " " + length;
	}
	
}