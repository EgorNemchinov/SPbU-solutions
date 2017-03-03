package rbt

import common.*
import tools.Logger

class RedBlackTree<T: Comparable<T>>(var root: RedBlackNode<T>? = null) : SearchTree<T>, Tree<T>(root) {
    //it's better than null because it's black
    val nil: RedBlackNode<T> = RedBlackNode(null, isBlack = true)

    override fun root(): RedBlackNode<T>? {
        return root
    }

    override fun insert(value: T) {
        if(root == null) {
            root = RedBlackNode(value)
            root!!.isBlack = true
            root!!.left = nil
            root!!.right = nil
            return
        }
        var inserted: Boolean = false
        var currentNode: RedBlackNode<T> = root!!
        while (!inserted) {
            if(value > currentNode.value!!) {
                if(currentNode.right == nil || currentNode.right == null) {
                    currentNode.right = RedBlackNode(value)
                    currentNode = currentNode.right!!
                    inserted = true
                } else
                    currentNode = currentNode.right!!
            } else if(value < currentNode.value!!) {
                if(currentNode.left == nil || currentNode.left == null) {
                    currentNode.left = RedBlackNode(value)
                    currentNode = currentNode.left!!
                    inserted = true
                } else
                    currentNode = currentNode.left!!
            } else {
                Logger.warning("Attempt to add node that is already contained in this tree.")
                return
            }
        }
        currentNode.left = nil
        currentNode.right = nil
        correct(currentNode)
        root!!.isBlack = true
    }

    fun correct(node: RedBlackNode<T>) {
        //if parent isn't red no need to fix
        if(node.parent != null && node.parent!!.isBlack)
            return
        var currentNode = node
        var grandparent = currentNode.grandparent()
         if(grandparent == null) {
            root!!.isBlack = true
            return
        }
        var uncle = currentNode.uncle()
        if(currentNode.parent!!.isLeftChild()) {
            if(!uncle!!.isBlack) {
                currentNode.parent!!.isBlack = true
                uncle.isBlack = true
                grandparent.isBlack = false
            } else {
                //uncle is black
                if(currentNode.isRightChild()) {
                    //converting left triangle to left line
                    currentNode.parent!!.rotateLeft()
                    currentNode = currentNode.left!!
                }
                //left line
                grandparent.rotateRight()
                currentNode.parent!!.isBlack = true
                grandparent.isBlack = false
            }
        } else if(currentNode.parent!!.isRightChild()) {
            if(!uncle!!.isBlack) {
                currentNode.parent!!.isBlack = true
                uncle.isBlack = true
                grandparent.isBlack = false
            } else {
                if(currentNode.isLeftChild()) {
                    //converting right triangle to right line
                    currentNode.parent!!.rotateRight()
                    currentNode = currentNode.right!!
                }
                //right line
                grandparent.rotateLeft()
                currentNode.parent!!.isBlack = true
                grandparent.isBlack = false
            }
        }
        if(root!!.parent != null) {
            root = calculateRoot() as RedBlackNode<T>?
        }
        if(currentNode.parent != null && !currentNode.parent!!.isBlack) {
            //continue for parent if it's red
            correct(currentNode.parent!!)
        }
    }

    override fun remove(value: T): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun find(value: T): RedBlackNode<T>? {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun min(): RedBlackNode<T>? {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun max(): RedBlackNode<T>? {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}