#include <stdio.h>
#include <stdlib.h>
#include "tools.h"

void quickSort(char **a, int first, int last);

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

	quickSort(strings, 0, amount - 1);

	for (i = 0; i < amount; ++i)
	{
		puts(strings[i]);
		free(strings[i]);
	}
	free(strings);
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