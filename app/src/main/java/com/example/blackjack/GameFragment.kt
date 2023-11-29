package com.example.blackjack

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.internal.ViewUtils.dpToPx
import kotlin.math.roundToInt

class GameFragment : Fragment() {
    val dealerHand = Hand()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("BlackJack", "onCreateView called")

        val view = inflater.inflate(R.layout.fragment_game, container, false)

        val marker5 = view.findViewById<ImageView>(R.id.marker5ImageView)
        val marker10 = view.findViewById<ImageView>(R.id.marker10ImageView)
        val marker25 = view.findViewById<ImageView>(R.id.marker25ImageView)
        val marker50 = view.findViewById<ImageView>(R.id.marker50ImageView)
        val marker100 = view.findViewById<ImageView>(R.id.marker100ImageView)
        val cardBlankDealer = view.findViewById<ImageView>(R.id.cardBlankDealerImageView)
        val cardNextCardDealer = view.findViewById<ImageView>(R.id.cardNextCardDealer)
        val cardDarkDealer = view.findViewById<ImageView>(R.id.cardDarkDealerImageView)
        val cardDarkDealerTest = view.findViewById<ImageView>(R.id.cardDarkDealerTestImageView)
        val card1BlankPlayer = view.findViewById<ImageView>(R.id.card1BlankPlayerImageView)
        val cardNextCard = view.findViewById<ImageView>(R.id.cardNextCardImageView)
        val card2BlankPlayer = view.findViewById<ImageView>(R.id.card2BlankPlayerImageView)
        val madeBetMarker5 = view.findViewById<ImageView>(R.id.madeBetMarker5ImageView)
        val madeBetMarker10 = view.findViewById<ImageView>(R.id.madeBetMarker10ImageView)
        val madeBetMarker25 = view.findViewById<ImageView>(R.id.madeBetMarker25ImageView)
        val madeBetMarker50 = view.findViewById<ImageView>(R.id.madeBetMarker50ImageView)
        val madeBetMarker100 = view.findViewById<ImageView>(R.id.madeBetMarker100ImageView)
        val deal = view.findViewById<ImageButton>(R.id.dealImageButton)
        val stand = view.findViewById<ImageButton>(R.id.standImageButton)
        val hit = view.findViewById<ImageButton>(R.id.hitImageButton)
        val split = view.findViewById<ImageButton>(R.id.splitImageButton)
        val doubleDown = view.findViewById<ImageButton>(R.id.doubleDownImageButton)
        val repeatBet = view.findViewById<ImageButton>(R.id.repeatBetImageButton)
        val bannerWin = view.findViewById<ImageView>(R.id.bannerWinImageView)
        val bannerLose = view.findViewById<ImageView>(R.id.bannerLoseImageView)
        val bannerBust = view.findViewById<ImageView>(R.id.bannerBustImageView)
        val bannerBlackjack = view.findViewById<ImageView>(R.id.bannerBlackjackImageView)
        val constraintlayout = view.findViewById<ConstraintLayout>(R.id.constraintlayout)
        val cardPile = view.findViewById<ImageView>(R.id.cardPileImageView)
        var totalBetText = view.findViewById<TextView>(R.id.totalBetTextView)
        var pointsPlayerText = view.findViewById<TextView>(R.id.pointsPlayerTextView)
        var pointsDealerText = view.findViewById<TextView>(R.id.pointsDealerTextView)


        cardBlankDealer.visibility = View.INVISIBLE
        cardNextCardDealer.visibility = View.INVISIBLE
        cardDarkDealer.visibility = View.INVISIBLE

        card1BlankPlayer.visibility = View.INVISIBLE
        card2BlankPlayer.visibility = View.INVISIBLE
        cardNextCard.visibility = View.INVISIBLE
        cardPile.visibility = View.INVISIBLE
        madeBetMarker5.visibility = View.INVISIBLE
        madeBetMarker10.visibility = View.INVISIBLE
        madeBetMarker25.visibility = View.INVISIBLE
        madeBetMarker50.visibility = View.INVISIBLE
        madeBetMarker100.visibility = View.INVISIBLE
        hit.visibility = View.INVISIBLE
        stand.visibility = View.INVISIBLE
        deal.visibility = View.INVISIBLE
        split.visibility = View.INVISIBLE
        doubleDown.visibility = View.INVISIBLE
        repeatBet.visibility = View.INVISIBLE
        bannerWin.visibility = View.INVISIBLE
        bannerLose.visibility = View.INVISIBLE
        bannerBust.visibility = View.INVISIBLE
        bannerBlackjack.visibility = View.INVISIBLE


