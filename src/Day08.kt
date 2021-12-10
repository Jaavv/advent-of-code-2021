// https://adventofcode.com/2021/day/8

fun main() {
    val input = readInput("Day08")
    val outputValue = input.map { it.split(" | ") }.map { it.last() }.map { it.split(" ") }
    val unique = outputValue.map { it -> it.filter { uniqueSegments(it) } }
    val instances = unique.sumOf { it.size }

    println(day08Part1(input)) // 330

    val test = listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")
    val test2 = listOf("cf bcdf acf abcdefg")
    val test3 = listOf("abcefg cf acdeg acdfg bcdf abdfg abdefg acf abcdefg abcdfg")
    val test1 = test3[0].split(" | ").map { it.split(" ") }.flatten().sortedBy { it.length }

    val seg = SegmentDecoder()

    for (i in test1) seg.insertData(i)

    for (i in 1..8) println(seg.getNumber(i))


}

fun uniqueSegments(input: String): Boolean {
    return (input.length == 2) || (input.length == 3) || (input.length == 4) || (input.length == 7)
}

fun day08Part1(input: List<String>): Int {
    return input.asSequence()
        .map { it.split(" | ") }
        .map { it.last() }
        .map { it.split(" ") }
        .map { it -> it.filter { uniqueSegments(it) } }
        .sumOf { it.size }
}


val x = mutableMapOf<Int, MutableMap<String, List<String>>>()

class SegmentDecoder {

    private lateinit var a: String
    private lateinit var b: String
    private lateinit var c: String
    private lateinit var d: String
    private lateinit var e: String
    private lateinit var f: String
    private lateinit var g: String


    private var segmentA = mutableListOf<String>()
    private var segmentB = mutableListOf<String>()
    private var segmentC = mutableListOf<String>()
    private var segmentD = mutableListOf<String>()
    private var segmentE = mutableListOf<String>()
    private var segmentF = mutableListOf<String>()
    private var segmentG = mutableListOf<String>()

    fun insertData(input: String) {
        when (input.length) {
            5 -> {
                insert2(input)
                insert3(input)
                insert5(input)
            }
            6 -> {
                insert0(input)
                insert6(input)
                insert9(input)
            }
            7 -> insert8(input)
        }
    }

    fun isUnique(input: String): Boolean {
        return (input.length == 2) || (input.length == 3) || (input.length == 4) || (input.length == 7)
    }

    fun insertUnique(input: String) {
        when (input.length) {
            2 -> insert1(input)
            3 -> insert7(input)
            4 -> insert4(input)
        }
    }

    fun insert(input: String) {
        if (isUnique(input)) insertUnique(input)

    }

    private fun insert0(input: String) {
        segmentA.add(input)
        segmentB.add(input)
        segmentC.add(input)
        segmentE.add(input)
        segmentF.add(input)
        segmentG.add(input)
    }

    private fun insert1(input: String) {
        segmentC.add(input)
        segmentF.add(input)
    }

    private fun insert2(input: String) {
        segmentA.add(input)
        segmentC.add(input)
        segmentD.add(input)
        segmentE.add(input)
        segmentG.add(input)
        segmentG.add(input)
    }

    private fun insert3(input: String) {
        segmentA.add(input)
        segmentC.add(input)
        segmentD.add(input)
        segmentF.add(input)
        segmentG.add(input)
    }

    private fun insert4(input: String) {
        segmentB.add(input)
        segmentC.add(input)
        segmentD.add(input)
        segmentF.add(input)
    }

    private fun insert5(input: String) {
        segmentA.add(input)
        segmentB.add(input)
        segmentD.add(input)
        segmentF.add(input)
        segmentG.add(input)
    }

    private fun insert6(input: String) {
        segmentA.add(input)
        segmentB.add(input)
        segmentD.add(input)
        segmentE.add(input)
        segmentF.add(input)
        segmentG.add(input)
    }

    private fun insert7(input: String) {
        segmentA.add(input)
        segmentC.add(input)
        segmentF.add(input)
    }

    private fun insert8(input: String) {
        segmentA.add(input)
        segmentB.add(input)
        segmentC.add(input)
        segmentD.add(input)
        segmentE.add(input)
        segmentF.add(input)
        segmentG.add(input)
    }

    private fun insert9(input: String) {
        segmentA.add(input)
        segmentB.add(input)
        segmentC.add(input)
        segmentD.add(input)
        segmentF.add(input)
        segmentG.add(input)
    }

    fun getNumber(number: Int): List<String> {

        val mostA = segmentA.joinToString("").split("").groupingBy { it }.eachCount()
        val mostB = segmentB.joinToString("").split("").groupingBy { it }.eachCount()
        val mostC = segmentC.joinToString("").split("").groupingBy { it }.eachCount()
        val mostD = segmentD.joinToString("").split("").groupingBy { it }.eachCount()
        val mostE = segmentE.joinToString("").split("").groupingBy { it }.eachCount()
        val mostF = segmentF.joinToString("").split("").groupingBy { it }.eachCount()
        val mostG = segmentG.joinToString("").split("").groupingBy { it }.eachCount()
//        val mostA = segmentA.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
//        val mostB = segmentB.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
//        val mostC = segmentC.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
//        val mostD = segmentD.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
//        val mostE = segmentE.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
//        val mostF = segmentF.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
//        val mostG = segmentG.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        return when (number) {
            0 -> listOf(mostA, mostB, mostC, mostE, mostF, mostG) as List<String>
            1 -> listOf(mostC, mostF) as List<String>
            2 -> listOf(mostA, mostC, mostD, mostE, mostG) as List<String>
            3 -> listOf(mostA, mostC, mostD, mostF, mostG) as List<String>
            4 -> listOf(mostB, mostC, mostD, mostF) as List<String>
            5 -> listOf(mostA, mostB, mostD, mostF, mostG) as List<String>
            6 -> listOf(mostA, mostB, mostD, mostE, mostF, mostG) as List<String>
            7 -> listOf(mostA, mostC, mostF) as List<String>
            8 -> listOf(mostA, mostB, mostC, mostD, mostE, mostF, mostG) as List<String>
            9 -> listOf(mostA, mostB, mostC, mostD, mostF, mostG) as List<String>
            else -> listOf(mostA, mostB, mostC, mostD, mostE, mostF, mostG) as List<String>
        }
    }

    fun decodeOutput(output: List<String>): Int {
        return 0
    }
}