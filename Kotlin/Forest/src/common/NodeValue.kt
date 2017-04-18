package common

/**
 * Created by Egor Nemchinov on 08.04.17.
 * @Link github.com/ImmortalTurtle
 * SPbU, 2017
 */
abstract class NodeValue<T: Comparable<T>>: Comparable<NodeValue<T>> {
    abstract override fun compareTo(other: NodeValue<T>): Int
    abstract operator fun compareTo(other: T): Int
    abstract override fun equals(other: Any?): Boolean
}