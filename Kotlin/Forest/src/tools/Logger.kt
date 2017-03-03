package tools

/**
* Created by Egor Nemchinov on 03.03.17.
* @Link github.com/ImmortalTurtle
* SPbU, 2017
*/
class Logger {
    companion object {
        var debugging: Boolean = false

        fun debugInfo(s: String) {
            if(debugging)
                println("Debug: " + s)
        }

        fun error(s: String) {
            println("Error: $s")
        }

        fun warning(s: String) {
            println("Warning: $s")
        }

    }
}
