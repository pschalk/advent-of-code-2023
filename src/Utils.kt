import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

class MathUtils {
    companion object {
        fun getLeastCommonMultiple(numbers: Collection<Long>): Long {
            return numbers.map { getPrimes(it) }.reduce { i, j -> i + j }.reduce { i, j -> i * j }
        }

        fun getPrimes(number: Long): Set<Long> {
            val primes = mutableSetOf<Long>()

            var currentNumber = number
            var i = 2L
            while (i * i < currentNumber) {
                if (number % i == 0L) {
                    primes.add(i)
                    currentNumber /= i
                }
                i++
            }

            primes.add(currentNumber)
            return primes
        }
    }
}
