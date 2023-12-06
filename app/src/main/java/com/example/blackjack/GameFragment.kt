package com.example.blackjack

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.media.Image
import android.os.Build
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
import com.example.blackjack.DelaysUtil.Companion.bannerShow
import com.example.blackjack.DelaysUtil.Companion.blackjackDealer
import com.example.blackjack.DelaysUtil.Companion.blackjackPlayerBanner
import com.example.blackjack.DelaysUtil.Companion.bust
import com.example.blackjack.DelaysUtil.Companion.buttonsContinue
import com.example.blackjack.DelaysUtil.Companion.cardsToPile
import com.example.blackjack.DelaysUtil.Companion.cashOutMoney
import com.example.blackjack.DelaysUtil.Companion.checkDealer
import com.example.blackjack.DelaysUtil.Companion.checkDealersCards
import com.example.blackjack.DelaysUtil.Companion.dealerCards
import com.example.blackjack.DelaysUtil.Companion.doubleDownButton
import com.example.blackjack.DelaysUtil.Companion.drawCard
import com.example.blackjack.DelaysUtil.Companion.drawNewCard
import com.example.blackjack.DelaysUtil.Companion.flipDealerCard
import com.example.blackjack.DelaysUtil.Companion.getWinner
import com.example.blackjack.DelaysUtil.Companion.pile
import com.example.blackjack.DelaysUtil.Companion.removeBannersBlackjack
import com.example.blackjack.DelaysUtil.Companion.removeBlackjackBanner
import com.example.blackjack.DelaysUtil.Companion.repeatBetButton
import com.example.blackjack.DelaysUtil.Companion.revealBlackjack
import com.example.blackjack.DelaysUtil.Companion.revealCard
import com.example.blackjack.DelaysUtil.Companion.revealCardWhenFlip
import com.example.blackjack.DelaysUtil.Companion.shakeCard
import com.example.blackjack.DelaysUtil.Companion.splitActions
import com.example.blackjack.DelaysUtil.Companion.toPile

class GameFragment : Fragment() {
    val dealerHand = Hand()

    data class BetInfo( val marker: ImageView,
                        val madeBetMarker: ImageView,
                        var count: Int,
                        val value: Int
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
        madeBetMarker5.visibility = View.INVISIBLE
        madeBetMarker10.visibility = View.INVISIBLE
        madeBetMarker25.visibility = View.INVISIBLE
        madeBetMarker50.visibility = View.INVISIBLE
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
            )
        val dealerCardImageViews: MutableList<ImageView> =
            mutableListOf(cardBlankDealer, cardDarkDealer, cardNextCardDealer)
        val cardDisplay = CardDisplay()
        val playerHand = Hand()
        val dealerHand = Hand()
        val util = Util()
//        val hand = Hand()
//        val widthPx = 70   // BYT ??
//        val heightPx = 100  // BYT ??
        val widthDp = 70f // bredden i dp
        val heightDp = 100f // höjden i dp
        val density = resources.displayMetrics.density
        val widthPx = (widthDp * density).toInt() // bredden i pixlar
        val heightPx = (heightDp * density).toInt() // höjden i pixlar
//        val cardList = mutableListOf<ImageView>()  // ???

