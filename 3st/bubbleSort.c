#include <stdio.h>
#include <stdlib.h> 
#include "tools.h"

void bubbleSort(char ** strings, int n);

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

	bubbleSort(strings, n);

	for (i = 0; i < n; ++i)
	{
		puts(strings[i]);
		free(strings[i]);
	}
	free(strings);
}

void bubbleSort(char ** strings, int n) {
	int i, swapped, j;
	for (i = 0; i < n - 1; i++)
	{
		swapped = 0;
		for (j = 0; j < n - i - 1; j++) {
		
			if(compareStrings(strings[j], strings[j + 1]) == 1) {
				swap(strings, j, j+1);
				swapped = 1;
			}
		}
		if(swapped == 0) 
			continue;
	}
}