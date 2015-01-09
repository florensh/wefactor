package de.hhn.labswps.wefactor.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class Entry.
 *
 * @author Patrick Wohlgemuth
 */
@Entity
@Table(name = "entry")
@Where(clause = "inactive = 'N'")
// @SQLDelete(sql = "UPDATE entry set inactive = 'Y' WHERE Id = ?")
@JsonIgnoreProperties({ "id", "softDeleted", "Account", "createdBy",
        "lastModifiedBy" })
@DiscriminatorColumn(name = "ENTRY_TYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Entry extends BaseSoftDeletableEntity implements
        Comparable<Entry> {

    /** The account. */
    private Account account;

    /** The changes. */
    private String changes;

    /** The entry code text. */
    private String entryCodeText = null;

    /** The entry date. */
    private Date entryDate;

    /** The entry description. */
    private String entryDescription = null;

    /** The group. */
    private Group group;

    /** The language. */
    private String language;

    /** The name. */
    private String name = null;

    /** The ratings. */
    private Set<EntryRating> ratings = new HashSet<EntryRating>();

    /** The statistics. */
    private EntryStatistics statistics = new EntryStatistics();

    /** The tags. */
    private Set<Tag> tags = new HashSet<Tag>();

    /** The teaser. */
    private String teaser;

    /**
     * Adds the rating.
     *
     * @param rating
     *            the rating
     */
    public void addRating(final EntryRating rating) {
        this.ratings.add(rating);
        rating.setEntry(this);
    }

    /**
     * Adds the tag.
     *
     * @param tag
     *            the tag
     */
    public void addTag(final Tag tag) {
        this.tags.add(tag);

    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Entry o) {
        if (this.entryDate.before(o.entryDate)) {
            return 1;
        } else if (this.entryDate.after(o.entryDate)) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Gets the account.
     *
     * @return the account
     */
    @ManyToOne
    @JoinColumn(name = "myAccount", nullable = false)
    public Account getAccount() {
        return this.account;
    }

    /**
     * Gets the changes.
     *
     * @return the changes
     */
    public String getChanges() {
        return this.changes;
    }

    /**
     * Gets the description short.
     *
     * @return the description short
     */
    @Transient
    public String getDescriptionShort() {
        final int maxLength = 100;
        if (this.entryDescription != null) {
            if (this.entryDescription.length() > maxLength) {
                return this.entryDescription.substring(0, maxLength) + "...";

            } else {
                return this.entryDescription;
            }
        } else {
            return "";
        }
    }

    /**
     * Gets the entry code text.
     *
     * @return the entry code text
     */
    @Lob
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
     * Gets the entry date as string.
     *
     * @return the entry date as string
     */
    @Transient
    public String getEntryDateAsString() {
        final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(this.entryDate);
    }

    /**
     * Gets the entry description.
     *
     * @return the entry description
     */
    @Lob
    public String getEntryDescription() {
        return this.entryDescription;
    }

    /**
     * Gets the group.
     *
     * @return the group
     */
    @ManyToOne
    @JoinColumn(name = "myGroup", nullable = true)
    public Group getGroup() {
        return this.group;
    }

    /**
     * Gets the head version.
     *
     * @return the head version
     */
    @Transient
    public Entry getHeadVersion() {
        return this.getOrderedVersions().get(0);
    }

    /**
     * Gets the language.
     *
     * @return the language
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    @Transient
    private Object[] getOrderedVersionAsArray(final Function<Entry, String> f) {
        return this.getOrderedVersions().stream().map(f)
                .collect(Collectors.toList()).toArray();
    }

    /**
     * Gets the ordered version ids.
     *
     * @return the ordered version ids
     */
    @Transient
    public Object[] getOrderedVersionIds() {
        return this.getOrderedVersionAsArray(x -> x.getId().toString());
    }

    /**
     * Gets the ordered versions.
     *
     * @return the ordered versions
     */
    @Transient
    public abstract List<Entry> getOrderedVersions();

    /**
     * Gets the ordered version types.
     *
     * @return the ordered version types
     */
    @Transient
    public Object[] getOrderedVersionTypes() {
        return this.getOrderedVersionAsArray(x -> x.getClass().getSimpleName());
    }

    /**
     * Gets the parent.
     *
     * @return the parent
     */
    @Transient
    public abstract Entry getParent();

    /**
     * Gets the ranking value.
     *
     * @return the ranking value
     */
    @Transient
    @JsonProperty("rankingValue")
    public Double getRankingValue() {

        if (this.ratings.isEmpty()) {
            return new Double("0.0");
        }

        Double d = new Double("0.0");
        int a = 0;
        for (final EntryRating ra : this.ratings) {
            d = d + ra.getValue();
            a++;
        }

        return d / a;
    }

    /**
     * Gets the rating count.
     *
     * @return the rating count
     */
    @Transient
    @JsonProperty("rankingCount")
    public int getRatingCount() {
        return this.ratings.size();
    }

    /**
     * Gets the ratings.
     *
     * @return the ratings
     */
    @OneToMany(mappedBy = "entry")
    public Set<EntryRating> getRatings() {
        return this.ratings;
    }

    /**
     * Gets the statistics.
     *
     * @return the statistics
     */
    @Embedded
    public EntryStatistics getStatistics() {
        return this.statistics;
    }

    /**
     * Gets the tag as strings.
     *
     * @return the tag as strings
     */
    @Transient
    @JsonProperty("tagAsStrings")
    public Set<String> getTagAsStrings() {
        final Set<String> retVal = new HashSet<String>();
        for (final Tag t : this.tags) {
            retVal.add(t.getName());
        }
        return retVal;
    }

    /**
     * Gets the tags.
     *
     * @return the tags
     */
    @ManyToMany
    @JoinTable(name = "TAGMAP", joinColumns = { @JoinColumn(name = "ENTRY_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "TAG_ID", referencedColumnName = "ID") })
    public Set<Tag> getTags() {
        return this.tags;
    }

    /**
     * Gets the teaser.
     *
     * @return the teaser
     */
    public String getTeaser() {
        return this.teaser;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Transient
    public String getType() {
        return this.getClass().getSimpleName();
    }

    /**
     * Gets the version display text.
     *
     * @return the version display text
     */
    @Transient
    public abstract String getVersionDisplayText();

    /**
     * Gets the versionnumber.
     *
     * @return the versionnumber
     */
    @Transient
    public int getVersionnumber() {
        return this.getParent().getOrderedVersions().size()
                - this.getParent().getOrderedVersions().indexOf(this);
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
     * Sets the changes.
     *
     * @param changes
     *            the new changes
     */
    public void setChanges(final String changes) {
        this.changes = changes;
    }

    /**
     * Sets the entry code text.
     *
     * @param entryCodeTextParam
     *            the new entry code text
     */
    public void setEntryCodeText(final String entryCodeTextParam) {
        this.entryCodeText = entryCodeTextParam;
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
     * Sets the group.
     *
     * @param group
     *            the new group
     */
    public void setGroup(final Group group) {
        this.group = group;
    }

    /**
     * Sets the language.
     *
     * @param language
     *            the new language
     */
    public void setLanguage(final String language) {
        this.language = language;
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

    /**
     * Sets the ratings.
     *
     * @param ratings
     *            the new ratings
     */
    public void setRatings(final Set<EntryRating> ratings) {
        this.ratings = ratings;
    }

    /**
     * Sets the statistics.
     *
     * @param statistics
     *            the new statistics
     */
    public void setStatistics(final EntryStatistics statistics) {
        this.statistics = statistics;
    }

    /**
     * Sets the tags.
     *
     * @param tags
     *            the new tags
     */
    public void setTags(final Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Sets the teaser.
     *
     * @param teaser
     *            the new teaser
     */
    public void setTeaser(final String teaser) {
        this.teaser = teaser;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.name;
    }

}
