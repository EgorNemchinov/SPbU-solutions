#include <stdlib.h>
#include <stdio.h>
#include "linked_list.h"
#include "big_num.h"
#define POS 0
#define NEG 1
#define BASE 10
#define INVALID_SYMBOL_STRING "Invalid symbol was entered."
#define MANY_OPERATORS_STRING "Too many operators."
#define WRONG_NUMBER_SIGN_STRING "Wrong number's sign was entered."
#define LIST_NULL_STRING "Passed LinkedList pointer is null."

BigNum* createBigNum(LinkedList *list, char sign) {
	if(sign != POS && sign != NEG) {
		puts(WRONG_NUMBER_SIGN_STRING);
		exit(1);
	} 
	if(list == NULL){
		puts("LIST_NULL\n");
		exit(1);
	}

	/*if(list->size == 0)
		pushFront(list, 0);*/

	BigNum* num = (BigNum*) malloc(sizeof(BigNum));
	num->sign = sign;
	num->digs = list;
	return num;
}

BigNum* createBigNumEmpty() {
	return createBigNum(createLinkedList(), POS);
}

//less important digits go in the beginning (abc->cba)
void putDigit(BigNum* num, char dig) {
	pushBack(num->digs, dig);
}

//without spaces unlike printLinkedList()
void printBigNum(BigNum* num) {
	if(num == NULL) {
		puts("BigNum is NULL.\n");
		return;
	}
	if(num->sign != POS && num->sign != NEG) {
		puts(WRONG_NUMBER_SIGN_STRING);
		exit(1);
	} 
	if(num->digs == NULL) {
		puts(LIST_NULL_STRING);
		exit(1);
	}

	Node *tmp = num->digs->tail;
	if(num->sign == NEG && !isZero(num)) 
		putchar('-');
	while(tmp) {
		putchar(tmp->value+'0');
		tmp = tmp->prev;
	}
	printf("\n");
}

void deleteBigNum(BigNum **num) {
	if(num == NULL)
		return;
	if((*num) == NULL) 
		return;
	deleteLinkedList(&((*num)->digs));
	free((*num));
	(*num) = NULL;
}

void deleteZeros(BigNum *a) {
	Node *node = a->digs->tail;
	if(node == NULL)
		return;
	while(node != NULL && node != a->digs->head) {
		if(node->value != 0)
			return;
		node = node->prev;
		popBack(a->digs);
		//TODO: free mem
	}
}

void normalize(BigNum* a, char delZeros) {
	if(a == NULL) 
		return;
	if(delZeros)
		deleteZeros(a);
	Node *node = a->digs->head;
	if(node == NULL) 
		return;
	Node *next = node->next;
	char value;
	while(node != NULL) {
		value = node->value;
		// printf("Current value is %d\n", value);
		if(value >= BASE) {
			if(next == NULL) {
				putDigit(a, (value / BASE));
			} else {
				next->value += (value / BASE);
			}
			node->value = value % 10;
		} else if(value < 0) {
			//it simply must NOT be null cz we always subtract from the biggest
			if(next != NULL) {
				next->value -= (((value)*(-1)-1) / BASE) + 1;	
			}
			node->value = 10- ((value*(-1)) % 10);
		}
		node = next;
		if(node != NULL)
			next = node->next;
	}
	if(delZeros)
		deleteZeros(a);
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

char isZero(BigNum *num) {
	if(num->digs->size == 0) {
		return 1;
	} else if(num->digs->size == 1) {
		if(num->digs->head == 0)
			return 1;
	}
	return 0;
}