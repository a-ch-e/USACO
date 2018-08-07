/*
ID: ayc22032
LANG: JAVA
TASK: shopping
*/

/*

Shopping Offers
IOI'95
In a certain shop, each kind of product has an integer price. For example, the price of a flower is 2 zorkmids (z) and
the price of a vase is 5z. In order to attract more customers, the shop introduces some special offers.

A special offer consists of one or more product items together for a reduced price, also an integer. Examples:

three flowers for 5z instead of 6z, or
two vases together with one flower for 10z instead of 12z.
Write a program that calculates the price a customer has to pay for a purchase, making optimal use of the special offers
to make the price as low as possible. You are not allowed to add items, even if that would lower the price.

For the prices and offers given above, the (lowest) price for three flowers and two vases is 14z: two vases and one flower
for the reduced price of 10z and two flowers for the regular price of 4z.

PROGRAM NAME: shopping

INPUT FORMAT

The input file has a set of offers followed by a purchase.
Line 1:	s, the number of special offers, (0 <= s <= 99).
Line 2..s+1:	Each line describes an offer using several integers. The first integer is n (1 <= n <= 5), the number of
products that are offered. The subsequent n pairs of integers c and k indicate that k items (1 <= k <= 5) with product code
c (1 <= c <= 999) are part of the offer. The last number p on the line stands for the reduced price (1 <= p <= 9999). The
reduced price of an offer is less than the sum of the regular prices.
Line s+2:	The first line contains the number b (0 <= b <= 5) of different kinds of products to be purchased.
Line s+3..s+b+2:	Each of the subsequent b lines contains three values: c, k, and p. The value c is the (unique) product
code (1 <= c <= 999). The value k indicates how many items of this product are to be purchased (1 <= k <= 5). The value p
is the regular price per item (1 <= p <= 999). At most 5*5=25 items can be in the basket.

SAMPLE INPUT (file shopping.in)

2
1 7 3 5
2 7 1 8 2 10
2
7 3 2
8 2 5

OUTPUT FORMAT

A single line with one integer: the lowest possible price to be paid for the purchases.

SAMPLE OUTPUT (file shopping.out)

14

 */

import java.io.*;
import java.util.*;

public class shopping2 {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("shopping.in"));
		int S = Integer.parseInt(read.readLine());
		Offer2[] offers = new Offer2[S];
		for(int i = 0; i < S; i++){
			offers[i] = new Offer2(new StringTokenizer(read.readLine()));
		}
		int B = Integer.parseInt(read.readLine());
		int[] codes = new int[B];
		int[] items = new int[B];
		int[] prices = new int[B];
		int maxPrice = 0;
		for(int i = 0; i < B; i++){
			StringTokenizer st = new StringTokenizer(read.readLine());
			codes[i] = Integer.parseInt(st.nextToken());
			items[i] = Integer.parseInt(st.nextToken());
			prices[i] = Integer.parseInt(st.nextToken());
			maxPrice += items[i] * prices[i];
		}
		read.close();
		
		double bestCase = 1;
		for(int i = 0; i < S; i++){
			bestCase = Math.min(bestCase, offers[i].norm(codes, prices));
		}
		System.out.println(bestCase);
		
		int ans = maxPrice;
		ArrayList<ArrayList<Config>> dp = new ArrayList<ArrayList <Config>>(maxPrice + 1);
		boolean[] canReach = new boolean[maxPrice + 1];
		for(int i = 0; i <= maxPrice; i++){
			dp.add(new ArrayList<Config>());
		}
		dp.get(maxPrice).add(new Config(items, 0));
		canReach[maxPrice] = true;
		
		for(int i = maxPrice; i >= maxPrice * bestCase; i--){
			if(!canReach[i])
				continue;
			for(Config c: dp.get(i)){
				for(int j = c.lastOffer; j < S; j++){
					Config temp = c.reduce(offers[j], j);
					if(temp != null){
						ans = Math.min(ans, i - offers[j].save);
						dp.get(i - offers[j].save).add(temp);
						canReach[i - offers[j].save] = true;
					}
				}
			}
//			for(int j = 0; j < S; j++){
//				if(i + offers[j].save <= maxPrice && !dp.get(i + offers[j].save).isEmpty()){
//					for(Config c : dp.get(i + offers[j].save)){
//						if(c.lastOffer > j)
//							continue;
//						Config temp = c.reduce(offers[j], j);
//						if(temp != null){
//							ans = i;
//							dp.get(i).add(temp);
//						}
//					}
//				}
//			}
			System.out.println(dp.get(i));
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("shopping.out"));
		out.println(ans);
		System.out.println(ans);
		out.close();
		System.exit(0);
		
	}
	
}

class Offer2{
	
	int[][] tempPack;
	int[] pack;
	int price;
	int save;
	
	public Offer2(StringTokenizer st){
		
		int n = Integer.parseInt(st.nextToken());
		tempPack = new int[n][2];
		for(int i = 0; i < n; i++){
			tempPack[i][0] = Integer.parseInt(st.nextToken());
			tempPack[i][1] = Integer.parseInt(st.nextToken());
		}
		price = Integer.parseInt(st.nextToken());
		
	}
	
	public double norm(int[] codes, int[] prices){
		
		pack = new int[codes.length];
		int cost = 0;
		for(int i = 0; i < pack.length; i++){
			for(int j = 0; j < tempPack.length; j++){
				if(tempPack[j][0] == codes[i]){
					pack[i] = tempPack[j][1];
					break;
				}
			}
			cost += pack[i] * prices[i];
		}
		save = cost - price;
		double temp = price;
		return temp / cost;
	}
	
}

class Config{
	
	int[] itemsLeft;
	int lastOffer;
	public Config(int[] items, int last){
		itemsLeft = new int[items.length];
		for(int i = 0; i < items.length; i++){
			itemsLeft[i] = items[i];
		}
		lastOffer = last;
	}
	
	public Config reduce(Offer2 o, int ind){
		boolean ok = true;
		for(int i = 0; i < itemsLeft.length; i++){
			itemsLeft[i] -= o.pack[i];
			if(itemsLeft[i] < 0)
				ok = false;
		}
		Config temp = new Config(itemsLeft, ind);
		for(int i = 0; i < itemsLeft.length; i++){
			itemsLeft[i] += o.pack[i];
		}
		if(ok) return temp;
		
		return null;
	}
	
	public String toString(){
		return Arrays.toString(itemsLeft) + "; last offer " + lastOffer;
	}
	
}