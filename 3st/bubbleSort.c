#include <stdio.h>
#include <string.h> 
#include <stdlib.h> 
#include "compare.c"

const int MAX_STRING_LENGTH = 30;
const int MAX_STRINGS_AMOUNT = 10;

int main() {
	int n, i, swapped, j, k;
	FILE *fin, *fout;
	fin = fopen("input.txt", "r");
	fout = fopen("output.txt", "w");
	// printf("Enter amount of strings:\n");
	fscanf(fin, "%i\n", &n);
	// printf("Enter strings one by one:\n");
	// char strings[MAX_STRINGS_AMOUNT][MAX_STRING_LENGTH];
	char **strings;
	strings = malloc(MAX_STRINGS_AMOUNT * sizeof(char*));

	for (i = 0; i < MAX_STRINGS_AMOUNT; ++i)
	{
		strings[i] = malloc((MAX_STRING_LENGTH + 1) * sizeof(char));
	}

	for (i = 0; i < n; ++i)
	{
		fgets(strings[i], sizeof(strings[i]), fin);
		strings[i][strcspn(strings[i], "\n")] = 0;
		puts(strings[i]);
	}
	for (i = 0; i < n - 1; i++)
	{
		swapped = 0;
		for (j = 0; j < n - i - 1; j++) {
		
			if(compareStrings(strings[j], strings[j + 1]) == -1) {
				char *temp = strings[j];
				strings[j] = strings[j+1];
				strings[j+1] = temp;
				/*for (k = 0; k < MAX_STRING_LENGTH; ++k) //меняем строки местами
				{
						char temp = strings[j][k];
						strings[j][k] = strings[j+1][k];
						strings[j+1][k] = temp;
				} */
				swapped = 1;
			}
		}
		if(swapped == 0) 
			continue;
	}
	puts("Finished");
	for (i = 0; i < n; ++i)
	{
		fprintf(fout, "%d - %s\n", i, strings[i]);
	}

	for (i = 0; i < MAX_STRINGS_AMOUNT; ++i)
	{
		free(strings[i]);
	}
	fclose(fin);
	fclose(fout);
}