package de.hhn.labswps.wefactor.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class Tag.
 */
@Entity
@Table(name = "Tag")
@JsonIgnoreProperties({ "id", "entries", "createdBy", "lastModifiedBy" })
public class Tag extends BaseEntity {

    /** The entries. */
    private Set<Entry> entries = new HashSet<Entry>();

    /** The name. */
    private String name;

    /**
     * Instantiates a new tag.
     */
    public Tag() {

    }

    /**
     * Instantiates a new tag.
     *
     * @param name
     *            the name
     */
    public Tag(final String name) {
        this.name = name;
    }

    /**
     * Gets the entries.
     *
     * @return the entries
     */
    @ManyToMany(mappedBy = "tags")
    public Set<Entry> getEntries() {
        return this.entries;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the entries.
     *
     * @param entries
     *            the new entries
     */
    public void setEntries(final Set<Entry> entries) {
        this.entries = entries;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(final String name) {
        this.name = name;
    }

}
