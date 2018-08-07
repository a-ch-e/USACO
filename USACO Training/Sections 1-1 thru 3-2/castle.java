/*
ID: ayc22032
LANG: JAVA
TASK: castle
 */

/*
The Castle
IOI'94 - Day 1
 
In a stroke of luck almost beyond imagination, Farmer John was sent a ticket to the Irish Sweepstakes (really a lottery) for his birthday. This ticket turned out to have only the winning number for the lottery! Farmer John won a fabulous castle in the Irish countryside. 
Bragging rights being what they are in Wisconsin, Farmer John wished to tell his cows all about the castle. He wanted to know how many rooms it has and how big the largest room was. In fact, he wants to take out a single wall to make an even bigger room. 
Your task is to help Farmer John know the exact room count and sizes. 

The castle floorplan is divided into M (wide) by N (1 <=M,N<=50) square modules. Each such module can have between zero and four walls. Castles always have walls on their "outer edges" to keep out the wind and rain. 
Consider this annotated floorplan of a castle: 

     1   2   3   4   5   6   7
   #############################
 1 #   |   #   |   #   |   |   #
   #####---#####---#---#####---#   
 2 #   #   |   #   #   #   #   #
   #---#####---#####---#####---#
 3 #   |   |   #   #   #   #   #   
   #---#########---#####---#---#
 4 # ->#   |   |   |   |   #   #   
   ############################# 

#  = Wall     -,|  = No wall
-> = Points to the wall to remove to
     make the largest possible new room
     
By way of example, this castle sits on a 7 x 4 base. A "room" includes any set of connected "squares" in the floor plan. This floorplan contains five rooms (whose sizes are 9, 7, 3, 1, and 8 in no particular order). 
Removing the wall marked by the arrow merges a pair of rooms to make the largest possible room that can be made by removing a single wall. 
The castle always has at least two rooms and always has a wall that can be removed. 

PROGRAM NAME: castle

INPUT FORMAT

The map is stored in the form of numbers, one number for each module, M numbers on each of N lines to describe the floorplan. The input order corresponds to the numbering in the example diagram above. 
Each module number tells how many of the four walls exist and is the sum of up to four integers: 
 	-1: wall to the west 
	-2: wall to the north 
	-4: wall to the east 
	-8: wall to the south 

Inner walls are defined twice; a wall to the south in module 1,1 is also indicated as a wall to the north in module 2,1. 

Line 1: 	Two space-separated integers: M and N
Line 2..: 	M x N integers, several per line. 

SAMPLE INPUT (file castle.in) 

7 4
11 6 11 6 3 10 6
7 9 6 13 5 15 5
1 10 12 7 13 7 5
13 11 10 8 10 12 13

OUTPUT FORMAT

The output contains several lines: 
Line 1: 	The number of rooms the castle has. 
Line 2: 	The size of the largest room
Line 3: 	The size of the largest room creatable by removing one wall 
Line 4: 	The single wall to remove to make the largest room possible
Choose the optimal wall to remove from the set of optimal walls by choosing the module farthest to the west (and then, if still tied, farthest to the south). If still tied, choose 'N' before 'E'. Name that wall by naming the module that borders it on either the west or south, along with a direction of N or E giving the location of the wall with respect to the module. 

SAMPLE OUTPUT (file castle.out)

5
9
16
4 1 E

 */

import java.io.*;
import java.util.*;

