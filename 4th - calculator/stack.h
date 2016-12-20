#ifndef STACK
#define STACK

typedef struct _Stack_Node NumNode;

typedef struct Stack {
	size_t size;
	NumNode *last;
} Stack;

struct _Stack_Node {
	BigNum *num;
	NumNode *prev;
};

Stack * createNumStack();
void deleteNumStack(Stack **stack);
void stack_push(Stack *stack, BigNum *number);
BigNum* stack_pop(Stack *stack);
BigNum* stack_peek(Stack *stack);
void printNumStack(Stack *stack);
BigNum* getFromEnd(Stack *stack, int index);

#endif