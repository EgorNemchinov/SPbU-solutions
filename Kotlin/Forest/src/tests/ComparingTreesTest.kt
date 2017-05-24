package tests

import bst.BinarySearchTree
import common.SearchTree
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import rbt.RedBlackTree
import tools.Logger
import java.util.*

/**
 * Created by Egor Nemchinov on 22.05.17.
 * SPbU, 2017
 */
class ComparingTreesTest {

    val VALUES_AMOUNT = 1000000
    val SEARCH_AMOUNT = 1000000
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

    @Test
    fun randomizedInsertionAndSearchTest() {
        tree = RedBlackTree()
        insertionRBTree(true)
        searchingRBTree(true)
        tree = BinarySearchTree()
        insertionBSTree(true)
        searchingBSTree(true)
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
        println()
    }

    fun insertionRBTree(randomized: Boolean) {
        tree = RedBlackTree()
        val beginTime = System.nanoTime()
        val list = if(randomized) randomizedValuesList else sequentalValuesList
        for(i in 0..list.size-1) {
            tree.insert(randomizedValuesList[i])
        }
        val totalTime = (System.nanoTime() - beginTime)/1000000f
        println("Insertion in ${if(randomized) "randomized" else "sequental"} RedBlackTree has taken $totalTime ms")
    }

    fun searchingRBTree(randomized: Boolean) {
        val beginTime = System.nanoTime()
        val list = if(randomized) randomizedValuesList else sequentalValuesList
        for(i in 0..SEARCH_AMOUNT-1) {
            tree.find(list[i])
        }
        val totalTime = (System.nanoTime() - beginTime)/1000000f
        println("Searching $SEARCH_AMOUNT values in ${if(randomized) "randomized" else "sequental"} RedBlackTree has taken $totalTime ms")
    }

    fun insertionBSTree(randomized: Boolean) {
        val beginTime = System.nanoTime()
        val list = if(randomized) randomizedValuesList else sequentalValuesList
        for(i in 1..list.size-1) {
            tree.insert(randomizedValuesList[i])
        }
        val totalTime = (System.nanoTime() - beginTime)/1000000f
        println("Insertion in ${if(randomized) "randomized" else "sequental"} BinarySearchTree has taken $totalTime ms")
    }


    fun searchingBSTree(randomized: Boolean) {
        val beginTime = System.nanoTime()
        val list = if(randomized) randomizedValuesList else sequentalValuesList
        for(i in 0..SEARCH_AMOUNT-1) {
            tree.find(list[i])
        }
        val totalTime = (System.nanoTime() - beginTime)/1000000f
        println("Searching $SEARCH_AMOUNT values in ${if(randomized) "randomized" else "sequental"} BinarySearchTree has taken $totalTime ms")
    }
}