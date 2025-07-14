package blackjack.model

import blackjack.Fixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StatsTest {
//    @Test
//    fun `playerBoard - Stats make good player board that represent state of player`() {
//        // player1 -> bust
//        val player1 = Player("player1")
//        player1.drawCard(Fixture.DIAMONDS_TEN)
//        player1.drawCard(Fixture.DIAMONDS_JACK)
//        player1.drawCard(Fixture.DIAMONDS_QUEEN)
//
//        // player2 -> win
//        val player2 = Player("player2")
//        player2.drawCard(Fixture.DIAMONDS_TEN)
//        player2.drawCard(Fixture.DIAMONDS_ACE)
//
//        // player3 -> tie
//        val player3 = Player("player3")
//        player3.drawCard(Fixture.DIAMONDS_NINE)
//        player3.drawCard(Fixture.DIAMONDS_JACK)
//
//        val dealer = Dealer()
//        dealer.drawCard(Fixture.DIAMONDS_NINE)
//        dealer.drawCard(Fixture.DIAMONDS_JACK)
//
//        val stats = Stats(listOf(player1, player2, player3), dealer)
//        val board = stats.playerBoard
//        assertEquals(Result.LOSE, board[player1])
//        assertEquals(Result.WIN, board[player2])
//        assertEquals(Result.TIE, board[player3])
//    }
//
//    @Test
//    fun `updateDealerStats() - method update dealer's stats`() {
//        // player1 -> bust
//        val player1 = Player("player1")
//        player1.drawCard(Fixture.DIAMONDS_TEN)
//        player1.drawCard(Fixture.DIAMONDS_JACK)
//        player1.drawCard(Fixture.DIAMONDS_QUEEN)
//
//        // player2 -> win
//        val player2 = Player("player2")
//        player2.drawCard(Fixture.DIAMONDS_TEN)
//        player2.drawCard(Fixture.DIAMONDS_ACE)
//
//        // player3 -> tie
//        val player3 = Player("player3")
//        player3.drawCard(Fixture.DIAMONDS_NINE)
//        player3.drawCard(Fixture.DIAMONDS_JACK)
//
//        val dealer = Dealer()
//        dealer.drawCard(Fixture.DIAMONDS_NINE)
//        dealer.drawCard(Fixture.DIAMONDS_JACK)
//
//        val stats = Stats(listOf(player1, player2, player3), dealer)
//        stats.updateDealerStats()
//        val dealerStats = stats.dealerStats
//        assertEquals(1, dealerStats[Result.WIN])
//        assertEquals(1, dealerStats[Result.LOSE])
//        assertEquals(1, dealerStats[Result.TIE])
//    }
//
//    @Test
//    fun `Stats has pot that is sum of betting amounts of all players`() {
//        val bettingAmount01 = 10000
//        val bettingAmount02 = 20000
//        val player1 = Player("player1").placeBets(bettingAmount01)
//        val player2 = Player("player2").placeBets(bettingAmount02)
//        val player3 = Player("player3").placeBets(bettingAmount02)
//        val players = listOf(player1, player2, player3)
//        val stats = Stats(players, Dealer())
//        assertEquals(bettingAmount01 + bettingAmount02 + bettingAmount02, stats.pot)
//    }

    @Test
    fun `payOutPotToEarnings() - one player vs dealer, player bust`() {
        // player1 -> 30 -> bust
        val player1 = Player("player1").placeBets(100)
        player1.drawCard(Fixture.DIAMONDS_TEN)
        player1.drawCard(Fixture.DIAMONDS_JACK)
        player1.drawCard(Fixture.DIAMONDS_QUEEN)

        // dealer -> 19
        val dealer = Dealer()
        dealer.drawCard(Fixture.DIAMONDS_NINE)
        dealer.drawCard(Fixture.DIAMONDS_JACK)

        val players = listOf(player1)

        val stats = Stats(players, dealer)
        assertEquals(-100, stats.earningMap[player1])
    }

    @Test
    fun `payOutPotToEarnings() - one player vs dealer, player blackjack`() {
        // player2 -> blackjack
        val player2 = Player("player2").placeBets(1000)
        player2.drawCard(Fixture.DIAMONDS_TEN)
        player2.drawCard(Fixture.DIAMONDS_ACE)

        // dealer -> 19
        val dealer = Dealer()
        dealer.drawCard(Fixture.DIAMONDS_NINE)
        dealer.drawCard(Fixture.DIAMONDS_JACK)

        val players = listOf(player2)

        val stats = Stats(players, dealer)
        assertEquals(1500, stats.earningMap[player2])
    }

    @Test
    fun `payOutPotToEarnings() - one player vs dealer, player win`() {
        // player3 -> 20 -> win
        val player3 = Player("player3").placeBets(3000)
        player3.drawCard(Fixture.DIAMONDS_TEN)
        player3.drawCard(Fixture.DIAMONDS_JACK)

        // dealer -> 19
        val dealer = Dealer()
        dealer.drawCard(Fixture.DIAMONDS_NINE)
        dealer.drawCard(Fixture.DIAMONDS_JACK)

        val players = listOf(player3)

        val stats = Stats(players, dealer)
        assertEquals(3000, stats.earningMap[player3])
    }

    @Test
    fun `payOutPotToEarnings() - one player vs dealer, player tie`() {
        // player4 -> 19 -> tie
        val player4 = Player("player4").placeBets(4000)
        player4.drawCard(Fixture.DIAMONDS_NINE)
        player4.drawCard(Fixture.DIAMONDS_JACK)

        // dealer -> 19
        val dealer = Dealer()
        dealer.drawCard(Fixture.DIAMONDS_NINE)
        dealer.drawCard(Fixture.DIAMONDS_JACK)

        val players = listOf(player4)

        val stats = Stats(players, dealer)
        assertEquals(0, stats.earningMap[player4])
    }

    @Test
    fun `payOutPotToEarnings() - one player vs dealer, player lose`() {
        // player5 -> 17 -> lose
        val player5 = Player("player5").placeBets(5000)
        player5.drawCard(Fixture.DIAMONDS_SEVEN)
        player5.drawCard(Fixture.DIAMONDS_JACK)

        // dealer -> 19
        val dealer = Dealer()
        dealer.drawCard(Fixture.DIAMONDS_NINE)
        dealer.drawCard(Fixture.DIAMONDS_JACK)

        val players = listOf(player5)

        val stats = Stats(players, dealer)
        assertEquals(-5000, stats.earningMap[player5])
    }

    @Test
    fun `payOutPotToEarnings() - provide map of pay out amounts`() {
        // player1 -> 30 -> bust
        val player1 = Player("player1").placeBets(100)
        player1.drawCard(Fixture.DIAMONDS_TEN)
        player1.drawCard(Fixture.DIAMONDS_JACK)
        player1.drawCard(Fixture.DIAMONDS_QUEEN)

        // player2 -> blackjack
        val player2 = Player("player2").placeBets(1000)
        player2.drawCard(Fixture.DIAMONDS_TEN)
        player2.drawCard(Fixture.DIAMONDS_ACE)

        // player3 -> 20 -> win
        val player3 = Player("player3").placeBets(3000)
        player3.drawCard(Fixture.DIAMONDS_TEN)
        player3.drawCard(Fixture.DIAMONDS_JACK)

        // player4 -> 19 -> tie
        val player4 = Player("player4").placeBets(4000)
        player4.drawCard(Fixture.DIAMONDS_NINE)
        player4.drawCard(Fixture.DIAMONDS_JACK)

        // player5 -> 17 -> lose
        val player5 = Player("player5").placeBets(5000)
        player5.drawCard(Fixture.DIAMONDS_SEVEN)
        player5.drawCard(Fixture.DIAMONDS_JACK)

        // dealer -> 19
        val dealer = Dealer()
        dealer.drawCard(Fixture.DIAMONDS_NINE)
        dealer.drawCard(Fixture.DIAMONDS_JACK)

        val players = listOf(player1, player2, player3, player4, player5)

        val stats = Stats(players, dealer)
        assertEquals(-100, stats.earningMap[player1])
        assertEquals(1500, stats.earningMap[player2])
        assertEquals(3000, stats.earningMap[player3])
        assertEquals(0, stats.earningMap[player4])
        assertEquals(-5000, stats.earningMap[player5])
    }
}
