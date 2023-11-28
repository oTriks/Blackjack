package com.example.blackjack

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
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
        cardPile.visibility = View.VISIBLE
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

//        val cardToImageViewMap = mutableMapOf<Card.PlayingCard, ImageView>()
//        if (playerHand.cards.isNotEmpty()) {
//            cardToImageViewMap[playerHand.cards[0]] = card1BlankPlayer
//            cardToImageViewMap[playerHand.cards[1]] = card2BlankPlayer
//            cardToImageViewMap[playerHand.cards[2]] = cardNextCard
//        }
//        if (dealerHand.cards.isNotEmpty()) {
//            cardToImageViewMap[dealerHand.cards[0]] = cardBlankDealer
//            cardToImageViewMap[dealerHand.cards[1]] = cardDarkDealer
//            cardToImageViewMap[dealerHand.cards[2]] = cardNextCardDealer
//        }
//        if (dealerHand.cards.isNotEmpty()) {
//            Log.d("BlackJack", "Dealer's hand is not empty. Cards: ${dealerHand.cards}")
//
//            cardToImageViewMap[dealerHand.cards[0]] = cardBlankDealer
//            cardToImageViewMap[dealerHand.cards[1]] = cardDarkDealer
//        } else {
//            Log.d("BlackJack", "Dealer's hand is empty.")
//        }



        fun animateCardToPile(card: ImageView) {

            val xAnimator = ObjectAnimator.ofFloat(card, "x", cardPile.x)
            val yAnimator = ObjectAnimator.ofFloat(card, "y", cardPile.y)

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(xAnimator, yAnimator)
            animatorSet.start()
        }

//        fun gameEnd() {
//            if (winner == "player") {
//                highscore += totalBet //
//            } else if (winner == "dealer") {
//                highscore -= totalBet
//            }
//            for (card in playerHand.cards + dealerHand.cards) {
//                val imageView = dealerCardImageViews.firstOrNull { it.card == card }
//
//                if (imageView != null) {
//                    animateCardToPile(imageView)
//                } else {
//                Log.d("BlackJack", "ImageView is null for card: $card")
//            }
//            }
//            repeatBet.visibility = View.VISIBLE
//        }


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

        fun dealToDealer(dealerHand: Hand, deck: Deck, dealerPointsText: TextView) {
            Log.d("BlackJack", "dealToDealer called. Dealer's hand before dealing: ${dealerHand.cards}")

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


                newCardImageView?.let {
                    val image = cardDisplay.getCardImage(newCard)
                    it.setImageResource(image)
                    cardNextCardDealer.visibility = View.VISIBLE
                    it.visibility = View.VISIBLE
                    val index = dealerHand.cards.indexOf(newCard)
                    if (index < dealerCardImageViews.size) {
                        dealerCardImageViews[index].setImageResource(cardDisplay.getCardImage(newCard))
                        dealerCardImageViews[index].visibility = View.VISIBLE
                    }
                }

                Handler().postDelayed({
                    dealToDealer(dealerHand, deck, dealerPointsText)
                }, 1000)
            } else {
                val playerPoints = playerHand.calculatePoints(playerHand.cards)
                winner = compareHands(playerPoints.first, pointsWithAceAsOne)
                if (winner == "Player") {
                    bannerWin.visibility = View.VISIBLE
//                    gameEnd()
                } else if (winner == "Dealer") {
                    bannerLose.visibility = View.VISIBLE
//                    gameEnd()
                }
            }
        }

//        fun dealCardToDealer(card: Card.PlayingCard) {
//            val imageView = ImageView(context)
//            imageView.setImageResource(cardDisplay.getCardImage(card))
//            deck.addCardToHandAndMap(dealerHand, card, imageView)
//        }


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
        fun dpToPx(dp: Int): Int {
            val density = resources.displayMetrics.density
            return (dp * density).roundToInt()
        }

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

            val constraintSet = ConstraintSet()

            deck.drawCard(playerHand.cards)
            val newCard = playerHand.cards.last()
            val image = cardDisplay.getCardImage(newCard)
//            val newCardImageView = ImageView(context)
            cardNextCard.setImageResource(image)
            cardNextCard.visibility = View.VISIBLE
            cardNextCard.id = View.generateViewId()

            cardNextCard.visibility = View.VISIBLE
            cardNextCard.setImageResource(image)
            cardNextCard.id = View.generateViewId()




//            val newCard = playerHand.cards.last()
//            val image = cardDisplay.getCardImage(newCard)
//            cardNextCard.setImageResource(image)


//            constraintSet.clone(constraintlayout)
//            constraintSet.clear(card2BlankPlayer.id, ConstraintSet.END)
//            constraintSet.connect(card2BlankPlayer.id, ConstraintSet.END, card1BlankPlayer.id, ConstraintSet.END, dpToPx(60))
//            constraintSet.applyTo(constraintlayout)


//            constraintSet.clone(constraintlayout)
//            constraintSet.clear(card2BlankPlayer.id, ConstraintSet.END)
//            constraintSet.connect(card2BlankPlayer.id, ConstraintSet.START, card1BlankPlayer.id, ConstraintSet.END, dpToPx(15)) // Add a margin of 15dp
//            constraintSet.applyTo(constraintlayout)


            constraintSet.clone(constraintlayout)
            constraintSet.clear(card2BlankPlayer.id, ConstraintSet.END)
            constraintSet.connect(card2BlankPlayer.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dpToPx(40))
            constraintSet.applyTo(constraintlayout)
            constraintSet.clone(constraintlayout)
            constraintSet.clear(cardNextCard.id, ConstraintSet.END)
            constraintSet.connect(cardNextCard.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dpToPx(40))
            constraintSet.applyTo(constraintlayout)

//            lastCardId = card2BlankPlayer.id
//            constraintSet.connect(cardNextCard.id, ConstraintSet.START, lastCardId, ConstraintSet.END, dpToPx(30))
//            constraintSet.applyTo(constraintlayout)
//            lastCardId = cardNextCard.id


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
                    startGame()
                }

            }



        return view
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


        }



