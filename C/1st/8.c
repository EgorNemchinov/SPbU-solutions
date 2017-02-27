#include <stdio.h>
#include <string.h>

int main() {
	
	char string[] = "ololakinalolhlol";
	char substring[] = "lol";

	int c = 0;
	char* p = strstr(string, substring);
	c = p;

	printf("%d\n", c);
}