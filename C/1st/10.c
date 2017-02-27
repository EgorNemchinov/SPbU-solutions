#include <stdio.h>

//не разобрался как без цикла

int zeroAmount(int *array, int size);

int main() {
	
	int arr[] = {1, 3, 0 , 6, 0, 10, 0};

	printf("%d\n", zeroAmount(arr, 7));
	return 0;
}

int zeroAmount(int *array, int size) {
	int i, count = 0;
	for (i = 0; i < size; ++i)
	{
		count+=(*(array + i)==0);
	}
	return count;
}