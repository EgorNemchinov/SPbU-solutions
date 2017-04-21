package btree

import java.util.*

/**
 * Created by Egor Nemchinov on 09.04.17.
 * @Link github.com/ImmortalTurtle
 * SPbU, 2017
 */
class ComparableList<T: Comparable<T>>: ArrayList<T>, Comparable<Comparable<T>> {
    constructor() : super()

    constructor(values: MutableList<T>): super(values)

    override fun compareTo(other: Comparable<T>): Int {
        //todo: compareTo
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}