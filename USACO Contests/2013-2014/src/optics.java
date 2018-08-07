/*
Allen Cheng
USACO Gold Division
2014 US Open Contest Problem 2: Cow Optics
 */

/*
Problem 2: Cow Optics [Brian Dean, 2014]

Farmer John's cows would like to host a dance party in their barn, complete
with a laser light show.  Unfortunately, the only working laser they have
found is located far away from the barn and too heavy to move, so they plan
to re-direct the laser light to the barn using a series of mirrors.

The layout of the farm has the laser at position (0,0) pointing north (in
the positive y direction), and the barn at (Bx, By); we can think of both
the laser and the barn as points in the 2D plane.  There are already N cows
(1 <= N <= 100,000) scattered throughout the farm holding mirrors that are
aligned at angles of 45 degrees to the axes.  For example, a mirror aligned
like \ will take a beam of light entering from below and reflect it to the
left.  We can also think of the mirrors as being located at points in the
2D plane.

Just before pressing the big red button to activate the laser, Bessie
noticed a major flaw in the plan: the laser cannot hit the barn with the
mirrors in their current configuration!  As a result, she plans to run out
onto the field, and hold up one more mirror (placed once again at a 45
degree angle) in order to redirect the laser onto the barn. Please count
the number of locations in the field where Bessie can stand to accomplish
this goal.

All coordinates are integers between -1,000,000,000 and 1,000,000,000. It
is guaranteed that any mirrors to be placed will be in this range as well.
The cows running the laser insist that the beam should never come back to
(0,0) after leaving this location (and with the mirrors in their initial
configuration, it is guaranteed that this will not happen).  No two cows
occupy the same point in space, and Bessie cannot locate herself at the 
same position as an existing cow.

PROBLEM NAME: optics

INPUT FORMAT:

* Line 1: The integers N, Bx, and By.

* Lines 2..N + 1: Line i+1 describes the ith mirror with 3 elements:
        its (x,y) location, and its orientation (either '\' or '/').

SAMPLE INPUT (file optics.in):

4 1 2
-2 1 \
2 1 /
2 2 \
-2 2 /

OUTPUT FORMAT: A single integer giving the number of locations
where Bessie can stand to redirect the laser to the barn.

SAMPLE OUTPUT (file optics.out):

2

OUTPUT DETAILS:

A mirror at (0,1) or (0,2) placed in either direction would do the trick.
 */
import java.util.*;
import java.io.*;

public class optics {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("optics.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int bX = Integer.parseInt(st.nextToken());
		int bY = Integer.parseInt(st.nextToken());
		Mirror[] mirrorsX = new Mirror[N];
		Mirror[] mirrorsY = new Mirror[N];
		TreeSet<Integer> usefulX = new TreeSet<Integer>();
		TreeSet<Integer> usefulY = new TreeSet<Integer>();
		usefulX.add(bX);
		usefulY.add(bY);
		//usefulX.add(0);
		//usefulY.add(0);
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(read.readLine());
			int x0 = Integer.parseInt(st.nextToken());
			int y0 = Integer.parseInt(st.nextToken());
			boolean s = st.nextToken().charAt(0) == '/';
			mirrorsX[i] = new Mirror(x0, y0, s);
			//mirrorsY[i] = new Mirror(x0, y0, s);
			usefulX.add(x0);
			usefulY.add(y0);
		}
		read.close();
				
		mirrorsY = Arrays.copyOf(mirrorsX, N);
		Arrays.sort(mirrorsX, new MirrorCompX());
		Arrays.sort(mirrorsY, new MirrorCompY());
		for(int i = 0; i < N; i++){
			mirrorsX[i].xInd = i;
			mirrorsY[i].yInd = i;
		}
		
//		System.out.println(Arrays.toString(mirrorsX));
//		System.out.println(Arrays.toString(mirrorsY));
		
		//Mirror current = mirrorsX[-1 * Arrays.binarySearch(mirrorsX, new Mirror(0, 0, true), new MirrorCompX()) - 1];
		Mirror current = new Mirror(0, 0, true);
		current.xInd = -1 * Arrays.binarySearch(mirrorsX, current, new MirrorCompX()) - 2;
		//current.yInd = -1 * Arrays.binarySearch(mirrorsY, current, new MirrorCompY()) - 2;
		boolean lineX = false;
		int coord = 0;
		boolean up = true;
		TreeSet<Mirror> pointsX = new TreeSet<Mirror>(new MirrorCompX());
		TreeSet<Mirror> pointsY = new TreeSet<Mirror>(new MirrorCompY());
		
