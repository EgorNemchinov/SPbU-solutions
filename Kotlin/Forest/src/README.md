 # Analysis of performance
 
 Comparing insertion and searching in Binary search tree, Red-Black tree and BTree.
 B-Tree branching degree is 1000.
 Time displayed in milliseconds.
 
 ### Insertion:
 
 Amount of values  | Binary Search Tree | Red-Black Tree | BTree
| ------------- | ------------- | ------------- | ------------- |
| 100  | Randomized: 3.9 <br> Ordered: 10 <br> |  Randomized: 10.5 <br> Ordered: 6.3 <br> |  Randomized: 51 <br> Ordered: 0.9 <br>  |
| 10,000  |  Randomized: 50 <br> Ordered: 1677 <br> |  Randomized: 173 <br> Ordered: 160 <br> |  Randomized: 309 <br> Ordered: 26 <br> |
| 1,000,000  |  Randomized: 3156 <br> Ordered: ∞ <br>|  Randomized: 8855 <br> Ordered: 7256 <br> |  Randomized: 1690 <br> Ordered: 210 <br> |

 ###Searching:
 
 Amount of values  | Binary Search Tree | Red-Black Tree | BTree
| ------------- | ------------- | ------------- | ------------- |
| 100  | Randomized: 0.3 <br> Ordered: 0.95 <br> |  Randomized: 0.4 <br> Ordered: 4 <br>  |  Randomized: 0.09 <br> Ordered: 0.07 <br>  |
| 10,000  |  Randomized: 17 <br> Ordered: 1137 <br> |  Randomized: 32 <br> Ordered: 6 <br> |  Randomized: 18 <br> Ordered:19 <br> |
| 1,000,000  | Randomized: 1618 <br> Ordered: ∞  <br>|  Randomized: 1604 <br> Ordered: 328 <br> |  Randomized: 214 <br> Ordered: 110<br> |

So, with **evenly spread** values searching in BSTree can be **as fast as** in RBTree, however insertion is **much faster** because of lack of balancing.
Though in bad cases(like when values are sorted) binary search tree is extremely slow.

B-Tree seems to have significant advantage over other trees when amount of values is *big enough*.

Worth noticing that binary search in B-Tree node improves performance:
  * searching is **20**(for randomized) to **40**(for sequental) **times** faster
  * insertion is **10**(for randomized) to **50**(for sequental) **times** faster.
 These numbers correspond to branching factor of 1,000 and amount of nodes 1,000,000. 
