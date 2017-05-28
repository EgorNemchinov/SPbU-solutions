package btree

import common.Node
import common.NodeValue

/**
 * Created by Egor Nemchinov on 29.03.17.
 * SPbU, 2017
 */

class BNode<T: Comparable<T>> constructor(var t: Int = 2, var values: BNodeValue<T> = BNodeValue(),
                              var parent: BNode<T>? = null, var children: BChildren<BNode<T>> = BChildren())
    : Node<T>{

    constructor(t: Int = 2, valuesList: MutableList<T>, parent: BNode<T>? = null, children: MutableList<BNode<T>> = mutableListOf())
            : this(t, BNodeValue(ComparableList(valuesList)), parent, BChildren(children))

    override fun value(): NodeValue<T>? {
        TODO()
    }

    override fun parent(): BNode<T>? {
        return parent
    }

    override fun children(): BChildren<Node<T>> {
        return children as BChildren<Node<T>>
    }

    fun insertValue(value: T) {
        var i: Int = 0
        while(i < values.size){
            if(values[i] > value) {
                values.add(i, value)
                return
            }
            i++
        }
        values.add(i, value)
    }

    fun containsValue(value: T): Boolean {
        return values.contains(value)
    }

    //careful: parent mustn't be null
    fun indexInParentsChildren(): Int = parent!!.children.nodes.indexOf(this)

    fun leftClosestBrother(): BNode<T>? {
        if(parent == null)
            return null
        var index = parent!!.children.nodes.indexOf(this)
        if(index > 0)
            return parent!!.children[index-1]
        else
            return null
    }

    fun rightClosestBrother(): BNode<T>? {
        if(parent == null)
            return null
        var index = parent!!.children.nodes.indexOf(this)
        if(index < parent!!.children.nodes.size - 1)
            return parent!!.children[index+1]
        else
            return null
    }

    fun full(): Boolean {
        return values.size>= 2*t-1
    }

    override fun isLeaf(): Boolean {
        return children.isEmpty()
    }

    override fun toString(): String {
        return "${values.values.joinToString(separator = "|", prefix = "[", postfix = "]")}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as BNode<*>

        if(values != other.values)
            return false
        if(children != other.children)
            return false
        if(t != other.t)
            return false
        if(parent?.values!! == other.parent?.values)
            return false

        return true
    }
}
