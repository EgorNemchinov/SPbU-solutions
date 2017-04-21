package btree

import common.NodeChildren

/**
 * Created by Egor Nemchinov on 07.04.17.
 * @Link github.com/ImmortalTurtle
 * SPbU, 2017
 */
class BChildren<T>(var nodes: MutableList<T> = mutableListOf()) : NodeChildren<T>(), MutableList<T> {

    override fun first(): T? {
        return nodes.firstOrNull()
    }

    override fun last(): T? {
        return nodes.lastOrNull()
    }

    //implementing mutableList
    override val size: Int
        get() = nodes.size

    override fun contains(element: T): Boolean {
        return nodes.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return nodes.containsAll(elements)
    }

    override fun get(index: Int): T {
        return nodes.get(index)
    }

    override fun indexOf(element: T): Int {
        return nodes.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return nodes.isEmpty()
    }

    override fun iterator(): MutableIterator<T> {
        return nodes.iterator()
    }

    override fun lastIndexOf(element: T): Int {
        return nodes.lastIndexOf(element)
    }

    override fun listIterator(): MutableListIterator<T> {
        return nodes.listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<T> {
        return nodes.listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return nodes.subList(fromIndex, toIndex)
    }

    override fun add(element: T): Boolean {
        return nodes.add(element)
    }

    override fun add(index: Int, element: T) {
        return nodes.add(index, element)
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return nodes.addAll(index, elements)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return nodes.addAll(elements)
    }

    override fun clear() {
        return nodes.clear()
    }

    override fun remove(element: T): Boolean {
        return nodes.remove(element)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return nodes.removeAll(elements)
    }

    override fun removeAt(index: Int): T {
        return nodes.removeAt(index)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        return nodes.retainAll(elements)
    }

    override fun set(index: Int, element: T): T {
        return nodes.set(index, element)
    }
}