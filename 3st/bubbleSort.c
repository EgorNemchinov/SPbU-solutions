#include <stdio.h>
#include <stdlib.h> 
#include "tools.h"

void bubbleSort(char ** strings, int amount);

int main(int argc, const char ** argv) {
	int amount, i;
	char **strings;
	FILE *fin;

	if(argc != 3) {
		fprintf(stderr, "Invalid arguments. Enter amount of strings and name of the input file.\n");
		exit(1);
	}

	amount = atoi(argv[1]);
	if (!(fin = fopen(argv[2], "r"))) {
        perror(argv[2]);
        exit(1);
    }
	
	strings = (char**) malloc(amount * sizeof(char*));
	readInput(fin, strings, amount);

	bubbleSort(strings, amount);

	for (i = 0; i < amount; ++i)
	{
		puts(strings[i]);
		free(strings[i]);
	}
	free(strings);
}

void bubbleSort(char ** strings, int amount) {
	int i, j, swapped;
	
	for (i = 0; i < amount - 1; i++)
	{
		swapped = 0;
		for (j = 0; j < amount - i - 1; j++) {
		
			if(compareStrings(strings[j], strings[j + 1]) == 1) {
				swap(strings, j, j+1);
				swapped = 1;
			}
		}
		if(swapped == 0) 
			continue;
	}
}