package org.itstep;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Task01 {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
//        System.out.println(date.);
        System.out.println(date.getMonth());
        System.out.println(date.getTime());
        System.out.println(date.toString());

        LocalDateTime data1 = LocalDateTime.now();
        System.out.println("data1 - " + data1);

        LocalDate data2 = LocalDate.now();
        System.out.println("data2 - " + data2);

    }
}
