#ifndef TOOLS
#define TOOLS

void readInput(FILE * fin, char ** arr, int n);
void swap(char **arr, int a, int b);
//возвращает -1 когда arr < to, 0 при arr == to и 1 при arr > to
int compareStrings(char *arr, char *to);
#endif