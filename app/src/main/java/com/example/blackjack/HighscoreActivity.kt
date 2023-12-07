package com.example.blackjack

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class HighscoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)

        val sharedPreferences = getSharedPreferences("myGamePreferences", Context.MODE_PRIVATE)

        val playerMoney = sharedPreferences.getInt("playerMoney", 0)
        val wins = sharedPreferences.getInt("wins", 0)
        val losses = sharedPreferences.getInt("losses", 0)
        val draws = sharedPreferences.getInt("draws", 0)
        val blackjacks = sharedPreferences.getInt("blackjacks", 0)
        val gamesPlayed = sharedPreferences.getInt("gamesPlayed", 0)
        val bust = sharedPreferences.getInt("bust", 0)
        val dealerBust = sharedPreferences.getInt("dealerBust", 0)
        val compare = sharedPreferences.getInt("compare", 0)


        findViewById<TextView>(R.id.playerMoneyText).text = "Player Money: $playerMoney"
        findViewById<TextView>(R.id.winsText).text = "Wins: $wins"
        findViewById<TextView>(R.id.lossesText).text = "Losses: $losses"
        findViewById<TextView>(R.id.drawsText).text = "Draws: $draws"
        findViewById<TextView>(R.id.blackjacksText).text = "Blackjacks: $blackjacks"
        findViewById<TextView>(R.id.compareScoreText).text = "Compared score: $compare"
        findViewById<TextView>(R.id.playerBustText).text = "Player went bust: $bust"
        findViewById<TextView>(R.id.dealerBustText).text = "Dealer went bust: $dealerBust"
        findViewById<TextView>(R.id.playedGamesText).text = "Games Played: $gamesPlayed"


    }
}