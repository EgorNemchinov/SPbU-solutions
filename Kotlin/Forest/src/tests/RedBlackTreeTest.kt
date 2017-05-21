package tests

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import rbt.RedBlackNode
import rbt.RedBlackTree
import java.util.*

/**
 * Created by Egor Nemchinov on 15.03.17.
 * SPbU, 2017
 */
internal class RedBlackTreeTest {

    fun isTreeValid(tree: RedBlackTree<Int>): Boolean {
        if(tree.root == null)
            return true
        if(!tree.root!!.isBlack)
            return false
        print("Root is black, ")
        if(tree.countBlackNodesToLeaf(tree.root!!) == null)
            return false
        print("black nodes to leaf is the same, ")
        if(hasStreakOfRed(tree.root!!))
            return false
        println("red nodes have black children.")
        return true
    }

    fun hasStreakOfRed(node: RedBlackNode<*>): Boolean {
        if (!node.isBlack) {
            if (node.childrenToList().filter { !it.isBlack }.isNotEmpty())
                return true
        }
        var result = false
        for(child in node.childrenToList())
            result = result || hasStreakOfRed(child)
        return result
    }

    @Test
    fun randomizedTreeTest() {
        val randomizer: Random = Random()
        val tree: RedBlackTree<Int> = RedBlackTree()
        for (i in 0..10000) {
            tree += randomizer.nextInt()
        }
        println("Randomized RBTree is built.")
        assertTrue(isTreeValid(tree))
    }

    @Test
    fun sequentialTreeTest() {
        val tree: RedBlackTree<Int> = RedBlackTree()
        for (i in 0..10000) {
            tree += i
        }
        println("Sequential RBTree is built.")
        assertTrue(isTreeValid(tree))
    }
}