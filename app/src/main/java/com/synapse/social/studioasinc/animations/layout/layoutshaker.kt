// Crafted by Ashik from StudioAs Inc.
package com.synapse.social.studioasinc.animations.layout

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import kotlin.math.PI
import kotlin.math.sin
import kotlin.math.exp

object layoutshaker {
    private const val SHAKE_DURATION = 600L
    private const val INITIAL_AMPLITUDE = 14f
    
    @JvmStatic
    fun shake(view: View) {
        view.clearAnimation()
        SpringShakeAnimation().apply {
            duration = SHAKE_DURATION
            view.startAnimation(this)
        }
    }

    private class SpringShakeAnimation : Animation() {
        private val amplitude = INITIAL_AMPLITUDE
        private val cycles = 6.5f
        private val beta = 3.5f

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            val angle = 2f * PI.toFloat() * cycles * interpolatedTime
            val displacement = amplitude * sin(angle) * 
                               exp(-beta * interpolatedTime).toFloat()
            t.matrix.setTranslate(displacement, 0f)
        }
    }
}