package com.gathin.tvhome;

import android.view.View;

final class SwitcherMotion {

    private static final float ENTER_RISE_DP = 48f;
    private static final float CARDS_RISE_DP = 32f;
    private static final float EXIT_SINK_DP = 24f;

    private final View screen, content, cards;
    private final boolean animate;
    private final Motion.Spec spatial;
    private final float density;

    SwitcherMotion(View screen, View content, View cards, Config config) {
        this.screen = screen;
        this.content = content;
        this.cards = cards;
        this.animate = config.animations;
        this.spatial = Motion.DEFAULT_SPATIAL.reduced(config.reducedMotion);
        this.density = screen.getResources().getDisplayMetrics().density;
    }

    void enter() {
        if (!animate) return;

        screen.setAlpha(0f);
        cards.setAlpha(0f);
        content.setTranslationY(ENTER_RISE_DP * density);
        Motion.DEFAULT_EFFECTS.fade(screen, 1f, 0);
        spatial.drive(content.animate().translationY(0f)).start();
    }

    void revealCards() {
        if (!animate) return;

        cards.setTranslationY(CARDS_RISE_DP * density);
        Motion.DEFAULT_EFFECTS.fade(cards, 1f, 0);
        spatial.drive(cards.animate().translationY(0f)).start();
    }

    void exit(Runnable done) {
        if (!animate) {
            done.run();
            return;
        }

        Motion.FAST_EFFECTS.fade(screen, 0f, 0);
        Motion.FAST_SPATIAL.drive(content.animate().translationY(EXIT_SINK_DP * density)).start();
        screen.postDelayed(done, Motion.FAST_EFFECTS.durationMs());
    }
}
