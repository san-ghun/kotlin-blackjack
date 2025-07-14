package blackjack.model

enum class Result(val value: Int) {
    LOSE(0),
    WIN(1),
    TIE(2),
    BLACKJACK(3),
}
