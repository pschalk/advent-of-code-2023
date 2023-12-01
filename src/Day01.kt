fun main() {

    val numberRegex = Regex("(\\d)")
    val numberWordsRegex = Regex("(?=(\\d)|(one)|(two)|(three)|(four)|(five)|(six)|(seven)|(eight)|(nine))")
    val numberMap = mapOf(
        "zero" to "0",
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    fun part1(input: List<String>): Long = input.map { numberRegex.findAll(it) }
            .map { it.first().value + it.last().value }
            .sumOf { it.toLong() }

    fun toNumberValue(value: String): String = if(numberRegex.matches(value)) value else numberMap[value]!!

    fun findValue(matchResult: MatchResult): String = matchResult.groups.filterNotNull().map { it.value }.filter { it.isNotEmpty() }.first()

    fun part2(input: List<String>): Long {
        return input.map { numberWordsRegex.findAll(it) }
            .map { toNumberValue(findValue(it.first())) + toNumberValue(findValue(it.last())) }
            .sumOf { it.toLong() }
    }

    val input = readInput("Day01")
    "Part 1: ".println()
    part1(input).println()

    "Part 2: ".println()
    part2(input).println()
}