        var totalBet = 0
        var highscore = 0
        val cardImageViews: MutableList<ImageView> = mutableListOf(card1BlankPlayer, card2BlankPlayer, cardNextCard)
        val dealerCardImageViews: MutableList<ImageView> = mutableListOf(cardBlankDealer, cardDarkDealer, cardNextCardDealer)
        val cardDisplay = CardDisplay()
        val playerHand = Hand()
        val dealerHand = Hand()
        var lastBetMarkers = mutableListOf<ImageView>()
        var lastBet = 0
        var winner = ""
        val handler = Handler()
        val delayMillisCardsToPile = 2000L
        val delayMillisPile = 2500L
        val delayMillisBanner = 2800L

        fun animateCardDealing(
            cardImageView: ImageView,
            startX: Float,
            startY: Float,
            endX: Float,
            endY: Float,
            duration: Long,
            delay: Long
        ) {
            val animatorX = ObjectAnimator.ofFloat(cardImageView, View.X, startX, endX)
            animatorX.interpolator = AccelerateDecelerateInterpolator()
            animatorX.duration = duration
            animatorX.startDelay = delay
            animatorX.start()

            val animatorY = ObjectAnimator.ofFloat(cardImageView, View.Y, startY, endY)
            animatorY.interpolator = AccelerateDecelerateInterpolator()
            animatorY.duration = duration
            animatorY.startDelay = delay
            animatorY.start()
        }
        fun animateCardsToPile(cardImageViews: List<ImageView>, cardPile: ImageView) {
            val cardPileX = cardPile.x
            val cardPileY = cardPile.y

//            for ((index, cardImageView) in cardImageViews.withIndex()) {
//                animateCardDealing(cardImageView, cardImageView.x, cardImageView.y, cardPileX, cardPileY, 500L, index * 200L)
//            }
            cardImageViews.forEachIndexed { index, cardImageView ->
                animateCardDealing(cardImageView, cardImageView.x, cardImageView.y, cardPileX, cardPileY, 500L, index * 200L)
                cardImageView.setImageResource(R.drawable.card_dark)

            }
            dealerCardImageViews.forEachIndexed { index, cardImageView ->
                animateCardDealing(cardImageView, cardImageView.x, cardImageView.y, cardPileX, cardPileY, 500L, index * 200L)
                cardImageView.setImageResource(R.drawable.card_dark)
            }
        }

fun handleVisibilityChangesWithAnimation(banner: View) {
    handler.postDelayed({
        animateCardsToPile(cardImageViews, cardPile)
    }, delayMillisCardsToPile)

    handler.postDelayed({
        cardPile.visibility = View.VISIBLE
    }, delayMillisPile)

    handler.postDelayed({
        banner.visibility = View.INVISIBLE
    }, delayMillisBanner)
}


        fun emptyBoard(){

        }

        fun gameEnd() {



            if (winner == "player") {
                highscore += totalBet //
            } else if (winner == "dealer") {
                highscore -= totalBet
            }
            repeatBet.visibility = View.VISIBLE
        }

fun dpToPx(dp: Int): Int {
    val density = resources.displayMetrics.density
    return (dp * density).roundToInt()
}

        fun updateDealerCards(dealerHand: Hand) {
            dealerHand.cards.forEachIndexed { i, card ->
                val imageView: ImageView
                if (i < dealerCardImageViews.size) {
                    imageView = dealerCardImageViews[i]
                } else {
                    imageView = ImageView(context)
                    dealerCardImageViews.add(imageView)
                }

                val image = cardDisplay.getCardImage(card)
                imageView.setImageResource(image)
            }
        }

        fun compareHands(playerPoints: Int, dealerPoints: Int): String {
            Log.d("BlackJack", "first compare")
            return when {

                playerPoints > 21 -> "Dealer"
                dealerPoints > 21 -> "Player"
                playerPoints > dealerPoints -> "Player"
                dealerPoints > playerPoints -> "Dealer"
                else -> "Draw"
            }
        }

        fun flipCard(cardImageView: ImageView) {
            val animator1 = ObjectAnimator.ofFloat(cardImageView, View.ROTATION_Y, 0f, 180f)
            animator1.duration = 500

            val animator2 = ObjectAnimator.ofFloat(cardImageView, View.ROTATION_Y, 180f, 0f)
            animator2.duration = 500

            val set = AnimatorSet()
            set.playSequentially(animator1, animator2)

            set.start()
        }




