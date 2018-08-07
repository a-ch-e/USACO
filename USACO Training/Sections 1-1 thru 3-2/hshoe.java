/*
 * Allen Cheng
 * ID: ayc22032
 * Lang: JAVA
 * Task: hshoe
 */

import java.io.*;

public class hshoe {
	
	private static char[][] grid;
	private static boolean[][] track;
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("hshoe.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hshoe.out")));
		int dim = Integer.parseInt(read.readLine());
		grid = new char[dim][dim];
		track = new boolean[dim][dim];
		for(int i = 0; i < dim; i++){
			String temp = read.readLine();
			for(int j = 0; j < dim; j++){
				grid[i][j] = temp.charAt(j);
			}
		}
		
		System.out.println(ways(0, 0, 0, 0));
		out.close();
		System.exit(0);
	}
	
	private static int ways(int x, int y, int open, int close){
		if(track[x][y]){
			return 0;
		}
		if(grid[x][y] == ')'){
			close++;
			if(close == open){
				return 1;
			} else {
				track[x][y] = true;
				int max = 0;
				if(x < grid[0].length - 1)
					max = ways(x + 1, y, open, close);
				
				if(y < grid.length - 1)
					max = Math.max(max, ways(x, y + 1, open, close));
				
				if(x > 0)
					max = Math.max(max, ways(x - 1, y, open, close));
				
				if(y > 0)
					max = Math.max(max, ways(x, y - 1, open, close));
				track[x][y] = false;
				if(max > 0) return max + 1;
			}
		} else {
			if(close > 0){
				return 0;
			} else {
				open++;
				track[x][y] = true;
				int max = 0;
				if(x < grid[0].length - 1)
					max = ways(x + 1, y, open, close);
				
				if(y < grid.length - 1)
					max = Math.max(max, ways(x, y + 1, open, close));
				
				if(x > 0)
					max = Math.max(max, ways(x - 1, y, open, close));
				
				if(y > 0)
					max = Math.max(max, ways(x, y - 1, open, close));
				track[x][y] = false;
				if(max > 0) return max + 1;
			}
		}
		return 0;
	}
	
	private static int paths(char[][] grid, char key, int x, int y, int open, int close){
		print(grid);
		if(key == '('){
			if(grid[x][y] == ')'){
				return paths(grid, ')', x, y, open, close);
			} else if (grid[x][y] != '('){
				return 0;
			}
		}
		if(key == ')' && grid[x][y] != ')'){
			return 0;
		}
		if(key == ')' && close == open){
			return 0;
		}
		if(key == '('){
			int max = 0;
			if(x < grid[0].length - 1){
				grid[x][y] = 'x';
				max = 1 + paths(grid, '(',  x + 1, y, open + 1, close);
				grid[x][y] = '(';
			}
			if(y < grid.length - 1){
				grid[x][y] = 'x';
				max = Math.max(max, 1 + paths(grid, '(',  x, y + 1, open + 1, close));
				grid[x][y] = '(';
			}
			if(x> 0){
				grid[x][y] = 'x';
				max = Math.max(max, 1 + paths(grid, '(',  x - 1, y, open + 1, close));
				grid[x][y] = '(';
			}
			if(y > 0){
				grid[x][y] = 'x';
				max = Math.max(max, 1 + paths(grid, '(',  x, y - 1, open + 1, close));
				grid[x][y] = '(';
			}
			return max;
		} else {
			if(key == ')'){
				int max = 0;
				if(x < grid[0].length - 1){
					grid[x][y] = 'x';
					max = 1 + paths(grid, ')',  x + 1, y, open, close + 1);
					grid[x][y] = ')';
				}
				if(y < grid.length - 1){
					grid[x][y] = 'x';
					max = Math.max(max, 1 + paths(grid, ')',  x, y + 1, open, close + 1));
					grid[x][y] = ')';
				}
				if(x> 0){
					grid[x][y] = 'x';
					max = Math.max(max, 1 + paths(grid, ')',  x - 1, y, open, close + 1));
					grid[x][y] = ')';
				}
				if(y > 0){
					grid[x][y] = 'x';
					max = Math.max(max, 1 + paths(grid, ')',  x, y - 1, open, close + 1));
					grid[x][y] = ')';
				}
			}
		}
		
		return 0;
	}
	
	
	private static void print(char[][] grid){
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
}
