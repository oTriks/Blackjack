package com.example.blackjack

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class HighscoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val wins = sharedPreferences.getInt("wins", 0)
        val losses = sharedPreferences.getInt("losses", 0)
        val blackjacks = sharedPreferences.getInt("blackjacks", 0)
        val gamesPlayed = sharedPreferences.getInt("gamesPlayed", 0)

//        val winsTextView: TextView = findViewById(R.id.winsTextView)
//        val lossesTextView: TextView = findViewById(R.id.lossesTextView)
//        val blackjacksTextView: TextView = findViewById(R.id.blackjacksTextView)
//        val gamesPlayedTextView: TextView = findViewById(R.id.gamesPlayedTextView)

//        winsTextView.text = "Wins: $wins"
//        lossesTextView.text = "Losses: $losses"
//        blackjacksTextView.text = "Blackjacks: $blackjacks"
//        gamesPlayedTextView.text = "Games Played: $gamesPlayed"

    }
}