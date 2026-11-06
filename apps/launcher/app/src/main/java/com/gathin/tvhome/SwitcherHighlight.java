package com.gathin.tvhome;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView;

final class SwitcherHighlight extends RecyclerView.ItemDecoration {

    private static final int OPAQUE = 255;

    private static final FloatPropertyCompat<SwitcherHighlight> REACH =
            new FloatPropertyCompat<SwitcherHighlight>("reach") {
                @Override
                public float getValue(SwitcherHighlight highlight) {
                    return highlight.reachDp;
                }

                @Override
                public void setValue(SwitcherHighlight highlight, float value) {
                    highlight.reachDp = value;
                    highlight.cards.invalidate();
                }
            };

    private final RecyclerView cards;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF shape = new RectF();
    private final float density;
    private final boolean animate;
    private final SpringAnimation pop;
    private float reachDp = FocusIndicator.PADDING_DP;

    private SwitcherHighlight(RecyclerView cards, Palette palette, Config config) {
        this.cards = cards;
        this.density = cards.getResources().getDisplayMetrics().density;
        this.animate = config.animations;
        paint.setColor(palette.primary);
        Motion.Spec fast = Motion.FAST_SPATIAL.reduced(config.reducedMotion);
        pop = fast.apply(new SpringAnimation(this, REACH));
        pop.setMinimumVisibleChange(0.05f);
    }

    static void attach(RecyclerView cards, Palette palette, Config config) {
        SwitcherHighlight highlight = new SwitcherHighlight(cards, palette, config);
        cards.addItemDecoration(highlight);
        cards.getViewTreeObserver().addOnGlobalFocusChangeListener(
                (oldFocus, newFocus) -> highlight.onFocusMoved(newFocus));
    }

    private void onFocusMoved(View focused) {
        if (focused == null || focused.getParent() != cards) {
            cards.invalidate();
            return;
        }

        if (!animate) {
            reachDp = FocusIndicator.PADDING_DP;
            cards.invalidate();
            return;
        }

        pop.cancel();
        reachDp = FocusIndicator.POP_FROM_DP;
        pop.animateToFinalPosition(FocusIndicator.PADDING_DP);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state) {
        View card = parent.getFocusedChild();
        if (card == null || card.getAlpha() <= 0f) return;

        float reach = reachDp * density;
        float halfWidth = card.getWidth() * card.getScaleX() / 2f + reach;
        float halfHeight = card.getHeight() * card.getScaleY() / 2f + reach;
        float centreX = card.getLeft() + card.getTranslationX() + card.getWidth() / 2f;
        float centreY = card.getTop() + card.getTranslationY() + card.getHeight() / 2f;
        shape.set(centreX - halfWidth, centreY - halfHeight,
                centreX + halfWidth, centreY + halfHeight);
        float radius = SwitcherCard.RADIUS_DP * density * card.getScaleX() + reach;
        paint.setAlpha(Math.round(OPAQUE * card.getAlpha()));
        canvas.drawRoundRect(shape, radius, radius, paint);
    }
}
