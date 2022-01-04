Implementation of Self Balancing AVL tree.
ensures the time complexity is O(log(n)) in the worst case.
At any point the height of two child subtrees cannot have a difference of more than one. (if difference > 1 , this is known as an imbalance.)
if there is an imbalance in the tree, nessecary roataions are performed to make sure our tree remains balances and the time complexity remains o(log(n))
in the worst case.
