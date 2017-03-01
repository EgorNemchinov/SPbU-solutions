package common

class ChildrenNodes<T>(left: T? = null, right: T? = null) {
    var left: T? = left
        set(node) {

        }
    var right: T? = left
        set(node) {

        }


    fun toList(): List<T> {
        var list: List<T> = listOf()
        if(left != null)
            list += left!!
        if(right != null)
            list += right!!
        return list
    }

    override fun toString(): String {
        return ""
    }
}