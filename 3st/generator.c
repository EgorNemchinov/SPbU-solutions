#include <stdio.h>
#include <time.h>
#include <stdlib.h>

#define MAX_LENGTH 100000
#define MIN_LENGTH 100
#define CHAR_RANGE_START 48
#define CHAR_RANGE_END 122

int randInRange(int start, int end) {
	return (start + (rand() % (end - start)));
}

int main(int argc, char ** argv) {
	if(argc < 3) {
		fprintf(stderr, "Invalid arguments. Enter required amount of strings and name of the output file.\n");
		exit(1);
	}

	int n = atoi(argv[1]);
	FILE *fout;
	if (!(fout = fopen(argv[2], "w"))) {
        perror(argv[2]);
        exit(1);
    }

	int i, j, len;
	for (i = 0; i < n; ++i)
	{
		len = randInRange(MIN_LENGTH, MAX_LENGTH);
		for (int i = 0; i < len; ++i)
			{
				fprintf(fout, "%c", randInRange(CHAR_RANGE_START, CHAR_RANGE_END));
			}
		fprintf(fout,"\n");
	}

	fclose(fout);
}