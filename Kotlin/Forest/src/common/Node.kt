package common

interface Node<T> {
    fun value(): T?
    fun parent() : Node<T>?
    fun leftChild() : Node<T>?
    fun rightChild() : Node<T>?

    fun isLeftChild(): Boolean {
        return parent() != null && parent()!!.leftChild() == this
    }
    fun isRightChild(): Boolean {
        return parent() != null && parent()!!.rightChild() == this
    }
}