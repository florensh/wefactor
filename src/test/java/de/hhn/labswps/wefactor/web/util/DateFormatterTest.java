package de.hhn.labswps.wefactor.web.util;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.BaseTest;

public class DateFormatterTest extends BaseTest {

    @Autowired
    private DateFormatter dateFormatter;
    private Date date = new Date();
    private Date date2 = new Date();

    @Test
    public void testJustNow() {
        date.setTime(date.getTime() - 1L);
        assertTrue(this.dateFormatter.format(date).equalsIgnoreCase("just now"));

        date2.setTime(date2.getTime() - 59000L);
        assertTrue(this.dateFormatter.format(date2)
                .equalsIgnoreCase("just now"));
    }

    @Test
    public void testMinutesago() {
        date.setTime(date.getTime() - 60000L);
        assertTrue(this.dateFormatter.format(date).equalsIgnoreCase(
                "1 minutes ago"));

        date2.setTime(date2.getTime() - 3599000L);
        assertTrue(this.dateFormatter.format(date2).equalsIgnoreCase(
                "59 minutes ago"));
    }

    @Test
    public void testHoursago() {
        date.setTime(date.getTime() - 3600000L);
        assertTrue(this.dateFormatter.format(date).equalsIgnoreCase(
                "1 hours ago"));

        date2.setTime(date2.getTime() - 86399000L);
        assertTrue(this.dateFormatter.format(date2).equalsIgnoreCase(
                "23 hours ago"));
    }

    @Test
    public void testDaysago() {
        date.setTime(date.getTime() - 86400000L);
        assertTrue(this.dateFormatter.format(date).equalsIgnoreCase(
                "1 days ago"));

        date2.setTime(date2.getTime() - 604799000L);
        assertTrue(this.dateFormatter.format(date2).equalsIgnoreCase(
                "6 days ago"));
    }

    @Test
    public void testWeeksago() {
        date.setTime(date.getTime() - 604800000L);
        assertTrue(this.dateFormatter.format(date).equalsIgnoreCase(
                "1 weeks ago"));

        date2.setTime(date2.getTime() - 2591999000L);
        assertTrue(this.dateFormatter.format(date2).equalsIgnoreCase(
                "4 weeks ago"));
    }

    @Test
    public void testMonthsago() {
        date.setTime(date.getTime() - 4078400000L);
        assertTrue(this.dateFormatter.format(date).equalsIgnoreCase(
                "1 months ago"));

        date2.setTime(date2.getTime() - 31449600000L);
        assertTrue(this.dateFormatter.format(date2).equalsIgnoreCase(
                "11 months ago"));
    }

    @Test
    public void testYearsago() {
        date.setTime(date.getTime() - 31536000000L);
        assertTrue(this.dateFormatter.format(date).equalsIgnoreCase(
                "1 years ago"));
        date2.setTime(date2.getTime() - 315360000000L);
        assertTrue(this.dateFormatter.format(date2).equalsIgnoreCase(
                "10 years ago"));
    }
}