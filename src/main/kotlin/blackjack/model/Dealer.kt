package blackjack.model

data class Dealer(override val name: String = "Dealer") : Playable {
    private var _hand = Hand()
    override val hand: Hand get() = _hand
    override val bet = Playable.INITIAL_BETTING_AMOUNT

    override fun drawCard(newCard: PlayingCard) {
        val deque = ArrayDeque(hand.cards)
        deque.addLast(newCard)
        _hand = Hand(deque.toList())
    }

    override fun calculateHand(): Int {
        return _hand.calculateHand()
    }

    fun shouldDrawCardOrNot(): Boolean {
        val score = calculateHand()
        return score < DRAW_LIMIT
    }

    companion object {
        const val DRAW_LIMIT = 17
    }
}
