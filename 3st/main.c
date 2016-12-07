#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> 
#include "tools.h"
#include "algorithms.h"
#define HELP_STRING "Simple sorts.\nTo call a sort: ./<RUNNABLE> <ARG_1> <AMOUNT_OF_STRINGS> <INPUT_FILE> \n\
Соль в солонке. \n\
To call Quick sort, first argument is  \"-q\"(by default). \n\
To call Bubble sort, first argument - \"-b\".\n\
To call Insertion sort, first argument - \"-i\".\n
To call Merge sort, first argument - \"-m\".\n"

int main(int argc, char ** argv) {
	int amount, i, success = 1;
	char **strings, ch;
	FILE *fin;
	char sort = 'q'; //b or q(default)

	while((ch = getopt(argc, argv, "hqbim")) != EOF) {
		switch(ch) {
			case 'h':
				printf("%s", HELP_STRING);
				success = 0;
				return 0;
			case 'q':
			case 'b':
			case 'i':
			case 'm':
				sort = ch;
				break;
			default:
				fprintf(stderr, "Неизвестный параметр: '%s'. '-h' чтобы узнать в чем соль.\n", optarg);
				return 1;
		}
	}

	argc -= optind;
	argv += optind;

	if(argc < 2) {
		fprintf(stderr, "Неверные параметры. '-h' чтобы узнать в чем соль.\n");
		exit(1);
	} 

	amount = atoi(argv[0]);
	if (!(fin = fopen(argv[1], "r"))) {
        perror(argv[1]);
        exit(1);
    }
	
    strings = (char**) malloc(amount * sizeof(char*));
	readInput(fin, strings, amount);

	switch(sort) {
		case 'b':
			bubbleSort(strings, amount);
			break;
		case 'i':
			insertionSort(strings, amount);
			break;
		case 'm':
			mergeSort(strings, 0, amount - 1);
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
			puts(strings[i]);
			free(strings[i]);
		}
		free(strings);
	}
}