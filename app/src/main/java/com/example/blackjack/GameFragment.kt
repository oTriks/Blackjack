package com.example.blackjack

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.blackjack.DelaysUtil.Companion.activateMarkers
import com.example.blackjack.DelaysUtil.Companion.bannerShow
import com.example.blackjack.DelaysUtil.Companion.blackjackDealer
import com.example.blackjack.DelaysUtil.Companion.blackjackPlayerBanner
import com.example.blackjack.DelaysUtil.Companion.bust
import com.example.blackjack.DelaysUtil.Companion.buttonsContinue
import com.example.blackjack.DelaysUtil.Companion.cardsToPile
import com.example.blackjack.DelaysUtil.Companion.cashOutMoney
import com.example.blackjack.DelaysUtil.Companion.checkDealer
import com.example.blackjack.DelaysUtil.Companion.checkDealerCards
import com.example.blackjack.DelaysUtil.Companion.checkDealersCards
import com.example.blackjack.DelaysUtil.Companion.drawCard
import com.example.blackjack.DelaysUtil.Companion.drawNewCard
import com.example.blackjack.DelaysUtil.Companion.flipDealerCard
import com.example.blackjack.DelaysUtil.Companion.gameStart
import com.example.blackjack.DelaysUtil.Companion.invisbleCards
import com.example.blackjack.DelaysUtil.Companion.lowHand
import com.example.blackjack.DelaysUtil.Companion.noMoney
import com.example.blackjack.DelaysUtil.Companion.pile
import com.example.blackjack.DelaysUtil.Companion.removeBannersBlackjack
import com.example.blackjack.DelaysUtil.Companion.removeBlackjackBanner
import com.example.blackjack.DelaysUtil.Companion.removeMarkers
import com.example.blackjack.DelaysUtil.Companion.repeatBetButton
import com.example.blackjack.DelaysUtil.Companion.reset
import com.example.blackjack.DelaysUtil.Companion.revealBlackjack
import com.example.blackjack.DelaysUtil.Companion.revealCard
import com.example.blackjack.DelaysUtil.Companion.revealCardWhenFlip
import com.example.blackjack.DelaysUtil.Companion.shakeCard
import com.example.blackjack.DelaysUtil.Companion.splitActions
import com.example.blackjack.DelaysUtil.Companion.toPile
import com.example.blackjack.DelaysUtil.Companion.updateMoney

class GameFragment : Fragment() {
    data class BetInfo(
        val marker: ImageView,
        val madeBetMarker: ImageView,
        var count: Int,
        val value: Int
    )

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
        val deal = view.findViewById<ImageButton>(R.id.dealImageButton)
        val stand = view.findViewById<ImageButton>(R.id.standImageButton)
        val hit = view.findViewById<ImageButton>(R.id.hitImageButton)
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
        val cashOutButton = view.findViewById<ImageView>(R.id.cashoutImageView)
        val bannerNoFunds = view.findViewById<ImageView>(R.id.bannerNoFundsImageView)

        val viewsToHide = arrayOf(
            bannerNoFunds,
            cardBlankDealer,
            cardNextCardDealer,
            cardDarkDealer,
            cardDealingOutCardsToDealer,
            cardDealingOutCardsToPlayer,
            cardDealingOutCardsToPlayerTense,
            card1BlankPlayer,
            card2BlankPlayer,
            cardNextCard,
            cardNextCard2,
            cardNextCard3,
            cardNextCard4,
            cardPile,
            totalBetText,
            madeBetMarker5,
            madeBetMarker10,
            madeBetMarker25,
            madeBetMarker50,
            madeBetMarker100,
            markersWinnerDealer,
            markersWinnerPlayer,
            hit,
            stand,
            deal,
            doubleDown,
            repeatBet,
            insurance,
            yes,
            no,
            bannerWin,
            bannerLose,
            bannerBust,
            bannerBlackjack,
            bannerSplit,
            bannerBlackjackPlayerSplit,
            bannerBlackjackDealerSplit,
            pointsPlayerText,
            pointsDealerText,
            homeButton,
            cashOutButton
        )
        for (view in viewsToHide) {
            view.visibility = View.INVISIBLE
        }

        val handler = Handler(Looper.getMainLooper())

