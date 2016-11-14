
int compareStrings(char *arr, char *to) { //возвращает -1 когда arr < to, 0 при arr == to и 1 при arr > to
	int i;
	 while((i = *to - *arr) == 0) {
	 	if(*arr == 0) 
	 		if(*to == 0)
	 			return 0;
	 		else return 1;
	 	else if(*to == 0)
	 		return -1;
	 	arr++;
	 	to++;
	 }
	 if(i<0) 
	 	return -1;
	 else if(i>0)
	 	return 1;
	 else return 0;
	/*while(*(arr+i) != '\0' && *(to+i) != '\0') {
		if(*(arr+i) < *(to+i)) 
			return -1;
		if(*(arr+i) > *(to+i))
			return 1;
		i++;
	} 
	if(*(arr+i) == '\0') {
			if(*(to+i) == '\0')  
				return 0; 		//строки равны
			else return -1; //первая - суффикс второй
	} else if(*(to+i) == '\0') 
			return 1; //вторая - суффикс первой*/
}