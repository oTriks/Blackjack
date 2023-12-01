package com.example.blackjack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val playButton = findViewById<ImageButton>(R.id.playImageButton)
        val highscoreButton = findViewById<ImageButton>(R.id.highscoreImageButton)
        playButton.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.insuranceImageView, GameFragment())
            transaction.commit()
        }
        highscoreButton.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.insuranceImageView, GameFragment())
            transaction.commit()
        }

    }
}