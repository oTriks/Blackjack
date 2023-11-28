package com.example.blackjack

import android.content.Context
import android.widget.ImageView


class Deck(private val context: Context) {
    var cards: MutableList<Card.PlayingCard> = mutableListOf()
    var cardDisplay = CardDisplay()
    var cardToImageViewMap: MutableMap<Card.PlayingCard, ImageView> = mutableMapOf()

    init {
        for (suit in Card.Suit.values()) {
            for (rank in Card.Rank.values()) {
                if (rank != Card.Rank.HIDDEN) {
                    cards.add(Card.PlayingCard(suit, rank))
                }
            }
        }
    }
//    fun addCardToHandAndMap(hand: Hand, card: Card.PlayingCard, imageView: ImageView) {
//        hand.cards.add(card)
//        cardToImageViewMap[card] = imageView
//    }

//    fun dealHand(playerHand: Hand, dealerHand: Hand) {
//        for (i in 0 until 4) {
//            val card = cards.random()
//            cards.remove(card)
//            val imageView = ImageView(context)
//            imageView.setImageResource(cardDisplay.getCardImage(card))
//            val hand = if (i % 2 == 0) playerHand else dealerHand
//            addCardToHandAndMap(hand, card, imageView)
//        }
//    }
//fun dealHand(playerHand: Hand, dealerHand: Hand) {
//    for (i in 0 until 4) {
//        val card = cards.random()
//        cards.remove(card)
//        val imageView = ImageView(context)
//        imageView.setImageResource(cardDisplay.getCardImage(card))
//        val hand = if (i % 2 == 0) playerHand else dealerHand
//
//        if (i == 1 && hand == dealerHand) {
//            dealerHand.hiddenCard = card
//        } else {
//            addCardToHandAndMap(hand, card, imageView)
//        }
//    }

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

    fun drawCard(hand: MutableList<Card.PlayingCard>) {
        val card = cards.random()
        hand.add(card)
        cards.remove(card)
    }


//    fun drawCard(hand: Hand) {
//        val card = cards.random()
//        cards.remove(card)
//        val imageView = ImageView(context)
//        imageView.setImageResource(cardDisplay.getCardImage(card))
////        addCardToHandAndMap(hand, card, imageView)
//    }
}