        fun dealToDealer(dealerHand: Hand, deck: Deck, dealerPointsText: TextView) {
            Log.d(
                "BlackJack",
                "dealToDealer called. Dealer's hand before dealing: ${dealerHand.cards}"
            )

            val (pointsWithAceAsOne, pointsWithAceAsEleven) = dealerHand.calculatePoints(dealerHand.cards)
            if (pointsWithAceAsOne > 21 && pointsWithAceAsEleven > 21) {
                if (pointsWithAceAsOne == pointsWithAceAsEleven || pointsWithAceAsEleven > 21) {
                    dealerPointsText.text = pointsWithAceAsOne.toString()
                } else {
                    dealerPointsText.text = "$pointsWithAceAsOne/$pointsWithAceAsEleven"
                }
                bannerWin.visibility = View.VISIBLE
                Handler().postDelayed({
                    bannerWin.visibility = View.INVISIBLE
                }, 2000)
                return
            }
            if (pointsWithAceAsOne == pointsWithAceAsEleven || pointsWithAceAsEleven > 21) {
                dealerPointsText.text = pointsWithAceAsOne.toString()
            } else {
                dealerPointsText.text = "$pointsWithAceAsOne/$pointsWithAceAsEleven"
            }

            if (pointsWithAceAsOne < 17 || pointsWithAceAsEleven < 17) {
                deck.drawCard(dealerHand.cards)
                val newCard = dealerHand.cards.last()
                val newCardImageView = ImageView(context)
                newCardImageView.setImageResource(cardDisplay.getCardImage(newCard))
                val constraintSet = ConstraintSet()
                val offset = dpToPx(50)


                constraintSet.clone(constraintlayout)
                constraintSet.clear(cardDarkDealer.id, ConstraintSet.END)
                constraintSet.connect(
                    cardDarkDealer.id,
                    ConstraintSet.START,
                    cardBlankDealer.id,
                    ConstraintSet.START,
                    offset
                )
                constraintlayout.postDelayed({
                    constraintSet.applyTo(constraintlayout)
                }, 5000)

                constraintlayout.postDelayed({
                    constraintSet.clone(constraintlayout)
                    constraintSet.clear(cardNextCardDealer.id, ConstraintSet.END)
                    constraintSet.connect(
                        cardNextCardDealer.id,
                        ConstraintSet.START,
                        cardDarkDealer.id,
                        ConstraintSet.START,
                        offset
                    )
                    constraintSet.applyTo(constraintlayout)
                }, 5000)


                newCardImageView?.let {
                    val image = cardDisplay.getCardImage(newCard)
                    it.setImageResource(image)
                    cardNextCardDealer.visibility = View.VISIBLE
                    it.visibility = View.VISIBLE
                    val index = dealerHand.cards.indexOf(newCard)
                    if (index < dealerCardImageViews.size) {
                        dealerCardImageViews[index].setImageResource(
                            cardDisplay.getCardImage(
                                newCard
                            )
                        )
                        dealerCardImageViews[index].visibility = View.VISIBLE
                    }
                }

                Handler().postDelayed({
                    dealToDealer(dealerHand, deck, dealerPointsText)
                }, 1000)
            } else {
                val playerPoints = playerHand.calculatePoints(playerHand.cards)
                winner = compareHands(playerPoints.first, pointsWithAceAsOne)

                when (winner) {
                    "Player" -> {
                        bannerWin.visibility = View.VISIBLE
                        handleVisibilityChangesWithAnimation(bannerWin)
                    }

                    "Dealer" -> {
                        bannerLose.visibility = View.VISIBLE
                        handleVisibilityChangesWithAnimation(bannerLose)
                    }
                }
                gameEnd()
            }
        }




        fun setupMarker(
            marker: ImageView,
            madeBetMarker: ImageView,
            value: Int,
            totalBetText: TextView,
            dealButton: ImageButton
        ) {
            marker.setOnClickListener {
                lastBetMarkers.add(marker)
                madeBetMarker.visibility = View.VISIBLE
                totalBet += value
                totalBetText.text = totalBet.toString()
                deal.visibility = View.VISIBLE
            }
        }


        setupMarker(marker5, madeBetMarker5, 5, totalBetText, deal)
        setupMarker(marker10, madeBetMarker10, 10, totalBetText, deal)
        setupMarker(marker25, madeBetMarker25, 25, totalBetText, deal)
        setupMarker(marker50, madeBetMarker50, 50, totalBetText, deal)
        setupMarker(marker100, madeBetMarker100, 100, totalBetText, deal)

        var lastCardId: Int = cardNextCard.id
        val deck = Deck(requireContext())


