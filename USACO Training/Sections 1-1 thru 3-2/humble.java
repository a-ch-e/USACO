/*
ID: ayc22032
LANG: JAVA
TASK: humble
 */

/*

 Humble Numbers
 For a given set of K prime numbers S = {p1, p2, ..., pK}, consider the set of all numbers whose
 prime factors are a subset of S. This set contains, for example, p1, p1p2, p1p1, and p1p2p3 (among
 others). This is the set of `humble numbers' for the input set S. Note: The number 1 is explicitly
 declared not to be a humble number.

 Your job is to find the Nth humble number for a given set S. Long integers (signed 32-bit) will be
 adequate for all solutions.

 PROGRAM NAME: humble

 INPUT FORMAT

 Line 1:	 Two space separated integers: K and N, 1 <= K <=100 and 1 <= N <= 100,000.
 Line 2:	 K space separated positive integers that comprise the set S.
 SAMPLE INPUT (file humble.in)

 4 19
 2 3 5 7
 OUTPUT FORMAT

 The Nth humble number from set S printed alone on a line.
 SAMPLE OUTPUT (file humble.out)

 27

 */

import java.io.*;
import java.util.*;

public class humble {

	public static void main(String[] args) throws IOException {

		long a = System.currentTimeMillis();
		BufferedReader read = new BufferedReader(new FileReader("humble.in"));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int primes = Integer.parseInt(st.nextToken());
		final int n = Integer.parseInt(st.nextToken());
//		HumbleAL humbles = new HumbleAL();
		TreeSet<Long> humbles = new TreeSet<Long>();
		long[] primeSet = new long[primes];
		st = new StringTokenizer(read.readLine());
		for (int i = 0; i < primes; i++) {
			long l = Long.parseLong(st.nextToken());
			humbles.add(l);
			primeSet[i] = l;
		}
		read.close();
		Arrays.sort(primeSet);
		System.out.println(System.currentTimeMillis() - a);
		
		
		int used = 0;
		long mult = 1;
		long track = Long.MAX_VALUE;
		while((humbles.size() + used <= n || primeSet[0] * mult <= track)){
			mult = humbles.pollFirst();
			used++;
			for(int i = 0; i < primes; i++){
				long temp = mult * primeSet[i];
				if(temp > track)
					break;
				humbles.add(temp);
				if(humbles.size() + used > n)
					humbles.pollLast();
				if(humbles.size() + used >= n)
					track = humbles.last();
				
			}
			if(humbles.isEmpty()){
				humbles.add(mult);
				break;
			}
//			System.out.println(humbles);
		}
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"humble.out")));
//		System.out.println(humbles);
		out.println(humbles.last());
		System.out.println(humbles.last());
		out.close();
		System.exit(0);
		
//		initial arraylist version
/*		int used = 0;
		long mult = 1;
		long track = Long.MAX_VALUE;
		while ((humbles.size() <= n || primeSet[0] * mult <= track)
				&& used < humbles.size()) {
			mult = humbles.get(used);
			// System.out.println(used + " " + humbles.size());
			used++;
			for (int i = 0; i < primes; i++) {
				long temp = mult * primeSet[i];
				// int key = Collections.binarySearch(humbles, temp);
				// System.out.println(key + " " + temp[i]);
				if (temp > track)
					break;
				int key = humbles.add(temp);
				if (key < n) {
					if (humbles.size() > n)
						track = humbles.get(n - 1);
				}
			}
			// System.out.println(used);
			// if(track < Long.MAX_VALUE)
			// System.out.println(humbles);
		}
		// System.out.println(System.currentTimeMillis() - a);
		// System.out.println(track);
		// System.out.println(used);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"humble.out")));
		out.println(humbles.get(n - 1));
		System.out.println(humbles.get(n - 1));
		out.close();
		System.exit(0);*/

	}

}

class HumbleAL {
	private int size; // stores the number of objects
	private long[] myArray;

	public HumbleAL() // default constructor makes 10 cells
	{
		myArray = new long[10];
		size = 0;
	}

	public HumbleAL(int len) {
		myArray = new long[len];
		size = 0;
	}

	public int size() {
		return size;
	}

	public int add(long obj) {
		int index = Arrays.binarySearch(myArray, 0, size, obj);
		if (index >= 0)
			return index;
		index = -1 * (index + 1);
		if (size == myArray.length) {
			long[] temp = new long[2 * size];
			System.arraycopy(myArray, 0, temp, 0, index);
			System.arraycopy(myArray, index, temp, index + 1, size - index);
			temp[index] = obj;
			myArray = temp;
		} else {
			System.arraycopy(myArray, index, myArray, index + 1, size - index);
			myArray[index] = obj;
		}

		size++;
		return index;
	}

	/*
	 * return obj at position index.
	 */
	public long get(int index) {
		return myArray[index];
	}

	/*
	 * replaces obj at position index.
	 */
	/*
	 * returns the Objects in the array, nicely formatted, e.g. [Apple, Banana,
	 * Cucumber, Date]
	 */
	public String toString() {
		String s = "[";
		s += myArray[0];
		for (int i = 1; i < size; i++) {
			s += ", ";
			s += myArray[i];
		}
		s += "]";
		return s;
	}
}
