import java.io.*;
import java.util.*;

public class PalindromePath {
	
	private static char[][] grid;
	private static int count;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("palpath.in"));
		int N = Integer.parseInt(read.readLine());
		grid = new char[N][N];
		for(int i = 0; i < N; i++){
			grid[i] = read.readLine().toCharArray();
		}
		read.close();
		
		count = 0;
		recur(0, 0, N - 1, N - 1);
		PrintWriter out = new PrintWriter(new FileWriter("palpath.out"));
		out.println(count);
		System.out.println(count);
		out.close();
		System.exit(0);
		
	}
	
	private static void recur(int x1, int y1, int x2, int y2){
		
		if(x1 == x2){
			for(int i = 1; i < (x2 - x1)/2; i++){
				if(grid[x1][y1 + i] != grid[x2][y2 - i])
					return;
			}
			count++;
			count %= 1000000007;
			return;
		}
		if(y1 == y2){
			for(int i = 1; i < (y2 - y1)/2; i++){
				if(grid[x1 + i][y1] != grid[x2 - i][y1])
					return;
			}
			count++;
			count %= 1000000007;
			return;
		}
		if(grid[x1][y1 + 1] == grid[x2][y2 - 1])
			recur(x1, y1 + 1, x2, y2 - 1);
		if(grid[x1][y1 + 1] == grid[x2 - 1][y2])
			recur(x1, y1 + 1, x2 - 1, y2);
		if(grid[x1 + 1][y1] == grid[x2][y2 - 1])
			recur(x1 + 1, y1, x2, y2 - 1);
		if(grid[x1 + 1][y1] == grid[x2 - 1][y2])
			recur(x1 + 1, y1, x2 - 1, y2);
		
	}
	
}
