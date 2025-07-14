package blackjack.view

import blackjack.model.Dealer
import blackjack.model.Player
import blackjack.model.Stats

object OutputView {
    fun displayInitialState(
        players: List<Player>,
        dealer: Dealer,
    ) {
        var sentence = "dealer, "
        val names = players.map { it.name }
        sentence += names.joinToString(", ")
        println("\nDealing two cards to $sentence.")
        println("Dealer: ${dealer.hand.cards[0].string}")
        players.forEach { displayCurrentHand(it) }
    }

    fun displayCurrentHand(player: Player) {
        val hand = player.hand
        println("${player.name}'s cards: ${hand.toText()}")
    }

    fun displayDealerDrawsCard() {
        println("\nDealer draws one more card due to having 16 or less.")
    }

    fun displayFinalState(
        players: List<Player>,
        dealer: Dealer,
    ) {
        println("\nDealer's cards: ${dealer.hand.toText()} – Total: ${dealer.calculateHand()}")
        players.forEach { player ->
            println("${player.name}'s cards: ${player.hand.toText()} – Total: ${player.calculateHand()}")
        }
    }

    fun displayEarnings(winStatistics: Stats) {
        val players = winStatistics.players
        val dealer = winStatistics.dealer
        val earningsMap = winStatistics.earningMap
        println("\n## Final Earnings")
        println("Dealer: ${earningsMap[dealer]}")
        players.forEach { player ->
            println("${player.name}: ${earningsMap[player]}")
        }
    }
}
