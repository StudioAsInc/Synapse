package com.synapse.social.studioasinc;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;

public class RoundedBackgroundSpan extends ReplacementSpan {
    private final int backgroundColor;
    private final int textColor;
    private final float radius;

    public RoundedBackgroundSpan(int backgroundColor, int textColor, float radius) {
        super();
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.radius = radius;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return (int) (paint.measureText(text, start, end));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        RectF rect = new RectF(x, top, x + measureText(paint, text, start, end), bottom);
        paint.setColor(backgroundColor);
        canvas.drawRoundRect(rect, radius, radius, paint);
        paint.setColor(textColor);
        canvas.drawText(text, start, end, x, y, paint);
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }
}