package com.gathin.tvhome;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;

final class VolumeKeys {
    static final long REPEAT_DELAY_MS = 500;
    static final long REPEAT_INTERVAL_MS = 60;
    static final int MAX_REPEATS = 100;

    private static Handler repeatHandler;
    private static Runnable repeatStep;

    private VolumeKeys() { }

    static int direction(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) return AudioManager.ADJUST_RAISE;
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) return AudioManager.ADJUST_LOWER;
        return 0;
    }

    static boolean changesPress(int action, int repeatCount) {
        return action != KeyEvent.ACTION_DOWN || repeatCount == 0;
    }

    static boolean handle(Context context, KeyEvent event) {
        int direction = direction(event.getKeyCode());
        if (direction == 0) return false;
        if (!changesPress(event.getAction(), event.getRepeatCount())) return true;

        stop();
        if (event.getAction() != KeyEvent.ACTION_DOWN) return true;

        AudioManager audio = context.getSystemService(AudioManager.class);
        if (audio == null) return true;

        startRepeat(audio, direction);
        return true;
    }

    static void stop() {
        if (repeatHandler != null && repeatStep != null) repeatHandler.removeCallbacks(repeatStep);
        repeatStep = null;
    }

    private static void startRepeat(AudioManager audio, int direction) {
        if (repeatHandler == null) repeatHandler = new Handler(Looper.getMainLooper());
        repeatStep = new Runnable() {
            private int repeats;

            @Override
            public void run() {
                audio.adjustVolume(direction, AudioManager.FLAG_SHOW_UI);
                repeats++;
                if (repeats < MAX_REPEATS) repeatHandler.postDelayed(this, REPEAT_INTERVAL_MS);
            }
        };
        repeatHandler.postDelayed(repeatStep, REPEAT_DELAY_MS);
    }
}
