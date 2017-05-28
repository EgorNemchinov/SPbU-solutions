package btree

import common.Node
import common.SearchTree
import tools.Logger
import java.util.*

/**
 * Created by Egor Nemchinov on 29.03.17.
 * SPbU, 2017
 */
class BTree<T: Comparable<T>>(var root: BNode<T>? = null, var t: Int = 2): SearchTree<T>() {

    override fun root(): Node<T>? {
        return root
    }

    override fun find(value: T): Node<T>? {
        if(root == null)
            return null
        return find(value, root!!)
    }

    fun find(value: T, root: BNode<T>): Node<T>? {
        if(root.containsValue(value))
            return root
        if(root.isLeaf())
            return null
        val childIndex = (0 until root.values.size-1).firstOrNull{ root.values[it] > value } ?: root.children.lastIndex
            return find(value, root.children[childIndex])
    }

    private fun splitNode(node: BNode<T>): Pair<BNode<T>, BNode<T>> {
        val left: BNode<T> = BNode(node.t, ArrayList(node.values.subList(0, node.t - 1)), node.parent)
        val right: BNode<T> = BNode(node.t, ArrayList(node.values.subList(node.t, node.values.size)), node.parent)
        node.values.removeAt(node.t - 1)

        if(!node.isLeaf()) {
            left.children.nodes = ArrayList(node.children.subList(0, node.t))
            right.children.nodes = ArrayList(node.children.subList(node.t, node.children.size))
            left.children.nodes.map{it.parent = left}
            right.children.nodes.map{it.parent = right}
        }

        return Pair(left, right)
    }

    override fun insert(value: T) {
        if(root == null) {
            root = BNode(t, valuesList = mutableListOf(value))
            return
        }
        insert(value, root!!)
    }

    val consequentSearch: (T, BNode<T>) -> Int = {
        value, node -> ((0 until node.values.size - 1).firstOrNull{node.values[it] > value} ?: node.values.size)
    }

    val binarySearch: (T, BNode<T>) -> Int = {
        value, node -> insertionIndex(node.values.binarySearch(value))
    }

    val insertionIndex: (Int) -> (Int) = {
        index -> if(index >= 0) index else (-index - 1)
    }

    val indexInNode: (T, BNode<T>) -> Int = binarySearch

    fun insert(value: T, node: BNode<T>) {
        if(node.isLeaf()) {
            node.values.add(indexInNode(value, node), value)
            balanceUp(node)
        } else {
            val childIndex = indexInNode(value, node)
            insert(value, node.children[childIndex])
        }
    }

    fun balanceUp(node: BNode<T>) {
        if(node.values.size >= 2*t) {
            val middle = node.values[t-1]
            val pair = splitNode(node)

            var newParent: BNode<T>
            if(node.parent == null) {
                newParent = BNode(t, mutableListOf(middle), node.parent, mutableListOf(pair.first, pair.second))
                newParent.children.map { it.parent = newParent }
                root = newParent
                return
            } else
                newParent = node.parent!!

            val index = indexInNode(middle, newParent)
            newParent.values.add(index, middle)
            newParent.children.set(index, pair.first)
            newParent.children.add(index + 1, pair.second)

            if(node.parent != null)
                balanceUp(node.parent!!)
        } else
            return
    }

    override fun remove(value: T): Boolean {
        val node: BNode<T>  = findNode(value) ?: return false
        removeFromNode(value, node)
        if(root!!.values.isEmpty() && !root!!.children.isEmpty()) {
            root = root!!.children.first()!!
            root!!.parent = null
        }
        return true
    }

