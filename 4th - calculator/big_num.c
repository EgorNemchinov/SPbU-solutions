#include "linked_list.с"
#include <stdlib.h>
#include <stdio.h>
#define POS 1
#define NEG 2
#define LIST_NULL 11
#define INVALID_SIGN 12
#define INVALID_SYMBOL_STRING "Invalid symbol was entered."
#define MANY_OPERATORS_STRING "Too many operators."
#define WRONG_NUMBER_SIGN_STRING "Wrong number's sign was entered."
#define LIST_NULL_STRING "Passed LinkedList pointer is null."

typedef struct _BigNum BigNum; //_BigNum?
struct _BigNum {
	LinkedList *digs;
	char sign; //or a pointer? i guess with char better this one
};


BigNum* createBigNum(LinkedList *list, char sign) {
	if(sign != POS && sign != NEG) {
		puts(WRONG_NUMBER_SIGN_STRING);
		exit(INVALID_SIGN);
	} 
	if(list == NULL){
		puts("LIST_NULL\n");
		exit(LIST_NULL);
	}

	if(list->size == 0)
		pushFront(list, '0');

	BigNum* num = (BigNum*) malloc(sizeof(BigNum));
	num->sign = sign;
	num->digs = list;
	return num;
}

//less important digits go in the beginning (abc->cba)
void put(BigNum* num, char dig) {
	pushFront(num->digs, dig);
}

//without spaces unlike printLinkedList()
void printBigNum(BigNum* num) {
	if(num == NULL) {
		puts("BigNum is NULL.\n");
		return;
	}
	if(num->sign != POS && num->sign != NEG) {
		puts(WRONG_NUMBER_SIGN_STRING);
		exit(INVALID_SIGN);
	} 
	if(num->digs == NULL) {
		puts(LIST_NULL_STRING);
		exit(LIST_NULL);
	}

	Node *tmp = num->digs->tail;
	if(num->sign == NEG) 
		putchar('-');
	while(tmp) {
		putchar(tmp->value);
		tmp = tmp->prev;
	}
	printf("\n");
}