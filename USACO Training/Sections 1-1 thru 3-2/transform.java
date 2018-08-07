/*
ID: ayc22031
LANG: JAVA
PROG: transform
 */

import java.io.*;

/*
 * A square pattern of size N x N (1 <= N <= 10) black and white square tiles is transformed into another square pattern. Write a program that will recognize the minimum transformation that has been applied to the original pattern given the following list of possible transformations:

#1: 90 Degree Rotation: The pattern was rotated clockwise 90 degrees.
#2: 180 Degree Rotation: The pattern was rotated clockwise 180 degrees.
#3: 270 Degree Rotation: The pattern was rotated clockwise 270 degrees.
#4: Reflection: The pattern was reflected horizontally (turned into a mirror image of itself by reflecting around a vertical line in the middle of the image).
#5: Combination: The pattern was reflected horizontally and then subjected to one of the rotations (#1-#3).
#6: No Change: The original pattern was not changed.
#7: Invalid Transformation: The new pattern was not obtained by any of the above methods.
In the case that more than one transform could have been used, choose the one with the minimum number above.
 */
public class transform {
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("transform.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("transform.out")));
		int length = Integer.parseInt(read.readLine());
		char[][] temp = new char[length][length];
		for(int i = 0; i < temp.length; i++){
			String line = read.readLine();
			for(int j = 0; j < temp[i].length; j++){
				temp[i][j] = line.charAt(j); 
			}
		}
		Grid orig = new Grid(temp);
		char[][] temp2 = new char[length][length];
		for(int i = 0; i < temp2.length; i++){
			String line = read.readLine();
			for(int j = 0; j < temp2[i].length; j++){
				temp2[i][j] = line.charAt(j); 
			}
		}
		Grid comp = new Grid(temp2);
//		orig = Grid.rotate(Grid.rotate(Grid.rotate(orig)));
//		comp = Grid.rotate(Grid.rotate(Grid.rotate(comp)));
		orig.print();
		System.out.println();
		comp.print();
		System.out.println();
		System.out.println(orig.equals(comp));
		if(Grid.rotate(orig).equals(comp)){
			out.println("1");
		} else if (Grid.rotate(Grid.rotate(orig)).equals(comp)){
			out.println("2");
		} else if (Grid.rotate(Grid.rotate(Grid.rotate(orig))).equals(comp)){
			out.println("3");
		} else {
			orig = Grid.reflect(orig);
			if(orig.equals(comp)){
				out.println("4");
			} else if(Grid.rotate(orig).equals(comp)){
				out.println("5");
			} else if (Grid.rotate(Grid.rotate(orig)).equals(comp)){
				out.println("5");
			} else if (Grid.rotate(Grid.rotate(Grid.rotate(orig))).equals(comp)){
				out.println("5");
			} else if(Grid.reflect(orig).equals(comp)){
				out.println("6");
			} else {
				out.println("7");
			}
		}
		out.close();
		System.exit(0);
		
	}
	
}

class Grid{
	
	private char[][] grid;
	private int size;
	
	public Grid(char[][] in){
		grid = in;
		size = in.length;
	}
	
	public int size(){
		return size;
	}
	
	public char[][] getGrid(){
		return grid;
	}
	
	public boolean equals(Grid other){
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++){
				if(grid[i][j] != other.getGrid()[i][j]) return false;
			}
		}
		return true;
	}
	
	public void print(){
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
	
	public static Grid rotate(Grid orig){
		char[][] changed = new char[orig.size()][orig.size()];
		char[][] old = orig.getGrid();
		for(int i = 0; i < old.length; i++){
			for(int j = 0; j < old[i].length; j++){
				changed[j][changed.length - 1 - i] = old[i][j];
			}
		}
		return new Grid(changed);
	}
	
	public static Grid reflect(Grid orig){
		char[][] changed = new char[orig.size()][orig.size()];
		char[][] old = orig.getGrid();
		for(int i = 0; i < old.length; i++){
			for(int j = 0; j < old[i].length; j++){
				changed[i][changed.length - 1 - j] = old[i][j];
			}
		}
		return new Grid(changed);
	}
	
	
}
