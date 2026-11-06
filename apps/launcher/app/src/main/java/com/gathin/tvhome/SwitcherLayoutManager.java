package com.gathin.tvhome;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

final class SwitcherLayoutManager extends LinearLayoutManager {

    private final Motion.Spec spring;
    private final boolean animate;

    SwitcherLayoutManager(Context context, Config config) {
        super(context, HORIZONTAL, false);
        spring = Motion.DEFAULT_SPATIAL.reduced(config.reducedMotion);
        animate = config.animations;
    }

    static void padToCentre(RecyclerView cards) {
        Resources res = cards.getResources();
        int cardWidth = res.getDimensionPixelSize(R.dimen.switcher_card_width)
                + 2 * res.getDimensionPixelSize(R.dimen.switcher_card_margin);
        int side = Math.max(0, (cards.getWidth() - cardWidth) / 2);
        cards.setPadding(side, cards.getPaddingTop(), side, cards.getPaddingBottom());
    }

    @Override
    public boolean requestChildRectangleOnScreen(@NonNull RecyclerView parent, @NonNull View child,
            @NonNull Rect rect, boolean immediate, boolean focusedChildVisible) {
        int childCentre = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2;
        int dx = childCentre - parent.getWidth() / 2;
        if (dx == 0) return false;

        if (immediate || !animate) {
            parent.scrollBy(dx, 0);
        } else {
            parent.smoothScrollBy(dx, 0, spring.interpolator(), (int) spring.durationMs());
        }
        return true;
    }
}
