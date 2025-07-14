package blackjack.model

data class Player(override val name: String) : Playable {
    private var _hand = Hand()
    override val hand: Hand get() = _hand
    private var _bet = Playable.INITIAL_BETTING_AMOUNT
    override val bet get() = _bet

    override fun drawCard(newCard: PlayingCard) {
        val deque = ArrayDeque(hand.cards)
        deque.addLast(newCard)
        _hand = Hand(deque.toList())
    }

    override fun calculateHand(): Int {
        return _hand.calculateHand()
    }

    fun placeBets(amount: Int): Player {
        _bet += amount
        return this
    }

    fun resetBetting() {
        _bet = Playable.INITIAL_BETTING_AMOUNT
    }
}
