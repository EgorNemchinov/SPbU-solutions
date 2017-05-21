package rbt

import bst.BinarySearchNode
import common.*
import tools.Logger

class RedBlackTree<T: Comparable<T>>(var root: RedBlackNode<T>? = null) : SearchTree<T>() {
    //it's better than null because it's black
    val nil: RedBlackNode<T> = RedBlackNode(BinaryNodeValue(), isBlack = true)

    override fun root(): RedBlackNode<T>? {
        return root
    }

    override fun insert(value: T) {
        if (root == null) {
            root = RedBlackNode(value)
            root!!.isBlack = true
            root!!.left = nil
            root!!.right = nil
            return
        }
        var inserted: Boolean = false
        var currentNode: RedBlackNode<T> = root!!
        while (!inserted) {
            if (currentNode.value < value) {
                if (currentNode.right == nil || currentNode.right == null) {
                    currentNode.right = RedBlackNode(value)
                    currentNode = currentNode.right!!
                    inserted = true
                    break
                } else
                    currentNode = currentNode.right!!
            } else if (currentNode.value > value) {
                if (currentNode.left == nil || currentNode.left == null) {
                    currentNode.left = RedBlackNode(value)
                    currentNode = currentNode.left!!
                    inserted = true
                    break
                } else
                    currentNode = currentNode.left!!
            } else {
                Logger.warning("Attempt to add node that is already contained in this tree.")
                return
            }
        }
        currentNode.left = nil
        currentNode.right = nil
        Logger.debugInfo("Inserted ${currentNode.value}. Correction is ahead.")
        correctAfterInsertion(currentNode)
        root!!.isBlack = true
    }

    fun correctAfterInsertion(node: RedBlackNode<T>) {
        Logger.debugInfo("correctAfterInsertion called for $node")
        //if parent isn't red no need to fix
        if (node.parent != null && node.parent!!.isBlack)
            return
        var currentNode = node
        var grandparent = currentNode.grandparent()
        if (grandparent == null) {
            root!!.isBlack = true
            return
        }
        var uncle = currentNode.uncle()
        if (currentNode.parent!!.isLeftChild()) {
            if (!uncle!!.isBlack) {
                //uncle is red then recolor uncle parent and grandparent
                currentNode.parent!!.isBlack = true
                uncle.isBlack = true
                grandparent.isBlack = false
                correctAfterInsertion(grandparent)
            } else {
                //uncle is black
                if (currentNode.isRightChild()) {
                    //converting left triangle to left line
                    currentNode.parent!!.rotateLeft()
                    currentNode = currentNode.left!!
                }
                //left line
                grandparent.rotateRight()
                currentNode.parent!!.isBlack = true
                grandparent.isBlack = false
            }
        } else if (currentNode.parent!!.isRightChild()) {
            if (!uncle!!.isBlack) {
                currentNode.parent!!.isBlack = true
                uncle.isBlack = true
                grandparent.isBlack = false
                correctAfterInsertion(grandparent)
            } else {
                if (currentNode.isLeftChild()) {
                    //converting right triangle to right line
                    currentNode.parent!!.rotateRight()
                    currentNode = currentNode.right!!
                }
                //right line
                grandparent.rotateLeft()
                currentNode.parent!!.isBlack = true
                grandparent.isBlack = false
            }
        }
        if (root!!.parent != null) {
            root = calculateRoot() as RedBlackNode<T>?
        }
        if (currentNode.parent != null && !currentNode.parent!!.isBlack) {
            //continue for parent if it's red
            correctAfterInsertion(currentNode.parent!!)
        }
        Logger.debugInfo("correctAfterInsertion finished")
    }


    override fun find(value: T): RedBlackNode<T>? {
        if (root == null)
            return null
        var currentNode: RedBlackNode<T> = root!!
        while (true) {
            if (currentNode.value!! < value) {
                if (currentNode.right == nil) { //mb check for null too
                    return null
                }
                currentNode = currentNode.right!!
            } else if (currentNode.value!! > value) {
                if (currentNode.left == nil) {
                    return null
                }
                currentNode = currentNode.left!!
            } else {
                return currentNode
            }
        }
    }

    override fun remove(value: T): Boolean {
        val deletedNode: RedBlackNode<T>? = find(value) ?: return false
        return remove(deletedNode!!)
    }

