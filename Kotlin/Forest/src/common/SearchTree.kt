package common

abstract class SearchTree<T : Comparable<T>>(root: Node<T>?) : Tree<T>(root), Iterable<Node<T>?> {

    override operator fun iterator(): Iterator<Node<T>?> = TreeIterator(this, min())

    abstract fun insert(value: T)
    abstract fun remove(value: T): Boolean
    abstract fun find(value: T): Node<T>?
    fun min(): Node<T>? {
        var current: Node<T> = root() ?: return null
        while(current.leftChild() != null && current.leftChild()!!.value() != null) {
            current = current.leftChild()!!
        }
        return current
    }

    fun max(): Node<T>? {
        var current: Node<T> = root() ?: return null
        while(current.rightChild() != null && current.rightChild()!!.value() != null) {
            current = current.rightChild()!!
        }
        return current
    }

    operator fun plusAssign(value: T) {
        insert(value)
    }
    operator fun minusAssign(value: T) {
        remove(value)
    }

    fun closestBigger(node: Node<T>): Node<T>? {
        var currentNode: Node<T>? = node.rightChild()
        if(currentNode != null && currentNode.value() != null) {
            //go as left as possible from node's right child
            while(currentNode!!.leftChild() != null && currentNode.leftChild()!!.value() != null) {
                currentNode = currentNode.leftChild()
            }
            return currentNode
        }
        else if(node.parent() != null){
            if(node.isLeftChild()) {
                return node.parent()
            }
            else {
                //go on parent's line until node isn't left child or it's parent is null
                var current: Node<T>? = node
                while(current != null && current.isRightChild()) {
                    current = current.parent()
                }
                if(current == null || current.parent() == null) {
                    return null
                } else {
                    return current.parent()!!
                }
            }
        } else {
            return null
        }
    }

    fun closestSmaller(node: Node<T>): Node<T>? {
        var currentNode: Node<T>? = node.leftChild() ?: node.parent() ?: return null
        if(currentNode != null && currentNode.value() != null) {
            //go as left as possible from node's right child
            while(currentNode!!.rightChild() != null && currentNode.rightChild()!!.value() != null) {
                currentNode = currentNode.rightChild()
            }
            return currentNode
        }
        else if(node.parent() != null){
            if(node.isRightChild()) {
                return node.parent()
            } else {
                //go on parent's line until node isn't left child or it's parent is null
                var current: Node<T>? = node
                while(current != null && current.isLeftChild()) {
                    current = current.parent()
                }
                if(current == null || current.parent() == null) {
                    return null
                } else {
                    return current.parent()!!
                }
            }
        } else {
            return null
        }
    }

    class TreeIterator<T: Comparable<T>>(var tree: SearchTree<T>, var currentNode: Node<T>? = tree.root()): Iterator<Node<T>?> {
        var next: Node<T>? = currentNode

        override fun hasNext(): Boolean {
            return !(next == null || next!!.value() == null)
        }

        override fun next(): Node<T>? {
            currentNode = next
            next = tree.closestBigger(currentNode!!)
            return currentNode
        }
    }

}
