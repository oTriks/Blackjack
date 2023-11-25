package com.example.blackjack

class Deck {
var cards: MutableList<Card.Card> = mutableListOf()


    init {
        for (suit in Card.Suit.values()) {
            for (rank in Card.Rank.values()) {
                if (rank != Card.Rank.HIDDEN) {
                    cards.add(Card.Card(suit, rank))
                }
            }
        }
    }

    fun dealHand(playerHand: MutableList<Card.Card>, dealerHand: MutableList<Card.Card>) {
        var card = cards.random()
        playerHand.add(card)
        cards.remove(card)

        card = cards.random()
        dealerHand.add(card)
        cards.remove(card)

        card = cards.random()
        playerHand.add(card)
        cards.remove(card)

        card = cards.random()
        dealerHand.add(card)
        cards.remove(card)
    }

//    val newCard = deck.cards.random()
//    deck.cards.remove(newCard)         SKAPA
//    playerHand.cards.add(newCard)



}