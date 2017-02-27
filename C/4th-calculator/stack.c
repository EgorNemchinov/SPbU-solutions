#include <stdlib.h>
#include <stdio.h>
#include "big_num.h"
#include "stack.h"

Stack* createNumStack() {
	Stack *stack = (Stack*) malloc(sizeof(Stack));
	stack->size = 0;
	stack->last = NULL;

	return stack;
}

void deleteNumStack(Stack *stack) {
	if(stack == NULL) 
		return;
	NumNode *tmp = (stack)->last;
	NumNode *prev = NULL;

	while(tmp != NULL) {
		prev = tmp->prev;
		deleteBigNum(&(tmp->num)); //FIXME: are there exceptions?
		free(tmp);
		tmp = prev;
		stack->size--;
	}
	free(stack);
	// (*stack) = NULL; 
}

void stack_push(Stack *stack, BigNum *number) {
	normalize(number, 1);
	NumNode *tmp = (NumNode*) malloc(sizeof(NumNode));
	if(tmp == NULL) {
		exit(1);
	}
	tmp->num = number;
	tmp->prev = stack->last;

	stack->last = tmp;
	stack->size++;
}

BigNum* stack_pop(Stack *stack) {
	if(stack == NULL) {
		printf("Empty reference to stack.\n");
		exit(1);
	}

	NumNode *last = stack->last;
	BigNum *tmp;

	if(last == NULL) {
		printf("Stack underflow.\n");
		exit(1);
	}
	stack->last = last->prev;

	tmp = last->num;
	free(last);

	//TODO: remember to free BigNum after use
	stack->size--;
	return tmp;
}

BigNum* stack_peek(Stack *stack) {
	if(stack == NULL) {
		printf("Empty reference to stack.\n");
		exit(1);
	}
	if(stack->size == 0) {
		printf("Stack underflow.\n");
		exit(1);
	}
	//"if" above contains case if stack->last is NULL
	BigNum *tmp = stack->last->num;
	return tmp;
}

void printNumStack(Stack *stack) {
	NumNode *tmp = stack->last;
	size_t index = stack->size - 1;

	while(tmp != NULL) {
		printf("%zu:", index);
		printBigNum(tmp->num);
		index--;
		tmp = tmp->prev;
	}
}

BigNum* getFromEnd(Stack *stack, int index) {
	NumNode *tmp = stack->last;

	if(stack == NULL) {
		printf("Empty reference to stack.\n");
		exit(1);
	}
	if(index > stack->size - 1) {
		printf("Requested element with index %d. Stack size is %zu", index, stack->size);
		exit(1);
	}
	while(index > 0) {
		tmp = tmp->prev;
		index--;
	}
	return tmp->num;
}
