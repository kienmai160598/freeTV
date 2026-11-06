package com.gathin.tvhome;

/**
 * Available RAM from the lines of /proc/meminfo. MemAvailable exists only since Linux 3.14; on
 * older kernels (a Philips TPM171E on Android TV 8 runs 3.10) it is MemFree + Buffers + Cached,
 * the estimate free(1) used before MemAvailable.
 */
final class MemInfo {
    private MemInfo() { }

    static long availableMb(Iterable<String> lines) {
        long available = -1, free = -1, buffers = -1, cached = -1;
        for (String line : lines) {
            if (line.startsWith("MemAvailable:")) available = kb(line);
            else if (line.startsWith("MemFree:")) free = kb(line);
            else if (line.startsWith("Buffers:")) buffers = kb(line);
            else if (line.startsWith("Cached:")) cached = kb(line);
        }
        if (available < 0 && free >= 0) {
            available = free + Math.max(0, buffers) + Math.max(0, cached);
        }
        return available < 0 ? -1 : available / 1024;
    }

    private static long kb(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length < 2) return -1;
        try {
            return Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