		int[] track = new int[8];
		for(int i = 0; i < track.length; i++)
			track[i] = -10;
		
		for(int m = 0; ; m++){
			Mirror next = null;
			if(!lineX && up){
				if(current.xInd >= N - 1 || mirrorsX[current.xInd + 1].x != current.x){
					for(int i : usefulY.tailSet(current.y, false)){
						pointsX.add(new Mirror(coord, i, true));
						pointsY.add(new Mirror(coord, i, true));
					}
					break;
				}
				next = mirrorsX[current.xInd + 1];
				for(int i : usefulY.subSet(current.y, false, next.y, false)){
					pointsX.add(new Mirror(coord, i, true));
					pointsY.add(new Mirror(coord, i, true));
				}
				coord = next.y;
			}
			if(!lineX && !up){
				if(current.xInd <= 0 || mirrorsX[current.xInd - 1].x != current.x){
					for(int i : usefulY.headSet(current.y, false)){
						pointsX.add(new Mirror(coord, i, true));
						pointsY.add(new Mirror(coord, i, true));
					}
					break;
				}
				next = mirrorsX[current.xInd - 1];
				for(int i : usefulY.subSet(next.y, false, current.y, false)){
					pointsX.add(new Mirror(coord, i, true));
					pointsY.add(new Mirror(coord, i, true));
				}
				coord = next.y;
			}
			if(lineX && up){
				if(current.yInd >= N - 1 || mirrorsY[current.yInd + 1].y != current.y){
					for(int i : usefulX.tailSet(current.x, false)){
						pointsX.add(new Mirror(i, coord, true));
						pointsY.add(new Mirror(i, coord, true));
					}
					break;
				}
				next = mirrorsY[current.yInd + 1];
				for(int i : usefulX.subSet(current.x, false, next.x, false)){
					pointsX.add(new Mirror(i, coord, true));
					pointsY.add(new Mirror(i, coord, true));
				}
				coord = next.x;
			}
			if(lineX && !up){
				if(current.yInd <= 0 || mirrorsY[current.yInd - 1].y != current.y){
					for(int i : usefulX.headSet(current.x, false)){
						pointsX.add(new Mirror(i, coord, true));
						pointsY.add(new Mirror(i, coord, true));
					}
					break;
				}
				next = mirrorsY[current.yInd - 1];
				for(int i : usefulX.subSet(next.x, false, current.x, false)){
					pointsX.add(new Mirror(i, coord, true));
					pointsY.add(new Mirror(i, coord, true));
				}
				coord = next.x;
			}
			
			lineX = !lineX;
			up = !(up ^ next.slash);
			current = next;
			//System.out.println("X-dir: " + lineX + ", up: " + up + ", coord: " + coord + ", current: " + current);
			
			track[m % 8] = current.xInd;
			if(track[0] == track[4] && track[1] == track[5] && track[2] == track[6] && track[3] == track[7])
				break;
			
		}
		
		//System.out.println(pointsX);
		usefulX.remove(bX);
		usefulY.remove(bY);
		usefulX.add(0);
		usefulY.add(0);

		TreeSet<Mirror> allPoints = new TreeSet<Mirror>(new MirrorCompX());	
		
		lineX = false;
		coord = bX;
		up = true;
		current = new Mirror(bX, bY, true);
		current.xInd = -1 * Arrays.binarySearch(mirrorsX, current, new MirrorCompX()) - 2;
		//current.yInd = -1 * Arrays.binarySearch(mirrorsY, current, new MirrorCompY()) - 1;
		
		track = new int[8];
		for(int i = 0; i < track.length; i++)
			track[i] = -10;
		
