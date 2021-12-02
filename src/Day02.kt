// https://adventofcode.com/2021/day/2

fun main() {
    val input = readInput("Day02")
    println(part1(input))
    println(multiplier((part1(input)))) // 1936494
    println(part2(input))
    println(multiplier(part2(input))) // 1997106066
}

data class Position(var x: Int = 0, var y: Int = 0)

fun multiplier(position: Position): Int {
    return position.x * position.y
}

fun multiplier(position2: Position2): Int {
    return position2.x * position2.y
}

fun part1(input: List<String>): Position {
    val dir = input.map { it.split(" ") }
    val position = Position()
    for (i in dir) {
        when (i[0]) {
            "forward" -> position.x += i[1].toInt()
            "up" -> position.y -= i[1].toInt()
            "down" -> position.y += i[1].toInt()
        }
    }
    return position
}

data class Position2(var x: Int = 0, var y: Int = 0, var aim: Int = 0)

fun part2(input: List<String>): Position2 {
    val dir = input.map { it.split(" ") }
    val position = Position2()
    for (i in dir) {
        when (i[0]) {
            "forward" -> {
                position.x += i[1].toInt()
                position.y += position.aim * i[1].toInt()
            }
            "up" -> {
//                position.y -= i[1].toInt()
                position.aim -= i[1].toInt()
            }
            "down" -> {
//                position.y += i[1].toInt()
                position.aim += i[1].toInt()
            }
        }
    }
    return position
}