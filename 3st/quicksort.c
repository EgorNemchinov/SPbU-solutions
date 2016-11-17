#include <stdio.h>
#include <string.h>
#include "compare.c"
#include <stdlib.h>
#define MAX_STRING_LENGTH 256
#define MAX_STRINGS_AMOUNT 100

void quickSort(char **a, int first, int last);
void swap(char **arr, int a, int b);
void randomSort(char **a, int first, int last);

int main() {
	int i, n;
	FILE *fin, *fout;
	fin = fopen("input.txt", "r");
	fout = fopen("output.txt", "w");

	fscanf(fin, "%i\n", &n);
	char **strings;
	strings = malloc(MAX_STRINGS_AMOUNT * sizeof(char*));

	for (i = 0; i < n; ++i)
	{
		strings[i] = malloc((MAX_STRING_LENGTH + 1) * sizeof(char));
	}

	for (i = 0; i < n; ++i)
	{
		fgets(strings[i], sizeof(strings[i]), fin);
		strings[i][strcspn(strings[i], "\n")] = 0;
		puts(strings[i]);
	}

	quickSort(strings, 0, n-1);

	for (i = 0; i < n; ++i)
	{
		fprintf(fout, "%s\n", strings[i]);
	}

	for (i = 0; i < MAX_STRINGS_AMOUNT; ++i)
	{
		free(strings[i]);
	}
	fclose(fin);
	fclose(fout);
}

void swap(char **arr, int a, int b)
{
    char *temp = arr[a];
   	arr[a] = arr[b];
   	arr[b] = temp;
}

void quickSort(char **a, int first, int last){
	int i = first, j = last;
	char  *p = a[(first + last) / 2];
     //p - центр. эл-т

	do
	{
		while ( i <= last && compareStrings(a[i], p) < 0) i++;
		while (j >= first && compareStrings(a[j], p) > 0) j--;
		
		if(i <= j) {
			if(compareStrings(a[i], a[j]) > 0) {
				swap(a, i, j);
			} 
			i++;
			j--;
		}
	} while (i <= j);

	if(j > first) quickSort(a, first, j);
	if(i < last) quickSort(a, i, last);
}