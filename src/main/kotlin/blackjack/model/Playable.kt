package blackjack.model

interface Playable {
    val name: String
    val hand: Hand

    fun requestCard(condition: () -> Boolean): Boolean {
        return condition()
    }

    fun drawCard(newCard: PlayingCard)

    fun calculateHand(): Int

    fun isBust(): Boolean {
        return calculateHand() > BUST_LIMIT
    }

    fun isBlackjack(): Boolean {
        val hasTwoCards = hand.cards.size == 2
        val hasTwentyOne = calculateHand() == BUST_LIMIT
        return hasTwoCards && hasTwentyOne
    }

    companion object {
        const val BUST_LIMIT = 21
    }
}
