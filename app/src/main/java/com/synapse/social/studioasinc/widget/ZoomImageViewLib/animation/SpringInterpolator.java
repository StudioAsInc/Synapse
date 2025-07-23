package com.synapse.social.studioasinc.widget.ZoomImageViewLib.animation;

import android.view.animation.Interpolator;

public class SpringInterpolator implements Interpolator {
    private float factor;

    public SpringInterpolator(float factor2) {
        this.factor = factor2;
    }

    public float getInterpolation(float input) {
        return (float) ((Math.pow(2.0d, (double) (-10.0f * input)) * Math.sin((((double) (input - (this.factor / 4.0f))) * 6.283185307179586d) / ((double) this.factor))) + 1.0d);
    }
}
