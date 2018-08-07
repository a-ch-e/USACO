/*
ID: ayc22032
LANG: JAVA
TASK: rect1
 */

/*

Shaping Regions

N opaque rectangles (1 <= N <= 1000) of various colors are placed on a white sheet of
paper whose size is A wide by B long. The rectangles are put with their sides parallel
to the sheet's borders. All rectangles fall within the borders of the sheet so that
different figures of different colors will be seen.

The coordinate system has its origin (0,0) at the sheet's lower left corner with axes
parallel to the sheet's borders.

PROGRAM NAME: rect1

INPUT FORMAT

The order of the input lines dictates the order of laying down the rectangles. The first
input line is a rectangle "on the bottom".

Line 1:	 A, B, and N, space separated (1 <= A,B <= 10,000)
Lines 2-N+1:	Five integers: llx, lly, urx, ury, color: the lower left coordinates and
upper right coordinates of the rectangle whose color is `color' (1 <= color <= 2500) to
be placed on the white sheet. The color 1 is the same color of white as the sheet upon
which the rectangles are placed.

SAMPLE INPUT (file rect1.in)

20 20 3
2 2 18 18 2
0 8 19 19 3
8 0 10 19 4

INPUT EXPLANATION

Note that the rectangle delineated by 0,0 and 2,2 is two units wide and two high. Here's
a schematic diagram of the input:

11111111111111111111
33333333443333333331
33333333443333333331
33333333443333333331
33333333443333333331
33333333443333333331
33333333443333333331
33333333443333333331
33333333443333333331
33333333443333333331
33333333443333333331
33333333443333333331
11222222442222222211
11222222442222222211
11222222442222222211
11222222442222222211
11222222442222222211
11222222442222222211
11111111441111111111
11111111441111111111

The '4's at 8,0 to 10,19 are only two wide, not three (i.e., the grid contains a 4 and 8,0 and
a 4 and 8,1 but NOT a 4 and 8,2 since this diagram can't capture what would be shown on graph
paper).
OUTPUT FORMAT

The output file should contain a list of all the colors that can be seen along with the total
area of each color that can be seen (even if the regions of color are disjoint), ordered by
increasing color. Do not display colors with no area.
SAMPLE OUTPUT (file rect1.out)

1 91
2 84
3 187
4 38

 */

import java.io.*;
import java.util.*;

