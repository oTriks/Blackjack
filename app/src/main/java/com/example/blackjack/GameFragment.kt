package com.example.blackjack

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class GameFragment : Fragment() {
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
        val marker25 =view.findViewById<ImageView>(R.id.marker25ImageView)
        val marker50 = view.findViewById<ImageView>(R.id.marker50ImageView)
        val marker100 = view.findViewById<ImageView>(R.id.marker100ImageView)
        val cardBlankDealer = view.findViewById<ImageView>(R.id.cardBlankDealerImageView)
        val cardDarkDealer = view.findViewById<ImageView>(R.id.cardDarkDealerImageView)
        val card1BlankPlayer = view.findViewById<ImageView>(R.id.card1BlankPlayerImageView)
        val card2BlankPlayer = view.findViewById<ImageView>(R.id.card2BlankPlayerImageView)
        val card3BlankPlayer = view.findViewById<ImageView>(R.id.card3BlankPlayerImageView)
        val card4BlankPlayer = view.findViewById<ImageView>(R.id.card4BlankPlayerImageView)
        val card5BlankPlayer = view.findViewById<ImageView>(R.id.card5BlankPlayerImageView)
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
        cardBlankDealer.visibility = View.INVISIBLE
        cardDarkDealer.visibility = View.VISIBLE
        card1BlankPlayer.visibility = View.INVISIBLE
        card2BlankPlayer.visibility = View.INVISIBLE
        card3BlankPlayer.visibility = View.INVISIBLE
        card4BlankPlayer.visibility = View.INVISIBLE
        card5BlankPlayer.visibility = View.INVISIBLE
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
        var pointsText = view.findViewById<TextView>(R.id.pointsTextView)


        fun setupMarker(marker: ImageView, madeBetMarker: ImageView, value: Int, totalBetText: TextView, dealButton: ImageButton){
            marker.setOnClickListener {
                madeBetMarker.visibility  = View.VISIBLE
                totalBet += value
                totalBetText.text = totalBet.toString()
                deal.visibility = View.VISIBLE
            }
        }
        val cardImageViews = listOf(card1BlankPlayer, card2BlankPlayer, card3BlankPlayer, card4BlankPlayer, card5BlankPlayer)
        // behöver lägga till felhantering för många kort


        setupMarker(marker5, madeBetMarker5, 5, totalBetText, deal)
        setupMarker(marker10, madeBetMarker10, 10, totalBetText, deal)
        setupMarker(marker25, madeBetMarker25, 25, totalBetText, deal)
        setupMarker(marker50, madeBetMarker50, 50, totalBetText, deal)
        setupMarker(marker100, madeBetMarker100, 100, totalBetText, deal)

        deal.setOnClickListener {
            val deck = Deck()
            val hand = Hand()
            deck.dealHand(hand.cards)
            val (pointsWithAceAsOne, pointsWithAceAsEleven) = hand.calculatePoints(hand.cards)
            val cardDisplay = CardDisplay()
            hand.cards.forEachIndexed { i, card ->
                val image = cardDisplay.getCardImage(card)
                cardImageViews[i].setImageResource(image)
                Log.d("Blackjack", "Displaying card $card at index $i")
            }
            deal.visibility = View.INVISIBLE
            card1BlankPlayer.visibility = View.VISIBLE
            card2BlankPlayer.visibility = View.VISIBLE
            hit.visibility = View.VISIBLE
            stand.visibility = View.VISIBLE
            doubleDown.visibility = View.VISIBLE

            if (pointsWithAceAsOne == pointsWithAceAsEleven || pointsWithAceAsEleven > 21) {
                pointsText.text = pointsWithAceAsOne.toString()
            } else {
                pointsText.text = "$pointsWithAceAsOne/$pointsWithAceAsEleven"
            }


//            pointsText.text = points.toString()

            var currentCardIndex = 2
            hit.setOnClickListener {
                val card = deck.cards.random()
                deck.cards.remove(card)
                hand.cards.add(card)
                val image = cardDisplay.getCardImage(card)

                cardImageViews[currentCardIndex].setImageResource(image)
                cardImageViews[currentCardIndex].visibility = View.VISIBLE
                currentCardIndex++

                val (pointsWithAceAsOne, pointsWithAceAsEleven) = hand.calculatePoints(hand.cards)
                if (pointsWithAceAsOne == pointsWithAceAsEleven || pointsWithAceAsEleven > 21) {
                    pointsText.text = pointsWithAceAsOne.toString()
                } else {
                    pointsText.text = "$pointsWithAceAsOne/$pointsWithAceAsEleven"
                }


            }


        }
        return view
    }
}