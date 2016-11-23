#include <stdlib.h>
#include <stdio.h>

void readInput(FILE * fin, char ** strings, int n) {
	char c;
	size_t len;
	size_t index = -1, i = 0;

	//count symbols in line and allocate memory
	while(n > 0) {
		n--;
		index++;

		len = 0;
		while((c = fgetc(fin)) != '\n' && c >= 0) {
			len++;
		}
		len++;

		strings[index] = (char *) malloc(len * sizeof(char));

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
			(*(strings + index))[i] = c;
			i++;
		}
		(*(strings + index))[i] = '\0';
	}

	fclose(fin);
}


void swap(char **strings, int firstPos, int secondPos)
{
    char *temp = strings[firstPos];
   	strings[firstPos] = strings[secondPos];
   	strings[secondPos] = temp;
}

int compareStrings(char *strings, char *to) { 
	int i;
	if(strings == to) 
		return 0;
	 while((i = *to - *strings) == 0) {
	 	if(*strings == 0) 
	 		if(*to == 0)
	 			return 0;
	 		else return -1;
	 	else if(*to == 0)
	 		return 1;
	 	strings++;
	 	to++;
	 }
	 if(i < 0) 
	 	return 1;
	 else if(i > 0)
	 	return -1;
	 else return 0;
}