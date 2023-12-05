package com.example.blackjack

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class Hand {
    var cards: MutableList<Card.PlayingCard> = mutableListOf()
    var hiddenCard: Card.PlayingCard? = null

fun clear() {
    cards.clear()
}

fun calculatePoints(hand: List<Card.PlayingCard>): Pair<Int, Int> {
    var pointsWithAceAsOne = 0
    var pointsWithAceAsEleven = 0
    var aces = 0

    for (card in hand) {
        when (card.rank) {
            Card.Rank.TWO -> {
                pointsWithAceAsOne += 2
                pointsWithAceAsEleven += 2
            }
            Card.Rank.THREE -> {
                pointsWithAceAsOne += 3
                pointsWithAceAsEleven += 3
            }
            Card.Rank.FOUR -> {
                pointsWithAceAsOne += 4
                pointsWithAceAsEleven += 4
            }
            Card.Rank.FIVE -> {
                pointsWithAceAsOne += 5
                pointsWithAceAsEleven += 5
            }
            Card.Rank.SIX -> {
                pointsWithAceAsOne += 6
                pointsWithAceAsEleven += 6
            }
            Card.Rank.SEVEN -> {
                pointsWithAceAsOne += 7
                pointsWithAceAsEleven += 7
            }
            Card.Rank.EIGHT -> {
                pointsWithAceAsOne += 8
                pointsWithAceAsEleven += 8
            }
            Card.Rank.NINE -> {
                pointsWithAceAsOne += 9
                pointsWithAceAsEleven += 9
            }
            Card.Rank.TEN, Card.Rank.JACK, Card.Rank.QUEEN, Card.Rank.KING -> {
                pointsWithAceAsOne += 10
                pointsWithAceAsEleven += 10
            }
            Card.Rank.ACE -> {
                pointsWithAceAsOne += 1
                pointsWithAceAsEleven += 11
                aces++
            }
            Card.Rank.HIDDEN -> 0
        }
    }


    while (pointsWithAceAsEleven > 21 && aces > 0) {
        pointsWithAceAsEleven -= 10
        aces--
    }

    return Pair(pointsWithAceAsOne, pointsWithAceAsEleven)
}


    fun calculatePointsForBlackjack(cards: List<Card.PlayingCard>): Pair<Int, Int> {
        var pointsWithAceAsOne = 0
        var aces = 0

        for (card in cards) {
            pointsWithAceAsOne += when (card.rank) {
                Card.Rank.TWO -> 2
                Card.Rank.THREE -> 3
                Card.Rank.FOUR -> 4
                Card.Rank.FIVE -> 5
                Card.Rank.SIX -> 6
                Card.Rank.SEVEN -> 7
                Card.Rank.EIGHT -> 8
                Card.Rank.NINE -> 9
                Card.Rank.TEN, Card.Rank.JACK, Card.Rank.QUEEN, Card.Rank.KING -> 10
                Card.Rank.ACE -> {
                    aces++
                    11
                }
                Card.Rank.HIDDEN -> 0
            }
        }

        val pointsWithAceAsEleven = pointsWithAceAsOne + aces * 10


        return Pair(pointsWithAceAsOne, pointsWithAceAsEleven)
    }


    fun revealHiddenCard() {

        if (hiddenCard != null) {
            cards.add(hiddenCard!!)
            hiddenCard = null

        }
    }
    fun isBlackjack(): Boolean {
        return cards.size == 2 && calculatePointsForBlackjack(cards).first == 21
    }


}