        val playerCardImageViews: MutableList<ImageView> =
            mutableListOf(card1BlankPlayer, card2BlankPlayer, cardNextCard)
        val dealerCardImageViews: MutableList<ImageView> =
            mutableListOf(cardBlankDealer, cardDarkDealer, cardNextCardDealer)
        var lastBetMarkersValue = mutableListOf<Int>()
        val selectedMarkers = mutableMapOf<ImageView, BetInfo>()
        val selectedBets = mutableListOf<BetInfo>()
        val markerList = listOf(marker5, marker10, marker25, marker50, marker100)

        val cardDisplay = CardDisplay()
        val playerHand = Hand()
        val dealerHand = Hand()
        val animations = Animations()
        val util = Util()
        val bannerManager = BannerManager(animations)

        val deck = Deck(
            requireContext(),
            card1BlankPlayer,
            cardDealingOutCardsToPlayer,
            cardBlankDealer,
            cardDealingOutCardsToDealer,
            card2BlankPlayer,
            cardDarkDealer,
        )

        var winner = ""
        var wins: Int = 0
        var losses: Int = 0
        var blackjacks: Int = 0
        var gamesPlayed: Int = 0
        var draws: Int = 0
        var totalBetCost = 0
        var playerMoney = 500
        var totalBet = 0
        var highscore = 0
        var lastBet = 0
        var dealerDrawCounter = 0
        var playerHitCounter = 0
        var isNewRound = true


