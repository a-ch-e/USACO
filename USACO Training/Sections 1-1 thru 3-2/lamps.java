/*
ID: ayc22032
LANG: JAVA
TASK: lamps
 */

/*

Party Lamps
IOI 98 

To brighten up the gala dinner of the IOI'98 we have a set of N (10 <= N <= 100) colored lamps numbered from 1 to N. 
The lamps are connected to four buttons:

	-Button 1: When this button is pressed, all the lamps change their state: those that are ON are turned OFF and those that are OFF are turned ON.
	-Button 2: Changes the state of all the odd numbered lamps.
	-Button 3: Changes the state of all the even numbered lamps.
	-Button 4: Changes the state of the lamps whose number is of the form 3xK+1 (with K>=0), i.e., 1,4,7,...
	
A counter C records the total number of button presses. 
When the party starts, all the lamps are ON and the counter C is set to zero. 
You are given the value of counter C (0 <= C <= 10000) and the final state of some of the lamps after some operations have been executed. Write a
program to determine all the possible final configurations of the N lamps that are consistent with the given information, without repetitions.
 
PROGRAM NAME: lamps

INPUT FORMAT

No lamp will be listed twice in the input. 

Line 1: 	N 
Line 2: 	Final value of C 
Line 3: 	Some lamp numbers ON in the final configuration, separated by one space and terminated by the integer -1. 
Line 4: 	Some lamp numbers OFF in the final configuration, separated by one space and terminated by the integer -1. 

SAMPLE INPUT (file lamps.in) 

10
1
-1
7 -1

In this case, there are 10 lamps and only one button has been pressed. Lamp 7 is OFF in the final configuration. 

OUTPUT FORMAT

Lines with all the possible final configurations (without repetitions) of all the lamps. Each line has N characters,
where the first character represents the state of lamp 1 and the last character represents the state of lamp N. A 0
(zero) stands for a lamp that is OFF, and a 1 (one) stands for a lamp that is ON. The lines must be ordered from least
to largest (as binary numbers). 
If there are no possible configurations, output a single line with the single word `IMPOSSIBLE' 

SAMPLE OUTPUT (file lamps.out)

0000000000
0101010101
0110110110

In this case, there are three possible final configurations: 
	-All lamps are OFF
	-Lamps 1, 4, 7, 10 are OFF and lamps 2, 3, 5, 6, 8, 9 are ON.
	-Lamps 1, 3, 5, 7, 9 are OFF and lamps 2, 4, 6, 8, 10 are ON.


 */

import java.io.*;
import java.util.*;

public class lamps {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("lamps.in"));
		boolean[] lamps = new boolean[Integer.parseInt(read.readLine())];
		for(int i = 0; i < lamps.length; i++){
			lamps[i] = true;
		}
		int[] specs = new int[lamps.length];
		int num = Integer.parseInt(read.readLine());
		StringTokenizer st = new StringTokenizer(read.readLine());
		while(true){
			int a = Integer.parseInt(st.nextToken());
			if(a == -1)
				break;
			specs[a - 1] = 1;
		}
		st = new StringTokenizer(read.readLine());
		while(true){
			int a = Integer.parseInt(st.nextToken());
			if(a == -1)
				break;
			specs[a - 1] = -1;
		}
		read.close();
		
		TreeSet<String> ans = new TreeSet<String>();
		
		if(num % 2 == 0 || num >= 8){
			if(works(lamps, specs))
				ans.add(string(lamps));
			if(num >= 2){
				for(int i = 0; i < lamps.length; i++){
					button(i, lamps);
					for(int j = i + 1; j < lamps.length; j++){
						button(j, lamps);
						if(works(lamps, specs))
							ans.add(string(lamps));
						button(j, lamps);
					}
					button(i, lamps);
				}
			}
		}
		if(num % 2 == 1 || num >= 8){
			for(int i = 0; i < lamps.length; i++){
				button(i, lamps);
				if(works(lamps, specs))
					ans.add(string(lamps));
				button(i, lamps);
				if(i >= 3){
					for(int j = 0; j < 4; j++){
						if(j == i)
							continue;
						button(j, lamps);
					}
					if(works(lamps, specs))
						ans.add(string(lamps));
					for(int j = 0; j < 4; j++){
						if(j == i)
							continue;
						button(j, lamps);
					}
				}
			}
		}
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lamps.out")));
		for(String s : ans){
			System.out.println(s);
			out.println(s);
		}
		if(ans.isEmpty()){
			System.out.println("IMPOSSIBLE");
			out.println("IMPOSSIBLE");
		}
		out.close();
		System.exit(0);
	}
	
	private static void button(int a, boolean[] lamps){
		if(a < 0 || a > 3)
			return;
		if(a == 0){
			for(int i = 0; i < lamps.length; i++){
				lamps[i] = !lamps[i];
			}
		} else if(a == 1){
			for(int i = 0; i < lamps.length; i += 2){
				lamps[i] = !lamps[i];
			}
		} else if(a == 2){
			for(int i = 1; i < lamps.length; i += 2){
				lamps[i] = !lamps[i];
			}
		} else {
			for(int i = 0; i < lamps.length; i += 3){
				lamps[i] = !lamps[i];
			}
		}
	}
	
	private static boolean works(boolean[] lamps, int[] specs){
		for(int i = 0; i < specs.length; i++){
			if(specs[i] == 0)
				continue;
			if(lamps[i] ^ specs[i] > 0)
				return false;
		}
		return true;
	}
	
	private static String string(boolean[] lamps){
		String s= "";
		for(boolean b : lamps){
			if(b) s += "1";
			else s += "0";
		}
		return s;
	}
	
}
