package de.hhn.labswps.wefactor.web.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * The DateFormatter to create Strings like just now.
 */
public class DateFormatter {

    /**
     * Format to long date.
     *
     * @param date
     *            the date
     * @return the string
     */
    public String formatToLongDate(final Date date) {

        if (date == null) {
            return "";
        }

        DateFormat df;
        df = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);

        return df.format(date);
    }

    /**
     * Format.
     *
     * @param date
     *            the date
     * @return the string
     */
    public String format(final Date date) {

        final Date now = new Date();
        final Calendar nowCal = new GregorianCalendar();
        final Calendar dateCal = new GregorianCalendar();

        nowCal.setTime(now);
        dateCal.setTime(date);
        final long ms = 1;
        final long min = ms * 60000;
        final long hours = min * 60;
        final long day = hours * 24;
        final long week = day * 7;
        final long year = day * 365;
        final long time = now.getTime() - date.getTime();
        final int y = nowCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
        final int m = nowCal.get(Calendar.MONTH) - dateCal.get(Calendar.MONTH);

        if (time >= year) {
            return (time / year) + " years ago";
        }
        if (((y * 12) + m) > 0) { // in the same year
            int diff = 0;
            if (nowCal.get(Calendar.DAY_OF_MONTH) >= dateCal
                    .get(Calendar.DAY_OF_MONTH)) {
                diff = (y * 12) + m;
            } else {
                diff = ((y * 12) + m) - 1;
            }
            if (diff != 0) {
                return diff + " months ago";
            }
        }
        if (time > week) { // in the same month
            return (time / week) + " weeks ago";
        }
        if ((time > day) && (time < week)) { // in the same week
            return (time / day) + " days ago";
        }
        if (time > hours) { // in the same day
            return (time / hours) + " hours ago";
        }
        if (time > min) { // in the same hour
            return (time / min) + " minutes ago";
        }
        if (time > ms) { // in the same minute
            return "just now";
        } else { // Fallback to date
            final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            return df.format(date);
        }
    }
}
