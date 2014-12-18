package de.hhn.labswps.wefactor.web.DataObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hhn.labswps.wefactor.domain.Entry;
import de.hhn.labswps.wefactor.specification.WeFactorValues;

public class EntryList extends ArrayList<Entry> implements List<Entry> {

    public EntryList(List<Entry> entries) {
        addAll(entries);
    }

    public EntryList() {
    }

    public int getPercentageOfLanguage(WeFactorValues.ProgrammingLanguage lang) {

        return (100 / size()) * getNumberByLanguage().get(lang);
    }

    public Map<WeFactorValues.ProgrammingLanguage, Integer> getNumberByLanguage() {
        Map<WeFactorValues.ProgrammingLanguage, Integer> reVal = new HashMap<WeFactorValues.ProgrammingLanguage, Integer>();

        if (!isEmpty()) {
            for (Entry e : this) {

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
