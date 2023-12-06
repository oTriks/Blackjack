package com.example.blackjack

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.blackjack.DelaysUtil.Companion.forthCard
import com.example.blackjack.DelaysUtil.Companion.secondCard
import com.example.blackjack.DelaysUtil.Companion.thirdCard

class Deck(
    private val context: Context,
    private val firstCardImageView: ImageView,
    private val secondCardImageView: ImageView,
    private val thirdCardImageView: ImageView,
    private val fourthCardImageView: ImageView,
    private val fifthCardImageView: ImageView,
    private val sixthCardImageView: ImageView,
) {
    var cards: MutableList<Card.PlayingCard> = mutableListOf()
    val handler = Handler(Looper.getMainLooper())
    val cardImageViews = mutableListOf<ImageView>()

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
        val animatorRotation =
            ObjectAnimator.ofFloat(cardImageView, View.ROTATION, rotationStart, rotationEnd)

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
//        var card = cards.random()
        var card = Card.PlayingCard(Card.Suit.SPADES, Card.Rank.ACE)

        playerHand.cards.add(card)
        cards.remove(card)
        performDealingAnimation(
            firstCardImageView,
            secondCardImageView.x,
            secondCardImageView.y,
            firstCardImageView.x,
            firstCardImageView.y,
            0f,
            180f
        )
        firstCardImageView.visibility = View.VISIBLE


//        card = cards.random()
            card = Card.PlayingCard(Card.Suit.SPADES, Card.Rank.ACE)
        dealerHand.cards.add(card)
        cards.remove(card)
        handler.postDelayed({
            performDealingAnimation(
                thirdCardImageView,
                fourthCardImageView.x,
                fourthCardImageView.y,
                thirdCardImageView.x,
                thirdCardImageView.y,
                0f,
                180f
            )
            thirdCardImageView.visibility = View.VISIBLE
        }, secondCard)

            card = Card.PlayingCard(Card.Suit.SPADES, Card.Rank.FIVE)
//        card = cards.random()
        playerHand.cards.add(card)
        cards.remove(card)
        handler.postDelayed({
            performDealingAnimation(
                fifthCardImageView,
                secondCardImageView.x,
                secondCardImageView.y,
                fifthCardImageView.x,
                fifthCardImageView.y,
                0f,
                180f
            )
            fifthCardImageView.visibility = View.VISIBLE
        }, thirdCard)

            card = Card.PlayingCard(Card.Suit.HEARTS, Card.Rank.TEN)
//        card = cards.random()
        dealerHand.hiddenCard = card
        cards.remove(card)
        handler.postDelayed({
            performDealingAnimation(
                sixthCardImageView,
                fourthCardImageView.x,
                fourthCardImageView.y,
                sixthCardImageView.x,
                sixthCardImageView.y,
                0f,
                180f
            )
            sixthCardImageView.visibility = View.VISIBLE
        }, forthCard)

    }

    fun drawCard(hand: MutableList<Card.PlayingCard>) {
        val card = cards.random()
        hand.add(card)
        cards.remove(card)
    }
}