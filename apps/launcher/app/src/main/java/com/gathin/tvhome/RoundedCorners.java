package com.gathin.tvhome;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

final class RoundedCorners extends ViewOutlineProvider {

    private final float radius;

    private RoundedCorners(float radius) {
        this.radius = radius;
    }

    static void clip(View view, float radiusDp) {
        float density = view.getResources().getDisplayMetrics().density;
        view.setOutlineProvider(new RoundedCorners(radiusDp * density));
        view.setClipToOutline(true);
    }

    @Override
    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
    }
}