    private fun remove(deletedNode: RedBlackNode<T>): Boolean {
//        deletedNode != nil and not null
        var doubleBlackNode: RedBlackNode<T>? = null
        if(deletedNode.left!!.isNil() && deletedNode.right!!.isNil()) {
            doubleBlackNode = deletedNode.parent
            deletedNode.setParentsReferenceTo(nil)
        } else if(deletedNode.right!!.isNil()) {
            doubleBlackNode = deletedNode.left
            deletedNode.setParentsReferenceTo(deletedNode.left)
        } else if(deletedNode.left!!.isNil()) {
            doubleBlackNode = deletedNode.right
            deletedNode.setParentsReferenceTo(deletedNode.right)
        } else {
            var successor: RedBlackNode<T> = RedBlackTree(deletedNode.right!!).min() as RedBlackNode<T>
            deletedNode.value = successor.value()
            remove(successor)
        }
        if(deletedNode.isBlack ) { //balance was broken
            if(doubleBlackNode == null)
                Logger.error("doubleBlackNode after removal is null");
            fixAfterRemoval(doubleBlackNode!!)
        }

        return true
    }

    private fun fixAfterRemoval(deletedNode: RedBlackNode<T>) {
        var current = deletedNode
        Logger.debugInfo("fixAfterRemoval called for $current")
        while (current != root && current.isBlack) {
            var leftChild = current.left
            var rightChild = current.right
            var brother = current.brother()!!
            if (!rightChild!!.isBlack) { //1st step
                current.isBlack = false
                rightChild.isBlack = true
                current.rotateLeft()
            } else if (!leftChild!!.isBlack) {
                current.isBlack = false
                leftChild.isBlack = true
                current.rotateRight()
            }
            if (current.isLeftChild()) {
                if (brother.isBlack) { //2nd step
                    if (brother.left!!.isBlack && brother.right!!.isBlack) {
                        brother.recolor()
                        current.isBlack = true //the end
                    }
                    if (!brother.left!!.isBlack && brother.right!!.isBlack) {
                        brother.recolor()
                        brother.left!!.recolor()
                        brother.rotateRight()
                    }
                    if (!brother.right!!.isBlack) {
                        brother.isBlack = current.isBlack
                        current.isBlack = true
                        brother.right!!.isBlack = true
                        current.rotateLeft() //the end
                    }
                }
            } else {
                if (brother.isBlack) { //2nd step
                    if (brother.left!!.isBlack && brother.right!!.isBlack) {
                        brother.recolor()
                        current.isBlack = true
                        return
                    }
                    if (!brother.right!!.isBlack && brother.left!!.isBlack) {
                        brother.recolor()
                        brother.right!!.recolor()
                        brother.rotateLeft()
                    }
                    if (!brother.left!!.isBlack) {
                        brother.isBlack = current.isBlack
                        current.isBlack = true
                        brother.left!!.isBlack = true
                        current.rotateRight()
                        return
                    }
                }
            }
        }
    }


