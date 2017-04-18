package common

abstract class SearchTree<T : Comparable<T>>/*(root: Node<T>?)*/ : Iterable<Node<T>?>, Tree<T>() {

    override operator fun iterator(): Iterator<Node<T>?> = TreeIterator(this, min())
    abstract fun insert(value: T)
    abstract fun remove(value: T): Boolean
    abstract fun find(value: T): Node<T>?
    fun min(): Node<T>? {
        var current: Node<T> = root() ?: return null
        while(current.children().first() != null && current.children().first()!!.value() != null) {
            current = current.children().first()!!
        }
        return current
    }

    fun max(): Node<T>? {
        var current: Node<T> = root() ?: return null
        while(current.children().last() != null && current.children().last()!!.value() != null) {
            current = current.children().last()!!
        }
        return current
    }

    operator fun plusAssign(value: T) {
        insert(value)
    }
    operator fun minusAssign(value: T) {
        remove(value)
    }

    abstract fun closestBigger(node: Node<T>): Node<T>?
    abstract fun closestSmaller(node: Node<T>): Node<T>?

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
