package blackjack.model

class Stats(val players: List<Player>, val dealer: Dealer) {
    val earningMap: Map<Playable, Int> get() = payOutPotToEarnings()

    private fun calculatePlayerBoard(): Map<Player, Result> {
        val board = mutableMapOf<Player, Result>()
        players.forEach { recordPlayerBoard(it, board) }
        return board.toMap()
    }

    private fun recordPlayerBoard(
        player: Player,
        board: MutableMap<Player, Result>,
    ) {
        val playerScore = player.calculateHand()
        val dealerScore = dealer.calculateHand()
        when {
            player.isBlackjack() -> board[player] = Result.BLACKJACK
            player.isBust() -> board[player] = Result.LOSE
            dealer.isBust() -> board[player] = Result.WIN
            playerScore > dealerScore -> board[player] = Result.WIN
            playerScore < dealerScore -> board[player] = Result.LOSE
            else -> board[player] = Result.TIE
        }
    }

    // TODO: add case for dealer with blackjack
    fun payOutPotToEarnings(): Map<Playable, Int> {
        val playerBoard = calculatePlayerBoard()
        var pot = players.sumOf { it.bet }
        val map = mutableMapOf<Playable, Int>()
        val isDealerBlackjack = dealer.isBlackjack()
        if (isDealerBlackjack) {
            players.forEach { player ->
                when {
                    playerBoard[player] == Result.BLACKJACK -> {
                        val amount = player.bet
                        map[player] = 0
                        pot -= amount
                    }
                    playerBoard[player] == Result.LOSE -> {
                        val amount = player.bet
                        map[player] = -amount
                    }
                    playerBoard[player] == Result.TIE -> {
                        val amount = player.bet
                        map[player] = 0
                        pot -= amount
                    }
                }
            }
            map[dealer] = pot
            return map.toMap()
        }
        players.forEach { player ->
            when {
                playerBoard[player] == Result.BLACKJACK -> {
                    val amount = (player.bet * 1.5).toInt()
                    map[player] = amount
                    pot -= amount + player.bet
                }
                playerBoard[player] == Result.WIN -> {
                    val amount = player.bet
                    map[player] = amount
                    pot -= amount * 2
                }
                playerBoard[player] == Result.LOSE -> {
                    val amount = player.bet
                    map[player] = -amount
                }
                playerBoard[player] == Result.TIE -> {
                    val amount = player.bet
                    map[player] = 0
                    pot -= amount
                }
            }
        }
        map[dealer] = pot
        return map.toMap()
    }
}
