# kotlin-blackjack

### Reminders for Improvement from Reviewer --Suno (@Wordbe)

- Use backing properties only when truly necessary.
    - They can make the code harder to read and understand.
- Instead of using numbers to represent states, consider using enums.
    - They make the code much more readable.
- Avoid creating and parsing strings unnecessarily.
- Manage data in structured formats from the beginning—it’ll be easier to maintain.
- Strive to simplify your code as much as possible.
    - Simple code is always easier to manage than complex logic.
- Replace magic numbers with constants.
    - This improves clarity, reduces mistakes, and enhances overall understanding.

## Features List

### Model

Card

- [x] Card class hold string value of name(e.g. "A♥") and string value of digit(e.g. "A", "5", "K")

Hand

- [x] has `List<Card>` for player and dealer
- [x] extract logic for calculation of hand's score from `Player` and `Dealer` into `calculateCards()`

Rank

- [x] Add new enum class 'Rank'(for example) and use the class to calculate score of player's hand and dealer's hand

Playable (interface)

- [x] has value `name` in string
- [x] has list of card called `hand`
- [x] has method `requestCard()` to request a card by condition then, ask the card manager with boolean
- [x] has method `drawCard()` to take card given by a card manager
- [x] has method `calculateHand()` to calculate score of hand
- [x] has method `isBust()` to figure out the player of the dealer is bust or not
- [x] `isBlackjack()` to figure out a `Playable` is blackjack or not

Player: Playable

- [x] follows interface `Playable`
- [x] has method `calculateHand()` to calculate score of hand with `Rank` of `Card`
    - TODO: MUST have to find a solid logic for calculation
- [x] `bet` is property `Int` under `Playable`
- [x] init player bet -> bet = 0
- [x] player `placeBets(amount: Int)` -> (+ betting amount)

Dealer: Playable

- [x] follows interface `Playable`
- [x] has a method `shouldDrawCardOrNot()` return true or false to 'requestCard()' or not

Stats

- [x] has `players: List<Player>`, `dealer: Dealer` having instance of players and dealer
- [x] has `playerBoard: Map<Player, Int>` having key as player and value as result of player
- [x] has `dealerStats: Map<String, Int>` having key as "win", "lose", "tie" and value as count
- [x] has method `initPlayerBoard()` to initiate `playerBoard` based on the result of each player against dealer
- [x] has method `updateDealerStats()` to update `dealerStats` calculation based on `playerBoard`

- [x] `pot` is property of `Int` under `Stats`
- [x] `collectBets()` get sum of bet values from all players
- [ ] `payOutPotToEarnings()` calculate and return earning map between player and dealer
  - [ ] TODO: refactor the function into separate
  - [ ] game result -> dealer has blackjack
    - [ ] player non, dealer blackjack -> player earning -= bet
    - [ ] player blackjack, dealer blackjack -> player earning = 0; pot -= bet
  - [ ] game result -> dealer has no blackjack
    - [ ] player blackjack -> player earning += bet * 1.5; pot -= bet + (bet * 1.5)
    - [ ] player win -> player earning += bet; pot -= bet * 2
    - [ ] player lose -> player earning -= bet;
    - [ ] player tie -> player earning = 0; pot -= bet

### View

InputView

- [x] "Enter the names of the players (comma-separated):"
- [x] "Enter {name}’s betting amount:"
    - [x] condition. betting value should be dividable to 100, to prevent type conversion like `Int` -> `Double`
- [x] "Would {name} like to draw another card? (y for yes, n for no)"
- [x] add validation for duplicated names in `readPlayerNames()`

OutputView

- [x] "Dealing two cards to dealer, {name of players}."
    - [x] display initial state of hands of dealer and players
        - [x] dealer only show first card (`hand[0]`)
        - [x] player show all two cards
- [x] "{name}'s cards: {hand of player}"
    - [x] first hit question, display hand of player (both y or n)
    - [x] from second hit question, display when player say y
- [x] "Dealer draws one more card due to having 16 or less."
    - [x] display the message everytime when dealer get new card
- [x] "{name}'s cards: {hand} – Total: {score}"
- [x] "## Final Earnings"
    - "Dealer: 10000"
    - "{player.name}: 10000"
    - "{player.name}: -20000"

### Controller

GameManager

- [x] has 'players: List<player>' to store all players -> extract to 'PlayerManager'
- [x] has 'dealer: Dealer' -> extract to 'DealerManager'
- [x] has 'winStatistics: Stats' to store data related with result of the game
- [x] has link with 'InputView' to take user inputs
- [x] has link with 'OutputView' to output game result
- [x] separate responsibilities of GameManager into several new manager(controller) classes
- [x] refactor `run()` method (It is too big!!!)

CardManager

- [x] has 'cards: List<Card>' and get it from 'CardGenerator' as an input parameter
- [x] has 'cardMap: Map<Card, Boolean>' to figure out a card is available or not
- [x] giveCard method return Card from shuffled 'cards', then check the card to 'false' in 'cardMap'
- [x] use ArrayDeque to take a card and remove the card from the deque and update '_cards'

PlayerManager

- [x] has 'players: List<Player>'
- [x] takes player related control logic from 'GameManager'

DealerManager

- [x] has 'dealer: Dealer'
- [x] takes dealer related control logic from 'GameManager'

StatsManager

- [x] has 'winStatistics: Stats'
- [x] takes stats related control logic from 'GameManager'

### Utils

CardGenerator

- [x] This is an object
- [x] generateSuits method create list of cards in String form by combining digit part and figure part
- [x] generateCard method create card with combination of 'A 1 2 ... 10 J Q K' * ♥, ♣, ♠, ♦
    - ex. A♥, 4♣, 9♠, 6♦
- [x] generateCards method create 52 unique cards
