For generating file "input" with N lines:
./generate N input

For executing sort:
./%sort_name% ARG_1 N input

GPROF:

	BUBBLE SORT:
		Each sample counts as 0.01 seconds.
		  %   cumulative   self              self     total           
		 time   seconds   seconds    calls  ns/call  ns/call  name    
		 59.52      1.78     1.78 49995000    35.60    35.60  compareStrings
		 40.02      2.98     1.20                             readInput
		  0.67      3.00     0.02                             bubbleSort
		  0.34      3.01     0.01 24770626     0.41     0.41  swap

	 QUICK SORT:
		  %   cumulative   self              self     total           
		 time   seconds   seconds    calls  ns/call  ns/call  name    
		 99.63      1.09     1.09                             readInput
		  0.92      1.10     0.01    30212   332.82   332.82  swap
		  0.00      1.10     0.00   225593     0.00     0.00  compareStrings

	INSERTION SORT:
		  %   cumulative   self              self     total           
		 time   seconds   seconds    calls  ns/call  ns/call  name    
		 74.65      1.81     1.81                             readInput
		 24.47      2.40     0.59 24780615    23.89    23.89  compareStrings
		  1.24      2.43     0.03                             insertionSort

	MERGE SORT:
		Each sample counts as 0.01 seconds.
		  %   cumulative   self              self     total           
		 time   seconds   seconds    calls  us/call  us/call  name    
		 99.81      1.83     1.83                             readInput
		  0.55      1.84     0.01   120434     0.08     0.08  compareStrings
		  0.00      1.84     0.00     9998     0.00     1.00  merge

VALLGRIND:
	HEAP SUMMARY:
		in use at exit: 0 bytes in 0 blocks
    	total heap usage: 10,004 allocs, 10,004 frees, 502,951,468 bytes allocated
     
    	All heap blocks were freed -- no leaks are possible