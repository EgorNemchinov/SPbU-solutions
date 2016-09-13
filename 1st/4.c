#include <stdio.h>
#include <stdlib.h> 

int main() {
	
	int a, b, temp, count;

	scanf("%d %d", &a, &b);
	//возьмем модули от чисел
	a = abs(a);
	b = abs(b);

	//возьмем a как максимальное из двух, b - минимальное
	if(a<b) {//меняем местами
		temp = b;
		b = a;
		a = temp;
	}

	if(b==0) printf("No solution. Division by zero.\n");
	else {
		while(a>=b) {
			a-=b;
			count++;
		}

		printf("%d\n", count);
	}
	return 0;
}