		for(int m = 0; ; m++){
			Mirror next = null;
			if(!lineX && up){
				if(current.xInd >= N - 1 || mirrorsX[current.xInd + 1].x != current.x){
					SortedSet<Integer> set = usefulY.tailSet(current.y, false);
					if(set.size() > 0){
						Mirror a = new Mirror(coord, set.first(), true);
						Mirror b = new Mirror(coord, set.last(), true);
						allPoints.addAll(pointsX.subSet(a, true, b, true));
					}
					break;
				}
				next = mirrorsX[current.xInd + 1];
				SortedSet<Integer> set = usefulY.subSet(current.y, false, next.y, false);
				if(set.size() > 0){
					Mirror a = new Mirror(coord, set.first(), true);
					Mirror b = new Mirror(coord, set.last(), true);
					allPoints.addAll(pointsX.subSet(a, true, b, true));
				}
				coord = next.y;
			}
			if(!lineX && !up){
				if(current.xInd <= 0 || mirrorsX[current.xInd - 1].x != current.x){
					SortedSet<Integer> set = usefulY.headSet(current.y, false);
//					System.out.println(set);
					if(set.size() > 0){
						Mirror a = new Mirror(coord, set.first(), true);
						Mirror b = new Mirror(coord, set.last(), true);
						allPoints.addAll(pointsX.subSet(a, true, b, true));
					}
					break;
				}
				next = mirrorsX[current.xInd - 1];
				SortedSet<Integer> set = usefulY.subSet(next.y, false, current.y, false);
//				System.out.println(set);
				if(set.size() > 0){
					Mirror a = new Mirror(coord, set.first(), true);
					Mirror b = new Mirror(coord, set.last(), true);
					allPoints.addAll(pointsX.subSet(a, true, b, true));
				}
				coord = next.y;
			}
			if(lineX && up){
				if(current.yInd >= N - 1 || mirrorsY[current.yInd + 1].y != current.y){
					SortedSet<Integer> set = usefulX.tailSet(current.x, false);
//					System.out.println(set);
					if(set.size() > 0){
						Mirror a = new Mirror(set.first(), coord, true);
						Mirror b = new Mirror(set.last(), coord, true);
						allPoints.addAll(pointsY.subSet(a, true, b, true));
					}
//					System.out.println(pointsY.subSet(a, true, b, true));
					break;
				}
				next = mirrorsY[current.yInd + 1];
				SortedSet<Integer> set = usefulX.subSet(current.x, false, next.x, false);
				if(set.size() > 0){
					Mirror a = new Mirror(set.first(), coord, true);
					Mirror b = new Mirror(set.last(), coord, true);
					allPoints.addAll(pointsY.subSet(a, true, b, true));
				}
				coord = next.x;
			}
			if(lineX && !up){
				if(current.yInd <= 0 || mirrorsY[current.yInd - 1].y != current.y){
//					System.out.println(current);
					SortedSet<Integer> set = usefulX.headSet(current.x, false);
					if(set.size() > 0){
						Mirror a = new Mirror(set.first(), coord, true);
						Mirror b = new Mirror(set.last(), coord, true);
						allPoints.addAll(pointsY.subSet(a, true, b, true));
					}
					break;
				}
				next = mirrorsY[current.yInd - 1];
				SortedSet<Integer> set = usefulX.subSet(next.x, false, current.x, false);
				if(set.size() > 0){
					Mirror a = new Mirror(set.first(), coord, true);
					Mirror b = new Mirror(set.last(), coord, true);
					allPoints.addAll(pointsY.subSet(a, true, b, true));
				}
				coord = next.x;
			}
			
			lineX = !lineX;
			up = !(up ^ next.slash);
			current = next;
			//System.out.println("X-dir: " + lineX + ", up: " + up + ", coord: " + coord + ", current: " + current);
			
			track[m % 8] = current.xInd;
			if(track[0] == track[4] && track[1] == track[5] && track[2] == track[6] && track[3] == track[7])
				break;
		}
		//System.out.println(allPoints);
		
		lineX = true;
		coord = bY;
		up = true;
		current = new Mirror(bX, bY, true);
		//current.xInd = -1 * Arrays.binarySearch(mirrorsX, current, new MirrorCompX()) - 2;
		current.yInd = -1 * Arrays.binarySearch(mirrorsY, current, new MirrorCompY()) - 2;
		//System.out.println(current);
		
		track = new int[8];
		for(int i = 0; i < track.length; i++)
			track[i] = -10;
		
