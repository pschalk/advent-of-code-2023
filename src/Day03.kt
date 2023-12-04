typealias Row = Int

abstract class MapItem(
    val row: Row,
    val range: IntRange,
)

class Number(row: Row, range: IntRange, val value: Int): MapItem(row, range) {

}

class Symbol(row: Row, range: IntRange): MapItem(row, range) {

}

class Empty(row: Row, range: IntRange): MapItem(row, range) {

}

fun main() {

    val numberRegex = Regex("(?<number>\\d{1,3})")
    val symbolRegex = Regex("(?<symbol>[^\\p{L}\\d\\s/.])")

    val regex = Regex("(?<number>\\d{1,3})|(?<symbol>[^\\p{L}\\d\\s\\\\.])|(?<empty>[\\\\.])")

    fun part1(input: List<String>): Int {
        val gameMap = mutableMapOf<Int, MutableList<MapItem>>()

        // Build map
        for (row in input.indices) {
            regex.findAll(input[row]).forEach { matchResult ->
                if(matchResult.groups["number"] != null) {
                    val number = Number(row, matchResult.groups["number"]!!.range,  matchResult.groups["number"]!!.value.toInt())
                    gameMap.compute(row) {_, v -> v?.apply { add(number) } ?: mutableListOf(number) }
                } else if(matchResult.groups["symbol"] != null) {
                    val symbol = Symbol(row, matchResult.groups["symbol"]!!.range)
                    gameMap.compute(row) { _, v -> v?.apply { add(symbol) } ?: mutableListOf(symbol) }
                } else {
                    val number = Empty(row, matchResult.groups["empty"]!!.range)
                    gameMap.compute(row) {_, v -> v?.apply { add(number) } ?: mutableListOf(number) }
                }
            }
        }

        // Evaluate map
        val numbersForSum = mutableListOf<Int>()
        gameMap.values.forEach { list ->
            list.filter { it is Number }.forEach {

            }
        }

        // TODO FINISH / FIX THIS
        return numbersForSum.sum()
    }

    fun part2(input: List<String>) {

    }

    val input = readInput("Day03")
    "Part 1: ".println()
    part1(input).println()

    "Part 2: ".println()
    part2(input).println()
}
