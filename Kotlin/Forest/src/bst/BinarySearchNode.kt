package bst

import common.*

open class BinarySearchNode<T>(var value: T, var parent: BinarySearchNode<T>? = null,
                               var children: ChildrenNodes<BinarySearchNode<T>> = ChildrenNodes()) : Node<T> {

    open fun setLeftChild(node: BinarySearchNode<T>?) {
        if(node == this) {
            println("Attempt to set a node as a child of itself")
            return
        }
        children.left = node
        if(node != null)
            node.parent = this
    }
    open fun setRightChild(node: BinarySearchNode<T>?) {
        if(node == this) {
            println("Attempt to set a node as a child of itself")
            return
        }
        children.right = node
        if(node != null)
            node.parent = this
    }

    open fun setParentsReferenceTo(newChild: BinarySearchNode<T>?) {
        if(parent == null)
            return
        if(parent!!.children.left == this)
            parent!!.setLeftChild(newChild)
        else if(parent!!.children.right == this)
            parent!!.setRightChild(newChild)
    }

    open fun amountOfChildren(): Int {
        var count: Int = 0;
        if(children.left != null)
            count++
        if(children.right != null)
            count++
        return count
    }

    override fun toString(): String {
        //FIXME: change bst.BinarySearchNode's toString()
        return "bst.BinarySearchNode(value=$value, parent=$parent, children={})"
    }

}