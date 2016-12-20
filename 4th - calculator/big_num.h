#ifndef BIGNUMB
#define BIGNUMB
#include "linked_list.h"

typedef struct _BigNum BigNum; 

struct _BigNum {
	LinkedList *digs;
	unsigned int sign:1; 
};

BigNum* createBigNum(LinkedList *list, char sign);
BigNum* createBigNumEmpty();
void putDigit(BigNum* num, char dig);
void printBigNum(BigNum* num);
void deleteBigNum(BigNum **num);
void deleteZeros(BigNum *a);
void normalize(BigNum* a, char delZeros);
char cmpBigNumAbs(BigNum* a, BigNum *b);
char isZero(BigNum *num);
#endif