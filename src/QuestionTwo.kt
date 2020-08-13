fun main() {
    val arrayOne = arrayOf("1", "five", "2wenty", "thr33")
    val arrayTwo = arrayOf(arrayOf("1X2", "t3n"), arrayOf("1024", "5", "64"))
    val arrayThree = arrayOf(
        arrayOf(arrayOf("0", "0x2", "z3r1"), arrayOf("1", "55a46")),
        arrayOf(arrayOf("1", "2", "4"), arrayOf("0x5fp-2", "nine", "9"), arrayOf("4", "4", "4")),
        arrayOf(arrayOf("03"))
    )

    println(sum(arrayOne))
    println(sum(arrayTwo))
    println(sum(arrayThree))
}

fun sum(array: Array<*>): Int {
    var result = 0
    val p: ElementProcessor = object : ElementProcessor {
        override fun process(e: Any?) {
            val str = e.toString().replace("[^-?0-9]+".toRegex(), " ")

            val list = Regex("[0-9]+").findAll(str)
                .map(MatchResult::value)
                .toList()


            for (item in list) {
                result += item.toInt()
            }
        }
    }

    iterate(array, p)

    return result
}

fun iterate(o: Any?, p: ElementProcessor) {
    val n = java.lang.reflect.Array.getLength(o)
    for (i in 0 until n) {
        val e = java.lang.reflect.Array.get(o, i)
        if (e != null && e.javaClass.isArray) {
            iterate(e, p)
        } else {
            p.process(e)
        }
    }
}

interface ElementProcessor {
    fun process(e: Any?)
}