		for(int m = 0; ; m++){
			Mirror next = null;
			if(!lineX && up){
				if(current.xInd >= N - 1 || mirrorsX[current.xInd + 1].x != current.x){
					SortedSet<Integer> set = usefulY.tailSet(current.y, false);
					if(set.size() > 0){
						Mirror a = new Mirror(coord, set.first(), true);
						Mirror b = new Mirror(coord, set.last(), true);
						allPoints.addAll(pointsX.subSet(a, true, b, true));
					}
					break;
				}
				next = mirrorsX[current.xInd + 1];
				SortedSet<Integer> set = usefulY.subSet(current.y, false, next.y, false);
				if(set.size() > 0){
					Mirror a = new Mirror(coord, set.first(), true);
					Mirror b = new Mirror(coord, set.last(), true);
					allPoints.addAll(pointsX.subSet(a, true, b, true));
				}
				coord = next.y;
			}
			if(!lineX && !up){
				if(current.xInd <= 0 || mirrorsX[current.xInd - 1].x != current.x){
					SortedSet<Integer> set = usefulY.headSet(current.y, false);
//					System.out.println(set);
					if(set.size() > 0){
						Mirror a = new Mirror(coord, set.first(), true);
						Mirror b = new Mirror(coord, set.last(), true);
						allPoints.addAll(pointsX.subSet(a, true, b, true));
					}
					break;
				}
				next = mirrorsX[current.xInd - 1];
				SortedSet<Integer> set = usefulY.subSet(next.y, false, current.y, false);
//				System.out.println(set);
				if(set.size() > 0){
					Mirror a = new Mirror(coord, set.first(), true);
					Mirror b = new Mirror(coord, set.last(), true);
					allPoints.addAll(pointsX.subSet(a, true, b, true));
				}
				coord = next.y;
			}
			if(lineX && up){
				if(current.yInd >= N - 1 || mirrorsY[current.yInd + 1].y != current.y){
					SortedSet<Integer> set = usefulX.tailSet(current.x, false);
//					System.out.println(set);
					if(set.size() > 0){
						Mirror a = new Mirror(set.first(), coord, true);
						Mirror b = new Mirror(set.last(), coord, true);
						allPoints.addAll(pointsY.subSet(a, true, b, true));
					}
//					System.out.println(pointsY.subSet(a, true, b, true));
					break;
				}
				next = mirrorsY[current.yInd + 1];
				SortedSet<Integer> set = usefulX.subSet(current.x, false, next.x, false);
				if(set.size() > 0){
					Mirror a = new Mirror(set.first(), coord, true);
					Mirror b = new Mirror(set.last(), coord, true);
					allPoints.addAll(pointsY.subSet(a, true, b, true));
				}
				coord = next.x;
			}
			if(lineX && !up){
				if(current.yInd <= 0 || mirrorsY[current.yInd - 1].y != current.y){
//					System.out.println(current);
					SortedSet<Integer> set = usefulX.headSet(current.x, false);
					if(set.size() > 0){
						Mirror a = new Mirror(set.first(), coord, true);
						Mirror b = new Mirror(set.last(), coord, true);
						allPoints.addAll(pointsY.subSet(a, true, b, true));
					}
					break;
				}
				next = mirrorsY[current.yInd - 1];
				SortedSet<Integer> set = usefulX.subSet(next.x, false, current.x, false);
				if(set.size() > 0){
					Mirror a = new Mirror(set.first(), coord, true);
					Mirror b = new Mirror(set.last(), coord, true);
					allPoints.addAll(pointsY.subSet(a, true, b, true));
				}
				coord = next.x;
			}
			
			lineX = !lineX;
			up = !(up ^ next.slash);
			current = next;
			//System.out.println("X-dir: " + lineX + ", up: " + up + ", coord: " + coord + ", current: " + current);
			
			track[m % 8] = current.xInd;
			if(track[0] == track[4] && track[1] == track[5] && track[2] == track[6] && track[3] == track[7])
				break;
		}
		//System.out.println(allPoints);
		
		lineX = false;
		coord = bX;
		up = false;
		current = new Mirror(bX, bY, true);
		current.xInd = -1 * Arrays.binarySearch(mirrorsX, current, new MirrorCompX()) - 1;
		//current.yInd = -1 * Arrays.binarySearch(mirrorsY, current, new MirrorCompY()) - 1;
		
		track = new int[8];
		for(int i = 0; i < track.length; i++)
			track[i] = -10;
		
