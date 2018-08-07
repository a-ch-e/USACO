//lol @ black friday

import java.io.*;
import java.util.*;

public class AppChairs {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int P = Integer.parseInt(st.nextToken());
		long[] heights = new long[N];
		st = new StringTokenizer(read.readLine());
		for(int i = 0; i < N; i++){
			heights[i] = Long.parseLong(st.nextToken());
		}
		ArrayList<HL> intervals = new ArrayList<HL>(2 * P + 1);
		intervals.add(new HL(0, 0));
		for(int m = 0; m < P; m++){
			st = new StringTokenizer(read.readLine());
			int deltaH = Integer.parseInt(st.nextToken());
			int width = Integer.parseInt(st.nextToken());
			int start = Integer.parseInt(st.nextToken()) - 1;
			HL a = new HL(0, start);
			HL b = new HL(0, start + width);
			int aInd = Collections.binarySearch(intervals, a);
			if(aInd < 0){
				aInd = - aInd - 1;
				intervals.add(aInd, a);
				intervals.get(aInd).h = intervals.get(aInd - 1).h;
			}
			
			// careful with b end
			int bInd = intervals.size();
			if(start + width < N){
				bInd = Collections.binarySearch(intervals, b);
			}
//			System.out.println("bInd: " + bInd);
			if(bInd < 0){
				bInd = - bInd - 1;
				intervals.add(bInd, b);
				intervals.get(bInd).h = intervals.get(bInd - 1).h;
			}
//			System.out.println("aInd: " + aInd + ", bInd: " + bInd);
			for(int i = aInd; i < bInd; i++){
				intervals.get(i).h = deltaH + intervals.get(i).h;
			}
//			System.out.println(intervals);
			
		}
		
		read.close();
		
		int count = 0;
		for(int i = 0; i < intervals.size(); i++){
			int start = intervals.get(i).loc;
			long h = intervals.get(i).h;
			int end = N;
			if(i < intervals.size() - 1)
				end = intervals.get(i + 1).loc;
			for(int j = start; j < end; j++){
				if(Math.abs(heights[j] - h) <= 1)
					count++;
			}
		}
		
		System.out.println(count);
		System.exit(0);
		
	}
	
}

class HL implements Comparable<HL>{
	
	long h;
	int loc;
	
	public HL(long a, int b){
		
		h = a;
		loc = b;
	}
	
	public int compareTo(HL other){
		
		return this.loc - other.loc;
	}
	
	public String toString(){
		
		return "<H: " + h + ", L: " + loc + ">";
		
	}
	
}