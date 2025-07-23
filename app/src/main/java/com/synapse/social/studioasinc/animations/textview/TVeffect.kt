package com.synapse.social.studioasinc.animations.textview

// Coded by Ashik from StudioAs Inc.

import android.animation.ValueAnimator
import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TVeffects @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    private var charDelay: Long = 50L
    private var fadeDuration: Long = 200L
    private var totalDuration: Long = 0L  // if > 0, use totalDuration instead of charDelay

    // Setter methods to control timing from code
    fun setCharDelay(delay: Long) {
        charDelay = delay
    }

    fun setFadeDuration(duration: Long) {
        fadeDuration = duration
    }

    fun setTotalDuration(duration: Long) {
        totalDuration = duration
    }

    fun startTyping(text: CharSequence) {
        val delay = if (totalDuration > 0 && text.isNotEmpty()) totalDuration / text.length else charDelay
        startTyping(text, delay, fadeDuration)
    }

    private fun startTyping(text: CharSequence, charDelay: Long, fadeDuration: Long) {
        val spannable = SpannableStringBuilder(text)
        this.text = spannable

        for (i in text.indices) {
            val span = AlphaSpan(0f)
            spannable.setSpan(span, i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            ValueAnimator.ofFloat(0f, 1f).apply {
                startDelay = i * charDelay
                duration = fadeDuration
                addUpdateListener {
                    span.alpha = it.animatedValue as Float
                    this@TVeffects.text = spannable
                }
                start()
            }
        }
    }

    private class AlphaSpan(var alpha: Float) : CharacterStyle(), UpdateAppearance {
        override fun updateDrawState(tp: android.text.TextPaint) {
            tp.alpha = (alpha * 255).toInt().coerceIn(0, 255)
        }
    }
}