typealias WinningNumber = Int
typealias ElfNumber = Int

fun main() {

    val gameIdRegex = Regex("Card\\s*(?<gameId>\\d*)")
    val numberRegex = Regex("(?<number>\\d{1,2})")

    fun parseGameId(value: String): GameId {
        return gameIdRegex.find(value)!!.groups["gameId"]!!.value.toInt()
    }

    fun parseNumbers(value: String) = numberRegex.findAll(value).map { it.groups["number"]!!.value.toInt() }.toList()

    fun parseGameIdAndNumbers(scratchCard: String): Triple<GameId, List<WinningNumber>, List<ElfNumber>> {
        val arr = scratchCard.split(":")
        val gameId = parseGameId(arr[0])
        val winningAndElfNumbers = arr[1].split("|")
        val winningNumbers = parseNumbers(winningAndElfNumbers[0])
        val elfNumbers = parseNumbers(winningAndElfNumbers[1])
        return Triple(gameId, winningNumbers, elfNumbers)
    }

    fun part1(input: List<String>): Int {
        val points: List<Int> = input
            .map { parseGameIdAndNumbers(it) }
            .map { triple ->
                var points: Int = 0
                triple.second.forEach {
                    if(triple.third.contains(it)) {
                        if(points == 0) points = 1 else points *= 2
                    }
                }
                return@map points
            }

        return points.sum()
    }

    fun part2(input: List<String>): Int {

        var mapOfScratchCardTriples: Map<GameId, MutableList<Triple<GameId, List<WinningNumber>, List<ElfNumber>>>> = input.map { parseGameIdAndNumbers(it) }.map { it.first to mutableListOf(it) }.toMap()

        for (index in mapOfScratchCardTriples.keys) {
            val tripleList = mapOfScratchCardTriples[index]

            var innerIndex = 0
            while(true) {
                if(tripleList!!.size == innerIndex) break

                val triple = tripleList[innerIndex]
                var matchingNumbers = 0

                triple.second.forEach { if(triple.third.contains(it)) matchingNumbers++ }

                for (n in 1..matchingNumbers) {
                    val nextScratchCardIndex = index+n
                    mapOfScratchCardTriples[nextScratchCardIndex]!!.add(mapOfScratchCardTriples[nextScratchCardIndex]!![0])
                }

                innerIndex++
            }
        }

        return mapOfScratchCardTriples.values.flatten().size
    }

    val input = readInput("Day04")
    "Part 1: ".println()
    part1(input).println()

    "Part 2: ".println()
    part2(input).println()
}
