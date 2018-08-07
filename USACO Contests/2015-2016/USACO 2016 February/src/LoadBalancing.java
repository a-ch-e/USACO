/*
Allen Cheng, ID ayc22032
USACO, February 2016
Platinum 1: Load Balancing
*/

import java.io.*;
import java.util.*;

public class LoadBalancing {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("balancing.in"));
		int N = Integer.parseInt(read.readLine());
		TreeSet<Integer> xsTemp = new TreeSet<Integer>();
		TreeSet<Integer> ysTemp = new TreeSet<Integer>();
		Location[] xLocs = new Location[N];
		Location[] yLocs = new Location[N];
		for(int i = 0; i < N; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			xsTemp.add(x);
			ysTemp.add(y);
			xLocs[i] = new Location(x, y);
			yLocs[i] = new Location(x, y);
		}
		read.close();
		
		XComp xc = new XComp();
		YComp yc = new YComp();
		Arrays.sort(xLocs, xc);
		Arrays.sort(yLocs, yc);
		
		ArrayList<Integer> xs = new ArrayList(xsTemp);
		ArrayList<Integer> ys = new ArrayList(ysTemp);
		
		int xInd = 0;
		int yInd = 0;
		
		int[] n = new int[4];
		n[3] = N;
		while(true){
			if(xInd < xs.size()){
				int u = Arrays.binarySearch(xLocs, new Location(xs.get(xInd), 0), xc) -
						Arrays.binarySearch(xLocs, new Location(xs.get(xInd), 1000000), xc);
			}
			int dxm = -1;
			if(xInd > 0){
				dxm = Arrays.binarySearch(xLocs, new Location(xs.get(xInd - 1), 0), xc) -
						Arrays.binarySearch(xLocs, new Location(xs.get(xInd - 1), 1000000), xc);
			}
			int dyp = -1;
			if(yInd < ys.size()){
				dyp = Arrays.binarySearch(yLocs, new Location(ys.get(yInd), 0), yc) -
						Arrays.binarySearch(yLocs, new Location(ys.get(yInd), 1000000), yc);
			}
			int dym = -1;
			if(yInd < ys.size()){
				dym = Arrays.binarySearch(yLocs, new Location(ys.get(yInd - 1), 0), yc) -
						Arrays.binarySearch(yLocs, new Location(ys.get(yInd - 1), 1000000), yc);
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("balancing.out"));
		
		out.close();
		System.exit(0);
		
	}
	
}

class Location{
	
	int x, y;
	public Location(int a, int b){
		x = a;
		y = b;
	}
}

class XComp implements Comparator<Location>{
	
	public int compare(Location a, Location b){
		if(a.x == b.x)
			return a.y - b.y;
		return a.x - b.x;
	}
	
}

class YComp implements Comparator<Location>{
	
	public int compare(Location a, Location b){
		if(a.y == b.y)
			return a.x - b.x;
		return a.y - b.y;
	}
	
}