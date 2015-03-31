package de.hhn.labswps.wefactor.web.util;

import de.hhn.labswps.wefactor.domain.Entry;

public class LabelChecker {

    public boolean isNew(Entry entry) {
        return entry.getCreatedDate().getTime() - System.currentTimeMillis() < 1000
                * 60 * 60 * 24 * 3;
    }

}
