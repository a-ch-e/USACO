import java.io.*;
import java.util.*;

public class Beat {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(read.readLine());
		StringTokenizer st = new StringTokenizer(read.readLine());
		double x1Prev = Integer.parseInt(st.nextToken());
		double y1Prev = Integer.parseInt(st.nextToken());
		double x2Prev = Integer.parseInt(st.nextToken());
		double y2Prev = Integer.parseInt(st.nextToken());
		double maxSpeed = 0;
		
		for(int i = 1; i < N; i++){
			st = new StringTokenizer(read.readLine());
			double x1 = Integer.parseInt(st.nextToken());
			double y1 = Integer.parseInt(st.nextToken());
			double x2 = Integer.parseInt(st.nextToken());
			double y2 = Integer.parseInt(st.nextToken());
			double max1 = Math.max(Math.sqrt((x1-x1Prev)*(x1-x1Prev)+(y1-y1Prev)*(y1-y1Prev)), Math.sqrt((x2-x2Prev)*(x2-x2Prev)+(y2-y2Prev)*(y2-y2Prev)));
			double max2 = Math.max(Math.sqrt((x2-x1Prev)*(x2-x1Prev)+(y2-y1Prev)*(y2-y1Prev)), Math.sqrt((x1-x2Prev)*(x1-x2Prev)+(y1-y2Prev)*(y1-y2Prev)));
			maxSpeed = Math.max(maxSpeed, Math.min(max1, max2));
			x1Prev = x1;
			y1Prev = y1;
			x2Prev = x2;
			y2Prev = y2;
		}
		read.close();		
		System.out.println(maxSpeed);
		System.exit(0);
		
	}
	
}
