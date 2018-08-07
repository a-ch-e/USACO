/*
ID: ayc22031
LANG: JAVA
PROG: crypt1
 */

/*
 * Prime Cryptarithm
The following cryptarithm is a multiplication problem that can be solved by substituting digits from a specified set of N digits into the positions marked with *. If the set of prime digits {2,3,5,7} is selected, the cryptarithm is called a PRIME CRYPTARITHM.

      * * *
   x    * *
    -------
      * * *         <-- partial product 1
    * * *           <-- partial product 2
    -------
    * * * *
Digits can appear only in places marked by `*'. Of course, leading zeroes are not allowed.
Note that the 'partial products' are as taught in USA schools. The first partial product is the product of the final digit of the second number and the top number. The second partial product is the product of the first digit of the second number and the top number.

Write a program that will find all solutions to the cryptarithm above for any subset of digits from the set {1,2,3,4,5,6,7,8,9}.

PROGRAM NAME: crypt1

INPUT FORMAT

Line 1:	 N, the number of digits that will be used
Line 2:	N space separated digits with which to solve the cryptarithm
SAMPLE INPUT (file crypt1.in)

5
2 3 4 6 8
OUTPUT FORMAT

A single line with the total number of unique solutions. Here is the single solution for the sample input:

      2 2 2
    x   2 2
     ------
      4 4 4
    4 4 4
  ---------
    4 8 8 4
SAMPLE OUTPUT (file crypt1.out)

1
 */

import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;
import java.util.ArrayList;;

public class crypt1 {
	
	private static int[] digits;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("crypt1.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("crypt1.out")));
		int num = Integer.parseInt(read.readLine());
		digits = new int[num];
		StringTokenizer st = new StringTokenizer(read.readLine());
		for(int i = 0; i < num; i++){
			digits[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(digits);
		ArrayList<digSet> list = new ArrayList<digSet>();
		for(int i : digits){
			for(int j : digits){
				for(int k : digits){
					for(int l : digits){
						int check = 100*i + 10*j + k;
						if(contains(check * l) && check * l < 1000){
							if(list.isEmpty())
								list.add(new digSet(check, 1));
							else if(list.get(list.size() - 1).num == check)
								list.get(list.size() - 1).addDig(l);
							else
								list.add(new digSet(check, l));
						}
					}
				}
			}
		}
		int count = 0;
		System.out.println(list);
		for(digSet d : list){
			for(int i : d.digset){
				for(int j : d.digset){
					int check = d.num * (10 * i + j);
					if(check < 10000 && contains(check)){
						count++;
						System.out.println(d.num + " * " + (10*i + j));
					}
				}
			}
		}
		out.println(count);
		out.close();
		System.exit(0);
		
	}
	
	private static boolean contains(int num){
		while(num > 0){
			if(Arrays.binarySearch(digits, num % 10) < 0) return false;
			num /= 10;
		}
		return true;
	}
	
}

class digSet{
	
	int num;
	ArrayList<Integer> digset;
	
	public digSet(int n, int first){
		num = n;
		digset = new ArrayList<Integer>();
		digset.add(first);
	}
	
	public void addDig(int dig){
		digset.add(dig);
	}
	
	public String toString(){
		String s = "Number: ";
		s += num;
		s += ", digits ";
		s += digset;
		return s;
	}
	
}
