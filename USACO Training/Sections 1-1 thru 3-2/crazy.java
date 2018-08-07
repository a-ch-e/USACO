import java.io.*;

/*
 * Allen Cheng
 * Bronze Division
 * Crazy Fences, December
 */

public class crazy {

	private static char[][] grid;
	private static boolean[][] track;

	public static void main(String[] args) throws Exception {

		long t1 = System.nanoTime();
		BufferedReader read = new BufferedReader(new FileReader("crazy.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"crazy.out")));
		String[] temp = read.readLine().split(" ");
		grid = new char[10000][10000];
		track = new boolean[10000][10000];
		int fxmax = 0;
		int fymax = 0;
		for (int i = 0; i < Integer.parseInt(temp[0]); i++) {
			String[] temp2 = read.readLine().split(" ");
			if (temp2[0].equals(temp2[2])) {
				int y1 = Integer.parseInt(temp2[1]);
				int y2 = Integer.parseInt(temp2[3]);
				int x = Integer.parseInt(temp2[0]);
				fxmax = Math.max(fxmax, x);
				if (y1 <= y2) {
					for (int j = y1; j <= y2; j++) {
						grid[x][j] = 'f'; 
						fymax = Math.max(fymax, j);
					}
				} else {
					for (int j = y1; j >= y2; j--) {
						grid[x][j] = 'f';
						fymax = Math.max(fymax, j);
					}
				}
			} else {
				int x1 = Integer.parseInt(temp2[0]);
				int x2 = Integer.parseInt(temp2[2]);
				int y = Integer.parseInt(temp2[1]);
				fymax = Math.max(fymax, y);
				if (x1 <= x2) {
					for (int j = x1; j <= x2; j++) {
						grid[j][y] = 'f';
						fxmax = Math.max(fxmax, j);
					}
				} else {
					for (int j = x1; j >= x2; j--) {
						grid[j][y] = 'f';
						fxmax = Math.max(fxmax, j);
					}
				}
			}
		}
		int len = Integer.parseInt(temp[1]);
		int[][] coords = new int[2][len];
		for (int i = 0; i < len; i++) {
			String[] temp2 = read.readLine().split(" ");
			grid[Integer.parseInt(temp2[0])][Integer.parseInt(temp2[1])] = 'c';
			coords[0][i] = Integer.parseInt(temp2[0]);
			coords[1][i] = Integer.parseInt(temp2[1]);
		}
		int[] cncns = new int[len];
		int max = 0;
		for(int i = 0; i < len; i++){
			int x = coords[0][i];
			int y = coords[1][i];
			if(x > fxmax || y > fymax){
				max++;
				break;
			}
			cncns[i] = ways(x, y, x, y) + 1;
		}
//		for(int i = 0; i < len; i++){
//			System.out.print(cncns[i] + " ");
//		}
		for(int i = 1; i < len; i++){
			max = Math.max(max, cncns[i]);
		}
		out.println(max);
		out.close();
//		System.out.println((System.nanoTime() - t1)/1000000);
		System.exit(0);

	}

	private static int ways(int x, int y, int ox, int oy) {
		if (track[x][y] || grid[x][y] == 'f') {
			return 0;
		}
		if(x == 1 && y == 5) System.out.println("Here");
//		System.out.println(x + ", " + y);
		if(grid[x][y] == 'c' && !(x == ox && y == oy)){
			return 1;
		}
		track[x][y] = true;
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		if (x < grid[0].length - 1){
			a = ways(x + 1, y, ox, oy);
		}
		if (y < grid.length - 1){
			b = ways(x, y + 1, ox, oy);
		}
		if (x > 0){
			c = ways(x - 1, y, ox, oy);
		}
		if (y > 0){
			d = ways(x, y - 1, ox, oy);
		}
		track[x][y] = false;
		return a + b + c+ d;
	}

}