public class rect1 {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("rect1.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		int len = Integer.parseInt(st.nextToken());
		Stack<CRect> stack = new Stack<CRect>();
		stack.push(new CRect(0, 0, x, y, 1));
		for(int i = 0; i < len; i++){
			st = new StringTokenizer(read.readLine());
			stack.push(new CRect(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}
		read.close();
		
		ArrayList<CRect> polygon = new ArrayList<CRect>(len * 2);
		polygon.add(stack.pop());
		
		int[] colors = new int[2500];
		colors[polygon.get(0).color - 1] = polygon.get(0).area();
		CRect[] test = //new CRect(0, 0, 4, 4, 1).breakup(new CRect(1, 1, 3, 3, 1))
				//new CRect(1, 1, 3, 3, 1).breakup(new CRect(0, 0, 4, 4, 1))
				//new CRect(1, 1, 3, 3, 1).breakup(new CRect(2, 2, 4, 4, 1))
				//new CRect(2, 2, 4, 4, 1).breakup(new CRect(1, 1, 3, 3, 1))
				//new CRect(1, 2, 3, 4, 1).breakup(new CRect(2, 1, 4, 3, 1))
				//new CRect(2, 1, 4, 3, 1).breakup(new CRect(1, 2, 3, 4, 1))
				//new CRect(1, 1, 3, 4, 1).breakup(new CRect(2, 2, 4, 3, 1))
				//new CRect(2, 2, 4, 3, 1).breakup(new CRect(1, 1, 3, 4, 1))
				//new CRect(1, 2, 3, 3, 1).breakup(new CRect(2, 1, 4, 4, 1))
				//new CRect(2, 1, 4, 4, 1).breakup(new CRect(1, 2, 3, 3, 1))
				//new CRect(2, 2, 3, 4, 1).breakup(new CRect(1, 1, 4, 3, 1))
				//new CRect(1, 1, 4, 3, 1).breakup(new CRect(2, 2, 3, 4, 1))
				//new CRect(2, 1, 3, 3, 1).breakup(new CRect(1, 2, 4, 4, 1))
				//new CRect(1, 2, 4, 4, 1).breakup(new CRect(2, 1, 3, 3, 1))
				//new CRect(2, 1, 3, 4, 1).breakup(new CRect(1, 2, 4, 3, 1))
				new CRect(1, 2, 4, 3, 1).breakup(new CRect(2, 1, 3, 4, 1))
				;
//		for(CRect c : test){
//			System.out.println(c);
//		}
		
		while(!stack.isEmpty()){
			CRect top = stack.pop();
//			System.out.println(top);
			int area = top.area();
			int temp = polygon.size();
			int ind = 0;
			for(int i = 0; i < temp; i++){
				int intersect = polygon.get(i).intersect(top);
//				System.out.println(intersect);
				if(intersect > 0){
					area -= intersect;
//					System.out.println(polygon.get(i));
					CRect[] smalls = top.breakup(polygon.remove(i--));
					temp--;
//					System.out.println(smalls.length);
					for(CRect r : smalls){
						if(r.area() > 0)
							polygon.add(r);
					}
				}
				ind++;
			}
			polygon.add(top);
			colors[top.color - 1] += area;
//			for(int i = 0; i < colors.length; i++){
//				if(colors[i] > 0){
//					System.out.println((i + 1) + " " + colors[i]);
//				}
//			}
//			System.out.println(polygon);
//			System.out.println();
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("rect1.out")));
		for(int i = 0; i < colors.length; i++){
			if(colors[i] > 0){
				System.out.println((i + 1) + " " + colors[i]);
				out.println((i + 1) + " " + colors[i]);
			}
		}
		out.close();
		System.exit(0);
		
	}
	
}

class CRect{
	
	int x1, x2, y1, y2;
	int color;
	
	public CRect(int a, int b, int c, int d, int col){
		x1 = a;
		x2 = c;
		y1 = b;
		y2 = d;
		color = col;
	}
	
	public int area(){
		return (x2 - x1) * (y2 - y1);
	}
	
	public int intersect(CRect other){
		
		int tx1 = Math.max(x1, other.x1);
		int tx2 = Math.min(x2, other.x2);
		if(tx1 >= tx2)
			return 0;
		int ty1 = Math.max(y1, other.y1);
		int ty2 = Math.min(y2, other.y2);
		if(ty1 >= ty2)
			return 0;
		return (tx2 - tx1) * (ty2 - ty1);
		
	}
	
	public String toString(){
		
		return "(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + "), Color " + color; 
		
	}
	