    fun removeFromNode(value: T, node: BNode<T>): Boolean {
        if(node.isLeaf()) {
            if(node.values.size > node.t - 1 || node === root) {
                node.values.remove(value)
                return true
            } else if(node.values.size == node.t - 1) {
                val indexInParent = node.indexInParentsChildren()
                val leftBrother: BNode<T>? = node.leftClosestBrother()
                val rightBrother: BNode<T>? = node.rightClosestBrother()
                if(leftBrother != null && leftBrother.values.size > t) {
                    Logger.debugInfo("Moving value from left brother $leftBrother to parent ${node.parent} and from parent to node $node.")
                    val parentValue = node.parent!!.values[indexInParent - 1]
//                    insertIntoNode(parentValue, node)
                    node.parent!!.values[indexInParent - 1] = leftBrother.values.last()
                    leftBrother.values.removeAt(leftBrother.values.lastIndex)
                    return node.values.remove(value)
                } else if(rightBrother != null && rightBrother.values.size > t) {
                    Logger.debugInfo("Moving value from right brother $rightBrother to parent ${node.parent} and from parent to node $node.")
                    val parentValue = node.parent!!.values[indexInParent]
//                    insertIntoNode(parentValue, node)
                    node.parent!!.values[indexInParent] = rightBrother.values.first()
                    rightBrother.values.removeAt(0)
                    return node.values.remove(value)
                } //at least one of it's brothers has t values
                else if(leftBrother != null && leftBrother.values.size == t) {
                    val listOfValues: MutableList<T> = leftBrother.values
                    listOfValues.add(node.parent!!.values[indexInParent - 1])
                    listOfValues.addAll(node.values)
                    var united: BNode<T>  = BNode(t, listOfValues, node.parent)
                    node.parent!!.values.removeAt(indexInParent - 1)
                    node.parent!!.children.nodes.removeAt(indexInParent)
                    node.parent!!.children.nodes[indexInParent] = united
                    Logger.debugInfo("United with left brother.")
                    return united.values.remove(value)
                } else if(rightBrother != null && rightBrother.values.size == t) {
                    val listOfValues: MutableList<T> = node.values
                    listOfValues.add(node.parent!!.values[indexInParent])
                    listOfValues.addAll(rightBrother.values)
                    val united: BNode<T>  = BNode(t, listOfValues, node.parent)
                    node.parent!!.values.removeAt(indexInParent)
                    node.parent!!.children.nodes.removeAt(indexInParent)
                    node.parent!!.children.nodes[indexInParent] = united
                    Logger.debugInfo("United with right brother.")
                    return united.values.remove(value)
                } else {
                    Logger.error("When removing from a node with t keys couldn't find any brother with >= t keys.")
                    return false
                }
            } else {
                Logger.error("Called removing from incorrect tree. Less than t-1 value in a node.")
                return false
            }
        } else {
            Logger.debugInfo("Deleting internal node $node")
            val index: Int = node.values.indexOf(value)
            val leftChild = node.children[index]
            val rightChild = node.children[index+1]
            //both are not null because node is internal
            if(leftChild.values.size > t) {
                node.values[index] = leftChild.values[leftChild.values.lastIndex]
                leftChild.values.removeAt(leftChild.values.lastIndex)
                Logger.debugInfo("Value from left closest child moved to node.")
                return true
            } else if(rightChild.values.size > t) {
                node.values[index] = rightChild.values[0]
                rightChild.values.removeAt(0)
                Logger.debugInfo("Value from right closest child moved to node.")
                return true
            } else { //unite them
                val united: BNode<T> = BNode(t, leftChild.values + rightChild.values, node)
                node.values.removeAt(index)
                node.children.nodes.removeAt(index + 1)
                node.children.nodes[index] = united
                Logger.debugInfo("United left and right closest children.")
                return true
            }
        }
    }

    fun findNode(value: T): BNode<T>? {
        var current = root ?: return null
        while(current != null) {
            if(current.containsValue(value))
                return current
            else {
                var i: Int = 0
                while(i < current.values.size){
                    if(current.values[i] > value) {
                        current = current.children[i]
                        break
                    }
                    i++
                }
            }
        }
        return null
    }

    override fun iterator(): Iterator<Node<T>?> {
        println("Created iterator")
        return (object: Iterator<BNode<T>> {
            var queue: Queue<BNode<T>> = LinkedList<BNode<T>>()

            init {
                if(root != null)
                    queue.add(root)
            }

            override fun hasNext(): Boolean {
                return queue.isNotEmpty()
            }

            override fun next(): BNode<T> {
                var curNode = queue.poll()
                if(!curNode.isLeaf()) {
                    curNode.children.map {queue.add(it)}
                }
                return curNode
            }
        })
    }

    override fun closestBigger(node: Node<T>): Node<T>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun closestSmaller(node: Node<T>): Node<T>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}