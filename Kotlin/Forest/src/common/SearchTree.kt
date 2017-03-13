package common

abstract class SearchTree<T : Comparable<T>>(root: Node<T>?) : Tree<T>(root) {
    abstract fun insert(value: T)
    abstract fun remove(value: T): Boolean
    abstract fun find(value: T): Node<T>?
    abstract fun min(): Node<T>?
    abstract fun max(): Node<T>?

    operator fun plusAssign(value: T) {
        insert(value)
    }
    operator fun minusAssign(value: T) {
        remove(value)
    }

    //TODO: min & max

    fun closestBigger(node: Node<T>): Node<T>? {
        var currentNode: Node<T>? = node.rightChild() ?: return null
        //go as left as possible from node's right child
        while(currentNode!!.leftChild() != null && currentNode.leftChild()!!.value() != null) {
            currentNode = currentNode.leftChild()
        }
        return currentNode
    }

    fun closestSmaller(node: Node<T>): Node<T>? {
        var currentNode: Node<T>? = node.leftChild() ?: return null
        //go as right as possible from node's left child
        while(currentNode!!.rightChild() != null && currentNode.rightChild()!!.value() != null) {
            currentNode = currentNode.leftChild()
        }
        return currentNode
    }
}