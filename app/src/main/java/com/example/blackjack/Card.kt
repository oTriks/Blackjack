package com.example.blackjack

class Card {
    enum class Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }
    enum class Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    data class Card(val suit: Suit, val rank: Rank)
}
