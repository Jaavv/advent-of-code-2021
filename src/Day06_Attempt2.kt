// https://adventofcode.com/2021/day/6

/*
*  Second attempt as first one creates heap error
* */

fun main() {
    val input = readInput("Day06")[0].split(",").map { it.toInt() }
    println(day06Part1(input))
}

fun day06Part1(input: List<Int>): Long {
    val fishes: MutableMap<Int, Long> =
        mutableMapOf(0 to 0, 1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0)
    for (i in input) {
        fishes[i] = fishes.getValue(i) + 1
    }

    for (i in 0..256) {
        if (i == 0) {
            println("Day $i --- ${fishes.values} --- Total: ${fishes.values.sum()}")
        } else {
            val respawns = fishes.getValue(0)
            for (j in 1..8) {
                fishes[j - 1] = fishes.getValue(j)
            }
            fishes[6] = fishes.getValue(6) + respawns
            fishes[8] = 0
            fishes[8] = fishes.getValue(8) + respawns
            println("Day $i --- ${fishes.values} --- Total: ${fishes.values.sum()}")
        }
    }

    return fishes.values.sum() // 1629570219571
}