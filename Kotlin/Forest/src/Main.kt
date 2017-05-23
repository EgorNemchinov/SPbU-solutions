import common.SearchTree
import tools.InputHandler

fun main(args: Array<String>) {
    val inputHandler: InputHandler = InputHandler()
    inputHandler.start()
    var tree = inputHandler.tree
    tree as SearchTree<Int>
    if(tree != null)
    {
        tree.print()
    }
}