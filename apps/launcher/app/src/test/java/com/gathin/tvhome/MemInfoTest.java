package com.gathin.tvhome;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Test;

public class MemInfoTest {
    @Test public void memAvailableIsUsedWhenTheKernelHasIt() {
        assertEquals(945, MemInfo.availableMb(Arrays.asList(
                "MemTotal:        1572864 kB", "MemFree:          102400 kB",
                "MemAvailable:     967680 kB", "Buffers:           10240 kB",
                "Cached:           204800 kB")));
    }

    @Test public void olderKernelsAddFreeBuffersAndCached() {
        assertEquals(310, MemInfo.availableMb(Arrays.asList(
                "MemTotal:        1572864 kB", "MemFree:          102400 kB",
                "Buffers:           10240 kB", "Cached:           204800 kB")));
    }

    @Test public void noFigureAtAllIsUnknown() {
        assertEquals(-1, MemInfo.availableMb(
                Arrays.asList("MemTotal: 1572864 kB", "MemFree: lots")));
    }
}
