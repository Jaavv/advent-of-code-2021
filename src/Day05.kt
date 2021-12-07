// https://adventofcode.com/2021/day/5

fun main() {
    val input = readInput("Day05")
    println("PART1 POINTS: ${day5part1(input)}")
}

fun day5part1(input: List<String>): Int {
    val coordinates = input.map { inputParse(it) }.filter { straightLine(it) }
    val maxXX = coordinates.map { largestCoord(it) }.maxOf { it.x }
    val maxYY = coordinates.map { largestCoord(it) }.maxOf { it.y }
    val graph: MutableList<MutableList<Int>> = mutableListOf((mutableListOf()))
    var maxX = 0
    var maxY = 0
    for (i in input) {
        val data = inputParse(i)
        if (straightLine(data)) {
            val lines = line(data)
            val lmaxX = lines.maxOfOrNull { it.x }
            val lmaxY = lines.maxOfOrNull { it.y }
//            if (maxX < lmaxX!!) maxX = lmaxX
//            if (maxY < lmaxY!!) maxY = lmaxY
//            graph.addAll()


            if (graph.size < lmaxY!!) {
                val diffY = lmaxY - graph.size
                expandColumn(graph, diffY, 1 + lmaxX!!)
//                graph.addAll(MutableList(diffY) { MutableList(lmaxX!!) { 0 } })
                maxY = lmaxY
            }
            val currentRow = graph.map { it.size }.maxOf { it }
            if (currentRow < lmaxX!!) {
                val diffX = lmaxX - currentRow
                for (row in graph) expandRow(row, diffX)
                maxX = lmaxX
            }
//            for (row in graph) {
//                if (row.size < maxX!!) {
//                    val diffX = maxX - row.size + 1
//                    for (i in row.size + 1..maxX + 1)
//                        row.add(0)
//                }
//            }
            for (l in lines) {
                try {
                    graph[l.y][l.x] += 1
                } catch (e: Exception) {
                    println(data)
                    println("COLUMNS: ${graph.size}, ROWS: ${graph[0].size}")
                    println(graph[l.y])
                    break
                }
            }
        }
    }

    return graph.flatten().count { it >= 2 }
}

data class Coordinate(val x: Int, val y: Int)
data class Input(val from: Coordinate, val to: Coordinate)
enum class Direction { HORIZONTAL, VERTICAL }

fun inputParse(input: String): Input {
    val coord = input.split(" -> ")
    val x1y1 = coord.first().split(",").map { it.toInt() }
    val x2y2 = coord.last().split(",").map { it.toInt() }
    val from = Coordinate(x1y1.first(), x1y1.last())
    val to = Coordinate(x2y2.first(), x2y2.last())
    return Input(from, to)
}

fun largestCoord(input: Input): Coordinate {
    return Coordinate(maxOf(input.from.x, input.to.x), maxOf(input.from.y, input.to.y))
}

fun straightLine(input: Input): Boolean {
    return (input.from.x == input.to.x) or (input.from.y == input.to.y)
}

fun direction(from: Coordinate, to: Coordinate): Direction {
    return if (from.x == to.x) Direction.VERTICAL else Direction.HORIZONTAL
}

fun expandRow(row: MutableList<Int>, amount: Int) {
    for (i in 0..amount) row.add(0)
}

fun expandColumn(graph: MutableList<MutableList<Int>>, amount: Int, fill: Int) {
    for (i in 0..amount) {
        graph.add(MutableList(fill) { 0 })
    }
}

fun line(input: Input): List<Coordinate> {
    val list = mutableListOf<Coordinate>()
    var start = 0;
    var finish = 0;
    var base = 0
    val direction = direction(input.from, input.to)

    if (direction == Direction.HORIZONTAL) {
        start = input.from.x; finish = input.to.x; base = input.from.y
    }
    if (direction == Direction.VERTICAL) {
        start = input.from.y; finish = input.to.y; base = input.from.x
    }

    if (start > finish) start = finish.also { finish = start }
    for (i in start..finish) {
        when (direction) {
            Direction.VERTICAL -> list.add(Coordinate(base, i))
            Direction.HORIZONTAL -> list.add(Coordinate(i, base))
        }
//        list.add(Coordinate(i, base))
    }
    return list
}
