#include <stdlib.h>
#include <stdio.h>
// #include "big_num.c"
#include "stack.c"
#define QUIT '='
#define BASE 10
#define POS 1
#define NEG 2
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
/*void normalize(BigNum* a);
void deleteZeros(BigNum *a);*/

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
				//FIXME: get rid of copypaste
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
					a = stack_pop(stack);
					res = sum(a, b);
					stack_push(stack, res);
					deleteBigNum(&a);
					deleteBigNum(&b);
					break;
				case '-':
					b = stack_pop(stack);
					a = stack_pop(stack);
					res = sub(a, b);
					stack_push(stack, res);
					deleteBigNum(&a);
					deleteBigNum(&b);
					break;
				default:
					break;
			}
		}
		if(oper != 0 && list == NULL) 
			lastOper = oper;
	}
	while(c != QUIT);

	printBigNum(stack_peek(stack));

	deleteLinkedList(&list);
	deleteBigNum(&a);
	deleteBigNum(&b);
	deleteBigNum(&res);
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
				sum->digs->tail->value += (tempSum - BASE);
				putDigit(sum, 1);
			} else {
				sum->digs->tail->value += tempSum;
			}
		} else {
			if(tempSum > 9) {
				putDigit(sum, tempSum - BASE);
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
	return diff;
}

BigNum* subPos(BigNum *a, BigNum *b) { //TODO: check exceptions like nulls
	BigNum* diff = createBigNumEmpty();
	char sign;
	Node *aDig, *bDig;
	if(cmpBigNumAbs(a,b) == 1) {
		aDig = a->digs->head;
		bDig = b->digs->head;
		sign = POS;
	} else {
		aDig = b->digs->head;
		bDig = a->digs->head;
		sign = NEG;
	}

	char tempDif = 0;
	size_t index = 0;
	while(aDig != NULL || bDig != NULL) {
		tempDif = 0;
		if(aDig != NULL) 
			tempDif += aDig->value;
		if(bDig != NULL) 
			tempDif -= bDig->value;

		//if we have 1 in current rank from last operation
		if(index == diff->digs->size - 1) { 
			tempDif += diff->digs->tail->value;
			if(tempDif < 0) {
				diff->digs->tail->value = (tempDif + BASE);
				putDigit(diff, -1);
			} else {
				diff->digs->tail->value = tempDif;
			}
		} else {
			if(tempDif < 0) {
				putDigit(diff, tempDif + BASE); 
				putDigit(diff, -1);
			} else {
				putDigit(diff, tempDif);
			}
		}
		
		if(aDig != NULL)
			aDig = aDig->next;
		if(bDig != NULL)
			bDig = bDig->next;
		index++;
	}

	diff->sign = sign;
	deleteZeros(diff);
	
	return diff;
}

BigNum* mul(BigNum *a, BigNum *b) {

	return mulPos(a, b);
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
	Node *aWhile, *bWhile; //nodes in res, so we don't have to use getByIndex(aI+bI);
	while(aNode != NULL) {
		bI = 0;
		bWhile = aWhile;
		while(bNode != NULL) {
			if(res->digs->size > aI + bI) {
				// res->digs->
				// bWhile = 
			} else {
				putDigit(res, (aNode->value * (bNode->value)));
				bWhile = res->digs->tail;
			}

			bNode = bNode->next;
			bI++;
		}
		if(aI == 0)
			aWhile = res->digs->head;
		aNode = aNode->next;
		aI++;
	}
	return res;

}


BigNum* karatsuba(BigNum *a, BigNum *b) {
	BigNum* res = createBigNumEmpty();
	BigNum* a0 = createBigNumEmpty();
	BigNum* a1 = createBigNumEmpty();
	BigNum* b0 = createBigNumEmpty();
	BigNum* b1 = createBigNumEmpty();



}

char maxChar(char a, char b) {
	return a ? a > b : b;
}

//returns 1 if a>b, 0 if a==b and -1 if a<b
char cmpBigNumAbs(BigNum* a, BigNum *b) {
	char diff = 0; 
	if(a->digs->size > b->digs->size)
		return 1;
	if(a->digs->size < b->digs->size)
		return -1;
	Node *aNode = a->digs->tail;
	Node *bNode = b->digs->tail;
	while(aNode != NULL && bNode != NULL) {
		if(aNode->value > bNode->value) 
			return 1;
		if(aNode->value < bNode->value)
			return -1;
		aNode = aNode->prev;
		bNode = bNode->prev;
	}
	return 0;
}