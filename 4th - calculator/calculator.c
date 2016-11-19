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
BigNum* sum(BigNum *a, BigNum *b);

int main() {
	BigNum *a = NULL, *b = NULL;

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
				pushFront(list, (c - '0'));
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
			if(a == NULL) 
				a = createBigNum(list, sign);
			else
				b = createBigNum(list, sign);
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
	printBigNum(b);
	if(a != NULL && b != NULL) 
		printBigNum(sum(a,b));
	return 0;
}

//returns 1 if digit and 0 if not
int isDigit(char ch) {
	return 1 ? (ch <= '9' && ch >= '0') : 0;
}

BigNum* sum(BigNum* a, BigNum *b) {
	BigNum* sum = createBigNumEmpty();
	Node *aDig = a->digs->head;
	Node *bDig = b->digs->head;

	//add subtraction when one < 0
	char tempSum = 0;
	size_t index = 0;
	while(aDig != NULL || bDig != NULL) {
		tempSum = 0;
		if(aDig != NULL) 
			tempSum += aDig->value;
		if(bDig != NULL) 
			tempSum += bDig->value;

		//if we have 1 in current rank from last operation
		if(index == sum->digs->size - 1) { 
			if(tempSum > 9 - sum->digs->tail->value) {
				sum->digs->tail->value += (tempSum - 10);
				putDigit(sum, 1);
			} else {
				sum->digs->tail->value += tempSum;
			}
		} else {
			if(tempSum > 9) {
				putDigit(sum, tempSum - 10);
				putDigit(sum, 1);
			} else {
				putDigit(sum, tempSum);
			}
		}
		
		
		if(aDig != NULL)
			aDig = aDig->next;
		if(bDig != NULL)
			bDig = bDig->next;
		index++;
		
	}
	
	if(sum->digs->size == 0) 
		putDigit(sum, 0); //FIXME: is it correct? when is size 0
	return sum;
}

char maxChar(char a, char b) {
	return a ? a > b : b;
}