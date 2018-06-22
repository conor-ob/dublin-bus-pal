package ie.dublinbuspal.android.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;

import com.google.android.gms.maps.model.Marker;

public final class AnimationUtilities {

    private static final int HALF_SECOND = 500;
    private static final float TRANSPARENT = 0f;
    private static final float OPAQUE = 1f;

    public static void fadeInMarker(final Marker marker) {
        ObjectAnimator.ofFloat(marker, "alpha", TRANSPARENT, OPAQUE).setDuration(HALF_SECOND).start();
    }

    public static void fadeOutMarker(final Marker marker) {
        Animator animator = ObjectAnimator.ofFloat(marker, "alpha", OPAQUE, TRANSPARENT);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                marker.remove();
            }
            @Override public void onAnimationStart(Animator animator) {}
            @Override public void onAnimationCancel(Animator animator) {}
            @Override public void onAnimationRepeat(Animator animator) {}
        });
        animator.setDuration(HALF_SECOND).start();
    }

}
