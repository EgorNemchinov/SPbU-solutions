import rbt.*

fun main(args: Array<String>) {
    var tree: RedBlackTree<Int> = RedBlackTree(RedBlackNode(6))
    tree += 4
    tree += 7
    tree += 5
    tree += 10
    tree += 3
    tree += 1
    tree += 9
    tree += 8
    tree.draw()
}