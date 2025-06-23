package com.synapse.social.studioasinc.widget.ZoomImageViewLib.gestures;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import com.synapse.social.studioasinc.widget.ZoomImageViewLib.animation.AnimCompat;
import com.synapse.social.studioasinc.widget.ZoomImageViewLib.animation.SpringInterpolator;
import com.synapse.social.studioasinc.widget.ZoomImageViewLib.gestures.OnScaleAndMoveGestureListener;
import com.synapse.social.studioasinc.widget.ZoomImageViewLib.gestures.ScaleAndMoveDetector;
import com.synapse.social.studioasinc.R;
import java.lang.ref.WeakReference;

public class ZoomInImageViewAttacher implements View.OnTouchListener {
	/* access modifiers changed from: private */
	public int ZOOM_DURATION = 1000;
	private boolean hasInterruptParentNotToHandleTouchEvent = false;
	/* access modifiers changed from: private */
	public Interpolator mAnimInterpolator = new SpringInterpolator(1.0f);
	private Matrix mBaseMatrix = new Matrix();
	private Matrix mDrawMatrix = new Matrix();
	/* access modifiers changed from: private */
	public WeakReference<ImageView> mImageView;
	private final float[] mMatrixValues = new float[9];
	/* access modifiers changed from: private */
	public int mOffsetToLeft;
	/* access modifiers changed from: private */
	public int mOffsetToTop;
	/* access modifiers changed from: private */
	public ScaleAndMoveDetector mScaleGestureDetector;
	/* access modifiers changed from: private */
	public Matrix mSuppMatrix = new Matrix();
	/* access modifiers changed from: private */
	public View mWindowLayout;
	private WindowManager.LayoutParams mWindowLayoutParams;
	private WindowManager mWindowManager;
	/* access modifiers changed from: private */
	public Bitmap mZoomBitmap;
	private boolean mZoomEnabled = true;
	/* access modifiers changed from: private */
	public ImageView mZoomIV;
	
	public ZoomInImageViewAttacher() {
	}
	
	public void attachImageView(ImageView imageView) {
		if (imageView == null) {
			throw new NullPointerException("imageview is null");
		}
		this.mImageView = new WeakReference<>(imageView);
		imageView.setOnTouchListener(this);
		initGestureDectector();
	}
	
	public ZoomInImageViewAttacher(ImageView imageView) {
		attachImageView(imageView);
	}
	
	public synchronized void initGestureDectector() {
		this.mScaleGestureDetector = new ScaleAndMoveDetector(getImageView().getContext(), new OnScaleAndMoveGestureListener() {
			public void onScaleAndMove(ScaleGestureDetector detector, float currentScale, float moveDistanceFromX, float moveDistanceFromY) {
				if (ZoomInImageViewAttacher.this.mZoomIV == null) {
					ZoomInImageViewAttacher.this.mScaleGestureDetector.setStartScale(detector.getScaleFactor());
					return;
				}
				ZoomInImageViewAttacher.this.mSuppMatrix.reset();
				float centerX = ((float) ZoomInImageViewAttacher.this.mOffsetToLeft) + (((float) ZoomInImageViewAttacher.this.getImageView().getWidth()) / 2.0f);
				float centerY = ((float) ZoomInImageViewAttacher.this.mOffsetToTop) + (((float) ZoomInImageViewAttacher.this.getImageView().getHeight()) / 2.0f);
				if (((double) currentScale) >= 1.0d) {
					ZoomInImageViewAttacher.this.mSuppMatrix.postScale(currentScale, currentScale, centerX, centerY);
				} else {
					currentScale = 1.0f;
					ZoomInImageViewAttacher.this.mSuppMatrix.postScale(1.0f, 1.0f, centerX, centerY);
				}
				ZoomInImageViewAttacher.this.mSuppMatrix.postTranslate(moveDistanceFromX, moveDistanceFromY);
				if (ZoomInImageViewAttacher.this.mWindowLayout != null) {
					if (((double) currentScale) < 1.0d) {
						ZoomInImageViewAttacher.this.mWindowLayout.setBackgroundColor(Color.argb(0, 0, 0, 0));
					} else if (((double) currentScale) > 3.0d) {
						ZoomInImageViewAttacher.this.mWindowLayout.setBackgroundColor(Color.argb(200, 0, 0, 0));
					} else {
						ZoomInImageViewAttacher.this.mWindowLayout.setBackgroundColor(Color.argb((int) ((200.0d * (((double) currentScale) - 1.0d)) / 2.0d), 0, 0, 0));
					}
				}
				ZoomInImageViewAttacher.this.setImageViewMatrix(ZoomInImageViewAttacher.this.getDrawMatrix());
			}
			
			public void onScaleBegin(ScaleGestureDetector detector) {
				if (ZoomInImageViewAttacher.this.mZoomBitmap == null) {
					ZoomInImageViewAttacher.this.getImageView().setDrawingCacheEnabled(true);
					Bitmap unused = ZoomInImageViewAttacher.this.mZoomBitmap = Bitmap.createBitmap(ZoomInImageViewAttacher.this.getImageView().getDrawingCache());
					ZoomInImageViewAttacher.this.getImageView().destroyDrawingCache();
				}
				ZoomInImageViewAttacher.this.createZoomImage(ZoomInImageViewAttacher.this.mZoomBitmap, ZoomInImageViewAttacher.this.mOffsetToLeft, ZoomInImageViewAttacher.this.mOffsetToTop);
			}
			
			public void onScaleEnd(ScaleGestureDetector detector, float moveDistanceFromX, float moveDistanceFromY) {
				ZoomInImageViewAttacher.this.onReleaseZoom(moveDistanceFromX, moveDistanceFromY);
			}
		});
	}
	
