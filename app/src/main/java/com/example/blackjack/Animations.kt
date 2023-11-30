package com.example.blackjack

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.CycleInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.ImageButton
import android.widget.ImageView

class Animations {

    fun moveCard(
        cardImageView: ImageView,
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        duration: Long,
        delay: Long
    ) {
        val animatorX = ObjectAnimator.ofFloat(cardImageView, View.X, startX, endX)
        animatorX.interpolator = AccelerateDecelerateInterpolator()
        animatorX.duration = duration
        animatorX.startDelay = delay
        animatorX.start()

        val animatorY = ObjectAnimator.ofFloat(cardImageView, View.Y, startY, endY)
        animatorY.interpolator = AccelerateDecelerateInterpolator()
        animatorY.duration = duration
        animatorY.startDelay = delay
        animatorY.start()
    }




    fun flipCard(cardImageView: ImageView) {
        val animator1 = ObjectAnimator.ofFloat(cardImageView, View.ROTATION_Y, 0f, 180f)
        animator1.duration = 500

        val set = AnimatorSet()
        set.playSequentially(animator1)

        set.start()
    }

    fun fadeInImageView (imageView: ImageView) {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 1000 // Adjust the duration as needed
        imageView.startAnimation(fadeIn)
    }

    fun fadeOutImageView(imageView: ImageView, duration: Long = 1000) {
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = DecelerateInterpolator()
        fadeOut.duration = duration

        val animationSet = AnimationSet(false)
        animationSet.addAnimation(fadeOut)
        imageView.startAnimation(animationSet)
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                imageView.visibility = View.INVISIBLE
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
    fun buttonInRightSide(imageButton: ImageButton, context: Context, duration: Long) {

        val screenWidth = context.resources.displayMetrics.widthPixels
        val distanceToMoveIn = 10 * context.resources.displayMetrics.density // 10 cm in dp

        val startOffset = screenWidth + distanceToMoveIn
        val endOffset = 0f

        val translateAnimation = TranslateAnimation(startOffset, endOffset, 0f, 0f)
        translateAnimation.duration = duration
        imageButton.startAnimation(translateAnimation)
        imageButton.visibility = View.VISIBLE

    }
    fun buttonOutRightSide(imageButton: ImageButton, context: Context, duration: Long) {
        val screenWidth = context.resources.displayMetrics.widthPixels
        val distanceToMoveOut = 10 * context.resources.displayMetrics.density // 10 cm in dp

        val startOffset = 0f
        val endOffset = screenWidth + distanceToMoveOut

        val translateAnimation = TranslateAnimation(startOffset, endOffset, 0f, 0f)
        translateAnimation.duration = duration
        imageButton.startAnimation(translateAnimation)
        imageButton.visibility = View.INVISIBLE
    }

    fun buttonInLeftSide(imageButton: ImageButton, context: Context, duration: Long) {
        val screenWidth = context.resources.displayMetrics.widthPixels
        val distanceToMoveIn = 10 * context.resources.displayMetrics.density // 10 cm in dp

        val startOffset = -distanceToMoveIn
        val endOffset = 0f

        val translateAnimation = TranslateAnimation(startOffset, endOffset, 0f, 0f)
        translateAnimation.duration = duration
        imageButton.startAnimation(translateAnimation)
        imageButton.visibility = View.VISIBLE

    }
fun buttonOutLeftSide(imageButton: ImageButton, context: Context, duration: Long) {
    val screenWidth = context.resources.displayMetrics.widthPixels
    val distanceToMoveOut = 10 * context.resources.displayMetrics.density // 10 cm in dp

    val startOffset = 0f
    val endOffset = -screenWidth - distanceToMoveOut // Adjusted for moving out to the left

    val translateAnimation = TranslateAnimation(startOffset, endOffset, 0f, 0f)
    translateAnimation.duration = duration
    imageButton.startAnimation(translateAnimation)
    imageButton.visibility = View.INVISIBLE

}


    fun shakeCardSubtle(imageView: ImageView) {
        val shakeAnimation = ObjectAnimator.ofFloat(imageView, "translationX", 0f, -5f, 5f, -3f, 3f, -2f, 2f, 0f)
        shakeAnimation.duration = 2000
        shakeAnimation.interpolator = CycleInterpolator(1f) // Adjust the cycle count for more or fewer shakes
        shakeAnimation.start()
    }

    fun shakeButton(imageButton: ImageButton) {
        val shakeAnimation = ObjectAnimator.ofFloat(imageButton, "translationX", 0f, -3f, 3f, -3f, 3f, -2f, 2f, 0f)
        shakeAnimation.duration = 200
        shakeAnimation.interpolator = CycleInterpolator(1f) // Adjust the cycle count for more or fewer shakes
        shakeAnimation.start()
    }

    fun moveImageViewUp (imageView: ImageView) {
        val distanceToMoveUp = -130f

        val moveUp = TranslateAnimation(0f, 0f, 0f, distanceToMoveUp)
        moveUp.duration = 1000

        moveUp.fillAfter = true
        imageView.startAnimation(moveUp)
    }

    fun fadeInAndMoveUpImageView(imageView: ImageView) {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 1000

        val distanceToMoveUp = -130f
        val moveUp = TranslateAnimation(0f, 0f, 0f, distanceToMoveUp)
        moveUp.duration = 1000


        val animationSet = AnimationSet(true)
        animationSet.addAnimation(fadeIn)
        animationSet.addAnimation(moveUp)
        animationSet.fillAfter = true

        imageView.startAnimation(animationSet)
    }







    fun animateImageDisappear (imageView: ImageView) {
        val scaleAnimation = ScaleAnimation(
            1f, 1f, // X-axis scaling from 1 to 1 (no change)
            1f, 0f, // Y-axis scaling from 1 to 0 (shrinking vertically)
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point: X-axis center
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot point: Y-axis center
        )
        scaleAnimation.duration = 500 // Adjust the duration as needed

        val moveUp = TranslateAnimation(0f, 0f, 0f, -imageView.height.toFloat())
        moveUp.duration = 500 // Adjust the duration as needed

        // Combine both animations into an AnimationSet
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(scaleAnimation)
        animationSet.addAnimation(moveUp)

        // Ensure the image stays invisible after the animation
        animationSet.fillAfter = true

        imageView.startAnimation(animationSet)


        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                imageView.visibility = View.INVISIBLE
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }







}