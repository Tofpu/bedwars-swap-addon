package io.tofpu.bedwarsswapaddon.util;

public class TimeUtil {
    public static long millisToSeconds(final long millis) {
        return millis / 1000L;
    }

    public static long timeElapsed(final long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    public static long timeElapsedSeconds(final long startTime) {
        return TimeUtil.millisToSeconds(timeElapsed(startTime));
    }
}