        /*

    private fun remove(deletedNode: RedBlackNode<T>): Boolean {
        Logger.debugInfo("RBT - removing node $deletedNode")
        when (deletedNode.amountOfChildren()) {
            0, 1 -> {
                var successor = deletedNode.childrenToList().firstOrNull() ?: nil
                if (deletedNode.parent == null) {
                    root = successor
                    successor.left = deletedNode.left
                    successor.right = deletedNode.right
                }
                root!!.isBlack = true
                if (!successor.isBlack || !deletedNode.isBlack) {
                    //one of them is red
                    deletedNode.setParentsReferenceTo(successor)
                    successor.isBlack = true
                    return true
                } else {
                    //make deletedNode nil node
                    deletedNode.value = null
                    deletedNode.left = null
                    deletedNode.right = null
                    fixDoubleBlackNode(deletedNode)
                }
            }
            2 -> {
                Logger.debugInfo("2 case")
                var successor = (closestBigger(deletedNode) ?: closestSmaller(deletedNode)) as RedBlackNode<T>?
                if (successor == null) {
                    //happens only when deletedNode's children are null
                    root = null
                    return true
                }
                deletedNode.value = successor.value
                return remove(successor)
            }
            else -> {
                Logger.debugInfo("Amount of children is ${deletedNode.amountOfChildren()}")
            }
        }
        return true
    }

    //scary shit
    private fun fixDoubleBlackNode(doubleBlackNode: RedBlackNode<T>): Boolean {
        Logger.debugInfo("Fixing double black node $doubleBlackNode")
        //while node is double black and not root
        while (doubleBlackNode != root && countBlackNodesToLeaf(doubleBlackNode.parent!!) == null) {
            var brother: RedBlackNode<T> = doubleBlackNode.brother()!!
            //split into two mirror-equal cases
            if (brother.isRightChild()) {
                if (brother.isBlack) {
                    //has children because tree was balanced before removal
                    //which means that at least there are at least 2 black nodes including this
                    if (!brother.left!!.isBlack) {
                        brother.rotateRight()
                        doubleBlackNode.parent!!.rotateLeft() //a.k.a. brother.grandparent (after rotation)
                    } else if (!brother.right!!.isBlack) {
                        doubleBlackNode.parent!!.rotateLeft()
                    } else {
                        //both children are black
                        if (doubleBlackNode.parent!!.isBlack) {
                            doubleBlackNode.parent!!.isBlack = false //fixme: is it needed to make it red?
                            Logger.debugInfo("$doubleBlackNode -> ${doubleBlackNode.parent}")
                            return fixDoubleBlackNode(doubleBlackNode.parent!!)
                        } else {
                            doubleBlackNode.parent!!.isBlack = true
                            return true
                        }
                    }
                } else {
                    //brother is red
                    brother.parent!!.recolor()
                    brother.recolor()
                    brother.parent!!.rotateLeft()
                    //proceeds to case: black brother with black children
                    continue
                }
            } else {
                if (brother.isBlack) {
                    //has children because tree was balanced before removal
                    //which means that at least there are at least 2 black nodes including this
                    if (!brother.right!!.isBlack) {
                        brother.rotateLeft()
                        doubleBlackNode.parent!!.rotateRight()
                    } else if (!brother.left!!.isBlack) {
                        doubleBlackNode.parent!!.rotateRight()
                    } else {
                        if (doubleBlackNode.parent!!.isBlack) {
                            doubleBlackNode.parent!!.isBlack = false //fixme: is it needed to make it red?
                            Logger.debugInfo("$doubleBlackNode -> ${doubleBlackNode.parent}")
                            return fixDoubleBlackNode(doubleBlackNode.parent!!)
                        } else {
                            doubleBlackNode.parent!!.isBlack = true
                            return true
                        }
                    }
                } else {
                    //brother is red
                    brother.parent!!.recolor()
                    brother.recolor()
                    brother.parent!!.rotateRight()
                    //proceeds to case: black brother with black children
                    continue
                }
            }
        }
        if(doubleBlackNode.parent == null)
            root = doubleBlackNode
        if(doubleBlackNode == root)
            doubleBlackNode.isBlack
        return true
    }*/
    //returns Int if tree is correctAfterInsertion, null if it's not balanced somewhere
    fun countBlackNodesToLeaf(node: RedBlackNode<T> = root!!): Int? {
        var countLeft: Int?
        var countRight: Int?
        if (node.left == null)
            countLeft = 0
        else
            countLeft = countBlackNodesToLeaf(node.left!!)
        if (node.right == null)
            countRight = 0
        else
            countRight = countBlackNodesToLeaf(node.right!!)
        if (countLeft == null || countRight == null || countLeft != countRight)
            return null
        else
            return countLeft + (if (node.isBlack) 1 else 0)
    }

    override fun closestBigger(node: Node<T>): RedBlackNode<T>? {
        node as RedBlackNode<T>
        var currentNode: RedBlackNode<T>? = node.right
        if(currentNode != null && currentNode.value() != null) {
            //go as left as possible from node's right child
            while(currentNode!!.left != null && currentNode.left!!.value() != null) {
                currentNode = currentNode.left
            }
            return currentNode
        }
        else if(node.parent != null){
            if(node.isLeftChild()) {
                return node.parent
            }
            else {
                //go on parent's line until node isn't left child or it's parent is null
                var current: RedBlackNode<T>? = node
                while(current != null && current.isRightChild()) {
                    current = current.parent
                }
                if(current == null || current.parent == null) {
                    return null
                } else {
                    return current.parent!!
                }
            }
        } else {
            return null
        }
    }

    override fun closestSmaller(node: Node<T>): RedBlackNode<T>? {
        node as RedBlackNode<T>
        var currentNode: RedBlackNode<T>? = node.left ?: node.parent ?: return null
        if (currentNode != null && currentNode.value != null) {
            //go as left as possible from node's right child
            while (currentNode!!.right != null && currentNode.right!!.value() != null) {
                currentNode = currentNode.right
            }
            return currentNode
        } else if (node.parent != null) {
            if (node.isRightChild()) {
                return node.parent
            } else {
                //go on parent's line until node isn't left child or it's parent is null
                var current: RedBlackNode<T>? = node
                while (current != null && current.isLeftChild()) {
                    current = current.parent
                }
                if (current == null || current.parent == null) {
                    return null
                } else {
                    return current.parent!!
                }
            }
        } else {
            return null
        }
    }

}
