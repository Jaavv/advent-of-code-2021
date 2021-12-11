// https://adventofcode.com/2021/day/9

fun main() {
    val input = readInput("Day09")
    val part1 = input.map { it.split("") }.map { it -> it.filter { it != "" } }.map { it.map { it.toInt() } }
    println(day09Part1(part1)) // 522
    
}

fun day09Part1(input: List<List<Int>>): Int {
    val lowPoints =
        input.mapIndexed { row, column -> column.filterIndexed { index, value -> lowPoint(index, row, value, input) } }
            .flatten()
    return lowPoints.sumOf { it + 1 }
}

fun lowPoint(column: Int, row: Int, height: Int, world: List<List<Int>>): Boolean {
    val northHeight: Int? = try {
        world[row - 1][column]
    } catch (e: IndexOutOfBoundsException) {
        null
    }
    val eastHeight: Int? = try {
        world[row][column + 1]
    } catch (e: IndexOutOfBoundsException) {
        null
    }
    val southHeight: Int? = try {
        world[row + 1][column]
    } catch (e: IndexOutOfBoundsException) {
        null
    }
    val westHeight: Int? = try {
        world[row][column - 1]
    } catch (e: IndexOutOfBoundsException) {
        null
    }
    val surroundings = listOfNotNull(northHeight, southHeight, eastHeight, westHeight)
    return surroundings.all { it > height }
}