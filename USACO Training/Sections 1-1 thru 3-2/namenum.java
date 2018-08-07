/*
ID: ayc22031
LANG: JAVA
PROG: namenum 
 */

import java.io.*;
import java.util.ArrayList;

/*
 * Among the large Wisconsin cattle ranchers, it is customary to brand cows with serial numbers to please the Accounting Department.
 * The cow hands don't appreciate the advantage of this filing system, though, and wish to call the members of their herd by a pleasing name rather than saying, "C'mon, #4734, get along."

Help the poor cowhands out by writing a program that will translate the brand serial number of a cow into possible names uniquely associated with that serial number.
Since the cow hands all have cellular saddle phones these days, use the standard Touch-Tone(R) telephone keypad mapping to get from numbers to letters (except for "Q" and "Z"):

          2: A,B,C     5: J,K,L    8: T,U,V
          3: D,E,F     6: M,N,O    9: W,X,Y
          4: G,H,I     7: P,R,S
Acceptable names for cattle are provided to you in a file named "dict.txt", which contains a list of fewer than 5,000 acceptable cattle names (all letters capitalized). Take a cow's brand number and report which of all the possible words to which that number maps are in the given dictionary which is supplied as dict.txt in the grading environment (and is sorted into ascending order).

For instance, the brand number 4734 produces all the following names:

GPDG GPDH GPDI GPEG GPEH GPEI GPFG GPFH GPFI GRDG GRDH GRDI
GREG GREH GREI GRFG GRFH GRFI GSDG GSDH GSDI GSEG GSEH GSEI
GSFG GSFH GSFI HPDG HPDH HPDI HPEG HPEH HPEI HPFG HPFH HPFI
HRDG HRDH HRDI HREG HREH HREI HRFG HRFH HRFI HSDG HSDH HSDI
HSEG HSEH HSEI HSFG HSFH HSFI IPDG IPDH IPDI IPEG IPEH IPEI
IPFG IPFH IPFI IRDG IRDH IRDI IREG IREH IREI IRFG IRFH IRFI
ISDG ISDH ISDI ISEG ISEH ISEI ISFG ISFH ISFI
As it happens, the only one of these 81 names that is in the list of valid names is "GREG".

Write a program that is given the brand number of a cow and prints all the valid names that can be generated from that brand number or ``NONE'' if there are no valid names. Serial numbers can be as many as a dozen digits long.

PROGRAM NAME: namenum

INPUT FORMAT

A single line with a number from 1 through 12 digits in length.
SAMPLE INPUT (file namenum.in)

4734
OUTPUT FORMAT

A list of valid names that can be generated from the input, one per line, in ascending alphabetical order.
SAMPLE OUTPUT (file namenum.out)

GREG

 */

public class namenum {
	
	private static Dictionary DICT;
	private static ArrayList<String> names;
	
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("namenum.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("namenum.out")));
		long num = Long.parseLong(read.readLine());
		DICT = new Dictionary("dict.txt");
		DICT.searchPrint(num, out);
		out.close();
		System.exit(0);
		
	}
	
//	private static void genPoss(String num, ArrayList<String> current){
//		
//		if(num.length() > 0){
//			int x = current.size();
//			for(int i = 0; i < x; i++){
//				String s = current.remove(0);
//				int c = Integer.parseInt(num.substring(0, 1)) - 2;
//				current.add(s + LETTERS[c][0]);
////				System.out.println(s + LETTERS[c][0]);
//				current.add(s + LETTERS[c][1]);
////				System.out.println(s + LETTERS[c][1]);
//				current.add(s + LETTERS[c][2]);
////				System.out.println(s + LETTERS[c][2]);
//			}
//			genPoss(num.substring(1), current);
//		} else {
//			names = current;
//		}
//		
//	}

	
}

class Dictionary{
	
	private ArrayList<Long> words;
	private ArrayList<String> names;
	
	public Dictionary(String file) throws Exception{
		BufferedReader read = new BufferedReader(new FileReader(file));
		words = new ArrayList<Long>();
		names = new ArrayList<String>();
		while(true){
			 String word = read.readLine();
			 if(word == null) break;
			 names.add(word);
			 long name = 0;
			 while(word.length() > 0){
				 char first = word.charAt(0);
				 name = name* 10 + (first - 'A') / 3 + 2;
				 if(first == 'S' || first == 'V' || first == 'Y')
					 name--;
				 word = word.substring(1);
			 }
			 words.add(name);
//			 System.out.println(word);
		}
		
	}
	
	public void searchPrint(long num, PrintWriter out) throws Exception{
		boolean found = false;
		for(int i = 0; i < words.size(); i++){
			if(names.get(i).equals("KRISTOPHER")){
				System.out.println(":/");
				System.out.println(words.get(i));
			}
			if(num == words.get(i)){
				out.println(names.get(i));
				found = true;
			}
		}
		if(!found)
			out.println("NONE");
	}
//	
//	public boolean isWord(String test){
////		return helper(test, words.size() - 1, 0);
//	}
//	
////	private boolean helper(String key, int high, int low){
//		
//		int mid = (high + low) / 2;
////		System.out.println("Low: " + low);
////		System.out.println("High: " + high);
////		int comp = words.get(mid).compareTo(key);
////		if(comp == 0) return true;
//		if(mid == low){
//			if(words.get(high).equals(key)) return true;
//			return false;
//		}
////		System.out.println(words.get(mid));
////		if(comp < 0) return helper(key, high, mid);
////		else return helper(key, mid, low);
//		
//	}
//	
}
