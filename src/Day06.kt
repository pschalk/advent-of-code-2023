
fun main() {

    val numberRegex = Regex("(?<number>\\d{1,4})")

    fun part1(input: List<String>): Int {
        val timeArr = numberRegex.findAll(input[0].split(":")[1]).map { it.groups["number"]!!.value.toInt() }.toList()
        val distanceArr = numberRegex.findAll(input[1].split(":")[1]).map { it.groups["number"]!!.value.toInt() }.toList()

        val configsToWin = mutableListOf<Int>()

        timeArr.forEachIndexed { index, time ->
            val distance = distanceArr[index]
            var configToWinAmount = 0
            for(buttonHoldDown: Int in 1..Int.MAX_VALUE) {
                val raceTime = time - buttonHoldDown
                val mmTraveled = buttonHoldDown * raceTime
                if( mmTraveled > distance ) {
                    configToWinAmount++
                } else if(raceTime == 0){
                    configsToWin.add(configToWinAmount)
                    break
                }
            }
        }

        return configsToWin.reduce { sum, i -> sum * i }
    }

    fun part2(input: List<String>): Int {
        val time = numberRegex.findAll(input[0].split(":")[1]).map { it.groups["number"]!!.value }.joinToString("").toLong()
        val distance = numberRegex.findAll(input[1].split(":")[1]).map { it.groups["number"]!!.value }.joinToString("").toLong()

        var configToWinAmount = 0
        for(buttonHoldDown: Int in 1..Int.MAX_VALUE) {
            val raceTime = time - buttonHoldDown
            val mmTraveled = buttonHoldDown * raceTime
            if( mmTraveled > distance ) {
                configToWinAmount++
            } else if(raceTime == 0L){
                break
            }
        }

        return configToWinAmount
    }

    val input = readInput("Day06")
    "Part 1: ".println()
    part1(input).println()

    "Part 2: ".println()
    part2(input).println()
}