        val deck = Deck(
            requireContext(),
            card1BlankPlayer,
            cardDealingOutCardsToPlayer,
            cardBlankDealer,
            cardDealingOutCardsToDealer,
            card2BlankPlayer,
            cardDarkDealer,
            cardDisplay,
            constraintlayout,
            widthPx,
            heightPx,
            playerCardImageViews
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
        var totalBetCost = 0

        animations.fadeInImageView(homeButton)

        val handler = Handler(Looper.getMainLooper())
        var playerMoney = 500
        playerMoneyText.text = playerMoney.toString()


        val selectedMarkers = mutableMapOf<ImageView, BetInfo>()
        val selectedBets = mutableListOf<BetInfo>()


        val originalPositionsMarkers = hashMapOf<ImageView, Pair<Float, Float>>()
        fun saveOriginalPositionMarkers(imageView: ImageView) {
            val viewTreeObserver = imageView.viewTreeObserver
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        imageView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    } else {
                        imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                    originalPositionsMarkers[imageView] = Pair(imageView.x, imageView.y)
                }
            })
        }
        saveOriginalPositionMarkers(madeBetMarker5)
        saveOriginalPositionMarkers(madeBetMarker10)
        saveOriginalPositionMarkers(madeBetMarker25)
        saveOriginalPositionMarkers(madeBetMarker50)
        saveOriginalPositionMarkers(madeBetMarker100)
        fun resetCardPositionsMarkers() {
            originalPositionsMarkers.forEach { (imageView, position) ->
                imageView.x = position.first
                imageView.y = position.second
            }
        }


        val originalPositionsCards = hashMapOf<ImageView, Pair<Float, Float>>()

        fun saveOriginalPositionCards(imageView: ImageView) {
            val viewTreeObserver = imageView.viewTreeObserver
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        imageView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    } else {
                        imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                    originalPositionsCards[imageView] = Pair(imageView.x, imageView.y)
                }
            })
        }
        saveOriginalPositionCards(card1BlankPlayer)
        saveOriginalPositionCards(card2BlankPlayer)
        saveOriginalPositionCards(cardBlankDealer)
        saveOriginalPositionCards(cardDarkDealer)
        saveOriginalPositionCards(cardNextCard)

        fun resetCardPositionsCards() {
            originalPositionsCards.forEach { (imageView, position) ->
                imageView.x = position.first
                imageView.y = position.second
            }
        }


//        view.post {
//            originalPositions.clear() // Clear previous entries, if any
//            selectedMarkers.forEach { (marker, _) ->
//                originalPositions[marker] = Pair(marker.x, marker.y)
//            }
//        }



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
            dealerCardImageViews.forEachIndexed { index, cardImageView ->
                animateCardToPile(cardImageView, cardPile, index)
            }
//            handler.postDelayed({
            cardImageViews.forEachIndexed { index, cardImageView ->
                animateCardToPile(cardImageView, cardPile, index)
            }
