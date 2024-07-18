package com.dbt.dateparse.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    private static final String initialSplit = "(?<=Z)";
    private static final String secondarySplit = "(?=[-@+])";
    private static final Pattern digitsAtStart = Pattern.compile("^\\d+");
    private static final Pattern lettersAtEnd = Pattern.compile("[a-zA-Z]+$");

    /**
     * Parses the input date string and performs operations on it. Operations take the following
     * format a `+`, `-`, or `@` followed by an amount and a unit. Multiple operations can be
     * applied in succession.
     * <p><ul>
     * <li>`+` Add the offset to the date modifier
     * <li>`-` Subtract offset from the date modifier
     * <li>`@` rounds down to this time unit
     * <li>the amount is an integer
     * <li>the unit is one of either s = second, m = minute, h = hour, d = day, mon = month, y = year
     * </ul><p>
     *
     * @param date the input date string to parse
     * @return the resulting Date object after applying operations
     */
    public static Date parse(String date) {
        // Split the input date string into two parts the date and the
        List<String> initialArrayList = Arrays.asList(date.split(initialSplit));

        // Split the operations in the second part of the input date string
        String[] operations = initialArrayList.get(1).split(secondarySplit);

        // Parse the first part of the input date string and convert it to a Date object
        Date returnDate = Date.from(Instant.parse(initialArrayList.get(0)));

        // Iterate over the operations
        for (String operation : operations) {
            // Parse a single operation and create an array from the parts (+,5,d)
            List<String> operationArray = getOperationsArray(operation);

            // Perform the desired operation based on the first element of the operationsArray
            switch (operationArray.get(0)) {
                case "+":
                    // Add the specified amount to the returnDate
                    returnDate = addToDate(returnDate, operationArray.get(1),
                            operationArray.get(2));
                    break;
                case "-":
                    // Subtract the specified amount from the returnDate
                    returnDate = subtractFromDate(returnDate, operationArray.get(1),
                            operationArray.get(2));
                    break;
                case "@":
                    // Snap the returnDate to the specified unit
                    returnDate = snapToDate(returnDate, operationArray.get(1));
                    break;
            }
        }

        // Return the final parsed Date object
        return returnDate;
    }

    /**
     * Parses the operation string to extract relevant information and create an array of
     * operations.
     *
     * @param operation the input operation string to parse
     * @return a list containing the parsed operations
     */
    private static List<String> getOperationsArray(String operation) {
        List<String> operationsArray = new ArrayList<>();
        String substring = operation.substring(0, 1);
        operationsArray.add(substring);
        Matcher matcher = digitsAtStart.matcher(operation.substring(1));
        if (matcher.find()) {
            operationsArray.add(matcher.group());
        }
        matcher = lettersAtEnd.matcher(operation.substring(1));
        if (matcher.find()) {
            operationsArray.add(matcher.group());
        }
        return operationsArray;
    }

    /**
     * Adds the specified amount of time to the given date based on the unit provided.
     *
     * @param date   the date to which time will be added
     * @param amount the amount of time to add
     * @param unit   the unit in which the time is specified (s, m, h, d, mon)
     * @return the new Date after adding the specified amount of time
     */
    private static Date addToDate(Date date, String amount, String unit) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
        switch (unit) {
            case "s":
                localDateTime = localDateTime.plusSeconds(Integer.valueOf(amount));
                break;
            case "m":
                localDateTime = localDateTime.plusMinutes(Integer.valueOf(amount));
                break;
            case "h":
                localDateTime = localDateTime.plusHours(Integer.valueOf(amount));
                break;
            case "d":
                localDateTime = localDateTime.plusDays(Integer.valueOf(amount));
                break;
            case "mon":
                localDateTime = localDateTime.plusMonths(Integer.valueOf(amount));
                break;
            case "y":
                localDateTime = localDateTime.plusYears(Integer.valueOf(amount));
                break;
        }
        Date newDate = Date.from(localDateTime.atZone(ZoneOffset.UTC).toInstant());
        return newDate;
    }

    /**
     * Subtracts the specified amount of time from the given date based on the unit provided.
     *
     * @param date   the date from which time will be subtracted
     * @param amount the amount of time to subtract
     * @param unit   the unit in which the time is specified (s, m, h, d, mon)
     * @return the new Date after subtracting the specified amount of time
     */
    private static Date subtractFromDate(Date date, String amount, String unit) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
        switch (unit) {
            case "s":
                localDateTime = localDateTime.minusSeconds(Integer.valueOf(amount));
                break;
            case "m":
                localDateTime = localDateTime.minusMinutes(Integer.valueOf(amount));
                break;
            case "h":
                localDateTime = localDateTime.minusHours(Integer.valueOf(amount));
                break;
            case "d":
                localDateTime = localDateTime.minusDays(Integer.valueOf(amount));
                break;
            case "mon":
                localDateTime = localDateTime.minusMonths(Integer.valueOf(amount));
                break;
            case "y":
                localDateTime = localDateTime.minusYears(Integer.valueOf(amount));
                break;
        }
        Date newDate = Date.from(localDateTime.atZone(ZoneOffset.UTC).toInstant());
        return newDate;
    }

    /**
     * Snap the given date to the nearest unit specified by the input string.
     *
     * @param date the date to snap
     * @param unit the unit to which the date should be snapped (s, m, h, d, mon)
     * @return the snapped date
     */
    private static Date snapToDate(Date date, String unit) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
        switch (unit) {
            case "s":
                localDateTime = localDateTime.truncatedTo(ChronoUnit.SECONDS);
                break;
            case "m":
                localDateTime = localDateTime.truncatedTo(ChronoUnit.MINUTES);
                break;
            case "h":
                localDateTime = localDateTime.truncatedTo(ChronoUnit.HOURS);
                break;
            case "d":
                localDateTime = localDateTime.truncatedTo(ChronoUnit.DAYS);
                break;
            case "mon":
                localDateTime = localDateTime.with(TemporalAdjusters.firstDayOfMonth())
                        .truncatedTo(ChronoUnit.DAYS);
                break;
            case "y":
                localDateTime = localDateTime.with(TemporalAdjusters.firstDayOfYear())
                        .truncatedTo(ChronoUnit.DAYS);
                break;
        }
        Date newDate = Date.from(localDateTime.atZone(ZoneOffset.UTC).toInstant());
        return newDate;
    }

}
