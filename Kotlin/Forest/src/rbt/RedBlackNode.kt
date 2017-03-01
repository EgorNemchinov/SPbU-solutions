package rbt

import common.*

class RedBlackNode<T>(var value: T, var parent: RedBlackNode<T>? = null,
                      var children: ChildrenNodes<RedBlackNode<T>> = ChildrenNodes(),
                      var isBlack: Boolean = false) : Node<T> {

    fun setLeftChild(node: RedBlackNode<T>?) {
        if(node == this) {
            println("Attempt to set a node as a child of itself")
            return
        }
        children.left = node
        if(node != null)
            node.parent = this
    }

    fun setRightChild(node: RedBlackNode<T>?) {
        if(node == this) {
            println("Attempt to set a node as a child of itself")
            return
        }
        children.right = node
        if(node != null)
            node.parent = this
    }

    fun amountOfChildren(): Int {
        return children.toList().size
    }
}