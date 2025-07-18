package blackjack.model

class Stats(val players: List<Player>, val dealer: Dealer) {
    private var isDealerBlackjack = false
    private var playerBoard = mapOf<Player, Result>()
    val earningMap: Map<Playable, Int> get() = payOutPotToEarnings()

    init {
        isDealerBlackjack = dealer.isBlackjack()
        playerBoard = calculatePlayerBoard()
    }

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
        board[player] =
            when {
                player.isBlackjack() -> Result.BLACKJACK
                player.isBust() -> Result.LOSE
                dealer.isBust() -> Result.WIN
                playerScore > dealerScore -> Result.WIN
                playerScore < dealerScore -> Result.LOSE
                else -> Result.TIE
            }
    }

    fun payOutPotToEarnings(): Map<Playable, Int> {
        var pot = players.sumOf { it.bet }
        val earningsMap = mutableMapOf<Playable, Int>()

        players.forEach { player ->
            val result = playerBoard.getOrDefault(player, Result.LOSE)
            val amount = calculatePayout(result, player.bet, isDealerBlackjack)
            pot -= payoutImpactOnPot(result, player.bet, isDealerBlackjack)
            earningsMap[player] = amount
        }

        earningsMap[dealer] = pot
        return earningsMap.toMap()
    }

    private fun calculatePayout(
        result: Result,
        bet: Int,
        dealerBlackjack: Boolean,
    ): Int {
        return if (dealerBlackjack) {
            calculatePayoutWithDealerBlackjack(result, bet)
        } else {
            calculatePayoutWithDealerNonBlackjack(result, bet)
        }
    }

    private fun calculatePayoutWithDealerBlackjack(
        result: Result,
        bet: Int,
    ): Int {
        return when (result) {
            Result.BLACKJACK, Result.TIE -> 0
            Result.LOSE -> -bet
            else -> 0
        }
    }

    private fun calculatePayoutWithDealerNonBlackjack(
        result: Result,
        bet: Int,
    ): Int {
        return when (result) {
            Result.BLACKJACK -> (bet * Result.BLACKJACK_BONUS).toInt()
            Result.WIN -> bet
            Result.TIE -> 0
            Result.LOSE -> -bet
        }
    }

    private fun payoutImpactOnPot(
        result: Result,
        bet: Int,
        dealerBlackjack: Boolean,
    ): Int {
        return if (dealerBlackjack) {
            payoutImpactOnPotWithDealerBlackjack(result, bet)
        } else {
            payoutImpactOnPotWithDealerNonBlackjack(result, bet)
        }
    }

    private fun payoutImpactOnPotWithDealerBlackjack(
        result: Result,
        bet: Int,
    ): Int {
        return when (result) {
            Result.BLACKJACK, Result.TIE -> bet
            else -> 0
        }
    }

    private fun payoutImpactOnPotWithDealerNonBlackjack(
        result: Result,
        bet: Int,
    ): Int {
        return when (result) {
            Result.BLACKJACK -> bet + (bet * Result.BLACKJACK_BONUS).toInt()
            Result.WIN -> bet * 2
            Result.TIE -> bet
            else -> 0
        }
    }
}
