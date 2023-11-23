package com.example.blackjack

class Deck {
var cards: MutableList<Card> = mutableListOf()
    fun dealHand(hand: MutableList<Card>) {
        repeat(2) {
            val card = cards.removeAt(cards.lastIndex)
            hand.add(card)
        }
    }
}