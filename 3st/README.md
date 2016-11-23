For generating file "input" with N lines:
./generate N input

For executing sort:
./%sort_name% N input

GPROF:

	BUBBLE SORT:
		Each sample counts as 0.01 seconds.
		  %   cumulative   self              self     total           
		 time   seconds   seconds    calls  ms/call  ms/call  name    
		100.47      0.35     0.35        1   351.64   351.64  readInput
		  0.00      0.35     0.00   499500     0.00     0.00  compareStrings
		  0.00      0.35     0.00   251300     0.00     0.00  swap
		  0.00      0.35     0.00        1     0.00     0.00  bubbleSort

	 QUICK SORT:
		  %   cumulative   self              self     total           
		 time   seconds   seconds    calls  ms/call  ms/call  name    
		100.43      0.28     0.28        1   281.19   281.19  readInput
		  0.00      0.28     0.00    15398     0.00     0.00  compareStrings
		  0.00      0.28     0.00     2336     0.00     0.00  swap
		  0.00      0.28     0.00        1     0.00     0.00  quickSort

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

