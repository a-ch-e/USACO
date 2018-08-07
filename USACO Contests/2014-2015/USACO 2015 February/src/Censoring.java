/*
Allen Cheng
USACO Gold Division, February 2015
Problem 2 : Censoring
 */

/*
Farmer John has purchased a subscription to Good Hooveskeeping magazine for his cows, so they have
plenty of material to read while waiting around in the barn during milking sessions. Unfortunately,
the latest issue contains a rather inappropriate article on how to cook the perfect steak, which FJ
would rather his cows not see (clearly, the magazine is in need of better editorial oversight).

FJ has taken all of the text from the magazine to create the string S of length at most 10^5
characters. He has a list of censored words t_1 ... t_N that he wishes to delete from S. To do so
Farmer John finds the earliest occurrence of a censored word in S (having the earliest start index)
and removes that instance of the word from S. He then repeats the process again, deleting the earliest
occurrence of a censored word from S, repeating until there are no more occurrences of censored words
in S. Note that the deletion of one censored word might create a new occurrence of a censored word
that didn't exist before.

Farmer John notes that the censored words have the property that no censored word appears as a substring
of another censored word. In particular this means the censored word with earliest index in S is uniquely defined.

Please help FJ determine the final contents of S after censoring is complete.

INPUT FORMAT: (file censor.in)

The first line will contain S. The second line will contain N, the number of censored words. The next N lines
contain the strings t_1 ... t_N. Each string will contain lower-case alphabet characters (in the range a..z),
and the combined lengths of all these strings will be at most 10^5.

OUTPUT FORMAT: (file censor.out)

The string S after all deletions are complete. It is guaranteed that S will not become empty during the deletion process.

SAMPLE INPUT:

begintheescapexecutionatthebreakofdawn
2
escape
execution

SAMPLE OUTPUT:

beginthatthebreakofdawn
 */

import java.io.*;
import java.util.*;

public class Censoring {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("censor.in"));
		String str = read.readLine();
		int N = Integer.parseInt(read.readLine());
		Trie trie = new Trie();
		for(int i = 0; i < N; i++){
			String temp = read.readLine();
			trie.addWord(temp);
		}
		read.close();
		
		int maxCheck = 0;
		while(maxCheck < str.length()){
			int beg = -1;
			int end = -1;
			for(int i = maxCheck; i < str.length(); i++){
				TrieNode node = trie.root;
				for(int j = i; j < str.length(); j++){
					if(node.endWord){
						beg = i;
						end = j;
						break;
					} else if(j - i >= trie.LEN_CAP){
						int len = -1;
						for(int k = 0; k < node.longs.size() && len < 0; k++){
							if(node.longs.get(k).length() <= str.length() - j && node.longs.get(k).equals(str.substring(j, j + node.longs.get(k).length()))){
								len = node.longs.get(k).length();
								break;
							}
						}
						if(len < 0)
							break;
						beg = i;
						end = j;
						break;
					}
					if(!node.next.keySet().contains(str.charAt(j))){
						break;
					}
					node = node.next.get(str.charAt(j));
				}
				if(end != -1){
					break;
				}
			}
			if(end < 0){
				break;
			} else {
				str = str.substring(0, beg) + str.substring(end);
			}
			
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));
		out.println(str);
//		System.out.println(str);
		out.close();
		System.exit(0);
		
	}
	
}

class Trie{
	
	TrieNode root;
	public static final int LEN_CAP = 20;
	public Trie(){
		root = new TrieNode('*');
	}
	public void addWord(String s){
		if(s.length() > LEN_CAP)
			root.addLongWord(s, LEN_CAP);
		else
			root.addWord(s);
	}
	public boolean hasWord(String s){
		return root.hasWord(s);
	}
	
}

class TrieNode{
	
	char val;
	boolean endWord;
	HashMap<Character, TrieNode> next;
	ArrayList<String> longs;
	
	public TrieNode(char c){
		val = c;
		next = new HashMap<Character, TrieNode>();
		endWord = false;
		longs = new ArrayList<String>(5);
	}
	
	public void addWord(String s){
		if(s.length() == 0){
			endWord = true;
			return;
		}
		if(next.keySet().contains(s.charAt(0))){
			next.get(s.charAt(0)).addWord(s.substring(1));
		} else {
			TrieNode n = new TrieNode(s.charAt(0));
			n.addWord(s.substring(1));
			next.put(s.charAt(0), n);
		}
	}
	
	public void addLongWord(String s, int rem){
		
		if(rem == 0)
			longs.add(s);
		if(next.keySet().contains(s.charAt(0))){
			next.get(s.charAt(0)).addLongWord(s.substring(1), rem - 1);
		} else {
			TrieNode n = new TrieNode(s.charAt(0));
			n.addWord(s.substring(1));
			next.put(s.charAt(0), n);
		}
		
	}
	
	public boolean hasWord(String s){
		
		if(s.length() == 0){
			return endWord;
		}
		if(next.keySet().contains(s.charAt(0))){
			return next.get(s.charAt(0)).hasWord(s.substring(1));
		}
		return false;
		
	}
	
}