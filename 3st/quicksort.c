#include <stdio.h>
#include "tools.h"
#include <stdlib.h>

void quickSort(char **a, int first, int last);

int main(int argc, char ** argv) {
	int n, i;
	char **strings;
	FILE *fin;

	if(argc != 3) {
		fprintf(stderr, "Invalid arguments. Enter amount of strings and name of the input file.\n");
		exit(1);
	}

	n = atoi(argv[1]);
	if (!(fin = fopen(argv[2], "r"))) {
        perror(argv[2]);
        exit(1);
    }
	
	strings = (char**) malloc(n * sizeof(char*));
	readInput(fin, strings, n);

	quickSort(strings, 0, n-1);

	for (i = 0; i < n; ++i)
	{
		puts(strings[i]);
		free(strings[i]);
	}
	free(strings);
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