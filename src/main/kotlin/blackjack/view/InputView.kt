package blackjack.view

object InputView {
    fun readPlayerNames(): List<String> {
        println("Enter the names of the players (comma-separated):")
        val input = readln().trim()
        require(input.isNotBlank()) { "Please enter a name." }
        val trimmedInput = input.split(",")
        require(trimmedInput.isNotEmpty()) { "Please enter a name." }
        require(trimmedInput.toSet().size == trimmedInput.size) { "There are duplicates in the names." }
        trimmedInput.forEach { require(it.isNotEmpty()) { "Please enter a valid name." } }
        return trimmedInput
    }

    fun readPlayerBettingAmount(name: String): Int {
        println("\nEnter $name’s betting amount:")
        val input = readln().trim()
        require(input.isNotBlank()) { "Please enter the amount of betting." }
        val userInput = input.toIntOrNull() ?: throw IllegalArgumentException("Please enter a valid amount as number.")
        require(userInput > 0) { "Please enter a valid amount." }
        require(userInput % 100 == 0) { "Please enter a valid amount, dividable to 100." }
        return userInput
    }

    fun readYesOrNo(name: String): Boolean {
        println("\nWould $name like to draw another card? (y for yes, n for no)")
        val input = readln().trim()
        return when (input) {
            "y" -> true
            "n" -> false
            else -> throw IllegalArgumentException("Please write 'y' for 'yes' or 'n' for 'no'.")
        }
    }

    fun <T> retryable(inputMethod: () -> T): T {
        while (true) {
            try {
                return inputMethod()
            } catch (err: IllegalArgumentException) {
                println("${err.message}")
            }
        }
    }
}
