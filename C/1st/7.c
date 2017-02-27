#include <stdlib.h>
#include <stdio.h>
#include "stack.c"
#define STACK_MAX_SIZE 20
#define STACK_OVERFLOW  -100
#define STACK_UNDERFLOW -101
#define OPEN_CIR 1
#define CLOSE_CIR 2
#define OPEN_SQ 3
#define CLOSE_SQ 4
#define OPEN_FIG 5
#define CLOSE_FIG 6

int main() {

	FILE *fin = fopen("input.txt", "r");
	FILE *fout = fopen("output.txt", "w");

	Stack_t stack;
	stack.size = 0;

	int failed = 0;

	char c;
	do {
		c = (char)fgetc(fin);
		switch(c) {
			case '(':
				 push(&stack, OPEN_CIR);
				break;
			case '[':
				 push(&stack, OPEN_CIR);
				break;
			case '{':
				 push(&stack, OPEN_FIG);
				break;
			case ')':
				if(stack.size <= 0) {
					failed = 1;
				} else if(peek(&stack) == OPEN_CIR) {
					 pop(&stack);
				} else {
					failed = 1;
				}
				break;
			case ']':
				if(stack.size <= 0) {
					failed = 1;
				} else if(peek(&stack) == OPEN_SQ) {
					 pop(&stack);
				} else {
					failed = 1;
				}
				break;
			case '}':
				if(stack.size <= 0) {
					failed = 1;
				} else if(peek(&stack) == OPEN_FIG) {
					 pop(&stack);
				} else {
					failed = 1;
				}
				break;
		}
	}
	while(c != EOF && failed == 0);

	if(stack.size != 0) failed = 1;
	if(failed) {
		fprintf(fout, "%s\n", "Failed.");
	} else 
		fprintf(fout, "%s\n", "Success.");
 
	return 0;
}