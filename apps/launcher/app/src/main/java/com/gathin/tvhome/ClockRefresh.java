package com.gathin.tvhome;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;

/**
 * Keeps the TextClocks under a view ticking on their own. Before Android 12 a TextClock without
 * seconds only advances on ACTION_TIME_TICK, which some firmware stops sending after a standby
 * (a TCL Android 9 TV once the power button has been used), freezing every clock until a reboot.
 * Redraws them at each minute change and when the display comes back on (the minute wait runs on
 * uptime, which stands still while the set sleeps). A display listener rather than
 * ACTION_SCREEN_ON, which that firmware does not send to the app on screen. From Android 12 a
 * TextClock times itself.
 */
final class ClockRefresh implements Runnable, DisplayManager.DisplayListener {
    private static final long MARGIN_MS = 250L;

    private final View root;
    private final DisplayManager displays;
    private boolean on = true;

    ClockRefresh(View root) {
        this.root = root;
        displays = (DisplayManager) root.getContext().getSystemService(Context.DISPLAY_SERVICE);
    }

    void start() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) return;
        displays.registerDisplayListener(this, null);
        schedule();
    }

    void stop() {
        root.removeCallbacks(this);
        displays.unregisterDisplayListener(this);
    }

    @Override
    public void run() {
        redraw(root);
        schedule();
    }

    @Override
    public void onDisplayChanged(int displayId) {
        Display display = root.getDisplay();
        if (display == null || display.getDisplayId() != displayId) return;
        boolean nowOn = display.getState() == Display.STATE_ON;
        if (nowOn && !on) run();
        on = nowOn;
    }

    @Override
    public void onDisplayAdded(int displayId) {
    }

    @Override
    public void onDisplayRemoved(int displayId) {
    }

    private void schedule() {
        root.removeCallbacks(this);
        root.postDelayed(this, 60_000L - System.currentTimeMillis() % 60_000L + MARGIN_MS);
    }

    /** Setting the pattern a clock already has calls its onTimeChanged (AOSP 8, 9 and 11). */
    private static void redraw(View view) {
        if (view instanceof TextClock) {
            TextClock clock = (TextClock) view;
            clock.setFormat12Hour(clock.getFormat12Hour());
        } else if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) redraw(group.getChildAt(i));
        }
    }
}
