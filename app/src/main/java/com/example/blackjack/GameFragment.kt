package com.example.blackjack

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.ChangeBounds
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class GameFragment : Fragment() {
    val dealerHand = Hand()

    data class BetInfo( val marker: ImageView,
                        val madeBetMarker: ImageView,
                        var count: Int,
                        val originalX: Float,
                        val originalY: Float
//    )
//                        var originalX: Float = marker.x,
//                        var originalY: Float = marker.y
    )
    data class ImageViewPosition(val x: Float, val y: Float)


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
        val markersWinnerDealer = view.findViewById<ImageView>(R.id.markerWinnerDealerImageView)
        val markersWinnerPlayer = view.findViewById<ImageView>(R.id.markerWinnerPlayerImageView)
        val cardBlankDealer = view.findViewById<ImageView>(R.id.cardBlankDealerImageView)
        val cardNextCardDealer = view.findViewById<ImageView>(R.id.cardNextCardDealer)
        val cardDarkDealer = view.findViewById<ImageView>(R.id.cardDarkDealerImageView)
        val cardDealingOutCardsToDealer =
            view.findViewById<ImageView>(R.id.cardDealingOutCardsToDealerImageView)
        val cardDealingOutCardsToPlayer =
            view.findViewById<ImageView>(R.id.cardDealingOutCardsToPlayerImageView)
        val cardDealingOutCardsToPlayerTense =
            view.findViewById<ImageView>(R.id.cardDealingOutCardsToPlayerTenseImageView)
        val card1BlankPlayer = view.findViewById<ImageView>(R.id.card1BlankPlayerImageView)
        val cardNextCard = view.findViewById<ImageView>(R.id.cardNextCardImageView)
        val cardNextCard2 = view.findViewById<ImageView>(R.id.cardNextCard2ImageView)
        val cardNextCard3 = view.findViewById<ImageView>(R.id.cardNextCard3ImageView)
        val cardNextCard4 = view.findViewById<ImageView>(R.id.cardNextCard4ImageView)

        val card2BlankPlayer = view.findViewById<ImageView>(R.id.card2BlankPlayerImageView)
        val madeBetMarker5 = view.findViewById<ImageView>(R.id.madeBetMarker5ImageView)
        val madeBetMarker10 = view.findViewById<ImageView>(R.id.madeBetMarker10ImageView)
        val madeBetMarker25 = view.findViewById<ImageView>(R.id.madeBetMarker25ImageView)
        val madeBetMarker50 = view.findViewById<ImageView>(R.id.madeBetMarker50ImageView)
        val madeBetMarker100 = view.findViewById<ImageView>(R.id.madeBetMarker100ImageView)


//




        val deal = view.findViewById<ImageButton>(R.id.dealImageButton)
        val stand = view.findViewById<ImageButton>(R.id.standImageButton)
        val hit = view.findViewById<ImageButton>(R.id.hitImageButton)
        val split = view.findViewById<ImageButton>(R.id.splitImageButton)
        val doubleDown = view.findViewById<ImageButton>(R.id.doubleDownImageButton)
        val insurance = view.findViewById<ImageButton>(R.id.insuranceImageButton)
        val yes = view.findViewById<ImageButton>(R.id.yesImageButton)
        val no = view.findViewById<ImageButton>(R.id.noImageButton)
        val repeatBet = view.findViewById<ImageButton>(R.id.repeatBetImageButton)
        val bannerWin = view.findViewById<ImageView>(R.id.bannerWinImageView)
        val bannerLose = view.findViewById<ImageView>(R.id.bannerLoseImageView)
        val bannerBust = view.findViewById<ImageView>(R.id.bannerBustImageView)
        val bannerBlackjack = view.findViewById<ImageView>(R.id.bannerBlackjackImageView)
        val bannerSplit = view.findViewById<ImageView>(R.id.bannerSplitImageView)
        val bannerBlackjackPlayerSplit =
            view.findViewById<ImageView>(R.id.bannerBlackjackSplitPlayerImageView)
        val bannerBlackjackDealerSplit =
            view.findViewById<ImageView>(R.id.bannerBlackjackSplitDealerImageView)
        val constraintlayout = view.findViewById<ConstraintLayout>(R.id.insuranceImageView)
        val cardPile = view.findViewById<ImageView>(R.id.cardPileImageView)
        var totalBetText = view.findViewById<TextView>(R.id.totalBetTextView)
        var pointsPlayerText = view.findViewById<TextView>(R.id.pointsPlayerTextView)
        var pointsDealerText = view.findViewById<TextView>(R.id.pointsDealerTextView)
        var playerMoneyText = view.findViewById<TextView>(R.id.playerMoneyTextView)
        val homeButton = view.findViewById<ImageView>(R.id.homeImageView)
        val cashoutButton = view.findViewById<ImageView>(R.id.cashoutImageView)



        cardBlankDealer.visibility = View.INVISIBLE
        cardNextCardDealer.visibility = View.INVISIBLE
        cardDarkDealer.visibility = View.INVISIBLE
        cardDealingOutCardsToDealer.visibility = View.INVISIBLE
        cardDealingOutCardsToPlayer.visibility = View.INVISIBLE
        cardDealingOutCardsToPlayerTense.visibility = View.INVISIBLE
        card1BlankPlayer.visibility = View.INVISIBLE
        card2BlankPlayer.visibility = View.INVISIBLE
        cardNextCard.visibility = View.INVISIBLE
        cardNextCard2.visibility = View.INVISIBLE
        cardNextCard3.visibility = View.INVISIBLE
        cardNextCard4.visibility = View.INVISIBLE

        cardPile.visibility = View.INVISIBLE
        totalBetText.visibility = View.INVISIBLE
        madeBetMarker5.visibility = View.VISIBLE
        madeBetMarker10.visibility = View.INVISIBLE
        madeBetMarker25.visibility = View.INVISIBLE
        madeBetMarker50.visibility = View.VISIBLE
        madeBetMarker100.visibility = View.INVISIBLE
        markersWinnerDealer.visibility = View.INVISIBLE
        markersWinnerPlayer.visibility = View.INVISIBLE
        hit.visibility = View.INVISIBLE
        stand.visibility = View.INVISIBLE
        deal.visibility = View.INVISIBLE
        split.visibility = View.INVISIBLE
        doubleDown.visibility = View.INVISIBLE
        repeatBet.visibility = View.INVISIBLE
        insurance.visibility = View.INVISIBLE
        yes.visibility = View.INVISIBLE
        no.visibility = View.INVISIBLE
        bannerWin.visibility = View.INVISIBLE
        bannerLose.visibility = View.INVISIBLE
        bannerBust.visibility = View.INVISIBLE
        bannerBlackjack.visibility = View.INVISIBLE
        bannerSplit.visibility = View.INVISIBLE
        bannerBlackjackPlayerSplit.visibility = View.INVISIBLE
        bannerBlackjackDealerSplit.visibility = View.INVISIBLE
        pointsPlayerText.visibility = View.INVISIBLE
        pointsDealerText.visibility = View.INVISIBLE
        homeButton.visibility = View.INVISIBLE
        cashoutButton.visibility = View.INVISIBLE

        val TAG = "GameFragment"
        var totalBet = 0
        var highscore = 0
        val playerCardImageViews: MutableList<ImageView> =
            mutableListOf(
                card1BlankPlayer,
                card2BlankPlayer,
                cardNextCard,
                cardNextCard2,
                cardNextCard3,
                cardNextCard4
            )
        val dealerCardImageViews: MutableList<ImageView> =
            mutableListOf(cardBlankDealer, cardDarkDealer, cardNextCardDealer)
        val cardDisplay = CardDisplay()
        val playerHand = Hand()
        val dealerHand = Hand()
        val util = Util()
        val delay = DelaysUtil()
        val hand = Hand()
        val deck = Deck(
            requireContext(),
            card1BlankPlayer,
            cardDealingOutCardsToPlayer,
            cardBlankDealer,
            cardDealingOutCardsToDealer,
            card2BlankPlayer,
            cardDarkDealer
        )
        val animations = Animations()
        val bannerManager = BannerManager(animations)
        var lastBetMarkersValue = mutableListOf<Int>()

        var lastBet = 0
        var winner = ""
         var wins: Int = 0
         var losses: Int = 0
         var blackjacks: Int = 0
         var gamesPlayed: Int = 0
        var draws: Int = 0

        animations.fadeInImageView(homeButton)

        val handler = Handler(Looper.getMainLooper())
        var playerMoney = 500
        playerMoneyText.text = playerMoney.toString()

        //        val betInfo5 = BetInfo(marker5, madeBetMarker5, 0, madeBetMarker5.x, madeBetMarker5.y)
//        val betInfo10 = BetInfo(marker10, madeBetMarker10, 0, madeBetMarker10.x, madeBetMarker10.y)
//        val betInfo25 = BetInfo(marker25, madeBetMarker25, 0, madeBetMarker25.x, madeBetMarker25.y)
//        val betInfo50 = BetInfo(marker50, madeBetMarker50, 0, madeBetMarker50.x, madeBetMarker50.y)
//        val betInfo100 = BetInfo(marker100, madeBetMarker100, 0, madeBetMarker100.x, madeBetMarker100.y)
//        marker5.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                val x = marker5.x
//                val y = marker5.y
//
//                // Logga värdena
//                Log.d("MyApp", "marker5 x: $x, marker5 y: $y")
//
//                marker5.viewTreeObserver.removeOnGlobalLayoutListener(this)
//            }
//        })
//        val betInfo5 = BetInfo(marker5, madeBetMarker5, 0, marker5.x, marker5.y)
//        val betInfo10 = BetInfo(marker10, madeBetMarker10,  0, marker10.x, marker10.y)
//        val betInfo25 = BetInfo(marker25, madeBetMarker25,  0, marker25.x, marker25.y)
//        val betInfo50 = BetInfo(marker50, madeBetMarker50,  0, marker50.x, marker50.y)
//        val betInfo100 = BetInfo(marker100, madeBetMarker100,  0, marker100.x, marker100.y)

        val selectedMarkers = mutableMapOf<ImageView, BetInfo>()

//        selectedMarkers[marker5] = betInfo5
//        selectedMarkers[marker10] = betInfo10
//        selectedMarkers[marker25] = betInfo25
//        selectedMarkers[marker50] = betInfo50
//        selectedMarkers[marker100] = betInfo100

        val originalPositions = mutableMapOf<ImageView, Pair<Float, Float>>()

        view.post {
            originalPositions.clear() // Clear previous entries, if any
            selectedMarkers.forEach { (marker, _) ->
                originalPositions[marker] = Pair(marker.x, marker.y)
            }
        }



        fun animateCardToPile(cardImageView: ImageView, cardPile: ImageView, index: Int) {
            val cardPileX = cardPile.x
            val cardPileY = cardPile.y
            animations.moveCard(
                cardImageView,
                cardImageView.x,
                cardImageView.y,
                cardPileX,
                cardPileY,
                500L,
                index * 200L
            )
            cardImageView.setImageResource(R.drawable.card_dark)
        }

        fun animationCardsToPile(cardImageViews: List<ImageView>, cardPile: ImageView) {
            cardImageViews.forEachIndexed { index, cardImageView ->
                animateCardToPile(cardImageView, cardPile, index)
            }
            dealerCardImageViews.forEachIndexed { index, cardImageView ->
                animateCardToPile(cardImageView, cardPile, index)
            }
        }


        fun moveCardsToPile(banner: View) {
            handler.postDelayed({
                animationCardsToPile(playerCardImageViews, cardPile)
            }, delay.cardsToPile)

            handler.postDelayed({
                cardPile.visibility = View.VISIBLE
            }, delay.pile)

            handler.postDelayed({
                banner.visibility = View.INVISIBLE   // not working??
            }, delay.banner)

            handler.postDelayed({
                animations.buttonInRightSide(repeatBet, requireContext(), 1000L)
            }, delay.repeatBetButton)

        }


        fun emptyBoard() {
            animations.fadeOutTextView(pointsPlayerText)
            animations.fadeOutTextView(pointsDealerText)
            animations.fadeOutTextView(totalBetText)
        }

        fun gameEnd() {
            emptyBoard()
            gamesPlayed++
            Handler().postDelayed({
            animations.fadeInImageView(cashoutButton)
            }, delay.cashOut)
        }


        fun updateDealerCards(dealerHand: Hand) {
            Log.d(TAG, "Entering someMethod()")
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
            Log.d(TAG, "Entering someMethod()")
            return when {
                playerPoints > 21 -> "Dealer"
                dealerPoints > 21 -> "Player"
                playerPoints > dealerPoints -> "Player"
                dealerPoints > playerPoints -> "Dealer"
                else -> "Draw"
            }
        }

        fun flipCard(imageView: ImageView, hand: Hand) {
            Log.d(TAG, "Entering someMethod()")
            animations.flipCard(imageView)
            Handler().postDelayed({
                updateDealerCards(hand)
            }, delay.revealCardWhenFlip)
            hand.revealHiddenCard()
        }

        fun flipCardTense(imageView: ImageView, hand: Hand) {
            Log.d(TAG, "Entering someMethod()")
            Handler().postDelayed({
                animations.shakeCardSubtle(cardDarkDealer)
            }, delay.shakeCard)
            Handler().postDelayed({
                flipCard(cardDarkDealer, dealerHand)
            }, delay.revealCard)
            hand.revealHiddenCard()
        }

        fun drawCardDealer() {
            Log.d(TAG, "Entering someMethod()")
            deck.drawCard(dealerHand.cards)
            val newCard = dealerHand.cards.last()
            val newCardImageView = ImageView(context)
            newCardImageView.setImageResource(cardDisplay.getCardImage(newCard))

            val offsetDp = 15f
            val density = resources.displayMetrics.density
            val offsetPx = (offsetDp * density).toInt()
            val cardDarkDealerEndX = cardDarkDealer.x + offsetPx
            val cardBlankDealerEndX = cardBlankDealer.x + offsetPx

            animations.moveCard(
                cardDarkDealer,
                cardDarkDealer.x,
                cardDarkDealer.y,
                cardBlankDealerEndX,
                cardBlankDealer.y,
                500L,
                0L
            )

            deck.performDealingAnimation(
                cardNextCardDealer,
                cardDealingOutCardsToDealer.x,
                cardDealingOutCardsToDealer.y,
                cardDarkDealerEndX,
                cardDarkDealer.y,
                0f,
                180f
            )

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
        }

        fun animateMarkersToWinner(winnerIsDealer: Boolean) {
            Log.d(TAG, "Entering someMethod()")
            val destinationX = if (winnerIsDealer) markersWinnerDealer.x else markersWinnerPlayer.x
            val destinationY = if (winnerIsDealer) markersWinnerDealer.y else markersWinnerPlayer.y
            for ((marker, betInfo) in selectedMarkers) {
                animations.moveCard(
                    betInfo.madeBetMarker,
                    betInfo.madeBetMarker.x,
                    betInfo.madeBetMarker.y,
                    destinationX,
                    destinationY,
                    500L,
                    0L
                )
            }
        }



        fun cashOut() {
            Log.d(TAG, "Entering someMethod()")
            val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("playerMoney", playerMoney)
            editor.putInt("wins", wins)
            editor.putInt("losses", losses)
            editor.putInt("draws", draws)
            editor.putInt("blackjacks", blackjacks)
            editor.putInt("gamesPlayed", gamesPlayed)

            editor.apply()
            val intent = Intent(requireContext(), HighscoreActivity::class.java)
            startActivity(intent)
        }

        cashoutButton.setOnClickListener {
        cashOut()
        }
        homeButton.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        fun determineWinner(playerHand: Hand, dealerHand: Hand) {
            Log.d(TAG, "Entering someMethod()")
            Log.d("BlackJack", "Entering determineWinner")
            val playerPoints = playerHand.calculatePoints(playerHand.cards)
            val winner =
                compareHands(playerPoints.first, dealerHand.calculatePoints(dealerHand.cards).first)
            when (winner) {
                "Player" -> {
                    animations.fadeInAndMoveUpImageView(bannerWin)
                    bannerManager.fadeOutBanner(bannerWin)
                    animateMarkersToWinner(winnerIsDealer = false)
                    moveCardsToPile(bannerWin)
                    playerMoney += 2 * totalBet
                    playerMoneyText.text = playerMoney.toString()
                    totalBet = lastBetMarkersValue.sum()
                    totalBetText.text = totalBet.toString()
                    wins++

                }

                "Dealer" -> {
                    animations.fadeInAndMoveUpImageView(bannerLose)
                    bannerManager.fadeOutBanner(bannerLose)
                    animateMarkersToWinner(winnerIsDealer = true)
                    moveCardsToPile(bannerLose)
                    totalBet = lastBetMarkersValue.sum()
                    totalBetText.text = totalBet.toString()
                    losses++
                }

                "Draw" -> {
                    animations.fadeInAndMoveUpImageView(bannerSplit)
                    bannerManager.fadeOutBanner(bannerSplit)
                    animateMarkersToWinner(winnerIsDealer = false)
                    moveCardsToPile(bannerSplit)
                    playerMoney += totalBet
                    playerMoneyText.text = playerMoney.toString()
                    totalBet = lastBetMarkersValue.sum()
                    totalBetText.text = totalBet.toString()
                    draws++
                }
            }

            gameEnd()
        }


        fun Hand.updatePointsText(textView: TextView, hasPlayerStood: Boolean) {
            Log.d(TAG, "Entering someMethod()")
            val pointsPair = calculatePoints(cards)
            val pointsWithAceAsOne = pointsPair.first
            val pointsWithAceAsEleven = pointsPair.second

            val pointsText = when {
                pointsWithAceAsOne == pointsWithAceAsEleven || pointsWithAceAsEleven > 21 -> {
                    pointsWithAceAsOne.toString()
                }

                hasPlayerStood && pointsWithAceAsEleven <= 21 -> {
                    "$pointsWithAceAsEleven"
                }

                else -> {
                    "$pointsWithAceAsOne/$pointsWithAceAsEleven"
                }
            }

            util.setTextViewBackground(textView, pointsText, R.color.black)

        }

        fun checKDealerPoints(dealerHand: Hand, dealerPointsText: TextView) {
            Log.d(TAG, "Entering someMethod()")
            val (pointsWithAceAsOne, pointsWithAceAsEleven) = dealerHand.calculatePoints(dealerHand.cards)
            when {
                pointsWithAceAsEleven > 21 && pointsWithAceAsOne > 21 -> {
                    dealerPointsText.text = pointsWithAceAsOne.toString()
                }

                pointsWithAceAsEleven > 21 -> {
                    dealerPointsText.text = pointsWithAceAsOne.toString()
                }

                pointsWithAceAsEleven in 17..21 -> {
                    dealerPointsText.text = pointsWithAceAsEleven.toString()
                }

                pointsWithAceAsEleven < 17 && pointsWithAceAsOne < 17 -> {
                    do {
                        drawCardDealer()
                        dealerHand.updatePointsText(pointsDealerText, false)
                    } while (dealerHand.calculatePoints(dealerHand.cards).second < 17)
                }
            }
            Handler().postDelayed({
                determineWinner(playerHand, dealerHand)
            }, delay.determineWinner)
        }

        fun setupMarker(
            marker: ImageView,
            madeBetMarker: ImageView,
            value: Int,
            totalBetText: TextView,
            dealButton: ImageButton,
            originalX: Float,
            originalY: Float
        ) {
//            val originalX = marker.x
//            val originalY = marker.y

            val betInfo = selectedMarkers.getOrDefault(marker, BetInfo(marker, madeBetMarker, 0, originalX, originalY)).apply {
        count++
    }
                 selectedMarkers[marker] = betInfo

                marker.setOnClickListener {
//
                lastBetMarkersValue.add(value)
                madeBetMarker.visibility = View.VISIBLE
                animations.fadeInTextView(totalBetText)
                lastBet = lastBetMarkersValue.sum()

                animations.moveCard(
                    madeBetMarker,
                    marker.x,
                    marker.y,
                    betInfo.originalX,
                    betInfo.originalY,
                    500L,
                    0L
                )
                totalBet = lastBetMarkersValue.sum()
                totalBetText.text = totalBet.toString()
                animations.buttonInRightSide(deal, requireContext(), 1000L)
                animations.buttonOutRightSide(repeatBet, requireContext(), 1000L)
                util.setTextViewBackground(totalBetText, totalBet.toString(), R.color.black)
            }
        }

        fun getOriginalPositionAndSetupMarker(marker: ImageView, madeBetMarker: ImageView, value: Int, totalBetText: TextView, dealButton: ImageButton) {
            marker.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val originalX = marker.x
                    val originalY = marker.y

                    val betInfo = BetInfo(marker, madeBetMarker, 0, originalX, originalY)
                    selectedMarkers[marker] = betInfo

                    setupMarker(marker, madeBetMarker, value, totalBetText, dealButton, originalX, originalY)

                    marker.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

        getOriginalPositionAndSetupMarker(marker5, madeBetMarker5, 5, totalBetText, deal)
        getOriginalPositionAndSetupMarker(marker10, madeBetMarker10, 10, totalBetText, deal)
        getOriginalPositionAndSetupMarker(marker25, madeBetMarker25, 25, totalBetText, deal)
        getOriginalPositionAndSetupMarker(marker50, madeBetMarker50, 50, totalBetText, deal)
        getOriginalPositionAndSetupMarker(marker100, madeBetMarker100, 100, totalBetText, deal)


//        setupMarker(marker5, madeBetMarker5, 5, totalBetText, deal)
//        setupMarker(marker10, madeBetMarker10, 10, totalBetText, deal)
//        setupMarker(marker25, madeBetMarker25, 25, totalBetText, deal)
//        setupMarker(marker50, madeBetMarker50, 50, totalBetText, deal)
//        setupMarker(marker100, madeBetMarker100, 100, totalBetText, deal)



        fun checkForBlackjack(playerHand: Hand, dealerHand: Hand) {
            if (playerHand.isBlackjack()) {
                Handler().postDelayed({
                    animations.fadeInImageView(bannerBlackjackPlayerSplit)
                    flipCardTense(cardDarkDealer, dealerHand)
                    Handler().postDelayed({
                        Handler().postDelayed({
                            if (dealerHand.isBlackjack()) {
                                animations.fadeInImageView(bannerBlackjackDealerSplit)
                                Handler().postDelayed({
                                    animations.fadeInAndMoveUpImageView(bannerSplit)
                                    bannerManager.fadeOutBanner(bannerSplit)
                                    moveCardsToPile(bannerSplit)
                                    draws++
                                }, delay.bannerSplit)
                                Handler().postDelayed({
                                    animations.removeImage(bannerBlackjackDealerSplit)
                                    animations.removeImage(bannerBlackjackPlayerSplit)
                                }, delay.removeBannersBlackjack)
                            } else {
                                animations.fadeInAndMoveUpImageView(bannerWin)
                                animations.removeImage(bannerBlackjackPlayerSplit)
                                bannerManager.fadeOutBanner(bannerWin)
                                moveCardsToPile(bannerWin)
                                wins++
                            }
                        }, delay.blackjackDealer)
                    }, delay.checkDealersCards)
                    gameEnd()
                }, delay.blackjackPlayerBanner)
                Handler().postDelayed({
                    moveCardsToPile(bannerBlackjack)
                }, delay.moveCardsToPile)
                gameEnd()
            } else if (!playerHand.isBlackjack()) {
                Handler().postDelayed({
                    animations.buttonInRightSide(hit, requireContext(), 1000L)
                    animations.buttonInRightSide(stand, requireContext(), 1000L)
                    playerHand.updatePointsText(pointsPlayerText, false)
                    animations.fadeInTextView(pointsPlayerText)
                    dealerHand.updatePointsText(pointsDealerText, false)
                    animations.fadeInTextView(pointsDealerText)
                }, delay.buttonsContinue)
            }
        }


        fun updateCardImages(
            hand: Hand,
            cardImageViews: List<ImageView>,
            cardDisplay: CardDisplay
        ) {
            hand.cards.forEachIndexed { i, card ->
                if (i < cardImageViews.size) {
                    val image = cardDisplay.getCardImage(card)
                    cardImageViews[i].setImageResource(image)
                }
            }
        }

        fun dealerGotAce(): Boolean {
            return dealerHand.cards.isNotEmpty() && dealerHand.cards[0].rank == Card.Rank.ACE
        }

        fun doubleDown() {
            animations.buttonInLeftSide(doubleDown, requireContext(), 1000L)
        }

        doubleDown.setOnClickListener {
            animations.buttonOutRightSide(hit, requireContext(), 1000L)
            animations.buttonOutRightSide(stand, requireContext(), 1000L)
            animations.buttonOutLeftSide(doubleDown, requireContext(), 1000L)
            animations.fadeInImageView(cardDealingOutCardsToPlayerTense)
            animations.shakeCardTense(cardDealingOutCardsToPlayerTense)
            val doubleDownAmount = totalBet
            playerMoney -= doubleDownAmount
            playerMoneyText.text = playerMoney.toString()
            totalBet = lastBetMarkersValue.sum() * 2
            totalBetText.text = totalBet.toString()
            for ((marker, betInfo) in selectedMarkers) {  // gör liknande för insurance
                animations.moveCard(
                    betInfo.madeBetMarker,
                    marker.x,
                    marker.y,
                    betInfo.madeBetMarker.x,
                    betInfo.madeBetMarker.y,
                    500L,
                    0L
                )
            }

            Handler().postDelayed({
                deck.drawCard(playerHand.cards)
                val newCard = playerHand.cards.last()
                val image = cardDisplay.getCardImage(newCard)
                val newConstraintLayout = ConstraintLayout(view.context)
                newConstraintLayout.layoutParams = constraintlayout.layoutParams
                cardNextCard.visibility = View.VISIBLE
                cardNextCard.setImageResource(image)
                cardNextCard.id = View.generateViewId()
                val offsetDp = 80f
                val density = resources.displayMetrics.density
                val offsetPx = (offsetDp * density).toInt()
                val card1BlankPlayerEndX = card1BlankPlayer.x + offsetPx
                val card2BlankPlayerEndX = card2BlankPlayer.x + offsetPx

                deck.performDealingAnimation(
                    cardNextCard,
                    cardDealingOutCardsToPlayerTense.x,
                    cardDealingOutCardsToPlayerTense.y,
                    card2BlankPlayerEndX,
                    card2BlankPlayer.y,
                    140f,
                    360f
                )
                val card1Animator = ObjectAnimator.ofFloat(
                    card2BlankPlayer,
                    View.X,
                    card2BlankPlayer.x,
                    card1BlankPlayerEndX
                )
                card1Animator.interpolator = AccelerateDecelerateInterpolator()
                card1Animator.duration = 500L
                val card2Animator = ObjectAnimator.ofFloat(
                    cardNextCard,
                    View.X,
                    cardDealingOutCardsToPlayer.x,
                    card2BlankPlayerEndX
                )
                card2Animator.interpolator = AccelerateDecelerateInterpolator()
                card2Animator.duration = 500L
                val animatorSet = AnimatorSet()
                animatorSet.playTogether(card1Animator, card2Animator)

                animatorSet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        val cardNextCardEndX = card2BlankPlayer.x + offsetPx
                        val cardNextCardAnimator = ObjectAnimator.ofFloat(
                            cardNextCard,
                            View.X,
                            card2BlankPlayerEndX,
                            cardNextCardEndX
                        )
                        cardNextCardAnimator.interpolator = AccelerateDecelerateInterpolator()
                        cardNextCardAnimator.duration = 500L
                        cardNextCardAnimator.start()
                    }
                })
                animatorSet.start()
                animations.shakeTextSubtle(pointsPlayerText)
                playerHand.updatePointsText(pointsPlayerText, false)
                val (pointsWithAceAsOne, pointsWithAceAsEleven) = playerHand.calculatePoints(
                    playerHand.cards
                )
                if (pointsWithAceAsOne > 21 && pointsWithAceAsEleven > 21) {
                    animations.fadeInAndMoveUpImageView(bannerBust)
                    bannerManager.fadeOutBanner(bannerBust)
                    moveCardsToPile(bannerBust)
                    losses++
                }
                cardDealingOutCardsToPlayerTense.visibility = View.INVISIBLE
                Handler().postDelayed({
                    flipCard(cardDarkDealer, dealerHand)
                    checKDealerPoints(dealerHand, pointsDealerText)
                }, delay.checkDealer)
            }, delay.drawCard)
        }

        fun insurance() {
            animations.buttonOutLeftSide(yes, requireContext(), 1000L)
            animations.buttonOutRightSide(no, requireContext(), 1000L)
            animations.removeImage(insurance)
            flipCardTense(cardDarkDealer, dealerHand)
            if (dealerHand.isBlackjack()) {
                flipCardTense(cardDarkDealer, dealerHand)
                Handler().postDelayed({
                    dealerHand.revealHiddenCard()
                    animations.fadeInImageView(bannerBlackjackDealerSplit)
                    animations.fadeInAndMoveUpImageView(bannerLose)
                    bannerManager.fadeOutBanner(bannerLose)
                    moveCardsToPile(bannerLose)
                    losses++
                }, delay.revealBlackjack)
                Handler().postDelayed({
                    animations.removeImage(bannerBlackjackDealerSplit)
                }, delay.removeBlackjackBanner)
            } else if (!dealerHand.isBlackjack()) {
                Handler().postDelayed({
                    checKDealerPoints(dealerHand, pointsDealerText)
                }, delay.drawNewCard)
            }
        }

        fun startGame() {
            playerHand.clear()
            dealerHand.clear()
            Handler().postDelayed({
                doubleDown()
            }, delay.doubleDownButton)
            deck.dealHand(playerHand, dealerHand)
            updateDealerCards(dealerHand)
            updateCardImages(playerHand, playerCardImageViews, cardDisplay)
            updateCardImages(dealerHand, dealerCardImageViews, cardDisplay)
            checkForBlackjack(playerHand, dealerHand)
        }

        repeatBet.setOnClickListener {
            for (marker in lastBetMarkersValue) {
                // kod?
            }
            totalBet = lastBet
            totalBetText.text = totalBet.toString()
            startGame()
        }


        deal.setOnClickListener {
            animations.buttonOutRightSide(deal, requireContext(), 1000L)
            playerMoney -= lastBet
            playerMoneyText.text = playerMoney.toString()
            startGame()
        }
        var hitCounter = 0

        fun dealNewCard(cardIndex: Int) {
            animations.shakeButton(hit)
            deck.drawCard(playerHand.cards)
            val newCard = playerHand.cards.last()
            val image = cardDisplay.getCardImage(newCard)

            val currentCardNextCard = when (cardIndex) {
                1 -> cardNextCard2
                2 -> cardNextCard3
                3 -> cardNextCard4
                else -> throw IllegalArgumentException("Invalid card index: $cardIndex")
            }
            currentCardNextCard.visibility = View.VISIBLE
            currentCardNextCard.setImageResource(image)
            currentCardNextCard.id = View.generateViewId()

            if (cardIndex > 1) {
                val prevCardNextCard = when (cardIndex - 1) {
                    1 -> cardNextCard
                    2 -> cardNextCard2
                    3 -> cardNextCard3
                    else -> throw IllegalArgumentException("Invalid card index: ${cardIndex - 1}")
                }

                val offsetDp = 35f
                val density = resources.displayMetrics.density
                val offsetPx = (offsetDp * density).toInt()
                val cardNextCardEndX = prevCardNextCard.x + offsetPx

                deck.performDealingAnimation(
                    currentCardNextCard,
                    cardDealingOutCardsToPlayer.x,
                    cardDealingOutCardsToPlayer.y,
                    cardNextCardEndX,
                    prevCardNextCard.y,
                    0f,
                    180f
                )
            }
            hitCounter++
        }
        fun animateCards(card2BlankPlayer: ImageView, cardNextCard: ImageView, offsetPx: Int) {
            val card1BlankPlayerEndX = card1BlankPlayer.x + offsetPx
            val card2BlankPlayerEndX = card2BlankPlayer.x + offsetPx

            val card1Animator = ObjectAnimator.ofFloat(
                card2BlankPlayer,
                View.X,
                card2BlankPlayer.x,
                card1BlankPlayerEndX
            )
            card1Animator.interpolator = AccelerateDecelerateInterpolator()
            card1Animator.duration = 500L

            val card2Animator = ObjectAnimator.ofFloat(
                cardNextCard,
                View.X,
                cardDealingOutCardsToPlayer.x,
                card2BlankPlayerEndX
            )
            card2Animator.interpolator = AccelerateDecelerateInterpolator()
            card2Animator.duration = 500L

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(card1Animator, card2Animator)

            animatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    val cardNextCardEndX = card2BlankPlayer.x + offsetPx
                    val cardNextCardAnimator = ObjectAnimator.ofFloat(
                        cardNextCard,
                        View.X,
                        card2BlankPlayerEndX,
                        cardNextCardEndX
                    )
                    cardNextCardAnimator.interpolator = AccelerateDecelerateInterpolator()
                    cardNextCardAnimator.duration = 500L
                    cardNextCardAnimator.start()
                }
            })
            animatorSet.start()
        }

        fun drawAndAnimateCard(previousCard: ImageView) {
            deck.drawCard(playerHand.cards)
            val newCard = playerHand.cards.last()
            val image = cardDisplay.getCardImage(newCard)

            val cardNextCard = ImageView(context).apply {   // card.id?
                visibility = View.VISIBLE
                setImageResource(image)
                id = View.generateViewId()
            }

            val offsetDp = 35f
            val widthDp = 70f
            val heightDp = 100f
            val density = resources.displayMetrics.density
            val offsetPx = (offsetDp * density).toInt()
            val widthPx = (widthDp * density).toInt()
            val heightPx = (heightDp * density).toInt()
            val cardEndX = previousCard.x + offsetPx

            val constraintLayout = constraintlayout
            val params = ConstraintLayout.LayoutParams(widthPx, heightPx)
            cardNextCard.setLayoutParams(params)

            constraintLayout.addView(cardNextCard)


            deck.performDealingAnimation(
                cardNextCard,
                cardDealingOutCardsToPlayer.x,
                cardDealingOutCardsToPlayer.y,
                cardEndX,
                previousCard.y,
                0f,
                180f
            )

            val card1BlankPlayerEndX = card1BlankPlayer.x + offsetPx
            val card2BlankPlayerEndX = card2BlankPlayer.x + offsetPx

            val card1Animator = ObjectAnimator.ofFloat(
                card2BlankPlayer,
                View.X,
                card2BlankPlayer.x,
                card1BlankPlayerEndX
            )
            card1Animator.interpolator = AccelerateDecelerateInterpolator()
            card1Animator.duration = 500L

            val card2Animator = ObjectAnimator.ofFloat(
                cardNextCard,
                View.X,
                cardDealingOutCardsToPlayer.x,
                card2BlankPlayerEndX
            )
            card2Animator.interpolator = AccelerateDecelerateInterpolator()
            card2Animator.duration = 500L

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(card1Animator, card2Animator)

            animatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    val cardNextCardEndX = card2BlankPlayer.x + offsetPx
                    val cardNextCardAnimator = ObjectAnimator.ofFloat(
                        cardNextCard,
                        View.X,
                        card2BlankPlayerEndX,
                        cardNextCardEndX
                    )
                    cardNextCardAnimator.interpolator = AccelerateDecelerateInterpolator()
                    cardNextCardAnimator.duration = 500L
                    cardNextCardAnimator.start()
                }
            })
            animatorSet.start()

            hitCounter++
        }
        fun drawPlayerCard(previousCard: ImageView) {

            deck.drawCard(playerHand.cards)
            val newCard = playerHand.cards.last()
            val image = cardDisplay.getCardImage(newCard)

            val cardNextCard = ImageView(context).apply {   // card.id?
                visibility = View.VISIBLE
                setImageResource(image)
                id = View.generateViewId()
            }

            val offsetDp = 35f + (35f * hitCounter)
            val widthDp = 70f
            val heightDp = 100f
            val density = resources.displayMetrics.density
            val offsetPx = (offsetDp * density).toInt()
            val widthPx = (widthDp * density).toInt()
            val heightPx = (heightDp * density).toInt()
            val cardEndX = previousCard.x + offsetPx

            val constraintLayout = constraintlayout
            val params = ConstraintLayout.LayoutParams(widthPx, heightPx)
            cardNextCard.setLayoutParams(params)

            constraintLayout.addView(cardNextCard)


            deck.performDealingAnimation(
                cardNextCard,
                cardDealingOutCardsToPlayer.x,
                cardDealingOutCardsToPlayer.y,
                cardEndX,
                previousCard.y,
                0f,
                180f
            )
            hitCounter++
        }
