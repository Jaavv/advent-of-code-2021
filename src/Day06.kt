// https://adventofcode.com/2021/day/6

fun main() {
    val input = readInput("Day06")
    val data = input.flatMap { it.split(",") }.map { it.toInt() }

    val day1 = Day06Part1(data)
    for (i in 0..256) {
        day1.execute() // Part 1 = 359344
    }
}

class Lanternfish constructor(private var timer: Int) {

    private var resetState = false

    fun getState(): Boolean {
        return this.resetState
    }

    fun getTimer(): Int {
        return this.timer
    }

    fun countDown() {
        if (timer <= 0) {
            this.resetTimer()
        } else {
            this.resetState = false
            timer -= 1
        }
    }

    private fun resetTimer() {
        resetState = true
        timer = 6
    }
}

class Day06Part1(input: List<Int>) {

    private var day = 0
    private var newSpawn = false
    private var listOfFishes = mutableListOf<Lanternfish>()

    init {
        for (i in input) {
            val fish = Lanternfish(i)
            listOfFishes.add(fish)
        }
    }

    fun getFishAmount(): Int {
        return listOfFishes.size
    }

    private fun spawnNewFishes(amount: Int) {
        for (i in 1..amount)
            listOfFishes.add(Lanternfish(8))
    }

    private fun nextDay() {
        day += 1
        newSpawn = false
        listOfFishes.map { it.countDown() }
        val spawn = listOfFishes.filter { it.getState() }.size
        if (spawn > 0) spawnNewFishes(spawn)
    }

    fun execute() {
        if (day == 80 || day == 256) miniData()
        nextDay()
    }

    private fun displayData() {
        val day: String = if (day == 0) "Initial State: " else "After $day day: "
        println("$day [Fishes=${this.getFishAmount()}] ${this.listOfFishes.map { it.getTimer() }}")
    }

    fun miniData() {
        println("DAY: $day --- Amount of Lanternfish: ${getFishAmount()}")
    }
}