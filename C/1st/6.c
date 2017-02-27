#include <stdio.h>

long int tenTo(int power);

int main() {
	const int DIGITS = 2;
	const int MAX_SUM = 9*(DIGITS/2);

	int sums[MAX_SUM+1];
	int temp, count;
	int i, k, j;
	int siz = MAX_SUM+1;
	count = 0;

	for (i = 0; i < siz; ++i)
	{
		sums[i] = 0;
	}

	for (k = 0; k < tenTo((DIGITS/2)); ++k) 
	{
			i = k;
			temp = 0;
			for (j = DIGITS/2 - 1; j>0; --j)
			{
				int num = tenTo(j);
				temp += i/num;
				i%=num;
			}
			temp+=i; //единицы

			sums[temp]++; 
	}

	for (i = 0; i < siz; ++i) 
	{
		count+=sums[i]*sums[i];
	}
	
	printAnswer(DIGITS, count);

	return 0;
}

long int tenTo(int power) {
	int i;
	long int result = 1;
	for (i = 0; i < power; ++i)
	{
		result *= 10;
	}
	return result;
}

void printAnswer(int digits, int count) {
	printf("Счастливых билетов от ");//TODO: в функцию
	for(i=0; i<digits; ++i) {
		printf("0");
	}
	printf(" до ");
	for(i=0; i<digits; ++i) {
		printf("9");
	}printf(": %d.\n", count);
}