//            }, dealerCards)  // ha dealerns kort lägst?

        }


        fun moveCardsToPile(banner: View) {
            handler.postDelayed({
                animationCardsToPile(playerCardImageViews, cardPile)
            }, cardsToPile)

            handler.postDelayed({
                cardPile.visibility = View.VISIBLE
            }, pile)

            handler.postDelayed({
                banner.visibility = View.INVISIBLE   // not working??
            }, bannerShow)

            handler.postDelayed({
                animations.buttonInRightSide(repeatBet, requireContext(), 1000L)
            }, repeatBetButton)
            Handler().postDelayed({
            Log.d("CardPositions", "after animated cards to pile position for ${card1BlankPlayer.id}: ${card1BlankPlayer.x}, ${card1BlankPlayer.y}")
            }, dealerCards)
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
            }, cashOutMoney)
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
            }, revealCardWhenFlip)
            hand.revealHiddenCard()
        }

        fun flipCardTense(imageView: ImageView, hand: Hand) {
            Log.d(TAG, "Entering someMethod()")
            Handler().postDelayed({
                animations.shakeCardSubtle(cardDarkDealer)
            }, shakeCard)
            Handler().postDelayed({
                flipCard(cardDarkDealer, dealerHand)
            }, revealCard)
            hand.revealHiddenCard()
        }

        var dealerDrawCounter = 0
        var playerHitCounter = 0

        fun drawFirstCard(hand: Hand, previousCard: ImageView, offsetDp: Float, widthDp: Float, heightDp: Float, dealingOutCards: ImageView, card1: ImageView, card2: ImageView, counter: Int, cardList: MutableList<ImageView>): Int {
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


        fun drawCard(hand: Hand, previousCard: ImageView, offsetDp: Float, widthDp: Float, heightDp: Float, dealingOutCards: ImageView, counter: Int, cardList: MutableList<ImageView>): Int {
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
            return counter +1
        }

        fun drawCardDealer() {
            if (dealerDrawCounter == 0) {
                dealerDrawCounter = drawFirstCard(dealerHand, cardDarkDealer, 15f, 50f, 70f, cardDealingOutCardsToDealer, cardBlankDealer, cardDarkDealer, dealerDrawCounter, dealerCardImageViews)
            }else if (dealerDrawCounter > 0){
                dealerDrawCounter = drawCard(dealerHand,cardDarkDealer, 15f + (15f * dealerDrawCounter), 50f, 70f, cardDealingOutCardsToDealer, dealerDrawCounter, dealerCardImageViews)
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
//            totalBet = 0
//            lastBet = 0
//            lastBetMarkersValue.clear()

//            totalBetText.text = "0"
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
                }

                pointsWithAceAsEleven > 21 -> {
                    dealerPointsText.text = pointsWithAceAsOne.toString()
                }

                pointsWithAceAsEleven in 17..21 -> {
                    dealerPointsText.text = pointsWithAceAsEleven.toString()
                }

                pointsWithAceAsEleven < 17 && pointsWithAceAsOne < 17 -> {
                    while (dealerHand.calculatePoints(dealerHand.cards).second < 17) {
                        Log.d("blackjack", "drawCardWithDelay")
                        drawCardDealer()
                        dealerHand.updatePointsText(pointsDealerText, false)

                    }
                }
            }

            Handler().postDelayed({
                determineWinner(playerHand, dealerHand)
            }, getWinner)
        }

        fun setupMarker(
            marker: ImageView,
            madeBetMarker: ImageView,
            value: Int,
            totalBetText: TextView,
            dealButton: ImageButton,
        ) {

            val betInfo = selectedMarkers.getOrDefault(marker, BetInfo(marker, madeBetMarker, 0, value)).apply {
        count++
    }
                 selectedMarkers[marker] = betInfo

                marker.setOnClickListener {
                    selectedBets.add(betInfo)
                    lastBetMarkersValue.add(value)
                madeBetMarker.visibility = View.VISIBLE
                animations.fadeInTextView(totalBetText)
                lastBet = lastBetMarkersValue.sum()

                animations.moveCard(
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
            }
        }

        setupMarker(marker5, madeBetMarker5, 5, totalBetText, deal)
        setupMarker(marker10, madeBetMarker10, 10, totalBetText, deal)
        setupMarker(marker25, madeBetMarker25, 25, totalBetText, deal)
        setupMarker(marker50, madeBetMarker50, 50, totalBetText, deal)
        setupMarker(marker100, madeBetMarker100, 100, totalBetText, deal)



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
                                }, splitActions)
                                Handler().postDelayed({
                                    animations.removeImage(bannerBlackjackDealerSplit)
                                    animations.removeImage(bannerBlackjackPlayerSplit)
                                }, removeBannersBlackjack)
                            } else {
                                animations.fadeInAndMoveUpImageView(bannerWin)
                                animations.removeImage(bannerBlackjackPlayerSplit)
                                bannerManager.fadeOutBanner(bannerWin)
                                moveCardsToPile(bannerWin)
                                playerMoney += (2.5 * totalBet).toInt()  // rätt??
                                playerMoneyText.text = playerMoney.toString()
                                wins++
                                blackjacks++

                            }
                        }, blackjackDealer)
                    }, checkDealersCards)
                    gameEnd()
                }, blackjackPlayerBanner)
                Handler().postDelayed({
                    moveCardsToPile(bannerBlackjack)
                }, toPile)
                gameEnd()
            } else if (!playerHand.isBlackjack()) {
                Handler().postDelayed({
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
                }, checkDealer)
            }, drawCard)
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
                }, revealBlackjack)
                Handler().postDelayed({
                    animations.removeImage(bannerBlackjackDealerSplit)
                }, removeBlackjackBanner)
            } else if (!dealerHand.isBlackjack()) {
                Handler().postDelayed({
                    checKDealerPoints(dealerHand, pointsDealerText)
                }, drawNewCard)
            }
        }

        fun startGame() {
            playerHand.clear()
            dealerHand.clear()
            Handler().postDelayed({
                doubleDown()
            }, doubleDownButton)
            deck.dealHand(playerHand, dealerHand)
            updateDealerCards(dealerHand)
            updateCardImages(playerHand, playerCardImageViews, cardDisplay)
            updateCardImages(dealerHand, dealerCardImageViews, cardDisplay)
            checkForBlackjack(playerHand, dealerHand)
        }

