package org.crudtest.core.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static Clock clock = Clock.systemDefaultZone();

    public static String formattedSysDate() {
        return DateTimeFormatter.ofPattern("uuuuMMddHHmmss").format(LocalDateTime.now(clock));
    }

    public static Date sysdate() {
        return new Date();
    }
}
