#ifndef LINKEDLIST
#define LINKEDLIST

typedef struct _Node Node;

struct _Node {
	char value; 
	Node *next;
   	Node *prev;
};

typedef struct LinkedList {
	size_t size;
	Node *head;
	Node *tail;
} LinkedList;

LinkedList* createLinkedList();
void deleteLinkedList(LinkedList **list);
void pushFront(LinkedList *list, char data);
char popFront(LinkedList *list);
void pushBack(LinkedList *list, char data);
char popBack(LinkedList *list);
void printLinkedList(LinkedList *list);
Node* getByIndex(LinkedList *list, size_t index);
#endif