package com.example.demo.datetime;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class SaveAndFetchTimeFromDB {

    public static void main(String[] args) {

    }

    public static Date getDateFromDateTime(Date date, BigDecimal decimalTime) {
        // convert decimal to hours, minutes and seconds
        int hours = (int) decimalTime.doubleValue();
        double remainingMinutesDecimal = (decimalTime.doubleValue() - hours) * 60;
        int minutes = (int) remainingMinutesDecimal;
        double remainingSecondsDecimal = (remainingMinutesDecimal - minutes) * 60;
        int seconds = (int) remainingSecondsDecimal;

        // this is done because some stores maybe open for more than 24 hours
        int daysPassed = hours / 24;
        if (daysPassed > 0) {
            hours = hours - (24 * daysPassed);
        }

        // create LocalTime using the hours, minutes and seconds
        LocalTime localTime = LocalTime.of(hours, minutes, seconds);

        // creating the LocalDate
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        LocalDate localDate = LocalDate.of(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH) + daysPassed // add this to move the date as well to next day
        );

        // finally create the LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
