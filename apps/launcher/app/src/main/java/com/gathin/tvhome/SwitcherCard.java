package com.gathin.tvhome;

import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;

final class SwitcherCard {

    static final float RADIUS_DP = Shapes.CONTAINER;
    private static final float CARD_PADDING_DP = 12f;
    private static final float REST_SCALE = 0.88f;
    private static final float REST_ALPHA = 0.7f;
    private static final float DISMISS_RISE = 1.1f;

    private final View card;
    private final Palette palette;
    private final boolean animate;
    private final Motion.Spec spatial;
    private final GradientDrawable background = new GradientDrawable();
    private final SpringAnimation scaleX, scaleY, alpha;

    SwitcherCard(View card, View art, Palette palette, Config config) {
        this.card = card;
        this.palette = palette;
        this.animate = config.animations;
        this.spatial = Motion.DEFAULT_SPATIAL.reduced(config.reducedMotion);
        Motion.Spec effects = Motion.DEFAULT_EFFECTS.reduced(config.reducedMotion);
        scaleX = spatial.apply(new SpringAnimation(card, DynamicAnimation.SCALE_X));
        scaleY = spatial.apply(new SpringAnimation(card, DynamicAnimation.SCALE_Y));
        alpha = effects.apply(new SpringAnimation(card, DynamicAnimation.ALPHA));
        scaleX.addUpdateListener((animation, value, velocity) -> redrawHighlight());
        float density = card.getResources().getDisplayMetrics().density;
        background.setCornerRadius(RADIUS_DP * density);
        card.setBackground(background);
        RoundedCorners.clip(art, RADIUS_DP - CARD_PADDING_DP);
        reset();
    }

    void reset() {
        card.setTranslationY(0f);
        showFocus(card.hasFocus(), false);
    }

    void showFocus(boolean focused) {
        showFocus(focused, animate);
    }

    void dismiss(Runnable done) {
        float rise = -card.getHeight() * DISMISS_RISE;
        if (!animate) {
            done.run();
            return;
        }

        alpha.cancel();
        Motion.FAST_EFFECTS.fade(card, 0f, 0);
        spatial.drive(card.animate().translationY(rise))
                .setUpdateListener(animation -> redrawHighlight())
                .withEndAction(done)
                .start();
    }

    private void showFocus(boolean focused, boolean animated) {
        float scale = focused ? 1f : REST_SCALE;
        background.setColor(focused ? palette.surfaceContainerHighest : palette.surfaceContainer);
        if (focused) {
            alpha.cancel();
            card.setAlpha(1f);
        }
        if (!animated) {
            card.setScaleX(scale);
            card.setScaleY(scale);
            if (!focused) card.setAlpha(REST_ALPHA);
            redrawHighlight();
            return;
        }

        scaleX.animateToFinalPosition(scale);
        scaleY.animateToFinalPosition(scale);
        if (!focused) alpha.animateToFinalPosition(REST_ALPHA);
    }

    private void redrawHighlight() {
        if (card.getParent() instanceof View) ((View) card.getParent()).invalidate();
    }
}
