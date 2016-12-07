#include <stdio.h>
#include <stdlib.h> 
#include <unistd.h>
#include <sys/mman.h>
#include <fcntl.h> 
#include "tools.h"
#include "algorithms.h"
#define HELP_STRING "Mmap sorts.\nTo call a sort: ./<RUNNABLE> <ARG_1> <AMOUNT_OF_STRINGS> <INPUT_FILE> \n\
To call Quick sort, first argument is  \"-q\"(by default). \n\
To call Bubble sort, first argument - \"-b\".\n"

int main(int argc, char ** argv) {
	int amount, i, fin, success = 1;
	char **strings, ch;
	size_t fileSize;
	char sort = 'q'; //b or q(default)

	while((ch = getopt(argc, argv, "hqbi")) != EOF) {
		switch(ch) {
			case 'h':
				printf("%s", HELP_STRING);
				success = 0;
				return 0;
			case 'q':
				sort = 'q';
				break;
			case 'b':
				sort = 'b';
				break;
			case 'i':
				sort = 'i';
				break;
			default:
				fprintf(stderr, "Неизвестный параметр: '%s'\n", optarg);
				return 1;
		}
	}

	argc -= optind;
	argv += optind;

	if(argc < 2) {
		fprintf(stderr, "Неверные параметры. Введите количество строк и имя входного файла.\n");
		exit(1);
	} 

	amount = atoi(argv[0]);
	fin = open(argv[argc - 1], O_RDONLY);
	
    strings = (char**) malloc(amount * sizeof(char*));
	readInput(fin, strings, amount, &fileSize);

	switch(sort) {
		case 'b':
			bubbleSort(strings, amount);
			break;
		case 'i':
			insertionSort(strings, amount);
			break;
		case 'q':
		default:
			quickSort(strings, 0, amount - 1);
			break;
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