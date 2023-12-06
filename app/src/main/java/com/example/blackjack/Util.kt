package com.example.blackjack

import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.ColorRes

class Util {


    fun setTextViewBackground(textView: TextView, text: String, @ColorRes colorResId: Int) {
        textView.text = text
        val backgroundColor = textView.context.resources.getColor(colorResId, textView.context.theme) and 0x00FFFFFF or (128 shl 24)
        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.shape = GradientDrawable.RECTANGLE
        val cornerRadius = textView.resources.getDimensionPixelSize(R.dimen.background_corner_radius_more_rounded).toFloat()
        backgroundDrawable.cornerRadius = cornerRadius
        val padding = textView.resources.getDimensionPixelSize(R.dimen.background_padding)
        textView.setPadding(padding, padding, padding, padding)
        val backgroundWidth = textView.resources.getDimensionPixelSize(R.dimen.background_width)
        val backgroundHeight = textView.resources.getDimensionPixelSize(R.dimen.background_height)
        backgroundDrawable.setSize(backgroundWidth, backgroundHeight)
        backgroundDrawable.setColor(backgroundColor)
        textView.background = backgroundDrawable
        textView.gravity = Gravity.CENTER
    }

//    fun TextView.setTextViewBackground(text: String, @ColorRes colorResId: Int) {
//        this.text = text
//        val backgroundColor = context.resources.getColor(colorResId, context.theme) and 0x00FFFFFF or (128 shl 24)
//        val backgroundDrawable = GradientDrawable()
//        backgroundDrawable.shape = GradientDrawable.RECTANGLE
//        val cornerRadius = resources.getDimensionPixelSize(R.dimen.background_corner_radius_more_rounded).toFloat()
//        backgroundDrawable.cornerRadius = cornerRadius
//        val padding = resources.getDimensionPixelSize(R.dimen.background_padding)
//        setPadding(padding, padding, padding, padding)
//        val backgroundWidth = resources.getDimensionPixelSize(R.dimen.background_width)
//        val backgroundHeight = resources.getDimensionPixelSize(R.dimen.background_height)
//        backgroundDrawable.setSize(backgroundWidth, backgroundHeight)
//        backgroundDrawable.setColor(backgroundColor)
//        background = backgroundDrawable
//        gravity = Gravity.CENTER
//    }


}