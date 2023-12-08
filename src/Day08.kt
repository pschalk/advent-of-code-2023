
fun main() {

    val coordsRegex = Regex("(?<coords>[A-Z]{1,3})")

    fun parseMap(input: List<String>): MutableMap<String, Pair<String, String>> {
        val coordsMap = mutableMapOf<String, Pair<String, String>>()
        for (index in 2..<input.size) {
            val coordsArr = input[index].replace("\\s".toRegex(), "")
                .split("=")
            val parsedList = coordsRegex.findAll(coordsArr[1]).toList()
            coordsMap[coordsArr[0]] =
                Pair(parsedList[0].groups["coords"]!!.value, parsedList[1].groups["coords"]!!.value)
        }
        return coordsMap
    }

    fun part1(input: List<String>): Int {
        val instructions = input[0]

        // Parse to map
        val coordsMap = parseMap(input)

        // Navigate
        var index = 0
        var currentElement: Pair<String, String>? = coordsMap["AAA"]
        var steps = 0
        while (true) {
            val instruction = instructions[index]

            val nextElement = if(instruction == 'L') {
                currentElement!!.first
            } else {
                currentElement!!.second
            }

            currentElement = coordsMap[nextElement]
            steps++
            if(nextElement == "ZZZ") {
                break
            } else if(index == instructions.length-1){
                index = 0
            } else {
                index++
            }
        }

        return steps
    }

    fun part2(input: List<String>): Int {
        val instructions = input[0]

        // Parse to map
        val coordsMap = parseMap(input)

        // Get starting nodes
        val startingNodes = coordsMap.keys.filter {it.endsWith("A")}

        // Navigate
        var index = 0
        var currentElements: Map<String, Pair<String, String>> = startingNodes.associateWith { coordsMap[it]!! }
        var steps = 0
        while (true) {
            val instruction = instructions[index]

            currentElements = if(instruction == 'L') {
                currentElements.values.associate { it.first to coordsMap[it.first]!! }
            } else {
                currentElements.values.associate { it.second to coordsMap[it.second]!! }
            }

            steps++
            if(currentElements.keys.all { it.endsWith("Z") }) {
                break
            } else if(index == instructions.length-1){
                index = 0
            } else {
                index++
            }
        }

        return steps
    }

    val input = readInput("Day08")
    "Part 1: ".println()
    part1(input).println()

    "Part 2: ".println()
    part2(input).println()
}
