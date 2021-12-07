import kotlin.math.abs

// https://adventofcode.com/2021/day/5

fun main() {
    val input = readInput("Day05")
    println("PART1 POINTS: ${day5part1(input)}") // 4421
    println("PART2 POINTS: ${day5part2(input)}") // 18674
}

fun day5part1(input: List<String>): Int {
    val data = input.map { inputParse(it) }.filter { isStraightLine(it) }
    val maxX = data.map { largestCoord(it) }.maxOf { it.x }
    val maxY = data.map { largestCoord(it) }.maxOf { it.y }
    val graph = MutableList(maxY + 1) { MutableList(maxX + 1) { 0 } }
    val coordinates = data.map { straightLine(it) }.flatten()
    for (i in coordinates) insertLine(graph, i)
    return graph.flatten().count { it >= 2 }
}

fun day5part2(input: List<String>): Int {
    val straightLine =
        input.map { inputParse(it) }.filter { isStraightLine(it) }.map { straightLine(it) }.flatten().toMutableList()
    val diagLine =
        input.map { inputParse(it) }.filter { isDiagonalLine(it) }.map { diagonalLine(it) }.flatten().toMutableList()
    straightLine.addAll(diagLine)
    val maxX = straightLine.map { largestCoord2(it) }.maxOf { it.x }
    val maxY = straightLine.map { largestCoord2(it) }.maxOf { it.y }
    val graph = MutableList(maxY + 1) { MutableList(maxX + 1) { 0 } }
    for (i in straightLine) insertLine(graph, i)
    return graph.flatten().count { it >= 2 }
}

data class Coordinate(val x: Int, val y: Int)
data class Input(val from: Coordinate, val to: Coordinate)
enum class Direction { HORIZONTAL, VERTICAL, DIAG_UP_FORWARD, DIAG_UP_BACKW, DIAG_DOWN_FORWARD, DIAG_DOWN_BACKW }

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

fun largestCoord2(input: Coordinate): Coordinate {
    return Coordinate(maxOf(input.x, input.x), maxOf(input.y, input.y))
}

fun insertLine(map: MutableList<MutableList<Int>>, coordinate: Coordinate) {
    map[coordinate.y][coordinate.x] += 1
}

fun isStraightLine(input: Input): Boolean {
    return (input.from.x == input.to.x) || (input.from.y == input.to.y)
}

fun isDiagonalLine(input: Input): Boolean {
//    return ((input.from.x == input.from.y) && (input.to.x == input.to.y)) ||
//            ((input.from.x == input.to.y) && (input.from.y == input.to.x))
    return (abs(input.from.x - input.to.x) == abs(input.from.y - input.to.y))
}

fun direction(from: Coordinate, to: Coordinate): Direction {
    return if (from.x == to.x) Direction.VERTICAL else Direction.HORIZONTAL
}

fun straightLine(input: Input): List<Coordinate> {
    val list = mutableListOf<Coordinate>()
    var start = 0
    var finish = 0
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
    }
    return list
}

fun diagDirection(input: Input): Direction {
    var diagDir: Direction = Direction.DIAG_UP_FORWARD
    if ((input.from.y > input.to.y) && (input.from.x < input.to.x)) diagDir = Direction.DIAG_UP_FORWARD
    if ((input.from.y > input.to.y) && (input.from.x > input.to.x)) diagDir = Direction.DIAG_UP_BACKW
    if ((input.from.y < input.to.y) && (input.from.x < input.to.x)) diagDir = Direction.DIAG_DOWN_FORWARD
    if ((input.from.y < input.to.y) && (input.from.x > input.to.x)) diagDir = Direction.DIAG_DOWN_BACKW
    return diagDir
}

fun diagonalLine(input: Input): List<Coordinate> {
    val list = mutableListOf<Coordinate>()
    val range = abs(input.from.x - input.to.x)

    for (i in 0..range) {
        when (diagDirection(input)) {
            Direction.DIAG_UP_FORWARD -> list.add(Coordinate(input.from.x + i, input.from.y - i))
            Direction.DIAG_UP_BACKW -> list.add(Coordinate(input.from.x - i, input.from.y - i))
            Direction.DIAG_DOWN_FORWARD -> list.add(Coordinate(input.from.x + i, input.from.y + i))
            Direction.DIAG_DOWN_BACKW -> list.add(Coordinate(input.from.x - i, input.from.y + i))
        }
    }
    return list
}