public class castle {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("castle.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int a = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		CastleModule[][] castle = new CastleModule[b][a];
		for(int i = 0; i < castle.length; i++){
			st = new StringTokenizer(read.readLine());
			for(int j = 0; j < castle[i].length; j++){
				castle[i][j] = new CastleModule(st.nextToken());
			}
		}
		read.close();
		
		int comp = 0;
		for(int i = 0; i < castle.length; i++){
			for(int j = 0; j < castle[0].length; j++){
				if(castle[i][j].component < 0){
					floodFill(castle, i, j, comp);
					comp++;
				}
			}
		}
		int[] rooms = new int[comp];
		for(int i = 0; i < castle.length; i++){
			for(int j = 0; j < castle[0].length; j++){
				rooms[castle[i][j].component]++;
			}
		}
//		for(int i : rooms){
//			System.out.print(i + " ");
//		}
//		System.out.println();
		int max = rooms[0];
		for(int i = 1; i < rooms.length; i++)
			max = Math.max(rooms[i], max);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("castle.out")));
		out.println(comp);
		System.out.println(comp);
		out.println(max);
		System.out.println(max);
		
		boolean north = false;
		max = 0;
		int rightX = 0;
		int rightY = 0;
		for(int i = 0; i < castle[0].length; i++){
			for(int j = castle.length - 1; j >= 0; j--){
//				if(j == 2 && i == 0)
//					continue;
//				if(j == 5 && i == 9)
//					System.out.println("!");
				if(castle[j][i].walls[0] && j > 0){
//					for(int x = 0; x < castle.length; x++){
//						for(int y = 0; y < castle[0].length; y++){
//							castle[x][y].component = -1;
//						}
//					}
//					castle[j][i].walls[0] = false;
//					comp = 0;
//					for(int x = 0; x < castle.length; x++){
//						for(int y = 0; y < castle[0].length; y++){
//							if(castle[x][y].component < 0){
//								floodFill(castle, x, y, comp);
//								comp++;
//							}
//						}
//					}
//					rooms = new int[comp];
//					for(int x = 0; x < castle.length; x++){
//						for(int y = 0; y < castle[0].length; y++){
//							rooms[castle[x][y].component]++;
//						}
//					}
//					int temp = rooms[0];
//					for(int k = 1; k < rooms.length; k++){
//						temp = Math.max(temp, rooms[k]);
//					}
					int temp = 0;
					if(castle[j][i].component != castle[j - 1][i].component)
						temp = rooms[castle[j][i].component] + rooms[castle[j - 1][i].component];
//					if(j == 5 && i == 9)
//						System.out.println(temp);
					if(temp > max){
//						System.out.println(max + " " + temp + " " + comp);
						max = temp;
						north = true;
						rightX = j + 1;
						rightY = i + 1;
//						System.out.println(rightX + " " + rightY);
//						System.out.println();
					}
//					castle[j][i].walls[0] = true;
				}
				if(castle[j][i].walls[1] && i < castle[0].length - 1){
//					for(int x = 0; x < castle.length; x++){
//						for(int y = 0; y < castle[0].length; y++){
//							castle[x][y].component = -1;
//						}
//					}
//					castle[j][i].walls[1] = false;
//					comp = 0;
//					for(int x = 0; x < castle.length; x++){
//						for(int y = 0; y < castle[0].length; y++){
//							if(castle[x][y].component < 0){
//								floodFill(castle, x, y, comp);
//								comp++;
//							}
//						}
//					} 
//					rooms = new int[comp];
//					for(int x = 0; x < castle.length; x++){
//						for(int y = 0; y < castle[0].length; y++){
//							rooms[castle[x][y].component]++;
//						}
//					}
//					int temp = rooms[0];
//					for(int k = 1; k < rooms.length; k++){
//						temp = Math.max(temp, rooms[k]);
//					}
					int temp = 0;
					if(castle[j][i].component != castle[j][i + 1].component)
						temp = rooms[castle[j][i].component] + rooms[castle[j ][i + 1].component];
					if(temp > max){
//						System.out.println(max + " " + temp);
						max = temp;
						north = false;
						rightX = j + 1;
						rightY = i + 1;
//						System.out.println(rightX + " " + rightY);
//						System.out.println();
					}
//					castle[j][i].walls[1] = true;
				}
			}
		}
		out.println(max);
		System.out.println(max);
		out.print(rightX + " " + rightY + " ");
		System.out.print(rightX + " " + rightY + " ");
		if(north){
			out.println("N");
			System.out.println("N");
		} else {
			out.println("E");
			System.out.println("E");
		}
		out.close();
		System.exit(0);
		
	}
	
	private static void floodFill(CastleModule[][] castle, int x, int y, int c){
		
		castle[x][y].component = c;
		if(x > 0 && castle[x - 1][y].component < 0 && !castle[x][y].walls[0]){
			floodFill(castle, x - 1, y, c);
		}
		if(x < castle.length - 1 && castle[x + 1][y].component < 0 && !castle[x][y].walls[2]){
			floodFill(castle, x + 1, y, c);
		}
		if(y > 0 && castle[x][y - 1].component < 0 && !castle[x][y].walls[3]){
			floodFill(castle, x, y - 1, c);
		}
		if(y < castle[0].length - 1 && castle[x][y + 1].component < 0 && !castle[x][y].walls[1]){
			floodFill(castle, x, y + 1, c);
		}
		
	}
	
}

class CastleModule{
	
	boolean[] walls;
	int component;
	
	public CastleModule(String s){
		
		component = -1;
		int code = Integer.parseInt(s);
		walls = new boolean[4];
		if(code >= 8){
			code -= 8;
			walls[2] = true;
		}
		if(code >= 4){
			code -= 4;
			walls[1] = true;
		}
		if(code >= 2){
			code -= 2;
			walls[0] = true;
		}
		if(code >= 1){
			walls[3] = true;
		}
		
	}
	
}
