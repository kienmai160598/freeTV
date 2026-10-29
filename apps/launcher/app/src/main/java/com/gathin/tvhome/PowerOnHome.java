package com.gathin.tvhome;

import android.view.KeyEvent;

final class PowerOnHome {
    static final long SCREEN_ON_WINDOW_MS = 60_000;

    private final String liveTvPackage;
    private long armedUntil;

    PowerOnHome(String liveTvPackage) {
        this.liveTvPackage = liveTvPackage == null ? "" : liveTvPackage;
    }

    boolean enabled() {
        return !liveTvPackage.isEmpty();
    }

    void armUntil(long uptimeMs) {
        armedUntil = Math.max(armedUntil, uptimeMs);
    }

    void armAfterScreenOn(long nowMs) {
        armUntil(nowMs + SCREEN_ON_WINDOW_MS);
    }

    void onKeyDown(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_POWER || keyCode == KeyEvent.KEYCODE_WAKEUP) return;

        armedUntil = 0;
    }

    boolean sendsHome(CharSequence windowPackage, long nowMs) {
        return enabled() && windowPackage != null && liveTvPackage.contentEquals(windowPackage)
                && nowMs < armedUntil;
    }
}
