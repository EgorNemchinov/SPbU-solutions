import bst.BinarySearchNode
import bst.BinarySearchTree

fun main(args: Array<String>) {
    var tree: BinarySearchTree<Int> = BinarySearchTree(BinarySearchNode(4))
    tree.insert(2)
    tree.insert(6)
    tree.insert(6)
    tree.insert(8)
    tree.insert(5)
    tree.insert(3)

    tree.print()
    tree.remove(4)
    tree.remove(3)
    println("After removal:")
    tree.print()
}