package blackjack.model

import blackjack.Fixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PlayerTest {
    @ParameterizedTest(name = "Player - has name {0}")
    @ValueSource(strings = ["Player", "A♥", "san", "ann"])
    fun `Player has a name in string`(candidate: String) {
        assertEquals(candidate, Player(candidate).name)
    }

    @Test
    fun `requestCard() - request true`() {
        val player = Player("player")
        val request = player.requestCard { true }
        assertEquals(true, request)
    }

    @Test
    fun `requestCard() - request false`() {
        val player = Player("player")
        val request = player.requestCard { false }
        assertEquals(false, request)
    }

    @Test
    fun `drawCard()`() {
        val player = Player("player")

        // initial state of player's hand
        assertEquals(0, player.hand.cards.size)

        // draw first card
        player.drawCard(Fixture.SPADES_ACE)
        assertEquals(1, player.hand.cards.size)
        assertEquals(Fixture.SPADES_ACE, player.hand.cards.last())

        // draw second card
        player.drawCard(Fixture.DIAMONDS_JACK)
        assertEquals(2, player.hand.cards.size)
        assertEquals(Fixture.DIAMONDS_JACK, player.hand.cards.last())
    }

    @Test
    fun `Player has hand which is a list of card`() {
        val cards = PlayingCard.Deck
        val player = Player("player")

        // initial state of player's hand
        assertEquals(emptyList<PlayingCard>(), player.hand.cards)

        // draw first card
        player.drawCard(cards[0])
        assertEquals(cards.subList(0, 1), player.hand.cards)

        // draw second card
        player.drawCard(cards[1])
        assertEquals(cards.subList(0, 2), player.hand.cards)
    }

    @Test
    fun `calculateHand() - player has TWO and TEN`() {
        val player = Player("player")

        player.drawCard(Fixture.DIAMONDS_TWO)
        player.drawCard(Fixture.DIAMONDS_TEN)

        assertEquals(12, player.calculateHand())
    }

    @Test
    fun `calculateHand() - player has Two ACE`() {
        val player = Player("player")

        player.drawCard(Fixture.DIAMONDS_ACE)
        player.drawCard(Fixture.SPADES_ACE)

        assertEquals(12, player.calculateHand())
    }

    @Test
    fun `calculateHand() - player has Three ACE and TWO`() {
        val player = Player("player")

        player.drawCard(Fixture.DIAMONDS_ACE)
        player.drawCard(Fixture.SPADES_ACE)
        player.drawCard(Fixture.HEARTS_ACE)
        player.drawCard(Fixture.DIAMONDS_TWO)

        assertEquals(15, player.calculateHand())
    }

    @Test
    fun `calculateHand() - player has Three ACE and TEN`() {
        val player = Player("player")

        player.drawCard(Fixture.DIAMONDS_ACE)
        player.drawCard(Fixture.SPADES_ACE)
        player.drawCard(Fixture.HEARTS_ACE)
        player.drawCard(Fixture.DIAMONDS_TEN)

        assertEquals(13, player.calculateHand())
    }

    @Test
    fun `isBust() - player is busted`() {
        val player = Player("player")

        player.drawCard(Fixture.DIAMONDS_TEN)
        player.drawCard(Fixture.DIAMONDS_JACK)
        player.drawCard(Fixture.DIAMONDS_QUEEN)
        assertEquals(true, player.isBust())
    }

    @Test
    fun `isBust() - player is not busted`() {
        val player = Player("player")

        player.drawCard(Fixture.DIAMONDS_TEN)
        player.drawCard(Fixture.DIAMONDS_JACK)
        assertEquals(false, player.isBust())
    }

    @Test
    fun `isBlackjack() - player is not blackjack but not busted`() {
        val player = Player("player")
        player.drawCard(Fixture.DIAMONDS_JACK)
        player.drawCard(Fixture.DIAMONDS_QUEEN)
        assertEquals(false, player.isBust())
        assertEquals(false, player.isBlackjack())
    }

    @Test
    fun `isBlackjack() - player is not blackjack but also busted`() {
        val player = Player("player")
        player.drawCard(Fixture.DIAMONDS_JACK)
        player.drawCard(Fixture.DIAMONDS_QUEEN)
        player.drawCard(Fixture.DIAMONDS_SIX)
        assertEquals(true, player.isBust())
        assertEquals(false, player.isBlackjack())
    }

    @Test
    fun `isBlackjack() - player is blackjack`() {
        val player = Player("player")
        player.drawCard(Fixture.DIAMONDS_JACK)
        player.drawCard(Fixture.DIAMONDS_ACE)
        assertEquals(true, player.isBlackjack())
    }
}
