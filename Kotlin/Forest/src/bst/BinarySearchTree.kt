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
                if(currentBinarySearchNode.right == null) {
                    currentBinarySearchNode.right = BinarySearchNode(value)
                    return
                }
                currentBinarySearchNode = currentBinarySearchNode.right!!
            } else if(value < currentBinarySearchNode.value) {
                if(currentBinarySearchNode.left == null) {
                    currentBinarySearchNode.left = BinarySearchNode(value)
                    return
                }
                currentBinarySearchNode = currentBinarySearchNode.left!!
            } else {
                println("Attemopt to add node that is already contain in this tree.")
                return
            }
        }
    }

    //returns false if there was no node to be removed or an error occured
    override fun remove(value: T): Boolean {
        //finding binarySearchNode to be removed
        var binarySearchNode: BinarySearchNode<T>? = find(value) ?: return false
        when(binarySearchNode!!.amountOfChildren()) {
            0 -> {
                binarySearchNode.setParentsReferenceTo(null)
            }
            1 -> {
                var child = binarySearchNode.childrenToList().firstOrNull() ?: return false
                child.parent = binarySearchNode.parent
                binarySearchNode.setParentsReferenceTo(child)
            }
            2 -> {
                //searching for minimum in the right tree. could be max in the left tree?
                val replacement: BinarySearchNode<T> = BinarySearchTree(binarySearchNode.right).min()!!
                //replacement is a leaf so has no children
                replacement.setParentsReferenceTo(null)
                replacement.left = binarySearchNode.left
                replacement.right = binarySearchNode.right

                //setting parent's reference
                 if(binarySearchNode.parent == null)
                     this.root = replacement
                else {
                     binarySearchNode.setParentsReferenceTo(replacement)
                 }

                binarySearchNode.left!!.parent = replacement
                binarySearchNode.right!!.parent = replacement
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
                if(currentBinarySearchNode.right == null) {
                    return null
                }
                currentBinarySearchNode = currentBinarySearchNode.right!!
            } else if(value < currentBinarySearchNode.value) {
                if(currentBinarySearchNode.left == null) {
                    return null
                }
                currentBinarySearchNode = currentBinarySearchNode.left!!
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
            if(root!!.left == null)
                return currentBinarySearchNode
            else
                return BinarySearchTree(root!!.left).min()
        }
    }

    override fun max(): BinarySearchNode<T>? {
        if(root == null)
            return null
        var currentBinarySearchNode: BinarySearchNode<T> = root!!
        while(true) {
            if(root!!.right == null)
                return currentBinarySearchNode
            else
                return BinarySearchTree(root!!.right).min()
        }
    }
}