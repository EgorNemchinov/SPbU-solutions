package rbt

import bst.BinarySearchNode
import common.*
import tools.Logger

class RedBlackTree<T: Comparable<T>>(var root: RedBlackNode<T>? = null) : SearchTree<T>() {
    //it's better than null because it's black
    val nil: RedBlackNode<T> = RedBlackNode(BinaryNodeValue(), isBlack = true)

    override fun root(): RedBlackNode<T>? {
        return root
    }

    override fun insert(value: T) {
        if (root == null) {
            root = RedBlackNode(value)
            root!!.isBlack = true
            root!!.left = nil
            root!!.right = nil
            return
        }
        var inserted: Boolean = false
        var currentNode: RedBlackNode<T> = root!!
        while (!inserted) {
            if (currentNode.value < value) {
                if (currentNode.right == nil || currentNode.right == null) {
                    currentNode.right = RedBlackNode(value)
                    currentNode = currentNode.right!!
                    inserted = true
                    break
                } else
                    currentNode = currentNode.right!!
            } else if (currentNode.value > value) {
                if (currentNode.left == nil || currentNode.left == null) {
                    currentNode.left = RedBlackNode(value)
                    currentNode = currentNode.left!!
                    inserted = true
                    break
                } else
                    currentNode = currentNode.left!!
            } else {
                Logger.warning("Attempt to add node that is already contained in this tree.")
                return
            }
        }
        currentNode.left = nil
        currentNode.right = nil
        Logger.debugInfo("Inserted ${currentNode.value}. Correction is ahead.")
        correctAfterInsertion(currentNode)
        root!!.isBlack = true
    }

    fun correctAfterInsertion(node: RedBlackNode<T>) {
        Logger.debugInfo("correctAfterInsertion called for $node")
        //if parent isn't red no need to fix
        if (node.parent != null && node.parent!!.isBlack)
            return
        var currentNode = node
        var grandparent = currentNode.grandparent()
        if (grandparent == null) {
            root!!.isBlack = true
            return
        }
        var uncle = currentNode.uncle()
        if (currentNode.parent!!.isLeftChild()) {
            if (!uncle!!.isBlack) {
                //uncle is red then recolor uncle parent and grandparent
                currentNode.parent!!.isBlack = true
                uncle.isBlack = true
                grandparent.isBlack = false
                correctAfterInsertion(grandparent)
            } else {
                //uncle is black
                if (currentNode.isRightChild()) {
                    //converting left triangle to left line
                    currentNode.parent!!.rotateLeft()
                    currentNode = currentNode.left!!
                }
                //left line
                grandparent.rotateRight()
                currentNode.parent!!.isBlack = true
                grandparent.isBlack = false
            }
        } else if (currentNode.parent!!.isRightChild()) {
            if (!uncle!!.isBlack) {
                currentNode.parent!!.isBlack = true
                uncle.isBlack = true
                grandparent.isBlack = false
                correctAfterInsertion(grandparent)
            } else {
                if (currentNode.isLeftChild()) {
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
        if (root!!.parent != null) {
            root = calculateRoot() as RedBlackNode<T>?
        }
        if (currentNode.parent != null && !currentNode.parent!!.isBlack) {
            //continue for parent if it's red
            correctAfterInsertion(currentNode.parent!!)
        }
        Logger.debugInfo("correctAfterInsertion finished")
    }


    override fun find(value: T): RedBlackNode<T>? {
        if (root == null)
            return null
        var currentNode: RedBlackNode<T> = root!!
        while (true) {
            if (currentNode.value!! < value) {
                if (currentNode.right == nil) { //mb check for null too
                    return null
                }
                currentNode = currentNode.right!!
            } else if (currentNode.value!! > value) {
                if (currentNode.left == nil) {
                    return null
                }
                currentNode = currentNode.left!!
            } else {
                return currentNode
            }
        }
    }

}
