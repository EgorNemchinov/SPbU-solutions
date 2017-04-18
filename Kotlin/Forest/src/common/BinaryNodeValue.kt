package common

import tools.Logger

/**
 * Created by Egor Nemchinov on 08.04.17.
 * @Link github.com/ImmortalTurtle
 * SPbU, 2017
 */
class BinaryNodeValue<T: Comparable<T>>(var value: T? = null) : NodeValue<T>() {
    override fun compareTo(other: NodeValue<T>): Int {
        other as BinaryNodeValue<T>
        return value!!.compareTo(other.value!!)
    }

    override fun compareTo(other: T): Int {
        return value!!.compareTo(other)
    }

    override fun equals(other: Any?): Boolean {
        if(other == null)
            return value == null
        if (other.javaClass != javaClass) return false

        other as BinaryNodeValue<*>

        if(value != other.value)
            return false

        return true
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    override fun toString(): String {
        return value.toString()
    }
}