package com.example.blackjack

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.ChangeBounds
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

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
        val cardDealingOutCardsToDealer =
            view.findViewById<ImageView>(R.id.cardDealingOutCardsToDealerImageView)
        val cardDealingOutCardsToPlayer =
            view.findViewById<ImageView>(R.id.cardDealingOutCardsToPlayerImageView)
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


        cardBlankDealer.visibility = View.INVISIBLE
        cardNextCardDealer.visibility = View.INVISIBLE
        cardDarkDealer.visibility = View.INVISIBLE
        cardDealingOutCardsToDealer.visibility = View.INVISIBLE
        cardDealingOutCardsToPlayer.visibility = View.INVISIBLE
        card1BlankPlayer.visibility = View.INVISIBLE
        card2BlankPlayer.visibility = View.INVISIBLE
        cardNextCard.visibility = View.INVISIBLE
        cardPile.visibility = View.INVISIBLE
        totalBetText.visibility = View.INVISIBLE
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
        var totalBet = 0
        var highscore = 0
        val playerCardImageViews: MutableList<ImageView> =
            mutableListOf(card1BlankPlayer, card2BlankPlayer, cardNextCard)
        val dealerCardImageViews: MutableList<ImageView> =
            mutableListOf(cardBlankDealer, cardDarkDealer, cardNextCardDealer)
        val cardDisplay = CardDisplay()
        val playerHand = Hand()
        val dealerHand = Hand()
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
//        var lastBetMarkers = mutableListOf<ImageView>()
        var lastBetMarkers = mutableListOf<Int>()

        var lastBet = 0
        var winner = ""
        val handler = Handler(Looper.getMainLooper())
        val delayMillisCardsToPile = 2000L
        val delayMillisPile = 2500L
        val delayMillisBanner = 2800L
        val textSwitcher = view.findViewById<TextSwitcher>(R.id.textSwitcher)
        var playerMoney = 500
        playerMoneyText.text = playerMoney.toString()


        val defaultCard1BlankPlayerParams = card1BlankPlayer.layoutParams as ConstraintLayout.LayoutParams
        val defaultCard2BlankPlayerParams = card2BlankPlayer.layoutParams as ConstraintLayout.LayoutParams
        val defaultCardNextCardParams = cardNextCard.layoutParams as ConstraintLayout.LayoutParams
        val initialXCard1BlankPlayer = card2BlankPlayer.x
        val initialYCard1BlankPlayer = card1BlankPlayer.y
        val initialXCard2BlankPlayer = card2BlankPlayer.x
        val initialYCard2BlankPlayer = card2BlankPlayer.y
        val initialXCardNextCardDealer = cardNextCardDealer.x
        val initialYCardNextCardDealer = cardNextCardDealer.y
        val initialXCardBlankDealer = cardBlankDealer.x
        val initialYCardBlankDealer = cardBlankDealer.y
        val initialXCardDarkDealer = cardDarkDealer.x
        val initialYCardDarkDealer = cardDarkDealer.y





        fun animationCardsToPile(cardImageViews: List<ImageView>, cardPile: ImageView) {
            val cardPileX = cardPile.x
            val cardPileY = cardPile.y
            cardImageViews.forEachIndexed { index, cardImageView ->
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
            dealerCardImageViews.forEachIndexed { index, cardImageView ->
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
        }

        fun moveCardsToPile(banner: View) {
            handler.postDelayed({
                animationCardsToPile(playerCardImageViews, cardPile)
            }, delayMillisCardsToPile)

            handler.postDelayed({
                cardPile.visibility = View.VISIBLE
            }, delayMillisPile)

            handler.postDelayed({
                banner.visibility = View.INVISIBLE   // not working??
            }, delayMillisBanner)
        }


        fun emptyBoard() {
            pointsPlayerText.visibility = View.INVISIBLE
            pointsDealerText.visibility = View.INVISIBLE
            totalBetText.visibility = View.INVISIBLE
            hit.visibility = View.INVISIBLE
            stand.visibility = View.INVISIBLE

        }

        fun gameEnd() {
            emptyBoard()

//            if (winner == "player") {
//                highscore += totalBet //
//            } else if (winner == "dealer") {
//                highscore -= totalBet
//            }
//            lastBetMarkers.forEach { it.visibility = View.INVISIBLE }
//            animations.buttonInRightSide(repeatBet, requireContext(), 1000L) // cant have like this cause of when hit deal button

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
            return when {   // fixa logic ess är värt 11, fixa logic om man får 21 på flera kort
                playerPoints > 21 -> "Dealer"
                dealerPoints > 21 -> "Player"
                playerPoints > dealerPoints -> "Player"
                dealerPoints > playerPoints -> "Dealer"
                else -> "Draw"
            }
        }



        fun flipCard(imageView: ImageView, hand: Hand) {
            val delayRevealCardWhenFlip = 250L
            animations.flipCard(imageView)
            Handler().postDelayed({
                updateDealerCards(hand)
            }, delayRevealCardWhenFlip)
            hand.revealHiddenCard()
        }
        fun flipCardTense(imageView: ImageView, hand: Hand) {
            val delayShakeCard = 1200L
            val delayRevealCard = 3000L
            Handler().postDelayed({
                animations.shakeCardSubtle(cardDarkDealer)
            }, delayShakeCard)
            Handler().postDelayed({
                flipCard(cardDarkDealer, dealerHand)
            }, delayRevealCard)
            hand.revealHiddenCard()
        }


//        fun drawCardDealer(specificCard: Card.PlayingCard? = null) {
//            deck.drawCard(dealerHand.cards, specificCard)
fun drawCardDealer() {  // SKA HA DETTA, ANDRA TEST
    deck.drawCard(dealerHand.cards)  // SKA HA DETTA, ANDRA TEST
//            drawCardDealer(Card.PlayingCard(Card.Suit.SPADES, Card.Rank.TEN))
    val newCard = dealerHand.cards.last()
    val newCardImageView = ImageView(context)
    newCardImageView.setImageResource(cardDisplay.getCardImage(newCard))

    Log.d("BlackJack", "dealerDrawnCard?: $newCard")

    val offsetDp = 15f
    val density = resources.displayMetrics.density
    val offsetPx = (offsetDp * density).toInt()
    val cardDarkDealerEndX = cardDarkDealer.x + offsetPx
    val cardBlankDealerEndX = cardBlankDealer.x + offsetPx

    animations.moveCard(cardDarkDealer, cardDarkDealer.x, cardDarkDealer.y, cardBlankDealerEndX, cardBlankDealer.y, 500L, 0L)

    deck.performDealingAnimation(cardNextCardDealer, cardDealingOutCardsToDealer.x, cardDealingOutCardsToDealer.y, cardDarkDealerEndX, cardDarkDealer.y, 0f, 180f)

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
        fun determineWinner(playerHand: Hand, dealerHand: Hand) {
            Log.d("BlackJack", "Entering determineWinner")
            val playerPoints = playerHand.calculatePoints(playerHand.cards)
            val winner = compareHands(playerPoints.first, dealerHand.calculatePoints(dealerHand.cards).first)

            when (winner) {
                "Player" -> {
                    Log.d("BlackJack", "Entering Player case")
                    Log.d("BlackJack", "PlayerMoney before added money: $playerMoney") // Add this line for debugging
                    repeatBet.visibility = View.VISIBLE
                    bannerWin.visibility = View.VISIBLE
                    moveCardsToPile(bannerWin)
                    playerMoney += 2* totalBet
                    playerMoneyText.text = playerMoney.toString()
                    Log.d("BlackJack", "PlayerMoney after added money: $playerMoney") // Add this line for debugging

                }
                "Dealer" -> {
                    Log.d("BlackJack", "Entering Dealer case")
                    repeatBet.visibility = View.VISIBLE
                    bannerLose.visibility = View.VISIBLE
                    moveCardsToPile(bannerLose)
                }
                "Draw" -> {
                    Log.d("BlackJack", "Entering Draw case")
                    repeatBet.visibility = View.VISIBLE
                    bannerSplit.visibility = View.VISIBLE
                    moveCardsToPile(bannerSplit)
                    playerMoney += totalBet
                    playerMoneyText.text = playerMoney.toString()
                }
            }
            gameEnd()
            Log.d("BlackJack", "Exiting determineWinner")
        }

        fun Hand.updatePointsText(textView: TextView, hasPlayerStood: Boolean) {
            val pointsWithAceAsOne = calculatePoints(cards).first
            val pointsWithAceAsEleven = calculatePoints(cards).second
            val pointsText =
                if (pointsWithAceAsOne == pointsWithAceAsEleven || pointsWithAceAsEleven > 21) {
                    pointsWithAceAsOne.toString()
                }else if (hasPlayerStood){
                    "$pointsWithAceAsEleven"
                }
                else {
                    "$pointsWithAceAsOne/$pointsWithAceAsEleven"
                }
            textView.text = pointsText
        }

        fun checKDealerPoints(dealerHand: Hand, dealerPointsText: TextView) {
            val (pointsWithAceAsOne, pointsWithAceAsEleven) = dealerHand.calculatePoints(dealerHand.cards)
            when {
                pointsWithAceAsEleven > 21 && pointsWithAceAsOne > 21 -> {
                    dealerPointsText.text = pointsWithAceAsOne.toString()
                    Log.d("BlackJack", "case1?: $playerMoney")
                }
                pointsWithAceAsEleven > 21 -> {
                    // Only points with Ace as eleven are over 21
                    dealerPointsText.text = pointsWithAceAsOne.toString()
                    Log.d("BlackJack", "case2?: $playerMoney")
                }
                pointsWithAceAsEleven in 17..21 -> {
                    dealerPointsText.text = pointsWithAceAsEleven.toString()
                    Log.d("BlackJack", "case3?: $playerMoney")
                }
                pointsWithAceAsEleven < 17  && pointsWithAceAsOne < 17 -> {
                    Log.d("BlackJack", "case4?: $playerMoney")
                    do {
                        drawCardDealer()
                        Log.d("BlackJack", "Draw?: $playerMoney")
                        dealerHand.updatePointsText(pointsDealerText, false)
                        Log.d("BlackJack", "Drawing card until dealer reaches 17 or more: $playerMoney")
                    } while (dealerHand.calculatePoints(dealerHand.cards).second < 17)
                }
            }
            determineWinner(playerHand, dealerHand)
        }




        fun setupMarker(
            marker: ImageView,
            madeBetMarker: ImageView,
            value: Int,
            totalBetText: TextView,
            dealButton: ImageButton
        ) {
            marker.setOnClickListener {
                lastBetMarkers.add(value)
                madeBetMarker.visibility = View.VISIBLE
                totalBetText.visibility = View.VISIBLE
//                lastBet = value
                lastBet = lastBetMarkers.sum()

                animations.moveCard(
                    madeBetMarker,
                    marker.x,
                    marker.y,
                    madeBetMarker.x,
                    madeBetMarker.y,
                    500L,
                    0L
                )

                totalBet = lastBetMarkers.sum()
                totalBetText.text = totalBet.toString()
                animations.buttonInRightSide(deal, requireContext(), 1000L)
                deal.visibility = View.VISIBLE
            }
        }


        setupMarker(marker5, madeBetMarker5, 5, totalBetText, deal)
        setupMarker(marker10, madeBetMarker10, 10, totalBetText, deal)
        setupMarker(marker25, madeBetMarker25, 25, totalBetText, deal)
        setupMarker(marker50, madeBetMarker50, 50, totalBetText, deal)
        setupMarker(marker100, madeBetMarker100, 100, totalBetText, deal)





        fun checkForBlackjack(playerHand: Hand, dealerHand: Hand) {
            val delayBlackjackPlayerBanner = 1800L
            val delayBannerSplit = 700L
            val delayRemoveBannersBlackjack = 2000L
            val delayCheckDealersCards = 3000L
            val delayBlackjackDealer = 500L
            val delayButtonsContinue = 1200L

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
                                }, delayBannerSplit)
                                Handler().postDelayed({
                                    animations.removeImage(bannerBlackjackDealerSplit)
                                    animations.removeImage(bannerBlackjackPlayerSplit)
                                }, delayRemoveBannersBlackjack)
                            } else {
                                animations.fadeInAndMoveUpImageView(bannerWin)
                                animations.removeImage(bannerBlackjackPlayerSplit)
                                bannerManager.fadeOutBanner(bannerWin)
                                moveCardsToPile(bannerWin)
                            }
                        }, delayBlackjackDealer)
                    }, delayCheckDealersCards)
                    gameEnd()
                }, delayBlackjackPlayerBanner)
                Handler().postDelayed({
                    moveCardsToPile(bannerBlackjack)
                }, 5300)
                gameEnd()
            } else if (!playerHand.isBlackjack()) {
                Handler().postDelayed({
                    animations.buttonInRightSide(hit, requireContext(), 1000L)
                    animations.buttonInRightSide(stand, requireContext(), 1000L)
                    playerHand.updatePointsText(pointsPlayerText, false)
                    animations.fadeInTextView(pointsPlayerText)
                    dealerHand.updatePointsText(pointsDealerText, false)
                    animations.fadeInTextView(pointsDealerText)
                }, delayButtonsContinue)
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




        fun insurance(){
            animations.buttonOutLeftSide(yes, requireContext(), 1000L)
            animations.buttonOutRightSide(no,requireContext(), 1000L)
            animations.removeImage(insurance)
            flipCardTense(cardDarkDealer, dealerHand)
            if (dealerHand.isBlackjack()){
                flipCardTense(cardDarkDealer, dealerHand)
                Handler().postDelayed({
                    dealerHand.revealHiddenCard()
                    animations.fadeInImageView(bannerBlackjackDealerSplit)
                    animations.fadeInAndMoveUpImageView(bannerLose)
                    bannerManager.fadeOutBanner(bannerLose)
                    moveCardsToPile(bannerLose)
                }, 3700L)
                Handler().postDelayed({
                    animations.removeImage(bannerBlackjackDealerSplit)
                }, 4700)
            } else{
                checKDealerPoints(dealerHand, pointsDealerText)
            }

        }


        fun startGame() {
//            lastBetMarkers.clear()
            deck.dealHand(playerHand, dealerHand)
            updateDealerCards(dealerHand)           // vad gör denna?
            updateCardImages(playerHand, playerCardImageViews, cardDisplay)
            updateCardImages(dealerHand, dealerCardImageViews, cardDisplay)
            checkForBlackjack(playerHand, dealerHand)
        }


        repeatBet.setOnClickListener {
            for (marker in lastBetMarkers) {
//                marker.visibility = View.VISIBLE
            }
            totalBet = lastBet
            totalBetText.text = totalBet.toString()
            startGame()
        }



        deal.setOnClickListener {
            Log.d("BlackJack", "Player Money before deduction: $playerMoney")

            animations.buttonOutRightSide(deal, requireContext(), 1000L)
            playerMoney -= lastBet
            Log.d("BlackJack", "Player Money after deduction: $playerMoney") // Add this line for debugging

            playerMoneyText.text = playerMoney.toString()
            startGame()
        }


        hit.setOnClickListener {

            animations.shakeButton(hit)
            deck.drawCard(playerHand.cards)     // generate random card
            val newCard = playerHand.cards.last()      // works together to set card picture
            val image = cardDisplay.getCardImage(newCard)    // works together to set card picture
            val transition = ChangeBounds()  //används inte?
            transition.duration = 3000  // används inte?
            val newConstraintLayout = ConstraintLayout(view.context)             // bakgrund ?
            newConstraintLayout.layoutParams = constraintlayout.layoutParams     //  bakgrund?

            cardNextCard.visibility = View.VISIBLE
            cardNextCard.setImageResource(image)   // works together to set card picture
            cardNextCard.id = View.generateViewId()




            val offsetDp = 35f
            val density = resources.displayMetrics.density
            val offsetPx = (offsetDp * density).toInt()
            val card1BlankPlayerEndX = card1BlankPlayer.x + offsetPx
            val card2BlankPlayerEndX = card2BlankPlayer.x + offsetPx

            deck.performDealingAnimation(cardNextCard, cardDealingOutCardsToPlayer.x, cardDealingOutCardsToPlayer.y, card2BlankPlayerEndX, card2BlankPlayer.y, 0f, 180f)

            val card1Animator = ObjectAnimator.ofFloat(card2BlankPlayer, View.X, card2BlankPlayer.x, card1BlankPlayerEndX)
            card1Animator.interpolator = AccelerateDecelerateInterpolator()
            card1Animator.duration = 500L

            val card2Animator = ObjectAnimator.ofFloat(cardNextCard, View.X, cardDealingOutCardsToPlayer.x, card2BlankPlayerEndX)
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
                animations.buttonOutRightSide(hit, requireContext(), 1000L)
                animations.buttonOutRightSide(stand, requireContext(), 1000L)
                animations.fadeInAndMoveUpImageView(bannerBust)
                bannerManager.fadeOutBanner(bannerBust)
                 moveCardsToPile(bannerBust)

            }
        }


        fun resetCardPositions() {
//            card1BlankPlayer.layoutParams = defaultCard1BlankPlayerParams
//            card2BlankPlayer.layoutParams = defaultCard2BlankPlayerParams
//            cardNextCard.layoutParams = defaultCardNextCardParams
            card1BlankPlayer.translationX = 0f
            card1BlankPlayer.translationY = 0f
            card2BlankPlayer.translationX = 0f
            card2BlankPlayer.translationY = 0f
            cardNextCard.translationX = 0f
            cardNextCard.translationY = 0f

            cardNextCardDealer.translationX = 0f
            cardNextCardDealer.translationY = 0f
            cardBlankDealer.translationX = 0f
            cardBlankDealer.translationY = 0f
            cardDarkDealer.translationX = 0f
            cardDarkDealer.translationY = 0f






        }

        stand.setOnClickListener {
            dealerHand.updatePointsText(pointsDealerText, false)

            playerHand.updatePointsText(pointsPlayerText, true)

            animations.buttonOutRightSide(hit, requireContext(), 1000L)
            animations.buttonOutRightSide(stand, requireContext(), 1000L)
            if (dealerGotAce()){
                animations.fadeInImageButton(insurance)
                animations.buttonInLeftSide(yes, requireContext(), 1000L)
                animations.buttonInRightSide(no, requireContext(), 1000L)
                yes.setOnClickListener {
                    insurance()
//                    val insuranceAmount = totalBet / 2               // add when got coins
//                    totalBet -= insuranceAmount                       // add when got coins
//                    totalBetText.text = totalBet.toString()           // add when got coins
                }
                no.setOnClickListener {
                    insurance()
                }
            }else {
                flipCard(cardDarkDealer, dealerHand)
                checKDealerPoints(dealerHand, pointsDealerText)


            }
    }



            repeatBet.setOnClickListener {
                resetCardPositions()
                totalBet = lastBetMarkers.sum()
                totalBetText.text = totalBet.toString()
                for (markerResourceId in lastBetMarkers) {
                    val markerImageView = view.findViewById<ImageView>(markerResourceId)
                    markerImageView.visibility = View.VISIBLE
                }
                startGame()


//                for (marker in lastBetMarkers) {
//                    marker.visibility = View.VISIBLE
//                    totalBet = lastBet
//                    // remove constraints / default constraints for cards
//                    startGame()
//                }
            }



        return view
}
    }







