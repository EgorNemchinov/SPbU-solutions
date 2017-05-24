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
internal class RedBlackTreeTimeTest {

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
        print("red nodes have black children, ")
        for(node in tree) {
            if(node!!.isLeaf() && node.isLeaf()) {
                node as RedBlackNode<*>
                if(node.isBlack)
                    return false
            }
        }
        println("leaf nodes are black.\n")
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
        val beginTime = System.nanoTime()
        for (i in 0..1000) {
            tree += randomizer.nextInt()
        }
        val totalTime = (System.nanoTime() - beginTime) / 1000000f
        println(String.format("Randomized RBTree is built with 10000 nodes.\nTime taken: %.2f ms.", totalTime))
        assertTrue(isTreeValid(tree))
    }

    @Test
    fun sequentialTreeTest() {
        val tree: RedBlackTree<Int> = RedBlackTree()
        val beginTime = System.nanoTime()
        for (i in 0..1000) {
            tree += i
        }
        val totalTime = (System.nanoTime() - beginTime) / 1000000f
        println(String.format("Sequential RBTree is built with 10000 nodes. \nTime taken: %.2f ms.", totalTime))
        assertTrue(isTreeValid(tree))
    }
}