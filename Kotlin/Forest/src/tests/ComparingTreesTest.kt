package tests

import bst.BinarySearchTree
import btree.BTree
import common.SearchTree
import org.junit.jupiter.api.Test
import rbt.RedBlackTree
import tools.Logger
import java.util.*

/**
 * Created by Egor Nemchinov on 22.05.17.
 * SPbU, 2017
 */
class ComparingTreesTest {

    val VALUES_AMOUNT = 10000
    val SEARCH_AMOUNT = VALUES_AMOUNT
    val randomizer = Random()
    val randomizedValuesList = ArrayList<Int>(VALUES_AMOUNT)
    val sequentalValuesList = ArrayList<Int>(VALUES_AMOUNT)
    var tree: SearchTree<Int> = BinarySearchTree()

    init {
        for(i in 0..VALUES_AMOUNT-1) {
            randomizedValuesList += randomizer.nextInt()
            sequentalValuesList += i
        }
        Logger.warnings = false
        println("Initializing tests with $VALUES_AMOUNT values.")
    }

    fun printResults(operationName: String, randomized: Boolean, treeName: String, time: Float) {
        println("$operationName $VALUES_AMOUNT in ${if(randomized) "randomized" else "sequental"} $treeName has taken $time ms.")
    }

    @Test
    fun randomizedInsertionAndSearchTest() {
        tree = RedBlackTree()
        insertionRBTree(true)
        searchingRBTree(true)
        tree = BinarySearchTree()
        insertionBSTree(true)
        searchingBSTree(true)
        tree = BTree(t = 1000)
        insertionBTree(true)
        searchingBTree(true)
        println()
    }

    @Test
    fun sequentalInsertionAndSearchTest() {
        tree = RedBlackTree()
        insertionRBTree(false)
        searchingRBTree(false)
        tree = BinarySearchTree()
        insertionBSTree(false)
        searchingBSTree(false)
        tree = BTree(t = 1000)
        insertionBTree(false)
        searchingBTree(false)
        println()
    }

    fun insertionRBTree(randomized: Boolean) {
        tree = RedBlackTree()
        val beginTime = System.nanoTime()
        val list = if(randomized) randomizedValuesList else sequentalValuesList
        for(i in 0..list.size-1) {
            tree.insert(list[i])
        }
        val totalTime = (System.nanoTime() - beginTime)/1000000f
        printResults("Insertion", randomized, "RedBlackTree", totalTime)
    }

    fun searchingRBTree(randomized: Boolean) {
        val beginTime = System.nanoTime()
        val list = if(randomized) randomizedValuesList else sequentalValuesList
        for(i in 0..SEARCH_AMOUNT-1) {
            tree.find(list[i])
        }
        val totalTime = (System.nanoTime() - beginTime)/1000000f
        printResults("Searching", randomized, "RedBlackTree", totalTime)
    }

    fun insertionBSTree(randomized: Boolean) {
        val beginTime = System.nanoTime()
        val list = if(randomized) randomizedValuesList else sequentalValuesList
        for(i in 1..list.size-1) {
            tree.insert(list[i])
            if(i % 100000 == 0)
                println(i)
        }
        val totalTime = (System.nanoTime() - beginTime)/1000000f
        printResults("Insertion", randomized, "BinarySearchTree", totalTime)
    }


    fun searchingBSTree(randomized: Boolean) {
        val beginTime = System.nanoTime()
        val list = if(randomized) randomizedValuesList else sequentalValuesList
        for(i in 0..SEARCH_AMOUNT-1) {
            tree.find(list[i])
        }
        val totalTime = (System.nanoTime() - beginTime)/1000000f
        printResults("Searching", randomized, "BinarySearchTree", totalTime)
    }

    fun insertionBTree(randomized: Boolean) {
        val beginTime = System.nanoTime()
        val list = if(randomized) randomizedValuesList else sequentalValuesList
        for(i in 0..SEARCH_AMOUNT-1) {
            tree.insert(list[i])
        }
        val totalTime = (System.nanoTime() - beginTime)/1000000f
        printResults("Insertion", randomized, "BTree", totalTime)
    }

    fun searchingBTree(randomized: Boolean) {
        val beginTime = System.nanoTime()
        val list = if(randomized) randomizedValuesList else sequentalValuesList
        for(i in 0..SEARCH_AMOUNT-1) {
            tree.find(list[i])
        }
        val totalTime = (System.nanoTime() - beginTime)/1000000f
        printResults("Searching", randomized, "BTree", totalTime)
    }

}