        fun startGame() {

            lastBetMarkers.clear()
            deck.dealHand(playerHand, dealerHand)
            updateDealerCards(dealerHand)
            val (pointsWithAceAsOne, pointsWithAceAsEleven) = playerHand.calculatePoints(playerHand.cards)

            playerHand.cards.forEachIndexed { i, card ->
                if (i < cardImageViews.size) {
                    val image = cardDisplay.getCardImage(card)
                    cardImageViews[i].setImageResource(image)


                }
            }
//
            dealerHand.cards.forEachIndexed { i, card ->
                if (i < cardImageViews.size) {
                    val image = cardDisplay.getCardImage(card)
                    dealerCardImageViews[i].setImageResource(image)
                }
            }

            deal.visibility = View.INVISIBLE
            card1BlankPlayer.visibility = View.VISIBLE
            card2BlankPlayer.visibility = View.VISIBLE
            cardBlankDealer.visibility = View.VISIBLE
            cardDarkDealer.visibility = View.VISIBLE
            hit.visibility = View.VISIBLE
            stand.visibility = View.VISIBLE
            doubleDown.visibility = View.INVISIBLE

            if (pointsWithAceAsOne == pointsWithAceAsEleven || pointsWithAceAsEleven > 21) {
                pointsPlayerText.text = pointsWithAceAsOne.toString()
            } else {
                pointsPlayerText.text = "$pointsWithAceAsOne/$pointsWithAceAsEleven"
            }

            repeatBet.visibility = View.VISIBLE
            repeatBet.setOnClickListener {
                totalBet = lastBet
                startGame()
            }
        }


        deal.setOnClickListener {
            startGame()
        }


        hit.setOnClickListener {

            deck.drawCard(playerHand.cards)
            val newCard = playerHand.cards.last()
            val image = cardDisplay.getCardImage(newCard)
            val transition = ChangeBounds()
            transition.duration = 3000
            val newConstraintLayout = ConstraintLayout(view.context)
            newConstraintLayout.layoutParams = constraintlayout.layoutParams

            cardNextCard.visibility = View.VISIBLE
            cardNextCard.setImageResource(image)
            cardNextCard.id = View.generateViewId()


            val offsetDp = 15f
            val density = resources.displayMetrics.density
            val offsetPx = (offsetDp * density).toInt()
            val card1BlankPlayerEndX = card1BlankPlayer.x + offsetPx
//            val delayMillisDealNextCard = 1000L
//            TransitionManager.beginDelayedTransition(constraintlayout, transition)
//            constraintlayout.findViewById<ImageView>(cardNextCard.id)?.apply {
//                visibility = View.VISIBLE
//                setImageResource(image)
//                id = View.generateViewId()
//            }
            animateCardDealing(card2BlankPlayer, card2BlankPlayer.x, card2BlankPlayer.y, card1BlankPlayerEndX, card1BlankPlayer.y, 500L, 0L)


            val card2BlankPlayerEndX = card2BlankPlayer.x + offsetPx
//            handler.postDelayed({
            deck.performDealingAnimation(cardNextCard, cardDarkDealer.x, cardDarkDealer.y, card2BlankPlayerEndX, card1BlankPlayer.y, 0f, 180f)
//            }, delayMillisDealNextCard)







            val (pointsWithAceAsOne, pointsWithAceAsEleven) = playerHand.calculatePoints(
                playerHand.cards
            )
            if (pointsWithAceAsOne == pointsWithAceAsEleven || pointsWithAceAsEleven > 21) {
                pointsPlayerText.text = pointsWithAceAsOne.toString()
            } else {
                pointsPlayerText.text = "$pointsWithAceAsOne/$pointsWithAceAsEleven"
            }
            if (pointsWithAceAsOne > 21 && pointsWithAceAsEleven > 21) {
                bannerBust.visibility = View.VISIBLE
                Handler().postDelayed({
                    bannerBust.visibility = View.INVISIBLE
                }, 2000)
                hit.visibility = View.INVISIBLE
            }
        }

            stand.setOnClickListener {

                dealerHand.revealHiddenCard()

                val (pointsWithAceAsOne, pointsWithAceAsEleven) = dealerHand.calculatePoints(dealerHand.cards)
                if (dealerHand.cards.size == 2 && (pointsWithAceAsOne == 21 || pointsWithAceAsEleven == 21)) {
                    bannerBlackjack.visibility = View.VISIBLE
                    Handler().postDelayed({
                        bannerBlackjack.visibility = View.INVISIBLE
                    }, 1000)
                } else {

                    dealToDealer(dealerHand, deck, pointsDealerText)
                }
                stand.visibility = View.INVISIBLE
                hit.visibility = View.INVISIBLE
                updateDealerCards(dealerHand)
                }

            repeatBet.setOnClickListener {
                for (marker in lastBetMarkers) {
                    marker.visibility = View.VISIBLE
                    totalBet = lastBet
                    // remove constraints / default constraints for cards
                    startGame()
                }
            }



        return view
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


        }



