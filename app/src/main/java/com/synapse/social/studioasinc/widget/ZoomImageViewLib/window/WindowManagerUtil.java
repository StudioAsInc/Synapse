package com.synapse.social.studioasinc.widget.ZoomImageViewLib.gestures;

import android.os.Build;
import android.os.Looper;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;

public class WindowManagerUtil {
    public static synchronized void removeViewSafety(@NonNull WindowManager windowManager, @NonNull View viewNeedRemove) {
        synchronized (WindowManagerUtil.class) {
            if (!(windowManager == null || viewNeedRemove == null)) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    if (isAttachedToWindow(viewNeedRemove)) {
                        try {
                            windowManager.removeView(viewNeedRemove);
                        } catch (Exception e) {
                        }
                    } else {
                        try {
                            windowManager.removeView(viewNeedRemove);
                        } catch (Exception e2) {
                        }
                    }
                }
            }
        }
    }

    public static synchronized void addViewSafety(@NonNull WindowManager windowManager, @NonNull View viewNeedAdd, @NonNull WindowManager.LayoutParams params) {
        synchronized (WindowManagerUtil.class) {
            if (!(windowManager == null || viewNeedAdd == null || params == null)) {
                if (Looper.myLooper() == Looper.getMainLooper() && !isAttachedToWindow(viewNeedAdd)) {
                    try {
                        windowManager.addView(viewNeedAdd, params);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public static boolean isAttachedToWindow(View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            return view.isAttachedToWindow();
        }
        return view.getWindowToken() != null;
    }
}
