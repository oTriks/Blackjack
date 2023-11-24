package com.example.blackjack

class Deck {
var cards: MutableList<Card.Card> = mutableListOf()


    init {
        for (suit in Card.Suit.values()) {
            for (rank in Card.Rank.values()) {
                cards.add(Card.Card(suit, rank))
            }
        }
    }

    fun dealHand(hand: MutableList<Card.Card>) {
        repeat(2) {
            val card = cards.random()
            hand.add(card)
            cards.remove(card)
        }
    }
}