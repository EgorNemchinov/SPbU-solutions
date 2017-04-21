package bst

import common.*
import tools.Logger

open class BinarySearchNode<T: Comparable<T>>(var value: BinaryNodeValue<T>, var parent: BinarySearchNode<T>? = null,
                               var children: BinaryChildren<BinarySearchNode<T>> = BinaryChildren()) : Node<T> {

    constructor(value: T) : this(BinaryNodeValue(value))

    var left: BinarySearchNode<T>? = children.left
        set(value) {
            Logger.debugInfo("Left child of ${this.value} is set to $value ")
            if(value == this) {
                println("Attempt to set a node as a child of itself")
                return
            }
            field = this
            children.left = value
            if(value != null)
                value.parent = this
        }
        get() {
            return children.left
        }
    var right: BinarySearchNode<T>? = children.right
        set(value) {
            Logger.debugInfo("Right child of ${this.value} is set to $value ")
            if(value == this) {
                println("Attempt to set a node as a child of itself")
                return
            }
            field = value
            children.right = value
            if(value != null)
                value.parent = this
        }
        get() {
            return children.right
        }

    override fun value(): BinaryNodeValue<T>? {
        return value
    }

    override fun parent(): Node<T>? {
        return parent
    }

    override fun children(): NodeChildren<Node<T>> {
        return children
    }

    fun setParentsReferenceTo(newChild: BinarySearchNode<T>?) {
        Logger.debugInfo("Setting ${this.value}'s parents reference to $newChild")
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

    fun isLeftChild(): Boolean {
        if(parent == null)
            return false
        return parent!!.left == this
    }

    fun isRightChild(): Boolean {
        if(parent == null)
            return false
        return parent!!.right == this
    }

    override fun isLeaf(): Boolean {
        return left == null && right == null
    }

    override fun toString(): String {
        return "$value "
//        return "$value. children are ${children.left?.value} and ${children.right?.value}"
    }

}