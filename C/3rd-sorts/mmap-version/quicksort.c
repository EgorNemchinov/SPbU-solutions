#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h> 
#include <sys/mman.h>
#include "tools.h"

void quickSort(char **a, int first, int last);

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

	quickSort(strings, 0, amount - 1);

	for (i = 0; i < amount; ++i)
	{
		printString(strings[i], (char*)(strings[0] + fileSize));
	}
	munmap(strings[0], fileSize);
	free(strings);
	
	close(fin);
}

void quickSort(char **strings, int first, int last){
	int left = first, right = last;
	char  *middle = strings[(first + last) / 2];

	do
	{
		while (left <= last && compareStrings(strings[left], middle) < 0) left++;
		while (right >= first && compareStrings(strings[right], middle) > 0) right--;
		
		if(left <= right) {
			if(compareStrings(strings[left], strings[right]) > 0) {
				swap(strings, left, right);
			} 
			left++;
			right--;
		}

	} while (left <= right);

	if(right > first) quickSort(strings, first, right);
	if(left < last) quickSort(strings, left, last);
}