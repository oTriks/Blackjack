package com.example.blackjack

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActionBar
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout



class Deck(private val context: Context) {
    var cards: MutableList<Card.PlayingCard> = mutableListOf()
    var cardDisplay = CardDisplay()
//    var cardToImageViewMap: MutableMap<Card.PlayingCard, ImageView> = mutableMapOf()

    init {
        for (suit in Card.Suit.values()) {
            for (rank in Card.Rank.values()) {
                if (rank != Card.Rank.HIDDEN) {
                    cards.add(Card.PlayingCard(suit, rank))
                }
            }
        }
    }

fun animateCardDealingWithRotation(
    cardImageView: ImageView,
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    duration: Long,
    delay: Long,
    rotationStart: Float,
    rotationEnd: Float
) {
    val animatorX = ObjectAnimator.ofFloat(cardImageView, View.X, startX, endX)
    val animatorY = ObjectAnimator.ofFloat(cardImageView, View.Y, startY, endY)
    val animatorRotation = ObjectAnimator.ofFloat(cardImageView, View.ROTATION, rotationStart, rotationEnd)

    val animatorSet = AnimatorSet()
    animatorSet.playTogether(animatorX, animatorY, animatorRotation)
    animatorSet.interpolator = AccelerateDecelerateInterpolator()
    animatorSet.duration = duration
    animatorSet.startDelay = delay
    animatorSet.start()
}
    fun performDealingAnimation(
        cardImageView: ImageView,
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        rotationStart: Float,
        rotationEnd: Float
    ) {
        animateCardDealingWithRotation(
            cardImageView,
            startX,
            startY,
            endX,
            endY,
            500L,
            0L,
            rotationStart,
            rotationEnd
        )
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

    fun drawCard(hand: MutableList<Card.PlayingCard>) {
        val card = cards.random()
        hand.add(card)
        cards.remove(card)
    }

//    fun animateCardDealing(card: Card, constraintLayout: ConstraintLayout) {
//        val newCardImageView = ImageView(constraintLayout.context)
//        newCardImageView.setImageResource(cardDisplay.getCardImage(card))
//        constraintLayout.addView(newCardImageView)
//        val startX = 0f
//        val startY = 0f
//        newCardImageView.x = startX
//        newCardImageView.y = startY
//
//        val endX =
//        val endY =
//
//
//        val translateX = ObjectAnimator.ofFloat(newCardImageView, View.TRANSLATION_X, startX, endX)
//        val translateY = ObjectAnimator.ofFloat(newCardImageView, View.TRANSLATION_Y, startY, endY)
//
//
//        val fadeIn = ObjectAnimator.ofFloat(newCardImageView, View.ALPHA, 0f, 1f)
//
//        val animatorSet = AnimatorSet()
//        animatorSet.playTogether(translateX, translateY, fadeIn)
//        animatorSet.duration = 2000
//
//        animatorSet.start()
//
//        // Optionally, remove the ImageView after the animation completes
//        animatorSet.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator?) {
//                constraintLayout.removeView(newCardImageView)
//            }
//        })
//    }

//    fun drawCard(hand: Hand) {
//        val card = cards.random()
//        cards.remove(card)
//        val imageView = ImageView(context)
//        imageView.setImageResource(cardDisplay.getCardImage(card))
////        addCardToHandAndMap(hand, card, imageView)
//    }
}