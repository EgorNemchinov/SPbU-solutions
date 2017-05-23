package rbt

import bst.BinaryChildren
import common.*
import tools.Logger

//TODO: isBlack -> enum
class RedBlackNode<T: Comparable<T>>(var value: BinaryNodeValue<T>, var parent: RedBlackNode<T>? = null,
                           var children: BinaryChildren<RedBlackNode<T>> = BinaryChildren(),
                           var isBlack: Boolean = false) : Node<T> {
    constructor(value: T, parent: RedBlackNode<T>? = null, children: BinaryChildren<RedBlackNode<T>> = BinaryChildren(), isBlack: Boolean = false)
            : this(BinaryNodeValue(value), parent, children, isBlack)

    var left: RedBlackNode<T>? = children.left
        set(value) {
            Logger.debugInfo("Left child of ${this} is set to $value ")
            if(value == this) {
                Logger.warning("Attempt to set a node $this as a child of itself")
                return
            }
            field = value
            children.left = value
            if(value != null)
                value.parent = this
        }
        get() {
            return children.left
        }
    var right: RedBlackNode<T>? = children.right
        set(value) {
            Logger.debugInfo("Right child of ${this} is set to $value ")
            if(value == this) {
                Logger.warning("Attempt to set a node $this as a child of itself")
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


    fun setParentsReferenceTo(newChild: RedBlackNode<T>?) {
        Logger.debugInfo("Setting ${this.value}'s parents reference to $newChild")
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

    fun isNil(): Boolean {
        return value.value == null && left == null && right == null
    }

    override fun parent() : RedBlackNode<T>? {
        return parent
    }

    override fun value(): BinaryNodeValue<T> {
        return value
    }

    override fun children(): BinaryChildren<Node<T>> {
        return children as BinaryChildren<Node<T>>
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

    fun brother(): RedBlackNode<T>? {
        if(parent == null)
            return null
        if(isLeftChild()) {
            return parent!!.right
        } else {
            return parent!!.left
        }
    }

    fun childrenToList(): List<RedBlackNode<T>> {
        var list: List<RedBlackNode<T>> = listOf()
        if(left != null && left!!.value != null)
            list += left!!
        if(right != null && right!!.value != null)
            list += right!!
        return list
    }

    fun amountOfChildren(): Int {
        var count: Int = 0
        if(left != null)
            count++
        if(right != null)
            count++
        return count
    }


    fun rotateLeft() {
        if(this.right == null)
            return
        Logger.debugInfo("rotateLeft() executed for $this")
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
        Logger.debugInfo("rotateRight() executed for $this")
    }

    fun recolor() {
        isBlack = !isBlack
    }

    override fun toString(): String {
        return "$value${if(isBlack) "B" else "R"}"
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
        return (left == null) && (right == null)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if(other == null) {
            return this.value.value == null
        }
        if (other.javaClass != javaClass) return false

        other as RedBlackNode<*>

        if (value != other.value) return false
        if (isBlack != other.isBlack) return false
        if(parent?.value == other.parent?.value)
            return false
        //todo: compare children

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + (parent?.hashCode() ?: 0)
        result = 31 * result + (left?.hashCode() ?: 0)
        result = 31 * result + (right?.hashCode() ?: 0)
        result = 31 * result + isBlack.hashCode()
        return result
    }
}