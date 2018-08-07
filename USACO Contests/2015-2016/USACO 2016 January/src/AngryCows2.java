/*
Allen Cheng, ID ayc22032
USACO, January 2016
Gold 1: Angry Cows
*/

/*
Bessie the cow has designed what she thinks will be the next big hit video game: "Angry Cows". The
premise, which she believes is completely original, is that the player shoots a cow with a
slingshot into a one-dimensional scene consisting of a set of hay bales located at various points
on a number line; the cow lands with sufficient force to detonate the hay bales in close proximity
to her landing site, which in turn might set of a chain reaction that causes additional hay bales
to explode. The goal is to use a single cow to start a chain reaction that detonates all the hay
bales.

There are N hay bales located at distinct integer positions x1,x2,...,xN on the number line. If a
cow is launched with power R landing at position x, this will causes a blast of "radius R",
engulfing all hay bales within the range x-R...x+R. These hay bales then themselves explode (all
simultaneously), each with a blast radius of R-1. Any not-yet-exploded bales caught in these blasts
then all explode (all simultaneously) with blast radius R-2, and so on.

Please determine the minimum amount of power R with which a single cow may be launched so that, if
it lands at an appropriate location, it will cause subsequent detonation of every single hay bale
in the scene.

INPUT FORMAT (file angry.in):

The first line of input contains N (2<=N<=50,000). The remaining N lines all contain integers
x1...xN (each in the range 0...1,000,000,000).

OUTPUT FORMAT (file angry.out):

Please output the minimum power R with which a cow must be launched in order to detonate all the
hay bales. Answers should be rounded and printed to exactly 1 decimal point.

SAMPLE INPUT:

5
8
10
3
11
1

SAMPLE OUTPUT:

3.0

In this example, a cow launched with power 3 at, say, location 5, will cause immediate detonation
of hay bales at positions 3 and 8. These then explode (simultaneously) each with blast radius 2,
engulfing bales at positions 1 and 10, which next explode (simultaneously) with blast radius 1,
engulfing the final bale at position 11, which finally explodes with blast radius 0.

Problem credits: Brian Dean
 */
import java.io.*;
import java.util.*;

public class AngryCows2 {

	public static void main(String[] args) throws IOException {

		BufferedReader read = new BufferedReader(new FileReader("angry.in"));
		int N = Integer.parseInt(read.readLine());
		int[] bales = new int[N];
		for(int i = 0; i < N; i++){
			bales[i] = Integer.parseInt(read.readLine());
		}
		read.close();
		
		Arrays.sort(bales);
		
		int[] toGoLeft = new int[N];
		int[] toGoRight = new int[N];
		for(int i = 1; i < N; i++){
			toGoLeft[i] = Math.max(bales[i] - bales[i - 1], toGoLeft[i - 1] + 1);
			toGoRight[N - 1 - i] = Math.max(bales[N - i] - bales[N - 1 - i], toGoRight[N - i] + 1);
		}
		double best = Math.min(toGoRight[0], toGoLeft[N - 1]);
		for(int i = 0; i < N - 1; i++){
			double temp = Math.max(toGoLeft[i] + 1, toGoRight[i + 1] + 1);
			temp = Math.max(temp, (bales[i + 1] - bales[i]) / 2.0);
			best = Math.min(best, temp);
		}

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));
		out.format("%.1f\n", best);
		System.out.format("%.1f\n", best);
		out.close();
		System.exit(0);

	}

}
