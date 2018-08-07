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

public class shopping {
	
	private static int[] scale;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader read = new BufferedReader(new FileReader("shopping.in"));
		int S = Integer.parseInt(read.readLine());
		Offer[] offers = new Offer[S];
		for(int i = 0; i < S; i++){
			offers[i] = new Offer(new StringTokenizer(read.readLine()));
		}
		int B = Integer.parseInt(read.readLine());
		if(B == 0){
			PrintWriter out = new PrintWriter(new FileWriter("shopping.out"));
			out.println(0);
			System.out.println(0);
			out.close();
			System.exit(0);
		}
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
		
		scale = new int[B];
		scale[B - 1] = 1;
		for(int i = B - 2; i >= 0; i--){
			scale[i] = (items[i + 1] + 1) * scale[i + 1];
		}
		
		for(int i = 0; i < S; i++){
			offers[i].norm(codes, prices);
		}
		
		int ans = maxPrice;
		
		int[] configs = new int[toInd(items) + 1];
		for(int i = 0; i < configs.length; i++){
			configs[i] = Integer.MAX_VALUE;
		}
		configs[configs.length - 1] = maxPrice;
		for(int i = configs.length - 1; i >= 0; i--){
			ans = Math.min(ans, configs[i]);
			int[] temp = toItems(i);
			for(int j = 0; j < S; j++){
				boolean ok = true;
				for(int k = 0; k < temp.length; k++){
					temp[k] -= offers[j].pack[k];
					if(temp[k] < 0)
						ok = false;
				}
				int ind = toInd(temp);
				for(int k = 0; k < temp.length; k++){
					temp[k] += offers[j].pack[k];
				}
				if(ok)
					configs[ind] = Math.min(configs[ind], configs[i] - offers[j].save);
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("shopping.out"));
		out.println(ans);
		System.out.println(ans);
		out.close();
		System.exit(0);
		
	}
	
	private static int toInd(int[] items){
		
		int ind = 0;
		for(int i = 0; i < items.length; i++){
			ind += scale[i] * items[i];
		}
		return ind;
		
	}
	
	private static int[] toItems(int ind){
		
		int[] items = new int[scale.length];
		for(int i = 0; i < items.length; i++){
			items[i] = ind / scale[i];
			ind %= scale[i];
		}
		return items;
		
	}
	
}

class Offer{
	
	int[][] tempPack;
	int[] pack;
	int price;
	int save;
	
	public Offer(StringTokenizer st){
		
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