package com.gathin.tvhome;

import static android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND;
import static android.content.ComponentCallbacks2.TRIM_MEMORY_COMPLETE;
import static android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL;
import static android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW;
import static android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MemoryTrimPolicyTest {
    @Test public void openingAnAppKeepsTheWallpaper() {
        assertFalse(MemoryTrimPolicy.shouldRelease(TRIM_MEMORY_UI_HIDDEN, false));
    }

    @Test public void backgroundPressureReleasesOnlyWhenHomeIsStopped() {
        assertTrue(MemoryTrimPolicy.shouldRelease(TRIM_MEMORY_BACKGROUND, false));
        assertTrue(MemoryTrimPolicy.shouldRelease(TRIM_MEMORY_COMPLETE, false));
        assertFalse(MemoryTrimPolicy.shouldRelease(TRIM_MEMORY_BACKGROUND, true));
    }

    @Test public void lowMemoryWhileHomeIsOnScreenKeepsTheWallpaper() {
        assertFalse(MemoryTrimPolicy.shouldRelease(TRIM_MEMORY_RUNNING_LOW, true));
        assertFalse(MemoryTrimPolicy.shouldRelease(TRIM_MEMORY_RUNNING_CRITICAL, true));
        assertTrue(MemoryTrimPolicy.shouldRelease(TRIM_MEMORY_RUNNING_LOW, false));
        assertTrue(MemoryTrimPolicy.shouldRelease(TRIM_MEMORY_RUNNING_CRITICAL, false));
    }
}
