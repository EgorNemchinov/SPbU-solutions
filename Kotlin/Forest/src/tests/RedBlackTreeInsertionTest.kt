package tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import rbt.RedBlackNode
import rbt.RedBlackTree

/**
 * Created by Egor Nemchinov on 24.05.17.
 * @Link github.com/ImmortalTurtle
 * SPbU, 2017
 */
class RedBlackTreeInsertionTest {
    @Test
    fun insertOneKey() {

        val expectedTree = RedBlackTree<Int>()

        val root = RedBlackNode(20)
        root.isBlack = true

        expectedTree.root = root

        val actualTree = RedBlackTree<Int>()

        actualTree.insert(20)

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun insertRightRightCase() {

        val expectedTree = RedBlackTree<Int>()

        val root = RedBlackNode(25)
        root.isBlack = true

        val node1 = RedBlackNode(20)
        root.left = node1
        node1.parent = root

        val node2 = RedBlackNode(30)
        root.right = node2
        node2.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int>()

        for (i in listOf(20, 25, 30)) {
            actualTree.insert(i)
        }

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun insertRightLeftCase() {

        val expectedTree = RedBlackTree<Int>()

        val root = RedBlackNode(25)
        root.isBlack = true

        val node1 = RedBlackNode(20)
        root.left = node1
        node1.parent = root

        val node2 = RedBlackNode(30)
        root.right = node2
        node2.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int>()

        for (i in listOf(20, 30, 25)) {
            actualTree.insert(i)
        }

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun insertLeftLeftCase() {

        val expectedTree = RedBlackTree<Int>()

        val root = RedBlackNode(25)
        root.isBlack = true

        val node1 = RedBlackNode(20)
        root.left = node1
        node1.parent = root

        val node2 = RedBlackNode(30)
        root.right = node2
        node2.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int>()

        for (i in listOf(30, 25, 20)) {
            actualTree.insert(i)
        }

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun insertLeftRightCase() {

        val expectedTree = RedBlackTree<Int>()

        val root = RedBlackNode(25)
        root.isBlack = true

        val node1 = RedBlackNode(20)
        root.left = node1
        node1.parent = root

        val node2 = RedBlackNode(30)
        root.right = node2
        node2.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int>()

        for (i in listOf( 30, 20, 25)) {
            actualTree.insert(i)
        }

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun insertRecoloringRelatives() {

        val expectedTree = RedBlackTree<Int>()

        val root = RedBlackNode(25)
        root.isBlack = true

        val node1 = RedBlackNode(20)
        root.left = node1
        node1.parent = root
        node1.isBlack = true

        val node2 = RedBlackNode(30)
        root.right = node2
        node2.parent = root
        node2.isBlack = true

        val node3 = RedBlackNode(40)
        node2.right = node3
        node3.parent = node2

        expectedTree.root = root

        val actualTree = RedBlackTree<Int>()

        for (i in listOf(20, 25, 30, 40)) {
            actualTree.insert(i)
        }

        assertEquals(expectedTree, actualTree)

    }

    /* Insert according to the following scenario:
     * 1) Insert root(20) -> recoloring root in black
     * 2) Insert node(25), node(30, 30) -> RightRight case
     * 3) Insert node(40) -> recoloring father, uncle, grandfather; recoloring root in black
     * 4) Insert node(35) -> RightLeft case
     * 5) Insert node(27) -> recoloring father, uncle, grandfather
     * 6) Insert node(26) -> LeftLeft case
     * 7) Insert node(10)
     * 8) Insert node(15) -> LeftRight case
     * 9) Insert node(28) -> recoloring father, uncle, grandfather;
     *                           RightLeft case(for grandfather);
     * 10) Insert illegal key and value
     */
    @Test
    fun insertByScenario() {

        val expectedTree = RedBlackTree<Int>()

        val root = RedBlackNode(27)
        root.isBlack = true

        val node1 = RedBlackNode(35)
        root.right = node1
        node1.parent = root

        val node2 = RedBlackNode(30)
        node1.left = node2
        node2.parent = node1
        node2.isBlack = true

        val node3 = RedBlackNode(40)
        node1.right = node3
        node3.parent = node1
        node3.isBlack = true

        val node4 = RedBlackNode(28)
        node2.left = node4
        node4.parent = node2

        val node5 = RedBlackNode(25)
        root.left = node5
        node5.parent = root

        val node6 = RedBlackNode(26)
        node5.right = node6
        node6.parent = node5
        node6.isBlack = true

        val node7 = RedBlackNode(15)
        node5.left = node7
        node7.parent = node5
        node7.isBlack = true

        val node8 = RedBlackNode(20)
        node7.right = node8
        node8.parent = node7

        val node9 = RedBlackNode(10)
        node7.left = node9
        node9.parent = node7

        expectedTree.root = root

        val actualTree = RedBlackTree<Int>()

        for (i in listOf(20, 25, 30, 40, 35, 27, 26, 10, 15, 28)) {
            actualTree.insert(i)
        }

        assertEquals(expectedTree, actualTree)

    }
}