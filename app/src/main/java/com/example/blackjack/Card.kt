package com.example.blackjack

class Card {
    enum class Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }
    enum class Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE, HIDDEN
    }

    data class PlayingCard(val suit: Suit, val rank: Rank)
}
