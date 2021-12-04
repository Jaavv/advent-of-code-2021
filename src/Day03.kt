// https://adventofcode.com/2021/day/3

fun main() {
    // part 1
    val input = readInput("Day03")
    val orgList = mutableListOf<StringBuilder>()
    for (i in 0..input[0].length - 1) {
        val s = StringBuilder()
        for (y in 0..input.size - 1) {
            val o = input[y][i]
            s.append(o)
        }
        orgList.add(i, s)
    }
    println(input)
    println(orgList)

    val gamma = orgList.joinToString("") { getCommonBit(it) }
        .toInt(2)

    val epsilon = orgList.joinToString("") { getLeastCommonBit(it) }
        .toInt(2)

    val power = gamma * epsilon
    println("Power Consumption: $power") // 2724524

    // Part2
    println("==== PART 2 ====")
    var oxygenData = input.toMutableList()
    var co2Data = input.toMutableList()
    for (i in 0 until input[0].length) {
        // oxygen generator rating
        println("Current bit: $oxygenData")
        val bits = oxygenData.map { it.get(i) }
        println("BITS: $bits -- 0bits: ${bits.count { c -> c == '0' }} -- 1bits: ${bits.count { c -> c == '1' }}")
        println("Most Common Oxygen: ${mostCommonOxygen(bits)}")
        oxygenData = oxygenData.filter { s -> s[i].toString() == mostCommonOxygen(bits) }.toMutableList()

        // co2 scrubber rating
        val co2Bits = co2Data.map { it.get(i) }
        co2Data = co2Data.filter { s -> s[i].toString() == leastCommonCO2(co2Bits) }.toMutableList()
    }
    val oxygenBinaryCode = oxygenData[0]
    val co2BinaryCode = co2Data[0]
    println("OXYGEN GENERATOR RATING: BINARY = $oxygenBinaryCode DECIMAL = ${oxygenBinaryCode.toInt(2)} ")
    println("CO2 SCRUBBER RATING: BINARY = $co2BinaryCode DECIMAL = ${co2BinaryCode.toInt(2)} ")
    println("LIFE SUPPORT RATING = ${oxygenBinaryCode.toInt(2) * co2BinaryCode.toInt(2)}") // 2775870
}

fun mostCommonOxygen(input: List<Char>): String {
    return if (input.groupingBy { it }.eachCount()['0'] == input.groupingBy { it }.eachCount()['1']) "1" else
        input.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key.toString()
}

fun leastCommonCO2(input: List<Char>): String {
    return if (input.groupingBy { it }.eachCount()['0'] == input.groupingBy { it }.eachCount()['1']) "0" else
        input.groupingBy { it }.eachCount().minByOrNull { it.value }?.key.toString()
}

fun getCommonBit(input: StringBuilder): String {
    var commonBit = "0"
    if ((input.count { c -> c == '0' }) < (input.count { c -> c == '1' })) commonBit = "1"
    return commonBit
}

fun getLeastCommonBit(input: StringBuilder): String {
    var leastCommonBit = "0"
    if ((input.count { c -> c == '0' }) > (input.count { c -> c == '1' })) leastCommonBit = "1"
    return leastCommonBit
}