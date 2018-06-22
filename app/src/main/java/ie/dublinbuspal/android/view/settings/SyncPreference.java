package ie.dublinbuspal.android.view.settings;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class SyncPreference extends Preference {

    private ImageView sync;
    private boolean refreshing;

    public SyncPreference(Context context) {
        super(context);
    }

    public SyncPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SyncPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        View view = super.onCreateView(parent);
        setSync(view);
        return view;
    }

    private void setSync(View preferenceView) {
        if (preferenceView != null && preferenceView instanceof ViewGroup) {
            ViewGroup viewGroup = preferenceView.findViewById(android.R.id.widget_frame);
            if (viewGroup != null) {
                int count = viewGroup.getChildCount();
                for (int i = 0; i < count; i++) {
                    View syncView = viewGroup.getChildAt(i);
                    if (syncView instanceof ImageView) {
                        sync = (ImageView) syncView;
                        break;
                    }
                }
            }
        }
    }

    void setRefreshing(boolean refreshing) {
        this.refreshing = refreshing;
        if (refreshing) {
            RotateAnimation anim = new RotateAnimation(360.0f, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setDuration(750);
            anim.setRepeatCount(Animation.INFINITE);
            sync.startAnimation(anim);
        } else {
            sync.clearAnimation();
        }
    }

    boolean isRefreshing() {
        return refreshing;
    }

}
