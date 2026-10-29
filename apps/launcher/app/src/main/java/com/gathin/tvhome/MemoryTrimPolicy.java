package com.gathin.tvhome;

import android.content.ComponentCallbacks2;

final class MemoryTrimPolicy {
    private MemoryTrimPolicy() { }

    static boolean shouldRelease(int level, boolean homeStarted) {
        if (homeStarted) return false;

        return level >= ComponentCallbacks2.TRIM_MEMORY_BACKGROUND
                || level == ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW
                || level == ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL;
    }
}
