import common.Tree
import rbt.*
import tools.InputHandler

fun main(args: Array<String>) {
    val inputHandler: InputHandler = InputHandler()
    inputHandler.start()
    var tree = inputHandler.getTree()
    if(tree != null)
        tree.draw()
}