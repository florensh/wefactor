package de.hhn.labswps.wefactor.web.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public String format(final Date date) {

        final Date now = new Date();

        // TODO x minutes before
        // TODO x hours before
        // TODO x days before
        // TODO x weeks before
        // TODO x months before
        // TODO x years before

        if ((now.getTime() - date.getTime()) < 60000) {// in the same minute
            return "just now";
        } else {// Fallback to date
            final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            return df.format(date);
        }

    }
}
