package org.leafall.authservice.utils;

import java.time.Instant;
import java.util.Date;

public abstract class TimeUtils {

    public static long getCurrentTimeFromUTC() {
        return Instant.now().toEpochMilli();
    }

    public static Date getExpiredDateFromUTC(long minutesToExpire) {
        var time = getSecondsFromMinutes(minutesToExpire);
        var instant = Instant.now().plusSeconds(time);
        return Date.from(instant);
    }

    private static long getSecondsFromMinutes(long minutes) {
        return minutes * 60;
    }
}
