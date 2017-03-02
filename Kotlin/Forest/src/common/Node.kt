package common

interface Node<T> {
    fun value(): T?
    fun parent() : Node<T>?
    fun leftChild() : Node<T>?
    fun rightChild() : Node<T>?
}