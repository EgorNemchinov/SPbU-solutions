package bst

import common.NodeChildren

/**
 * Created by Egor Nemchinov on 07.04.17.
 * SPbU, 2017
 */
class BinaryChildren<T>(var left: T? = null, var right: T? = null) : NodeChildren<T>() {

    override fun first(): T? {
        return left
    }

    override fun last(): T? {
        return right
    }

    override fun toString(): String {
        return left.toString()+" "+right.toString()
    }

    override fun iterator(): Iterator<T> {
        return (object: Iterator<T> {
            var curNode: T? = null
            var nextNode: T? = null

            init {
                if(left != null)
                    curNode = left
                else if(right != null)
                    curNode = right
                nextNode = curNode
            }

            override fun hasNext(): Boolean {
                if(nextNode == null)
                    return false
                return !nextNode!!.equals(null)
            }

            override fun next(): T {
                curNode = nextNode
                if(curNode == left)
                    nextNode = right
                else if(curNode == right)
                    nextNode = null
                return curNode!!
            }
        })
    }
}