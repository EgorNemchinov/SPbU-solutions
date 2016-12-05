#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h> 
#include <sys/mman.h>
#include <sys/stat.h>

void readInput(int fin, char ** strings, int amount, size_t *size) {
	size_t index = 0, i = 0;
	struct stat statbuf;
	char *src;

	if(fin < 0) {
		perror("File");
		exit(1);
	}
	
	if((fstat(fin, &statbuf)) < 0) {
		printf("fstat error.");
		exit(1);
	}
	*size = statbuf.st_size;

	if ( (src =(char*) mmap(NULL, statbuf.st_size, PROT_READ, MAP_SHARED, fin, 0)) == MAP_FAILED )  {
		printf("mmap error.");
		exit(1);
	}

	strings[index++] = src;

	for (i = 0; i < statbuf.st_size && index < amount; ++i)
	{
		if(src[i] == '\n') {
			strings[index++] = src + (i+1);
		}
	}

}

void printString(char *str, char *last) {
	char ch;
	int index = 0;
	while(((str + index) <  last) && (ch = str[index]) != '\n') {
		printf("%c", ch);
		index++;
	}
	putchar('\n');
}


void swap(char **strings, int firstPos, int secondPos)
{
    char *temp = strings[firstPos];
   	strings[firstPos] = strings[secondPos];
   	strings[secondPos] = temp;
}

int compareStrings(const char *strings, const char *to) { 
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

int compareMappedStrings(const char *strings, const char *to) { 
	int i;
	if(strings == to) 
		return 0;
	 while((i = *to - *strings) == 0) {
	 	if(*strings == '\n') 
	 		if(*to == '\n')
	 			return 0;
	 		else return -1;
	 	else if(*to == '\n')
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