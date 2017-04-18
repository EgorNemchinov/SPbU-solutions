package common

/**
 * Created by Egor Nemchinov on 07.04.17.
 * @Link github.com/ImmortalTurtle
 * SPbU, 2017
 */

abstract class NodeChildren<out T> : Iterable<T> {
    abstract fun first(): T?
    abstract fun last(): T?
}
