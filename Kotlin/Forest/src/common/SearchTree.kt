package common

import java.util.*

abstract class SearchTree<T : Comparable<T>> : Iterable<Node<T>?>, Tree<T>() {

    var currentIterator: Iterator<Node<T>?> = orderIterator()
    override operator fun iterator(): Iterator<Node<T>?> = currentIterator
    abstract fun insert(value: T)
    abstract fun remove(value: T): Boolean
    abstract fun find(value: T): Node<T>?
    fun min(): Node<T>? {
        var current: Node<T> = root() ?: return null
        while(current.children().first() != null && !current.children().first()!!.value()!!.equals(null)) {
            current = current.children().first()!!
        }
        return current
    }

    fun max(): Node<T>? {
        var current: Node<T> = root() ?: return null
        while(current.children().last() != null && !current.children().last()!!.value()!!.equals(null)) {
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

    fun dfsIterator(): Iterator<Node<T>?> = DfsIterator(this)
    fun bfsIterator(): Iterator<Node<T>?> = BfsIterator(this)
    fun orderIterator(): Iterator<Node<T>?> = OrderIterator(this)

    class BfsIterator<T: Comparable<T>>(var tree: SearchTree<T>): Iterator<Node<T>?> {
        var queue: Queue<Node<T>> = LinkedList<Node<T>>()

        init {
            if(tree.root() != null)
                queue.add(tree.root())
        }

        override fun hasNext(): Boolean {
            return queue.isNotEmpty()
        }

        override fun next(): Node<T> {
            var curNode = queue.poll()
            if(!curNode.isLeaf()) {
                for(child in curNode.children()) {
                    queue.add(child)
                }
            }
            return curNode
        }
    }

    class DfsIterator<T: Comparable<T>>(var tree: SearchTree<T>): Iterator<Node<T>?> {
        var stack: Stack<Node<T>> = Stack()

        init {
            if(tree.root() != null)
                stack.add(tree.root())
        }

        override fun hasNext(): Boolean {
            return stack.isNotEmpty()
        }

        override fun next(): Node<T> {
            var curNode = stack.pop()
            if(!curNode.isLeaf()) {
                for(child in curNode.children().toList().reversed()) {
                    stack.add(child)
                }
            }
            return curNode
        }
    }

    class OrderIterator<T: Comparable<T>>(var tree: SearchTree<T>, var currentNode: Node<T>? = tree.min()): Iterator<Node<T>?> {
        var nextNode: Node<T>? = currentNode

        override fun hasNext(): Boolean {
            if(nextNode == null)
                return false
            return !nextNode!!.equals(null)
        }

        override fun next(): Node<T>? {
            currentNode = nextNode
            nextNode = tree.closestBigger(currentNode!!)
            return currentNode
        }

    }

}
