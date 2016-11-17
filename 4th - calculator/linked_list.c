#include <stdlib.h>

typedef struct _Node Node;
struct _Node {
	char *value;
	Node *next;
   	Node *prev;
};

typedef struct LinkedList {
	size_t size;
	Node *head;
	Node *tail;
} LinkedList;

LinkedList* createLinkedList() {
	LinkedList *tmp = (LinkedList*) malloc(sizeof(LinkedList));
	tmp->size = 0;
	tmp->head = tmp->tail = NULL;

	return tmp;
}

void deleteLinkedList(LinkedList **list) {//why **
	Node *tmp = (*list)->head;
	Node *next = NULL;
	while(tmp) {
		next = tmp->next;
		free(tmp);
		tmp = next;
	}
	free(*list);
	(*list) = NULL;
}

void pushFront(LinkedList *list, char *data) {
	Node *tmp = (Node*) malloc(sizeof(Node));
	if(tmp == NULL) {
		//means no memory allocated?
		exit(1);
	}
	tmp->value = data;
	tmp->next = list->head;
	tmp->prev = NULL;

	if(list->head) {
		list->head->prev = tmp;
	}
	list->head = tmp;

	if(list->tail == NULL) {
		//tmp - first element in the list
		list->tail = tmp;
	}
	list->size++;
}

char* popFront(LinkedList *list) {
	Node *prevHead = list->head;
	char *tmp;
	if(list->head == NULL) {
		exit(2);
	}
    list->head = list->head->next;
    //FIXME: what if it's NULL?
    if(list->head) {
    	list->head->prev = NULL;
    }
    if (prevHead == list->tail) {
        list->tail = NULL;
    }
	tmp = prevHead->value;
	free(prevHead);

	list->size--;
	return tmp;
}

void pushBack(LinkedList *list, char *data) {
	Node *tmp = (Node*) malloc(sizeof(Node));
	if(tmp == NULL) {
		//means no memory allocated?
		exit(3);
	}
	tmp->value = data;
	tmp->prev = list->tail;
	tmp->next = NULL;

	if(list->tail) {
		list->tail->next = tmp;
	}
	list->tail = tmp;

	if(list->head == NULL) {
		//tmp - first element in the list
		list->head = tmp;
	}
	list->size++;
}

char* popBack(LinkedList *list) {
	Node *prevTail= list->tail;
	char *tmp;
	if(list->tail == NULL) {
		exit(4);
	}
    list->tail = list->tail->prev;
    //FIXME: what if it's NULL?
    if(list->tail) {
    	list->tail->next = NULL;
    }
    if (prevTail == list->head) {
        list->head = NULL;
    }
	tmp = prevTail->value;
	free(prevTail);

	list->size--;
	return tmp;
}

void printLinkedList(LinkedList *list) {
	Node *tmp = list->head;
	while(tmp) {
		printf("%d ", *tmp->value);
		tmp = tmp->next;
	}
	printf("\n");
}

//we are not gonna need this, just added for fun
Node* getByIndex(LinkedList *list, size_t index) {
	Node *tmp = NULL;
	size_t i;

	//choosing which way to go: from head or from tail
	if(index < list->size / 2) {
		i = 0;
		tmp = list->head;
		while(tmp && i < index) {
			tmp = tmp->next;
			i++;
		}
	} else {
		i = list->size - 1;
		tmp = list->tail;
		while(tmp && i > index) {
			tmp = tmp->prev;
			i--;
		}
	}
}