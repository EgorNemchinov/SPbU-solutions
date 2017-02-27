#ifndef TOOLS
#define TOOLS

void readInput(int fin, char ** arr, int amount, size_t *size);
void printString(char *str, char *last);
void swap(char **arr, int a, int b);
//возвращает -1 когда arr < to, 0 при arr == to и 1 при arr > to
int compareStrings(const char *strings, const char *to);
int compareMappedStrings(const char *strings, const char *to);

#endif