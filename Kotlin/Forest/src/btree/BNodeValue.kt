package btree

import common.NodeValue

/**
 * Created by Egor Nemchinov on 08.04.17.
 * SPbU, 2017
 */
class BNodeValue<T: Comparable<T>>(var values: ComparableList<T> = ComparableList()) : NodeValue<ComparableList<T>>(), MutableList<T> {
    override fun compareTo(other: NodeValue<ComparableList<T>>): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun compareTo(other: ComparableList<T>): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other == null)
            return false
        if(other.javaClass != this.javaClass)
            return false

        other as BNodeValue<*>
        var equal = true
        other.values.mapIndexed { index, comparable ->  equal = equal && comparable == this.values[index]}
        return equal
    }

    operator fun plus(value: BNodeValue<T>): BNodeValue<T> {
        values.addAll(value.values)
        return this
    }

    //implementing MutableList<T>
    override val size: Int
        get() = values.size

    override fun contains(element: T): Boolean {
        return values.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return values.containsAll(elements)
    }

    override fun get(index: Int): T {
        return values.get(index)
    }

    override fun indexOf(element: T): Int {
        return values.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return values.isEmpty()
    }

    override fun iterator(): MutableIterator<T> {
        return values.iterator()
    }

    override fun lastIndexOf(element: T): Int {
        return values.lastIndexOf(element)
    }

    override fun listIterator(): MutableListIterator<T> {
        return values.listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<T> {
        return values.listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return values.subList(fromIndex, toIndex)
    }

    override fun add(element: T): Boolean {
        return values.add(element)
    }

    override fun add(index: Int, element: T) {
        return values.add(index, element)
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return values.addAll(index, elements)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return values.addAll(elements)
    }

    override fun clear() {
        return values.clear()
    }

    override fun remove(element: T): Boolean {
        return values.remove(element)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return values.removeAll(elements)
    }

    override fun removeAt(index: Int): T {
        return values.removeAt(index)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        return values.retainAll(elements)
    }

    override fun set(index: Int, element: T): T {
        return values.set(index, element)
    }
}