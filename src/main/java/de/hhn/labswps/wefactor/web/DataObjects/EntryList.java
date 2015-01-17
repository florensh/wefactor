package de.hhn.labswps.wefactor.web.DataObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hhn.labswps.wefactor.domain.Entry;
import de.hhn.labswps.wefactor.specification.WeFactorValues;

/**
 * The Class EntryList.
 */
public class EntryList extends ArrayList<Entry> implements List<Entry> {

    /**
     *
     */
    private static final long serialVersionUID = -251998595554110396L;

    /**
     * Instantiates a new entry list.
     *
     * @param entries
     *            the entries
     */
    public EntryList(final List<Entry> entries) {
        this.addAll(entries);
    }

    /**
     * Instantiates a new entry list.
     */
    public EntryList() {
    }

    /**
     * Instantiates a new entry list.
     *
     * @param entries
     *            the entries
     */
    public EntryList(final Set<Entry> entries) {
        this.addAll(entries);
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

        return (100 / this.size()) * this.getNumberByLanguage().get(lang);
    }

    /**
     * Gets the number by language.
     *
     * @return the number by language
     */
    public Map<WeFactorValues.ProgrammingLanguage, Integer> getNumberByLanguage() {
        final Map<WeFactorValues.ProgrammingLanguage, Integer> reVal = new HashMap<WeFactorValues.ProgrammingLanguage, Integer>();

        if (!this.isEmpty()) {
            for (final Entry e : this) {

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
