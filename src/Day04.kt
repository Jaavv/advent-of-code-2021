// https://adventofcode.com/2021/day/4

fun main() {
    val input = readInput("Day04")

    val drawnNumbers = input.first().split(",")
    val board = input.subList(1, input.size)
        .filter { it != "" }
        .windowed(5, 5)
        .map {
            it.map {
                it.split(" ")
                    .filter { it != "" }
            }
        }

    fun markDrawnNumber(row: MutableList<String>, number: String) {
        row[row.indexOf(number)] = "$number*"
    }

    fun rowBingoCheck(row: MutableList<String>): Boolean {
        return row.count { c -> c.contains("*") } == 5
    }

    fun colBingoCheck(board: List<MutableList<String>>, index: Int): Boolean {
        return board.map { it[index] }.count { c -> c.contains("*") } == 5
    }

    fun part1(numbers: List<String>, board: List<List<List<String>>>): Int {
        val board1 = board.map { it -> it.map { it.toMutableList() } }
        var currentNumber = 0
        var winningBoard: List<MutableList<String>> = mutableListOf()
        drawNo@ for (n in numbers) {
            for (board in board1) {
                for ((rIndex, row) in board.withIndex()) {
                    if (row.contains(n)) {
                        markDrawnNumber(row, n)
                        val rowBingo = rowBingoCheck(row)
                        val colBingo = colBingoCheck(board, rIndex)
                        if (rowBingo || colBingo) {
                            winningBoard = board
                            currentNumber = n.toInt()
                            break@drawNo
                        }
                    }
                }
            }
        }
        val sumOfUnmarked = winningBoard.flatten().filter { !it.contains("*") }.sumOf { it.toInt() }
        println("WINNING NUMBER: $currentNumber * UNMARKED BOARD SUM: $sumOfUnmarked")
        return currentNumber * sumOfUnmarked
    }
    println("FINAL SCORE: ${part1(drawnNumbers, board)}")

    fun part2() {
        TODO()
    }
}

