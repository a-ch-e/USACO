/*
ID: ayc22032
LANG: JAVA
TASK: job
*/

/*
Job Processing
IOI'96
A factory is running a production line that requires two operations to be performed on each job:
first operation "A" then operation "B". Only a certain number of machines are capable of performing
each operation.

Figure 1 shows the organization of the production line that works as follows. A type "A" machine
takes a job from the input container, performs operation "A" and puts the job into the intermediate
container. A type "B" machine takes a job from the intermediate container, performs operation "B"
and puts the job into the output container. All machines can work in parallel and independently of
each other, and the size of each container is unlimited. The machines have different performance
characteristics, a given machine requires a given processing time for its operation.

Give the earliest time operation "A" can be completed for all N jobs provided that the jobs are
available at time 0. Compute the minimal amount of time that is necessary to perform both
operations (successively, of course) on all N jobs.

PROGRAM NAME: job

INPUT FORMAT

Line 1:	Three space-separated integers:
N, the number of jobs (1<=N<=1000).
M1, the number of type "A" machines (1<=M1<=30)
M2, the number of type "B" machines (1<=M2<=30)
Line 2..etc:	M1 integers that are the job processing times of each type "A" machine (1..20)
followed by M2 integers, the job processing times of each type "B" machine (1..20).

SAMPLE INPUT (file job.in)

5 2 3
1 1 3 1 4

OUTPUT FORMAT

A single line containing two integers: the minimum time to perform all "A" tasks and the minimum
time to perform all "B" tasks (which require "A" tasks, of course).

SAMPLE OUTPUT (file job.out)

3 5
*/

import java.io.*;
import java.util.*;

public class job {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("job.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M1 = Integer.parseInt(st.nextToken());
		int M2 = Integer.parseInt(st.nextToken());
		int[] A = new int[M1];
		st = new StringTokenizer(read.readLine());
		for(int i = 0; i < M1; i++){
			if(!st.hasMoreTokens())
				st = new StringTokenizer(read.readLine());
			A[i] = Integer.parseInt(st.nextToken());
		}
		int[] B = new int[M2];
		for(int i = 0; i < M2; i++){
			if(!st.hasMoreTokens())
				st = new StringTokenizer(read.readLine());
			B[i] = Integer.parseInt(st.nextToken());
		}
		read.close();
		
		Arrays.sort(A);
		Arrays.sort(B);
		int[] aFree = new int[M1];
		int[] bFree = new int[M2];
		
		int time;
		for(time = 1; ; time++){
			int count = 0;
			for(int i = 0; i < M1; i++){
				count += time / A[i];
			}
			if(count >= N)
				break;
		}
		int[] aCounts = new int[M1];
		int n1 = N;
		for(int i = M1 - 1; i >= 0 && n1 > 0; i--){
			aCounts[i] = time / A[i];
			n1 -= aCounts[i];
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("job.out"));
		out.print(time + " ");
		System.out.print(time + " ");
		
		int[] order = new int[N];
		int[] bQ = new int[M2];
		for(int i = 0; i < N; i++){
			int min = 0;
			for(int j = 1; j < M2; j++){
				if(bQ[j] + B[j] < bQ[min] + B[min]){
					min = j;
				}
			}
			order[i] = min;
			bQ[min] += B[min];
		}
		//System.out.println(Arrays.toString(order));
		
		n1 = N;
		int n2 = 0;
		int done = 0;
		int ind = N - 1;
		for(time = 0; ; time++){
			//System.out.println(n1 + " " + n2);
			//System.out.println(Arrays.toString(aFree));
			//System.out.println(Arrays.toString(bFree));
			for(int i = 0; i < M1 && time > 0; i++){
				if(aFree[i] == time)
					n2++;
			}
			for(int i = 0; i < M2 && time > 0; i++){
				if(bFree[i] == time)
					done++;
			}
			
			for(int i = 0; i < M1 && n1 > 0; i++){
				if(aFree[i] <= time && aCounts[i] > 0){
					n1--;
					aFree[i] = time + A[i];
					aCounts[i]--;
				}
			}
//			if(n1 == 0 && max(aFree) == time){
//				out.print(time + " ");
//				System.out.print(time + " ");
//			}
			while(n2 > 0){
				int mach = order[ind];
				if(bFree[mach] > time)
					break;
				ind--;
				n2--;
				bFree[mach] = time + B[mach];
			}
			if(done == N && max(bFree) == time){
				out.println(time);
				System.out.println(time);
				break;
			}
		}
		out.close();
		System.exit(0);
		
	}
	
	private static int max(int[] arr){
		if(arr.length < 1)
			return -1;
		int max = arr[0];
		for(int i = 1; i < arr.length; i++){
			max = Math.max(arr[i], max);
		}
		return max;
	}
	
}
