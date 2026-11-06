package com.gathin.tvhome;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BannerShapeTest {
    @Test
    public void wideBannersAreKept() {
        assertTrue(AppBanner.isBannerShaped(320, 180));
        assertTrue(AppBanner.isBannerShaped(1920, 1080));
    }

    @Test
    public void squareAndTallPicturesAreNotBanners() {
        assertFalse(AppBanner.isBannerShaped(64, 64));
        assertFalse(AppBanner.isBannerShaped(256, 256));
        assertFalse(AppBanner.isBannerShaped(100, 160));
    }

    @Test
    public void drawablesWithoutAnIntrinsicSizeStillFillTheTile() {
        assertTrue(AppBanner.isBannerShaped(-1, -1));
        assertTrue(AppBanner.isBannerShaped(0, 0));
    }
}
