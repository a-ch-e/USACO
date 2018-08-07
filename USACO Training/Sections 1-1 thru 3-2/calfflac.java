/*
ID: ayc22031
LANG: JAVA
PROG: calfflac
 */

/*
 * Calf Flac
 It is said that if you give an infinite number of cows an infinite number of heavy-duty laptops (with very large keys), that they will ultimately produce all the world's great palindromes. Your job will be to detect these bovine beauties.

 Ignore punctuation, whitespace, numbers, and case when testing for palindromes, but keep these extra characters around so that you can print them out as the answer; just consider the letters `A-Z' and `a-z'.

 Find the largest palindrome in a string no more than 20,000 characters long. The largest palindrome is guaranteed to be at most 2,000 characters long before whitespace and punctuation are removed.

 PROGRAM NAME: calfflac

 INPUT FORMAT

 A file with no more than 20,000 characters. The file has one or more lines which, when taken together, represent one long string. No line is longer than 80 characters (not counting the newline at the end).
 SAMPLE INPUT (file calfflac.in)

 Confucius say: Madam, I'm Adam.
 OUTPUT FORMAT

 The first line of the output should be the length of the longest palindrome found. The next line or lines should be the actual text of the palindrome (without any surrounding white space or punctuation but with all other characters) printed on a line (or more than one line if newlines are included in the palindromic text). If there are multiple palindromes of longest length, output the one that appears first.

 SAMPLE OUTPUT (file calfflac.out)

 11
 Madam, I'm Adam

 */

import java.io.*;

public class calfflac {

	private static int start;
	private static int end;
	private static int length;
	private static String test;

	public static void main(String[] args) throws IOException {

		BufferedReader read = new BufferedReader(new FileReader("calfflac.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"calfflac.out")));
		StringBuilder build = new StringBuilder();
		while (true) {
			int c = read.read();
			if (c == -1)
				break;
			build.append((char)c);
		}
		test = build.toString().toLowerCase();
		StringBuilder fixed = new StringBuilder();
		for(int i = 0; i < test.length(); i++){
			if(isLetter(test.charAt(i))) fixed.append(test.charAt(i));
		}
		test = fixed.toString();
		oddPalindromes();
		// System.out.println("-------------------------------------------------------------");
		evenPalindromes();
		int newStart = 0;
		int newEnd = 0;
		String orig = build.toString();
		
		for(int i = 0; i < orig.length(); i++){
			if(!isLetter(orig.charAt(i))) continue;
			start--;
			end--;
			if(start == -1) newStart = i;
			if(end == -1) newEnd = i;
		}
		System.out.println(newStart);
		System.out.println(newEnd);
		out.println(length);
		out.println(orig.substring(newStart, newEnd + 1));
		out.close();
		System.exit(0);

	}

	private static void oddPalindromes() {

		for (int i = 0; i < test.length(); i++) {
			int a = i;
			int b = i;
			int len = 1;
			
//			if (LETTERS.indexOf(test.charAt(i)) < 0)
//				continue;
//
			while (test.charAt(a) == test.charAt(b)) {
//				if (length < len) {
//					start = a;
//					end = b;
//					length = len;
//				}
//				int temp = a - 1;
//				if (temp < 0)
//					break;
//				while (LETTERS.indexOf(test.charAt(temp)) < 0) {
//					temp--;
//					if (temp < 0)
//						break;
//				}
//				if (temp < 0)
//					break;
//
//				int temp2 = b + 1;
//				if (temp2 >= test.length())
//					break;
//				while (LETTERS.indexOf(test.charAt(temp2)) < 0) {
//					temp2++;
//					if (temp2 >= test.length())
//						break;
//				}
//				if (temp2 >= test.length())
//					break;
//				// System.out.println(test.charAt(a) + " " + test.charAt(b));
//				// System.out.println(test.charAt(temp) + " " +
//				// test.charAt(temp2));
//				// System.out.println(test.charAt(start) + " " +
//				// test.charAt(end));
//				// System.out.println(start + " " + end);
//				// System.out.println();
//				if (test.charAt(temp) != test.charAt(temp2)) {
//					break;
//				}
//				len += 2;
//				a = temp;
//				b = temp2;
				if(a <= 0) break;
				if(b >= test.length() - 1) break;
				a--;
				b++;
				if(test.charAt(a) != test.charAt(b)){
					a++;
					b--;
					break;
				}
//				System.out.println(test.charAt(a) + " " +  test.charAt(b));
				len += 2;
			}

			if (length < len) {
				start = a;
				end = b;
				length = len;
			}

		}

	}

	private static void evenPalindromes() {

		for (int i = 0; i < test.length() - 1; i++) {
			int a = i;
			int b = i + 1;
			int len = 2;
			while(test.charAt(a) == test.charAt(b)){
				if(a <= 0) break;
				if(b >= test.length() - 1) break;
				a--;
				b++;
				if(test.charAt(a) != test.charAt(b)){
					a++;
					b--;
					break;
				}
				len += 2;
			}
//			if (LETTERS.indexOf(test.charAt(i)) < 0
//					|| LETTERS.indexOf(test.charAt(i + 1)) < 0) {
//				continue;
//			}
//
//			while (true) {
//				if (length < len) {
//					start = a;
//					end = b;
//					length = len;
//				}
//				int temp = a - 1;
//				if (temp < 0)
//					break;
//				while (LETTERS.indexOf(test.charAt(temp)) < 0) {
//					temp--;
//					if (temp < 0)
//						break;
//				}
//				if (temp < 0)
//					break;
//
//				int temp2 = b + 1;
//				if (temp2 >= test.length())
//					break;
//				while (LETTERS.indexOf(test.charAt(temp2)) < 0) {
//					temp2++;
//					if (temp2 >= test.length())
//						break;
//				}
//				if (temp2 >= test.length())
//					break;
//				// System.out.println(test.charAt(a) + " " + test.charAt(b));
//				// System.out.println(test.charAt(temp) + " " +
//				// test.charAt(temp2));
//				// System.out.println(test.charAt(start) + " " +
//				// test.charAt(end));
//				// System.out.println(start + " " + end);
//				// System.out.println(length);
//				// System.out.println(len);
//				// System.out.println();
//				if (test.charAt(temp) != test.charAt(temp2)) {
//					break;
//				}
//				len += 2;
//				a = temp;
//				b = temp2;
//			}

			if (length < len) {
				start = a;
				end = b;
				length = len;
			}

		}

	}
	
	private static boolean isLetter(char c){
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
	}

}
