package bst

import common.SearchTree
import common.Tree

class BinarySearchTree<T : Comparable<T>>(var root: BinarySearchNode<T>? = null) : Tree<T>(root), SearchTree<T> {

    override fun insert(value: T) {
        if(root == null) {
            root = BinarySearchNode(value)
            return
        }
        var currentBinarySearchNode: BinarySearchNode<T> = root!!
        while (true) {
            if(value > currentBinarySearchNode.value) {
                if(currentBinarySearchNode.children.right == null) {
                    currentBinarySearchNode.setRightChild(BinarySearchNode(value))
                    return
                }
                currentBinarySearchNode = currentBinarySearchNode.children.right!!
            } else if(value < currentBinarySearchNode.value) {
                if(currentBinarySearchNode.children.left == null) {
                    currentBinarySearchNode.setLeftChild(BinarySearchNode(value))
                    return
                }
                currentBinarySearchNode = currentBinarySearchNode.children.left!!
            } else {
                //Tree already contains this value
                return
            }
        }
    }

    //returns false if there was no node to be removed
    override fun remove(value: T): Boolean {
        //finding binarySearchNode to be removed
        var binarySearchNode: BinarySearchNode<T>? = find(value) ?: return false
        when(binarySearchNode!!.amountOfChildren()) {
            0 -> {
                binarySearchNode.setParentsReferenceTo(null)
            }
            1 -> {
                //FIXME: .first()
                var child = binarySearchNode.children.toList().first()
                child.parent = binarySearchNode.parent
                binarySearchNode.setParentsReferenceTo(child)
            }
            2 -> {
                //searching for minimum in the right tree. could be max in the left tree?
                val replacement: BinarySearchNode<T> = BinarySearchTree(binarySearchNode.children.right).min()!!
                //replacement is a leaf so has no children
                replacement.setParentsReferenceTo(null)
                replacement.setLeftChild(binarySearchNode.children.left)
                replacement.setRightChild(binarySearchNode.children.right)

                //setting parent's reference
                 if(binarySearchNode.parent == null)
                     this.root = replacement
                else {
                     binarySearchNode.setParentsReferenceTo(replacement)
                 }

                binarySearchNode.children.left!!.parent = replacement
                binarySearchNode.children.right!!.parent = replacement
            }
        }
        return true
    }

    override fun find(value: T): BinarySearchNode<T>? {
        if(root == null)
            return null
        var currentBinarySearchNode: BinarySearchNode<T> = root!!
        while (true) {
            if(value > currentBinarySearchNode.value) {
                if(currentBinarySearchNode.children.right == null) {
                    return null
                }
                currentBinarySearchNode = currentBinarySearchNode.children.right!!
            } else if(value < currentBinarySearchNode.value) {
                if(currentBinarySearchNode.children.left == null) {
                    return null
                }
                currentBinarySearchNode = currentBinarySearchNode.children.left!!
            } else {
                return currentBinarySearchNode
            }
        }
    }

    //return null if tree has null root
    override fun min(): BinarySearchNode<T>? {
        if(root == null)
            return null
        var currentBinarySearchNode: BinarySearchNode<T> = root!!
        while(true) {
            if(root!!.children.left == null)
                return currentBinarySearchNode
            else
                return BinarySearchTree(root!!.children.left).min()
        }
    }

    override fun max(): BinarySearchNode<T>? {
        if(root == null)
            return null
        var currentBinarySearchNode: BinarySearchNode<T> = root!!
        while(true) {
            if(root!!.children.right == null)
                return currentBinarySearchNode
            else
                return BinarySearchTree(root!!.children.right).min()
        }
    }

    fun print() {
        if(root != null)
            this.print(root!!)
        println()
    }

    private fun print(root: BinarySearchNode<T>) {
        if(root.children.left != null)
            print(root.children.left!!)
        print(" " + root.value)
        if(root.children.right != null)
            print(root.children.right!!)
    }
}