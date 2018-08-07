/*
Allen Cheng, ID ayc22032
USACO, December 2015
Gold 1: High Card Low Card (Gold)
*/
/*
Bessie the cow is a huge fan of card games, which is quite surprising, given her lack of opposable
thumbs. Unfortunately, none of the other cows in the herd are good opponents. They are so bad, in
fact, that they always play in a completely predictable fashion! Nonetheless, it can still be a
challenge for Bessie to figure out how to win.
Bessie and her friend Elsie are currently playing a simple card game where they take a deck of 2N
cards, conveniently numbered 1... 2N, and divide them into N cards for Bessie and N cards for Elsie.
The two then play N rounds, where in each round Bessie and Elsie both play a single card. In the
first N/2 rounds, the player with the highest card earns a point, and in the last N/2 rounds, the
rules switch and the player who plays the lowest card wins a point.

Given that Bessie can predict the order in which Elsie will play her cards, please determine the
maximum number of points Bessie can win.

INPUT FORMAT (file cardgame.in):

The first line of input contains the value of N (2 <= N <= 50,000; N will be even).
The next N lines contain the cards that Elsie will play in each of the successive rounds of the
game. Note that it is easy to determine Bessie's cards from this information.

OUTPUT FORMAT (file cardgame.out):

Output a single line giving the maximum number of points Bessie can score.

SAMPLE INPUT:

4
1
8
4
3

SAMPLE OUTPUT:

2
Here, Bessie must have cards 2, 5, and 6, and 7 in her hand, and she can use these to win at most 2
points, by saving her '2' card until one of the hands in the second half of the game.

Problem credits: Brian Dean
*/

import java.io.*;
import java.util.*;

public class HighLowCard {
		
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("cardgame.in"));
		int N = Integer.parseInt(read.readLine());
		boolean[] used = new boolean[2 * N];
		int[] bCards = new int[N];
		int[] eCards = new int[N];
		for(int i = 0; i < N; i++){
			eCards[i] = Integer.parseInt(read.readLine()) - 1;
			used[eCards[i]] = true;
		}
		read.close();
		
		int bInd = 0;
		for(int i = 0; i < used.length && bInd < N; i++){
			if(!used[i])
				bCards[bInd++] = i;
		}
		
		int[][] edges = new int[2 * N + 2][];
		int[][] matr = new int[2 * N + 2][2 * N + 2];
		edges[0] = new int[N];
		matr[0] = new int[N];
		for(int i = 1; i <= N; i++){
			edges[0][i - 1] = i;
			matr[0][i - 1] = 1;
		}

		for(int i = 0; i < N; i++){
			int count = 1;
			for(int j = 0; j < N / 2; j++){
				if(bCards[i] > eCards[j]){
					count++;
				}
			}
			for(int j = N / 2; j < N; j++){
				if(bCards[i] < eCards[j]){
					count++;
				}
			}
			
			edges[i + 1] = new int[count];
			matr[i + 1] = new int[count];
			
			edges[i + 1][0] = 0;
			matr[i + 1][0] = 0;
			int ind = 1;
			for(int j = 0; j < N / 2; j++){
				if(bCards[i] > eCards[j]){
					edges[i + 1][ind] = j + N + 1;
					matr[i + 1][ind++] = 1;
				}
			}
			for(int j = N / 2; j < N; j++){
				if(bCards[i] < eCards[j]){
					edges[i + 1][ind] = j + N + 1;
					matr[i + 1][ind++] = 1;
				}
			}
		}
		
		edges[2 * N + 1] = new int[N];
		matr[2 * N + 1] = new int[N];
		for(int i = 1; i <= N; i++){
			edges[2 * N + 1][i - 1] = i + N;
			matr[2 * N + 1][i - 1] = 0;
		}
		
		for(int i = 0; i < N / 2; i++){
			int count = 1;
			for(int j = 0; j < N; j++){
				if(bCards[j] > eCards[i]){
					count++;
				}
			}
			edges[i + N + 1] = new int[count];
			matr[i + N + 1] = new int[count];
			
			edges[i + N + 1][count - 1] = 2 * N + 1;
			matr[i + N + 1][count - 1] = 1;
			int ind = 0;
			for(int j = 0; j < N; j++){
				if(bCards[j] > eCards[i]){
					edges[i + N + 1][ind] = j + 1;
					matr[i + N + 1][ind++] = 0;
				}
			}
		}
		for(int i = N / 2; i < N; i++){
			int count = 1;
			for(int j = 0; j < N; j++){
				if(bCards[j] < eCards[i]){
					count++;
				}
			}
			edges[i + N + 1] = new int[count];
			matr[i + N + 1] = new int[count];
			
			edges[i + N + 1][count - 1] = 2 * N + 1;
			matr[i + N + 1][count - 1] = 1;
			int ind = 0;
			for(int j = 0; j < N; j++){
				if(bCards[j] < eCards[i]){
					edges[i + N + 1][ind] = j + 1;
					matr[i + N + 1][ind++] = 0;
				}
			}
		}
		
				
		int M = 2 * (N + 1);
//		for(int i = 0; i < M; i++){
//			System.out.println(Arrays.toString(edges[i]));
//		}
		
		int total = 0;
		
		
		while(true){
//			for(int i = 0; i < M; i++){
//				System.out.println(Arrays.toString(matr[i]));
//			}
//			System.out.println(total);
			int[] prevNodes = new int[M];
			int[] flows = new int[M];
			boolean[] visited = new boolean[M];
			for(int i = 0; i < M; i++){
				prevNodes[i] = -1;
			}
			flows[0] = Integer.MAX_VALUE;
			
			boolean done = false;
			while(true){
//				System.out.println(Arrays.toString(flows));
				int maxFlow = 0;
				int maxLoc = -1;
				for(int i = 0; i < M; i++){
					if(flows[i] > maxFlow && !visited[i]){
						maxFlow = flows[i];
						maxLoc = i;
					}
				}
				//System.out.println(maxLoc);
				//System.out.println(Arrays.toString(flows));
				if(maxLoc == -1){
					done = true;
//					System.out.println("!");
					break;
				}
				if(maxLoc == M - 1)
					break;
				visited[maxLoc] = true;
				for(int i = 0; i < edges[maxLoc].length; i++){
					if(flows[edges[maxLoc][i]] < Math.min(maxFlow, matr[maxLoc][i])){
						prevNodes[edges[maxLoc][i]] = maxLoc;
						flows[edges[maxLoc][i]] = Math.min(maxFlow, matr[maxLoc][i]);
					}
				}
			}
			if(done)
				break;
			int pathCap = flows[M - 1];
			total += pathCap;
			
			int curr = M - 1;
			while(curr != 0){
				int next = prevNodes[curr];
				int indN = Arrays.binarySearch(edges[next], curr);
				matr[next][indN] -= pathCap;
				int indC = Arrays.binarySearch(edges[curr], next);
				matr[curr][indC] += pathCap;
				curr = next;
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("cardgame.out"));
		out.println(total);
		System.out.println(total);
		out.close();
		System.exit(0);
		
	}
	
}
