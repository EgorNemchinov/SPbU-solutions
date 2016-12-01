For generating file "input" with N lines:
./generate N input

For executing sort:
./%sort_name% N input

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

VALLGRIND:

	BUBBLE SORT:
		HEAP SUMMARY:
	    	in use at exit: 0 bytes in 0 blocks
	    	total heap usage: 1,005 allocs, 1,005 frees, 50,932,904 bytes allocated

	    	All heap blocks were freed -- no leaks are possible

	QUICKSORT:
		HEAP SUMMARY:
		   in use at exit: 0 bytes in 0 blocks
		   total heap usage: 1,005 allocs, 1,005 frees, 50,933,200 bytes allocated
		 
		 All heap blocks were freed -- no leaks are possible

