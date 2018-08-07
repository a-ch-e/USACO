/*
ID: ayc22032
LANG: JAVA
TASK: skidesign
 */

/*
Ski Course Design
Farmer John has N hills on his farm (1 <= N <= 1,000), each with an integer elevation in the range 0 .. 100. In the winter, since there is abundant snow on these hills, FJ routinely operates a ski training camp.

Unfortunately, FJ has just found out about a new tax that will be assessed next year on farms used as ski training camps. Upon careful reading of the law, however, he discovers that the official definition of a ski camp requires the difference between the highest and lowest hill on his property to be strictly larger than 17. Therefore, if he shortens his tallest hills and adds mass to increase the height of his shorter hills, FJ can avoid paying the tax as long as the new difference between the highest and lowest hill is at most 17.

If it costs x^2 units of money to change the height of a hill by x units, what is the minimum amount of money FJ will need to pay? FJ can change the height of a hill only once, so the total cost for each hill is the square of the difference between its original and final height. FJ is only willing to change the height of each hill by an integer amount.

PROGRAM NAME: skidesign

INPUT FORMAT:

Line 1:	The integer N.
Lines 2..1+N:	Each line contains the elevation of a single hill.
SAMPLE INPUT (file skidesign.in):

5
20
4
1
24
21
INPUT DETAILS:

FJ's farm has 5 hills, with elevations 1, 4, 20, 21, and 24.

OUTPUT FORMAT:

The minimum amount FJ needs to pay to modify the elevations of his hills so the difference between largest and smallest is at most 17 units.
Line 1:
SAMPLE OUTPUT (file skidesign.out):

18
OUTPUT DETAILS:

FJ keeps the hills of heights 4, 20, and 21 as they are. He adds mass to the hill of height 1, bringing it to height 4 (cost = 3^2 = 9). He shortens the hill of height 24 to height 21, also at a cost of 3^2 = 9. 
 */

import java.io.*;
import java.util.*;

public class skidesign {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("skidesign.in"));
		int N = Integer.parseInt(read.readLine());
		int[] hills = new int[N];
		for(int i = 0; i < N; i++){
			hills[i] = Integer.parseInt(read.readLine());
		}
		read.close();
		
		Arrays.sort(hills);
		
		int[] adds = new int[N];

		int start = 0;
		long min = Long.MAX_VALUE;
		for(int i = hills[0]; i < hills[N - 1]; i++){
			long sum = 0;
			for(int j = 0; j < N; j++){
				if(hills[j] < i){
					sum += (i - hills[j]) * (i - hills[j]);
				}
				if(hills[j] > i + 17){
					sum += (hills[j] - i - 17) * (hills[j] - i - 17);
				}
			}
			if(sum < min){
				start = i;
				min = sum;
			}
		}
		
		System.out.println(start);
		
		for(int i = 1; hills[N - 1] - hills[0] > 17; i++){
		if(hills[N - 1 - i] - hills[i] > 17){
			for(int j = 0; j < i; j++){
				adds[j] += hills[i] - hills[j];
				hills[j] = hills[i];
				adds[N - 1 - j] += hills[N - 1 - j] - hills[N - 1 - i];
				hills[N - 1 - j] = hills[N - 1 - i];
			}
		} else {
			int a = Math.min(hills[i] - hills[0], (hills[N - 1] - hills[0] - 17) / 2);
			int b = hills[N - 1] - hills[0] - 17 - a;
			for(int j = 0; j < i; j++){
				adds[j] += a;
				hills[j] += a;
				adds[N - 1 - j] += b;;
				hills[N - 1 - j] -= b;
			}
		}
	}
	System.out.println(Arrays.toString(hills));

		
		PrintWriter out = new PrintWriter(new FileWriter("skidesign.out"));
		out.println(min);
		System.out.println(min);
		out.close();
		System.exit(0);
		
	}
	
}
