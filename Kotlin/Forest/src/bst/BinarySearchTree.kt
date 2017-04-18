package bst

import common.*
import tools.Logger

class BinarySearchTree<T : Comparable<T>>(var root: BinarySearchNode<T>? = null) : SearchTree<T>() {

    override fun root(): Node<T>? {
        return root
    }

    override fun insert(value: T) {
        Logger.debugInfo("BinarySearchTree: inserting $value")
        if(root == null) {
            root = BinarySearchNode(value)
            return
        }
        var currentBinarySearchNode: BinarySearchNode<T> = root!!
        while (true) {
            if(currentBinarySearchNode.value < value) {
                if(currentBinarySearchNode.right == null) {
                    currentBinarySearchNode.right = BinarySearchNode(value)
                    return
                }
                currentBinarySearchNode = currentBinarySearchNode.right!!
            } else if(currentBinarySearchNode.value > value) {
                if(currentBinarySearchNode.left == null) {
                    currentBinarySearchNode.left = BinarySearchNode(value)
                    return
                }
                currentBinarySearchNode = currentBinarySearchNode.left!!
            } else {
                Logger.warning("Attempt to add node that is already contain in this tree.")
                return
            }
        }
    }

    //returns false if there was no node to be removed or an error occured
    //FIXME: nullpointer exception in 67-68
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
                val replacement: BinarySearchNode<T> = (BinarySearchTree(binarySearchNode.right).min() as BinarySearchNode<T>?)!!
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
        var currentNode: BinarySearchNode<T> = root!!
        while (true) {
            if(currentNode.value < value) {
                if(currentNode.right == null) {
                    return null
                }
                currentNode = currentNode.right!!
            } else if(currentNode.value > value) {
                if(currentNode.left == null) {
                    return null
                }
                currentNode = currentNode.left!!
            } else {
                return currentNode
            }
        }
    }

    override fun closestBigger(node: Node<T>): BinarySearchNode<T>? {
        node as BinarySearchNode<T>
        var currentNode: BinarySearchNode<T>? = node.right
        if(currentNode != null) {
            //go as left as possible from node's right child
            while(currentNode!!.left != null && currentNode.left!!.value() != null) {
                currentNode = currentNode.left
            }
            return currentNode
        }
        else if(node.parent() != null){
            if(node.isLeftChild()) {
                return node.parent
            }
            else {
                //go on parent's line until node isn't left child or it's parent is null
                var current: BinarySearchNode<T>? = node
                while(current != null && current.isRightChild()) {
                    current = current.parent
                }
                if(current == null || current.parent == null) {
                    return null
                } else {
                    return current.parent!!
                }
            }
        } else {
            return null
        }
    }

    override fun closestSmaller(node: Node<T>): BinarySearchNode<T>? {
        node as BinarySearchNode<T>
        var currentNode: BinarySearchNode<T>? = node.left ?: node.parent ?: return null
        if(currentNode != null ) {
            //go as left as possible from node's right child
            while(currentNode!!.right != null && currentNode.right!!.value() != null) {
                currentNode = currentNode.right
            }
            return currentNode
        }
        else if(node.parent != null){
            if(node.isRightChild()) {
                return node.parent
            } else {
                //go on parent's line until node isn't left child or it's parent is null
                var current: BinarySearchNode<T>? = node
                while(current != null && current.isLeftChild()) {
                    current = current.parent
                }
                if(current == null || current.parent == null) {
                    return null
                } else {
                    return current.parent!!
                }
            }
        } else {
            return null
        }
    }

    override fun iterator(): Iterator<Node<T>?> {
        return super.iterator()
    }

    override fun equals(other: Any?): Boolean {
        return super<SearchTree>.equals(other)
    }

    override fun hashCode(): Int {
        return super<SearchTree>.hashCode()
    }
}