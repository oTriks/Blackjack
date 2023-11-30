package com.example.blackjack

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView

class BannerManager (private val animations: Animations) {
    private val handler = Handler(Looper.getMainLooper())
    private val delayMillisShowBannerFor = 2000L



    fun fadeOutBanner(imageView: ImageView) {
        handler.postDelayed({
            animations.fadeOutImageView(imageView)
        }, delayMillisShowBannerFor)
        }
}