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
        val noIndex = row.withIndex().filter { it.value == number }.map { it.index }
        for (i in noIndex) {
            row[i] = "$number*"
        }
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
                for (row in board) {
                    if (row.contains(n)) {
                        markDrawnNumber(row, n)
                        val colIndex = row.withIndex().filter { it.value == "$n*" }.map { it.index }
                        val rowBingo = rowBingoCheck(row)
                        var colBingo = false
                        colCheck@ for (c in colIndex) {
                            if (colBingoCheck(board, c))
                                colBingo = true
                            break@colCheck
                        }
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
    println("FINAL SCORE: ${part1(drawnNumbers, board)}") // 34506

    fun part2(numbers: List<String>, board: List<List<List<String>>>): Int {
        var board2 = board.map { it -> it.map { it.toMutableList() }.toMutableList() }.toMutableList()
        var currentNumber = 0
        var winningBoards: MutableList<Int> = mutableListOf()
        for (n in numbers) {
            drawBoard@ for ((bIndex, board) in board2.withIndex()) {
                if (winningBoards.contains(bIndex)) {
                    continue
                } else {
                    for (row in board) {
                        if (row.contains(n)) {
                            val rowIndex = row.indexOf(n)
                            row[rowIndex] = "$n*"
                            val colIndex = row.indexOf("$n*")
                            if (rowBingoCheck(row) || colBingoCheck(board, colIndex)) {
                                winningBoards.add(bIndex)
                                currentNumber = n.toInt()
                            }
                        }
                    }
                }
            }
        }
        val sumOfLastWinning =
            board2.get(winningBoards.last()).flatten().filter { !it.contains("*") }.sumOf { it.toInt() }
        println("LAST WINNING NUMBER: $currentNumber * LAST UNMARKED BOARD SUM: $sumOfLastWinning")
        return currentNumber * sumOfLastWinning
    }
    println("FINAL SCORE: ${part2(drawnNumbers, board)}") // 7686
}