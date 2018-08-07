/*
ID: ayc22031
LANG: JAVA
PROG: beads
 */

import java.io.*;

public class beads {
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("beads.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));
		char[] necklace = new char[Integer.parseInt(read.readLine())];
		int len = necklace.length;
		for(int i = 0; i < len; i++){
			necklace[i] = (char)read.read();
		}
		int max = 0;
		for(int i = 0; i < len; i++){
			int temp = i;
			int count = 0;
			while(necklace[temp] == 'w'){
				temp = (len + temp - 1) % len;
				count++;
				if(count > len){
					out.println(len);
					out.close();
					System.exit(0);
				}
			}
			char comp = necklace[temp];
			if(comp == 'r') comp = 'b';
			else comp = 'r';
			while(comp != necklace[temp]){
				temp = (len + temp - 1) % len;
				count++;
				if(count > len){
					out.println(len);
					out.close();
					System.exit(0);
				}
			}
			
			temp = (i + 1) % len;
			while(necklace[temp] == 'w'){
				temp = (temp + 1) % len;
				count++;
				if(count > len){
					out.println(len);
					out.close();
					System.exit(0);
				}
			}
			comp = necklace[temp];
			if(comp == 'r') comp = 'b';
			else comp = 'r';
			while(comp != necklace[temp]){
				temp = (temp + 1) % len;
				count++;
				if(count > len){
					out.println(len);
					out.close();
					System.exit(0);
				}
			}
			max = Math.max(max, count);
		}
		out.println(max);
		out.close();
		System.exit(0);
		
	}
	
}
