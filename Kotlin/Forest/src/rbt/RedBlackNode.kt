package rbt

import common.*

open class RedBlackNode<T>(var value: T?, var parent: RedBlackNode<T>? = null,
                           left: RedBlackNode<T>? = null,
                           right: RedBlackNode<T>? = null,
                           isBlack: Boolean = false) : Node<T> {
    var left: RedBlackNode<T>? = left
        set(value) {
            if(value == this) {
                println("Attempt to set a node as a child of itself")
                return
            }
            field = value
            if(value != null)
                value.parent = this
        }
    var right: RedBlackNode<T>? = right
        set(value) {
            if(value == this) {
                println("Attempt to set a node as a child of itself")
                return
            }
            field = value
            if(value != null)
                value.parent = this
        }
    var isBlack: Boolean = isBlack

    fun setParentsReferenceTo(newChild: RedBlackNode<T>?) {
        if(newChild != null)
            newChild.parent = parent
        if(parent == null) {
            return
        }
        if(parent!!.left == this)
            parent!!.left = newChild
        else if(parent!!.right == this)
            parent!!.right = newChild
        parent = null
    }

    override fun parent() : RedBlackNode<T>? {
        return parent
    }

    override fun value(): T? {
        return value
    }

    override fun leftChild(): Node<T>? {
        return left
    }

    override fun rightChild(): Node<T>? {
        return right
    }

    fun grandparent(): RedBlackNode<T>? {
        var current: RedBlackNode<T>? = this.parent ?: return null
        current = current!!.parent ?: return null
        return current
    }

    fun uncle(): RedBlackNode<T>? {
        var grandparent: RedBlackNode<T>? = grandparent() ?: return null
        if(grandparent!!.left == this.parent)
            return grandparent.right
        else if(grandparent.right == this.parent)
            return grandparent.left
        else
            return null
    }

    fun childrenToList(): List<RedBlackNode<T>> {
        var list: List<RedBlackNode<T>> = listOf()
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


    fun rotateLeft() {
        if(this.right == null)
            return
        var rightChild: RedBlackNode<T> = this.right!!
        var floatingNode: RedBlackNode<T>? = rightChild.left //this will later become right child of this node
        this.setParentsReferenceTo(rightChild) //linking node's parent to node's right child
        rightChild.left = this
        this.right = floatingNode
    }
    fun rotateRight() {
        if(this.left == null)
            return
        var leftChild: RedBlackNode<T> = this.left!!
        var floatingNode: RedBlackNode<T>? = leftChild.right
        this.setParentsReferenceTo(leftChild)
        leftChild.right = this
        this.left = floatingNode
    }

    fun isLeftChild(): Boolean {
        return parent != null && parent!!.left == this
    }
    fun isRightChild(): Boolean {
        return parent != null && parent!!.right == this
    }

    override fun toString(): String {
        if(value == null)
            return "NIL"
        return "$value${if(isBlack) "B" else "R"}"
    }
}