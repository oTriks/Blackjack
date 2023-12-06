package com.example.blackjack

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
import android.widget.TextView

class Animations {

    fun moveObject(
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

    fun animateCardToPile(cardImageView: ImageView, cardPile: ImageView, index: Int, context: Context) {
        val cardPileX = cardPile.x
        val cardPileY = cardPile.y
        moveObject(
            cardImageView,
            cardImageView.x,
            cardImageView.y,
            cardPileX,
            cardPileY,
            500L,
            index * 200L
        )
        cardImageView.setImageResource(context.resources.getIdentifier("card_dark", "drawable", context.packageName))
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
        fadeIn.duration = 1000
        imageView.startAnimation(fadeIn)
        imageView.visibility = View.VISIBLE
    }
    fun fadeInImageButton (imageButton: ImageButton) {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 1000
        imageButton.startAnimation(fadeIn)
        imageButton.visibility = View.VISIBLE
    }

    fun fadeInTextView(textView: TextView) {
        if (textView.visibility == View.INVISIBLE) {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 1000
        textView.startAnimation(fadeIn)
        textView.visibility = View.VISIBLE
        }
    }

    fun fadeOutTextView(textView: TextView) {
        if (textView.visibility == View.VISIBLE) {
            val fadeOut = AlphaAnimation(1f, 0f)
            fadeOut.duration = 1000
            textView.startAnimation(fadeOut)

            fadeOut.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    // Optional: Code to run when animation starts
                }

                override fun onAnimationEnd(animation: Animation?) {
                    textView.visibility = View.INVISIBLE // or View.GONE
                }

                override fun onAnimationRepeat(animation: Animation?) {
                    // Optional: Code to run when animation repeats
                }
            })
        }
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

    fun fadeOutMarkersImageView(imageView: ImageView, duration: Long = 200) {
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
        if (imageButton.visibility == View.INVISIBLE) {
            val screenWidth = context.resources.displayMetrics.widthPixels
            val distanceToMoveIn = 10 * context.resources.displayMetrics.density

            val startOffset = screenWidth + distanceToMoveIn
            val endOffset = 0f

            val translateAnimation = TranslateAnimation(startOffset, endOffset, 0f, 0f)
            translateAnimation.duration = duration
            imageButton.startAnimation(translateAnimation)
            imageButton.visibility = View.VISIBLE
        }
    }
    fun buttonOutRightSide(imageButton: ImageButton, context: Context, duration: Long) {
        if (imageButton.visibility == View.VISIBLE) {
            val screenWidth = context.resources.displayMetrics.widthPixels
            val distanceToMoveOut = 10 * context.resources.displayMetrics.density

            val startOffset = 0f
            val endOffset = screenWidth + distanceToMoveOut

            val translateAnimation = TranslateAnimation(startOffset, endOffset, 0f, 0f)
            translateAnimation.duration = duration
            imageButton.startAnimation(translateAnimation)
            imageButton.visibility = View.INVISIBLE
        }
    }

    fun buttonInLeftSide(imageButton: ImageButton, context: Context, duration: Long) {
        if (imageButton.visibility == View.INVISIBLE) {
            val screenWidth = context.resources.displayMetrics.widthPixels
            val distanceToMoveIn = 10 * context.resources.displayMetrics.density

            val startOffset = -(screenWidth + distanceToMoveIn)
            val endOffset = 0f

            val translateAnimation = TranslateAnimation(startOffset, endOffset, 0f, 0f)
            translateAnimation.duration = duration
            imageButton.startAnimation(translateAnimation)
            imageButton.visibility = View.VISIBLE
        }
    }

fun buttonOutLeftSide(imageButton: ImageButton, context: Context, duration: Long) {
    if (imageButton.visibility == View.VISIBLE) {
        val screenWidth = context.resources.displayMetrics.widthPixels
        val distanceToMoveOut = 10 * context.resources.displayMetrics.density

        val startOffset = 0f
        val endOffset = -screenWidth - distanceToMoveOut

        val translateAnimation = TranslateAnimation(startOffset, endOffset, 0f, 0f)
        translateAnimation.duration = duration
        imageButton.startAnimation(translateAnimation)
        imageButton.visibility = View.INVISIBLE
    }
}


    fun shakeCardSubtle(imageView: ImageView) {
        val shakeAnimation = ObjectAnimator.ofFloat(imageView, "translationX", 0f, -5f, 5f, -3f, 3f, -2f, 2f, 0f)
        shakeAnimation.duration = 2000
        shakeAnimation.interpolator = CycleInterpolator(1f)
        shakeAnimation.start()
    }

    fun shakeCardTense(imageView: ImageView) {
        val shakeAnimation = ObjectAnimator.ofFloat(imageView, "translationX", 0f, -2f, 3f, -3f, 3f, -2f, 2f, 0f)
        shakeAnimation.duration = 2000
        shakeAnimation.interpolator = CycleInterpolator(1f)
        shakeAnimation.start()
    }


    fun shakeButton(imageButton: ImageButton) {
        val shakeAnimation = ObjectAnimator.ofFloat(imageButton, "translationX", 0f, -3f, 3f, -3f, 3f, -2f, 2f, 0f)
        shakeAnimation.duration = 200
        shakeAnimation.interpolator = CycleInterpolator(1f)
        shakeAnimation.start()
    }
    fun shakeTextSubtle(textView: TextView) {
        val shakeAnimation = ObjectAnimator.ofFloat(textView, "translationX", 0f, -2f, 3f, -3f, 3f, -2f, 2f, 0f)
        shakeAnimation.duration = 200
        shakeAnimation.interpolator = CycleInterpolator(1f)
        shakeAnimation.start()
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



    fun removeImage (imageView: ImageView) {
        val scaleAnimation = ScaleAnimation(
            1f, 1f, // X-axis scaling from 1 to 1 (no change)
            1f, 0f, // Y-axis scaling from 1 to 0 (shrinking vertically)
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point: X-axis center
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot point: Y-axis center
        )
        scaleAnimation.duration = 500 // Adjust the duration as needed

        val moveUp = TranslateAnimation(0f, 0f, 0f, -imageView.height.toFloat())
        moveUp.duration = 500 // Adjust the duration as needed
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(scaleAnimation)
        animationSet.addAnimation(moveUp)

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