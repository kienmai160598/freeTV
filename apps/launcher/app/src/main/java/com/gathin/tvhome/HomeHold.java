package com.gathin.tvhome;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

final class HomeHold {

    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    private static Runnable pendingHold;
    private static long heldPressDownTime = -1;

    private HomeHold() {
    }

    static boolean isHomeKey(KeyEvent event) {
        return event.getKeyCode() == KeyEvent.KEYCODE_HOME;
    }

    static boolean handle(Context context, KeyEvent event, Runnable shortPress) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getRepeatCount() == 0) arm(context, event.getDownTime());
            return true;
        }

        boolean held = disarm() == event.getDownTime();
        if (!held && !event.isCanceled()) shortPress.run();
        return true;
    }

    private static void arm(Context context, long downTime) {
        disarm();
        pendingHold = () -> {
            pendingHold = null;
            heldPressDownTime = downTime;
            openSwitcher(context);
        };
        MAIN.postDelayed(pendingHold, ViewConfiguration.getLongPressTimeout());
    }

    private static long disarm() {
        if (pendingHold != null) MAIN.removeCallbacks(pendingHold);
        pendingHold = null;
        long held = heldPressDownTime;
        heldPressDownTime = -1;
        return held;
    }

    static void openSwitcher(Context context) {
        Intent switcher = new Intent(context, AppSwitcherActivity.class);
        context.startActivity(switcher.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
