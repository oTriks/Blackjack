package com.example.blackjack

class Card {
    enum class Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    enum class Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    data class Card(val suit: Suit, val rank: Rank)

    fun dealHand(deck: MutableList<Card>, hand: MutableList<Card>) {
        repeat(2) {
            val card = deck.removeAt(deck.lastIndex)
            hand.add(card)
        }
    }

    fun calculatePoints(hand: List<Card>): Int {
        var points = 0
        var aces = 0

        for (card in hand) {
            points += when (card.rank) {
                Rank.TWO -> 2
                Rank.THREE -> 3
                Rank.FOUR -> 4
                Rank.FIVE -> 5
                Rank.SIX -> 6
                Rank.SEVEN -> 7
                Rank.EIGHT -> 8
                Rank.NINE -> 9
                Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING -> 10
                Rank.ACE -> {
                    aces++
                    11
                }
            }
        }

        while (points > 21 && aces > 0) {
            points -= 10
            aces--
        }

        return points

    }

    fun getCardImage(card: Card): Int {
        return when (card.suit) {
            Suit.HEARTS -> when (card.rank) {
                Rank.TWO -> R.drawable.card_hearts_2
                Rank.THREE -> R.drawable.card_hearts_3
                Rank.FOUR -> R.drawable.card_hearts_4
                Rank.FIVE -> R.drawable.card_hearts_5
                Rank.SIX -> R.drawable.card_hearts_6
                Rank.SEVEN -> R.drawable.card_hearts_7
                Rank.EIGHT -> R.drawable.card_hearts_8
                Rank.NINE -> R.drawable.card_hearts_9
                Rank.TEN -> R.drawable.card_hearts_10
                Rank.JACK -> R.drawable.card_hearts_j
                Rank.QUEEN -> R.drawable.card_hearts_q
                Rank.KING -> R.drawable.card_hearts_k
                Rank.ACE -> R.drawable.card_hearts_a

            }

            Suit.DIAMONDS -> when (card.rank) {
                Rank.TWO -> R.drawable.card_diamonds_2
                Rank.THREE -> R.drawable.card_diamonds_3
                Rank.FOUR -> R.drawable.card_diamonds_4
                Rank.FIVE -> R.drawable.card_diamonds_5
                Rank.SIX -> R.drawable.card_diamonds_6
                Rank.SEVEN -> R.drawable.card_diamonds_7
                Rank.EIGHT -> R.drawable.card_diamonds_8
                Rank.NINE -> R.drawable.card_diamonds_9
                Rank.TEN -> R.drawable.card_diamonds_10
                Rank.JACK -> R.drawable.card_diamonds_j
                Rank.QUEEN -> R.drawable.card_diamonds_q
                Rank.KING -> R.drawable.card_diamonds_k
                Rank.ACE -> R.drawable.card_diamonds_a
            }

            Suit.SPADES -> when (card.rank) {
                Rank.TWO -> R.drawable.card_spades_2
                Rank.THREE -> R.drawable.card_spades_3
                Rank.FOUR -> R.drawable.card_spades_4
                Rank.FIVE -> R.drawable.card_spades_5
                Rank.SIX -> R.drawable.card_spades_6
                Rank.SEVEN -> R.drawable.card_spades_7
                Rank.EIGHT -> R.drawable.card_spades_8
                Rank.NINE -> R.drawable.card_spades_9
                Rank.TEN -> R.drawable.card_spades_10
                Rank.JACK -> R.drawable.card_spades_j
                Rank.QUEEN -> R.drawable.card_spades_q
                Rank.KING -> R.drawable.card_spades_k
                Rank.ACE -> R.drawable.card_spades_a
            }

            Suit.CLUBS -> when (card.rank) {
                Rank.TWO -> R.drawable.card_clubs_2
                Rank.THREE -> R.drawable.card_clubs_3
                Rank.FOUR -> R.drawable.card_clubs_4
                Rank.FIVE -> R.drawable.card_clubs_5
                Rank.SIX -> R.drawable.card_clubs_6
                Rank.SEVEN -> R.drawable.card_clubs_7
                Rank.EIGHT -> R.drawable.card_clubs_8
                Rank.NINE -> R.drawable.card_clubs_9
                Rank.TEN -> R.drawable.card_clubs_10
                Rank.JACK -> R.drawable.card_clubs_j
                Rank.QUEEN -> R.drawable.card_clubs_q
                Rank.KING -> R.drawable.card_clubs_k
                Rank.ACE -> R.drawable.card_clubs_a

            }

            // val card = Card(Suit.HEARTS, Rank.ACE)

        }
    }
//    val cardImage = getCardImage(card)
//    imageView.setImageResource(cardImage)
}