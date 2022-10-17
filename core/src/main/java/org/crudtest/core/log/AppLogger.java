package org.crudtest.core.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.crudtest.core.properties.ApplicationProperties;

public class AppLogger {

    static final FileHandler fh;

    static {
        // TODO create Formatter
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$s %2$s %5$s%6$s%n");

        try {

            fh = new FileHandler(ApplicationProperties.LOG_PATH.getValue());
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            fh.setLevel(changeLevel(ApplicationProperties.LOG_LEVEL.getValue()));
        } catch (IOException ie) {
            throw new RuntimeException(ie);
        }

    }

    static Level changeLevel(String level) {
        switch (level) {
        case "DEBUG":
            return Level.FINE;
        case "INFO":
            return Level.INFO;
        case "WARN":
            return Level.WARNING;
        case "ERROR":
            return Level.SEVERE;
        default:
            return Level.WARNING;
        }
    }

    final Logger log;

    <T> AppLogger(Class<T> clazz) {
        log = Logger.getLogger(clazz.getName());
        log.addHandler(fh);
    }

    public static <T> AppLogger getLogger(Class<T> clazz) {
        return new AppLogger(clazz);
    }

    public void debug(String msg) {
        log.logp(Level.FINE, getOrigin()[0], getOrigin()[1], msg);
    }

    public void info(String msg) {
        log.logp(Level.INFO, getOrigin()[0], getOrigin()[1], msg);
    }

    public void warn(String msg, Throwable e) {
        log.logp(Level.WARNING, getOrigin()[0], getOrigin()[1], msg, e);
    }

    public void error(String msg, Throwable e) {
        log.logp(Level.SEVERE, getOrigin()[0], getOrigin()[1], msg, e);
    }

    String[] getOrigin() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        if (stack != null && stack.length > 3) {
            return new String[] { stack[3].getClassName(), stack[3].getMethodName() };
        }
        return new String[] { "unknown", "unknown" };
    }

}
