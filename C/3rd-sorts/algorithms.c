#include <stdio.h>
#include <malloc.h>
#include "tools.h"
#include "algorithms.h"

void quickSort(char **strings, int first, int last){
	int left = first, right = last;
	char  *middle = strings[(first + last) / 2];

	do
	{
		while (left <= last && compareStrings(strings[left], middle) < 0) left++;
		while (right >= first && compareStrings(strings[right], middle) > 0) right--;
		
		if(left <= right) {
			if(compareStrings(strings[left], strings[right]) > 0) {
				swap(strings, left, right);
			} 
			left++;
			right--;
		}

	} while (left <= right);

	if(right > first) quickSort(strings, first, right);
	if(left < last) quickSort(strings, left, last);
}

void bubbleSort(char ** strings, int amount) {
	int i, j, swapped;
	
	for (i = 0; i < amount - 1; i++)
	{
		swapped = 0;
		for (j = 0; j < amount - i - 1; j++) {
		
			if(compareStrings(strings[j], strings[j + 1]) == 1) {
				swap(strings, j, j+1);
				swapped = 1;
			}
		}
		if(swapped == 0) 
			continue;
	}
}

void insertionSort(char **strings, int amount) {
	char  *temp;
	int i, j;

	for (i = 1; i < amount; ++i)
	{
		temp = strings[i];
		j = i - 1;

		while(j >= 0 && (compareStrings(temp, strings[j]) < 0)) {
			strings[j+1] = strings[j];
			--j;
		}	
		strings[j+1] = temp;
	}
}

void mergeSort(char **strings, int first, int last){
	int middle = (first + last) / 2;

	if(first < last) {
		mergeSort(strings, first, middle);
		mergeSort(strings, middle + 1, last);
		merge(strings, first, middle, last);
	}
}

void merge(char **strings, int left, int middle, int right) {
	int i, j, k;
	int n1 = middle - left + 1;
	int n2 = right - middle;

	char **L, **R;
	L = (char**) malloc(n1 * sizeof(char*));
	R = (char**) malloc(n2 * sizeof(char*));

	for (i = 0; i < n1; ++i)
	{
		L[i] = strings[left + i];
	}

	for (j = 0; j < n2; ++j)
	{
		R[j] = strings[middle + 1 + j];	
	}

	i = 0, j = 0, k = left;
	while(i < n1 && j < n2) {
		if(compareStrings(L[i], R[j]) <= 0) {
			strings[k] = L[i];
			i++;
		} else {
			strings[k] = R[j];
			j++;
		}
		k++;
	}

	while(i < n1) {
		strings[k] = L[i];
		i++;
		k++;
	}

	while(j < n2) {
		strings[k] = R[j];
		j++;
		k++;
	}
}