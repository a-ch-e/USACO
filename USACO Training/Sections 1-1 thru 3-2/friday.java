import java.io.*;

/*
ID: ayc22031
LANG: JAVA
PROG: friday
 */




public class friday {
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader read = new BufferedReader(new FileReader("friday.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("friday.out")));
		int n = Integer.parseInt(read.readLine());
		int[] count = new int[7];
		int friday = 0;
		for(int i = 0; i < n; i++){
			count[friday]++; // January
			friday = (friday + 3) % 7;
			count[friday]++; // February
			if(isLeap(i + 1900)) friday = (friday + 1) % 7;
			count[friday]++; // March
			friday = (friday + 3) % 7;
			count[friday]++; // April
			friday = (friday + 2) % 7;
			count[friday]++; // May
			friday = (friday + 3) % 7;
			count[friday]++; // June
			friday = (friday + 2) % 7;
			count[friday]++; // July
			friday = (friday + 3) % 7;
			count[friday]++; // August
			friday = (friday + 3) % 7;
			count[friday]++; // September
			friday = (friday + 2) % 7;
			count[friday]++; // October
			friday = (friday + 3) % 7;
			count[friday]++; // November
			friday = (friday + 2) % 7;
			count[friday]++; // December
			friday = (friday + 3) % 7;
		}
		out.print(count[0]);
		for(int i = 1; i < 7; i++){
			out.print(" " + count[i]);
		}
		out.print("\n");
		out.close();
		System.exit(0);
		
	}
	
	private static boolean isLeap(int year){
		if(year % 400 == 0)
			return true;
		if(year % 100 == 0)
			return false;
		if(year % 4 == 0)
			return true;
		return false;
	}
	
}