        val originalPositionsMarkers = hashMapOf<ImageView, Pair<Float, Float>>()
        fun saveOriginalPositionMarkers(imageView: ImageView) {
            val viewTreeObserver = imageView.viewTreeObserver
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    originalPositionsMarkers[imageView] = Pair(imageView.x, imageView.y)
                }
            })
        }
        saveOriginalPositionMarkers(madeBetMarker5)
        saveOriginalPositionMarkers(madeBetMarker10)
        saveOriginalPositionMarkers(madeBetMarker25)
        saveOriginalPositionMarkers(madeBetMarker50)
        saveOriginalPositionMarkers(madeBetMarker100)

        fun resetPositionsMarkers() {
            originalPositionsMarkers.forEach { (imageView, position) ->
                imageView.x = position.first
                imageView.y = position.second
            }
        }

        val originalPositionsCards = hashMapOf<ImageView, Pair<Float, Float>>()
        fun saveOriginalPositionCards(imageView: ImageView) {
            val viewTreeObserver = imageView.viewTreeObserver
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    originalPositionsCards[imageView] = Pair(imageView.x, imageView.y)
                }
            })
        }
        saveOriginalPositionCards(card1BlankPlayer)
        saveOriginalPositionCards(card2BlankPlayer)
        saveOriginalPositionCards(cardBlankDealer)
        saveOriginalPositionCards(cardDarkDealer)
        saveOriginalPositionCards(cardNextCard)

        fun resetPositionsCards() {
            originalPositionsCards.forEach { (imageView, position) ->
                imageView.x = position.first
                imageView.y = position.second
            }
        }

        animations.fadeInImageView(homeButton)
        playerMoneyText.text = playerMoney.toString()

        fun setupMarker(
            marker: ImageView,
            madeBetMarker: ImageView,
            value: Int,
            totalBetText: TextView,
            dealButton: ImageButton,
        ) {
            val betInfo =
                selectedMarkers.getOrDefault(marker, BetInfo(marker, madeBetMarker, 0, value))
                    .apply {
                        count++
                    }
            selectedMarkers[marker] = betInfo
            marker.setOnClickListener {
                if (isNewRound) {
                    selectedBets.clear()
                    lastBetMarkersValue.clear()
                    isNewRound = false
                }
                repeatBet.isEnabled = false
                if (playerMoney >= value) {
                    selectedBets.add(betInfo)
                    playerMoney -= value
                    lastBetMarkersValue.add(value)
                    madeBetMarker.visibility = View.VISIBLE
                    animations.fadeInTextView(totalBetText)
                    lastBet = lastBetMarkersValue.sum()

                    animations.moveObject(
                        madeBetMarker,
                        marker.x,
                        marker.y,
                        madeBetMarker.x,
                        madeBetMarker.y,
                        500L,
                        0L
                    )
                    totalBet = lastBetMarkersValue.sum()
                    totalBetText.text = totalBet.toString()
                    animations.buttonInRightSide(deal, requireContext(), 1000L)
                    animations.buttonOutRightSide(repeatBet, requireContext(), 1000L)
                    util.setTextViewBackground(totalBetText, totalBet.toString(), R.color.black)
                } else {
                    animations.fadeInAndMoveUpImageView(bannerNoFunds)
                    handler.postDelayed({
                        animations.fadeOutImageView(bannerNoFunds)
                    }, noMoney)
                }
            }
        }
        setupMarker(marker5, madeBetMarker5, 5, totalBetText, deal)
        setupMarker(marker10, madeBetMarker10, 10, totalBetText, deal)
        setupMarker(marker25, madeBetMarker25, 25, totalBetText, deal)
        setupMarker(marker50, madeBetMarker50, 50, totalBetText, deal)
        setupMarker(marker100, madeBetMarker100, 100, totalBetText, deal)


        fun listOfCardsToPile(cardImageViews: List<ImageView>, cardPile: ImageView) {
            dealerCardImageViews.forEachIndexed { index, cardImageView ->
                animations.animateCardToPile(cardImageView, cardPile, index, requireContext())
            }
            cardImageViews.forEachIndexed { index, cardImageView ->
                animations.animateCardToPile(cardImageView, cardPile, index, requireContext())
            }
        }


        fun moveCardsToPile(banner: View) {
            handler.postDelayed({
                listOfCardsToPile(playerCardImageViews, cardPile)
            }, cardsToPile)

            handler.postDelayed({
                cardPile.visibility = View.VISIBLE
            }, pile)

            handler.postDelayed({
                banner.visibility = View.INVISIBLE   // not working??
            }, bannerShow)

            handler.postDelayed({
                animations.buttonInRightSide(repeatBet, requireContext(), 1300L)
            }, repeatBetButton)

            handler.postDelayed({
                dealerCardImageViews.forEachIndexed { index, cardImageView ->
                    cardImageView.visibility = View.INVISIBLE
                }
                playerCardImageViews.forEachIndexed { index, cardImageView ->
                    cardImageView.visibility = View.INVISIBLE
                }
            }, invisbleCards)
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

        fun flipCard(imageView: ImageView, hand: Hand) {
            animations.flipCard(imageView)
            handler.postDelayed({
                updateDealerCards(hand)
            }, revealCardWhenFlip)
            hand.revealHiddenCard()
        }

        fun flipCardTense(imageView: ImageView, hand: Hand) {
            handler.postDelayed({
                animations.shakeCardSubtle(cardDarkDealer)
            }, shakeCard)
            handler.postDelayed({
                flipCard(cardDarkDealer, dealerHand)
            }, revealCard)
            hand.revealHiddenCard()
        }

        fun drawFirstCard(
            hand: Hand,
            previousCard: ImageView,
            offsetDp: Float,
            widthDp: Float,
            heightDp: Float,
            dealingOutCards: ImageView,
            card1: ImageView,
            card2: ImageView,
            counter: Int,
            cardList: MutableList<ImageView>
        ): Int {
            deck.drawCard(hand.cards)
            val newCard = hand.cards.last()
            val image = cardDisplay.getCardImage(newCard)

            val cardNextCard = ImageView(context).apply {
                visibility = View.VISIBLE
                setImageResource(image)
                id = View.generateViewId()
            }

            val density = resources.displayMetrics.density
            val offsetPx = (offsetDp * density).toInt()
            val widthPx = (widthDp * density).toInt()
            val heightPx = (heightDp * density).toInt()
            val cardEndX = previousCard.x + offsetPx

            val constraintLayout = constraintlayout
            val params = ConstraintLayout.LayoutParams(widthPx, heightPx)
            cardNextCard.setLayoutParams(params)
            constraintLayout.addView(cardNextCard)
            cardList.add(cardNextCard)
            deck.performDealingAnimation(
                cardNextCard,
                dealingOutCards.x,
                dealingOutCards.y,
                cardEndX,
                previousCard.y,
                0f,
                180f
            )
            val card1EndX = card1.x + offsetPx
            val card2EndX = card2.x + offsetPx

            val card1Animator = ObjectAnimator.ofFloat(
                card2,
                View.X,
                card2.x,
                card1EndX
            )
            card1Animator.interpolator = AccelerateDecelerateInterpolator()
            card1Animator.duration = 500L

            val card2Animator = ObjectAnimator.ofFloat(
                cardNextCard,
                View.X,
                dealingOutCards.x,
                card2EndX
            )
            card2Animator.interpolator = AccelerateDecelerateInterpolator()
            card2Animator.duration = 500L

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(card1Animator, card2Animator)

            animatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    val cardNextCardEndX = card2.x + offsetPx
                    val cardNextCardAnimator = ObjectAnimator.ofFloat(
                        cardNextCard,
                        View.X,
                        card2EndX,
                        cardNextCardEndX
                    )
                    cardNextCardAnimator.interpolator = AccelerateDecelerateInterpolator()
                    cardNextCardAnimator.duration = 500L
                    cardNextCardAnimator.start()
                }
            })
            animatorSet.start()
            return counter + 1
        }


        fun drawCard(
            hand: Hand,
            previousCard: ImageView,
            offsetDp: Float,
            widthDp: Float,
            heightDp: Float,
            dealingOutCards: ImageView,
            counter: Int,
            cardList: MutableList<ImageView>
        ): Int {
            deck.drawCard(hand.cards)
            val newCard = hand.cards.last()
            val image = cardDisplay.getCardImage(newCard)

            val cardNextCard = ImageView(context).apply {
                visibility = View.VISIBLE
                setImageResource(image)
                id = View.generateViewId()
            }
            val density = resources.displayMetrics.density
            val offsetPx = (offsetDp * density).toInt()
            val widthPx = (widthDp * density).toInt()
            val heightPx = (heightDp * density).toInt()
            val cardEndX = previousCard.x + offsetPx

            val constraintLayout = constraintlayout
            val params = ConstraintLayout.LayoutParams(widthPx, heightPx)
            cardList.add(cardNextCard)
            cardNextCard.setLayoutParams(params)
            constraintLayout.addView(cardNextCard)

            deck.performDealingAnimation(
                cardNextCard,
                dealingOutCards.x,
                dealingOutCards.y,
                cardEndX,
                previousCard.y,
                0f,
                180f
            )
            return counter + 1
        }

        fun drawCardDealer() {
            if (dealerDrawCounter == 0) {
                dealerDrawCounter = drawFirstCard(
                    dealerHand,
                    cardDarkDealer,
                    35f,
                    70f,
                    100f,
                    cardDealingOutCardsToDealer,
                    cardBlankDealer,
                    cardDarkDealer,
                    dealerDrawCounter,
                    dealerCardImageViews
                )
            } else if (dealerDrawCounter > 0) {
                dealerDrawCounter = drawCard(
                    dealerHand,
                    cardDarkDealer,
                    35f + (35f * dealerDrawCounter),
                    70f,
                    100f,
                    cardDealingOutCardsToDealer,
                    dealerDrawCounter,
                    dealerCardImageViews
                )
            }
        }

        fun animateMarkersToWinner(winnerIsDealer: Boolean) {
            val destinationX = if (winnerIsDealer) markersWinnerDealer.x else markersWinnerPlayer.x
            val destinationY = if (winnerIsDealer) markersWinnerDealer.y else markersWinnerPlayer.y
            for ((marker, betInfo) in selectedMarkers) {
                animations.moveObject(
                    betInfo.madeBetMarker,
                    betInfo.madeBetMarker.x,
                    betInfo.madeBetMarker.y,
                    destinationX,
                    destinationY,
                    500L,
                    0L
                )
                handler.postDelayed({
                    animations.fadeOutMarkersImageView(betInfo.madeBetMarker)
                }, removeMarkers)
            }
        }


        fun gameEnd() {
            isNewRound = true
            playerHitCounter = 0
            dealerDrawCounter = 0
            handler.postDelayed({
                for (marker in markerList) {
                    marker.isEnabled = true
                }
            }, activateMarkers)
            deal.isEnabled = true
            repeatBet.isEnabled = true

            animations.fadeOutTextView(pointsPlayerText)
            animations.fadeOutTextView(pointsDealerText)
            animations.fadeOutTextView(totalBetText)
            gamesPlayed++
            handler.postDelayed({
                animations.fadeInImageView(cashOutButton)
            }, cashOutMoney)
            handler.postDelayed({
                resetPositionsCards()
                resetPositionsMarkers()
                yes.isEnabled = true
                no.isEnabled = true
            }, reset)
        }


        fun bestPoints(pointsWithAceAsOne: Int, pointsWithAceAsEleven: Int): Int {
            return if (pointsWithAceAsEleven <= 21) pointsWithAceAsEleven else pointsWithAceAsOne
        }

        fun determineWinner(playerHand: Hand, dealerHand: Hand) {
            val playerPoints = playerHand.calculatePoints(playerHand.cards)
            val dealerPoints = dealerHand.calculatePoints(dealerHand.cards)

            val bestPlayerPoints = bestPoints(playerPoints.first, playerPoints.second)
            val bestDealerPoints = bestPoints(dealerPoints.first, dealerPoints.second)

            val winner = compareHands(bestPlayerPoints, bestDealerPoints)
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
            val (pointsWithAceAsOne, pointsWithAceAsEleven) = dealerHand.calculatePoints(dealerHand.cards)
            when {
                pointsWithAceAsEleven > 21 && pointsWithAceAsOne > 21 -> {
                    dealerPointsText.text = pointsWithAceAsOne.toString()
                                determineWinner(playerHand, dealerHand)
                }

                pointsWithAceAsEleven > 21 -> {
                    dealerPointsText.text = pointsWithAceAsOne.toString()
                                determineWinner(playerHand, dealerHand)
                }

                pointsWithAceAsEleven in 17..21 -> {
                    dealerPointsText.text = pointsWithAceAsEleven.toString()
                                determineWinner(playerHand, dealerHand)
                }
                pointsWithAceAsEleven < 17 && pointsWithAceAsOne < 17 -> {
                    drawCardDealer()
                    dealerHand.updatePointsText(pointsDealerText, false)
                    Log.d("blackjack", "drawCardWithDelay")
                    handler.postDelayed({
                    checKDealerPoints(dealerHand, pointsDealerText)
                    }, lowHand)
                }
            }
        }

        fun checkForBlackjack(playerHand: Hand, dealerHand: Hand) {
            if (playerHand.isBlackjack()) {
                handler.postDelayed({
                    animations.fadeInImageView(bannerBlackjackPlayerSplit)
                    flipCardTense(cardDarkDealer, dealerHand)
                    handler.postDelayed({
                        handler.postDelayed({
                            if (dealerHand.isBlackjack()) {
                                animations.fadeInImageView(bannerBlackjackDealerSplit)
                                handler.postDelayed({
                                    animations.fadeInAndMoveUpImageView(bannerSplit)
                                    bannerManager.fadeOutBanner(bannerSplit)
                                    moveCardsToPile(bannerSplit)
                                    draws++
                                    gameEnd()
                                }, splitActions)
                                handler.postDelayed({
                                    animations.removeImage(bannerBlackjackDealerSplit)
                                    animations.removeImage(bannerBlackjackPlayerSplit)
                                }, removeBannersBlackjack)
                            } else {
                                animations.fadeInAndMoveUpImageView(bannerWin)
                                animations.removeImage(bannerBlackjackPlayerSplit)
                                bannerManager.fadeOutBanner(bannerWin)
                                moveCardsToPile(bannerWin)
                                animateMarkersToWinner(winnerIsDealer = false)
                                playerMoney += (2.5 * totalBet).toInt()
                                playerMoneyText.text = playerMoney.toString()
                                wins++
                                blackjacks++
                                gameEnd()
                            }
                        }, blackjackDealer)
                    }, checkDealersCards)
                }, blackjackPlayerBanner)
                handler.postDelayed({
                    moveCardsToPile(bannerBlackjack)
                }, toPile)
            } else if (!playerHand.isBlackjack()) {
                handler.postDelayed({
                    animations.buttonInLeftSide(doubleDown, requireContext(), 1000L)

                    animations.buttonInRightSide(hit, requireContext(), 1000L)
                    animations.buttonInRightSide(stand, requireContext(), 1000L)
                    playerHand.updatePointsText(pointsPlayerText, false)
                    animations.fadeInTextView(pointsPlayerText)
                    dealerHand.updatePointsText(pointsDealerText, false)
                    animations.fadeInTextView(pointsDealerText)
                }, buttonsContinue)
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


        doubleDown.setOnClickListener {
            val doubleDownAmount = totalBet
            if (playerMoney >= doubleDownAmount) {
                animations.buttonOutRightSide(hit, requireContext(), 1000L)
                animations.buttonOutRightSide(stand, requireContext(), 1000L)
                animations.buttonOutLeftSide(doubleDown, requireContext(), 1000L)
                animations.fadeInImageView(cardDealingOutCardsToPlayerTense)
                animations.shakeCardTense(cardDealingOutCardsToPlayerTense)
                playerMoney -= doubleDownAmount
                playerMoneyText.text = playerMoney.toString()
                totalBet = lastBetMarkersValue.sum() * 2
                totalBetText.text = totalBet.toString()
                for ((marker, betInfo) in selectedMarkers) {
                    animations.moveObject(
                        betInfo.madeBetMarker,
                        marker.x,
                        marker.y,
                        betInfo.madeBetMarker.x,
                        betInfo.madeBetMarker.y,
                        500L,
                        0L
                    )
                }

                handler.postDelayed({
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
                    handler.postDelayed({
                        flipCard(cardDarkDealer, dealerHand)
                        checKDealerPoints(dealerHand, pointsDealerText)
                        gameEnd()
                    }, checkDealerCards)
                }, drawCard)
            } else {
                animations.fadeInAndMoveUpImageView(bannerNoFunds)
                handler.postDelayed({
                    animations.fadeOutImageView(bannerNoFunds)
                }, noMoney)
            }
        }
        fun insurance() {
            animations.buttonOutLeftSide(yes, requireContext(), 1000L)
            animations.buttonOutRightSide(no, requireContext(), 1000L)
            animations.removeImage(insurance)
            flipCardTense(cardDarkDealer, dealerHand)
            if (dealerHand.isBlackjack()) {
                flipCardTense(cardDarkDealer, dealerHand)
                handler.postDelayed({
                    dealerHand.revealHiddenCard()
                    animations.fadeInImageView(bannerBlackjackDealerSplit)
                    animations.fadeInAndMoveUpImageView(bannerLose)
                    bannerManager.fadeOutBanner(bannerLose)
                    animateMarkersToWinner(winnerIsDealer = true)
                    moveCardsToPile(bannerLose)
                    losses++
                    gameEnd()
                }, revealBlackjack)
                handler.postDelayed({
                    animations.removeImage(bannerBlackjackDealerSplit)
                }, removeBlackjackBanner)
            } else if (!dealerHand.isBlackjack()) {
                handler.postDelayed({
                    checKDealerPoints(dealerHand, pointsDealerText)
                }, drawNewCard)
            }
        }

        fun startGame() {

            playerHand.clear()
            dealerHand.clear()
            deck.dealHand(playerHand, dealerHand)
            updateDealerCards(dealerHand)
            updateCardImages(playerHand, playerCardImageViews, cardDisplay)
            updateCardImages(dealerHand, dealerCardImageViews, cardDisplay)
            checkForBlackjack(playerHand, dealerHand)
        }

        fun cashOut() {
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

        cashOutButton.setOnClickListener {
            cashOut()
        }
        homeButton.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        deal.setOnClickListener {
            deal.isEnabled = false
            repeatBet.isEnabled = false
            animations.buttonOutRightSide(deal, requireContext(), 1000L)
            playerMoneyText.text = playerMoney.toString()
            startGame()
            for (marker in markerList) {
                marker.isEnabled = false
            }
        }


        hit.setOnClickListener {
            animations.shakeButton(hit)
            if (playerHitCounter == 0) {
                animations.buttonOutLeftSide(doubleDown, requireContext(), 1000L)
                playerHitCounter = drawFirstCard(
                    playerHand,
                    card2BlankPlayer,
                    35f,
                    70f,
                    100f,
                    cardDealingOutCardsToPlayer,
                    card1BlankPlayer,
                    card2BlankPlayer,
                    playerHitCounter,
                    playerCardImageViews
                )
            } else if (playerHitCounter > 0) {
                playerHitCounter = drawCard(
                    playerHand,
                    card2BlankPlayer,
                    35f + (35f * playerHitCounter),
                    70f,
                    100f,
                    cardDealingOutCardsToPlayer,
                    playerHitCounter,
                    playerCardImageViews
                )
            }

            animations.shakeTextSubtle(pointsPlayerText)
            playerHand.updatePointsText(pointsPlayerText, false)

            val (pointsWithAceAsOne, pointsWithAceAsEleven) = playerHand.calculatePoints(
                playerHand.cards
            )

            if (pointsWithAceAsOne > 21 && pointsWithAceAsEleven > 21) {
                animations.buttonOutRightSide(hit, requireContext(), 1000L)
                animations.buttonOutRightSide(stand, requireContext(), 1000L)
                animations.fadeOutTextView(pointsPlayerText)
                handler.postDelayed({
                    flipCard(cardDarkDealer, dealerHand)
                }, flipDealerCard)
                handler.postDelayed({
                    animations.fadeInAndMoveUpImageView(bannerBust)
                    bannerManager.fadeOutBanner(bannerBust)
                    moveCardsToPile(bannerBust)
                    animateMarkersToWinner(winnerIsDealer = true)
                    animations.fadeOutTextView(pointsDealerText)
                    animations.fadeOutTextView(totalBetText)
                    losses++
                    gameEnd()
                }, bust)
            } else if (pointsWithAceAsOne == 21 || pointsWithAceAsEleven == 21) {
                animations.buttonOutRightSide(hit, requireContext(), 1000L)
                animations.buttonOutRightSide(stand, requireContext(), 1000L)
                playerHand.updatePointsText(pointsPlayerText, true)
                handler.postDelayed({
                    flipCard(cardDarkDealer, dealerHand)
                    checKDealerPoints(dealerHand, pointsDealerText)
                }, checkDealer)
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
                insurance.bringToFront()
                yes.bringToFront()
                no.bringToFront()
                yes.setOnClickListener {
                    yes.isEnabled = false
                    val insuranceAmount = totalBet / 2
                    if (playerMoney >= insuranceAmount) {
                        insurance()
                        playerMoney -= insuranceAmount
                        playerMoneyText.text = playerMoney.toString()
                        for ((marker, betInfo) in selectedMarkers) {
                            animations.moveObject(
                                betInfo.madeBetMarker,
                                marker.x,
                                marker.y,
                                betInfo.madeBetMarker.x,
                                betInfo.madeBetMarker.y,
                                500L,
                                0L
                            )
                        }
                    } else {
                        bannerNoFunds.bringToFront()
                        animations.fadeInAndMoveUpImageView(bannerNoFunds)
                        handler.postDelayed({
                            animations.fadeOutImageView(bannerNoFunds)
                        }, noMoney)
                    }
                    if (dealerHand.isBlackjack()) {
                        animateMarkersToWinner(winnerIsDealer = true)
                        handler.postDelayed({
                            playerMoney += totalBet
                            playerMoneyText.text = playerMoney.toString()
                        }, updateMoney)
                    }
                }

                no.setOnClickListener {
                    no.isEnabled = false
                    insurance()
                }
            } else {
                handler.postDelayed({
                    flipCard(cardDarkDealer, dealerHand)
                    if (dealerHand.isBlackjack()) {
                        handler.postDelayed({
                            animations.fadeInAndMoveUpImageView(bannerBlackjack)
                            bannerManager.fadeOutBanner(bannerBlackjack)
                            moveCardsToPile(bannerBlackjack)
                            animateMarkersToWinner(winnerIsDealer = true)
                            animations.fadeOutTextView(pointsDealerText)
                            animations.fadeOutTextView(totalBetText)
                            losses++
                            gameEnd()
                        }, blackjackDealer)
                    } else
                        handler.postDelayed({
                            checKDealerPoints(dealerHand, pointsDealerText)
                        }, checkDealer)
                }, flipDealerCard)
            }
        }


        repeatBet.setOnClickListener {
            animations.buttonOutRightSide(repeatBet, requireContext(), 1000L)
            deal.isEnabled = false
            repeatBet.isEnabled = false
            for (marker in markerList) {
                marker.isEnabled = false
            }
            resetPositionsCards()
            resetPositionsMarkers()

            var totalBetCost = 0

            for (betInfo in selectedBets) {
                betInfo.madeBetMarker.visibility = View.VISIBLE
                betInfo.count++
                totalBetCost += betInfo.count * betInfo.value

                animations.moveObject(
                    betInfo.madeBetMarker,
                    betInfo.marker.x,
                    betInfo.marker.y,
                    betInfo.madeBetMarker.x,
                    betInfo.madeBetMarker.y,
                    500L,
                    0L
                )
            }
            totalBet = lastBetMarkersValue.sum()
            totalBetText.text = totalBet.toString()
            animations.fadeInTextView(totalBetText)
            handler.postDelayed({
                startGame()
            }, gameStart)
        }

        return view
    }

}







