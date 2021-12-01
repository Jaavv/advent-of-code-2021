fun main() {
    fun part1(input: List<String>): Int {
        val result = input.map { it.toInt() }
        var prev: Int = -1
        var next: Int?
        var count = 0
        for (i in result) {
            next = i
            if (next > prev) count++
            prev = next
        }
        return count - 1
    }

    fun part2(input: List<String>): Int {
        val result2 = input.map { it.toInt() }
        val x = result2.windowed(3)
        var prev2: Int = -1
        var next2: Int?
        var count = 0
        for (i in x) {
            next2 = i.sum()
            if (next2 > prev2) count++
            prev2 = next2
        }
        return count - 1
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

