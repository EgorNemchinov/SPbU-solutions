package bst

import common.*

open class BinarySearchNode<T>(var value: T, var parent: BinarySearchNode<T>? = null,
                               left: BinarySearchNode<T>? = null,
                               right: BinarySearchNode<T>? = null) : Node<T> {
    var left: BinarySearchNode<T>? = left
        set(value) {
            if(value == this) {
                println("Attempt to set a node as a child of itself")
                return
            }
            field = value
            if(value != null)
                value.parent = this
        }
    var right: BinarySearchNode<T>? = right
        set(value) {
            if(value == this) {
                println("Attempt to set a node as a child of itself")
                return
            }
            field = value
            if(value != null)
                value.parent = this
        }

    override fun value(): T? {
        return value
    }

    override fun parent(): Node<T>? {
        return parent
    }

    override fun leftChild(): Node<T>? {
        return left
    }

    override fun rightChild(): Node<T>? {
        return right
    }

    fun setParentsReferenceTo(newChild: BinarySearchNode<T>?) {
        if(parent == null)
            return
        if(parent!!.left == this)
            parent!!.left = newChild
        else if(parent!!.right == this)
            parent!!.right = newChild
    }

    fun childrenToList(): List<BinarySearchNode<T>> {
        var list: List<BinarySearchNode<T>> = listOf()
        if(left != null)
            list += left!!
        if(right != null)
            list += right!!
        return list
    }

    fun amountOfChildren(): Int {
        var count: Int = 0;
        if(left != null)
            count++
        if(right != null)
            count++
        return count
    }

    override fun toString(): String {
        return "$value"
    }

}