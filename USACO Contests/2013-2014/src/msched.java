/*
Allen Cheng
USACO Silver Division
December Contest Problem 1: Milk Scheduling
 */

/*

Problem 1: Milk Scheduling [Traditional, 2011]

Farmer John has N cows that need to be milked (1 <= N <= 10,000), each of
which takes only one unit of time to milk.

Being impatient animals, some cows will refuse to be milked if Farmer
John waits too long to milk them.  More specifically, cow i produces g_i
gallons of milk (1 <= g_i <= 1000), but only if she is milked before a
deadline at time d_i (1 <= d_i <= 10,000).  Time starts at t=0, so at most
x total cows can be milked prior to a deadline at time t=x.

Please help Farmer John determine the maximum amount of milk that he can
obtain if he milks the cows optimally.

PROBLEM NAME: msched

INPUT FORMAT:

* Line 1: The value of N.

* Lines 2..1+N: Line i+1 contains the integers g_i and d_i.

SAMPLE INPUT (file msched.in):

4
10 3
7 5
8 1
2 1

INPUT DETAILS:

There are 4 cows.  The first produces 10 gallons of milk if milked by time
3, and so on.

OUTPUT FORMAT:

* Line 1: The maximum number of gallons of milk Farmer John can
        obtain.

SAMPLE OUTPUT (file msched.out):

25

OUTPUT DETAILS:

Farmer John milks cow 3 first, giving up on cow 4 since she cannot be
milked by her deadline due to the conflict with cow 3.  Farmer John then
milks cows 1 and 2.

 */

import java.util.*;
import java.io.*;

public class msched {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("msched.in"));
		int N = Integer.parseInt(read.readLine());
		MilkCow[] cows = new MilkCow[N];
		for(int i = 0; i < N; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			cows[i] = new MilkCow(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		read.close();
		
		Arrays.sort(cows, new MilkCowTimeComparator());
		int times = 0;
		int[] timeChanges = new int[N];
		int prevTime = -1;
		int ind = 0;
		for(int i = 0; i < cows.length; i++){
			if(cows[i].time != prevTime){
				prevTime = cows[i].time;
				timeChanges[ind++] = i;
				times++;
			}
		}
//		System.out.println(times);
		int totalMilk = 0;
		int count = 0;
		
		for(int i = times - 1; i >= 0; i--){
			int q = 0;
			if(i == 0){
				q = Math.min(N - timeChanges[i] - count,
						cows[timeChanges[i]].time);
			} else {
				q = Math.min(N - timeChanges[i] - count,
						cows[timeChanges[i]].time - cows[timeChanges[i - 1]].time);
			}
//			System.out.println(q);
			for(int j = 0; j < q; j++){
				int bestInd = -1;
//				System.out.println("Search!");
				for(int k = timeChanges[i]; k < N; k++){
					if(cows[k].seen)
						continue;
					if((bestInd == -1 || cows[k].milk > cows[bestInd].milk)){
						bestInd = k;
//						System.out.println(cows[bestInd].seen);
					}
				}
				if(bestInd == -1)
					break;
				cows[bestInd].seen = true;
				totalMilk += cows[bestInd].milk;
//				System.out.println(totalMilk);
			}
			count += q;
//			System.out.println("--------");
		}
		
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("msched.out")));
		out.println(totalMilk);
		System.out.println(totalMilk);
		out.close();
		System.exit(0);
		
	}
	
}

class MilkCow{
	
	int milk, time;
	boolean seen;
	
	public MilkCow(int m, int t){
		milk = m;
		time = t;
		seen = false;
	}
	
	public String toString(){
		return "Produces " + milk + " gallons of milk if produced before time " + time;
	}
	
}

class MilkCowTimeComparator implements Comparator<MilkCow>{
	
	public int compare(MilkCow a, MilkCow b){
		return a.time - b.time;
	}
	
}

class MilkCowMilkComparator implements Comparator<MilkCow>{
	
	public int compare(MilkCow a, MilkCow b){
		return a.milk - b.milk;
	}
}