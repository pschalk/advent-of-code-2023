typealias GameId = Int

fun main() {

    val maxCubesMap = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )

    val gameIdRegex = Regex("Game (?<gameId>\\d*)")
    val moveRegex = Regex("(?<cubesAmount>\\d*) (?<cubeColor>(blue|red|green))")

    fun parseGameId(value: String): GameId = gameIdRegex.find(value)!!.groups["gameId"]!!.value.toInt()

    fun parseGameIdAndMoves(line: String): Pair<GameId, List<String>> {
        val arr = line.split(":")
        val gameId = parseGameId(arr[0])
        val moves = arr[1].split(";")
        return Pair(gameId, moves)
    }

    fun part1(input: List<String>): Int {
        val gameIdList = mutableListOf<Int>()
        input.forEach { line ->
            val arr = line.split(":")
            val gameId = gameIdRegex.find(arr[0])!!.groups["gameId"]!!.value.toInt()
            val countOfBadPairs = arr[1].split(";")
                .flatMap { turn -> moveRegex.findAll(turn)
                        .map { Pair(it.groups["cubeColor"]!!.value, it.groups["cubesAmount"]!!.value.toInt()) }
                        .filter { maxCubesMap[it.first]!! < it.second }
                }.count()

            if(countOfBadPairs == 0) {
                gameIdList.add(gameId)
            }
        }

        return gameIdList.sum()
    }

    fun part2(input: List<String>): Int {
        val powerList = mutableListOf<Int>()
        input.forEach { line ->
            val maxCubesMap = mutableMapOf<String, Int>()
            val pairOfGameIdAndMoves = parseGameIdAndMoves(line)
            pairOfGameIdAndMoves.second
                .forEach{ turn -> moveRegex.findAll(turn)
                    .map { Pair(it.groups["cubeColor"]!!.value, it.groups["cubesAmount"]!!.value.toInt()) }
                    .forEach {
                        if(maxCubesMap.containsKey(it.first))
                            maxCubesMap.compute(it.first) { _, v -> if (v!! < it.second) it.second else v }
                        else
                            maxCubesMap.put(it.first, it.second)
                    }
                }

            powerList.add(maxCubesMap.values.reduce { acc, i -> acc * i })
        }

        return powerList.sum()
    }

    val input = readInput("Day02")
    "Part 1: ".println()
    part1(input).println()

    "Part 2: ".println()
    part2(input).println()
}