	public void setZoomReleaseAnimInterpolator(Interpolator animInterpolator) {
		if (animInterpolator != null) {
			this.mAnimInterpolator = animInterpolator;
		}
	}
	
	public void setZoomReleaseAnimDuration(int duration) {
		if (duration > 0) {
			this.ZOOM_DURATION = duration;
		}
	}
	
	public void setZoomable(boolean zoomable) {
		this.mZoomEnabled = zoomable;
	}
	
	public void detach() {
		clean();
	}
	
	private void clean() {
		if (this.mImageView != null) {
			if (this.mImageView.get() != null) {
				((ImageView) this.mImageView.get()).setOnTouchListener((View.OnTouchListener) null);
			}
			this.mImageView = null;
		}
		this.mWindowLayoutParams = null;
		this.mWindowLayout = null;
		this.mWindowManager = null;
		this.mScaleGestureDetector = null;
		recycleZoomBitmap();
	}
	
	/* access modifiers changed from: private */
	public void onReleaseZoom(float disx, float disy) {
		if (getImageView() != null && this.mZoomIV != null) {
			this.mZoomIV.post(new AnimatedZoomRunnable(getScale(), 1.0f, ((float) this.mOffsetToLeft) + (((float) this.mZoomBitmap.getWidth()) / 2.0f), ((float) this.mOffsetToTop) + (((float) this.mZoomBitmap.getHeight()) / 2.0f), disx, disy));
		}
	}
	
	/* access modifiers changed from: private */
	public void removeZoomImage() {
		this.hasInterruptParentNotToHandleTouchEvent = false;
		if (this.mZoomIV != null) {
			this.mZoomIV.setVisibility(4);
			if (!(this.mWindowManager == null || this.mWindowLayout == null)) {
				WindowManagerUtil.removeViewSafety(this.mWindowManager, this.mWindowLayout);
			}
			this.mZoomIV = null;
			recycleZoomBitmap();
		}
	}
	
	private void recycleZoomBitmap() {
		if (this.mZoomBitmap != null && !this.mZoomBitmap.isRecycled()) {
			this.mZoomBitmap.recycle();
			this.mZoomBitmap = null;
		}
	}
	
