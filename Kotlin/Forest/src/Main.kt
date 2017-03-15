import tools.InputHandler

fun main(args: Array<String>) {
    val inputHandler: InputHandler = InputHandler()
    inputHandler.start()
    var tree = inputHandler.tree
    if(tree != null)
    {
        tree.draw()
    }
}