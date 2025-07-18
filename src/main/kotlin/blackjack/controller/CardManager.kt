package blackjack.controller

import blackjack.model.PlayingCard

class CardManager() {
    var cards: List<PlayingCard> = emptyList()

    init {
        cards = PlayingCard.Deck.toList().shuffled()
    }

    private fun getCard(): PlayingCard {
        val deque = ArrayDeque(cards)
        val card = deque.first()
        deque.removeFirst()
        cards = deque.toList()
        return card
    }

    fun giveCard(): PlayingCard {
        val card = getCard()
        return card
    }
}
