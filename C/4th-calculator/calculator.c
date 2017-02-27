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
BigNum* mulInt(BigNum *a, int b);
BigNum* divide(BigNum *a, BigNum *b);
BigNum* divPos(BigNum *a, BigNum *b);
char cmpBigNumAbs(BigNum *a, BigNum *b);
void readInput(Stack *stack);
BigNum* readBigNum(char ch);
int howMany(BigNum *one, BigNum* another);

//TODO: split into a few files
int main() {
	Stack *stack = createNumStack();
	readInput(stack);

	return 0;
}

BigNum* readBigNum(char ch) {
	LinkedList* list = createLinkedList();
	do {
		pushFront(list, ch-'0');
		ch = getchar();
	} while(isDigit(ch));

	BigNum* result = createBigNum(list, POS);
	return result;
}

void readInput(Stack *stack) {
	char ch;
	BigNum *a, *b, *res;
	int finished = 0;
	do {
		ch = getchar();
		if(isDigit(ch)) {
			stack_push(stack, readBigNum(ch));
		} else if(ch == '=') {
			BigNum* num = stack_peek(stack);
			if(num != NULL)
				printBigNum(stack_peek(stack));
		} else if(ch == 'q') {
			finished = 1;
		} else if(ch == ' ' || ch == '\n') {
 			continue;
		} else {
			//operations
			switch(ch) {
				case '+':
					if(stack->size < 2) {
						puts("Unable to summarize: empty stack");
						continue;
					}
					b = stack_pop(stack);
					a = stack_pop(stack);
					stack_push(stack, sum(a,b));
					break;
				case '-':
					ch = getchar();
					if(ch == '\n' || ch == ' ') {
						if(stack->size < 2) {
							puts("Unable to subtract: empty stack");
							continue;
						}
						b = stack_pop(stack);
						a = stack_pop(stack);
						res = sub(a, b);
						stack_push(stack, res);
					} else if(isDigit(ch)) {
						BigNum* num = readBigNum(ch);
						num->sign = NEG;
						stack_push(stack, num);
					} else {
						printf("Unexpected symbol '%c' after '-'.\n", ch);
					}
					break;
				case '*':
					if(stack->size < 2) {
						puts("Unable to multiply: empty stack");
						continue;
					}
					b = stack_pop(stack);
					a = stack_pop(stack);
					res = mul(a, b);
					stack_push(stack, res);
					break;
				case '/':
					if(stack->size < 2) {
						puts("Unable to divide: empty stack");
						continue;
					}
					b = stack_pop(stack);
					a = stack_pop(stack);
					res = divide(a, b);
					stack_push(stack, res);
					break;
				default:
					//check if not any of the ones above
					break;
			}
		}
	} while(!finished);
	deleteNumStack(stack);
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

	normalize(a, 1);
	
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

BigNum* subPos(BigNum *a, BigNum *b) { 
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

	normalize(diff, 1);
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


//yes, I do know this is very clumsy way of doing it
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
		normalize(res, 0);
	}

	normalize(res, 1);

	deleteBigNum(&a);
	deleteBigNum(&b);

	return res;

}

BigNum* mulInt(BigNum *a, int b) {
	BigNum *res = createBigNumEmpty();
	int sign;
	res->sign = a->sign;
	if(b < 0) {
		if(a->sign == POS) 
			res->sign = NEG;
		else 
			res->sign = POS;
		b *= -1;
	} else if(b == 0) {
		putDigit(res, 0);
		return res;
	}

	Node *tmp = a->digs->tail;
	while(tmp != NULL) {
		pushFront(res->digs, (tmp->value)*b);
		tmp = tmp->prev;
	}
	normalize(res, 1);
	return res;
}

BigNum* divide(BigNum *a, BigNum *b) {
	BigNum *res;
	int sign = POS;

	if(a->sign == NEG) {
		if(b->sign == POS)
			sign = NEG; 
	} else {
		if(b->sign == NEG)
			sign == NEG;
	}

	if(isZero(b)) {
		printf("Error: division by zero.");
		exit(1);
	}

	res = divPos(a, b);
	res->sign = sign;
	return res;
}

BigNum* divPos(BigNum *a, BigNum *b) {
	LinkedList *list = createLinkedList();
	BigNum *res, *temp;
	Node *cur;

	if(cmpBigNumAbs(a, b) < 0) {
		pushFront(list, 0);
		res = createBigNum(list, POS);
		return res;
	}	

	temp = createBigNumEmpty();
	res = createBigNumEmpty();
	cur = a->digs->tail;
	pushFront(temp->digs, cur->value);

	while(cmpBigNumAbs(temp, b) < 0) {
		cur = cur->prev;
		//cur != null cz a>=b
		pushFront(temp->digs, cur->value);
	}

	do {
		int count = howMany(b, temp);
		pushFront(res->digs, count);
		temp = subPos(temp, mulInt(b, count));
		cur = cur->prev;
		if(cur != NULL) {
			pushFront(temp->digs, cur->value);
		}

	} while(cur != NULL);

	deleteBigNum(&temp);
	deleteBigNum(&a);
	deleteBigNum(&b);
	normalize(res, 1);
	return res;
}

//how many ones in another
int howMany(BigNum *one, BigNum* another) {
	int count;
	for (count = 0; count < 10; ++count)
	{
		if((cmpBigNumAbs(mulInt(one, count), another) <= 0) && (cmpBigNumAbs(mulInt(one, count+1), another) > 0))
			return count;
	}
}

char maxChar(char a, char b) {
	return a ? a > b : b;
}