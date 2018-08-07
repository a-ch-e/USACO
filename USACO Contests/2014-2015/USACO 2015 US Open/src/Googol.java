/*
Allen Cheng
2015 US Open, Gold Division
Problem 1: Googol
 */

/*
Tired of filtering through web search results targeted at other farm animals, the cows have decided
to launch their own search engine. Unfortunately, with their severe lack of experience managing
large software projects, the number of employees N (1<=N<=10^100) in their company ended up being
substantially larger than originally planned. In order to find a fitting name for their company,
the cows drew inspiration from the upper bound on N and decided to call it "Googol", being the name
for the number 10^100.

In a desperate attempt to improve the managerial structure of their company, the cows have
structured the company in the form of a binary tree, where each employee is responsible for
managing two direct subordinates, designated as the "left" and "right" subordinates in keeping
with the tree structure. In order to balance the managerial load on each employee, the cows have
arranged the structure of their company tree so that for each employee E, the total number of
employees in E's left subtree is either equal to or one greater than the total number of employees
in E's right subtree.

Each employee has a distinct integer-valued ID in the range 1...N, with the CEO (the root of the
tree) having ID 1. You can interactively query any employee to determine the IDs of her two
subordinates. This is done by writing the ID of the employee on the standard output stream (stdout)
followed by a newline. The response you will get back will be a single line containing two
integers, specifying the IDs of the left and right subordinates of this employee. It is possible
for both of these to be 0, if the employee has no subordinates, or for just the right ID to be 0,
if the employee only has a left subordinate (note that it is impossible, due to the balancing
condition above, for an employee to have a right subordinate but no left subordinate).

Unfortunately, the cows have lost track of the exact value of N. Please compute this number and
print out "Answer N" (followed by a newline) as your last line of output. Your program will be
limited to making at most 70,000 queries, and a running time limit of 4 seconds (8 for Java or
Python).

Here is an example of a possible interaction between your program and the grader:

YOUR PROGRAM: 1
GRADER: 4 3
YOUR PROGRAM: 4
GRADER: 2 0
YOUR PROGRAM: 3
GRADER: 0 0
YOUR PROGRAM: Answer 4
The tree corresponding to this interaction is

     1
   4   3
 2
Note that it was not necessary to ask about the children of node 2, since we can deduce that there
are no such children from the fact that node 4 has no right child.

Technical note: this problem is being graded by an interactive grader that is a new part of our
grading system. If you experience any behavior that appears to be a problem on the technical side
with the grader, please report it to bcdean@clemson.edu. We do not believe it should be necessary,
but if your code seems to be frozen during grading, try adding commands to flush the output stream
(e.g., fflush(stdout) or cout.flush()), in case there is unexpected buffering preventing your
output from reaching the grader (if this does seems necessary to make your code work, please also
send a note to bcdean@clemson.edu so we can fix the issue in the future).

[Problem credits: Brian Dean, 2015]
 */

import java.math.BigInteger;
import java.io.*;
import java.util.*;

public class Googol {
	
	private static BufferedReader read;
	private static final BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);
	
	public static void main(String[] args) throws IOException{
		
		read = new BufferedReader(new InputStreamReader(System.in));
		BigInteger ans = goLeft(BigInteger.ONE, null);
		System.out.println("Answer " + ans);
		System.exit(0);
		
	}
	
	private static BigInteger goLeft(BigInteger root, BigInteger rSize) throws IOException{
		
		System.out.println(root);
		StringTokenizer st = new StringTokenizer(read.readLine());
//		System.out.println(rSize);
//		if(rSize != null && rSize.equals(TWO))
//			System.out.println("!");
//		System.out.println();
		BigInteger lChild = new BigInteger(st.nextToken());
		BigInteger rChild = new BigInteger(st.nextToken());
		if(lChild.equals(BigInteger.ZERO))
			return BigInteger.ONE;
		if(rChild.equals(BigInteger.ZERO))
			return TWO;
		if(rSize == null){
			BigInteger lSize = goLeft(lChild, null);
			if(lSize.equals(BigInteger.ONE))
				return TWO.add(BigInteger.ONE);
			return lSize.add(goRight(rChild, lSize)).add(BigInteger.ONE);
		}
		if(rSize.equals(TWO))
			return TWO.add(BigInteger.ONE);
		BigInteger oSize = rSize.subtract(BigInteger.ONE).divide(TWO);
		if(rSize.mod(TWO).equals(BigInteger.ONE)){
			return oSize.add(goLeft(lChild, oSize)).add(BigInteger.ONE);
		}
		oSize = oSize.add(BigInteger.ONE);
		return oSize.add(goRight(rChild, oSize)).add(BigInteger.ONE);
		
		
	}
	
	private static BigInteger goRight(BigInteger root, BigInteger lSize) throws IOException{
		
		System.out.println(root);
		StringTokenizer st = new StringTokenizer(read.readLine());
//		System.out.println(lSize + "\n");
		BigInteger lChild = new BigInteger(st.nextToken());
		BigInteger rChild = new BigInteger(st.nextToken());
		if(lChild.equals(BigInteger.ZERO))
			return BigInteger.ONE;
		if(rChild.equals(BigInteger.ZERO))
			return TWO;
		if(lSize.equals(TWO.add(BigInteger.ONE)))
			return lSize;
		BigInteger oSize = lSize.subtract(BigInteger.ONE).divide(TWO);
		if(lSize.mod(TWO).equals(BigInteger.ZERO)){
			return oSize.add(goLeft(lChild, oSize)).add(BigInteger.ONE);
		}
		return oSize.add(goRight(rChild, oSize)).add(BigInteger.ONE);
		
		
	}
	
}
