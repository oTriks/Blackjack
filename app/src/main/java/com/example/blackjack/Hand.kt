package com.example.blackjack

class Hand {
    var cards: MutableList<Card.Card> = mutableListOf()
    fun calculatePoints(hand: List<Card.Card>): Int {
        var points = 0
        var aces = 0

        for (card in hand) {
            points += when (card.rank) {
                Card.Rank.TWO -> 2
                Card.Rank.THREE -> 3
                Card.Rank.FOUR -> 4
                Card.Rank.FIVE -> 5
                Card.Rank.SIX -> 6
                Card.Rank.SEVEN -> 7
                Card.Rank.EIGHT -> 8
                Card.Rank.NINE -> 9
                Card.Rank.TEN, Card.Rank.JACK, Card.Rank.QUEEN, Card.Rank.KING -> 10
                Card.Rank.ACE -> {
                    aces++
                    11
                }
            }
        }
        while (points > 21 && aces > 0) {
            points -= 10
            aces--
        }
        return points
    }
}