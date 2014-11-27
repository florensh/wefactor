package de.hhn.labswps.wefactor.web.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormatter {

    public String format(final Date date) {

        final Date now = new Date();
        Calendar nowCal = new GregorianCalendar();
        Calendar dateCal = new GregorianCalendar();

        nowCal.setTime(now);
        dateCal.setTime(date);
        final long ms = 1;
        final long min = ms * 60000;
        final long hours = min * 60;
        final long day = hours * 24;
        final long week = day * 7;
        final long year = day * 365;
        long time = now.getTime() - date.getTime();
        int y = nowCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
        int m = nowCal.get(Calendar.MONTH) - dateCal.get(Calendar.MONTH);

        if (time >= year) {//
            return (time / year) + " years ago";
        }
        if ((y * 12 + m) > 0) {// in the same year
            int diff = 0;
            if (nowCal.get(Calendar.DAY_OF_MONTH) >= dateCal
                    .get(Calendar.DAY_OF_MONTH)) {
                diff = y * 12 + m;
            } else {
                diff = (y * 12 + m) - 1;
            }
            if (diff != 0) {
                return diff + " months ago";
            }
        }
        if (time > week) {// in the same month
            return (time / week) + " weeks ago";
        }
        if (time > day && time < week) {// in the same week
            return (time / day) + " days ago";
        }
        if (time > hours) {// in the same day
            return (time / hours) + " hours ago";
        }
        if (time > min) {// in the same hour
            return (time / min) + " minutes ago";
        }
        if (time > ms) {// in the same minute
            return "just now";
        } else {// Fallback to date
            final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            return df.format(date);
        }

        // final LocalDateTime now = LocalDateTime.now();
        // final LocalDateTime postDate = date.toInstant()
        // .atZone(ZoneId.systemDefault()).toLocalDateTime();
        // Period p = Period.between(postDate.toLocalDate(), now.toLocalDate());
        // int y = p.getYears();
        // int m = p.getMonths();
        // int d = p.getDays();
        // if (p.getYears() > 0) {
        // return p.getYears() + " years ago";
        // }
        // if (p.getMonths() != 0) {
        // System.out.println(p.getMonths() + "ges");
        // System.out.println(now.toLocalDate() + "ges");
        // System.out.println(postDate.toLocalDate() + "ges");
        // System.out.println(p.getMonths() + "post");
        // System.out.println(p.getMonths() + "aktuell");
        // return p.getMonths() + " months ago";
        // }
        // if (p.getDays() < 7) {
        // return p.getDays() + " weeks ago";
        // }
        // if (p.getDays() != 0) {
        // return p.getDays() + " days ago";
        // }
        // if (postDate.getHour() < now.getHour()) {
        // return "hours ago";
        // }
        // if (postDate.getMinute() < now.getMinute()) {
        // return (now.getMinute() - postDate.getMinute()) + " minutes ago";
        // }
        // if (postDate.getNano() < now.getNano()) {
        // return "just now";
        // } else {// Fallback to date
        // final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        // return df.format(date);
        // }
        // final Date now = new Date();
        // final long ms = 1000;
        // final long min = ms * 60;
        // final long hours = min * 60;
        // final long day = hours * 24;
        // final long week = day * 7;
        // final long month = day * 30;
        // final long year = day * 365;
        // long time = now.getTime() - date.getTime();
        // // TODO x minutes ago
        // // TODO x hours ago
        // // TODO x days ago
        // // TODO x weeks ago
        // // TODO x months ago
        // // TODO x years ago
        //
        // if (time < min) {// in the same minute
        // return "just now";
        // }
        // if (time < hours) {// in the same hour
        // return (time / min) + " minutes ago";
        // }
        // if (time < day) {// in the same day
        // return (time / hours) + " hours ago";
        // }
        // if (time < week) {// in the same week
        // return (time / day) + " days ago";
        // }
        // if (time < month) {// in the same month
        // return (time / week) + " weeks ago";
        // }
        // if (time < year) {// in the same year
        // return (time / month) + " months ago";
        // }
        // if (time >= year) {//
        // return (time / year) + " years ago";
        // } else {// Fallback to date
        // final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        // return df.format(date);
        // }

    }
}
