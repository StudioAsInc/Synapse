package com.synapse.social.studioasinc.widget.ZoomImageViewLib.animation;

import android.os.Build;
import android.view.View;

public class AnimCompat {
    private static final int SIXTY_FPS_INTERVAL = 16;

    public static void postOnAnimation(View view, Runnable runnable) {
        if (Build.VERSION.SDK_INT >= SIXTY_FPS_INTERVAL) {
            view.postOnAnimation(runnable);
        } else {
            view.postDelayed(runnable, 16);
        }
    }
}
