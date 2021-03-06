#include<stdio.h>
#include<algorithm>

using namespace std;
int curr[500000];

int main(){
		
		int N;
		scanf("%d\n", &N);
		int depths[500000];
		int maxD = 0;
		for(int i = 0; i < N; i++){
			scanf("%d ", &depths[i]);
			maxD = max(maxD, depths[i]);
		}		
		
		
		int maxM = 0;
		for(int i = 1; i <= maxD; i++){
			for(int j = 0; j < N; j++){
				if(depths[j] < i - 1)
					continue;
				int next = j;
				while(depths[next] >= i)
					next++;
				for(int k = next - 1; k >= j; k--){
					curr[k] += next - j;
					maxM = max(curr[k], maxM);
				}
				j = next;
			}
			//System.out.println(Arrays.toString(mass[i]));
		}
		printf("%d\n", maxM);
		return 0;
		
	}

