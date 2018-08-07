/*
 * Allen Cheng
 * Bronze Division
 * USACO March Contest
 * Problem 1 - Cow Race
 */

/*
 Problem 1: Cow Race [Brian Dean, 2013]

 In order to finally settle their long-running dispute over who is the
 faster cow, Bessie and her friend Elsie decide to hold a race across the farm.

 The two cows start at the same location and begin running in the same
 direction at the same time.  The progress of each cow is described by a
 series of "segments", during each of which the cow runs at a constant
 speed.  For example, Bessie might run at a speed of 5 for 3 units of time,
 then at a speed of 10 for 6 units of time.  Bessie and Elsie both run for
 the same total amount of time.

 The cows would like your help in counting the number of leadership changes
 during their race.  A leadership change happens at a point in time when cow
 A pulls into the lead of cow B, whereas the last time one cow was in the
 lead, it was cow B.  For example, if B is in the lead and then A pulls
 ahead, then this is a leadership change.  If B is in the lead, and then A
 becomes even with B for some time and then finally pulls ahead, then this
 also counts as a leadership change.

 PROBLEM NAME: cowrace

 INPUT FORMAT:

 * Line 1: Two space-separated integers, N and M.  (1 <= N, M <= 1000)

 * Lines 2..1+N: Each line contains one of the N segments of Bessie's
 run, described by two integers: Bessie's speed and the amount
 of time she runs at that speed (both integers are in the range
 1..1000).

 * Lines 2+N..1+N+M: Each line contains one of the M segments of
 Elsie's run, described by two integers: Elsie's speed and the
 amount of time she runs at that speed (both integers are in
 the range 1..1000).

 SAMPLE INPUT (file cowrace.in):

 4 3
 1 2
 4 1
 1 1
 2 10
 2 3
 1 2
 3 9

 INPUT DETAILS:

 Bessie runs at a speed of 1 for 2 units of time, then at a speed of 4 for 1
 unit of time, then at a speed of 1 for 1 unit of time, and finally at a
 speed of 2 for 10 units of time.  Elsie runs at a speed of 2 for 3 units of
 time, then at a speed of 1 for 2 units of time, then finally at a speed of
 3 for 9 units of time.  Note that both cows run for a total of 14 units of
 time.

 OUTPUT FORMAT:

 * Line 1: The number of leadership changes during the race.

 SAMPLE OUTPUT (file cowrace.out):

 2

 OUTPUT DETAILS:

 Elsie is ahead until time t=3, when both cows meet after both have traveled
 6 units of total distance and travel together for 1 unit of time.  Bessie
 then pulls ahead briefly (the first leadership change), only to be
 overtaken shortly thereafter by Elsie (the second leadership change). 
 Elsie ends the race in the lead.
 */

import java.util.StringTokenizer;
import java.io.*;

public class CowRace {

	public static void main(String[] args) throws IOException {

		BufferedReader read = new BufferedReader(new FileReader("cowrace.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"cowrace.out")));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int len1 = Integer.parseInt(st.nextToken());
		int len2 = Integer.parseInt(st.nextToken());
		int time = 0;
		Spurt[] bessie = new Spurt[len1];
		Spurt[] elsie = new Spurt[len2];
		for (int i = 0; i < len1; i++) {
			st = new StringTokenizer(read.readLine());
			int speed = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			time += t;
			bessie[i] = new Spurt(speed, t);
		}
		for (int i = 0; i < len2; i++) {
			st = new StringTokenizer(read.readLine());
			int speed = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			elsie[i] = new Spurt(speed, t);
		}

		int bind = 0;
		int eind = 0;
		int bdist = 0;
		int edist = 0;
		int btime = 0;
		int etime = 0;
		int track = 0;
		int count = 0;
		for (int i = 0; i < time; i++) {
			bdist += bessie[bind].speed;
			btime++;
			if (btime >= bessie[bind].time) {
				btime = 0;
				bind++;
			}
			edist += elsie[eind].speed;
			etime++;
			if (etime >= elsie[eind].time) {
				etime = 0;
				eind++;
			}
//			System.out.println(bdist + " " + edist);
			int temp = bdist - edist;
			if (temp == 0)
				continue;
			if (track != 0 && (temp < 0 ^ track < 0)) {
				count++;
			}
			track = temp;
		}
		out.println(count);
		read.close();
		out.close();
		System.exit(0);

	}

}

class Spurt {

	public int speed;
	public int time;

	public Spurt(int s, int t) {
		speed = s;
		time = t;
	}

}
