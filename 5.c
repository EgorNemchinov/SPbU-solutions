#include <stdio.h>
#include <string.h>

typedef int bool;
#define true 1
#define false 0

void swap(int x, int y, int *arr); //поменять местами arr[x] и arr[y]
void printArray(int *arr, int size); //вывести массив с 1го эл-та
void fillArray(int *x, int size); //заполнить массив(индексами или вводом)

int main() {
	int m,n,i,j;
	printf("Enter m, n:\n");
	scanf("%d%d", &m, &n);

	int x[n+m+1]; 
	fillArray(x, m+n+1);

	if(m==0 || n==0) printArray(x, m+n+1);
	else {		
		for (i = 1; i <= n; ++i)
		{
			for (j = i+m; j > i; --j)
			{
				swap(j, j-1, x);
			}
		}
	}

	printArray(x, m+n+1);

	return 0;
}

void swap(int a, int b, int *arr) {
	int temp = arr[a];
	arr[a] = arr[b];
	arr[b] = temp;
}

void printArray(int *arr, int size) {
	int i;
	for (i = 1; i < size; ++i)
	{
		printf("%dй - %d\n", i, arr[i]);
	}
}

void fillArray(int *x, int size) {
	bool arrayFilled = false;
	int i;
	do {
		char k[3];
		printf("Wanna fill the array yourself? Y or N\n");
		scanf("%s", k);
		if(strcmp(k,"Y")==0) {
			for (i = 1; i < size; ++i)
			{
				scanf("%d", &x[i]);
			}
			arrayFilled = true;
		} else if(strcmp(k,"N")==0) {
			for (i = 1; i < size; ++i)
			{
				x[i] = i;
			}		
			arrayFilled = true;
		} else {
			printf("Try again.\n");
		}
	} while(arrayFilled==false);
}