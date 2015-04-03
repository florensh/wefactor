package de.hhn.labswps.wefactor.web.DataObjects;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

import de.hhn.labswps.wefactor.domain.Entry;
import de.hhn.labswps.wefactor.specification.WeFactorValues;
import de.hhn.labswps.wefactor.web.util.PageWrapper;

/**
 * The Class EntryList.
 */
public class EntryList extends PageWrapper<Entry> {

    /**
     *
     */
    private static final long serialVersionUID = -251998595554110396L;

    public EntryList(final Page<Entry> page, String url) {
        super(page, url);
    }

    /**
     * Gets the percentage of language.
     *
     * @param lang
     *            the lang
     * @return the percentage of language
     */
    public int getPercentageOfLanguage(
            final WeFactorValues.ProgrammingLanguage lang) {

        return (100 / getSize()) * this.getNumberByLanguage().get(lang);
    }

    /**
     * Gets the number by language.
     *
     * @return the number by language
     */
    public Map<WeFactorValues.ProgrammingLanguage, Integer> getNumberByLanguage() {
        final Map<WeFactorValues.ProgrammingLanguage, Integer> reVal = new HashMap<WeFactorValues.ProgrammingLanguage, Integer>();

        if (getSize() > 0) {
            for (final Entry e : getContent()) {

                Integer count = reVal.get(WeFactorValues.ProgrammingLanguage
                        .getLanguageForMode(e.getLanguage()));

                if (count == null) {
                    count = 0;
                }
                reVal.put(WeFactorValues.ProgrammingLanguage
                        .getLanguageForMode(e.getLanguage()), ++count);

            }
        }

        return reVal;
    }
}