	public CRect[] breakup(CRect other){
		CRect[] ans = null;
		//One within the other
		if(x1 <= other.x1 && y1 <= other.y1 && x2 >= other.x2 && y2 >= other.y2){
			ans = new CRect[0];
//			ans[0] = new CRect(x1, y1, x2, y2, 1);;
		} else if(x1 >= other.x1 && y1 >= other.y1 && x2 <= other.x2 && y2 <= other.y2){
			ans = new CRect[4];
			ans[0] = new CRect(other.x1, other.y1, x1, other.y2, 1);
			ans[1] = new CRect(x2, other.y1, other.x2, other.y2, 1);
			ans[2] = new CRect(x1, other.y1, x2, y1, 1);
			ans[3] = new CRect(x1, y2, x2, other.y2, 1);
		//one corner in
		} else if((x1 <= other.x1 && y1 <= other.y1) && x2 <= other.x2 && y2 <= other.y2){
			ans = new CRect[2];
			ans[0] = new CRect(other.x1, y2, other.x2, other.y2, 1);
			ans[1] = new CRect(x2, other.y1, other.x2, y2, 1);
		} else if((x1 >= other.x1 && y1 >= other.y1) && (x2 >= other.x2 && y2 >= other.y2)){
			ans = new CRect[2];
			ans[0] = new CRect(other.x1, y1, x1, other.y2, 1);
			ans[1] = new CRect(other.x1, other.y1, other.x2, y1, 1);
		} else if(x1 <= other.x1 && y1 >= other.y1 && x2 <= other.x2 && y2 >= other.y2){
			ans = new CRect[2];
			ans[0] = new CRect(x2, y1, other.x2, other.y2, 1);
			ans[1] = new CRect(other.x1, other.y1, other.x2, y1, 1);
		} else if(x1 >= other.x1 && y1 <= other.y1 && x2 >= other.x2 && y2 <= other.y2){
			ans = new CRect[2];
			ans[0] = new CRect(other.x1, y2, other.x2, other.y2, 1);
			ans[1] = new CRect(other.x1, other.y1, x1, y2, 1);
		//weird t-shape
		//facing right
		} else if((y1 <= other.y1 && y2 >= other.y2) && (x1 <= other.x1 && x2 <= other.x2)){
			ans = new CRect[1];
			ans[0] = new CRect(x2, other.y1, other.x2, other.y2, 1);
		} else if((y1 >= other.y1 && y2 <= other.y2) && (x1 >= other.x1 && x2 >= other.x2)){
			ans = new CRect[3]; //need to finish this case
			ans[0] = new CRect(other.x1, y2, other.x2, other.y2, 1);
			ans[1] = new CRect(other.x1, y1, x1, y2, 1);
			ans[2] = new CRect(other.x1, other.y1, other.x2, y1, 1);
		//facing left
		} else if((y1 <= other.y1 && y2 >= other.y2) && (x1 >= other.x1 && x2 >= other.x2)){
			ans = new CRect[1];
			ans[0] = new CRect(other.x1, other.y1, x1, other.y2, 1);
		} else if((y1 >= other.y1 && y2 <= other.y2) && (x1 <= other.x1 && x2 <= other.x2)){
			ans = new CRect[3];
			ans[0] = new CRect(other.x1, y2, other.x2, other.y2, 1);
			ans[1] = new CRect(x2, y1, other.x2, y2, 1);
			ans[2] = new CRect(other.x1, other.y1, other.x2, y1, 1);
		//facing up
		} else if((x1 >= other.x1 && x2 <= other.x2) && (y1 >= other.y1 && y2 >= other.y2)){
			ans = new CRect[3];
			ans[0] = new CRect(other.x1, y1, x1, other.y2, 1);
			ans[1] = new CRect(x2, y1, other.x2, other.y2, 1);
			ans[2] = new CRect(other.x1, other.y1, other.x2, y1, 1);
		} else if((x1 <= other.x1 && x2 >= other.x2) && (y1 <= other.y1 && y2 <= other.y2)){
			ans = new CRect[1];
			ans[0] = new CRect(other.x1, y2, other.x2, other.y2, 1);
		//facing down
		} else if((x1 >= other.x1 && x2 <= other.x2) && (y1 <= other.y1 && y2 <= other.y2)){
			ans = new CRect[3];
			ans[0] = new CRect(other.x1, other.y1, x1, y2, 1);
			ans[1] = new CRect(x2, other.y1, other.x2, y2, 1);
			ans[2] = new CRect(other.x1, y2, other.x2, other.y2, 1);
		} else if((x1 <= other.x1 && x2 >= other.x2) && (y1 >= other.y1 && y2 >= other.y2)){
			ans = new CRect[1];
			ans[0] = new CRect(other.x1, other.y1, other.x2, y1, 1);
		//cross shape
		} else if(x1 >= other.x1 && x2 <= other.x2){
//			System.out.println("!");
			ans = new CRect[2];
			ans[0] = new CRect(other.x1, other.y1, x1, other.y2, 1);
			ans[1] = new CRect(x2, other.y1, other.x2, other.y2, 1);
		} else {
			ans = new CRect[2];
			ans[0] = new CRect(other.x1, y2, other.x2, other.y2, 1);
			ans[1] = new CRect(other.x1, other.y1, other.x2, y1, 1);
		}
		
		
		return ans;
	}
	
}