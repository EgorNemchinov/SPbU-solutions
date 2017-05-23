package tools

import bst.BinarySearchTree
import btree.BTree
import common.*
import rbt.RedBlackTree

/**
 * Created by Egor Nemchinov on 03.03.17.
 * SPbU, 2017
 */
class InputHandler {
    var tree:  SearchTree<Int>? = null

    val INIT_STRING = """To find out possible commands, type "help" """
    val HELP_STRING = """To create new tree, type:
    "rbt" for RedBlackTree
    "bst" for BinarySearchTree
    "bt" for BTree

To insert values, type +%VALUE%, for example: "+3 +7 +5"
To remove values, type -%VALUE%, for example: "-3 -7 -5"
To find values, type %VALUE%, for example: "3 7 5"
"""
    init {
        println(INIT_STRING)
    }

    fun start() {
        var str: String? = readLine()
        while(str != null) {
            str.toLowerCase()
            var list = str.split(" ")
            if(str.contains("help")) {
                println(HELP_STRING)
            } else if(str.contains("bst")) {
                tree = BinarySearchTree<Int>()
                if(list.size > 1)
                    parseValuesToTree(list.subList(1, list.lastIndex + 1), tree)
            } else if(str.contains("rbt")) {
                tree = RedBlackTree<Int>()
                if(list.size > 1)
                    parseValuesToTree(list.subList(1, list.lastIndex + 1), tree)
            } else if(str.contains("bt")) {
                val split = str.split(" ")
                if(split.size > 1) {
                    tree = BTree<Int>(t = Integer.parseInt(split[1]));
                } else
                    tree = BTree<Int>()
            }
            else if(str.contains("draw")) {
                tree?.draw()
            } else if(str.contains("print")) {
                tree?.print()
            } else if(str.contains("count")) {
                var tree = tree
                if(tree != null && tree is RedBlackTree)
                    println("${tree.countBlackNodesToLeaf()} black nodes from root to leaf.")
            } else if(str.contains("debug")) {
                if(list[1] == "on")
                    Logger.debugging = true
                else if(list[2] == "off") {
                    Logger.debugging = false
                }
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
        Logger.debugInfo("parsing values for tree")
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

}