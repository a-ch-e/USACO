import java.io.*;
import java.util.*;

public class Snowflakes {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(read.readLine());
		Snowflake[] flakes = new Snowflake[240 * N];
		for(int i = 0; i < N; i++){
			flakes[240 * i] = new Snowflake(i + 1, read.readLine());
			for(int j = 1; j < flakes[240 * i].flake.length(); j++){
				flakes[240 * i + j] = flakes[240 * i + j - 1].rotate();
			}
		}
		read.close();
		
		Arrays.sort(flakes);
		for(int i = 0; i < flakes.length - 1; i++){
			if(flakes[i].equals(flakes[i + 1])){
				System.out.println("YES");
				System.out.println(flakes[i].ind + " " + flakes[i + 1].ind);
				System.exit(0);
			}
		}
		System.out.println("NO");
		System.exit(0);
		
	}
	
}

class Snowflake implements Comparable<Snowflake>{
	
	String flake;
	int ind;
	
	public Snowflake(int i, String s){
		ind = i;
		flake = s;
	}
	
	public Snowflake rotate(){
		
		return new Snowflake(ind, flake.substring(1) + flake.charAt(0));
		
	}
	
	public int compareTo(Snowflake other){
		return this.flake.compareTo(other.flake);
	}
	
	public boolean equals(Snowflake other){
		return this.flake.equals(other.flake);
	}
	
}