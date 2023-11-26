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

    fun dealHand(playerHand: Hand, dealerHand: Hand) {
        var card = cards.random()
        playerHand.cards.add(card)
        cards.remove(card)

        card = cards.random()
        dealerHand.cards.add(card)
        cards.remove(card)

        card = cards.random()
        playerHand.cards.add(card)
        cards.remove(card)

        card = cards.random()
        dealerHand.hiddenCard = card
        cards.remove(card)
    }

    fun drawCard(hand: MutableList<Card.Card>) {
        val card = cards.random()
        hand.add(card)
        cards.remove(card)
    }

//    val newCard = deck.cards.random()
//    deck.cards.remove(newCard)         SKAPA
//    playerHand.cards.add(newCard)



}