package com.dbt.dateparse.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.Test;

class DateUtilTest {

    @Test
    public void testParseAddSeconds() {
        Date testDate = DateUtil.parse("2024-07-15T12:00:00Z+5s");
        assertEquals(Date.from(Instant.parse("2024-07-15T12:00:05Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseMinusMinutes() {
        Date testDate = DateUtil.parse("2024-07-15T12:00:00Z-10m");
        assertEquals(Date.from(Instant.parse("2024-07-15T11:50:00Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseAddHours() {
        Date testDate = DateUtil.parse("2024-07-15T12:00:00Z+2h");
        assertEquals(Date.from(Instant.parse("2024-07-15T14:00:00Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseAddDays() {
        Date testDate = DateUtil.parse("2024-07-15T12:00:00Z+1d");
        assertEquals(Date.from(Instant.parse("2024-07-16T12:00:00Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseMinusMonths() {
        Date testDate = DateUtil.parse("2024-07-15T12:00:00Z-8mon");
        assertEquals(Date.from(Instant.parse("2023-11-15T12:00:00Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseAddMonths() {
        Date testDate = DateUtil.parse("2024-07-15T12:00:00Z+3mon");
        assertEquals(Date.from(Instant.parse("2024-10-15T12:00:00Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseSecondsSnapped() {
        Date testDate = DateUtil.parse("2024-07-15T12:34:56Z@s");
        assertEquals(Date.from(Instant.parse("2024-07-15T12:34:56Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseMinutesSnapped() {
        Date testDate = DateUtil.parse("2024-07-15T12:34:56Z@m");
        assertEquals(Date.from(Instant.parse("2024-07-15T12:34:00Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseWithHoursSnapped() {
        Date testDate = DateUtil.parse("2024-07-15T12:34:56Z@h");
        assertEquals(Date.from(Instant.parse("2024-07-15T12:00:00Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseDaysSnapped() {
        Date testDate = DateUtil.parse("2024-07-15T12:34:56Z@d");
        assertEquals(Date.from(Instant.parse("2024-07-15T00:00:00Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseMonthsSnapped() {
        Date testDate = DateUtil.parse("2024-07-15T12:34:56Z@mon");
        assertEquals(Date.from(Instant.parse("2024-07-01T00:00:00Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseYearsSnapped() {
        Date testDate = DateUtil.parse("2024-07-15T12:34:56Z@y");
        assertEquals(Date.from(Instant.parse("2024-01-01T00:00:00Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseMultipleAdds() {
        Date testDate = DateUtil.parse("2024-07-15T12:34:56Z+10s+10m+10h+10d+10mon");
        assertEquals(Date.from(Instant.parse("2025-05-25T22:45:06Z")), testDate,
                "The parsed date should match the expected date.");
    }

    @Test
    public void testParseMultipleAddAndMinus() {
        Date testDate = DateUtil.parse("2024-07-15T12:34:56Z-10s+10m-15h+10d+10mon");
        assertEquals(Date.from(Instant.parse("2025-05-24T21:44:46Z")), testDate,
                "The parsed date should match the expected date.");
    }

}