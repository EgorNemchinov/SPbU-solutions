#include <stdlib.h>
#include <stdio.h>

void readInput(FILE * fin, char ** arr, int n) {
	char c;
	size_t len;
	size_t index = -1, i = 0;

	//counting symbols in line and allocating memory
	while(n > 0) {
		n--;
		index++;

		len = 0;
		while((c = fgetc(fin)) != '\n' && c >= 0) {
			len++;
		}
		len++;
		// printf("%zu \n", len);
		arr[index] = (char *) malloc(len * sizeof(char));

		if(c == EOF && n > 0) {
			fprintf(stderr, "File ended sooner than was expected.\n");
			exit(1);
		}
	}

	//reset to initial
	rewind(fin);
	n = index + 1;
	index = -1;
	i = 0;

	while(n > 0) {
		n--;
		index++;
		i = 0;

		while((c = fgetc(fin)) != '\n' && c >= 0) {
			(*(arr + index))[i] = c;
			i++;
		}
		(*(arr + index))[i] = '\0';
	}

	fclose(fin);
}


void swap(char **arr, int a, int b)
{
    char *temp = arr[a];
   	arr[a] = arr[b];
   	arr[b] = temp;
}

int compareStrings(char *arr, char *to) { 
	int i;
	if(arr == to) 
		return 0;
	 while((i = *to - *arr) == 0) {
	 	if(*arr == 0) 
	 		if(*to == 0)
	 			return 0;
	 		else return -1;
	 	else if(*to == 0)
	 		return 1;
	 	arr++;
	 	to++;
	 }
	 if(i<0) 
	 	return 1;
	 else if(i>0)
	 	return -1;
	 else return 0;
}