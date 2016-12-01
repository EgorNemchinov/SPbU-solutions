#include <stdio.h>
#include <stdlib.h> 
#include <unistd.h>
#include <sys/mman.h>
#include <fcntl.h> 
#include "tools.h"
#include "algorithms.h"
#define HELP_STRING "\nTo call a sort: ./<RUNNABLE> <ARG_1> <AMOUNT_OF_STRINGS> <INPUT_FILE> \n\
To call Quick sort, first argument is  \"-q\" or \"--quick\"(by default). \n\
To call Bubble sort, first argument - \"-b\" or \"--bubble\".\n"

//TODO: change HELP string & take all cases of possible arguments into account
int main(int argc, const char ** argv) {
	int amount, i, fin, success = 1;
	char **strings;
	size_t fileSize;

	if(argc <= 3) {
		if(compareStrings(argv[argc - 1], "-h") == 0 || compareStrings(argv[argc - 1], "--help") == 0) {
			printf("%s", HELP_STRING);
			success = 0;
		} else {
			fprintf(stderr, "Invalid arguments. Enter amount of strings and name of the input file.\n");
		}
		exit(1);
	} 

	amount = atoi(argv[argc - 2]);
	fin = open(argv[argc - 1], O_RDONLY);
	
	if(argc == 4) {
		if(compareStrings(argv[argc - 3], "-q") == 0 || compareStrings(argv[argc - 3], "--quick") == 0) {
			strings = (char**) malloc(amount * sizeof(char*));
			readInput(fin, strings, amount, &fileSize);
			quickSort(strings, 0, amount - 1);	
		} else if(compareStrings(argv[argc - 3], "-b") == 0 || compareStrings(argv[argc - 3], "--bubble") == 0) {
			strings = (char**) malloc(amount * sizeof(char*));
			readInput(fin, strings, amount, &fileSize);
			bubbleSort(strings, amount);		
		} else if(compareStrings(argv[argc - 3], "-h" ) == 0 || compareStrings(argv[argc - 3], "--help") == 0) {
			printf("%s", HELP_STRING);
			success = 0;
		} 
	} else {
		strings = (char**) malloc(amount * sizeof(char*));
		readInput(fin, strings, amount, &fileSize);
		quickSort(strings, 0, amount - 1);
	}

	//if sort was executed, clean up
	if(success) {
		for (i = 0; i < amount; ++i)
		{
			printString(strings[i], (char*)(strings[0] + fileSize));
		}
		munmap(strings[0], fileSize);
		free(strings);
		
		close(fin);
	}
}