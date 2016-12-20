#include <stdlib.h>
#include <stdio.h>
#include "linked_list.h"
#include "big_num.h"
#include "stack.h"
#define QUIT '='
#define BASE 10
#define POS 0
#define NEG 1
#define INVALID_SYMBOL_STRING "Invalid symbol was entered."
#define MANY_OPERATORS_STRING "Too many operators."
#define WRONG_NUMBER_SIGN_STRING "Wrong number's sign was entered."

int isDigit(char ch);
BigNum* sum(BigNum *a, BigNum *b);
BigNum* sumPos(BigNum* a, BigNum *b);
BigNum* sub(BigNum* a, BigNum *b);
BigNum* subPos(BigNum* a, BigNum *b);
BigNum* mul(BigNum *a, BigNum *b);
BigNum* mulPos(BigNum *a, BigNum *b);
char cmpBigNumAbs(BigNum *a, BigNum *b);

//TODO: split into a few files
//TODO: make operation change first given BigNum and not create new one
//TODO: implement stack and operations on it, free temporary BigNums
int main() {
	BigNum *a = NULL, *b = NULL, *res = NULL;
	Stack *stack = createNumStack();

	//TODO: get input into another function
	char c;
	char lastOper = 0;
	LinkedList *list;
	do {
		list = NULL;
		char oper = 0, sign = 0;
		//reading string
		do {
			c = getchar();
			if(c == QUIT) {
				quit = 1;
				
			} 
			if(c == '+' || c == '-' || c == '*' || c == '/') {
				if(oper != 0) {
					puts(MANY_OPERATORS_STRING); 
					exit(1);
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
				exit(1);
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
				exit(1);
			}

			stack_push(stack, createBigNum(list, sign));
		} else {
			switch(oper) {
				case '+':
					b = stack_pop(stack);
					a = stack_peek(stack);
					sum(a, b);
					break;
				case '-':
					b = stack_pop(stack);
					a = stack_pop(stack);
					res = sub(a, b);
					printBigNum(res);
					stack_push(stack, res);
					break;
				case '*':
					b = stack_pop(stack);
					a = stack_pop(stack);
					res = mul(a, b);
					stack_push(stack, res);
				default:
					break;
			}
		}
		if(oper != 0 && list == NULL) 
			lastOper = oper;
	}
	while(!quit);

	printNumStack(stack);

	printBigNum(stack_peek(stack));
	deleteLinkedList(&list);
	// deleteBigNum(&a);
	// deleteBigNum(&b);
	// deleteBigNum(&res);
	deleteNumStack(&stack);
	return 0;
}

//returns 1 if digit and 0 if not
int isDigit(char ch) {
	return 1 ? (ch <= '9' && ch >= '0') : 0;
}

BigNum* sum(BigNum* a, BigNum *b) {
	BigNum* diff;
	if(a->sign == NEG) {
		if(b->sign == NEG) { //-A + -B = - (A + B)
			diff = sumPos(a, b);
			diff->sign = NEG;
		} else { // -A + B = B - A
			diff = subPos(b, a);
		}
	} else if (b->sign == NEG) { //A + (-B) == A - B
		diff = subPos(a,b);
	} else { //A + B
		diff = sumPos(a, b);
	}
	return diff;
}

BigNum* sumPos(BigNum* a, BigNum *b) {
	BigNum* sum = createBigNumEmpty();
	Node *aDig = a->digs->head;
	Node *bDig = b->digs->head;

	while(aDig != NULL && bDig != NULL) {
		aDig->value += bDig->value;
		aDig = aDig->next;
		bDig = bDig->next;
	}
	while(bDig != NULL) {
		putDigit(a, bDig->value);
	}

	normalize(a);
	
	deleteBigNum(&b);

	if(a->digs->size == 0) 
		putDigit(a, 0); //FIXME: is it correct? when is size 0
	return a;
}

//a - b
BigNum* sub(BigNum *a, BigNum *b) {
	BigNum* diff;
	if(a->sign == NEG) {
		if(b->sign == NEG) { //-A - (-B) == B - A
			diff = subPos(b, a);
		} else { // -A - B = - (B + A)
			diff = sumPos(a, b);
			diff->sign = NEG;
		}
	} else if (b->sign == NEG) { //A - (-B) == A + B
		diff = sumPos(a,b);
	} else { //A - B
		diff = subPos(a, b);
	}
	printBigNum(diff);
	return diff;
}

//TODO: subtract second from first!
BigNum* subPos(BigNum *a, BigNum *b) { //TODO: check exceptions like nulls
	BigNum* diff;
	char sign;
	Node *aDig, *bDig;
	if(cmpBigNumAbs(a,b) == 1) {
		aDig = a->digs->head;
		bDig = b->digs->head;
		diff = a;
		sign = POS;
	} else {
		aDig = b->digs->head;
		bDig = a->digs->head;
		diff = b;
		sign = NEG;
	}

	while(aDig != NULL && bDig != NULL) {
		aDig->value -= bDig->value;

		if(aDig != NULL)
			aDig = aDig->next;
		if(bDig != NULL)
			bDig = bDig->next;
	}

	normalize(diff);
	diff->sign = sign;

	if(diff == b)
		deleteBigNum(&a);
	else
		deleteBigNum(&b);
	
	return diff;
}

BigNum* mul(BigNum *a, BigNum *b) {
	BigNum* result;
	if(a->sign == NEG) {
		if(b->sign == NEG) {
			result = mulPos(a, b);
		} else {
			result = mulPos(a, b);
			result->sign = NEG;
		}
	} else {
		if(b->sign == NEG) {
			result = mulPos(a, b);
			result->sign = NEG;	
		} else {
			result = mulPos(a, b);
		}
	}

	return result;
}


BigNum* mulPos(BigNum *a, BigNum *b) {
	BigNum* res = createBigNumEmpty();
	Node *aNode, *bNode;

	//aNode is from the bigger number
	if(cmpBigNumAbs(a, b) >= 0) {
		aNode = a->digs->head;
		bNode = b->digs->head;
	} else {
		aNode = b->digs->head;
		bNode = a->digs->head;
	}

	//multiplication by 0
	if(a->digs->size <= 1) {
		if(a->digs->head == NULL || a->digs->head == 0){
			putDigit(res, 0);
			return res;
		}
	} 
	if(b->digs->size <= 1) {
		if(b->digs->head == NULL || b->digs->head == 0){
			putDigit(res, 0);
			return res;
		}
	} 

	char aI = 0, bI = 0;
	Node *aNodeCur = aNode, *bNodeCur = bNode; 
	//TODO: keep current nodes, so we don't have to use getByIndex(aI+bI);
	while(aNodeCur != NULL) {
		bI = 0;
		while(bNodeCur != NULL) {
			char charMulResult = (aNodeCur->value)*(bNodeCur->value);
			if(res->digs->size > aI + bI) {
				//add to existing. getByIndex takes a lot of time
				getByIndex(res->digs, aI + bI)->value += charMulResult;
			} else {
				putDigit(res, charMulResult);
			}

			bNodeCur = bNodeCur->next;
			bI++;
		}

		bNodeCur = bNode;
		aNodeCur = aNodeCur->next;
		aI++;
		normalize(res);
	}

	normalize(res);

	deleteBigNum(&a);
	deleteBigNum(&b);
	
	return res;

}

char maxChar(char a, char b) {
	return a ? a > b : b;
}