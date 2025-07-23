package com.synapse.social.studioasinc.widget.ZoomImageViewLib.gestures;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class ScaleAndMoveDetector implements ScaleGestureDetector.OnScaleGestureListener {
    private float LastX = 0.0f;
    private float LastY = 0.0f;
    private float mCurrentScale = 1.0f;
    private final ScaleGestureDetector mDetector;
    private OnScaleAndMoveGestureListener mDetectorListener;
    private float moveDisFromX = 0.0f;
    private float moveDisFromY = 0.0f;
    private float scaleStart = 1.0f;

    public ScaleAndMoveDetector(Context context, OnScaleAndMoveGestureListener detectorListener) {
        this.mDetector = new ScaleGestureDetector(context, this);
        this.mDetectorListener = detectorListener;
    }

    public boolean onScale(ScaleGestureDetector detector) {
        float tempx = detector.getFocusX();
        float tempy = detector.getFocusY();
        this.moveDisFromX = tempx - this.LastX;
        this.moveDisFromY = tempy - this.LastY;
        if (this.mDetectorListener == null) {
            return false;
        }
        this.mDetectorListener.onScaleAndMove(detector, getCurrentScale(detector.getScaleFactor()), this.moveDisFromX, this.moveDisFromY);
        return false;
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        this.LastX = detector.getFocusX();
        this.LastY = detector.getFocusY();
        this.moveDisFromX = 0.0f;
        this.moveDisFromY = 0.0f;
        this.scaleStart = detector.getScaleFactor();
        if (this.mDetectorListener == null) {
            return true;
        }
        this.mDetectorListener.onScaleBegin(detector);
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
        if (this.mDetectorListener != null) {
            this.mDetectorListener.onScaleEnd(detector, this.moveDisFromX, this.moveDisFromY);
        }
    }

    public void setStartScale(float scaleFactor) {
        this.scaleStart = scaleFactor;
    }

    private float getCurrentScale(float scaleFactor) {
        this.mCurrentScale = (1.0f / this.scaleStart) * scaleFactor;
        return this.mCurrentScale;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        return this.mDetector.onTouchEvent(ev);
    }
}
