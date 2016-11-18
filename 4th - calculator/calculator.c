#include <stdlib.h>
#include <stdio.h>
#include "big_num.c"
#define QUIT 'q'
#define POS 1
#define NEG 2
#define INVALID_SYMBOL 1
#define MANY_OPERATORS 2
#define WRONG_NUMBER_SIGN 3
#define INVALID_SYMBOL_STRING "Invalid symbol was entered."
#define MANY_OPERATORS_STRING "Too many operators."
#define WRONG_NUMBER_SIGN_STRING "Wrong number's sign was entered."

int isDigit(char ch);

int main() {
	BigNum *a = NULL;

	//TODO: get input into another function
	char c;
	do {
		LinkedList *list = NULL;
		char oper = 0, sign = 0;
		//reading string
		do {
			c = getchar();
				//FIXME: get rid of copypaste
			if(c == '+' || c == '-' || c == '*' || c == '/') {
				if(oper != 0) {
					puts(MANY_OPERATORS_STRING); 
					exit(MANY_OPERATORS);
				} else {
					oper = c;
				}
			} else if(isDigit(c)) {
				if(list == NULL) 
					list = createLinkedList();
				pushFront(list, c);
			} else if(c == '\0' || c == '\n' || c == QUIT) {
				continue; //mb not the best idea
			} else {
				puts(INVALID_SYMBOL_STRING);
				exit(INVALID_SYMBOL);
			}
		}
		while(c > 0 && c != '\n' && c != QUIT ); //means not '\0' and not EOF
		if(c == QUIT) 
			break;
		//so dirty. temporary. later we will quit after printing result

		if(list != NULL) {
			//FIXME: later put to stack
			//transforming '+' '-' or '' into number's sign
			if(oper == '+' || oper == 0)
				sign = POS;
			else if(oper == '-') 
				sign = NEG;
			else {
				puts(WRONG_NUMBER_SIGN_STRING);
				exit(WRONG_NUMBER_SIGN);
			}

			// printf("%d", sign);
			a = createBigNum(list, sign);
		} else {
			switch(c) {
				case '+':
					//sum
					break;
				case '-':
					break;
				default:
					break;
			}
		}
	}
	while(c != QUIT);

	printBigNum(a);

	return 0;
}

//returns 1 if digit and 0 if not
int isDigit(char ch) {
	return 1 ? (ch <= '9' && ch >= '0') : 0;
}
