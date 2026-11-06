package com.gathin.tvhome;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PowerOnHomeTest {
    private static final String LIVE_TV = "com.mediatek.wwtv.tvcenter";
    private static final long BOOT_WINDOW_MS = 180_000;
    private static final long SOON = 30_000;
    private static final int KEYCODE_DPAD_CENTER = 23;
    private static final int KEYCODE_POWER = 26;
    private static final int KEYCODE_WAKEUP = 224;

    private static PowerOnHome armedAtBoot() {
        PowerOnHome powerOnHome = new PowerOnHome(LIVE_TV);
        powerOnHome.armUntil(BOOT_WINDOW_MS);
        return powerOnHome;
    }

    @Test public void liveTvBeforeAnyKeyGoesHome() {
        assertTrue(armedAtBoot().sendsHome(LIVE_TV, SOON));
    }

    @Test public void otherAppsAreLeftAlone() {
        PowerOnHome powerOnHome = armedAtBoot();
        assertFalse(powerOnHome.sendsHome("com.netflix.ninja", SOON));
        assertFalse(powerOnHome.sendsHome(null, SOON));
    }

    @Test public void theFirstKeyHandsTheTvToTheUser() {
        PowerOnHome powerOnHome = armedAtBoot();
        powerOnHome.onKeyDown(KEYCODE_DPAD_CENTER);
        assertFalse(powerOnHome.sendsHome(LIVE_TV, SOON));
    }

    @Test public void powerAndWakeKeysDoNotCount() {
        PowerOnHome powerOnHome = armedAtBoot();
        powerOnHome.onKeyDown(KEYCODE_POWER);
        powerOnHome.onKeyDown(KEYCODE_WAKEUP);
        assertTrue(powerOnHome.sendsHome(LIVE_TV, SOON));
    }

    @Test public void theWindowEnds() {
        assertFalse(armedAtBoot().sendsHome(LIVE_TV, BOOT_WINDOW_MS));
    }

    @Test public void screenOnArmsForAMinute() {
        PowerOnHome powerOnHome = new PowerOnHome(LIVE_TV);
        long screenOn = 10 * BOOT_WINDOW_MS;
        assertFalse(powerOnHome.sendsHome(LIVE_TV, screenOn));
        powerOnHome.armAfterScreenOn(screenOn);
        assertTrue(powerOnHome.sendsHome(LIVE_TV, screenOn + 1));
        assertFalse(powerOnHome.sendsHome(LIVE_TV, screenOn + PowerOnHome.SCREEN_ON_WINDOW_MS));
    }

    @Test public void offMeansNothing() {
        PowerOnHome powerOnHome = new PowerOnHome("");
        powerOnHome.armUntil(BOOT_WINDOW_MS);
        assertFalse(powerOnHome.enabled());
        assertFalse(powerOnHome.sendsHome(LIVE_TV, SOON));
    }
}
