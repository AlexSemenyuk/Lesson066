package org.itstep;


import java.util.Date;

public class Logger {
    static void log(String... messages) {
        System.out.format("[%s] %s%n", new Date(), String.join(" ", messages));
    }
}
