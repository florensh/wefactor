package de.hhn.labswps.wefactor.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Entry.
 * 
 * @author Patrick Wohlgemuth
 */
@Entity
@Table(name = "entry")
public final class Entry {

    /** The entry code text. */
    private String entryCodeText = null;

    /** The entry date. */
    private Date entryDate;

    /** The entry description. */
    private String entryDescription = null;

    /**
     * Sets the entry code text.
     *
     * @param entryCodeTextParam
     *            the new entry code text
     */
    public void setEntryCodeText(final String entryCodeTextParam) {
        this.entryCodeText = entryCodeTextParam;
    }

    /** The id. */
    private Long id;

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param idParam
     *            the new id
     */
    public void setId(final Long idParam) {
        this.id = idParam;
    }

    /**
     * Sets the entry date.
     *
     * @param entyDate
     *            the new entry date
     */
    public void setEntryDate(final Date entyDate) {
        this.entryDate = entyDate;
    }

    /**
     * Sets the entry description.
     *
     * @param entryDescriptionParam
     *            the new entry description
     */
    public void setEntryDescription(final String entryDescriptionParam) {
        this.entryDescription = entryDescriptionParam;
    }

    /**
     * Gets the entry code text.
     *
     * @return the entry code text
     */
    public String getEntryCodeText() {
        return this.entryCodeText;
    }

    /**
     * Gets the entry date.
     *
     * @return the entry date
     */
    public Date getEntryDate() {
        return this.entryDate;
    }

    /**
     * Gets the entry description.
     *
     * @return the entry description
     */
    public String getEntryDescription() {
        return this.entryDescription;
    }
}
