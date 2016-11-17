//возвращает -1 когда arr < to, 0 при arr == to и 1 при arr > to
int compareStrings(char *arr, char *to) { 
	int i;
	if(arr == to) 
		return 0;
	 while((i = *to - *arr) == 0) {
	 	if(*arr == 0) 
	 		if(*to == 0)
	 			return 0;
	 		else return -1;
	 	else if(*to == 0)
	 		return 1;
	 	arr++;
	 	to++;
	 }
	 if(i<0) 
	 	return 1;
	 else if(i>0)
	 	return -1;
	 else return 0;
}