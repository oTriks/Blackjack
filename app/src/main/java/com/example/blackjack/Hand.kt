package com.example.blackjack

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView

class Hand {
    var cards: MutableList<Card.PlayingCard> = mutableListOf()
    var hiddenCard: Card.PlayingCard? = null
    fun calculatePoints(hand: List<Card.PlayingCard>): Pair<Int, Int>{
        var pointsWithAceAsOne   = 0
        var aces = 0


        for (card in hand) {
            pointsWithAceAsOne  += when (card.rank) {
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
                    1
                }
                Card.Rank.HIDDEN -> 0
            }
        }
        val pointsWithAceAsEleven = pointsWithAceAsOne + aces * 10

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


    fun flipCard(cardImageView: ImageView) {
        val animator1 = ObjectAnimator.ofFloat(cardImageView, View.ROTATION_Y, 0f, 180f)
        animator1.duration = 500

//        val animator2 = ObjectAnimator.ofFloat(cardImageView, View.ROTATION_Y, 180f, 0f)
//        animator2.duration = 500

//        val set = AnimatorSet()
//        set.playSequentially(animator1, animator2)
        val set = AnimatorSet()
        set.playSequentially(animator1)

        set.start()
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