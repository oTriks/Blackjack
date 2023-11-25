package com.example.blackjack

class CardDisplay {
    val heartsImages = mapOf(
        Card.Rank.TWO to R.drawable.card_hearts_2, Card.Rank.THREE to R.drawable.card_hearts_3, Card.Rank.FOUR to R.drawable.card_hearts_4, Card.Rank.FIVE to R.drawable.card_hearts_5, Card.Rank.SIX to R.drawable.card_hearts_6, Card.Rank.SEVEN to R.drawable.card_hearts_7, Card.Rank.EIGHT to R.drawable.card_hearts_8, Card.Rank.NINE to R.drawable.card_hearts_9, Card.Rank.TEN to R.drawable.card_hearts_10, Card.Rank.JACK to R.drawable.card_hearts_j, Card.Rank.QUEEN to R.drawable.card_hearts_q, Card.Rank.KING to R.drawable.card_hearts_k, Card.Rank.ACE to R.drawable.card_hearts_a, Card.Rank.HIDDEN to R.drawable.card_dark
    )
    val diamondsImages = mapOf(
        Card.Rank.TWO to R.drawable.card_diamonds_2, Card.Rank.THREE to R.drawable.card_diamonds_3, Card.Rank.FOUR to R.drawable.card_diamonds_4, Card.Rank.FIVE to R.drawable.card_diamonds_5, Card.Rank.SIX to R.drawable.card_diamonds_6, Card.Rank.SEVEN to R.drawable.card_diamonds_7, Card.Rank.EIGHT to R.drawable.card_diamonds_8, Card.Rank.NINE to R.drawable.card_diamonds_9, Card.Rank.TEN to R.drawable.card_diamonds_10, Card.Rank.JACK to R.drawable.card_diamonds_j, Card.Rank.QUEEN to R.drawable.card_diamonds_q, Card.Rank.KING to R.drawable.card_diamonds_k, Card.Rank.ACE to R.drawable.card_diamonds_a
    )
    val spadesImages = mapOf(
        Card.Rank.TWO to R.drawable.card_spades_2, Card.Rank.THREE to R.drawable.card_spades_3, Card.Rank.FOUR to R.drawable.card_spades_4, Card.Rank.FIVE to R.drawable.card_spades_5, Card.Rank.SIX to R.drawable.card_spades_6, Card.Rank.SEVEN to R.drawable.card_spades_7, Card.Rank.EIGHT to R.drawable.card_spades_8, Card.Rank.NINE to R.drawable.card_spades_9, Card.Rank.TEN to R.drawable.card_spades_10, Card.Rank.JACK to R.drawable.card_spades_j, Card.Rank.QUEEN to R.drawable.card_spades_q, Card.Rank.KING to R.drawable.card_spades_k, Card.Rank.ACE to R.drawable.card_spades_a
    )
    val clubsImages = mapOf(
        Card.Rank.TWO to R.drawable.card_clubs_2, Card.Rank.THREE to R.drawable.card_clubs_3, Card.Rank.FOUR to R.drawable.card_clubs_4, Card.Rank.FIVE to R.drawable.card_clubs_5, Card.Rank.SIX to R.drawable.card_clubs_6, Card.Rank.SEVEN to R.drawable.card_clubs_7, Card.Rank.EIGHT to R.drawable.card_clubs_8, Card.Rank.NINE to R.drawable.card_clubs_9, Card.Rank.TEN to R.drawable.card_clubs_10, Card.Rank.JACK to R.drawable.card_clubs_j, Card.Rank.QUEEN to R.drawable.card_clubs_q, Card.Rank.KING to R.drawable.card_clubs_k, Card.Rank.ACE to R.drawable.card_clubs_a
    )
    val cardImages = mapOf(
        Card.Suit.HEARTS to heartsImages,
        Card.Suit.DIAMONDS to diamondsImages,
        Card.Suit.SPADES to spadesImages,
        Card.Suit.CLUBS to clubsImages
    )
    fun getCardImage(card: Card.Card): Int {
        return cardImages[card.suit]?.get(card.rank) ?: R.drawable.card_blank
    }



}