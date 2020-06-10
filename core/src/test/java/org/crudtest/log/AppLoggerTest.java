package org.crudtest.log;

import org.junit.jupiter.api.Test;

class AppLoggerTest {

    static AppLogger log = AppLogger.getLogger(AppLoggerTest.class);

    @Test
    void test() {
        log.info("INFOログ");
        log.error("例外が発生しました。", new RuntimeException("hoge"));
    }

}
