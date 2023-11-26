package com.example.blackjack

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlin.math.roundToInt

class GameFragment : Fragment() {
    fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        cardBlankDealer.visibility = View.INVISIBLE
        cardNextCardDealer.visibility = View.INVISIBLE
        cardDarkDealer.visibility = View.INVISIBLE
        card1BlankPlayer.visibility = View.INVISIBLE
        card2BlankPlayer.visibility = View.INVISIBLE
        cardNextCard.visibility = View.INVISIBLE
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
        var totalBetText = view.findViewById<TextView>(R.id.totalBetTextView)
        var pointsPlayerText = view.findViewById<TextView>(R.id.pointsPlayerTextView)
        var pointsDealerText = view.findViewById<TextView>(R.id.pointsDealerTextView)
        val cardImageViews = listOf(card1BlankPlayer, card2BlankPlayer, cardNextCard)
        val dealerCardImageViews = listOf(cardBlankDealer, cardDarkDealer, cardNextCardDealer)
        val cardDisplay = CardDisplay()
        val playerHand = Hand()
        val dealerHand = Hand()

        fun updateDealerCards(dealerHand: Hand) {
            dealerHand.cards.forEachIndexed { index, card ->
                val imageView = dealerCardImageViews[index]
                val image = cardDisplay.getCardImage(card)
                imageView.setImageResource(image)
            }
        }
        fun compareHands(playerPoints: Int, dealerPoints: Int): String {
            return when {
                playerPoints > 21 -> "Dealer"
                dealerPoints > 21 -> "Player"
                playerPoints > dealerPoints -> "Player"
                dealerPoints > playerPoints -> "Dealer"
                else -> "Draw"
            }
        }
        fun dealToDealer(dealerHand: Hand, deck: Deck, dealerPointsText: TextView) {
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
                val newCard = deck.cards.random()
                deck.cards.remove(newCard)
                dealerHand.cards.add(newCard)
                val newCardImageView = ImageView(context)
                val image = cardDisplay.getCardImage(newCard)
                newCardImageView.setImageResource(image)
                newCardImageView.id = View.generateViewId()
                val layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                newCardImageView.layoutParams = layoutParams

                dealerCardImageViews.add(newCardImageView)
                constraintlayout.addView(newCardImageView)
                val constraintSet = ConstraintSet()
                constraintSet.clone(constraintlayout)
                val dpValue = 15
                val pixelValue = (dpValue * context.resources.displayMetrics.density).toInt()
                constraintSet.connect(newCardImageView.id, ConstraintSet.START, cardDarkDealer.id, ConstraintSet.END, pixelValue )
                constraintSet.applyTo(constraintlayout)
                cardNextCardDealer.visibility = View.VISIBLE
                dealerHand.cards.forEachIndexed { i, card ->
                    updateDealerCards(dealerHand)
                    Handler().postDelayed({
                        dealToDealer(dealerHand, deck, dealerPointsText)
                    }, 1000)
                }
            }else {
                val playerPoints = playerHand.calculatePoints(playerHand.cards)
                val winner = compareHands(playerPoints.first, pointsWithAceAsOne)
                if (winner == "player") {
                    bannerWin.visibility = View.VISIBLE
                } else if (winner == "dealer") {
                    bannerLose.visibility = View.VISIBLE
                }
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

        var lastCardId: Int = card2BlankPlayer.id
        val deck = Deck()
//        val playerHand = Hand()
//        val dealerHand = Hand()
        fun dpToPx(dp: Int): Int {
            val density = resources.displayMetrics.density
            return (dp * density).roundToInt()
        }
        deal.setOnClickListener {
            deck.dealHand(playerHand, dealerHand)
            val (pointsWithAceAsOne, pointsWithAceAsEleven) = playerHand.calculatePoints(playerHand.cards)


            playerHand.cards.forEachIndexed { i, card ->
                val image = cardDisplay.getCardImage(card)
                cardImageViews[i].setImageResource(image)
            }
            dealerHand.cards.forEachIndexed { i, card ->
                val image = cardDisplay.getCardImage(card)
                dealerCardImageViews[i].setImageResource(image)
            }
            deal.visibility = View.INVISIBLE
            card1BlankPlayer.visibility = View.VISIBLE
            card2BlankPlayer.visibility = View.VISIBLE
            cardBlankDealer.visibility = View.VISIBLE
            cardDarkDealer.visibility = View.VISIBLE
            hit.visibility = View.VISIBLE
            stand.visibility = View.VISIBLE
            doubleDown.visibility = View.VISIBLE

            if (pointsWithAceAsOne == pointsWithAceAsEleven || pointsWithAceAsEleven > 21) {
                pointsPlayerText.text = pointsWithAceAsOne.toString()
            } else {
                pointsPlayerText.text = "$pointsWithAceAsOne/$pointsWithAceAsEleven"
            }
        }

        hit.setOnClickListener {
            val newCard = deck.cards.random()
            deck.cards.remove(newCard)
            playerHand.cards.add(newCard)
//            deck.drawCard(playerHand.cards)
            cardNextCard.visibility = View.VISIBLE
            val image = cardDisplay.getCardImage(newCard)
            cardNextCard.setImageResource(image)
            cardNextCard.visibility = View.VISIBLE
            cardNextCard.id = View.generateViewId()
            cardNextCard.scaleX = 0.45f
            cardNextCard.scaleY = 0.45f
            val constraintSet = ConstraintSet() // kod till blankCard2
            constraintSet.clone(constraintlayout)
            constraintSet.clear(card2BlankPlayer.id, ConstraintSet.END)
            constraintSet.connect(card2BlankPlayer.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dpToPx(40))
            constraintSet.applyTo(constraintlayout)
            constraintSet.clear(cardNextCard.id, ConstraintSet.END)
            constraintSet.clone(constraintlayout)
            lastCardId = card2BlankPlayer.id
            constraintSet.connect(cardNextCard.id, ConstraintSet.START, lastCardId, ConstraintSet.END, dpToPx(30))
            constraintSet.applyTo(constraintlayout)
            lastCardId = cardNextCard.id


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
                // spara värdet (total bet)
                // lägg till marker motsvarande värdet
                // kör allt i deal
            }

            // Game end
            // Dela bort kort till övre vänsta hörnet i hög
            // Vid förlust dra marker till dealern, vinst mot spelare
            // revel repeatBet button



        return view
        }

    }