//            playerHand.clear()
//            dealerHand.clear()
//            hitCounter = 0
//            playerCardImageViews.forEach { it.visibility = View.INVISIBLE }
//            dealerCardImageViews.forEach { it.visibility = View.INVISIBLE }
//            selectedMarkers.forEach { (_, betInfo) ->
//                betInfo.madeBetMarker.visibility = View.INVISIBLE

        hit.setOnClickListener {
            animations.shakeButton(hit)
            if (hitCounter == 0) {
                animations.buttonOutLeftSide(doubleDown, requireContext(), 1000L)
                drawAndAnimateCard(card2BlankPlayer)
            }else if (hitCounter > 0){
                    drawPlayerCard(card2BlankPlayer)
                }
//            } else if (hitCounter == 1) {
//                animations.shakeButton(hit)
//                deck.drawCard(playerHand.cards)
//                val newCard = playerHand.cards.last()
//                val image = cardDisplay.getCardImage(newCard)
//
//                cardNextCard2.visibility = View.VISIBLE
//                cardNextCard2.setImageResource(image)
//                cardNextCard2.id = View.generateViewId()
//                val offsetDp = 35f
//                val density = resources.displayMetrics.density
//                val offsetPx = (offsetDp * density).toInt()
//                val cardNextCardEndX = cardNextCard.x + offsetPx
//                deck.performDealingAnimation(
//                    cardNextCard2,
//                    cardDealingOutCardsToPlayer.x,
//                    cardDealingOutCardsToPlayer.y,
//                    cardNextCardEndX,
//                    cardNextCard.y,
//                    0f,
//                    180f
//                )
//                hitCounter++
//            } else if (hitCounter == 2) {
//                animations.shakeButton(hit)
//                deck.drawCard(playerHand.cards)
//                val newCard = playerHand.cards.last()
//                val image = cardDisplay.getCardImage(newCard)
//
//                cardNextCard3.visibility = View.VISIBLE
//                cardNextCard3.setImageResource(image)
//                cardNextCard3.id = View.generateViewId()
//                val offsetDp = 35f
//                val density = resources.displayMetrics.density
//                val offsetPx = (offsetDp * density).toInt()
//                val cardNextCard2EndX = cardNextCard2.x + offsetPx
//                deck.performDealingAnimation(
//                    cardNextCard3,
//                    cardDealingOutCardsToPlayer.x,
//                    cardDealingOutCardsToPlayer.y,
//                    cardNextCard2EndX,
//                    cardNextCard2.y,
//                    0f,
//                    180f
//                )
//                hitCounter++
//            } else if (hitCounter == 3) {
//                animations.shakeButton(hit)
//                deck.drawCard(playerHand.cards)
//                val newCard = playerHand.cards.last()
//                val image = cardDisplay.getCardImage(newCard)
//
//                cardNextCard4.visibility = View.VISIBLE
//                cardNextCard4.setImageResource(image)
//                cardNextCard4.id = View.generateViewId()
//                val offsetDp = 35f
//                val density = resources.displayMetrics.density
//                val offsetPx = (offsetDp * density).toInt()
//                val cardNextCard3EndX = cardNextCard3.x + offsetPx
//                deck.performDealingAnimation(
//                    cardNextCard4,
//                    cardDealingOutCardsToPlayer.x,
//                    cardDealingOutCardsToPlayer.y,
//                    cardNextCard3EndX,
//                    cardNextCard.y,
//                    0f,
//                    180f
//                )
//                hitCounter++




            animations.shakeTextSubtle(pointsPlayerText)
            playerHand.updatePointsText(pointsPlayerText, false)

            val (pointsWithAceAsOne, pointsWithAceAsEleven) = playerHand.calculatePoints(
                playerHand.cards
            )

            if (pointsWithAceAsOne > 21 && pointsWithAceAsEleven > 21) {
                animations.buttonOutRightSide(hit, requireContext(), 1000L)
                animations.buttonOutRightSide(stand, requireContext(), 1000L)
                animations.fadeOutTextView(pointsPlayerText)
                Handler().postDelayed({
                    flipCard(cardDarkDealer, dealerHand)
                }, delay.flipCard)
                Handler().postDelayed({
                    animations.fadeInAndMoveUpImageView(bannerBust)
                    bannerManager.fadeOutBanner(bannerBust)
                    moveCardsToPile(bannerBust)
                    animateMarkersToWinner(winnerIsDealer = true)
                    animations.fadeOutTextView(pointsDealerText)
                    animations.fadeOutTextView(totalBetText)
                    losses++
                }, delay.bust)
            } else if (pointsWithAceAsOne == 21 || pointsWithAceAsEleven == 21) {
                animations.buttonOutRightSide(hit, requireContext(), 1000L)
                animations.buttonOutRightSide(stand, requireContext(), 1000L)
                playerHand.updatePointsText(pointsPlayerText, true)
                Handler().postDelayed({
                    flipCard(cardDarkDealer, dealerHand)
                    checKDealerPoints(dealerHand, pointsDealerText)
                }, delay.checkDealer)
            }
        }



        stand.setOnClickListener {
            dealerHand.updatePointsText(pointsDealerText, false)
            playerHand.updatePointsText(pointsPlayerText, true)
            animations.buttonOutLeftSide(doubleDown, requireContext(), 1000L)
            animations.buttonOutRightSide(hit, requireContext(), 1000L)
            animations.buttonOutRightSide(stand, requireContext(), 1000L)
            if (dealerGotAce()) {
                animations.fadeInImageButton(insurance)
                animations.buttonInLeftSide(yes, requireContext(), 1000L)
                animations.buttonInRightSide(no, requireContext(), 1000L)
                yes.setOnClickListener {
                    insurance()
                    val insuranceAmount = totalBet / 2
                    playerMoney -= insuranceAmount
                    playerMoneyText.text = playerMoney.toString()
                    if (dealerHand.isBlackjack()) {
                        playerMoney += totalBet
                        playerMoneyText.text = playerMoney.toString()
                    }
                }
                no.setOnClickListener {
                    insurance()
                }
            } else {
                Handler().postDelayed({
                    flipCard(cardDarkDealer, dealerHand)
                    Handler().postDelayed({
                        checKDealerPoints(dealerHand, pointsDealerText)
                    }, delay.checkDealer)
                }, delay.flipCard)

            }
        }



        repeatBet.setOnClickListener {
            totalBet = lastBetMarkersValue.sum()
            totalBetText.text = totalBet.toString()
            animations.buttonOutRightSide(repeatBet, requireContext(), 1000L)

            for ((marker, betInfo) in selectedMarkers) {
                betInfo.count = lastBetMarkersValue.count { it == betInfo.marker.id }
                betInfo.marker.visibility = View.VISIBLE
            }
            startGame()
        }



        return view
    }
}







