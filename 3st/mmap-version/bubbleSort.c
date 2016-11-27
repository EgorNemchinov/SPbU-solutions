#include <stdio.h>
#include <stdlib.h> 
#include <unistd.h>
#include <fcntl.h> 
#include <sys/mman.h>
#include "tools.h"

void bubbleSort(char ** strings, int amount);

int main(int argc, const char ** argv) {
	int amount, i, fin;
	char **strings;
	size_t fileSize;

	if(argc != 3) {
		fprintf(stderr, "Invalid arguments. Enter amount of strings and name of the input file.\n");
		exit(1);
	}

	amount = atoi(argv[1]);
	fin = open(argv[2], O_RDONLY);
	
	strings = (char**) malloc(amount * sizeof(char*));
	readInput(fin, strings, amount, &fileSize);

	bubbleSort(strings, amount);

	for (i = 0; i < amount; ++i)
	{
		printString(strings[i], (char*)(strings[0] + fileSize));
	}
	munmap(strings[0], fileSize);
	free(strings);

	close(fin);
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