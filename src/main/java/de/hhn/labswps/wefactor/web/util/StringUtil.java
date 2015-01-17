package de.hhn.labswps.wefactor.web.util;

/**
 * The Class StringUtil cuts Strings to a given length.
 *
 * @author Patrick Wohlgemuth
 */
public final class StringUtil {

    /**
     * Cut string to a given length.
     * gets two parameter, the String itself and the length where this string
     * will be cut
     *
     * @param s
     *            the string to cut
     * @param i
     *            the position to cut
     * @return the cut string
     */
    public String getCutString(final String s, final int i) {
        final String emptyString = "";

        final String dotted = "...";
        if (s.length() > (i + dotted.length())) {
            return s.substring(0, i) + dotted;
        } else if (s.length() == 0) {
            return emptyString;
        } else {
            return s;
        }

    }
}
