package de.hhn.labswps.wefactor.web.util;

/**
 * The Class StringUtil cuts Strings to a given length
 * 
 * @author Patrick Wohlgemuth
 * @date 05.12.2014
 */
public class StringUtil {

    /**
     * Cut string to a given length.
     * gets two parameter, the String itself and the length where this string
     * will be
     * cut
     */
    public String getCutString(String s, int i) {
        String emptyString = "";

        if (s.length() > i + 3) {
            String dotted = "...";
            return s.substring(0, i) + dotted;
        } else if (s.length() == 0) {
            return emptyString;
        } else {
            return s;
        }

    }
}
