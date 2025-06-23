package com.synapse.social.studioasinc.widget.ZoomImageViewLib.gestures;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ZoomInImageView extends ImageView {
    ZoomInImageViewAttacher attacher;

    public ZoomInImageView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ZoomInImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomInImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attacher = new ZoomInImageViewAttacher(this);
    }
}
