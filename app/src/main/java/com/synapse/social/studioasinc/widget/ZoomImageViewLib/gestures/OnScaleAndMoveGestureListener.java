package com.synapse.social.studioasinc.widget.ZoomImageViewLib.gestures;

import android.view.ScaleGestureDetector;

public interface OnScaleAndMoveGestureListener {
    void onScaleAndMove(ScaleGestureDetector scaleGestureDetector, float f, float f2, float f3);

    void onScaleBegin(ScaleGestureDetector scaleGestureDetector);

    void onScaleEnd(ScaleGestureDetector scaleGestureDetector, float f, float f2);
}