		for(int m = 0; ; m++){
			Mirror next = null;
			if(!lineX && up){
				if(current.xInd >= N - 1 || mirrorsX[current.xInd + 1].x != current.x){
					SortedSet<Integer> set = usefulY.tailSet(current.y, false);
					if(set.size() > 0){
						Mirror a = new Mirror(coord, set.first(), true);
						Mirror b = new Mirror(coord, set.last(), true);
						allPoints.addAll(pointsX.subSet(a, true, b, true));
					}
					break;
				}
				next = mirrorsX[current.xInd + 1];
				SortedSet<Integer> set = usefulY.subSet(current.y, false, next.y, false);
				if(set.size() > 0){
					Mirror a = new Mirror(coord, set.first(), true);
					Mirror b = new Mirror(coord, set.last(), true);
					allPoints.addAll(pointsX.subSet(a, true, b, true));
				}
				coord = next.y;
			}
			if(!lineX && !up){
				if(current.xInd <= 0 || mirrorsX[current.xInd - 1].x != current.x){
					SortedSet<Integer> set = usefulY.headSet(current.y, false);
//					System.out.println(set);
					if(set.size() > 0){
						Mirror a = new Mirror(coord, set.first(), true);
						Mirror b = new Mirror(coord, set.last(), true);
						allPoints.addAll(pointsX.subSet(a, true, b, true));
					}
					break;
				}
				next = mirrorsX[current.xInd - 1];
				SortedSet<Integer> set = usefulY.subSet(next.y, false, current.y, false);
//				System.out.println(set);
				if(set.size() > 0){
					Mirror a = new Mirror(coord, set.first(), true);
					Mirror b = new Mirror(coord, set.last(), true);
					allPoints.addAll(pointsX.subSet(a, true, b, true));
				}
				coord = next.y;
			}
			if(lineX && up){
				if(current.yInd >= N - 1 || mirrorsY[current.yInd + 1].y != current.y){
					SortedSet<Integer> set = usefulX.tailSet(current.x, false);
//					System.out.println(set);
					if(set.size() > 0){
						Mirror a = new Mirror(set.first(), coord, true);
						Mirror b = new Mirror(set.last(), coord, true);
						allPoints.addAll(pointsY.subSet(a, true, b, true));
					}
//					System.out.println(pointsY.subSet(a, true, b, true));
					break;
				}
				next = mirrorsY[current.yInd + 1];
				SortedSet<Integer> set = usefulX.subSet(current.x, false, next.x, false);
				if(set.size() > 0){
					Mirror a = new Mirror(set.first(), coord, true);
					Mirror b = new Mirror(set.last(), coord, true);
					allPoints.addAll(pointsY.subSet(a, true, b, true));
				}
				coord = next.x;
			}
			if(lineX && !up){
				if(current.yInd <= 0 || mirrorsY[current.yInd - 1].y != current.y){
//					System.out.println(current);
					SortedSet<Integer> set = usefulX.headSet(current.x, false);
					if(set.size() > 0){
						Mirror a = new Mirror(set.first(), coord, true);
						Mirror b = new Mirror(set.last(), coord, true);
						allPoints.addAll(pointsY.subSet(a, true, b, true));
					}
					break;
				}
				next = mirrorsY[current.yInd - 1];
				SortedSet<Integer> set = usefulX.subSet(next.x, false, current.x, false);
				if(set.size() > 0){
					Mirror a = new Mirror(set.first(), coord, true);
					Mirror b = new Mirror(set.last(), coord, true);
					allPoints.addAll(pointsY.subSet(a, true, b, true));
				}
				coord = next.x;
			}
			
			lineX = !lineX;
			up = !(up ^ next.slash);
			current = next;
			//System.out.println("X-dir: " + lineX + ", up: " + up + ", coord: " + coord + ", current: " + current);
			
			track[m % 8] = current.xInd;
			if(track[0] == track[4] && track[1] == track[5] && track[2] == track[6] && track[3] == track[7])
				break;
		}
		//System.out.println(allPoints);
		
		lineX = true;
		coord = bY; //fix!
		up = false;//temp
		current = new Mirror(bX, bY, true);
		//current.xInd = -1 * Arrays.binarySearch(mirrorsX, current, new MirrorCompX()) - 1;
		current.yInd = -1 * Arrays.binarySearch(mirrorsY, current, new MirrorCompY()) - 1;
		//System.out.println(current);
		
		track = new int[8];
		for(int i = 0; i < track.length; i++)
			track[i] = -10;
		
