package blackjack.controller

import blackjack.model.Dealer
import blackjack.model.Stats
import blackjack.view.InputView
import blackjack.view.OutputView

object GameMaster {
    val cardManager = CardManager()
    val playerManager = PlayerManager()
    val dealer = Dealer()

    fun run() {
        takePlayerNames()
        takePlayerBets()
        initHands()
        OutputView.displayInitialState(playerManager.players, dealer)
        askPlayersToHit()
        drawDealerCards()
        OutputView.displayFinalState(playerManager.players, dealer)
        val winStatistics = Stats(playerManager.players, dealer)
        OutputView.displayEarnings(winStatistics)
    }

    private fun takePlayerNames() {
        val names = InputView.retryable { InputView.readPlayerNames() }
        names.forEach { name -> playerManager.addPlayer(name) }
    }

    private fun takePlayerBets() {
        val players = playerManager.players
        players.forEach { player ->
            val bettingAmount = InputView.readPlayerBettingAmount(player.name)
            player.placeBets(bettingAmount)
        }
    }

    private fun initHands() {
        repeat(2) {
            playerManager.players.forEach { player ->
                player.drawCard(cardManager.giveCard())
            }
            dealer.drawCard(cardManager.giveCard())
        }
    }

    private fun askPlayersToHit() {
        playerManager.players.forEach { player ->
            playerManager.askPlayerHit(player) { cardManager.giveCard() }
        }
    }

    private fun drawDealerCards() {
        while (dealer.shouldDrawCardOrNot()) {
            dealer.drawCard(cardManager.giveCard())
            OutputView.displayDealerDrawsCard()
        }
    }
}
