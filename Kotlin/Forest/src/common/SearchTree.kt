package common

interface SearchTree<T> {
    fun insert(value: T)
    fun remove(value: T): Boolean
    fun find(value: T): Node<T>?
    fun min(): Node<T>?
    fun max(): Node<T>?

    operator fun plusAssign(value: T) {
        insert(value)
    }
    operator fun minusAssign(value: T) {
        remove(value)
    }
}