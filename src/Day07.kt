import kotlin.math.max
import kotlin.math.min

// https://adventofcode.com/2021/day/7


fun main() {
    val input = readInput("Day07").flatMap { it.split(",") }.map { it.toInt() }
    val input2 = readInput("Day07_test").flatMap { it.split(",") }.map { it.toLong() }
    println(day07Part1(input)) // 341534
    println(day07Part2(input2)) // not 93397707
}

fun day07Part1(input: List<Int>): Int {
    return input.map { diffy(input, it) }.map { it.sum() }.toSet().minOf { it }
}

fun diffy(list: List<Int>, number: Int): List<Int> {
    return list.map { max(it, number).minus(min(it, number)) }
}

fun day07Part2(input: List<Long>): Long {
    return input.asSequence().map { diffy2(input, it) }.map { it -> it.map { x(it) } }.map { it.sum() }.toSet()
        .minOf { it }
}

fun diffy2(list: List<Long>, number: Long): List<Long> {
    return list.map { max(it, number).minus(min(it, number)) }
}

fun x(a: Long): Long {
    if (a == 0L) return 0L
    return if (a == 1L) 1L else a + x(a - 1L)
}
