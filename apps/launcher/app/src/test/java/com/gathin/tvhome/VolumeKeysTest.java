package com.gathin.tvhome;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VolumeKeysTest {
    private static final int KEYCODE_VOLUME_UP = 24;
    private static final int KEYCODE_VOLUME_DOWN = 25;
    private static final int KEYCODE_VOLUME_MUTE = 164;
    private static final int KEYCODE_DPAD_UP = 19;
    private static final int ADJUST_RAISE = 1;
    private static final int ADJUST_LOWER = -1;
    private static final int ACTION_DOWN = 0;
    private static final int ACTION_UP = 1;

    @Test public void volumeUpAndDownAreHandled() {
        assertEquals(ADJUST_RAISE, VolumeKeys.direction(KEYCODE_VOLUME_UP));
        assertEquals(ADJUST_LOWER, VolumeKeys.direction(KEYCODE_VOLUME_DOWN));
    }

    @Test public void otherKeysPassThrough() {
        assertEquals(0, VolumeKeys.direction(KEYCODE_VOLUME_MUTE));
        assertEquals(0, VolumeKeys.direction(KEYCODE_DPAD_UP));
    }

    @Test public void onlyTheFirstDownAndTheUpChangeThePress() {
        assertTrue(VolumeKeys.changesPress(ACTION_DOWN, 0));
        assertTrue(VolumeKeys.changesPress(ACTION_UP, 0));
        assertFalse(VolumeKeys.changesPress(ACTION_DOWN, 1));
        assertFalse(VolumeKeys.changesPress(ACTION_DOWN, 12));
    }
}
