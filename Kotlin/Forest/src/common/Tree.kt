package common

import rbt.RedBlackNode

open class Tree<T>(private var root: Node<T>?) {

    open protected fun root(): Node<T>?  {
        return root
    }

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
        if(root() == null) {
            println("Tree is empty.")
        } else {
            print(root()!!)
            println()
        }
    }

    private fun print(node: Node<T>) {
        if(node.leftChild() != null) {
            this.print(node.leftChild()!!)
        }
        print(" $node ")
        if(node.rightChild() != null) {
            this.print(node.rightChild()!!)
        }
    }

    //TODO: implement using array of strings
    /**intellectual property of Vladimir Maltsev */
    fun draw(curNode: Node<T>? = root(), divider: String = "") {
        if(curNode == null || curNode.value() == null)
            return
        var specSymbol = ""
        if(curNode.parent() != null) {
            if(curNode == curNode.parent()!!.leftChild())
                specSymbol = "\\"
            else
                specSymbol = "/"
        }
        draw(curNode.rightChild(), divider + "   ")
        println("$divider$specSymbol$curNode")
        draw(curNode.leftChild(), divider + "   ")
    }

}