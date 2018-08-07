/*
ID: ayc22032
LANG: JAVA
TASK: spin
*/

/*
Spinning Wheels
1998 ACM NE Regionals

Each of five opaque spinning wheels has one or more wedges cut out of its edges. These wedges must
be aligned quickly and correctly. Each wheel also has an alignment mark (at 0 degrees) so that the
wheels can all be started in a known position. Wheels rotate in the `plus degrees' direction, so
that shortly after they start, they pass through 1 degree, 2 degrees, etc. (though probably not
at the same time).

This is an integer problem. Wheels are never actually at 1.5 degrees or 23.51234123 degrees. For
example, the wheels are considered to move instantaneously from 20 to 25 degrees during a single
second or even from 30 to 40 degrees if the wheel is spinning quickly.

All angles in this problem are presumed to be integers in the range 0 <= angle <= 359. The angle
of 0 degrees follows the angle of 359 degrees. Each wheel rotates at a certain integer number of
degrees per second, 1 <= speed <= 180.

Wedges for each wheel are specified by an integer start angle and integer angle size (or `extent'),
both specified in degrees. Wedges in the test data will be separated by at least one degree. The
'extent' also includes the original "degree" of the wedge, so '0 180' means degrees 0..180 inclusive
-- one more than most would imagine.

At the start, which is time 0, all the wheels' alignment marks line up. Your program must determine
the earliest time (integer seconds) at or after the start that some wedge on each wheel will align
with the wedges on the other wheel so that a light beam can pass through openings on all five wedges.
The wedges can align at any part of the rotation.

PROGRAM NAME: spin

INPUT FORMAT

Each of five input lines describes a wheel.

The first integer on an input line is the wheel's rotation speed. The next integer is the number of
wedges, 1 <= W <= 5. The next W pairs of integers tell each wedge's start angle and extent.

SAMPLE INPUT (file spin.in)

30 1 0 120
50 1 150 90
60 1 60 90
70 1 180 180
90 1 180 60

OUTPUT FORMAT

A single line with a single integer that is the first time the wedges align so a light beam can pass
through them. Print `none' (lower case, no quotes) if the wedges will never align properly.

SAMPLE OUTPUT (file spin.out)

9

 */

import java.io.*;
import java.util.*;

public class spin {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("spin.in"));
		SpinWheel[] wheels = new SpinWheel[5];
		for(int i = 0; i < wheels.length; i++){
			wheels[i] = new SpinWheel(read.readLine());
//			System.out.println(wheels[i]);
		}
		read.close();
		
//		System.out.println(intersect(wheels[0], wheels[1]));
		
		int t = 0;
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("spin.out")));
		
		while(t < 360){
			if(solved(wheels))
				break;
//			System.out.println(t);
			for(SpinWheel s : wheels){
//				System.out.println(s);
				s.rotate();
			}
			t++;
//			System.out.println("----------------\n");
		}
		if(t == 360){
			System.out.println("none");
			out.println("none");
		} else {
			System.out.println(t);
			out.println(t);
		}
		out.close();
		System.exit(0);
		
	}
	
	private static boolean solved(SpinWheel[] wheels){
		
		for(int i = 0; i < 360; i++){
			boolean all = true;
			for(int j = 0; j < wheels.length; j++){
				boolean wheel = false;
				for(int[] wedge: wheels[j].wedges){
					if(wedge[0] < 0 && i >= wedge[1])
						wheel = wheel || i - 360 >= wedge[0];
					else
						wheel = wheel || (i >= wedge[0] && i <= wedge[1]);
				}
				if(wheel == false){
//					if(i == 270)
//						System.out.println(wheel);
					all = false;
					break;
				}
			}
			if(all) return all;
		}
		return false;
		
	}
	
/*	private static boolean intersect(SpinWheel a, SpinWheel b){
		
		for(int[] wedge1 : a.wedges){
			for(int[] wedge2 : b.wedges){
				if((wedge1[0] > wedge2[0] ^ wedge1[1] > wedge2[1]) || (wedge1[0] < wedge2[0] && wedge2[0] < wedge1[1]) || (wedge2[0] < wedge1[0] && wedge1[0] < wedge2[1]))
					return true;
			}
		}
		return false;
		
	}*/
		
}

class SpinWheel{
	
	int speed;
	int[][] wedges;
	
	public SpinWheel(String in){
		
		StringTokenizer st = new StringTokenizer(in);
		speed = Integer.parseInt(st.nextToken());
		wedges = new int[Integer.parseInt(st.nextToken())][2];
		for(int i = 0; i < wedges.length; i++){
			wedges[i][0] = Integer.parseInt(st.nextToken());
			wedges[i][1] = Integer.parseInt(st.nextToken()) + wedges[i][0];
		}
		
	}
	
	public void rotate(){
		
		for(int i = 0; i < wedges.length; i++){
			for(int j = 0; j < wedges[i].length; j++){
				wedges[i][j] += speed;
				wedges[i][j] %= 360;
				if(j == 1 && wedges[i][0] > wedges[i][1])
					wedges[i][0] -= 360; 
			}
		}
		
	}
	
	public String toString(){
		
		String s = "Wedges: ";
		for(int i = 0; i < wedges.length; i++){
			s += "(";
			for(int j = 0; j < wedges[i].length; j++){
				s += wedges[i][j] + ", ";
			}
			s = s.substring(0, s.length() - 2);
			s += ")";
		}
		return s;
	}
	
}
