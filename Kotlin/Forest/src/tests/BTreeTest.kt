package tests

import btree.BNode
import btree.BTree
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

/**
 * Created by Egor Nemchinov on 22.05.17.
 * SPbU, 2017
 */
internal class BTreeTest {
    val VALUES_AMOUNT = 10000
    val T = 1000

    fun isTreeValid(tree: BTree<Int>, givenSet: Set<Int>): Boolean {
        if(tree.root == null)
            return true
        val valuesSet = HashSet<Int>()
        for(node in tree) {
            node as BNode<Int>
            if(node == tree.root) {
                if(node.parent != null)
                    return false
            } else if(node.values.size > node.t*2 - 1 || node.values.size < node.t - 1 )
                return false
            if(!node.isLeaf() && (node.values.size + 1 != node.children.size))
                return false
            val valuesSorted= (0..node.values.size-2).firstOrNull { node.values[it] > node.values[it + 1] }
            if(valuesSorted != null)
                return false
            valuesSet.addAll(node.values.values as List<Int>)
        }
        println("B-Tree is correct.")
        if(valuesSet == givenSet) {
            println("All values were added.")
            return true
        } else {
            println("Some values weren't added.")
            return false
        }
    }

    @Test
    fun randomizedTreeTest() {
        val randomizer: Random = Random()
        val tree: BTree<Int> = BTree(t = T)
        var givenValues = emptySet<Int>()
        for (i in 0..VALUES_AMOUNT-1) {
            givenValues += randomizer.nextInt()
            tree.insert(givenValues.last())
        }
        println("Randomized BTree is built.")
        Assertions.assertTrue(isTreeValid(tree, givenValues))
        println()
    }

    @Test
    fun sequentialTreeTest() {
        val tree: BTree<Int> = BTree(t = T)
        for (i in 0..VALUES_AMOUNT-1) {
            tree += i
        }
        println("Sequential BTree is built.")
        Assertions.assertTrue(isTreeValid(tree, (0..VALUES_AMOUNT-1).toSet()))
        println()
    }
}