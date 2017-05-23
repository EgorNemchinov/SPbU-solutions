package common

import tools.Logger

abstract open class Tree<T: Comparable<T>>() {

    protected val logger: Logger = Logger()

    abstract open fun root(): Node<T>?

    protected fun calculateRoot(): Node<T>? {
        return calculateRoot(root()!!)
    }

    protected fun calculateRoot(anyNode: Node<T>): Node<T>? {
        var cur = anyNode
        while(cur.parent() != null) {
            cur = cur.parent()!!
        }
        return cur
    }

    fun print() {
        Logger.debugInfo("Called print at tree with root ${root()}")
        if(root() == null) {
            println("Tree is empty.")
        } else {
            print(root()!!)
            println()
        }
    }

    //TODO: implement using iterable
    private fun print(node: Node<T>) {
        if(node.children().first() != null) {
            this.print(node.children().first()!!)
        }
        print(" ${if(!node.value()!!.equals(null)) "$node " else ""}")
        if(node.children().last() != null) {
            this.print(node.children().last()!!)
        }
    }

    //TODO: implement using array of strings, ?bfs?
    fun draw(curNode: Node<T>? = root(), divider: String = "") {
        if(curNode == root())
            Logger.debugInfo("Drawing tree with root ${root()}")
        if(curNode == null || curNode.value()!!.equals(null)) {
            return
        }
        var specSymbol = ""
        if(curNode.parent() != null) {
            if(curNode == curNode.parent()!!.children().first())
                specSymbol = "\\"
            else
                specSymbol = "/"
        }
        draw(curNode.children().last(), divider + "   ")
        println("$divider$specSymbol$curNode")
        draw(curNode.children().first(), divider + "   ")
    }

}