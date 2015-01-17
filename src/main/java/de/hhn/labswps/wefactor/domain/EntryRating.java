package de.hhn.labswps.wefactor.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * User provide ratings to entries to describe its quality.
 */
@Entity
@Table(name = "entryRating")
public class EntryRating extends BaseEntity {

    /** The account. */
    private Account account;

    /** The entry. */
    private Entry entry;

    /** The value. */
    private Integer value;

    /**
     * Gets the account.
     *
     * @return the account
     */
    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    public Account getAccount() {
        return this.account;
    }

    /**
     * Gets the entry.
     *
     * @return the entry
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entry")
    public Entry getEntry() {
        return this.entry;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Integer getValue() {
        return this.value;
    }

    /**
     * Sets the account.
     *
     * @param account
     *            the new account
     */
    public void setAccount(final Account account) {
        this.account = account;
    }

    /**
     * Sets the entry.
     *
     * @param entry
     *            the new entry
     */
    public void setEntry(final Entry entry) {
        this.entry = entry;
    }

    /**
     * Sets the value.
     *
     * @param value
     *            the new value
     */
    public void setValue(final Integer value) {
        this.value = value;
    }

}
