#include "linked_list.c"
#include <stdlib.h>
#define POS 1
#define NEG -1

typedef struct _BigNum BigNum; //_BigNum?
struct _BigNum {
	LinkedList *digs;
	char sign; //or a pointer? i guess with char better this one
};


BigNum* createBigNum(LinkedList *list, char sign) {
	BigNum* num = (BigNum*) malloc(sizeof(BigNum));
	num->sign = sign;
}

//FIXME:don't know if we'll need this one
/*BigNum* createBigNum() {
	LinkedList *list = createLinkedList();
	//put 0?
	return createBigNum(list, POS);
}*/

//less important digits go in the beginning (abc->cba)
void put(BigNum* num, char dig) {
	pushFront(num->digs, &dig);
}

//without spaces unlike printLinkedList()
void printBigNum(BigNum* num) {
	Node *tmp = num->digs->head;
	if(num->sign == NEG) 
		putchar('-');
	while(tmp) {
		putchar(*tmp->value);
		tmp = tmp->next;
	}
	printf("\n");
}