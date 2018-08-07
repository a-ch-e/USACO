import java.io.*;
import java.util.*;

public class SnowballFight {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(read.readLine());
		int A = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());
		long R = Long.parseLong(st.nextToken());
		st = new StringTokenizer(read.readLine());
		long hX = Long.parseLong(st.nextToken());
		long hY = Long.parseLong(st.nextToken());
		SBMan[] allies = new SBMan[A];
		for(int i = 0; i < A; i++){
			st = new StringTokenizer(read.readLine());
			long x = Long.parseLong(st.nextToken()) - hX;
			long y = Long.parseLong(st.nextToken()) - hY;
			allies[i] = new SBMan(x, y);
		}
		
		long[][] enemies = new long[B][2];
		boolean[] canHit = new boolean[A];
		for(int i = 0; i < B; i++){
			st = new StringTokenizer(read.readLine());
			enemies[i][0] = Long.parseLong(st.nextToken()) - hX;
			enemies[i][1] = Long.parseLong(st.nextToken()) - hY;
		}
		
		for(int i = 0; i < A; i++){
			canHit[i] = true;
			for(int j = 0; j < B; j++){
				double theta0 = Math.atan2(enemies[j][1], enemies[j][0]);
				if(theta0 < 0)
					theta0 += 2 * Math.PI;
//				System.out.println(theta0);
				double distance = enemies[j][0] * enemies[j][0] + enemies[j][1] * enemies[j][1];
				if(allies[i].angle == theta0 && distance < (allies[i].x * allies[i].x + allies[i].y * allies[i].y)){
					canHit[i] = false;
//					System.out.println(i);
				}
			}
		}
		
		boolean[] work = new boolean[B];
		long count = 0;
		for(int i = 0; i < B; i++){
			long x = enemies[i][0];
			long y = enemies[i][1];
			double theta0 = Math.atan2(y, x);
			if(theta0 < 0)
				theta0 += 2 * Math.PI;
//			System.out.println(theta0);
			double distance = Math.sqrt(x * x + y * y);
			
			work[i] = true;
			
			for(int j = 0; j < A; j++){
				if(!canHit[j])
					continue;
				if((x - allies[j].x) * (x - allies[j].x) + (y - allies[j].y) * (y - allies[j].y) <= R * R){
					work[i] = false;
					break;
				}
			}
			if(work[i] && R <= distance) {
				double dTheta = Math.asin(R / distance);
//				System.out.println(dTheta);
				for(int j = 0; j < A; j++){
//					System.out.println(allies[j].angle);
					if(!canHit[j])
						continue;
					if(between(theta0 - dTheta, allies[j].angle, theta0 + dTheta)){
						double theta1 = theta0 + Math.PI;
						if(theta1 >= 2 * Math.PI)
							theta1 -= 2 * Math.PI;
						double dTheta2 = Math.PI / 2 - dTheta;
//						System.out.println(dTheta2);
						double angle2 = Math.atan2(allies[j].y - y, allies[j].x - x);
						if(angle2 < 0)
							angle2 += 2 * Math.PI;
//						System.out.println(angle2);
						if(between(theta1 - dTheta2, angle2, theta1 + dTheta2)){
							work[i] = false;
							break;
						}
					}
				}
			}
			if(work[i])
				count++;
		}
		
		read.close();
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		out.println(count);
		String s = "";
		for(int i = 0; count > 0 && i < B; i++){
			if(!work[i])
				continue;
			if(s.length() == 0){
				s += (i + 1);
			} else {
				s += " " + (i + 1);
			}
		}
		out.println(s);
		out.close();
		
	}
	
	private static boolean between(double a, double b, double c){
		
//		System.out.format("(%.2f, %.2f, %.2f)\n", a, b, c);
		b -= a;
		if(b < 0){
			b += 2 * Math.PI;
		}
		if(b >= 2 * Math.PI){
			b -= 2 * Math.PI;
		}
		c -= a;
		if(c < 0){
			c += 2 * Math.PI;
		}
		if(c >= 2 * Math.PI){
			c -= 2 * Math.PI;
		}
//		System.out.format("--> (%.2f, %.2f, %.2f)\n", 0.0, b, c);
		return b < c;
		
	}
}

class SBMan/* implements Comparable<SBMan>*/{
	
	long x, y;
	double angle;
	
	public SBMan(long a, long b){
		x = a; y = b;
		angle = Math.atan2(y, x);
		if(angle < 0)
			angle += 2 * Math.PI;
	}
	
//	public int compareTo(SBMan other){
//		if(this.angle == other.angle)
//			return (this.x * this.x + this.y * this.y) - (other.x * other.x + other.y * other.y);
//		if(this.angle < other.angle)
//			return -1;
//		return 1;
//	}
}
