import java.io.*;
import java.util.*;

public class Donut {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int w = Integer.parseInt(st.nextToken());
		int h = Integer.parseInt(st.nextToken());
		read.close();		
		System.out.println(((w-1)*h+(h-1)*w - (w * h - 1)));
		System.exit(0);
		
	}
	
}