//        repeatBet.setOnClickListener {
//            for (marker in lastBetMarkersValue) {
//                // kod?
//            }
//            totalBet = lastBet
//            totalBetText.text = totalBet.toString()
//            startGame()
//        }


        deal.setOnClickListener {
            animations.buttonOutRightSide(deal, requireContext(), 1000L)
            playerMoney -= lastBet
            playerMoneyText.text = playerMoney.toString()
            startGame()
        }







//            playerHand.clear()
//            dealerHand.clear()
//            hitCounter = 0
//            playerCardImageViews.forEach { it.visibility = View.INVISIBLE }
//            dealerCardImageViews.forEach { it.visibility = View.INVISIBLE }
//            selectedMarkers.forEach { (_, betInfo) ->
//                betInfo.madeBetMarker.visibility = View.INVISIBLE

        hit.setOnClickListener {
            Log.d("CardPositions", "took a card position for ${card1BlankPlayer.id}: ${card1BlankPlayer.x}, ${card1BlankPlayer.y}")

            animations.shakeButton(hit)
            if (playerHitCounter == 0) {
                animations.buttonOutLeftSide(doubleDown, requireContext(), 1000L)
                playerHitCounter = drawFirstCard(playerHand, card2BlankPlayer, 35f, 70f, 100f, cardDealingOutCardsToPlayer, card1BlankPlayer, card2BlankPlayer, playerHitCounter, playerCardImageViews)
            }else if (playerHitCounter > 0){
                playerHitCounter = drawCard(playerHand, card2BlankPlayer, 35f + (35f * playerHitCounter), 70f, 100f, cardDealingOutCardsToPlayer, playerHitCounter, playerCardImageViews)
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
                Handler().postDelayed({
                    flipCard(cardDarkDealer, dealerHand)
                }, flipDealerCard)
                Handler().postDelayed({
                    animations.fadeInAndMoveUpImageView(bannerBust)
                    bannerManager.fadeOutBanner(bannerBust)
                    moveCardsToPile(bannerBust)
                    animateMarkersToWinner(winnerIsDealer = true)
                    animations.fadeOutTextView(pointsDealerText)
                    animations.fadeOutTextView(totalBetText)
                    losses++
                }, bust)
            } else if (pointsWithAceAsOne == 21 || pointsWithAceAsEleven == 21) {
                animations.buttonOutRightSide(hit, requireContext(), 1000L)
                animations.buttonOutRightSide(stand, requireContext(), 1000L)
                playerHand.updatePointsText(pointsPlayerText, true)
                Handler().postDelayed({
                    flipCard(cardDarkDealer, dealerHand)
                    checKDealerPoints(dealerHand, pointsDealerText)
                }, checkDealer)
            }
        }



        stand.setOnClickListener {
            Log.d("CardPositions", "stand position for ${card1BlankPlayer.id}: ${card1BlankPlayer.x}, ${card1BlankPlayer.y}")

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
                    }, checkDealer)
                }, flipDealerCard)

            }
        }



        repeatBet.setOnClickListener {
            animations.buttonOutRightSide(repeatBet, requireContext(), 1000L)

            resetCardPositionsCards()
            resetCardPositionsMarkers()

            var totalBetCost = 0


            for (betInfo in selectedBets) {
                betInfo.madeBetMarker.visibility = View.VISIBLE
                betInfo.count++
                totalBetCost += betInfo.count * betInfo.value

                animations.moveCard(
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

        }



        return view
    }
}







