import java.io.*;
import java.util.StringTokenizer;

/*
ID: ayc22031
LANG: JAVA
PROG: gift1
 */


public class gift1 {
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("gift1.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gift1.out")));
		Giver[] ppl = new Giver[Integer.parseInt(read.readLine())];
		for(int i = 0; i < ppl.length; i++){
			ppl[i] = new Giver(read.readLine());
		}
		int len = ppl.length;
		for(int i = 0; i < len; i++){
			Giver temp = ppl[search(ppl, read.readLine())];
			StringTokenizer line = new StringTokenizer(read.readLine());
			int tot = Integer.parseInt(line.nextToken());
			int num = Integer.parseInt(line.nextToken());
			if(num != 0){
				temp.money -= tot - tot % num;
				tot /= num;
			}
			for(int j = 0; j < num; j++){
				ppl[search(ppl, read.readLine())].money += tot;
			}
		}
		for(int i = 0; i < len; i++){
			out.println(ppl[i].name + " " + ppl[i].money);
		}
		out.close();
		System.exit(0);
		
	}
	
	private static int search(Giver[] array, String comp){
		int len = array.length;
		for(int i = 0; i < len; i++){
			if(array[i].name.equals(comp)) return i;
		}
		return -1;
	}
	
}

class Giver implements Comparable<Giver>{
	
	public int money;
	public String name;
	
	public Giver(String s){
		name = s;
		money = 0;
	}
	
	public int compareTo(Giver g){
		return name.compareTo(g.name);
	}
	
	public boolean equals(Giver g){
		return name.equals(g.name);
	}
}