		for(int m = 0; ; m++){
			Mirror next = null;
			if(!lineX && up){
				if(current.xInd >= N - 1 || mirrorsX[current.xInd + 1].x != current.x){
					SortedSet<Integer> set = usefulY.tailSet(current.y, false);
					if(set.size() > 0){
						Mirror a = new Mirror(coord, set.first(), true);
						Mirror b = new Mirror(coord, set.last(), true);
						allPoints.addAll(pointsX.subSet(a, true, b, true));
					}
					break;
				}
				next = mirrorsX[current.xInd + 1];
				SortedSet<Integer> set = usefulY.subSet(current.y, false, next.y, false);
				if(set.size() > 0){
					Mirror a = new Mirror(coord, set.first(), true);
					Mirror b = new Mirror(coord, set.last(), true);
					allPoints.addAll(pointsX.subSet(a, true, b, true));
				}
				coord = next.y;
			}
			if(!lineX && !up){
				if(current.xInd <= 0 || mirrorsX[current.xInd - 1].x != current.x){
					SortedSet<Integer> set = usefulY.headSet(current.y, false);
//					System.out.println(set);
					if(set.size() > 0){
						Mirror a = new Mirror(coord, set.first(), true);
						Mirror b = new Mirror(coord, set.last(), true);
						allPoints.addAll(pointsX.subSet(a, true, b, true));
					}
					break;
				}
				next = mirrorsX[current.xInd - 1];
				SortedSet<Integer> set = usefulY.subSet(next.y, false, current.y, false);
//				System.out.println(set);
				if(set.size() > 0){
					Mirror a = new Mirror(coord, set.first(), true);
					Mirror b = new Mirror(coord, set.last(), true);
					allPoints.addAll(pointsX.subSet(a, true, b, true));
				}
				coord = next.y;
			}
			if(lineX && up){
				if(current.yInd >= N - 1 || mirrorsY[current.yInd + 1].y != current.y){
					SortedSet<Integer> set = usefulX.tailSet(current.x, false);
//					System.out.println(set);
					if(set.size() > 0){
						Mirror a = new Mirror(set.first(), coord, true);
						Mirror b = new Mirror(set.last(), coord, true);
						allPoints.addAll(pointsY.subSet(a, true, b, true));
					}
//					System.out.println(pointsY.subSet(a, true, b, true));
					break;
				}
				next = mirrorsY[current.yInd + 1];
				SortedSet<Integer> set = usefulX.subSet(current.x, false, next.x, false);
				if(set.size() > 0){
					Mirror a = new Mirror(set.first(), coord, true);
					Mirror b = new Mirror(set.last(), coord, true);
					allPoints.addAll(pointsY.subSet(a, true, b, true));
				}
				coord = next.x;
			}
			if(lineX && !up){
				if(current.yInd <= 0 || mirrorsY[current.yInd - 1].y != current.y){
//					System.out.println(current);
					SortedSet<Integer> set = usefulX.headSet(current.x, false);
					if(set.size() > 0){
						Mirror a = new Mirror(set.first(), coord, true);
						Mirror b = new Mirror(set.last(), coord, true);
						allPoints.addAll(pointsY.subSet(a, true, b, true));
					}
					break;
				}
				next = mirrorsY[current.yInd - 1];
				SortedSet<Integer> set = usefulX.subSet(next.x, false, current.x, false);
				if(set.size() > 0){
					Mirror a = new Mirror(set.first(), coord, true);
					Mirror b = new Mirror(set.last(), coord, true);
					allPoints.addAll(pointsY.subSet(a, true, b, true));
				}
				coord = next.x;
			}
			
			lineX = !lineX;
			up = !(up ^ next.slash);
			current = next;
			//System.out.println("X-dir: " + lineX + ", up: " + up + ", coord: " + coord + ", current: " + current);
			
			track[m % 8] = current.xInd;
			if(track[0] == track[4] && track[1] == track[5] && track[2] == track[6] && track[3] == track[7])
				break;
		}
		//System.out.println(allPoints);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("optics.out")));
		out.println(allPoints.size());
		System.out.println(allPoints.size());
		out.close();
		System.exit(0);
		
	}
	
}

/*class Point{
	int x, y;
	
	public Point(int x0, int y0){
		x = x0;
		y = y0;
	}
	
}*/

class Mirror{
	
	int x, y;
	boolean slash;
	int xInd, yInd;
	
	public Mirror(int x0, int y0, boolean s){
		
		x = x0;
		y = y0;
		slash = s;
		xInd = -1;
		yInd = -1;
		
	}
	
	public String toString(){
		
		String s = "(" + x + ", " + y + ", ";
		if(slash) s += "/";
		else s += "\\";
		s += ") xInd " + xInd + " yInd " + yInd;
		return s;
		
	}
	
}

class MirrorCompX implements Comparator<Mirror>{
	
	public int compare(Mirror a, Mirror b){
		if(a.x == b.x) return a.y - b.y; 
		return a.x - b.x;
	}
	
}

class MirrorCompY implements Comparator<Mirror>{
	
	public int compare(Mirror a, Mirror b){
		if(a.y == b.y) return a.x - b.x; 
		return a.y - b.y;
	}
	
}