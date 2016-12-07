#include <stdio.h>
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