package com.synapse.social.studioasinc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class CenterCropLinearLayout extends LinearLayout {
    private Drawable backgroundDrawable;
    private Paint fadePaint;
    private Rect fadeRect;

    public CenterCropLinearLayout(Context context) {
        super(context);
        init(context, null);
    }

    public CenterCropLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CenterCropLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            int[] attrsArray = new int[]{android.R.attr.background};
            TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
            backgroundDrawable = ta.getDrawable(0);
            ta.recycle();
        }

        fadePaint = new Paint();
        fadeRect = new Rect();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (backgroundDrawable != null) {
            int viewWidth = getWidth();
            int viewHeight = getHeight();
            int drawableWidth = backgroundDrawable.getIntrinsicWidth();
            int drawableHeight = backgroundDrawable.getIntrinsicHeight();

            float scale = Math.max((float) viewWidth / drawableWidth, (float) viewHeight / drawableHeight);

            int scaledWidth = Math.round(scale * drawableWidth);
            int scaledHeight = Math.round(scale * drawableHeight);

            int dx = (viewWidth - scaledWidth) / 2;
            int dy = (viewHeight - scaledHeight) / 2;

            backgroundDrawable.setBounds(dx, dy, dx + scaledWidth, dy + scaledHeight);
            backgroundDrawable.draw(canvas);

            // Draw the fade effect below the background drawable
            int fadeHeight = (int) getResources().getDisplayMetrics().density * 300;
            fadeRect.set(0, viewHeight - fadeHeight, viewWidth, viewHeight);
            LinearGradient gradient = new LinearGradient(0, viewHeight - fadeHeight, 0, viewHeight, 0x00FFFFFF, 0xFF333333, Shader.TileMode.CLAMP);
            fadePaint.setShader(gradient);
            canvas.drawRect(fadeRect, fadePaint);
        }

        super.dispatchDraw(canvas);
    }

    @Override
    public void setBackground(Drawable background) {
        backgroundDrawable = background;
        invalidate();
    }

    @Override
    public void setBackgroundResource(int resid) {
        backgroundDrawable = ContextCompat.getDrawable(getContext(), resid);
        invalidate();
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        backgroundDrawable = background;
        invalidate();
    }
}

// Join Telegram @studioasinc
// https://t.me/studioasinc