package tools

import bst.BinarySearchTree
import common.*
import rbt.RedBlackTree

/**
 * Created by Egor Nemchinov on 03.03.17.
 * @Link github.com/ImmortalTurtle
 * SPbU, 2017
 */
class InputHandler {
    var tree:  SearchTree<Int>? = null

    //TODO: fully implement console-control
    fun start() {
        var str: String? = readLine()
        while(str != null) {
            str.toLowerCase()
            var list = str.split(" ")
            if(str.contains("bst")) {
                tree = BinarySearchTree<Int>()
                if(list.size > 1)
                    parseValuesToTree(list.subList(1, list.lastIndex + 1), tree)
            } else if(str.contains("rbt")) {
                tree = RedBlackTree<Int>()
                if(list.size > 1)
                    parseValuesToTree(list.subList(1, list.lastIndex + 1), tree)
            } else if(str.contains("draw")) {
                getTree()?.draw()
            } else if(str.contains("print")) {
                getTree()?.print()
            } else if(str.contains("end")) {
                return
            } else {
                //a string of values
                parseValuesToTree(list.subList(0, list.lastIndex + 1), tree)
            }
            str = readLine()
        }
    }

    fun parseValuesToTree(list: List<String>, tree: SearchTree<Int>?) {
        list.forEach {
            if(it.isEmpty())
                return@forEach
            if(tree == null) {
                Logger.error("Unable to add value: tree isn't initialized.")
                return
            } else {
                //+ to insert, - to remove, no sign to find
                try {
                    if(it.contains("+")) {
                        tree.insert(it.substring(1).toInt())
                    } else if(it.contains("-")) {
                        if(tree.remove(it.substring(1).toInt())) {
                            println("Removed ${it.substring(1)} from tree.")
                        } else {
                            println("Couldn't remove ${it.substring(1)} from tree.")
                        }
                    } else {
                        //if no sign then we check if this value is in the tree
                        if(tree.find(it.toInt()) != null) {
                            println("Tree contains value $it.")
                        } else {
                            println("Tree doesn't contain value $it.")
                        }
                    }
                } catch (e: Exception) {
                    Logger.error("Operations are possible only with integers.")
                    return@forEach
                }
            }
        }
    }


    fun getTree() : Tree<Int>? {
        if(tree is RedBlackTree<Int>)
            return tree as RedBlackTree<Int>
        if(tree is BinarySearchTree<Int>) {
            return tree as BinarySearchTree<Int>
        }
        return null
    }
}