	/* access modifiers changed from: private */
	public synchronized void createZoomImage(Bitmap bitmap, int mOffsetToLeft2, int mOffsetToTop2) {
		if (getImageView() != null) {
			if (this.mWindowLayoutParams == null || this.mWindowLayout == null) {
				this.mWindowLayoutParams = new WindowManager.LayoutParams();
				this.mWindowLayoutParams.format = 1;
				this.mWindowLayoutParams.alpha = 1.0f;
				this.mWindowLayoutParams.width = -1;
				this.mWindowLayoutParams.height = -1;
				this.mWindowLayoutParams.type = 1000;
				this.mWindowLayout = LayoutInflater.from(getImageView().getContext()).inflate(R.layout.layout_zoominimage, (ViewGroup) null);
			}
			this.mWindowLayout.setClickable(true);
			this.mZoomIV = (ImageView) this.mWindowLayout.findViewById(R.id.iv_zoominpic);
			this.mZoomIV.setVisibility(0);
			this.mZoomIV.setImageBitmap(bitmap);
			this.mBaseMatrix.reset();
			this.mSuppMatrix.reset();
			this.mBaseMatrix.postTranslate((float) mOffsetToLeft2, (float) mOffsetToTop2);
			this.mDrawMatrix.set(this.mBaseMatrix);
			this.mZoomIV.setScaleType(ImageView.ScaleType.MATRIX);
			setImageViewMatrix(this.mDrawMatrix);
			this.mWindowLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				public void onGlobalLayout() {
					if (ZoomInImageViewAttacher.this.mImageView.get() != null) {
						ZoomInImageViewAttacher.this.getImageView().postDelayed(new Runnable() {
							public void run() {
								ZoomInImageViewAttacher.this.getImageView().setVisibility(4);
							}
						}, 300);
					}
				}
			});
			Log.e("ivd", "show drag image");
			if (this.mWindowManager == null) {
				this.mWindowManager = (WindowManager) getImageView().getContext().getSystemService("window");
			}
			WindowManagerUtil.addViewSafety(this.mWindowManager, this.mWindowLayout, this.mWindowLayoutParams);
		}
	}
	
	public Matrix getDrawMatrix() {
		this.mDrawMatrix.set(this.mBaseMatrix);
		this.mDrawMatrix.postConcat(this.mSuppMatrix);
		return this.mDrawMatrix;
	}
	
	/* access modifiers changed from: private */
	public void setImageViewMatrix(Matrix matrix) {
		if (this.mZoomIV != null) {
			this.mZoomIV.setImageMatrix(matrix);
		}
	}
	
	public boolean onTouch(View view, MotionEvent event) {
		if (this.mZoomEnabled) {
			if (event.getPointerCount() < 2) {
				this.hasInterruptParentNotToHandleTouchEvent = false;
				view.getParent().requestDisallowInterceptTouchEvent(false);
			} else if (!this.hasInterruptParentNotToHandleTouchEvent) {
				view.getParent().requestDisallowInterceptTouchEvent(true);
				this.hasInterruptParentNotToHandleTouchEvent = true;
			}
		}
		switch (event.getAction()) {
			case 0:
			this.mOffsetToTop = (int) (event.getRawY() - event.getY());
			this.mOffsetToLeft = (int) (event.getRawX() - event.getX());
			break;
		}
		boolean handled = false;
		if (this.mZoomEnabled && this.mScaleGestureDetector != null && this.mScaleGestureDetector.onTouchEvent(event)) {
			handled = true;
		}
		if (view.onTouchEvent(event)) {
			return true;
		}
		return handled;
	}
	
	public ImageView getImageView() {
		if (this.mImageView != null) {
			return (ImageView) this.mImageView.get();
		}
		clean();
		return null;
	}
	
	public float getScale() {
		return (float) Math.sqrt((double) (((float) Math.pow((double) getValue(this.mSuppMatrix, 0), 2.0d)) + ((float) Math.pow((double) getValue(this.mSuppMatrix, 3), 2.0d))));
	}
	
	private float getValue(Matrix matrix, int whichValue) {
		matrix.getValues(this.mMatrixValues);
		return this.mMatrixValues[whichValue];
	}
	
	private class AnimatedZoomRunnable implements Runnable {
		private final float mFocalX;
		private final float mFocalY;
		private final long mStartTime = System.currentTimeMillis();
		private final float mTranslateDistanceX;
		private final float mTranslateDistanceY;
		private final float mZoomEndScale;
		private final float mZoomStartScale;
		
		public AnimatedZoomRunnable(float currentZoom, float targetZoom, float focalX, float focalY, float translateDistanceX, float translateDistanceY) {
			this.mFocalX = focalX;
			this.mFocalY = focalY;
			this.mZoomStartScale = currentZoom;
			this.mZoomEndScale = targetZoom;
			this.mTranslateDistanceX = translateDistanceX;
			this.mTranslateDistanceY = translateDistanceY;
			ZoomInImageViewAttacher.this.mWindowLayout.setBackgroundColor(Color.argb(0, 0, 0, 0));
		}
		
		public void run() {
			ImageView imageView = ZoomInImageViewAttacher.this.mZoomIV;
			if (imageView != null) {
				float time = (1.0f * ((float) (System.currentTimeMillis() - this.mStartTime))) / ((float) ZoomInImageViewAttacher.this.ZOOM_DURATION);
				float t = ZoomInImageViewAttacher.this.mAnimInterpolator.getInterpolation(time);
				float scales = this.mZoomStartScale + ((this.mZoomEndScale - this.mZoomStartScale) * t);
				ZoomInImageViewAttacher.this.mSuppMatrix.reset();
				ZoomInImageViewAttacher.this.mSuppMatrix.postScale(scales, scales, this.mFocalX, this.mFocalY);
				ZoomInImageViewAttacher.this.mSuppMatrix.postTranslate(this.mTranslateDistanceX + ((0.0f - this.mTranslateDistanceX) * t), this.mTranslateDistanceY + ((0.0f - this.mTranslateDistanceY) * t));
				ZoomInImageViewAttacher.this.setImageViewMatrix(ZoomInImageViewAttacher.this.getDrawMatrix());
				float stopTime = 1.0f;
				if (ZoomInImageViewAttacher.this.mAnimInterpolator instanceof SpringInterpolator) {
					stopTime = 0.8f;
				}
				if (time < stopTime) {
					AnimCompat.postOnAnimation(imageView, this);
				} else {
					ZoomInImageViewAttacher.this.getImageView().post(new Runnable() {
						public void run() {
							ZoomInImageViewAttacher.this.getImageView().setVisibility(0);
							ZoomInImageViewAttacher.this.removeZoomImage();
						}
					});
				}
			}
		}
	}
}