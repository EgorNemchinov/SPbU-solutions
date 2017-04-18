package bst

import common.NodeChildren

/**
 * Created by Egor Nemchinov on 07.04.17.
 * @Link github.com/ImmortalTurtle
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}