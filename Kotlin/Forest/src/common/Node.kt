package common

interface Node<T: Comparable<T>> {
    fun value(): NodeValue<T>?
    fun parent() : Node<T>?
    fun children(): NodeChildren<Node<T>>
}
//abstract class Node<T>(var value: T?, open var parent:Node<T>?, open var children:NodeChildren<Node<T>>) {
//    abstract var value: T?
//    abstract var parent: Node<T>?
//    abstract var children: NodeChildren<Node<T>>

//}