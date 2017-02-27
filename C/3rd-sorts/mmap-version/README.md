GPROF:

	BUBBLE SORT:
		Each sample counts as 0.01 seconds.
		  %   cumulative   self              self     total           
		 time   seconds   seconds    calls  ns/call  ns/call  name    
		 36.34      0.75     0.75                             printString
		 34.14      1.45     0.70 49995000    14.07    14.07  compareMappedStrings
		 27.80      2.02     0.57                             readInput
		  1.22      2.05     0.03 24770626     1.01     1.01  swap
		  0.49      2.06     0.01                             bubbleSort
		  0.49      2.07     0.01                             compareStrings

	QUICK SORT:
		Each sample counts as 0.01 seconds.
		  %   cumulative   self              self     total           
		 time   seconds   seconds    calls  ns/call  ns/call  name    
		 52.32      0.63     0.63                             printString
		 47.34      1.21     0.57                             readInput
		  0.83      1.22     0.01   225593    44.54    44.54  compareMappedStrings
		  0.00      1.22     0.00    30212     0.00     0.00  swap

	INSERTION SORT:
		Each sample counts as 0.01 seconds.
		  %   cumulative   self              self     total           
		 time   seconds   seconds    calls  ns/call  ns/call  name    
		 42.24      0.96     0.96                             readInput
		 28.16      1.61     0.64 24780615    25.91    25.91  compareMappedStrings
		 28.16      2.25     0.64                             printString
		  1.76      2.29     0.04                             insertionSort

	MERGE SORT:
		Each sample counts as 0.01 seconds.
		  %   cumulative   self              self     total           
		 time   seconds   seconds    calls  us/call  us/call  name    
		 53.00      0.84     0.84                             printString
		 46.69      1.59     0.74                             readInput
		  0.63      1.60     0.01   120434     0.08     0.08  compareMappedStrings
		  0.00      1.60     0.00     9998     0.00     1.00  merge

VALLGRIND:
	HEAP SUMMARY:
		in use at exit: 0 bytes in 0 blocks
		total heap usage: 2 allocs, 2 frees, 81,024 bytes allocated

		All heap blocks were freed -- no leaks are possible