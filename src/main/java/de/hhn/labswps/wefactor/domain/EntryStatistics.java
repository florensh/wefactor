package de.hhn.labswps.wefactor.domain;

import javax.persistence.Embeddable;

/**
 * The Class EntryStatistics contains information of an entry e.g. the count of
 * views.
 */
@Embeddable
public final class EntryStatistics {

    /** The views. */
    private int views = 0;

    /**
     * Adds the views.
     *
     * @param count
     *            the count
     */
    public void addViews(final int count) {
        this.views += count;
    }

    /**
     * Gets the views.
     *
     * @return the views
     */
    public int getViews() {
        return this.views;
    }

    /**
     * Sets the views.
     *
     * @param views
     *            the new views
     */
    public void setViews(final int views) {
        this.views = views;
    }

}
