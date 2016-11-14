#include <stdio.h>
#include <math.h>

#define MAX 100000

int main() {
	int n, i, j, count = 0;
	int num[MAX];

	scanf("%d", &n);
	
	for(i = 2; i <= n; i++) {
		*(num + i) = 1; 
	}
	for(i = 2; i <= ceil(sqrt(n)); i++) {
		j = 2*i;
		while(j <= n) {
			*(num + j) = 0;
			j += i;
		}
	}
	
	for(i = 2; i <= n; i++) {
		if(num[i] == 1) 
			printf("%d\n", i);
	}
	
	return 0;
}