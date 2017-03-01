package rbt

import common.*

class RedBlackTree<T: Comparable<T>>(var root: RedBlackNode<T>? = null) : SearchTree<T> {

    override fun insert(value: T) {
        if(root == null) {
            root = RedBlackNode(value)
            return
        }
        var currentNode: RedBlackNode<T> = root!!
        while (true) {
            if(value > currentNode.value) {
                if(currentNode.children.right == null) {
                    currentNode.setRightChild(RedBlackNode(value))
                    return
                }
                currentNode = currentNode.children.right!!
            } else if(value < currentNode.value) {
                if(currentNode.children.left == null) {
                    currentNode.setLeftChild(RedBlackNode(value))
                    return
                }
                currentNode = currentNode.children.left!!
            } else {
                //common.Tree already contains this value
                return
            }
        }
        //TODO: different cases
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